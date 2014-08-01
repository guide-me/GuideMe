package org.guideme.guideme.model;

/**
 * Video to be played when a page is shown to the user.
 */
public class Video {

    private String src;
    private String startAt;
    private String stopAt;
    private int loops = 1;

    private String ifSet;
    private String ifNotSet;
    private String set;
    private String unSet;
    private String scriptOnComplete;
    private String target;

    /**
     * Default constructor.
     */
    public Video() {
    }


    /**
     * URL (relative to Guide folder) of the media file to be shown/played on
     * the page.
     *
     * @return
     */
    public String getSrc() {
        return src;
    }

    /**
     * URL (relative to Guide folder) of the media file to be shown/played on
     * the page.
     *
     * @param src
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * Start the video at a specific position (format HH:mm:ss).
     *
     * @return
     */
    public String getStartAt() {
        return startAt;
    }

    /**
     * Start the video at a specific position (format HH:mm:ss).
     *
     * @param startAt
     */
    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    /**
     * Stop the video at a specific position (format HH:mm:ss).
     *
     * @return
     */
    public String getStopAt() {
        return stopAt;
    }

    /**
     * Stop the video at a specific position (format HH:mm:ss).
     *
     * @param stopAt
     */
    public void setStopAt(String stopAt) {
        this.stopAt = stopAt;
    }

    /**
     * The number of times the video must be played.
     *
     * @return
     */
    public int getLoops() {
        return loops;
    }

    /**
     * The number of times the video must be played.
     *
     * @param loops
     */
    public void setLoops(int loops) {
        this.loops = loops;
    }

    /**
     * The ID of the page the player must go to when the video is completed.
     *
     * @return
     */
    public String getTarget() {
        return target;
    }

    /**
     * The ID of the page the player must go to when the video is completed.
     *
     * @param target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * The script to be executed when the video is completed.
     *
     * @return
     */
    public String getScriptOnComplete() {
        return scriptOnComplete;
    }

    /**
     * The script to be executed when the video is completed.
     *
     * @param scriptOnComplete
     */
    public void setScriptOnComplete(String scriptOnComplete) {
        this.scriptOnComplete = scriptOnComplete;
    }

    /**
     * Show/play video only when the following flags are set.
     *
     * @return
     */
    public String getIfSet() {
        return ifSet;
    }

    /**
     * Show/play video when the following flags are set.
     *
     * @param ifSet
     */
    public void setIfSet(String ifSet) {
        this.ifSet = ifSet;
    }

    /**
     * Show/play video only when the following flags are NOT set.
     *
     * @return
     */
    public String getIfNotSet() {
        return ifNotSet;
    }

    /**
     * Show/play video only when the following flags are NOT set.
     *
     * @param ifNotSet
     */
    public void setIfNotSet(String ifNotSet) {
        this.ifNotSet = ifNotSet;
    }

    /**
     * The flags to be set when the video is shown/played.
     *
     * @return
     */
    public String getSet() {
        return set;
    }

    /**
     * The flags to be set when the video is shown/played.
     *
     * @param set
     */
    public void setSet(String set) {
        this.set = set;
    }

    /**
     * The flags to be unset when the video is shown/played.
     *
     * @return
     */
    public String getUnSet() {
        return unSet;
    }

    /**
     * The flags to be unset when the video is shown/played.
     *
     * @param unSet
     */
    public void setUnSet(String unSet) {
        this.unSet = unSet;
    }

}
