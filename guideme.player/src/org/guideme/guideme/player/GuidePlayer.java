package org.guideme.guideme.player;

import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.Constants;
import org.guideme.guideme.project.GuideProject;

public class GuidePlayer {
    
    private final Set<CurrentPageChangeListener> currentPagelisteners = new HashSet<>();
    private final GuideProject guideProject;
    private final GuideDecorator guideDecorator;
    
    private PageDecorator currentPage;

    public GuidePlayer(GuideProject guideProject) {
        this.guideProject = guideProject;
        this.guideDecorator = new GuideDecorator(guideProject.getGuide());
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
        return guideDecorator.getGuide().getTitle();
    }
    
    public PageDecorator getCurrentPage() {
        return currentPage;
    }
    
    public void start() {
        currentPage = guideDecorator.findPage(Constants.START_PAGE_ID);
        fireCurrentPageChangeEvent();
    }
    
    public void buttonPressed(Button button) {
        currentPage = guideDecorator.findPage(button.getTarget());
        fireCurrentPageChangeEvent();
    }
}



