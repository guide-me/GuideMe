package org.guideme.guideme.teaseme;

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
@XmlRootElement(name = "Tease")
@XmlAccessorType(XmlAccessType.NONE)
class TmGuideAdapter {

    @XmlElement(name = "Title")
    public String Title;

    @XmlElement(name = "Url")
    public String Url;

    @XmlElement(name = "Author")
    public TmAuthor Author;

    @XmlElement(name = "Description")
    public String Description;

    @XmlElement(name = "Thumbnail")
    public String Thumbnail;

    @XmlElementWrapper(name = "Keywords")
    @XmlElement(name = "Keyword")
    public String[] Keywords;

    @XmlElementWrapper(name = "Pages")
    @XmlElement(name = "Page")
    public TmPageAdapter[] Pages;
    
//
//    @XmlElementWrapper(name = "Chapters")
//    @XmlElement(name = "Chapter")
//    public XmlChapterAdapter[] Chapters;
    
    @XmlElement(name = "MediaDirectory")
    String MediaDirectory;
    
    public TmGuideAdapter() {
    }

    public Guide toGuide() {
        Guide guide = new Guide();

        guide.setTitle(this.Title);
        guide.setOriginalUrl(this.Url);
        guide.setAuthorName(this.Author.Name);
        guide.setAuthorUrl(this.Author.Url);
        guide.setDescription(Description);
        guide.setThumbnail(Thumbnail);
        if (this.Keywords != null && this.Keywords.length > 0) {
            guide.setKeywords(Arrays.asList(this.Keywords));
        }

        if (this.Pages != null && this.Pages.length > 0) {
            for (TmPageAdapter Page : this.Pages) {
                guide.addPage(Page.toPage(this.MediaDirectory));
            }
        }
//
//        if (this.Chapters != null && this.Chapters.length > 0) {
//            for (XmlChapterAdapter Chapter : this.Chapters) {
//                guide.addChapter(Chapter.toChapter());
//            }
//        }
        return guide;
    }

    @XmlAccessorType(XmlAccessType.NONE)
    public static class TmAuthor {

        @XmlElement(name = "Name")
        public String Name;
        @XmlElement(name = "Url")
        public String Url;
    }
}
