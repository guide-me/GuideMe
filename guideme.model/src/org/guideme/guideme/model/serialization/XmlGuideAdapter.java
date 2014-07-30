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
    
    @XmlElement(name = "OriginalUrl")
    public String OriginalUrl;
    
    @XmlElement(name = "AuthorName")
    public String AuthorName;
    
    @XmlElement(name = "AuthorUrl")
    public String AuthorUrl;
    
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
        this.OriginalUrl = guide.getOriginalUrl();
        this.AuthorName = guide.getAuthorName();
        this.AuthorUrl = guide.getAuthorUrl();
        
        this.Pages = XmlPageAdapter.fromList(guide.getPages());
        this.Chapters = XmlChapterAdapter.fromList(guide.getChapters());
    }

    public Guide toGuide() {
        Guide guide = new Guide();
        
        guide.setTitle(this.Title);
        guide.setOriginalUrl(this.OriginalUrl);
        guide.setAuthorName(AuthorName);
        guide.setAuthorUrl(AuthorUrl);

        if (this.Pages != null && this.Pages.length > 0) {
            for (XmlPageAdapter Page : this.Pages) {
                guide.addPage(Page.toPage());
            }
        }

        if (this.Chapters != null && this.Chapters.length > 0) {
            for (XmlChapterAdapter Chapter : this.Chapters) {
                guide.addChapter(Chapter.toChapter());
            }
        }
        
        return guide;
    }
}



