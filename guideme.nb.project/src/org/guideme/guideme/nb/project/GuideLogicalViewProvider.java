package org.guideme.guideme.nb.project;

import java.awt.Image;
import java.util.Collection;
import javax.swing.Action;
import org.guideme.guideme.nb.project.resources.Icons;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.openide.loaders.DataFolder;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

public class GuideLogicalViewProvider implements LogicalViewProvider {

    private final GuideProject guideProject;

    public GuideLogicalViewProvider(GuideProject guideProject) {
        this.guideProject = guideProject;
    }

    @Override
    public Node createLogicalView() {
        DataFolder dataObject = DataFolder.findFolder(guideProject.getProjectDirectory());
        return new GuideNode(dataObject.getNodeDelegate(), guideProject);
    }

    @Override
    public Node findPath(Node root, Object target) {
        // Not implemented yet.
        return null;
    }

    private static final class GuideNode extends FilterNode {

        final GuideProject project;

        public GuideNode(Node node, GuideProject project) {
            super(node,
                    Children.LEAF, // To show all files in folder: new FilterNode.Children(node),
                    new ProxyLookup(
                            Lookups.singleton(project),
                            node.getLookup()
                    )
            );
            this.project = project;
        }

        @Override
        public Image getIcon(int type) {
            return Icons.getGuideImage();
        }
        
        @Override
        public Image getOpenedIcon(int type) {
            return Icons.getOpenedGuideImage();
        }
        
        @Override
        public String getDisplayName() {
            return project.getGuideName();
        }
        
        @Override
        public Action[] getActions(boolean context) {
            Collection<? extends Action> lookupActions = Lookups.forPath("Actions/Guide").lookupAll(Action.class);
            return lookupActions.toArray(new Action[lookupActions.size()]);
        }

        @Override
        public Action getPreferredAction() {
            Collection<? extends Action> lookupActions = Lookups.forPath("Actions/Guide-Preferred").lookupAll(Action.class);
            return !lookupActions.isEmpty() ? lookupActions.iterator().next() : null;
        }
        
        @Override
        public boolean canRename() {
            return false;
        }
    }
}
