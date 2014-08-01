package org.guideme.guideme.editor;

import org.guideme.guideme.model.Button;
import org.openide.util.NbBundle;

@NbBundle.Messages("CTL_ContinueButton=Continue")
public class GuideEditor {

    public static Button createContinueButton(String target) {
        Button button = new Button();
        button.setTarget(target);
        button.setText(Bundle.CTL_ContinueButton());
        return button;
    }
}
