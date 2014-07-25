package org.guideme.guideme.nb.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.nodes.Node;

@NodeFactory.Registration(projectType = "org-guideme-guideme-nb-project", position = 20)
public class GuidesSubprojectNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project project) {
        GuidesSubprojectProvider subprojectProvider = project.getLookup().lookup(GuidesSubprojectProvider.class);
        assert subprojectProvider != null;
        return new GuidesNodeList(subprojectProvider.getSubprojects());
    }

    private class GuidesNodeList implements NodeList<Project> {

        Set<? extends Project> subprojects;

        public GuidesNodeList(Set<? extends Project> subprojects) {
            this.subprojects = subprojects;
        }

        @Override
        public List<Project> keys() {
            List<Project> result = new ArrayList<>();
            result.addAll(subprojects);
            return result;
        }

        @Override
        public Node node(Project node) {
            return new GuideProjectNode(node.getProjectDirectory());
        }

        @Override
        public void addNotify() {
            // Not implemented yet.
        }

        @Override
        public void removeNotify() {
            // Not implemented yet.
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

}
