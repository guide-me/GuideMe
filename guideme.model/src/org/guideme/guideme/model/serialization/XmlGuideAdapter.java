package org.guideme.guideme.model.serialization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.guideme.guideme.model.Guide;

/**
 * Adapter for XML-serialization.  
 */
@XmlRootElement(name = "Guide")
@XmlAccessorType(XmlAccessType.NONE)
class XmlGuideAdapter {

    @XmlElement(name = "Title")
    public String Title;
    
    @XmlElementWrapper(name = "Pages")
    @XmlElement(name = "Page")
    public XmlPageAdapter[] Pages;

    @XmlElementWrapper(name = "Chapters")
    @XmlElement(name = "Chapter")
    public XmlChapterAdapter[] Chapters;
    
    
    public XmlGuideAdapter() {
    }

    public XmlGuideAdapter(Guide guide) {
        this.Title = guide.getTitle();
        this.Pages = XmlPageAdapter.fromList(guide.getPages());
        this.Chapters = XmlChapterAdapter.fromList(guide.getChapters());
    }

    public Guide toGuide() {
        Guide guide = new Guide();
        
        guide.setTitle(this.Title);

        if (this.Pages != null && this.Pages.length > 0) {
            for (int i = 0; i < this.Pages.length; i++) {
                guide.addPage(this.Pages[i].toPage());
            }
        }

        if (this.Chapters != null && this.Chapters.length > 0) {
            for (int i = 0; i < this.Chapters.length; i++) {
                guide.addChapter(this.Chapters[i].toChapter());
            }
        }
        
        return guide;
    }
}



