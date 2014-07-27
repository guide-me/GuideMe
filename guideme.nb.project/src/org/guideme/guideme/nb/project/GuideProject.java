package org.guideme.guideme.nb.project;

import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import org.guideme.guideme.nb.project.resources.Icons;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

public class GuideProject implements Project {

    private final FileObject projectDirectory;

    private Lookup lookup;
    
    public GuideProject(FileObject projectDirectory) {
        this.projectDirectory = projectDirectory;
    }

    @Override
    public FileObject getProjectDirectory() {
        return projectDirectory;
    }
    
    @Override
    public Lookup getLookup() {
        if (lookup == null) {
            lookup = Lookups.fixed(new Object[] {
                this,
                new Info(),
                //new GuideProjectLogicalView(this)
            });
        }
        return lookup;
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
            return Icons.getGuideIcon();
        }

        @Override
        public Project getProject() {
            return GuideProject.this;
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
