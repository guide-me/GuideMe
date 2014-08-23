package org.guideme.guideme.player;

import java.util.EventListener;

public interface CountdownChangeListener extends EventListener {

    public void countdownChanged(CountdownChangeEvent ev);

}
