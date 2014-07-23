package org.guideme.guideme.nb.project;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.guideme.guideme.model.Page;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

@NodeFactory.Registration(projectType = "org-guideme-guideme-nb-project", position = 10)
public class PagesNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project project) {
        return new PagesNodeList((GuideMeProject)project);
    }

    
    private class PagesNodeList implements NodeList<Node> {
        
        GuideMeProject project;

        public PagesNodeList(GuideMeProject project) {
            this.project = project;
        }
        
        @Override
        public List<Node> keys() {
            List<Node> result = new ArrayList<Node>();
            
            for (Page page : project.getGuide().Pages) {
                try {
                    result.add(new PageNode(page));
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            
            return result;
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
        
        
        private class PageNode extends BeanNode {

            public PageNode(Page page) throws IntrospectionException {
                super(page);
                setName(page.Id);
                setDisplayName(page.Id);
                setShortDescription(page.Text);
            }
            
        }
    }
}
