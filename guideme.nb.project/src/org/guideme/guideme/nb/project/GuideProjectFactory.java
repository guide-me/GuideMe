package org.guideme.guideme.nb.project;

import java.io.IOException;
import org.guideme.guideme.nb.project.resources.Icons;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectFactory2;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service=ProjectFactory.class)
public class GuideProjectFactory implements ProjectFactory2
{
    @Override
    public boolean isProject(FileObject projectDirectory) {
        return isProject2(projectDirectory) != null;
    }

    @Override
    public Project loadProject(FileObject projectDirectory, ProjectState state) throws IOException {
        return isGuideFolder(projectDirectory) ? new GuideProject(projectDirectory) : null;
    }

    @Override
    public void saveProject(Project project) throws IOException, ClassCastException {
        // Not implemented yet.
    }

    @Override
    public ProjectManager.Result isProject2(FileObject projectDirectory) {
        return isGuideFolder(projectDirectory) ? new ProjectManager.Result(Icons.getGuideIcon()) : null;
    }

    public static boolean isGuideFolder(FileObject fileObject) {
        return fileObject.isFolder() && fileObject.getFileObject(Constants.GUIDE_FILE) != null;
    }
}
