package org.guideme.guideme.serialization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.guideme.guideme.model.Page;

/**
 * Adapter for XML-serialization.  
 */
class XmlPageAdapter {
    
    @XmlAttribute(name = "id")
    public String Id;

    @XmlAttribute(name = "title")
    public String Title;
    
    @XmlElement(name = "Text")
    public String Text;
    
    @XmlElement(name = "Script")
    public String Script;
    
    @XmlElement(name = "Image")
    public XmlImageAdapter[] Images;
        
    @XmlElement(name = "Audio")
    public XmlAudioAdapter[] Audios;
        
    @XmlElement(name = "Video")
    public XmlVideoAdapter[] Videos;
        
    @XmlElement(name = "Button")
    public XmlButtonAdapter[] Buttons;
    

    public XmlPageAdapter() {
    }
    
    public XmlPageAdapter(Page page) {
        this.Id = page.getId();
        this.Title = page.getTitle();
        this.Text = page.getText();
        this.Script = page.getScript();
        this.Images = XmlImageAdapter.fromList(page.getImages());
        this.Audios = XmlAudioAdapter.fromList(page.getAudios());
        this.Videos = XmlVideoAdapter.fromList(page.getVideos());
        this.Buttons = XmlButtonAdapter.fromList(page.getButtons());
    }

    public Page toPage() {
        Page page = new Page();
        page.setId(this.Id);
        page.setTitle(this.Title);
        page.setText(this.Text);
        page.setScript(this.Script);
        
        if (this.Images != null && this.Images.length > 0) {
            for (XmlImageAdapter image : this.Images) {
                page.addImage(image.toImage());
            }
        }
        if (this.Audios != null && this.Audios.length > 0) {
            for (XmlAudioAdapter audio : this.Audios) {
                page.addAudio(audio.toAudio());
            }
        }
        if (this.Videos != null && this.Videos.length > 0) {
            for (XmlVideoAdapter video : this.Videos) {
                page.addVideo(video.toVideo());
            }
        }        
        if (this.Buttons != null && this.Buttons.length > 0) {
            for (XmlButtonAdapter button : this.Buttons) {
                page.addButton(button.toButton());
            }
        }
        return page;
    }

    static XmlPageAdapter[] fromList(List<Page> pages) {
        if (pages == null || pages.isEmpty()) {
            return null;
        }
        List<XmlPageAdapter> result = new ArrayList<>();
        for (Page page : pages) {
            result.add(new XmlPageAdapter(page));
        }
        return result.toArray(new XmlPageAdapter[result.size()]);
    }

}
