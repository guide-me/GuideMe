package org.guideme.guideme.milovana;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

@ActionID(category="Milovana", id="org.guideme.guideme.milovana.MilovanaDownloadWizardAction")
@ActionRegistration(displayName="From Milovana...")
@ActionReference(path="Menu/File/Import", position=5)
public final class MilovanaDownloadWizardAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        panels.add(new MilovanaDownloadWizardPanel1());
        panels.add(new MilovanaDownloadWizardPanel2());
        panels.add(new MilovanaDownloadWizardPanel3());
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Download tease from Milovana");
        wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("org/guideme/guideme/milovana/resources/milovana.gif", true));
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            try {
                // Open the just downloaded guide.
                FileObject projectFolder = FileUtil.toFileObject((File)wiz.getProperty("destinationFolder"));
                Project project = ProjectManager.getDefault().findProject(projectFolder);
                Project[] array = new Project[1];
                array[0] = project;
                OpenProjects.getDefault().open(array, false, true);
            } catch (IOException | IllegalArgumentException ex) {
                Exceptions.printStackTrace(ex);
            }

        }
    }

}
