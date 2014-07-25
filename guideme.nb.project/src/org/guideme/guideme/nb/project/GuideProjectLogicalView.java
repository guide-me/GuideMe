package org.guideme.guideme.nb.project;

import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.nodes.Node;

public class GuideProjectLogicalView implements LogicalViewProvider {

    private final GuideProject guideProject;

    public GuideProjectLogicalView(GuideProject guideProject) {
        this.guideProject = guideProject;
    }
    
    @Override
    public Node createLogicalView() {
        return new GuideProjectNode(guideProject.getProjectDirectory());
    }

    @Override
    public Node findPath(Node root, Object target) {
        // Not implemented yet.
        return null;
    }
}
