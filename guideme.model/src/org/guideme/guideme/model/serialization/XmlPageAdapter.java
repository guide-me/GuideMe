package org.guideme.guideme.model.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.guideme.guideme.model.Page;

/**
 * Adapter for XML-serialization.  
 */
class XmlPageAdapter {
    
    @XmlAttribute(name = "id")
    public String Id;
    
    @XmlElement(name = "Text")
    public String Text;
    
    @XmlElementWrapper(name = "Images")
    @XmlElement(name = "Image")
    public XmlImageAdapter[] Images;
        
    @XmlElementWrapper(name = "Buttons")
    @XmlElement(name = "Button")
    public XmlButtonAdapter[] Buttons;
    

    public XmlPageAdapter() {
    }
    
    public XmlPageAdapter(Page page) {
        this.Id = page.getId();
        this.Text = page.getText();
        this.Images = XmlImageAdapter.fromList(page.getImages());
        this.Buttons = XmlButtonAdapter.fromList(page.getButtons());
    }

    public Page toPage() {
        Page page = new Page();
        page.setId(this.Id);
        page.setText(this.Text);
        
        if (this.Images != null && this.Images.length > 0) {
            for (XmlImageAdapter Image : this.Images) {
                page.addImage(Image.toImage());
            }
        }
        if (this.Buttons != null && this.Buttons.length > 0) {
            for (XmlButtonAdapter Button : this.Buttons) {
                page.addButton(Button.toButton());
            }
        }
        return page;
    }

    static XmlPageAdapter[] fromList(List<Page> pages) {
        if (pages == null || pages.isEmpty()) {
            return null;
        }
        List<XmlPageAdapter> result = new ArrayList<>();
        for (Page page : pages) {
            result.add(new XmlPageAdapter(page));
        }
        return result.toArray(new XmlPageAdapter[result.size()]);
    }

}
