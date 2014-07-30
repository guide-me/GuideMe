package org.guideme.guideme.milovana;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.guideme.guideme.model.Guide;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;

public class MilovanaDownloadWizardPanel2 implements WizardDescriptor.Panel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private MilovanaDownloadVisualPanel2 component;

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

    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        // return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
        if (component.getProjectName().length() == 0) {
            // Project Name is not a valid folder name.
            return false;
        }
        File f = FileUtil.normalizeFile(new File(component.getProjectLocation()).getAbsoluteFile());
        if (!f.isDirectory()) {
            // Project Folder is not a valid path.
            return false;
        }
        final File destFolder = FileUtil.normalizeFile(new File(component.getCreatedFolder()).getAbsoluteFile());
        File projLoc = destFolder;
        while (projLoc != null && !projLoc.exists()) {
            projLoc = projLoc.getParentFile();
        }
        if (projLoc == null || !projLoc.canWrite()) {
            // Project Folder cannot be created.
            return false;
        }
        if (FileUtil.toFileObject(projLoc) == null) {
            // Project Folder is not a valid path.
            return false;
        }
        File[] kids = destFolder.listFiles();
        if (destFolder.exists() && kids != null && kids.length > 0) {
            // Project Folder already exists and is not empty.
            return false;
        }

        return true;
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
            ls = new HashSet(listeners);
        }
        ChangeEvent ev = new ChangeEvent(this);
        for (ChangeListener l : ls) {
            l.stateChanged(ev);
        }
    }


    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state        
        String projectName = (String)wiz.getProperty("pojectName");
        if (projectName == null || projectName.length() == 0) {
            Guide guide = (Guide)wiz.getProperty("guide");
            projectName = guide.getTitle();
        }
        getComponent().setProjectName(projectName);
        
        File projectLocation = (File) wiz.getProperty("pojectLocation");
        if (projectLocation == null || projectLocation.getParentFile() == null || !projectLocation.getParentFile().isDirectory()) {
            projectLocation = ProjectChooser.getProjectsFolder();
        } else {
            projectLocation = projectLocation.getParentFile();
        }
        getComponent().setProjectLocation(projectLocation.getAbsolutePath());
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
        wiz.putProperty("pojectName", getComponent().getProjectName());
        wiz.putProperty("pojectLocation", new File(getComponent().getProjectLocation()));
    }

}
