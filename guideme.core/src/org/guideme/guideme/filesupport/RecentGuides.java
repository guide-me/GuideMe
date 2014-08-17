// Implementation is taken and adapted from org.netbeans.modules.openfile.RecentFiles.
package org.guideme.guideme.filesupport;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.openide.modules.OnStop;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Manages prioritized set of recently closed guides.
 *
 */
public final class RecentGuides {

    private static final Logger LOG = Logger.getLogger(RecentGuides.class.getName());

    /**
     * Request processor
     */
    private static final RequestProcessor RP = new RequestProcessor(RecentGuides.class);

    /**
     * Preferences node for storing history info
     */
    private static Preferences prefs;

    private static final List<HistoryItem> historyItems = new ArrayList<>();

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

    
    private RecentGuides() {
    }

    /**
     * Starts to listen for recently closed files
     */
    public static void init() {
        WindowManager.getDefault().invokeWhenUIReady(() -> {
            List<HistoryItem> loaded = load();
            synchronized (historyItems) {
                historyItems.addAll(loaded);
                if (windowRegistryListener == null) {
                    windowRegistryListener = new WindowRegistryListener();
                    TopComponent.getRegistry().addPropertyChangeListener(
                            windowRegistryListener);
                }
            }
        });
    }

    /**
     * Returns read-only list of recently closed guides
     *
     * @return
     */
    public static List<HistoryItem> getRecentGuides() {
        synchronized (historyItems) {
            return Collections.unmodifiableList(historyItems);
        }
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
        store(getRecentGuides());
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
            GuideDataObject dataObject = tc.getLookup().lookup(GuideDataObject.class);
            if (dataObject != null) {
                String path = dataObject.getGuideFile().getAbsolutePath();
                String guideTitle = dataObject.getGuide().getTitle();
                if (guideTitle == null || guideTitle.trim().length() == 0) {
                    guideTitle = path;
                }
                addFile(path, guideTitle);
            }
        });
    }

    static void addFile(String path, String guideTitle) {
        if (path != null) {
            synchronized (historyItems) {
                // avoid duplicates
                HistoryItem hItem = findHistoryItem(path);
                if (hItem != null) {
                    historyItems.remove(hItem);
                }

                // Add the file to the beginning of the list.
                historyItems.add(0, new HistoryItem(0, path, guideTitle));

                // Remove guides if the list gets too big.
                for (int i = MAX_HISTORY_ITEMS; i < historyItems.size(); i++) {
                    historyItems.remove(i);
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
            GuideDataObject dataObject = tc.getLookup().lookup(GuideDataObject.class);
            if (dataObject != null) {
                String path = dataObject.getGuideFile().getAbsolutePath();
                if (path != null) {
                    synchronized (historyItems) {
                        HistoryItem hItem = findHistoryItem(path);
                        if (hItem != null) {
                            historyItems.remove(hItem);
                        }
                        store();
                    }
                }
            }
        });
    }

    private static HistoryItem findHistoryItem(String path) {
        return historyItems.stream()
                .filter((HistoryItem hItem) -> path.equals(hItem.getPath()))
                .findFirst()
                .orElse(null);
    }

    static void pruneHistory() {
        synchronized (historyItems) {
            historyItems.forEach((HistoryItem historyItem) -> {
                File f = new File(historyItem.getPath());
                if (!f.exists()) {
                    historyItems.remove(historyItem);
                }
            });
        }
    }

    /**
     * Receives info about opened and closed TopComponents from window system.
     */
    private static class WindowRegistryListener implements PropertyChangeListener {

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
