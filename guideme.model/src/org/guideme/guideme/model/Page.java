package org.guideme.guideme.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Page")
public class Page {

    @XmlAttribute(name = "id")
    public String Id;
    
    @XmlElement(name = "Text")
    public String Text;
}
