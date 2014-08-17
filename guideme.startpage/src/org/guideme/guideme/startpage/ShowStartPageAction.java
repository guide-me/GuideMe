package org.guideme.guideme.startpage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

@ActionID(id = "org.guideme.guideme.startpage.ShowStartPageAction", category = "Help")
@ActionRegistration(displayName = "#LBL_Action", iconBase = "org/guideme/guideme/startpage/resources/startpage.gif", iconInMenu = false)
@ActionReference(path = "Menu/Help", name = "org-guideme-guideme-startpage-ShowStartPageAction", position = 1400)
@NbBundle.Messages("LBL_Action=Start &Page")
public class ShowStartPageAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        StartPageComponent topComp = null;
        Set<TopComponent> tcs = TopComponent.getRegistry().getOpened();
        for (TopComponent tc : tcs) {
            if (tc instanceof StartPageComponent) {
                topComp = (StartPageComponent) tc;
                break;
            }
        }
        if (topComp == null) {
            topComp = StartPageComponent.findComp();
        }

        topComp.open();
        topComp.requestActive();
    }

}
