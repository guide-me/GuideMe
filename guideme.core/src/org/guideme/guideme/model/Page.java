package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

public class Page {

    private String id;
    private String title;
    private String text;
    private String script;
    private final ArrayList<Image> images = new ArrayList<>();
    private final ArrayList<Audio> audios = new ArrayList<>();
    private final ArrayList<Video> videos = new ArrayList<>();
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

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public List<Image> getImages() {
        return images;
    }
    
    public void setImages(List<Image> images) {
        this.images.clear();
        this.images.addAll(images);
    }
    
    public List<Audio> getAudios() {
        return audios;
    }
    
    public void setAudios(List<Audio> audios) {
        this.audios.clear();
        this.audios.addAll(audios);
    }
    
    public List<Video> getVideos() {
        return videos;
    }
    
    public void setVideos(List<Video> videos) {
        this.videos.clear();
        this.videos.addAll(videos);
    }
    
    public List<Button> getButtons() {
        return buttons;
    }
    
    public void setButtons(List<Button> buttons) {
        this.buttons.clear();
        this.buttons.addAll(buttons);
    }
        
    public Image addImage(Image image) {
        images.add(image);
        return image;
    }

    public Audio addAudio(Audio audio) {
        audios.add(audio);
        return audio;
    }

    public Video addVideo(Video video) {
        videos.add(video);
        return video;
    }

    public Button addButton(Button button) {
        buttons.add(button);
        return button;
    }
}
