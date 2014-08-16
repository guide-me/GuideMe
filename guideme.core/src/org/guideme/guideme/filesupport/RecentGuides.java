// Implementation is taken and adapted from org.netbeans.modules.openfile.RecentFiles.
package org.guideme.guideme.filesupport;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.modules.OnStop;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
import org.openide.windows.CloneableTopComponent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Manages prioritized set of recently closed guides.
 *
 */
public final class RecentGuides {

    private static final Logger LOG = Logger.getLogger(RecentGuides.class.getName());

    /**
     * List of recently closed files
     */
    private static final List<HistoryItem> history = new ArrayList<>();
    /**
     * Request processor
     */
    private static final RequestProcessor RP = new RequestProcessor(RecentGuides.class);
    /**
     * Preferences node for storing history info
     */
    private static Preferences prefs;
    private static final Object HISTORY_LOCK = new Object();
    /**
     * Name of preferences node where we persist history
     */
    private static final String PREFS_NODE = "RecentGuidesHistory"; //NOI18N
    /**
     * Prefix of property for recent file URL
     */
    private static final String PROP_URL_PREFIX = "RecentGuidesURL."; //NOI18N
    /**
     * Prefix of property for recent guide title.
     */
    private static final String PROP_TITLE_PREFIX = "RecentGuidesTitle."; //NOI18N
    /**
     * Boundary for items count in history
     */
    static final int MAX_HISTORY_ITEMS = 15;

    private static PropertyChangeListener windowRegistryListener;

    private static final String RECENT_FILE_KEY = "nb.recent.file.path"; // NOI18N

    private RecentGuides() {
    }

    /**
     * Starts to listen for recently closed files
     */
    public static void init() {
        WindowManager.getDefault().invokeWhenUIReady(() -> {
            List<HistoryItem> loaded = load();
            synchronized (HISTORY_LOCK) {
                history.addAll(0, loaded);
                if (windowRegistryListener == null) {
                    windowRegistryListener = new WindowRegistryL();
                    TopComponent.getRegistry().addPropertyChangeListener(
                            windowRegistryListener);
                }
            }
        });
    }

    /**
     * Returns read-only list of recently closed guides
     */
    static List<HistoryItem> getRecentGuides() {
        synchronized (HISTORY_LOCK) {
            checkHistory();
            return Collections.unmodifiableList(history);
        }
    }
    private static volatile boolean historyProbablyValid;

    /**
     * True if there are probably some recently closed guides. Note: will still
     * be true if all of them are in fact invalid, but this is much faster than
     * calling {@link #getRecentGuides}.
     *
     * @return
     */
    public static boolean hasRecentGuides() {
        if (!historyProbablyValid) {
            synchronized (HISTORY_LOCK) {
                checkHistory();
                return !history.isEmpty();
            }
        }
        return historyProbablyValid;
    }

    /**
     * Loads list of recent guides stored in previous system sessions.
     *
     * @return list of stored recent guides
     */
    static List<HistoryItem> load() {
        String[] keys;
        Preferences _prefs = getPrefs();
        try {
            keys = _prefs.keys();
        } catch (BackingStoreException ex) {
            LOG.log(Level.FINE, ex.getMessage(), ex);
            return Collections.emptyList();
        }

        List<HistoryItem> result = new ArrayList<>();
        for (String curKey : keys) {
            if (curKey.startsWith(PROP_TITLE_PREFIX)) {
                continue;
            }
            String value = _prefs.get(curKey, null);
            if (value != null) {
                try {
                    int id = Integer.parseInt(curKey.substring(PROP_URL_PREFIX.length()));
                    HistoryItem hItem = new HistoryItem(id, value, _prefs.get(PROP_TITLE_PREFIX + id, value));
                    int ind = result.indexOf(hItem);
                    if (ind == -1) {
                        result.add(hItem);
                    } else {
                        _prefs.remove(PROP_URL_PREFIX
                                + Math.max(result.get(ind).id, id));
                        result.get(ind).id = Math.min(result.get(ind).id, id);
                    }
                } catch (NumberFormatException ex) {
                    LOG.log(Level.FINE, ex.getMessage(), ex);
                    _prefs.remove(curKey);
                }
            } else {
                //clear the recent files history file from the old,
                // not known and broken keys
                _prefs.remove(curKey);
            }
        }
        Collections.sort(result);
        store(result);

        return result;
    }

    static void store() {
        store(history);
    }

    static void store(List<HistoryItem> history) {
        Preferences _prefs = getPrefs();
        for (int i = 0; i < history.size(); i++) {
            HistoryItem hi = history.get(i);
            if ((hi.id != i) && (hi.id >= history.size())) {
                _prefs.remove(PROP_URL_PREFIX + hi.id);
                _prefs.remove(PROP_TITLE_PREFIX + hi.id);
            }
            hi.id = i;
            _prefs.put(PROP_URL_PREFIX + i, hi.getPath());
            _prefs.put(PROP_TITLE_PREFIX + i, hi.getGuideTitle());
        }
        LOG.log(Level.FINE, "Stored");
    }

    /**
     * Clear the history. Should be called only from tests.
     */
    static void clear() {
        try {
            synchronized (HISTORY_LOCK) {
                history.clear();
                getPrefs().clear();
                getPrefs().flush();
            }
        } catch (BackingStoreException ex) {
            LOG.log(Level.WARNING, null, ex);
        }
    }

