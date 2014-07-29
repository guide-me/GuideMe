package org.guideme.guideme.model.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import org.guideme.guideme.model.Chapter;

/**
 * Adapter for XML-serialization.  
 */
class XmlChapterAdapter {

    @XmlAttribute(name = "id")
    public String Id;

    @XmlElementWrapper(name = "Pages")
    @XmlElement(name = "Page")
    public XmlPageAdapter[] Pages;
    
    public XmlChapterAdapter() {
    }
    
    public XmlChapterAdapter(Chapter chapter) {
        this.Id = chapter.getId();
        this.Pages = XmlPageAdapter.fromList(chapter.getPages());
    }

    public Chapter toChapter() {
        Chapter chapter = new Chapter();
        
        chapter.setId(this.Id);
        
        if (this.Pages != null && this.Pages.length > 0) {
            for (int i = 0; i < this.Pages.length; i++) {
                chapter.addPage(this.Pages[i].toPage());
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
