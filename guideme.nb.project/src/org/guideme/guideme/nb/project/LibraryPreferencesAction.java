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
        id = "org.guideme.guideme.nb.project.LibraryPreferencesAction"
)
@ActionRegistration(
        iconBase = "org/guideme/guideme/nb/project/resources/options.png",
        displayName = "#CTL_LibraryPreferencesAction"
)
@ActionReference(path = "Menu/Library", position = 150, separatorBefore = 100)
@Messages("CTL_LibraryPreferencesAction=Library Preferences")
public final class LibraryPreferencesAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // Not implemented yet.
        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("Not implemented yet...", NotifyDescriptor.INFORMATION_MESSAGE));
    }
}
