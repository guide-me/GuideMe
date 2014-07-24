package org.guideme.guideme.nb.project;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.guideme.guideme.nb.project.resources.Icons;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.ProxyLookup;

@NodeFactory.Registration(projectType = "org-guideme-guideme-nb-project", position = 10)
public class GuidesNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project project) {
        LibraryProject libraryProject = project.getLookup().lookup(LibraryProject.class);
        assert libraryProject != null;
        return new GuidesNodeList(libraryProject);
    }

    private class GuidesNodeList implements NodeList<Node> {

        LibraryProject libraryProject;

        public GuidesNodeList(LibraryProject libraryProject) {
            this.libraryProject = libraryProject;
        }

        @Override
        public List<Node> keys() {
            List<Node> result = new ArrayList<>();
            for (FileObject child : libraryProject.getProjectDirectory().getChildren()) {
                try {
                    if (isGuide(child)) {
                        Node node = DataObject.find(child).getNodeDelegate();
                        result.add(new GuideNode(node, child));
                    }
                } catch (DataObjectNotFoundException donfe) {
                    Exceptions.printStackTrace(donfe);
                }
            }
            return result;
        }
        
                
        private boolean isGuide(FileObject fileObject) {
            return fileObject.isFolder() && fileObject.getFileObject(Constants.GUIDE_FILE) != null;
        }

        @Override
        public void addChangeListener(ChangeListener l) {
            // Not implemented yet.
        }

        @Override
        public void removeChangeListener(ChangeListener l) {
            // Not implemented yet.
        }

        @Override
        public Node node(Node key) {
            return new FilterNode(key);
        }

        @Override
        public void addNotify() {
            // Not implemented yet.
        }

        @Override
        public void removeNotify() {
            // Not implemented yet.
        }
        

        private class GuideNode extends FilterNode {

            private FileObject guideFolder;

            public GuideNode(Node node, FileObject guideFolder)
                    throws DataObjectNotFoundException {

                super(node, 
                        Children.LEAF,
                        new ProxyLookup(
                                new Lookup[]{
                                    node.getLookup()
                                }));

                this.guideFolder = guideFolder;
            }

            @Override
            public Image getIcon(int type) {
                return Icons.getGuideImage();
            }

            @Override
            public Image getOpenedIcon(int type) {
                return Icons.getGuideOpenImage();
            }

            @Override
            public String getDisplayName() {
                // TODO extract guide title.
                return guideFolder.getName();
            }

        }
    }
}
