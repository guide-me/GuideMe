package org.guideme.guideme.project;

import java.beans.PropertyChangeEvent;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // Make sure only one Guide is open at a time.
        OpenProjects.getDefault().addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if (OpenProjects.PROPERTY_OPEN_PROJECTS.equals(evt.getPropertyName())) {
                Project[] oldProjects = (Project[]) evt.getOldValue();
                Project[] newProjects = (Project[]) evt.getNewValue();
                if (newProjects.length > oldProjects.length) {
                    // Another project is opened.
                    OpenProjects.getDefault().close(oldProjects);
                }
            }
        });
    }
}
