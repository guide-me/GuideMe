package org.guideme.guideme.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import org.guideme.guideme.model.Image;

/**
 * Adapter for XML-serialization.  
 */
class XmlImageAdapter {
    
    @XmlAttribute(name = "id")
    public String Id;

    @XmlAttribute(name = "if-set")
    public String IfSet;

    @XmlAttribute(name = "if-not-set")
    public String IfNotSet;

    @XmlAttribute(name = "set")
    public String Set;

    @XmlAttribute(name = "unset")
    public String UnSet;
    
    public XmlImageAdapter() {
    }
    
    public XmlImageAdapter(Image image) {
        this.Id = image.getId();
        this.IfSet = image.getIfSet();
        this.IfNotSet = image.getIfNotSet();
        this.Set = image.getSet();
        this.UnSet = image.getUnSet();
    }

    
    public Image toImage() {
        Image image = new Image();
        image.setId(this.Id);
        image.setIfSet(this.IfSet);
        image.setIfNotSet(this.IfNotSet);
        image.setSet(this.Set);
        image.setUnSet(this.UnSet);
        return image;
    }

    static XmlImageAdapter[] fromList(List<Image> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }
        List<XmlImageAdapter> result = new ArrayList<>();
        for (Image image : images) {
            result.add(new XmlImageAdapter(image));
        }
        return result.toArray(new XmlImageAdapter[result.size()]);
    }

}