    static Preferences getPrefs() {
        if (prefs == null) {
            prefs = NbPreferences.forModule(RecentGuides.class).node(PREFS_NODE);
        }
        return prefs;
    }

    /**
     * Adds guide represented by given TopComponent to the list, if conditions
     * are met.
     */
    private static void addFile(final TopComponent tc) {
        RP.post(() -> {
            String path = obtainPath(tc);
            // This works because GuideCover sets the displayname to the guide title.
            String guideTitle = tc.getDisplayName();
            if (guideTitle == null || guideTitle.trim().length() == 0) {
                guideTitle = path;
            }
            addFile(path, guideTitle);
        });
    }

    static void addFile(String path, String guideTitle) {
        if (path != null) {
            historyProbablyValid = false;
            synchronized (HISTORY_LOCK) {
                // avoid duplicates
                HistoryItem hItem;
                do {
                    hItem = findHistoryItem(path);
                } while (history.remove(hItem));

                final HistoryItem newItem = new HistoryItem(0, path, guideTitle);
                history.add(0, newItem);
                for (int i = MAX_HISTORY_ITEMS; i < history.size(); i++) {
                    history.remove(i);
                }
                store();
            }
        }
    }

    /**
     * Removes file represented by given TopComponent from the list
     */
    private static void removeFile(final TopComponent tc) {
        RP.post(() -> {
            historyProbablyValid = false;
            String path = obtainPath(tc);
            if (path != null) {
                synchronized (HISTORY_LOCK) {
                    HistoryItem hItem = findHistoryItem(path);
                    if (hItem != null) {
                        history.remove(hItem);
                    }
                    store();
                }
            }
        });
    }

    private static String obtainPath(TopComponent tc) {
        Object file = tc.getClientProperty(RECENT_FILE_KEY);
        if (file instanceof File) {
            return ((File) file).getPath();
        }
        if (tc instanceof CloneableTopComponent) {
            DataObject dObj = tc.getLookup().lookup(DataObject.class);
            if (dObj != null) {
                FileObject fo = dObj.getPrimaryFile();
                if (fo != null) {
                    return convertFile2Path(fo);
                }
            }
        }
        return null;
    }

    private static HistoryItem findHistoryItem(String path) {
        for (HistoryItem hItem : history) {
            if (path.equals(hItem.getPath())) {
                return hItem;
            }
        }
        return null;
    }

    static String convertFile2Path(FileObject fo) {
        File f = FileUtil.toFile(fo);
        return f == null ? null : f.getPath();
    }

    static FileObject convertPath2File(String path) {
        File f = new File(path);
        f = FileUtil.normalizeFile(f);
        return f == null ? null : FileUtil.toFileObject(f);
    }

    /**
     * Checks recent files history and removes non-valid entries
     */
    private static void checkHistory() {
        assert Thread.holdsLock(HISTORY_LOCK);
        historyProbablyValid = !history.isEmpty();
    }

    static void pruneHistory() {
        synchronized (HISTORY_LOCK) {
            Iterator<HistoryItem> it = history.iterator();
            while (it.hasNext()) {
                HistoryItem historyItem = it.next();
                File f = new File(historyItem.getPath());
                if (!f.exists()) {
                    it.remove();
                }
            }
        }
    }

    /**
     * One item of the recently closed guides history. Comparable by the time
     * field, ascending from most recent to older items.
     */
    static final class HistoryItem implements Comparable<HistoryItem> {

        private int id;
        private final String path;
        private final String guideTitle;
        private String fileName;

        HistoryItem(int id, String path,
                String guideTitle) {
            this.path = path;
            this.id = id;
            this.guideTitle = guideTitle;
        }

        public String getPath() {
            return path;
        }

        public String getGuideTitle() {
            return guideTitle;
        }
        
        public String getFileName() {
            if (fileName == null) {
                int pos = path.lastIndexOf(File.separatorChar);
                if ((pos != -1) && (pos < path.length())) {
                    fileName = path.substring(pos + 1);
                } else {
                    fileName = path;
                }
            }
            return fileName;
        }

        @Override
        public int compareTo(HistoryItem o) {
            return this.id - o.id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof HistoryItem) {
                return ((HistoryItem) obj).getPath().equals(path);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + (this.path != null ? this.path.hashCode() : 0);
            return hash;
        }
    }

    /**
     * Receives info about opened and closed TopComponents from window system.
     */
    private static class WindowRegistryL implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String name = evt.getPropertyName();
            if (TopComponent.Registry.PROP_TC_CLOSED.equals(name)) {
                addFile((TopComponent) evt.getNewValue());
            }
            if (TopComponent.Registry.PROP_TC_OPENED.equals(name)) {
                removeFile((TopComponent) evt.getNewValue());
            }
        }
    }

    /**
     * {@link Runnable} that will be invoked during shutdown sequence and that
     * will add non-persistent {@link TopComponent}s to the list of recent
     * files. See bug #218695.
     */
    @OnStop
    public static final class NonPersistentDocumentsAdder implements Runnable {

        @Override
        public void run() {
            for (TopComponent tc : TopComponent.getRegistry().getOpened()) {
                if (TopComponent.PERSISTENCE_NEVER == tc.getPersistenceType()) {
                    addFile(tc);
                }
            }
        }
    }
}
