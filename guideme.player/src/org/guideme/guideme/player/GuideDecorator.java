package org.guideme.guideme.player;

import java.util.HashMap;
import org.guideme.guideme.model.Chapter;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;

/**
 * Player specific wrapper around a guide to keep the model clean.
 */
public class GuideDecorator {
    private final Guide guide;
    
    private final HashMap<String, PageDecorator> allPages = new HashMap<>();

    public GuideDecorator(Guide guide) {
        this.guide = guide;
        initialize();
    }
    
    public Guide getGuide() {
        return guide;
    }
    
    private void initialize() {
        for (Page page : guide.getPages()) {
            allPages.put(page.getId(), new PageDecorator(page));
        }
        for (Chapter chapter : guide.getChapters()) {
            for (Page page : chapter.getPages()) {
                allPages.put(page.getId(), new PageDecorator(page));
            }
        }
    }

    public PageDecorator findPage(String pageId) {
        return allPages.get(pageId);
    }
}
