package org.guideme.guideme.teaseme;

import javax.xml.bind.annotation.XmlAttribute;
import org.guideme.guideme.model.Audio;
import org.guideme.guideme.model.Video;

/**
 * Adapter for XML-serialization.  
 */
class TmMediaAdapter {
    
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
    
    @XmlAttribute(name = "start-at")
    public String StartAt;
    
    @XmlAttribute(name = "stop-at")
    public String StopAt;
    
    @XmlAttribute(name = "repeat")
    public String Repeat;
    
    public TmMediaAdapter() {
    }
    
    public boolean isAudio() {
        // In TeaseMe there was no distinction between audio and video, so
        // this is just a very naive test to distinct the types.
        return Src.endsWith(".mp3");
    }
    
    public Audio toAudio() {
        Audio audio = new Audio();
        audio.setSrc(this.Src);
        audio.setIfSet(this.IfSet);
        audio.setIfNotSet(this.IfNotSet);
        audio.setSet(this.Set);
        audio.setUnSet(this.UnSet);
        audio.setTarget(this.Target);
        audio.setStartAt(this.StartAt);
        audio.setStopAt(this.StopAt);
        if (this.Repeat != null && this.Repeat.length() > 0) {
            audio.setLoops(Integer.parseInt(this.Repeat));
        }
        return audio;
    }
    
    public Video toVideo() {
        Video video = new Video();
        video.setSrc(this.Src);
        video.setIfSet(this.IfSet);
        video.setIfNotSet(this.IfNotSet);
        video.setSet(this.Set);
        video.setUnSet(this.UnSet);
        video.setTarget(this.Target);
        video.setStartAt(this.StartAt);
        video.setStopAt(this.StopAt);
        if (this.Repeat != null && this.Repeat.length() > 0) {
            video.setLoops(Integer.parseInt(this.Repeat));
        }
        return video;
    }

}
