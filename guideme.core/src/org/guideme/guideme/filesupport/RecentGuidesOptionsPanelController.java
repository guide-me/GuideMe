package org.guideme.guideme.filesupport;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

@OptionsPanelController.SubRegistration(
        displayName = "#AdvancedOption_DisplayName_RecentGuides",
        keywords = "#AdvancedOption_Keywords_RecentGuides",
        keywordsCategory = "Advanced/RecentGuides"
)
@org.openide.util.NbBundle.Messages({"AdvancedOption_DisplayName_RecentGuides=Recent Guides", "AdvancedOption_Keywords_RecentGuides=recent guides"})
public final class RecentGuidesOptionsPanelController extends OptionsPanelController {

    private static final RecentGuides RECENT_GUIDES = RecentGuides.getDefault();

    private RecentGuidesPanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;

    void clearHistory() {
        RECENT_GUIDES.clearHistory();
    }

    @Override
    public void update() {
        panel.setMaxToRemember(RECENT_GUIDES.getMaxNumberOfGuides());
        changed = false;
    }

    @Override
    public void applyChanges() {
        SwingUtilities.invokeLater(() -> {
            RECENT_GUIDES.setMaxNumberOfGuides(panel.getMaxToRemember());
            changed = false;
        });
    }

    @Override
    public void cancel() {
        // need not do anything special, if no changes have been persisted yet
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null; // new HelpCtx("...ID") if you have a help set
    }

    @Override
    public JComponent getComponent(Lookup masterLookup) {
        return getPanel();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    private RecentGuidesPanel getPanel() {
        if (panel == null) {
            panel = new RecentGuidesPanel(this);
        }
        return panel;
    }

    void changed() {
        if (!changed) {
            changed = true;
            pcs.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
        }
        pcs.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
    }

}
