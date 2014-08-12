package org.guideme.guideme.player;

import javax.swing.event.ChangeEvent;
import org.guideme.guideme.model.Page;

public class CurrentPageChangeEvent extends ChangeEvent {

    private final Page page;
    
    public CurrentPageChangeEvent(Object source, Page page) {
        super(source);
        this.page = page;
    }
    
    public Page getCurrentPage() {
        return page;
    }

}
