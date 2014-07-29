/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.guideme.guideme.milovana;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.guideme.guideme.model.Guide;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Guide",
        id = "org.guideme.guideme.milovana.TestHtmlDownload"
)
@ActionRegistration(
        displayName = "#CTL_TestHtmlDownload"
)
@ActionReference(path = "Menu/Guide", position = -95, separatorAfter = -45)
@Messages("CTL_TestHtmlDownload=TestHtmlDownload")
public final class TestHtmlDownload implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Guide guide = new MilovanaHtmlTeaseConverter().createGuide("100");
    }
    
}
