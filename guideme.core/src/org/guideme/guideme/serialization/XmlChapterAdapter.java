package org.guideme.guideme.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.guideme.guideme.Constants;
import org.guideme.guideme.model.Chapter;

/**
 * Adapter for XML-serialization.  
 */
class XmlChapterAdapter {

    @XmlAttribute(name = "id")
    public String Id;

    @XmlAttribute(name = "title")
    public String Title;

    @XmlElement(name = "Page")
    public XmlPageAdapter[] Pages;
    
    public XmlChapterAdapter() {
    }
    
    public XmlChapterAdapter(Chapter chapter) {
        this.Id = chapter.getId();
        this.Title = chapter.getTitle();
        this.Pages = XmlPageAdapter.fromList(chapter.getPages());
    }

    public Chapter toChapter() {
        Chapter chapter = new Chapter();
        
        chapter.setId(this.Id);
        chapter.setTitle(this.Title);
        
        if (this.Pages != null && this.Pages.length > 0) {
            for (XmlPageAdapter Page : this.Pages) {
                chapter.addPage(Page.toPage());
            }
        }
        
        return chapter;
    }

    static XmlChapterAdapter[] fromList(List<Chapter> chapters) {
        if (chapters == null || chapters.isEmpty()) {
            return null;
        }
        List<XmlChapterAdapter> result = new ArrayList<>();
        for (Chapter chapter : chapters) {
            result.add(new XmlChapterAdapter(chapter));
        }
        return result.toArray(new XmlChapterAdapter[result.size()]);
    }
    
}
