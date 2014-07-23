package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Guide")
public class Guide {
    
    @XmlElement(name = "Title")
    public String Title;

    @XmlElementWrapper(name = "Pages")
    @XmlElement(name = "Page")
    public List<Page> Pages = new ArrayList<Page>();
}
