package org.guideme.guideme.teaseme;

import javax.xml.bind.annotation.XmlAttribute;
import org.guideme.guideme.model.Image;

/**
 * Adapter for XML-serialization.
 */
class TmImageAdapter {

    @XmlAttribute(name = "id")
    public String Src;

    @XmlAttribute(name = "if-set")
    public String IfSet;

    @XmlAttribute(name = "if-not-set")
    public String IfNotSet;

    @XmlAttribute(name = "set")
    public String Set;

    @XmlAttribute(name = "unset")
    public String UnSet;

    public TmImageAdapter() {
    }

    public Image toImage() {
        Image image = new Image();
        image.setSrc(this.Src);
        image.setIfSet(this.IfSet);
        image.setIfNotSet(this.IfNotSet);
        image.setSet(this.Set);
        image.setUnSet(this.UnSet);
        return image;
    }

}
