package org.guideme.guideme.nb.viewer;

import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Constants;
import org.guideme.guideme.nb.project.GuideProject;
import org.guideme.guideme.nb.viewer.decorators.GuideDecorator;
import org.guideme.guideme.nb.viewer.decorators.PageDecorator;

public class GuidePlayer {
    
    private final GuideProject guideProject;
    private final GuideDecorator guideDecorator;
    
    private PageDecorator currentPage;

    public GuidePlayer(GuideProject guideProject) {
        this.guideProject = guideProject;
        this.guideDecorator = new GuideDecorator(guideProject.getGuide());
    }
    
    public String getTitle() {
        return guideDecorator.getGuide().getTitle();
    }
    
    public PageDecorator getCurrentPage() {
        return currentPage;
    }
    
    
    public void start() {
        currentPage = guideDecorator.findPage(Constants.START_PAGE_ID);
        // TODO raise event currentPageChanged..
    }
    
    public void buttonPressed(Button button) {
        currentPage = guideDecorator.findPage(button.getTarget());
        // TODO raise event currentPageChanged..
    }
}



