package org.guideme.guideme.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import org.guideme.guideme.model.Button;

/**
 * Adapter for XML-serialization.  
 */
class XmlButtonAdapter {
    
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
    
    @XmlAttribute(name = "target")
    public String Target;
    
    @XmlAttribute(name = "onClick")
    public String ScriptOnClick;
    
    @XmlValue
    public String Text;
    

    public XmlButtonAdapter() {
    }
    
    public XmlButtonAdapter(Button button) {
        this.Id = button.getId();
        this.IfSet = button.getIfSet();
        this.IfNotSet = button.getIfNotSet();
        this.Set = button.getSet();
        this.UnSet = button.getUnSet();
        this.Target = button.getTarget();
        this.ScriptOnClick = button.getScriptOnClick();
        this.Text = button.getText();
    }

    public Button toButton() {
        Button button = new Button();
        button.setId(this.Id);
        button.setIfSet(this.IfSet);
        button.setIfNotSet(this.IfNotSet);
        button.setSet(this.Set);
        button.setUnSet(this.UnSet);
        button.setScriptOnClick(this.ScriptOnClick);
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
