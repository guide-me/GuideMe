package org.guideme.guideme.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import org.guideme.guideme.model.Metronome;

/**
 * Adapter for XML-serialization.  
 */
class XmlMetronomeAdapter {
    
    @XmlAttribute(name = "bpm")
    public String Bpm;
    
    @XmlAttribute(name = "loops")
    public String Loops;
    
    @XmlAttribute(name = "resolution")
    public String Resolution;
    
    @XmlAttribute(name = "rhythm")
    public String Rhythm;

    @XmlAttribute(name = "if-set")
    public String IfSet;

    @XmlAttribute(name = "if-not-set")
    public String IfNotSet;


    public XmlMetronomeAdapter() {
    }
    
    public XmlMetronomeAdapter(Metronome metronome) {
        this.Bpm = metronome.getBpm();
        this.Loops = metronome.getLoops() != Metronome.DEFAULT_LOOPS ? String.valueOf(metronome.getLoops()) : null;
        this.Resolution = metronome.getResolution() != Metronome.DEFAULT_RESOLUTION ? String.valueOf(metronome.getResolution()) : null;;
        this.Rhythm = !metronome.getRhythm().equals(Metronome.DEFAULT_RHYTHM) ? metronome.getRhythm() : null;
        this.IfSet = metronome.getIfSet();
        this.IfNotSet = metronome.getIfNotSet();
    }

    
    public Metronome toMetronome() {
        Metronome metronome = new Metronome();
        metronome.setBpm(this.Bpm);
        if (this.Loops != null && this.Loops.length() > 0) {
            metronome.setLoops(Integer.parseInt(this.Loops));
        }
        if (this.Resolution != null && this.Resolution.length() > 0) {
            metronome.setResolution(Integer.parseInt(this.Resolution));
        }
        metronome.setRhythm(this.Rhythm);
        metronome.setIfSet(this.IfSet);
        metronome.setIfNotSet(this.IfNotSet);
        return metronome;
    }

    static XmlMetronomeAdapter[] fromList(List<Metronome> metronomes) {
        if (metronomes == null || metronomes.isEmpty()) {
            return null;
        }
        List<XmlMetronomeAdapter> result = new ArrayList<>();
        for (Metronome metronome : metronomes) {
            result.add(new XmlMetronomeAdapter(metronome));
        }
        return result.toArray(new XmlMetronomeAdapter[result.size()]);
    }

}
