package org.guideme.guideme.teaseme;

import javax.xml.bind.annotation.XmlAttribute;
import org.guideme.guideme.model.Delay;

/**
 * Adapter for XML-serialization.  
 */
class TmDelayAdapter {
    
    @XmlAttribute(name = "seconds")
    public int PeriodInSeconds;

    @XmlAttribute(name = "style")
    public String Style;

    @XmlAttribute(name = "target")
    public String Target;
    
    @XmlAttribute(name = "if-set")
    public String IfSet;

    @XmlAttribute(name = "if-not-set")
    public String IfNotSet;

    @XmlAttribute(name = "set")
    public String Set;

    @XmlAttribute(name = "unset")
    public String UnSet;
    
    public TmDelayAdapter() {
    }
    
    public Delay toDelay() {
        Delay delay = new Delay();
        delay.setPeriodInSeconds(this.PeriodInSeconds);
        if (this.Style != null && this.Style.length() > 0) {
            switch (this.Style.toLowerCase()) {
                case "hidden": delay.setStyle(Delay.Style.Hidden); break;
                case "secret": delay.setStyle(Delay.Style.Secret); break;
                default: delay.setStyle(Delay.Style.Normal); break;
            }
        }
        delay.setTarget(this.Target);
        delay.setIfSet(this.IfSet);
        delay.setIfNotSet(this.IfNotSet);
        delay.setSet(this.Set);
        delay.setUnSet(this.UnSet);
        return delay;
    }
}
