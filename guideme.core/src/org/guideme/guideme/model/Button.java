package org.guideme.guideme.model;

/**
 * Button to be shown on a page.
 */
public class Button {

    private String target;
    private String text;

    private String ifSet;
    private String ifNotSet;
    private String set;
    private String unSet;
    private String scriptOnClick;

    /**
     * Default constructor.
     */
    public Button() {
    }

    /**
     * The ID of the page the player must go to when the video is completed.
     * @return 
     */
    public String getTarget() {
        return target;
    }

    /**
     * The ID of the page the player must go to when the video is completed.
     * @param target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * The text to be shown on the button.
     * 
     * @return 
     */
    public String getText() {
        return text;
    }

    /**
     * The text to be shown on the button.
     * 
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Show button only when the following flags are set.
     *
     * @return
     */
    public String getIfSet() {
        return ifSet;
    }

    /**
     * Show button only when the following flags are set.
     *
     * @param ifSet
     */
    public void setIfSet(String ifSet) {
        this.ifSet = ifSet;
    }

    /**
     * Show button only when the following flags are NOT set.
     *
     * @return
     */
    public String getIfNotSet() {
        return ifNotSet;
    }

    /**
     * Show button only when the following flags are NOT set.
     *
     * @param ifNotSet
     */
    public void setIfNotSet(String ifNotSet) {
        this.ifNotSet = ifNotSet;
    }

    /**
     * The flags to be set when the button is clicked.
     *
     * @return
     */
    public String getSet() {
        return set;
    }

    /**
     * The flags to be set when the button is clicked.
     *
     * @param set
     */
    public void setSet(String set) {
        this.set = set;
    }

    /**
     * The flags to be unset when the button is clicked.
     *
     * @return
     */
    public String getUnSet() {
        return unSet;
    }

    /**
     * The flags to be unset when the button is clicked.
     *
     * @param unSet
     */
    public void setUnSet(String unSet) {
        this.unSet = unSet;
    }

    /**
     * The script to be executed when the button is clicked.
     *
     * @return
     */
    public String getScriptOnClick() {
        return scriptOnClick;
    }

    /**
     * The script to be executed when the button is clicked.
     *
     * @param scriptOnClick
     */
    public void setScriptOnClick(String scriptOnClick) {
        this.scriptOnClick = scriptOnClick;
    }

}
