package org.guideme.guideme.nb.project;

import java.awt.Image;
import org.guideme.guideme.nb.project.resources.Icons;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.FilterNode;
import org.openide.nodes.FilterNode.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

public class LibraryProjectLogicalView implements LogicalViewProvider {

    private final LibraryProject libraryProject;

    public LibraryProjectLogicalView(LibraryProject libraryProject) {
        this.libraryProject = libraryProject;
    }
    
    
    @Override
    public Node createLogicalView() {
        try {
            FileObject libraryDirectory = libraryProject.getProjectDirectory();
            DataFolder projectFolder = DataFolder.findFolder(libraryDirectory);
            Node nodeOfProjectFolder = projectFolder.getNodeDelegate();
            return new LibraryNode(nodeOfProjectFolder, libraryProject);
        } catch (DataObjectNotFoundException donfe) {
            Exceptions.printStackTrace(donfe);
            return new AbstractNode(Children.LEAF);
        }
    }

    @Override
    public Node findPath(Node root, Object target) {
        // Not implemented yet.
        return null;
    }

    
    private final class LibraryNode extends FilterNode {

        private final LibraryProject libraryProject;
        
        public LibraryNode(Node node, LibraryProject libraryProject)
                throws DataObjectNotFoundException {
            
            super(node,
                    NodeFactorySupport.createCompositeChildren(libraryProject,
                        "Projects/org-guideme-guideme-nb-project/Nodes"),
                    new ProxyLookup(
                        new Lookup[] { 
                            Lookups.singleton(libraryProject),
                            node.getLookup()
                        }));
            
            this.libraryProject = libraryProject;
        }

        @Override
        public Image getIcon(int type) {
            return Icons.getLibraryImage();
        }

        @Override
        public Image getOpenedIcon(int type) {
            return getIcon(type);
        }

        @Override
        public String getDisplayName() {
            return libraryProject.getProjectDirectory().getName();
        }
    }
}
