package org.guideme.guideme.player;

import javax.swing.event.ChangeEvent;

public class CountdownChangeEvent extends ChangeEvent {
    private final int secondsRemaining;

    public CountdownChangeEvent(Object source, int secondsRemaining) {
        super(source);
        this.secondsRemaining = secondsRemaining;
    }

    public int getSecondsRemaining() {
        return secondsRemaining;
    }
}
