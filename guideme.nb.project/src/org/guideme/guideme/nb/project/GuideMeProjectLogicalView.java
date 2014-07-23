package org.guideme.guideme.nb.project;

import java.awt.Image;
import org.guideme.guideme.model.Guide;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.FilterNode;
import org.openide.nodes.FilterNode.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

public class GuideMeProjectLogicalView implements LogicalViewProvider {

    private final GuideMeProject project;

    public GuideMeProjectLogicalView(GuideMeProject project) {
        this.project = project;
    }
    
    
    @Override
    public Node createLogicalView() {
        try {
            Node guideNode = DataFolder.find(project.getProjectFile()).getNodeDelegate();
            return new GuideNode(guideNode, project);
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
            return new AbstractNode(Children.LEAF);
        }
    }

    @Override
    public Node findPath(Node root, Object target) {
        // Not implemented yet.
        return null;
    }

    
    private static final class GuideNode extends FilterNode {

        private final GuideMeProject project;

        public GuideNode(Node node, GuideMeProject project) throws DataObjectNotFoundException {
            super(
                node,
                createPageNodes(project),
                new ProxyLookup(
                        Lookups.singleton(project),
                        node.getLookup()
                )
            );
            this.project = project;
        }
        
        @Override
        public Image getIcon(int type) {
            return ImageUtilities.loadImage("org/guideme/guideme/nb/project/project_icon.gif");
        }

        @Override
        public Image getOpenedIcon(int type) {
            return getIcon(type);
        }

        @Override
        public String getDisplayName() {
            return project.getProjectDirectory().getName();
        }
        
        private static org.openide.nodes.Children createPageNodes(GuideMeProject project) {
            return NodeFactorySupport.createCompositeChildren(project, "Projects/org-guideme-guideme-nb-project");
        }
    }
}
