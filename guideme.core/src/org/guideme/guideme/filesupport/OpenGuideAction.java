package org.guideme.guideme.filesupport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Icon;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.guideme.guideme.resources.Icons;
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
@ActionReference(path = "Menu/File", position = 10)
@Messages("CTL_OpenGuideAction=Open Guide...")
public final class OpenGuideAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            File fileToOpen = new FileChooserBuilder(this.getClass())
                    .setBadgeProvider(new GuideBadgeProvider())
                    .setFileFilter(new FileNameExtensionFilter("GuideMe guides", GuideDataObject.FILE_EXTENSION))
                    .showOpenDialog();

            if (fileToOpen != null) {
                DataObject file = DataObject.find(FileUtil.toFileObject(fileToOpen));
                file.getLookup().lookup(OpenCookie.class).open();
            }
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private static class GuideBadgeProvider implements FileChooserBuilder.BadgeProvider {

        @Override
        public Icon getBadge(File file) {
            String extension = FileUtil.getExtension(file.getName());
            return GuideDataObject.FILE_EXTENSION.equals(extension) ? Icons.getGuideIcon() : null;
        }

        @Override
        public int getXOffset() {
            return 0;
        }

        @Override
        public int getYOffset() {
            return 0;
        }
    }
}
