package org.guideme.guideme.editor;

import org.guideme.guideme.model.Button;
import org.openide.util.NbBundle;

@NbBundle.Messages({
    "CTL_ContinueButton=Continue",
    "CTL_YesButton=Yes",
    "CTL_NoButton=No"})
public class GuideEditor {

    public static Button createContinueButton(String target) {
        Button button = new Button();
        button.setTarget(target);
        button.setText(Bundle.CTL_ContinueButton());
        return button;
    }
    
    public static Button createYesButton(String target) {
        Button button = new Button();
        button.setTarget(target);
        button.setText(Bundle.CTL_YesButton());
        return button;
    }
    
    public static Button createNoButton(String target) {
        Button button = new Button();
        button.setTarget(target);
        button.setText(Bundle.CTL_NoButton());
        return button;
    }

}
