package org.guideme.guideme.player;

import javax.swing.event.ChangeEvent;

public class CurrentPageChangeEvent extends ChangeEvent {

    private final PageDecorator page;
    
    public CurrentPageChangeEvent(Object source, PageDecorator page) {
        super(source);
        this.page = page;
    }
    
    public PageDecorator getCurrentPage() {
        return page;
    }

}
