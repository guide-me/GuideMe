package org.guideme.guideme.player;

import java.util.EventListener;


public interface CurrentPageChangeListener extends EventListener {
    public void currentPageChanged(CurrentPageChangeEvent ev);
}

