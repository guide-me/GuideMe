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
        id = "org.guideme.guideme.nb.project.OpenLibraryAction"
)
@ActionRegistration(
        iconBase = "org/guideme/guideme/nb/project/resources/library_icon.png",
        displayName = "#CTL_OpenLibraryAction"
)
@ActionReference(path = "Menu/Library", position = 10)
@Messages("CTL_OpenLibraryAction=Open Library...")
public final class OpenLibraryAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // Not implemented yet.
        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("Not implemented yet...", NotifyDescriptor.INFORMATION_MESSAGE));
    }
}
