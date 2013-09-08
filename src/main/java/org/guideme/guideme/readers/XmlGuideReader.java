package org.guideme.guideme.readers;

import java.io.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guideme.guideme.model.Audio;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Chapter;
import org.guideme.guideme.model.Delay;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Image;
import org.guideme.guideme.model.Metronome;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.model.Video;
import org.guideme.guideme.settings.ComonFunctions;

public class XmlGuideReader {
  private static Logger logger = LogManager.getLogger();

	
	public enum TagName
	{
		Title, Author, MediaDirectory, Settings, Page, Metronome, Image, Audio, Video, Delay, Button, Text, NOVALUE;

	    public static TagName toTag(String str)
	    {
	        try {
	            return valueOf(str);
	        } 
	        catch (Exception ex) {
	            return NOVALUE;
	        }
	    }   
	}	
	
	public static String loadXML(String xmlFileName, Guide guide) {
		String strTmpTitle = "";
		String strTmpAuthor = "";
		String strPage = "start";
		String strFlags;
		String strPreXMLPath;
		String pageName; 
		String ifSet; 
		String ifNotSet; 
		String Set;
		String UnSet;
		String strTag;
		Page page = null;
		
		try {
			strPreXMLPath = xmlFileName;
			int intPos = xmlFileName.lastIndexOf("\\");
			int intPos2 = xmlFileName.lastIndexOf(".xml");
			String PresName = xmlFileName.substring(intPos + 1, intPos2);
			guide.reset(PresName);
			
		    XMLInputFactory factory = XMLInputFactory.newInstance();
		    XMLStreamReader reader =
	        factory.createXMLStreamReader(
	        		new FileReader(strPreXMLPath));
	         
	         while (reader.hasNext()) {
	        	 int eventType = reader.next(); 
	        	 switch (eventType) {
	        	 case XMLStreamConstants.START_DOCUMENT:
	        		 logger.trace("loadXML Start document " + strPreXMLPath);
	        		 break;
	        	 case XMLStreamConstants.END_DOCUMENT:
	        		 logger.trace("loadXML End document");
	        		 break;
	        	 case XMLStreamConstants.START_ELEMENT:
	        		 logger.trace("loadXML Start tag " + reader.getName().getLocalPart());
	        		 strTag = reader.getName().getLocalPart();

	        		 switch (TagName.toTag(strTag)) {
	        		 case Title:
	        			 try {
	        				 logger.trace("Title Tag");
	        				 reader.next();
	        				 strTmpTitle = reader.getText();
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Title Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Audio:
	        			 try {
	        				 logger.trace("Audio Tag");
	        				 String strId;
	        				 String strStartAt;
	        				 String strStopAt;
	        				 String strTarget;
	        				 strTarget = reader.getAttributeValue(null, "target");
	        				 if (strTarget == null) strTarget = "";
	        				 strStartAt = reader.getAttributeValue(null, "start-at");
	        				 if (strStartAt == null) strStartAt = "";
	        				 strStopAt = reader.getAttributeValue(null, "stop-at");
	        				 if (strStopAt == null) strStopAt = "";
	        				 strId = reader.getAttributeValue(null, "id");
	        				 ifSet = reader.getAttributeValue(null, "if-set");
	        				 if (ifSet == null) ifSet = "";
	        				 ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
	        				 if (ifNotSet == null) ifNotSet = "";
	        				 Audio audio = new Audio(strId, strStartAt, strStopAt, strTarget, strTarget, ifSet, ifNotSet, "", "");
	        				 page.addAudio(audio);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Audio Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Author:
	        			 try {
	        				 logger.trace("Author Tag");
	        				 int eventType2 = reader.getEventType();
	        				 while (true) {
	        					 if (eventType2 == XMLStreamConstants.START_ELEMENT) {
	        						 if (reader.getName().getLocalPart().equals("Name")) {
	        	        				 logger.trace("Name Tag");
	        							 reader.next();
	        							 strTmpAuthor = reader.getText();
	        						 }
	        					 }
	        					 eventType2 = reader.next();
	        					 if (eventType2 == XMLStreamConstants.END_ELEMENT) {
	        						 if (reader.getName().getLocalPart().equals("Author")) break;
	        					 }
	        				 }
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Author Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Button:
	        			 try {
	        				 logger.trace("Button Tag");
	        				 String strTarget;
	        				 strTarget = reader.getAttributeValue(null, "target");
	        				 if (strTarget == null) strTarget = "";
	        				 Set = reader.getAttributeValue(null, "set");
	        				 if (Set == null) Set = "";
	        				 UnSet = reader.getAttributeValue(null, "unset");
	        				 if (UnSet == null) UnSet = "";
	        				 ifSet = reader.getAttributeValue(null, "if-set");
	        				 if (ifSet == null) ifSet = "";
	        				 ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
	        				 if (ifNotSet == null) ifNotSet = "";
	        				 reader.next();
	        				 Button button = new Button(strTarget, reader.getText(), ifSet, ifNotSet, Set, UnSet);
	        				 page.addButton(button);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Button Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Delay:
	        			 try {
	        				 logger.trace("Delay Tag");
	        				 String strSeconds;
	        				 String strStartWith;
	        				 String strStyle;
	        				 String strTarget;
	        				 strTarget = reader.getAttributeValue(null, "target");
	        				 if (strTarget == null) strTarget = "";
	        				 strStartWith = reader.getAttributeValue(null, "start-with");
	        				 if (strStartWith == null) strStartWith = "";
	        				 strStyle = reader.getAttributeValue(null, "style");
	        				 if (strStyle == null) strStyle = "";
	        				 strSeconds = reader.getAttributeValue(null, "seconds");
	        				 ifSet = reader.getAttributeValue(null, "if-set");
	        				 if (ifSet == null) ifSet = "";
	        				 ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
	        				 if (ifNotSet == null) ifNotSet = "";
	        				 Set = reader.getAttributeValue(null, "set");
	        				 if (Set == null) Set = "";
	        				 UnSet = reader.getAttributeValue(null, "unset");
	        				 if (UnSet == null) UnSet = "";
	        				 Delay delay = new Delay(strTarget, strSeconds, ifSet, ifNotSet, strStartWith, strStyle, Set, UnSet);
	        				 page.addDelay(delay);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Delay Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Image:
	        			 try {
	        				 logger.trace("Image Tag");
	        				 String strImage;
	        				 strImage = reader.getAttributeValue(null, "id");
	        				 if (strImage == null) strImage = "";
	        				 ifSet = reader.getAttributeValue(null, "if-set");
	        				 if (ifSet == null) ifSet = "";
	        				 ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
	        				 if (ifNotSet == null) ifNotSet = "";
	        				 if (!strImage.equals("")){
	        					 Image image = new Image(strImage, ifSet, ifNotSet);
	        					 page.addImage(image);
	        				 }
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Image Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case MediaDirectory:
	        			 try {
	        				 logger.trace("MediaDirectory Tag");
	        				 reader.next();
	        				 guide.setMediaDirectory(reader.getText());
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML MediaDirectory Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Metronome:
	        			 try {
	        				 logger.trace("Metronome Tag");
	        				 String strbpm;
	        				 strbpm = reader.getAttributeValue(null, "bpm");
	        				 if (strbpm == null) strbpm = "";
	        				 ifSet = reader.getAttributeValue(null, "if-set");
	        				 if (ifSet == null) ifSet = "";
	        				 ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
	        				 if (ifNotSet == null) ifNotSet = "";
	        				 if (!strbpm.equals("")) {
	        					 Metronome metronome = new Metronome(strbpm, ifSet, ifNotSet);
	        					 page.addMetronome(metronome);
	        				 }
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Metronome Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case NOVALUE:
	        			 break;
	        		 case Page:
	        			 try {
	        				 logger.trace("Page Tag");
	        				 pageName = reader.getAttributeValue(null, "id");
	        				 ifSet = reader.getAttributeValue(null, "if-set");
	        				 if (ifSet == null) ifSet = "";
	        				 ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
	        				 if (ifNotSet == null) ifNotSet = "";
	        				 Set = reader.getAttributeValue(null, "set");
	        				 if (Set == null) Set = "";
	        				 UnSet = reader.getAttributeValue(null, "unset");
	        				 if (UnSet == null) UnSet = "";
	        				 page = new Page(pageName, ifSet, ifNotSet, Set, UnSet, guide.getAutoSetPage()); 
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Page Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Settings:
	        			 try {
	        				 logger.trace("Settings Tag");
	        				 int eventType2 = reader.getEventType();
	        				 while (true) {
	        					 if (eventType2 == XMLStreamConstants.START_ELEMENT) {
	        						 if (reader.getName().getLocalPart().equals("AutoSetPageWhenSeen")) {
	        							 reader.next();
	        							 guide.setAutoSetPage(Boolean.parseBoolean(reader.getText()));
	        						 }	        				 
	        					 }
        						 eventType2 = reader.next();
        						 if (eventType2 == XMLStreamConstants.END_ELEMENT) {
        							 if (reader.getName().getLocalPart().equals("Settings")) break;
        						 }
	        				 }
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Settings Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Text:
	        			 try {
	        				 logger.trace("Text Tag");
	        				 if (reader.getName().getLocalPart().equals("Text")) {
	        					 String text = "";
	        					 String tag = "";
	        					 int eventType2 = reader.next();
	        					 while (true) {
	        						 switch (eventType2) {
	        						 case XMLStreamConstants.START_ELEMENT:
	        							 tag = reader.getName().toString();
	        							 text = text + "<" + tag;
	        							 for (int i=0; i < reader.getAttributeCount(); i++) {
	        								 text = text + " " + reader.getAttributeName(i) + "=\"" + reader.getAttributeValue(i) + "\"";
	        							 }
	        							 text = text + ">";
	        							 break;
	        						 case XMLStreamConstants.END_ELEMENT:
	        							 text = text + "</"  + tag + ">";
	        							 break;
	        						 case XMLStreamConstants.CHARACTERS:
	        							 text = text + reader.getText();
	        							 break;
	        						 }
	        						 eventType2 = reader.next();
	        						 if (eventType2 == XMLStreamConstants.END_ELEMENT) {
	        							 if (reader.getName().getLocalPart().equals("Text")) break;
	        						 }
	        					 }
	        					 page.setText(text);
	        				 }
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Text Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Video:
	        			 try {
	        				 logger.trace("Video Tag");
	        				 String strId;
	        				 String strStartAt;
	        				 String strStopAt;
	        				 String strTarget;
	        				 strTarget = reader.getAttributeValue(null, "target");
	        				 if (strTarget == null) strTarget = "";
	        				 strStartAt = reader.getAttributeValue(null, "start-at");
	        				 if (strStartAt == null) strStartAt = "";
	        				 strStopAt = reader.getAttributeValue(null, "stop-at");
	        				 if (strStopAt == null) strStopAt = "";
	        				 strId = reader.getAttributeValue(null, "id");
	        				 ifSet = reader.getAttributeValue(null, "if-set");
	        				 if (ifSet == null) ifSet = "";
	        				 ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
	        				 if (ifNotSet == null) ifNotSet = "";
	        				 Video video = new Video(strId, strStartAt, strStopAt, strTarget, ifSet, ifNotSet, "", "", "");
	        				 page.addVideo(video);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML Video Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 default:
	        			 break;
	        		 }
	        		 break;
	        	 case XMLStreamConstants.END_ELEMENT:
	        		 logger.trace("loadXML End tag " + reader.getName().getLocalPart());
	        		 try {
	        			 if (reader.getName().getLocalPart().equals("Page")) {
	        				 Chapter chapter = guide.getChapters().get("default");
	        				 chapter.getPages().put(page.getPageName(), page);
	        			 }
	        		 } catch (Exception e1) {
	        			 logger.error("loadXML EndPage Exception " + e1.getLocalizedMessage(), e1);
	        		 }
	        		 break;
	        	 case XMLStreamConstants.CHARACTERS:
	        		 break;
	        	 }

	        	 //eventType = reader.next();
	         }
	         
	         guide.setTitle(strTmpTitle + ", " + strTmpAuthor);

			// Return to where we left off
			try {
				strPage = guide.getSettings().getPage();
				strFlags = guide.getSettings().getFlags();
				if (strFlags != "") {
					ComonFunctions.SetFlags(strFlags, guide.getFlags());
				}
			} catch (Exception e1) {
				logger.error("loadXML Continue Exception " + e1.getLocalizedMessage(), e1);
			}

		} catch (Exception e) {
			logger.error("loadXML Exception ", e);
		}
		return strPage;
	}

}
