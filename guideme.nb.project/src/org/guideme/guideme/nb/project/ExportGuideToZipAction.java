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
        category = "Guide",
        id = "org.guideme.guideme.nb.project.ExportGuideToZipAction"
)
@ActionRegistration(
        iconBase = "org/guideme/guideme/nb/project/resources/zip.gif",
        displayName = "#CTL_ExportGuideToZipAction"
)
@ActionReference(path = "Menu/Guide", position = 50)
@Messages("CTL_ExportGuideToZipAction=Export To ZIP...")
public final class ExportGuideToZipAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // Not implemented yet.
        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("Not implemented yet...", NotifyDescriptor.INFORMATION_MESSAGE));
    }
}
