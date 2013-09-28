package org.guideme.guideme.readers;

import java.io.FileInputStream;
import java.util.HashMap;

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
import org.guideme.guideme.settings.GuideSettings;

public class XmlGuideReader {
	private static Logger logger = LogManager.getLogger();
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();

	private static XmlGuideReader xmlGuideReader;

	private XmlGuideReader() {
	}
	
	public static synchronized XmlGuideReader getXmlGuideReader() {
		if (xmlGuideReader == null) {
			xmlGuideReader = new XmlGuideReader();
		}
		return xmlGuideReader;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	
	private enum TagName
	{
		pref, Title, Author, MediaDirectory, Settings, Page, Metronome, Image, Audio, Video, Delay, Button, Text, javascript, NOVALUE;

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
	
	public String loadXML(String xmlFileName, Guide guide) {
		String strTmpTitle = "";
		String strTmpAuthor = "";
		String strPage = "start";
		String strFlags;
		String strPreXMLPath;
		String pageId; 
		String ifSet; 
		String ifNotSet; 
		String Set;
		String UnSet;
		String strTag;
		Page page = null;
		GuideSettings guideSettings;
		
		try {
			strPreXMLPath = xmlFileName;
			int intPos = xmlFileName.lastIndexOf("\\");
			int intPos2 = xmlFileName.lastIndexOf(".xml");
			String PresName = xmlFileName.substring(intPos + 1, intPos2);
			guide.reset(PresName);
			HashMap<String, Chapter> chapters = guide.getChapters();
			Chapter chapter = new Chapter("default");
			chapters.put("default", chapter);
			guideSettings = guide.getSettings();

			FileInputStream fis = new FileInputStream(strPreXMLPath);
	        UnicodeBOMInputStream ubis = new UnicodeBOMInputStream(fis);
	        ubis.skipBOM();
			
		    XMLInputFactory factory = XMLInputFactory.newInstance();
		    XMLStreamReader reader = factory.createXMLStreamReader(ubis);
	         
	         while (reader.hasNext()) {
	        	 int eventType = reader.next(); 
	        	 switch (eventType) {
	        	 case XMLStreamConstants.START_DOCUMENT:
	        		 logger.trace("loadXML " + PresName + " Start document " + strPreXMLPath);
	        		 break;
	        	 case XMLStreamConstants.END_DOCUMENT:
	        		 logger.trace("loadXML " + PresName + " End document");
	        		 break;
	        	 case XMLStreamConstants.START_ELEMENT:
	        		 //logger.trace("loadXML " + PresName + " Start tag " + reader.getName().getLocalPart());
	        		 strTag = reader.getName().getLocalPart();

	        		 switch (TagName.toTag(strTag)) {
	        		 case pref:
	        			 String key;
	        			 String screen = "";
	        			 String type;
	        			 String value = "";
	        			 key = reader.getAttributeValue(null, "key");
	        			 type = reader.getAttributeValue(null, "type");
	        			 if (! guideSettings.keyExists(key, type)) {
	        				 screen = reader.getAttributeValue(null, "screen");
	        				 value = reader.getAttributeValue(null, "value");
	        					if (type.equals("String")) {
	        						guideSettings.addPref(key, value, screen);
	        					}
	        					if (type.equals("Boolean")) {
	        						guideSettings.addPref(key, Boolean.parseBoolean(value), screen);
	        					}
	        					if (type.equals("Number")) {
	        						guideSettings.addPref(key, Double.parseDouble(value), screen);
	        					}
	        			 }
	        			 logger.trace("loadXML " + PresName + " pref " + key + "|" + value + "|" + screen + "|" + type);
	        			 break;
	        		 case Title:
	        			 try {
	        				 reader.next();
	        				 if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
	        					 strTmpTitle = reader.getText();
	        				 } else {
	        					 strTmpTitle = "";
	        				 }
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Title Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 logger.trace("loadXML " + PresName + " title " + strTmpTitle);
	        			 break;
	        		 case Audio:
	        			 try {
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
		        			 logger.trace("loadXML " + PresName + " Audio " + strId+ "|" + strStartAt+ "|" + strStopAt+ "|" + strTarget+ "|" + strTarget+ "|" + ifSet+ "|" + ifNotSet);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Audio Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Author:
	        			 try {
	        				 int eventType2 = reader.getEventType();
	        				 while (true) {
	        					 if (eventType2 == XMLStreamConstants.START_ELEMENT) {
	        						 if (reader.getName().getLocalPart().equals("Name")) {
	        	        				 logger.trace("Name Tag");
	        							 reader.next();
	        	        				 if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
	        	        					 strTmpAuthor = reader.getText();
	        	        				 } else {
	        	        					 strTmpAuthor = "";
	        	        				 }
	        						 }
	        					 }
	        					 eventType2 = reader.next();
	        					 if (eventType2 == XMLStreamConstants.END_ELEMENT) {
	        						 if (reader.getName().getLocalPart().equals("Author")) break;
	        					 }
	        				 }
		        			 logger.trace("loadXML " + PresName + " Author " + strTmpAuthor);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Author Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Button:
	        			 try {
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
	        				 String javascript = reader.getAttributeValue(null, "javascript");
	        				 if (javascript == null) javascript = "";
	        				 reader.next();
	        				 String BtnText;
	        				 if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
	        					 BtnText = reader.getText();
	        				 } else {
	        					 BtnText = "";
	        				 }
	        				 Button button = new Button(strTarget, BtnText, ifSet, ifNotSet, Set, UnSet, javascript);
	        				 page.addButton(button);
		        			 logger.trace("loadXML " + PresName + " Button " + strTarget+ "|" + BtnText + "|" + ifSet+ "|" + ifNotSet+ "|" + Set+ "|" + UnSet + "|" + javascript);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Button Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Delay:
	        			 try {
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
	        				 Delay delay = new Delay(strTarget, strSeconds, ifSet, ifNotSet, strStartWith, strStyle, Set, UnSet, "");
	        				 page.addDelay(delay);
		        			 logger.trace("loadXML " + PresName + " Delay " + strTarget+ "|" + strSeconds+ "|" + ifSet+ "|" + ifNotSet+ "|" + strStartWith+ "|" + strStyle+ "|" + Set+ "|" + UnSet);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Delay Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Image:
	        			 try {
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
		        			 logger.trace("loadXML " + PresName + " Image " + strImage+ "|" + ifSet+ "|" + ifNotSet);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Image Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case MediaDirectory:
	        			 try {
	        				 reader.next();
	        				 String dir;
	        				 if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
	        					 dir  = reader.getText();
	        				 } else {
	        					 dir = "";
	        				 }
	        				 guide.setMediaDirectory(dir);
		        			 logger.trace("loadXML " + PresName + " MediaDirectory " + dir);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " MediaDirectory Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Metronome:
	        			 try {
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
		        			 logger.trace("loadXML " + PresName + " Metronome " + strbpm + "|" + ifSet + "|" + ifNotSet);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Metronome Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case NOVALUE:
	        			 break;
	        		 case Page:
	        			 try {
	        				 pageId = reader.getAttributeValue(null, "id");
	        				 ifSet = reader.getAttributeValue(null, "if-set");
	        				 if (ifSet == null) ifSet = "";
	        				 ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
	        				 if (ifNotSet == null) ifNotSet = "";
	        				 Set = reader.getAttributeValue(null, "set");
	        				 if (Set == null) Set = "";
	        				 UnSet = reader.getAttributeValue(null, "unset");
	        				 if (UnSet == null) UnSet = "";
	        				 page = new Page(pageId, ifSet, ifNotSet, Set, UnSet, guide.getAutoSetPage()); 
		        			 logger.trace("loadXML " + PresName + " Page " + pageId + "|" + ifSet + "|" + ifNotSet + "|" + Set + "|" + UnSet);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Page Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Settings:
	        			 try {
	        				 int eventType2 = reader.getEventType();
	        				 while (true) {
	        					 if (eventType2 == XMLStreamConstants.START_ELEMENT) {
	        						 if (reader.getName().getLocalPart().equals("AutoSetPageWhenSeen")) {
	        							 reader.next();
	        	        				 if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
	        	        					 guide.setAutoSetPage(Boolean.parseBoolean(reader.getText()));
	        	        				 } else {
	        	        					 guide.setAutoSetPage(true); 
	        	        				 }
	        						 }	        				 
	        					 }
        						 eventType2 = reader.next();
        						 if (eventType2 == XMLStreamConstants.END_ELEMENT) {
        							 if (reader.getName().getLocalPart().equals("Settings")) break;
        						 }
	        				 }
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Settings Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Text:
	        			 try {
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
			        			 logger.trace("loadXML " + PresName + " Text " + text);
	        				 }
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Text Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case Video:
	        			 try {
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
		        			 logger.trace("loadXML " + PresName + " Video " + strId + "|" + strStartAt + "|" + strStopAt + "|" + strTarget + "|" + ifSet + "|" + ifNotSet);
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Video Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 case javascript:
	        			 try {
	        				 if (reader.getName().getLocalPart().equals("javascript")) {
			        			 String id = reader.getAttributeValue(null, "id");
			        			 String javascript = "";
	        					 int eventType2 = reader.next();
	        					 while (true) {
	        						 switch (eventType2) {
	        						 case XMLStreamConstants.START_ELEMENT:
	        							 break;
	        						 case XMLStreamConstants.END_ELEMENT:
	        							 break;
	        						 case XMLStreamConstants.CHARACTERS:
	        							 javascript = javascript + reader.getText();
	        							 break;
	        						 }
	        						 eventType2 = reader.next();
	        						 if (eventType2 == XMLStreamConstants.END_ELEMENT) {
	        							 if (reader.getName().getLocalPart().equals("javascript")) break;
	        						 }
	        					 }
			        			 logger.trace("loadXML " + PresName + " javascript " + id  + "|" + javascript);
			        			 if (! javascript.equals("")) {
			        				 page.setjScript(id, javascript);
			        			 }
	        				 }
	        			 } catch (Exception e1) {
	        				 logger.error("loadXML " + PresName + " Text Exception " + e1.getLocalizedMessage(), e1);
	        			 }
	        			 break;
	        		 default:
	        			 break;
	        		 }
	        		 break;
	        	 case XMLStreamConstants.END_ELEMENT:
	        		 //logger.trace("loadXML End tag " + reader.getName().getLocalPart());
	        		 try {
	        			 if (reader.getName().getLocalPart().equals("Page")) {
	        				 chapter = guide.getChapters().get("default");
	        				 chapter.getPages().put(page.getId(), page);
	        			 }
	        		 } catch (Exception e1) {
	        			 logger.error("loadXML " + PresName + " EndPage Exception " + e1.getLocalizedMessage(), e1);
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
				strPage = guideSettings.getPage();
				strFlags = guideSettings.getFlags();
				if (strFlags != "") {
					comonFunctions.SetFlags(strFlags, guide.getFlags());
				}
			} catch (Exception e1) {
				logger.error("loadXML " + PresName + " Continue Exception " + e1.getLocalizedMessage(), e1);
			}
			guide.setSettings(guideSettings);
			guideSettings.saveSettings();
		} catch (Exception e) {
			logger.error("loadXML " + xmlFileName + " Exception ", e);
		}
		return strPage;
	}

}
