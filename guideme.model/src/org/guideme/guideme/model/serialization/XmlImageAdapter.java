package org.guideme.guideme.model.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.guideme.guideme.model.Image;

/**
 * Adapter for XML-serialization.  
 */
class XmlImageAdapter {
    
    @XmlAttribute(name = "id")
    public String Id;

    
    public XmlImageAdapter() {
    }
    
    public XmlImageAdapter(Image image) {
        this.Id = image.getId();
    }

    
    public Image toImage() {
        Image image = new Image();
        image.setId(this.Id);
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
