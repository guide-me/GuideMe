package org.guideme.guideme.nb.project;

import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

public class GuideMeProject implements Project {

    @StaticResource()
    public static final String PROJECT_ICON = "org/guideme/guideme/nb/project/project_icon.gif";

    private final FileObject projectDirectory;
    private final ProjectState projectState;
    private Lookup lookup;
    
    public GuideMeProject(FileObject projectDirectory, ProjectState projectState) {
        this.projectDirectory = projectDirectory;
        this.projectState = projectState;
    }
    
    @Override
    public FileObject getProjectDirectory() {
        return projectDirectory;
    }
    
    @Override
    public Lookup getLookup() {
        if (lookup == null) {
            lookup = Lookups.fixed(new Object[] {
                // Register features here.
                new Info()
            });
        }
        return lookup;
    }
    
    public static Icon getProjectIcon() {
        return new ImageIcon(ImageUtilities.loadImage(PROJECT_ICON));
    }
    
    private final class Info implements ProjectInformation {

        @Override
        public String getName() {
            return getProjectDirectory().getName();
        }

        @Override
        public String getDisplayName() {
            return getName();
        }

        @Override
        public Icon getIcon() {
            return GuideMeProject.getProjectIcon();
        }

        @Override
        public Project getProject() {
            return GuideMeProject.this;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            // Not implemented yet.
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {
            // Not implemented yet.
        }
        
    }
}
