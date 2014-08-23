package org.guideme.guideme.teaseme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.guideme.guideme.filesupport.GuideDataObject;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.serialization.GuideSerializer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "org.guideme.guideme.teaseme.ImportTeaseAction"
)
@ActionRegistration(
        displayName = "#CTL_ImportTeaseAction"
)
@ActionReference(path = "Menu/File/Import", position = 200)
@Messages("CTL_ImportTeaseAction=TeaseMe file...")
public final class ImportTeaseAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            File fileToOpen = new FileChooserBuilder(this.getClass())
                    .setFileFilter(new FileNameExtensionFilter("TeaseMe files", "xml"))
                    .showOpenDialog();

            if (fileToOpen != null) {
                org.openide.filesystems.FileObject teaseFile = FileUtil.toFileObject(fileToOpen);
                Guide guide = new TeaseMeFileReader().readGuide(teaseFile.getInputStream());

                String fileName = teaseFile.getName() + "." + GuideDataObject.FILE_EXTENSION;
                FileObject fo = teaseFile.getParent().getFileObject(fileName);
                if (fo == null) {
                    fo = teaseFile.getParent().createData(fileName);
                }
                try (OutputStream stream = fo.getOutputStream()) {
                    GuideSerializer.getDefault().WriteGuide(guide, stream);
                }

                DataObject file = DataObject.find(teaseFile.getParent().getFileObject(fileName));
                file.getLookup().lookup(OpenCookie.class).open();
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
