package org.guideme.guideme.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import org.guideme.guideme.model.Delay;

/**
 * Adapter for XML-serialization.  
 */
class XmlDelayAdapter {
    
    @XmlAttribute(name = "period")
    public String Period;

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
    
    @XmlAttribute(name = "onComplete")
    public String ScriptOnComplete;
    
    
    public XmlDelayAdapter() {
    }
    
    public XmlDelayAdapter(Delay delay) {
        this.Period = delay.getPeriod();
        this.Style = delay.getStyle().toString().toLowerCase();
        this.Target = delay.getTarget();
        this.IfSet = delay.getIfSet();
        this.IfNotSet = delay.getIfNotSet();
        this.Set = delay.getSet();
        this.UnSet = delay.getUnSet();
        this.ScriptOnComplete = delay.getScriptOnComplete();
    }

    
    public Delay toDelay() {
        Delay delay = new Delay();
        delay.setPeriod(this.Period);
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
        delay.setScriptOnComplete(this.ScriptOnComplete);
        return delay;
    }

    static XmlDelayAdapter[] fromList(List<Delay> delays) {
        if (delays == null || delays.isEmpty()) {
            return null;
        }
        List<XmlDelayAdapter> result = new ArrayList<>();
        for (Delay delay : delays) {
            result.add(new XmlDelayAdapter(delay));
        }
        return result.toArray(new XmlDelayAdapter[result.size()]);
    }

}
