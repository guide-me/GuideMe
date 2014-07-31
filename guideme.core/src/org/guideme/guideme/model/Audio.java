package org.guideme.guideme.model;

/**
 * Audio to be played when a page is shown to the user.
 */
public class Audio {

    private String id;
    private String ifSet;
    private String ifNotSet;
    private String set;
    private String unSet;
    private String target;
    private String scriptOnCompleted;
    private String startAt;
    private String stopAt;
    private String loops;


    /**
     * Default constructor.
     */
    public Audio() {
    }

    /**
     * Constructor setting the ID of the audio file.
     *
     * @param id
     */
    public Audio(String id) {
        this.id = id;
    }

    /**
     * Start the playable media at a specific position (format HH:mm:ss).
     *
     * @return
     */
    public String getStartAt() {
        return startAt;
    }

    /**
     * Start the playable media at a specific position (format HH:mm:ss).
     *
     * @param startAt
     */
    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    /**
     * Stop the playable media at a specific position (format HH:mm:ss).
     *
     * @return
     */
    public String getStopAt() {
        return stopAt;
    }

    /**
     * Stop the playable media at a specific position (format HH:mm:ss).
     *
     * @param stopAt
     */
    public void setStopAt(String stopAt) {
        this.stopAt = stopAt;
    }

    /**
     * The number of times the playable media must be played.
     *
     * @return
     */
    public String getLoops() {
        return loops;
    }

    /**
     * The number of times the playable media must be played.
     *
     * @param loops
     */
    public void setLoops(String loops) {
        this.loops = loops;
    }

    /**
     * The ID of the page the player must go to when the playable media is
     * completed.
     *
     * @return
     */
    public String getTarget() {
        return target;
    }

    /**
     * The ID of the page the player must go to when the playable media is
     * completed.
     *
     * @param target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * The script to be executed when the playable media is completed.
     *
     * @return
     */
    public String getScriptOnCompleted() {
        return scriptOnCompleted;
    }

    /**
     * The flags to be executed when the playable media is completed.
     *
     * @param scriptOnCompleted
     */
    public void setScriptOnCompleted(String scriptOnCompleted) {
        this.scriptOnCompleted = scriptOnCompleted;
    }

    /**
     * URL (relative to Guide folder) of the media file to be shown/played on
     * the page.
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * URL (relative to Guide folder) of the media file to be shown/played on
     * the page.
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Show/play media file only when the following flags are set.
     *
     * @return
     */
    public String getIfSet() {
        return ifSet;
    }

    /**
     * Show/play media file when the following flags are set.
     *
     * @param ifSet
     */
    public void setIfSet(String ifSet) {
        this.ifSet = ifSet;
    }

    /**
     * Show/play media file only when the following flags are NOT set.
     *
     * @return
     */
    public String getIfNotSet() {
        return ifNotSet;
    }

    /**
     * Show/play media file only when the following flags are NOT set.
     *
     * @param ifNotSet
     */
    public void setIfNotSet(String ifNotSet) {
        this.ifNotSet = ifNotSet;
    }

    /**
     * The flags to be set when the media file is shown/played.
     *
     * @return
     */
    public String getSet() {
        return set;
    }

    /**
     * The flags to be set when the media file is shown/played.
     *
     * @param set
     */
    public void setSet(String set) {
        this.set = set;
    }

    /**
     * The flags to be unset when the media file is shown/played.
     *
     * @return
     */
    public String getUnSet() {
        return unSet;
    }

    /**
     * The flags to be unset when the media file is shown/played.
     *
     * @param unSet
     */
    public void setUnSet(String unSet) {
        this.unSet = unSet;
    }

}
