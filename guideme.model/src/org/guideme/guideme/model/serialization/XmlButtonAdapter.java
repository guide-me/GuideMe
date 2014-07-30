package org.guideme.guideme.model.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.guideme.guideme.model.Button;

/**
 * Adapter for XML-serialization.  
 */
class XmlButtonAdapter {
    
    @XmlAttribute(name = "target")
    public String Target;
    
    @XmlElement(name = "Text")
    public String Text;
    

    public XmlButtonAdapter() {
    }
    
    public XmlButtonAdapter(Button button) {
        this.Target = button.getTarget();
        this.Text = button.getText();
    }

    public Button toButton() {
        Button button = new Button();
        button.setTarget(this.Target);
        button.setText(this.Text);
        return button;
    }

    static XmlButtonAdapter[] fromList(List<Button> buttons) {
        if (buttons == null || buttons.isEmpty()) {
            return null;
        }
        List<XmlButtonAdapter> result = new ArrayList<>();
        for (Button button : buttons) {
            result.add(new XmlButtonAdapter(button));
        }
        return result.toArray(new XmlButtonAdapter[result.size()]);
    }

}
