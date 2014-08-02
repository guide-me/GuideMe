package org.guideme.guideme.model;

/**
 * Metronome to be shown/played on the page shown to the user.
 */
public class Metronome {

    /**
     * Infinite number of loops.
     */
    public static final int INFINITE_LOOPS = 0;

    /**
     * Default value for resolution (4 ticks per beat).
     */
    public static final int DEFAULT_RESOLUTION = 4;

    /**
     * Default value for rhythm (regular beat).
     */
    public static final String DEFAULT_RHYTHM = "";

    /**
     * Default value for the number of loops (infinite).
     */
    public static final int DEFAULT_LOOPS = INFINITE_LOOPS;

    private String bpm;
    private int resolution = DEFAULT_RESOLUTION;
    private String rhythm = DEFAULT_RHYTHM;
    private int loops = INFINITE_LOOPS;

    private String ifSet;
    private String ifNotSet;

    /**
     * Default constructor.
     */
    public Metronome() {
    }

    /**
     * Beats per minute.
     *
     * @return
     */
    public String getBpm() {
        return bpm;
    }

    /**
     * Beats per minute.
     *
     * @param bpm
     */
    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    /**
     * Ticks per beat.
     *
     * @return
     */
    public int getResolution() {
        return resolution;
    }

    /**
     * Ticks per beat.
     *
     * @param resolution
     */
    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    /**
     * Number of loops to be played.
     *
     * @return
     */
    public int getLoops() {
        return loops;
    }

    /**
     * Number of loops to be played.
     *
     * @param loops
     */
    public void setLoops(int loops) {
        this.loops = loops;
    }

    /**
     * Rhythm is a list of ticks so at 60 bpm, resolution of 4 we get 4 ticks
     * per second a straight 1 beat per second would be 0,4,8,12,16,20,24
     * (giving 7 one second beats). So if we want 20 fast beats (at 120 bpm)
     * then 5 slow we can use
     * 0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,42,46,50,54,58
     *
     * @return
     */
    public String getRhythm() {
        return rhythm;
    }

    /**
     * Rhythm is a list of ticks so at 60 bpm, resolution of 4 we get 4 ticks
     * per second a straight 1 beat per second would be 0,4,8,12,16,20,24
     * (giving 7 one second beats). So if we want 20 fast beats (at 120 bpm)
     * then 5 slow we can use
     * 0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,42,46,50,54,58
     *
     * @param rhythm
     */
    public void setRhythm(String rhythm) {
        this.rhythm = (rhythm != null) ? rhythm : DEFAULT_RHYTHM;
    }

    /**
     * Use metronome only when the following flags are set.
     *
     * @return
     */
    public String getIfSet() {
        return ifSet;
    }

    /**
     * Use metronome only when the following flags are set.
     *
     * @param ifSet
     */
    public void setIfSet(String ifSet) {
        this.ifSet = ifSet;
    }

    /**
     * Use metronome only when the following flags are NOT set.
     *
     * @return
     */
    public String getIfNotSet() {
        return ifNotSet;
    }

    /**
     * Use metronome only when the following flags are NOT set.
     *
     * @param ifNotSet
     */
    public void setIfNotSet(String ifNotSet) {
        this.ifNotSet = ifNotSet;
    }

}
