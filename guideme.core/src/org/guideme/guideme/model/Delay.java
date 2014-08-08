package org.guideme.guideme.model;

/**
 * Delay is a wait timer.
 */
public class Delay {

    /**
     * Style of the delay
     */
    public enum Style {

        /**
         * The timer with the wait time is shown to the user.
         */
        Normal,
        /**
         * The timer is shown but the wait time is kept secret.
         */
        Secret,
        /**
         * The timer is completely hidden.
         */
        Hidden
    }

    /**
     * Default delay style (normal: wait time is shown).
     */
    public static final Style DEFAULT_STYLE = Style.Normal;
    
    private String period;
    private int periodInSeconds;

    private Style style = DEFAULT_STYLE;
    private String target;

    private String ifSet;
    private String ifNotSet;
    private String set;
    private String unSet;
    private String scriptOnComplete;

    /**
     * Default constructor.
     */
    public Delay() {
    }

    /**
     * Period of the delay (format HH:mm:ss).
     *
     * @return
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Gets the (initial) total number of seconds of the delay.
     *
     * @return
     */
    public int getPeriodInSeconds() {
        return periodInSeconds;
    }

    /**
     * Period of the delay (format HH:mm:ss).
     *
     * @param period
     */
    public void setPeriod(String period) {
        this.period = period;
        this.periodInSeconds = parsePeriod(period, 0);
    }

    // TODO should this parsing be moved to separate util?
    private int parsePeriod(String period, int defaultValue) {
        int result = defaultValue;
        if (period != null && period.length() > 0) {
            String[] split = period.split(":");
            if (split.length == 3) {
                result = Integer.parseInt(split[2]) + 60 * Integer.parseInt(split[1]) + 60 * 60 * Integer.parseInt(split[0]);
            }
            if (split.length == 2) {
                result = Integer.parseInt(split[1]) + 60 * Integer.parseInt(split[0]);
            }
            if (split.length == 1) {
                result = Integer.parseInt(split[0]);
            }
        }
        return result;
    }

    /**
     * Style of the delay (normal/secret/hidden).
     *
     * @return
     */
    public Style getStyle() {
        return style;
    }

    /**
     * Style of the delay (normal/secret/hidden).
     *
     * @param style
     */
    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * ID of the page to go to when the delay has completed.
     *
     * @return
     */
    public String getTarget() {
        return target;
    }

    /**
     * ID of the page to go to when the delay has completed.
     *
     * @param target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Use delay only when the following flags are set.
     *
     * @return
     */
    public String getIfSet() {
        return ifSet;
    }

    /**
     * Use delay only when the following flags are set.
     *
     * @param ifSet
     */
    public void setIfSet(String ifSet) {
        this.ifSet = ifSet;
    }

    /**
     * Use delay only when the following flags are NOT set.
     *
     * @return
     */
    public String getIfNotSet() {
        return ifNotSet;
    }

    /**
     * Use delay only when the following flags are NOT set.
     *
     * @param ifNotSet
     */
    public void setIfNotSet(String ifNotSet) {
        this.ifNotSet = ifNotSet;
    }

    /**
     * The flags to be set when the delay has completed.
     *
     * @return
     */
    public String getSet() {
        return set;
    }

    /**
     * The flags to be set when the delay has completed.
     *
     * @param set
     */
    public void setSet(String set) {
        this.set = set;
    }

    /**
     * The flags to be unset when the delay has completed.
     *
     * @return
     */
    public String getUnSet() {
        return unSet;
    }

    /**
     * The flags to be unset when the delay has completed.
     *
     * @param unSet
     */
    public void setUnSet(String unSet) {
        this.unSet = unSet;
    }

    /**
     * The script to be executed when the delay has completed.
     *
     * @return
     */
    public String getScriptOnComplete() {
        return scriptOnComplete;
    }

    /**
     * The script to be executed when the delay has completed.
     *
     * @param scriptOnComplete
     */
    public void setScriptOnComplete(String scriptOnComplete) {
        this.scriptOnComplete = scriptOnComplete;
    }

}
