package org.guideme.guideme.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "guide")
public class Guide {
    
    @XmlElement(name = "title")
    public String Title;

}
