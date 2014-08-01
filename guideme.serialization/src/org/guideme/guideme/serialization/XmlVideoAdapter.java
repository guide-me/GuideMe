package org.guideme.guideme.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import org.guideme.guideme.model.Video;

/**
 * Adapter for XML-serialization.  
 */
class XmlVideoAdapter {
    
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
    
    @XmlAttribute(name = "onComplete")
    public String ScriptOnComplete;
    
    @XmlAttribute(name = "start-at")
    public String StartAt;
    
    @XmlAttribute(name = "stop-at")
    public String StopAt;
    
    @XmlAttribute(name = "loops")
    public String Loops;
    
    public XmlVideoAdapter() {
    }
    
    public XmlVideoAdapter(Video video) {
        this.Id = video.getId();
        this.IfSet = video.getIfSet();
        this.IfNotSet = video.getIfNotSet();
        this.Set = video.getSet();
        this.UnSet = video.getUnSet();
        this.Target = video.getTarget();
        this.ScriptOnComplete = video.getScriptOnComplete();
        this.StartAt = video.getStartAt();
        this.StopAt = video.getStopAt();
        this.Loops = video.getLoops();
    }

    
    public Video toVideo() {
        Video video = new Video();
        video.setId(this.Id);
        video.setIfSet(this.IfSet);
        video.setIfNotSet(this.IfNotSet);
        video.setSet(this.Set);
        video.setUnSet(this.UnSet);
        video.setTarget(this.Target);
        video.setScriptOnComplete(this.ScriptOnComplete);
        video.setStartAt(this.StartAt);
        video.setStopAt(this.StopAt);
        video.setLoops(this.Loops);
        return video;
    }

    static XmlVideoAdapter[] fromList(List<Video> videos) {
        if (videos == null || videos.isEmpty()) {
            return null;
        }
        List<XmlVideoAdapter> result = new ArrayList<>();
        for (Video video : videos) {
            result.add(new XmlVideoAdapter(video));
        }
        return result.toArray(new XmlVideoAdapter[result.size()]);
    }

}
