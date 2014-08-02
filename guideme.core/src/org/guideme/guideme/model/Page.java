package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Page.
 */
public class Page {

    private String id;
    private String title;
    private String text;
    private String script;
    private final ArrayList<Image> images = new ArrayList<>();
    private final ArrayList<Audio> audios = new ArrayList<>();
    private final ArrayList<Metronome> metronomes = new ArrayList<>();
    private final ArrayList<Video> videos = new ArrayList<>();
    private final ArrayList<Button> buttons = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Page() {
    }

    /**
     * Constructor setting the ID of this page.
     *
     * @param id
     */
    public Page(String id) {
        this.id = id;
    }

    /**
     * ID of the page (used for scripting purposes).
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * ID of the page (used for scripting purposes).
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Title of the page (used for display).
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Title of the page (used for display).
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Text to be displayed to the user (can contain HTML markup).
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Text to be displayed to the user (can contain HTML markup).
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Script to be executed when the page is shown.
     *
     * @return
     */
    public String getScript() {
        return script;
    }

    /**
     * Script to be executed when the page is shown.
     *
     * @param script
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * List with possible images to be shown. Which one will actually be used is
     * determined by the ifSet/ifNotSet flags etc.
     *
     * @return
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * List with possible images to be shown. Which one will actually be used is
     * determined by the ifSet/ifNotSet flags etc.
     *
     * @param images
     */
    public void setImages(List<Image> images) {
        this.images.clear();
        this.images.addAll(images);
    }

    /**
     * List with possible audio files to be played. Which one will actually be
     * used is determined by the ifSet/ifNotSet flags etc.
     *
     * @return
     */
    public List<Audio> getAudios() {
        return audios;
    }

    /**
     * List with possible audio files to be played. Which one will actually be
     * used is determined by the ifSet/ifNotSet flags etc.
     *
     * @param audios
     */
    public void setAudios(List<Audio> audios) {
        this.audios.clear();
        this.audios.addAll(audios);
    }

    /**
     * List with possible metronomes to be used. Which one will actually be used
     * is determined by the ifSet/ifNotSet flags etc.
     *
     * @return
     */
    public List<Metronome> getMetronomes() {
        return metronomes;
    }

    /**
     * List with possible metronomes to be used. Which one will actually be used
     * is determined by the ifSet/ifNotSet flags etc.
     *
     * @param metronomes
     */
    public void setMetronomes(List<Metronome> metronomes) {
        this.metronomes.clear();
        this.metronomes.addAll(metronomes);
    }

    /**
     * List with possible videos to be shown. Which one will actually be used is
     * determined by the ifSet/ifNotSet flags etc.
     *
     * @return
     */
    public List<Video> getVideos() {
        return videos;
    }

    /**
     * List with possible videos to be shown. Which one will actually be used is
     * determined by the ifSet/ifNotSet flags etc.
     *
     * @param videos
     */
    public void setVideos(List<Video> videos) {
        this.videos.clear();
        this.videos.addAll(videos);
    }

    /**
     * List with possible buttons to be shown. Which one will actually be used
     * is determined by the ifSet/ifNotSet flags etc.
     *
     * @return
     */
    public List<Button> getButtons() {
        return buttons;
    }

    /**
     * List with possible buttons to be shown. Which one will actually be used
     * is determined by the ifSet/ifNotSet flags etc.
     *
     * @param buttons
     */
    public void setButtons(List<Button> buttons) {
        this.buttons.clear();
        this.buttons.addAll(buttons);
    }

    /**
     * Adds a single image to the page at the end.
     *
     * @param image
     * @return
     */
    public Image addImage(Image image) {
        images.add(image);
        return image;
    }

    /**
     * Adds a single audio file to the page at the end.
     *
     * @param audio
     * @return
     */
    public Audio addAudio(Audio audio) {
        audios.add(audio);
        return audio;
    }

    /**
     * Adds a single metronome to the page at the end.
     *
     * @param metronome
     * @return
     */
    public Metronome addMetronome(Metronome metronome) {
        metronomes.add(metronome);
        return metronome;
    }

    /**
     * Adds a single video to the page at the end.
     *
     * @param video
     * @return
     */
    public Video addVideo(Video video) {
        videos.add(video);
        return video;
    }

    /**
     * Adds a single button to the page at the end.
     *
     * @param button
     * @return
     */
    public Button addButton(Button button) {
        buttons.add(button);
        return button;
    }
}
