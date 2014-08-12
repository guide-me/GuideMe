package org.guideme.guideme.player;

import java.util.HashSet;
import java.util.Set;
import org.guideme.guideme.Constants;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;

public class GuidePlayer {
    
    private final Set<CurrentPageChangeListener> currentPagelisteners = new HashSet<>();
    private final Guide guide;
    
    private Page currentPage;

    public GuidePlayer(Guide guide) {
        this.guide = guide;
    }
    
    public final void addCurrentPageChangeListener(CurrentPageChangeListener l) {
        synchronized (currentPagelisteners) {
            currentPagelisteners.add(l);
        }
    }

    public final void removeCurrentPageChangeListener(CurrentPageChangeListener l) {
        synchronized (currentPagelisteners) {
            currentPagelisteners.remove(l);
        }
    }

    protected void fireCurrentPageChangeEvent() {
        Set<CurrentPageChangeListener> ls;
        synchronized (currentPagelisteners) {
            ls = new HashSet(currentPagelisteners);
        }
        CurrentPageChangeEvent ev = new CurrentPageChangeEvent(this, currentPage);
        for (CurrentPageChangeListener l : ls) {
            l.currentPageChanged(ev);
        }
    }
    
    public String getTitle() {
        return guide.getTitle();
    }
    
    public Page getCurrentPage() {
        return currentPage;
    }
    
    public void start() {
        currentPage = guide.findPage(Constants.START_PAGE_ID);
        fireCurrentPageChangeEvent();
    }
    
    public void buttonPressed(Button button) {
        currentPage = guide.findPage(button.getTarget());
        fireCurrentPageChangeEvent();
    }
}



