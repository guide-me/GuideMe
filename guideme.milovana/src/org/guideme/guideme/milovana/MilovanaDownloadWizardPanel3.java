package org.guideme.guideme.milovana;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;

public class MilovanaDownloadWizardPanel3 implements WizardDescriptor.Panel<WizardDescriptor>, ChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private MilovanaDownloadVisualPanel3 component;

    private TeaseSummary teaseSummary;
    private File destinationFolder;
    private boolean downloadCompleted;
    private FileObject guideFile;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public MilovanaDownloadVisualPanel3 getComponent() {
        if (component == null) {
            component = new MilovanaDownloadVisualPanel3(this);
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
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
        return downloadCompleted;
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state
        teaseSummary = (TeaseSummary) wiz.getProperty("teaseSummary");
        destinationFolder = (File) wiz.getProperty("destinationFolder");
                
        startDownload();
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
        wiz.putProperty("guideFile", guideFile);
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

    void startDownload() {
        // TODO add async progress reporting.

        downloadCompleted = false;
        addChangeListener(this);

        try {
            guideFile = new MilovanaDownloader().saveGuide(teaseSummary, destinationFolder); 
            component.addProgress("Download complete.");
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        downloadCompleted = true;
        fireChangeEvent();

        removeChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
