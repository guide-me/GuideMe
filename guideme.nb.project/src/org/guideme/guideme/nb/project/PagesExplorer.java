package org.guideme.guideme.nb.project;

import java.awt.BorderLayout;
import java.awt.Image;
import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.nb.project.resources.Icons;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.loaders.DataFolder;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

@TopComponent.Description(
        preferredID = "PagesExplorer",
        iconBase="org/guideme/guideme/nb/project/resources/book_open.png", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "org.guideme.guideme.nb.project.PagesExplorer")
@ActionReference(path = "Menu/Window", position = 15)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PagesExplorerAction",
        preferredID = "PagesExplorer"
)
@Messages({
    "CTL_PagesExplorerAction=Pages Explorer",
    "CTL_PagesExplorer=Pages Explorer"
 })
public final class PagesExplorer extends TopComponent implements ExplorerManager.Provider, LookupListener {

    private final ExplorerManager explorerManager = new ExplorerManager();
    private Lookup.Result result = null;
    
    public PagesExplorer() {
        setName(Bundle.CTL_PagesExplorer());
        setDisplayName(Bundle.CTL_PagesExplorer());
        
        setLayout(new BorderLayout());
        BeanTreeView treeView = new BeanTreeView();
        treeView.setRootVisible(false);
        add(treeView, BorderLayout.CENTER);
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    @Override
    public void componentOpened() {
      Lookup.Template tpl = new Lookup.Template(GuideProject.class);
      result = Utilities.actionsGlobalContext().lookup(tpl);
      result.addLookupListener (this);  
    }
    @Override
    public void componentClosed() {
       result.removeLookupListener(this);
       result = null; 
    }
    
    @Override
    public void resultChanged(LookupEvent lookupEvent) {
        Lookup.Result r = (Lookup.Result) lookupEvent.getSource();
        Collection c = r.allInstances();
        if (!c.isEmpty()) {
            GuideProject guideProject = (GuideProject)c.iterator().next();
            loadTree(guideProject.getGuide());
        }
    }
    
    private void loadTree(Guide guide) {
        
        Children pageNodes = Children.create(new PageBeanNodeChildFactory(guide.Pages), true);
        Node guideNode = new AbstractNode(pageNodes);

        explorerManager.setRootContext(guideNode);
    }
    
    
    
    private class PageBeanNodeChildFactory extends ChildFactory<Page> {

        private final List<Page> pages;

        public PageBeanNodeChildFactory(List<Page> pages) {
            this.pages = pages;
        }
        
        
        @Override
        protected boolean createKeys(List<Page> toPopulate) {
            toPopulate.addAll(pages);
            return true;
        }
        
        @Override
        protected Node createNodeForKey(Page page) {
            PageBeanNode result = null;
            try {
                result = new PageBeanNode(page);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
            return result;
        }
        
    }
    
    private class PageBeanNode extends BeanNode {

        public PageBeanNode(Page bean) throws IntrospectionException {
            super(bean);
            setName(bean.Id);
            setDisplayName(bean.Id);
        }
        
    }
}
