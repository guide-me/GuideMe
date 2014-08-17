// Implementation is taken and adapted from org.netbeans.modules.welcome.Installer.
package org.guideme.guideme.startpage;

import java.util.Set;
import org.openide.modules.ModuleInstall;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.windows.WindowSystemEvent;
import org.openide.windows.WindowSystemListener;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        WindowManager.getDefault().addWindowSystemListener(new WindowSystemListener() {

            @Override
            public void beforeLoad(WindowSystemEvent event) {
            }

            @Override
            public void afterLoad(WindowSystemEvent event) {
            }

            @Override
            public void beforeSave(WindowSystemEvent event) {
                WindowManager.getDefault().removeWindowSystemListener(this);
                StartPageComponent topComp = null;
                boolean isEditorShowing = false;
                Set<TopComponent> tcs = TopComponent.getRegistry().getOpened();
                for (Mode mode : WindowManager.getDefault().getModes()) {
                    TopComponent tc = mode.getSelectedTopComponent();
                    if (tc instanceof StartPageComponent) {
                        topComp = (StartPageComponent) tc;
                    }
                    if (null != tc && WindowManager.getDefault().isEditorTopComponent(tc)) {
                        isEditorShowing = true;
                    }
                }
                if (isEditorShowing) {
                    if (topComp == null) {
                        topComp = StartPageComponent.findComp();
                    }
                    //activate start page at shutdown to avoid editor initialization
                    //before the start page is activated again at startup
                    topComp.open();
                    topComp.requestActive();
                } else if (topComp != null) {
                    topComp.close();
                }
            }

            @Override
            public void afterSave(WindowSystemEvent event) {
            }
        });
    }

}
