package org.guideme.guideme.model;

import org.openide.util.NbBundle;

@NbBundle.Messages("CTL_ContinueButton=Continue")
public class Button {

    private String target;
    private String text;

    
    public Button() {
    }

    public Button(String target, String text) {
        this.target = target;
        this.text = text;
    }
    
    
    public static Button Continue(String target) {
        return new Button(target, Bundle.CTL_ContinueButton());
    }
    

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
