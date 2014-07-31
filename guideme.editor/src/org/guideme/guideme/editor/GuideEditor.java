package org.guideme.guideme.editor;

import org.guideme.guideme.model.Button;
import org.openide.util.NbBundle;

@NbBundle.Messages("CTL_ContinueButton=Continue")
public class GuideEditor {

    public static Button createContinueButton(String target) {
        return new Button(target, Bundle.CTL_ContinueButton());
    }
}
