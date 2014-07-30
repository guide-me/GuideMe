package org.guideme.guideme.model.serialization;

import java.util.Arrays;
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
    
    @XmlElement(name = "Description")
    public String Description;
    
    @XmlElement(name = "Thumbnail")
    public String Thumbnail;
    
    @XmlElementWrapper(name = "Keywords")
    @XmlElement(name = "Keyword")
    public String[] Keywords;
    
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
        this.Description = guide.getDescription();
        this.Thumbnail = guide.getThumbnail();
        this.Keywords = guide.getKeywords().isEmpty() ? null : guide.getKeywords().toArray(new String[guide.getKeywords().size()]);

        this.Pages = XmlPageAdapter.fromList(guide.getPages());
        this.Chapters = XmlChapterAdapter.fromList(guide.getChapters());
    }

    public Guide toGuide() {
        Guide guide = new Guide();
        
        guide.setTitle(this.Title);
        guide.setOriginalUrl(this.OriginalUrl);
        guide.setAuthorName(AuthorName);
        guide.setAuthorUrl(AuthorUrl);
        guide.setDescription(Description);
        guide.setThumbnail(Thumbnail);
        if (this.Keywords != null && this.Keywords.length > 0) {
            guide.setKeywords(Arrays.asList(this.Keywords));
        }

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



