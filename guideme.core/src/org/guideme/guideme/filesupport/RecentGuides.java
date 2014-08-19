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
import org.guideme.guideme.utilities.CentralLookup;
import org.openide.modules.OnStop;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public class RecentGuides {

    private static final Logger LOG = Logger.getLogger(RecentGuides.class.getName());

    /**
     * Request processor
     */
    private static final RequestProcessor RP = new RequestProcessor(RecentGuides.class);

    private static final String PREFS_NODE = "RecentGuides";
    private static final String PROP_PREFIX_TITLE = "RecentGuideTitle.";
    private static final String PROP_PREFIX_PATH = "RecentGuidePath.";
    private static final String PROP_MAX_NUMBER = "MaxNumberOfGuides";
    private static final int DEFAULT_MAX_NUMBER = 15;
    private static RecentGuides instance;

    public static RecentGuides getDefault() {
        if (instance == null) {
            instance = new RecentGuides();
        }
        return instance;
    }

    private final CentralLookup lookup;
    private final Preferences preferences;

    private int maxNumberOfGuides;

    private final WindowRegistryListener windowRegistryListener = new WindowRegistryListener();

    private RecentGuides() {
        lookup = CentralLookup.getDefault();
        preferences = NbPreferences.forModule(RecentGuides.class).node(PREFS_NODE);
        maxNumberOfGuides = preferences.getInt(PROP_MAX_NUMBER, DEFAULT_MAX_NUMBER);
    }

    /**
     * Starts to listen for recently closed files
     */
    public void initialize() {
        WindowManager.getDefault().invokeWhenUIReady(() -> {
            List<RecentGuide> recentGuides = loadFromPreferences();
            recentGuides.stream().forEach((guide) -> {
                lookup.add(guide);
            });
            TopComponent.getRegistry().addPropertyChangeListener(windowRegistryListener);
        });
        LOG.log(Level.INFO, "RecentGuideService initialized.");
    }

    public void removeNonExistingGuides() {
        getGuides().forEach((RecentGuide rg) -> {
            File f = new File(rg.getPath());
            if (!f.exists()) {
                lookup.remove(rg);
            }
        });
    }

    public void clearHistory() {
        getGuides().forEach((RecentGuide rg) -> lookup.remove(rg));
        saveToPreferences();
        LOG.log(Level.INFO, "RecentGuide history cleared.");
    }

    public int getMaxNumberOfGuides() {
        return maxNumberOfGuides;
    }

    public void setMaxNumberOfGuides(int maxNumberOfGuides) {
        this.maxNumberOfGuides = maxNumberOfGuides;
        preferences.putInt(PROP_MAX_NUMBER, this.maxNumberOfGuides);
        removeGuidesIfListIsTooBig();
        saveToPreferences();
    }

    /**
     * The first item in the list is the guide opened most recently.
     * @return 
     */
    public List<RecentGuide> getGuides() {
        List<RecentGuide> result = new ArrayList<>();
        result.addAll(lookup.lookupAll(RecentGuide.class));
        Collections.reverse(result);
        return Collections.unmodifiableList(result);
    }

    private List<RecentGuide> loadFromPreferences() {
        String[] keys;
        try {
            keys = preferences.keys();
        } catch (BackingStoreException ex) {
            LOG.log(Level.FINE, ex.getMessage(), ex);
            return Collections.emptyList();
        }

        List<RecentGuide> result = new ArrayList<>();
        for (String curKey : keys) {
            if (curKey.startsWith(PROP_PREFIX_PATH)) {
                String path = preferences.get(curKey, null);
                if (path != null && new File(path).exists()) {
                    try {
                        int id = Integer.parseInt(curKey.substring(PROP_PREFIX_PATH.length()));
                        RecentGuide recentGuide = new RecentGuide(id, path, preferences.get(PROP_PREFIX_TITLE + id, path));
                        int ind = result.indexOf(recentGuide);
                        if (ind == -1) {
                            result.add(recentGuide);
                        } else {
                            preferences.remove(PROP_PREFIX_PATH + Math.max(result.get(ind).id, id));
                            result.get(ind).id = Math.min(result.get(ind).id, id);
                        }
                    } catch (NumberFormatException ex) {
                        LOG.log(Level.FINE, ex.getMessage(), ex);
                        preferences.remove(curKey);
                    }
                } else {
                    //clear the recent files history file from the old,
                    // not known and broken keys
                    preferences.remove(curKey);
                }

            }
        }
        Collections.sort(result);

        // If the list was bigger than the max, throw the last items away.
        for (int i = maxNumberOfGuides; i < result.size(); i++) {
            result.remove(i);
        }

        return result;
    }

    private void removeGuidesIfListIsTooBig() {
        List<RecentGuide> list = getGuides();
        for (int i = maxNumberOfGuides; i < list.size(); i++) {
            lookup.remove(list.get(i));
        }
    }

    /**
     * Adds guide represented by given TopComponent to the list, if conditions
     * are met.
     *
     * @param tc
     */
    public void addFile(final TopComponent tc) {
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

    private void addFile(String path, String guideTitle) {
        if (path != null) {
            // avoid duplicates
            RecentGuide guide = findRecentGuideByPath(path);
            if (guide != null) {
                lookup.remove(guide);
            }

            lookup.add(new RecentGuide(0, path, guideTitle));

            removeGuidesIfListIsTooBig();

            saveToPreferences();
        }
    }

    /**
     * Removes file represented by given TopComponent from the list
     *
     * @param tc
     */
    public void removeFile(final TopComponent tc) {
        RP.post(() -> {
            GuideDataObject dataObject = tc.getLookup().lookup(GuideDataObject.class);
            if (dataObject != null) {
                String path = dataObject.getGuideFile().getAbsolutePath();
                if (path != null) {
                    RecentGuide rg = findRecentGuideByPath(path);
                    if (rg != null) {
                        lookup.remove(rg);
                        saveToPreferences();
                    }
                }
            }
        });
    }

    private RecentGuide findRecentGuideByPath(String path) {
        return getGuides().stream()
                .filter((RecentGuide rg) -> path.equals(rg.getPath()))
                .findFirst()
                .orElse(null);
    }

    void saveToPreferences() {        
        String[] keys;
        try {
            keys = preferences.keys();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
            return;
        }
        // Just clear the previous values.
        for (String key : keys) {
            if (key.startsWith(PROP_PREFIX_PATH) || key.startsWith(PROP_PREFIX_TITLE)) {
                preferences.remove(key);
            }
        }
        // Save the new list.
        List<RecentGuide> list = new ArrayList<>(getGuides());
        for (int i = 0; i < list.size(); i++) {
            RecentGuide rg = list.get(i);
            rg.id = i;
            preferences.put(PROP_PREFIX_PATH + i, rg.getPath());
            preferences.put(PROP_PREFIX_TITLE + i, rg.getGuideTitle());
        }
        LOG.log(Level.FINE, "Stored");
    }

    /**
     * Receives info about opened and closed TopComponents from window system.
     */
    private static class WindowRegistryListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String name = evt.getPropertyName();
            if (TopComponent.Registry.PROP_TC_CLOSED.equals(name)) {
                getDefault().addFile((TopComponent) evt.getNewValue());
            }
            if (TopComponent.Registry.PROP_TC_OPENED.equals(name)) {
                getDefault().removeFile((TopComponent) evt.getNewValue());
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
                    getDefault().addFile(tc);
                }
            }
        }
    }

}
