package org.guideme.guideme.nb.project;

import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import javax.swing.Icon;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.nb.project.resources.Icons;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

public class GuideProject implements Project {

    private final FileObject projectDirectory;
    private final Guide guide;

    private Lookup lookup;
    
    public GuideProject(FileObject projectDirectory) {
        this.projectDirectory = projectDirectory;
        
        this.guide = parseGuide(projectDirectory.getFileObject(Constants.GUIDE_FILE));
    }

    private Guide parseGuide(FileObject guideFile) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Guide.class);
            return (Guide)ctx.createUnmarshaller().unmarshal(guideFile.getInputStream());
        } catch (FileNotFoundException | JAXBException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }
    
    public Guide getGuide() {
        return guide;
    }
    
    public String getGuideName() {
        return guide.Title != null ? guide.Title : projectDirectory.getName();
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
                new GuideLogicalViewProvider(this)
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
            return getGuideName();
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
