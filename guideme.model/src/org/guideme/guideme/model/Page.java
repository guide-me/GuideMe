package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

public class Page {

    private String id;
    private String text;
    private final ArrayList<Image> images = new ArrayList<>();
    private final ArrayList<Button> buttons = new ArrayList<>();

    public Page() {
    }

    public Page(String id) {
        this.id = id;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public List<Image> getImages() {
        return images;
    }
    
    public void setImages(List<Image> images) {
        this.images.clear();
        this.images.addAll(images);
    }
    
    public List<Button> getButtons() {
        return buttons;
    }
    
    public void setButtons(List<Button> buttons) {
        this.buttons.clear();
        this.buttons.addAll(buttons);
    }
    
    public Image addImage(String imageId) {
        Image image = new Image(imageId);
        images.add(image);
        return image;
    }
    
    public Image addImage(Image image) {
        images.add(image);
        return image;
    }

    public Button addButton(Button button) {
        buttons.add(button);
        return button;
    }
}
