package org.guideme.guideme.nb.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectFactory2;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service=ProjectFactory.class)
public class GuideMeProjectFactory implements ProjectFactory2
{
    public static final String PROJECT_FILE = "guide.xml";
    
    @Override
    public boolean isProject(FileObject projectDirectory) {
        return isProject2(projectDirectory) != null;
    }

    @Override
    public Project loadProject(FileObject projectDirectory, ProjectState state) throws IOException {
        return isProject(projectDirectory) ? new GuideMeProject(projectDirectory, state) : null;
    }

    @Override
    public void saveProject(Project project) throws IOException, ClassCastException {
        // Not implemented yet.
    }

    @Override
    public ProjectManager.Result isProject2(FileObject projectDirectory) {
        FileObject projectFile = projectDirectory.getFileObject(PROJECT_FILE);
        if (projectFile == null) {
            return null;
        }
        return new ProjectManager.Result(GuideMeProject.getProjectIcon());
    }

}
