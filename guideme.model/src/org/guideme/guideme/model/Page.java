package org.guideme.guideme.model;

public class Page {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Page() {
    }

    public Page(String id) {
        this.id = id;
    }
}
