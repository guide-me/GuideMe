/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guideme.guideme.nb.viewer;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Project",
        id = "org.guideme.guideme.nb.viewer.PlayGuideAction"
)
@ActionRegistration(
        displayName = "#CTL_PlayGuideAction"
)
@ActionReference(path = "Menu/BuildProject", position = 1, separatorAfter = 2)
@Messages("CTL_PlayGuideAction=Play Guide")
public final class PlayGuideAction extends AbstractAction {

    private final Project context;

    public PlayGuideAction(Project context) {
        super(Bundle.CTL_PlayGuideAction());
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        GuidePlayerTopComponent window = GuidePlayerTopComponent.findInstance();
        window.setDisplayName(context.getProjectDirectory().getName());
        window.open();
        window.requestActive();
    }
}
