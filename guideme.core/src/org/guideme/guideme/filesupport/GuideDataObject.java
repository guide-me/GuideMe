package org.guideme.guideme.filesupport;

import java.io.IOException;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.serialization.GuideSerializer;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@Messages({
    "LBL_GuideMe_LOADER=Files of GuideMe"
})
@MIMEResolver.NamespaceRegistration(
        displayName = "#LBL_GuideMe_LOADER",
        mimeType = "application/guideme+xml",
        elementNS = {"org.guideme.guideme"}
)
@DataObject.Registration(
        mimeType = "application/guideme+xml",
        iconBase = "org/guideme/guideme/resources/guide.png",
        displayName = "#LBL_GuideMe_LOADER",
        position = 300
)
@ActionReferences({
    @ActionReference(
            path = "Loaders/application/guideme+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
            position = 100,
            separatorAfter = 200
    ),
    @ActionReference(
            path = "Loaders/application/guideme+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
            position = 300
    ),
    @ActionReference(
            path = "Loaders/application/guideme+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
            position = 400,
            separatorAfter = 500
    ),
    @ActionReference(
            path = "Loaders/application/guideme+xml/Actions",
            id = @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
            position = 600
    ),
    @ActionReference(
            path = "Loaders/application/guideme+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
            position = 700,
            separatorAfter = 800
    ),
    @ActionReference(
            path = "Loaders/application/guideme+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
            position = 900,
            separatorAfter = 1000
    ),
    @ActionReference(
            path = "Loaders/application/guideme+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
            position = 1100,
            separatorAfter = 1200
    ),
    @ActionReference(
            path = "Loaders/application/guideme+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
            position = 1300
    ),
    @ActionReference(
            path = "Loaders/application/guideme+xml/Actions",
            id = @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
            position = 1400
    )
})
public class GuideDataObject extends MultiDataObject {

    private Guide guide;

    public GuideDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("application/guideme+xml", true);

        try {
            guide = GuideSerializer.getDefault().ReadGuide(pf.getInputStream());
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public Guide getGuide() {
        return guide;
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

//    @MultiViewElement.Registration(
//            displayName = "#LBL_GuideMe_EDITOR",
//            iconBase = "org/guideme/guideme/resources/guide.png",
//            mimeType = "application/guideme+xml",
//            persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
//            preferredID = "GuideMe",
//            position = 2000
//    )
//    @Messages("LBL_GuideMe_EDITOR=Source")
//    public static MultiViewEditorElement createEditor(Lookup lkp) {
//        return new MultiViewEditorElement(lkp);
//    }

}
