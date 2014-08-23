package org.guideme.guideme.teaseme;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import org.guideme.guideme.model.Button;

/**
 * Adapter for XML-serialization.  
 */
class TmButtonAdapter {
    
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
    
    @XmlValue
    public String Text;

    public TmButtonAdapter() {
    }
    
    public Button toButton() {
        Button button = new Button();
        button.setIfSet(this.IfSet);
        button.setIfNotSet(this.IfNotSet);
        button.setSet(this.Set);
        button.setUnSet(this.UnSet);
        button.setTarget(this.Target);
        button.setText(this.Text);
        return button;
    }

}
