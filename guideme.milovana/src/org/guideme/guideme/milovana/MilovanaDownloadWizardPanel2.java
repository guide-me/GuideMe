package org.guideme.guideme.milovana;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.commons.io.FileUtils;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;

public class MilovanaDownloadWizardPanel2 implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private MilovanaDownloadVisualPanel2 component;
    private File destinationFolder;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public MilovanaDownloadVisualPanel2 getComponent() {
        if (component == null) {
            component = new MilovanaDownloadVisualPanel2(this);
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    boolean isValid = true;
    
    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        // return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
        return isValid;
    }
    
    @Override
    public void validate() throws WizardValidationException {
        if (component.getProjectName().length() == 0) {
            isValid = false;
            throw new WizardValidationException(null, "Project Name is not a valid folder name.", null);
        }
        File f = FileUtil.normalizeFile(new File(component.getProjectLocation()).getAbsoluteFile());
        if (!f.isDirectory()) {
            isValid = false;
            throw new WizardValidationException(null, "Project Folder is not a valid path.", null);
        }
        destinationFolder = FileUtil.normalizeFile(new File(component.getCreatedFolder()).getAbsoluteFile());
        File projLoc = destinationFolder;
        while (projLoc != null && !projLoc.exists()) {
            projLoc = projLoc.getParentFile();
        }
        if (projLoc == null || !projLoc.canWrite()) {
            isValid = false;
            throw new WizardValidationException(null, "Project Folder cannot be created.", null);
        }
        if (FileUtil.toFileObject(projLoc) == null) {
            isValid = false;
            throw new WizardValidationException(null, "Project Folder is not a valid path.", null);
        }
        File[] kids = destinationFolder.listFiles();
        if (destinationFolder.exists() && kids != null && kids.length > 0) {
            isValid = false;
            throw new WizardValidationException(null, "Project Folder already exists and is not empty.", null);
        }
    }
    
    
    private final Set<ChangeListener> listeners = new HashSet<>();

    @Override
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    @Override
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    protected final void fireChangeEvent() {
        Set<ChangeListener> ls;
        synchronized (listeners) {
            ls = new HashSet<>(listeners);
        }
        ChangeEvent ev = new ChangeEvent(this);
        for (ChangeListener l : ls) {
            l.stateChanged(ev);
        }
    }


    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state  
        
        isValid = true;
        
        String projectName = (String)wiz.getProperty("pojectName");
        if (projectName == null || projectName.length() == 0) {
            TeaseSummary teaseSummary = (TeaseSummary)wiz.getProperty("teaseSummary");
            projectName = teaseSummary.getTitle();
        }
        getComponent().setProjectName(projectName);
        
        File projectLocation = (File) wiz.getProperty("pojectLocation");
        if (projectLocation == null) {
            projectLocation = FileUtils.getUserDirectory();
        }
        getComponent().setProjectLocation(projectLocation.getAbsolutePath());
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
        wiz.putProperty("pojectName", getComponent().getProjectName());
        wiz.putProperty("pojectLocation", new File(getComponent().getProjectLocation()));
        wiz.putProperty("destinationFolder", destinationFolder);
    }



}
