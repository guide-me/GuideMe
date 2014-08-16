package org.guideme.guideme.editor;

import org.guideme.guideme.model.Button;

public class GuideEditor {

    public static Button createContinueButton(String target) {
        Button button = new Button();
        button.setTarget(target);
        button.setText("Continue");
        return button;
    }
    
    public static Button createYesButton(String target) {
        Button button = new Button();
        button.setTarget(target);
        button.setText("Yes");
        return button;
    }
    
    public static Button createNoButton(String target) {
        Button button = new Button();
        button.setTarget(target);
        button.setText("No");
        return button;
    }

}
