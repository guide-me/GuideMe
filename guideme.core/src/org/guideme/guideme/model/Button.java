package org.guideme.guideme.model;

public class Button {

    private String target;
    private String text;

    
    public Button() {
    }

    public Button(String target, String text) {
        this.target = target;
        this.text = text;
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
