package org.guideme.guideme.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import org.guideme.guideme.model.Audio;

/**
 * Adapter for XML-serialization.  
 */
class XmlAudioAdapter {
    
    @XmlAttribute(name = "src")
    public String Src;

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
    
    @XmlAttribute(name = "onComplete")
    public String ScriptOnComplete;
    
    @XmlAttribute(name = "start-at")
    public String StartAt;
    
    @XmlAttribute(name = "stop-at")
    public String StopAt;
    
    @XmlAttribute(name = "loops")
    public String Loops;
    
    public XmlAudioAdapter() {
    }
    
    public XmlAudioAdapter(Audio audio) {
        this.Src = audio.getSrc();
        this.IfSet = audio.getIfSet();
        this.IfNotSet = audio.getIfNotSet();
        this.Set = audio.getSet();
        this.UnSet = audio.getUnSet();
        this.Target = audio.getTarget();
        this.ScriptOnComplete = audio.getScriptOnComplete();
        this.StartAt = audio.getStartAt();
        this.StopAt = audio.getStopAt();
        this.Loops = audio.getLoops() != Audio.DEFAULT_LOOPS ? String.valueOf(audio.getLoops()) : null;
    }

    
    public Audio toAudio() {
        Audio audio = new Audio();
        audio.setSrc(this.Src);
        audio.setIfSet(this.IfSet);
        audio.setIfNotSet(this.IfNotSet);
        audio.setSet(this.Set);
        audio.setUnSet(this.UnSet);
        audio.setTarget(this.Target);
        audio.setScriptOnComplete(this.ScriptOnComplete);
        audio.setStartAt(this.StartAt);
        audio.setStopAt(this.StopAt);
        if (this.Loops != null && this.Loops.length() > 0) {
            audio.setLoops(Integer.parseInt(this.Loops));
        }
        return audio;
    }

    static XmlAudioAdapter[] fromList(List<Audio> audios) {
        if (audios == null || audios.isEmpty()) {
            return null;
        }
        List<XmlAudioAdapter> result = new ArrayList<>();
        for (Audio audio : audios) {
            result.add(new XmlAudioAdapter(audio));
        }
        return result.toArray(new XmlAudioAdapter[result.size()]);
    }

}
