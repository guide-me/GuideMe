package org.guideme.guideme.nb.project;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.spi.project.SubprojectProvider;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

public class GuidesSubprojectProvider implements SubprojectProvider {

    private final LibraryProject project;

    public GuidesSubprojectProvider(LibraryProject project) {
        this.project = project;
    }

    @Override
    public Set<? extends Project> getSubprojects() {
        return loadProjects(project.getProjectDirectory());
    }

    private Set loadProjects(FileObject dir) {
        Set newProjects = new HashSet();
        for (FileObject child : dir.getChildren()) {
            if (GuideProjectFactory.isGuideFolder(child)) {
                try {
                    Project subp = ProjectManager.getDefault().findProject(child);
                    if (subp != null && subp instanceof GuideProject) {
                        newProjects.add((GuideProject) subp);
                    }
                } catch (IOException | IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        return Collections.unmodifiableSet(newProjects);
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
        // Not implemented yet.
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
        // Not implemented yet.
    }

}
