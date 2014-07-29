package org.guideme.guideme.model.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.guideme.guideme.model.Page;

/**
 * Adapter for XML-serialization.  
 */
class XmlPageAdapter {
    
    @XmlAttribute(name = "id")
    public String Id;
    
    @XmlElement(name = "Text")
    public String Text;
    

    public XmlPageAdapter() {
    }
    
    public XmlPageAdapter(Page page) {
        this.Id = page.getId();
        this.Text = page.getText();
    }

    public Page toPage() {
        Page page = new Page();
        page.setId(this.Id);
        page.setText(this.Text);
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
