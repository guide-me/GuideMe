package org.guideme.guideme.teaseme;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import org.guideme.guideme.model.Page;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 * Adapter for XML-serialization.
 */
class TmPageAdapter {

    @XmlAttribute(name = "id")
    public String Id;

    @XmlAttribute(name = "title")
    public String Title;

    @XmlAnyElement()
    public List<Element> OtherElements;

    @XmlAttribute(name = "set")
    public String Set;

    @XmlAttribute(name = "unset")
    public String UnSet;

    @XmlElement(name = "Image")
    public TmImageAdapter[] Images;

    @XmlElement(name = "Metronome")
    public TmMetronomeAdapter[] Metronomes;

    @XmlElement(name = "Media")
    public TmMediaAdapter[] Media;

    @XmlElement(name = "Button")
    public TmButtonAdapter[] Buttons;

    @XmlElement(name = "Delay")
    public TmDelayAdapter[] Delays;

    public TmPageAdapter() {
    }

    public String getText() {
        if (OtherElements != null) {
            Optional<Element> textElement = OtherElements.stream()
                    .filter((el) -> "Text".equals(el.getTagName()))
                    .findFirst();
            if (textElement.isPresent()) {
                return innerXml(textElement.get());
            }
        }
        return null;
    }

    public static String innerXml(Node node) {
        DOMImplementationLS lsImpl = (DOMImplementationLS) node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
        LSSerializer lsSerializer = lsImpl.createLSSerializer();
        lsSerializer.getDomConfig().setParameter("xml-declaration", false);
        NodeList childNodes = node.getChildNodes();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < childNodes.getLength(); i++) {
            sb.append(lsSerializer.writeToString(childNodes.item(i)));
        }
        return sb.toString();
    }

    public Page toPage(String mediaDirectory) {
        Page page = new Page();
        page.setId(this.Id);
        page.setTitle(this.Title);
        page.setText(this.getText());
        page.setSet(this.Set);
        page.setUnSet(this.UnSet);

        if (this.Images != null && this.Images.length > 0) {
            for (TmImageAdapter image : this.Images) {
                page.addImage(image.toImage(mediaDirectory));
            }
        }
        if (this.Metronomes != null && this.Metronomes.length > 0) {
            for (TmMetronomeAdapter metronome : this.Metronomes) {
                page.addMetronome(metronome.toMetronome());
            }
        }
        if (this.Media != null && this.Media.length > 0) {
            // In TeaseMe there was no distinction between audio and video.
            for (TmMediaAdapter media : this.Media) {
                if (media.isAudio()) {
                    page.addAudio(media.toAudio());
                } else {
                    page.addVideo(media.toVideo());
                }
            }
        }
        if (this.Buttons != null && this.Buttons.length > 0) {
            // The Buttons in TeaseMe are added in reverse order.
            List<TmButtonAdapter> buttonAdapters = Arrays.asList(this.Buttons);
            Collections.reverse(buttonAdapters);
            for (TmButtonAdapter button : buttonAdapters) {
                page.addButton(button.toButton());
            }
        }
        if (this.Delays != null && this.Delays.length > 0) {
            for (TmDelayAdapter delay : this.Delays) {
                page.addDelay(delay.toDelay());
            }
        }
        return page;
    }

}
