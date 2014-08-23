package org.guideme.guideme.teaseme;

import javax.xml.bind.annotation.XmlAttribute;
import org.guideme.guideme.model.Metronome;

/**
 * Adapter for XML-serialization.  
 */
class TmMetronomeAdapter {
    
    @XmlAttribute(name = "bpm")
    public String Bpm;
    
    @XmlAttribute(name = "if-set")
    public String IfSet;

    @XmlAttribute(name = "if-not-set")
    public String IfNotSet;


    public TmMetronomeAdapter() {
    }
    
    public Metronome toMetronome() {
        Metronome metronome = new Metronome();
        metronome.setBpm(this.Bpm);
        metronome.setIfSet(this.IfSet);
        metronome.setIfNotSet(this.IfNotSet);
        return metronome;
    }

}
