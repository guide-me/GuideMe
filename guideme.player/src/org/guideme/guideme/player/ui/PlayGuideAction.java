package org.guideme.guideme.player.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.guideme.guideme.project.GuideProject;
import org.guideme.guideme.resources.Icons;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Guide",
        id = "org.guideme.guideme.player.ui.PlayGuideAction"
)
@ActionRegistration(
        displayName = "#CTL_PlayGuideAction",
        iconBase = "org/guideme/guideme/resources/play.png"
)
@ActionReferences({
    @ActionReference(path = "Menu/Guide", position = 5),
    @ActionReference(path = "Actions/Guide-Preferred", position = 1)
})
@Messages("CTL_PlayGuideAction=Play Guide")
public final class PlayGuideAction extends AbstractAction {

    private final GuideProject guideProject;

    public PlayGuideAction(Project context) {
        super(Bundle.CTL_PlayGuideAction(), Icons.getPlayGuideIcon());
        this.guideProject = (GuideProject)context;
    }
    
    @Override
    public void actionPerformed(ActionEvent ev) {
        GuidePlayerTopComponent window = GuidePlayerTopComponent.findInstance();
        window.loadGuide(guideProject);
        window.open();
        window.requestActive();
    }
}
