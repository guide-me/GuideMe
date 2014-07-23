package org.guideme.guideme.nb.project;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

public class GuideMeProject implements Project {

    @StaticResource()
    public static final String PROJECT_ICON = "org/guideme/guideme/nb/project/project_icon.gif";

    private final FileObject projectFile;
    private final FileObject projectDirectory;

    private Lookup lookup;
    
    private Guide guide;
    
    public GuideMeProject(FileObject projectDirectory) {
        this.projectDirectory = projectDirectory;
        this.projectFile = projectDirectory.getFileObject(GuideMeProjectFactory.PROJECT_FILE);
        this.guide = parseProjectFile();
    }

    
    private Guide parseProjectFile() {
        try
        {
            // TODO refactor to separate class.
            JAXBContext ctx = JAXBContext.newInstance(Guide.class);
            return (Guide)ctx.createUnmarshaller().unmarshal(projectFile.getInputStream());
        } catch (FileNotFoundException | JAXBException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }
    
    public Guide getGuide() {
        return guide;
    }
    
    public FileObject getProjectFile() {
        return projectFile;
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
                new Info(),
                new GuideMeProjectLogicalView(this)
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
            return (guide != null && guide.Title != null) ? guide.Title : getName();
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
