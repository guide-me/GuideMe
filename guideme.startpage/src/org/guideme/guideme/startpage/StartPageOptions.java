// Implementation is taken and adapted from org.netbeans.modules.welcome.WelcomeOptions.
package org.guideme.guideme.startpage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

public class StartPageOptions {

    private static StartPageOptions theInstance;

    private static final String PROP_SHOW_ON_STARTUP = "showOnStartup";
    private static final String PROP_LAST_ACTIVE_TAB = "lastActiveTab";

    private PropertyChangeSupport propSupport;

    /**
     * Creates a new instance of StartPageOptions
     */
    private StartPageOptions() {
    }

    private Preferences prefs() {
        return NbPreferences.forModule(StartPageOptions.class);
    }

    public static synchronized StartPageOptions getDefault() {
        if (null == theInstance) {
            theInstance = new StartPageOptions();
        }
        return theInstance;
    }

    public void setShowOnStartup(boolean show) {
        boolean oldVal = isShowOnStartup();
        if (oldVal == show) {
            return;
        }
        prefs().putBoolean(PROP_SHOW_ON_STARTUP, show);
        if (null != propSupport) {
            propSupport.firePropertyChange(PROP_SHOW_ON_STARTUP, oldVal, show);
        }
    }

    public boolean isShowOnStartup() {
        return prefs().getBoolean(PROP_SHOW_ON_STARTUP, !Boolean.getBoolean("netbeans.full.hack"));
    }

    public void setLastActiveTab(int tabIndex) {
        int oldVal = getLastActiveTab();
        prefs().putInt(PROP_LAST_ACTIVE_TAB, tabIndex);
        if (null != propSupport) {
            propSupport.firePropertyChange(PROP_LAST_ACTIVE_TAB, oldVal, tabIndex);
        }
    }

    public int getLastActiveTab() {
        return prefs().getInt(PROP_LAST_ACTIVE_TAB, -1);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        if (null == propSupport) {
            propSupport = new PropertyChangeSupport(this);
        }
        propSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        if (null == propSupport) {
            return;
        }
        propSupport.removePropertyChangeListener(l);
    }

}
