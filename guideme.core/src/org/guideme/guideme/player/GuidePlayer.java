package org.guideme.guideme.player;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.guideme.guideme.Constants;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Image;
import org.guideme.guideme.model.Page;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public class GuidePlayer {
    
    private final Set<CurrentPageChangeListener> currentPagelisteners = new HashSet<>();
    private final Guide guide;
    private final FileObject guideDirectory;
    
    private Page currentPage;

    public GuidePlayer(Guide guide, FileObject guideDirectory) {
        this.guide = guide;
        this.guideDirectory = guideDirectory;
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
        CurrentPageChangeEvent ev = createCurrentPageChangeEvent();
        for (CurrentPageChangeListener l : ls) {
            l.currentPageChanged(ev);
        }
    }
    
    CurrentPageChangeEvent createCurrentPageChangeEvent() {
        // TODO substitute variables...
        String text = currentPage.getText();
        
        // TODO calculate using flags...
        File imageFile = null;
        Image gmImage = currentPage.getImages().iterator().hasNext() ? currentPage.getImages().iterator().next() : null;
        if (gmImage != null) {
            // TODO getSrc() might return image name with wildcards. 
            imageFile = FileUtil.toFile(guideDirectory.getFileObject(gmImage.getSrc()));
        }
        
        // TODO calculate using flags...
        List<Button> buttons = currentPage.getButtons();
        
        return new CurrentPageChangeEvent(this, text, imageFile, buttons);
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



