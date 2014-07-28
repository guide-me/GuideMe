package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Guide")
@XmlAccessorType(XmlAccessType.FIELD)
public class Guide {
    
    @XmlElement(name = "Title")
    private String title;

    @XmlElementWrapper(name = "Chapters")
    @XmlElement(name = "Chapter")
    private List<Chapter> chapters = new ArrayList<>();

    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<Chapter> getChapters() {
        return chapters;
    }
    
    public void setPages(List<Chapter> chapters) {
        this.chapters = chapters;
    }

}
