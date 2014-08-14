package org.guideme.guideme.filesupport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "org.guideme.guideme.filesupport.OpenGuideAction"
)
@ActionRegistration(
        displayName = "#CTL_OpenGuideAction"
)
@ActionReference(path = "Menu/File", position = 1, separatorAfter = 2)
@Messages("CTL_OpenGuideAction=Open Guide")
public final class OpenGuideAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            File fileToOpen = new FileChooserBuilder(this.getClass())
                    .addFileFilter(new FileNameExtensionFilter("XML-files", "xml"))
                    .showOpenDialog();
            
            if (fileToOpen != null) {
                DataObject file = DataObject.find(FileUtil.toFileObject(fileToOpen));
                file.getLookup().lookup(OpenCookie.class).open();
            }
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
