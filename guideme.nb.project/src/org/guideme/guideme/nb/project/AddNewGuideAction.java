package org.guideme.guideme.nb.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Library",
        id = "org.guideme.guideme.nb.project.AddNewGuideAction"
)
@ActionRegistration(
        iconBase = "org/guideme/guideme/nb/project/resources/guide_new_icon.png",
        displayName = "#CTL_AddNewGuideAction"
)
@ActionReference(path = "Menu/Library", position = 40, separatorBefore = 39)
@Messages("CTL_AddNewGuideAction=Add New Guide...")
public final class AddNewGuideAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // Not implemented yet.
        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("Not implemented yet...", NotifyDescriptor.INFORMATION_MESSAGE));
    }
}
