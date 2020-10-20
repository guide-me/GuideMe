package org.guideme.guideme.readers;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guideme.guideme.model.Audio;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Chapter;
import org.guideme.guideme.model.Delay;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Image;
import org.guideme.guideme.model.LoadGuide;
import org.guideme.guideme.model.Metronome;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.model.Text;
import org.guideme.guideme.model.Timer;
import org.guideme.guideme.model.Video;
import org.guideme.guideme.model.Webcam;
import org.guideme.guideme.model.WebcamButton;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.ui.DebugShell;

public class XmlGuideReader {
	private static Logger logger = LogManager.getLogger();
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	private DebugShell debugShell;

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
		pref, 
		Title, 
		Author, 
		MediaDirectory, 
		Settings, 
		Page, 
		Metronome, 
		Image, 
		Audio, 
		Audio2, 
		Video, 
		Webcam, 
		Delay, 
		Timer, 
		Button, 
		WebcamButton, 
		LeftText, 
		Text, 
		javascript, 
		GlobalJavascript, 
		CSS, 
		Include,
		LoadGuide,
		NOVALUE;

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

	public String loadXML(String xmlFileName, Guide guide, AppSettings appSettings, DebugShell debugShell) {
		String strPage = "start";
		String strFlags;
		GuideSettings guideSettings;

		try {
			int intPos = xmlFileName.lastIndexOf(appSettings.getFileSeparator());
			int intPos2 = xmlFileName.lastIndexOf(".xml");
			String PresName = xmlFileName.substring(intPos + 1, intPos2);
			guide.reset(PresName);
			HashMap<String, Chapter> chapters = guide.getChapters();
			Chapter chapter = new Chapter("default");
			chapters.put("default", chapter);
			guideSettings = guide.getSettings();
			guideSettings.setPageSound(true);

			this.debugShell = debugShell;
			parseFile(xmlFileName, guide, PresName, chapter, appSettings);

			// Return to where we left off
			try {
				if (guideSettings.isForceStartPage()) {
					guideSettings.setPage("start");
				}
				strPage = guideSettings.getPage();
				strFlags = guideSettings.getFlags();
				if (strFlags != "") {
					comonFunctions.SetFlags(strFlags, guide.getFlags());
				}
			} catch (Exception e1) {
				logger.error("loadXML " + PresName + " Continue Exception " + e1.getLocalizedMessage(), e1);
			}
			guide.setSettings(guideSettings);
			guide.getSettings().saveSettings();
		} catch (Exception e) {
			logger.error("loadXML " + xmlFileName + " Exception ", e);
		}
		return strPage;
	}

	private void parseFile(String xmlFileName, Guide guide, String PresName, Chapter chapter, AppSettings appSettings) {
		GuideSettings guideSettings;
		String strTag;
		String ifSet; 
		String ifNotSet; 
		String ifBefore;
		String ifAfter;
		String Set;
		String UnSet;
		String pageId; 
		Page page = null;
		String strTmpTitle = "";
		String strTmpAuthor = "";
		String btnDis;
		boolean disabled;
		String btnDefault;
		boolean defaultBtn;

		Page page404 = new Page("GuideMe404Error","", "", "", "", false, "", "");
		chapter.getPages().put(page404.getId(), page404);
		guideSettings = guide.getSettings();

		try {
			FileInputStream fis = new FileInputStream(xmlFileName);
			UnicodeBOMInputStream ubis = new UnicodeBOMInputStream(fis);
			ubis.skipBOM();

			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(ubis);
			while (reader.hasNext()) {
				int eventType = reader.next(); 
				switch (eventType) {
				case XMLStreamConstants.START_DOCUMENT:
					logger.trace("loadXML " + PresName + " Start document " + xmlFileName);
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
						String order = "";
						int sortOrder = 0;
						key = reader.getAttributeValue(null, "key");
						type = reader.getAttributeValue(null, "type");
						order = reader.getAttributeValue(null, "sortOrder");
						try {
							sortOrder = Integer.parseInt(order);
						}
						catch (Exception ex) {
							sortOrder = 0;
						}
						if (! guideSettings.keyExists(key, type)) {
							screen = reader.getAttributeValue(null, "screen");
							value = reader.getAttributeValue(null, "value");
							if (type.equals("String")) {
								guideSettings.addPref(key, value, screen, sortOrder);
							}
							if (type.equals("Boolean")) {
								guideSettings.addPref(key, Boolean.parseBoolean(value), screen, sortOrder);
							}
							if (type.equals("Number")) {
								guideSettings.addPref(key, Double.parseDouble(value), screen, sortOrder);
							}
						} else {
							guideSettings.setPrefOrder(key, sortOrder);
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
							String scriptVar;
							String volumeString;
							int volume;
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
							String loops = reader.getAttributeValue(null, "loops"); 
							if (loops == null) loops = "0";
							String javascript = reader.getAttributeValue(null, "onTriggered");
							if (javascript == null) javascript = "";
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							scriptVar = reader.getAttributeValue(null, "scriptvar");
							if (scriptVar == null) scriptVar = "";
							volumeString = reader.getAttributeValue(null, "volume");
							if (volumeString == null) volumeString = "100";
							try
							{
								volume = Integer.parseInt(volumeString);
								if (volume > 100) volume = 100;
								if (volume < 0) volume = 0;
							} catch (Exception ex) 
							{
								volume = 100;
							}
							

							Audio audio = new Audio(strId, strStartAt, strStopAt, strTarget, ifSet, ifNotSet, "", "", loops, javascript, ifAfter, ifBefore, scriptVar, volume);
							page.addAudio(audio);
							logger.trace("loadXML " + PresName + " Audio " + strId+ "|" + strStartAt+ "|" + strStopAt+ "|" + strTarget+ "|" + javascript+ "|" + ifSet+ "|" + ifNotSet);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Audio Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case Audio2:
						try {
							String strId;
							String strStartAt;
							String strStopAt;
							String strTarget;
							String scriptVar;
							String volumeString;
							int volume;
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
							String loops = reader.getAttributeValue(null, "loops"); 
							if (loops == null) loops = "0";
							String javascript = reader.getAttributeValue(null, "onTriggered");
							if (javascript == null) javascript = "";
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							scriptVar = reader.getAttributeValue(null, "scriptvar");
							if (scriptVar == null) scriptVar = "";
							volumeString = reader.getAttributeValue(null, "volume");
							if (volumeString == null) volumeString = "100";
							try
							{
								volume = Integer.parseInt(volumeString);
								if (volume > 100) volume = 100;
								if (volume < 0) volume = 0;
							} catch (Exception ex) 
							{
								volume = 100;
							}
							

							Audio audio = new Audio(strId, strStartAt, strStopAt, strTarget, ifSet, ifNotSet, "", "", loops, javascript, ifAfter, ifBefore, scriptVar, volume);
							page.addAudio2(audio);
							logger.trace("loadXML " + PresName + " Audio2 " + strId+ "|" + strStartAt+ "|" + strStopAt+ "|" + strTarget+ "|" + javascript+ "|" + ifSet+ "|" + ifNotSet);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Audio2 Exception " + e1.getLocalizedMessage(), e1);
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
							String scriptVar;
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
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							String javascript = reader.getAttributeValue(null, "onclick");
							if (javascript == null) javascript = "";
							String image = reader.getAttributeValue(null, "image"); 
							if (image == null) image = "";
							String hotKey;
							hotKey = reader.getAttributeValue(null, "hotkey");
							if (hotKey == null) hotKey = "";

							scriptVar = reader.getAttributeValue(null, "scriptvar");
							if (scriptVar == null) scriptVar = "";

							String fontName;
							fontName = reader.getAttributeValue(null, "fontName");
							if (fontName == null) fontName = "";
							String fontHeight;
							fontHeight = reader.getAttributeValue(null, "fontHeight");
							if (fontHeight == null) fontHeight = "";
							String bgColor1;
							bgColor1 = reader.getAttributeValue(null, "bgColor1");
							if (bgColor1 == null) bgColor1 = "";
							String bgColor2;
							bgColor2 = reader.getAttributeValue(null, "bgColor2");
							if (bgColor2 == null) bgColor2 = "";
							String fontColor;
							fontColor = reader.getAttributeValue(null, "fontColor");
							if (fontColor == null) fontColor = "";
							String sort;
							sort = reader.getAttributeValue(null, "sortOrder");
							if (sort == null) sort = "1";
							try {
								sortOrder = Integer.parseInt(sort);
							} catch (Exception ex) {
								sortOrder = 1;
							}
							btnDis = reader.getAttributeValue(null, "disabled");
							if (btnDis == null) btnDis = "false";
							try {
								disabled = Boolean.valueOf(btnDis);
							} catch (Exception ex) {
								disabled = false;
							}
							btnDefault = reader.getAttributeValue(null, "default");
							if (btnDefault == null) btnDefault = "false";
							try {
								defaultBtn = Boolean.valueOf(btnDefault);
							} catch (Exception ex) {
								defaultBtn = false;
							}
							String btnId;
							btnId = reader.getAttributeValue(null, "id");
							if (btnId == null) btnId = "";
							
							//reader.next();
							String BtnText = "";
							if (reader.getName().getLocalPart().equals("Button")) {
								try {
									BtnText = processText(reader, "Button");
								}
								catch (Exception ex) {
									logger.error("loadXML " + PresName + " Button Exception Text " + ex.getLocalizedMessage(), ex);
								}
							}
							//if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
							//	BtnText = reader.getText();
							//} else {
							//	BtnText = "";
							//}
							Button button = new Button(strTarget, BtnText, ifSet, ifNotSet, Set, UnSet, javascript, image, hotKey, fontName, fontHeight, fontColor, bgColor1, bgColor2, sortOrder, ifAfter, ifBefore, disabled, btnId, scriptVar, defaultBtn);
							page.addButton(button);
							logger.trace("loadXML " + PresName + " Button " + strTarget+ "|" + BtnText + "|" + ifSet+ "|" + ifNotSet+ "|" + Set+ "|" + UnSet + "|" + javascript);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Button Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case WebcamButton:
						try {
							String strType; 
							String strFile; 
							String strTarget;
							String scriptVar;
							strFile = reader.getAttributeValue(null, "file");
							if (strFile == null) strFile = "";
							strType = reader.getAttributeValue(null, "type");
							if (strType == null) strType = "Capture";
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
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							String javascript = reader.getAttributeValue(null, "onclick");
							if (javascript == null) javascript = "";
							String image = reader.getAttributeValue(null, "image"); 
							if (image == null) image = "";
							String hotKey;
							hotKey = reader.getAttributeValue(null, "hotkey");
							if (hotKey == null) hotKey = "";

							scriptVar = reader.getAttributeValue(null, "scriptvar");
							if (scriptVar == null) scriptVar = "";

							String fontName;
							fontName = reader.getAttributeValue(null, "fontName");
							if (fontName == null) fontName = "";
							String fontHeight;
							fontHeight = reader.getAttributeValue(null, "fontHeight");
							if (fontHeight == null) fontHeight = "";
							String bgColor1;
							bgColor1 = reader.getAttributeValue(null, "bgColor1");
							if (bgColor1 == null) bgColor1 = "";
							String bgColor2;
							bgColor2 = reader.getAttributeValue(null, "bgColor2");
							if (bgColor2 == null) bgColor2 = "";
							String fontColor;
							fontColor = reader.getAttributeValue(null, "fontColor");
							if (fontColor == null) fontColor = "";
							String sort;
							sort = reader.getAttributeValue(null, "sortOrder");
							if (sort == null) sort = "1";
							try {
								sortOrder = Integer.parseInt(sort);
							} catch (Exception ex) {
								sortOrder = 1;
							}
							btnDis = reader.getAttributeValue(null, "disabled");
							if (btnDis == null) btnDis = "false";
							try {
								disabled = Boolean.valueOf(btnDis);
							} catch (Exception ex) {
								disabled = false;
							}
							btnDefault = reader.getAttributeValue(null, "default");
							if (btnDefault == null) btnDefault = "false";
							try {
								defaultBtn = Boolean.valueOf(btnDefault);
							} catch (Exception ex) {
								defaultBtn = false;
							}
							String btnId;
							btnId = reader.getAttributeValue(null, "id");
							if (btnId == null) btnId = "";
							
							//reader.next();
							String BtnText = "";
							if (reader.getName().getLocalPart().equals("WebcamButton")) {
								try {
									BtnText = processText(reader, "WebcamButton");
								}
								catch (Exception ex) {
									logger.error("loadXML " + PresName + " WebcamButton Exception Text " + ex.getLocalizedMessage(), ex);
								}
							}
							//if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
							//	BtnText = reader.getText();
							//} else {
							//	BtnText = "";
							//}
							WebcamButton button = new WebcamButton(strType, strFile, strTarget, BtnText, ifSet, ifNotSet, Set, UnSet, javascript, image, hotKey, fontName, fontHeight, fontColor, bgColor1, bgColor2, sortOrder, ifAfter, ifBefore, disabled, btnId, scriptVar, defaultBtn);
							page.addWebcamButton(button);
							logger.trace("loadXML " + PresName + " WebcamButton " + strType+ "|" + strFile + "|" + strTarget + "|" + BtnText + "|" + ifSet+ "|" + ifNotSet+ "|" + Set+ "|" + UnSet + "|" + javascript);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " WebcamButton Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case Delay:
						try {
							String strSeconds;
							String strStartWith;
							String strStyle;
							String strTarget;
							String scriptVar;
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
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							Set = reader.getAttributeValue(null, "set");
							if (Set == null) Set = "";
							UnSet = reader.getAttributeValue(null, "unset");
							if (UnSet == null) UnSet = "";

							scriptVar = reader.getAttributeValue(null, "scriptvar");
							if (scriptVar == null) scriptVar = "";
							
							String javascript = reader.getAttributeValue(null, "onTriggered");
							if (javascript == null) javascript = "";
							Delay delay = new Delay(strTarget, strSeconds, ifSet, ifNotSet, strStartWith, strStyle, Set, UnSet, javascript, ifAfter, ifBefore, scriptVar);
							page.addDelay(delay);
							logger.trace("loadXML " + PresName + " Delay " + strTarget+ "|" + strSeconds+ "|" + ifSet+ "|" + ifNotSet+ "|" + strStartWith+ "|" + strStyle+ "|" + Set+ "|" + UnSet + "|" + javascript);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Delay Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case Timer:
						try {
							String strSeconds;
							String imageId;
							String text = "";
							String id;
							strSeconds = reader.getAttributeValue(null, "seconds");
							ifSet = reader.getAttributeValue(null, "if-set");
							if (ifSet == null) ifSet = "";
							ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
							if (ifNotSet == null) ifNotSet = "";
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							Set = reader.getAttributeValue(null, "set");
							if (Set == null) Set = "";
							UnSet = reader.getAttributeValue(null, "unset");
							if (UnSet == null) UnSet = "";
							imageId = reader.getAttributeValue(null, "imageId");
							if (imageId == null) imageId = "";
							id = reader.getAttributeValue(null, "id");
							if (id == null) id = "";
							
							String javascript = reader.getAttributeValue(null, "onTriggered");
							if (javascript == null) javascript = "";
							if (reader.getName().getLocalPart().equals("Timer")) {
								try {
									text = processText(reader, "Timer");
								}
								catch (Exception ex) {
									logger.error("loadXML " + PresName + " Timer Exception Text " + ex.getLocalizedMessage(), ex);
								}
							}
							Timer timer = new Timer(strSeconds, javascript, imageId, text, ifSet, ifNotSet, Set, UnSet, ifAfter, ifBefore, id);
							page.addTimer(timer);
							logger.trace("loadXML " + PresName + " Timer " + strSeconds + "|" + javascript);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Timer Exception " + e1.getLocalizedMessage(), e1);
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
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							if (!strImage.equals("")){
								Image image = new Image(strImage, ifSet, ifNotSet, ifAfter, ifBefore);
								page.addImage(image);
							}
							logger.trace("loadXML " + PresName + " Image " + strImage+ "|" + ifSet+ "|" + ifNotSet);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Image Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case LoadGuide:
						try {
							String strGuidePath;
							String strTarget;
							String strReturnTarget;
							strGuidePath = reader.getAttributeValue(null, "guidePath");
							if (strGuidePath == null) strGuidePath = "";
							strTarget = reader.getAttributeValue(null, "target");
							if (strTarget == null) strTarget = "";
							strReturnTarget = reader.getAttributeValue(null, "return-target");
							if (strReturnTarget == null) strReturnTarget = "";
							ifSet = reader.getAttributeValue(null, "if-set");
							if (ifSet == null) ifSet = "";
							ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
							if (ifNotSet == null) ifNotSet = "";
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							String preScript = reader.getAttributeValue(null, "preScript");
							if (preScript == null) preScript = "";
							String postScript = reader.getAttributeValue(null, "postScript");
							if (postScript == null) postScript = "";
							if (!strGuidePath.equals("")){
								LoadGuide loadGuide = new LoadGuide(strGuidePath, strTarget, strReturnTarget, ifSet, ifNotSet, ifAfter, ifBefore, preScript, postScript);
								page.addLoadGuide(loadGuide);
							}
							logger.trace("loadXML " + PresName + " LoadGuide " + strGuidePath + "|" + strTarget + "|" + ifSet+ "|" + ifNotSet);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " LoadGuide Exception " + e1.getLocalizedMessage(), e1);
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
							String beats;
							String loops;
							String rhythm;
							strbpm = reader.getAttributeValue(null, "bpm");
							if (strbpm == null) strbpm = "";
							ifSet = reader.getAttributeValue(null, "if-set");
							if (ifSet == null) ifSet = "";
							ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
							if (ifNotSet == null) ifNotSet = "";
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							beats = reader.getAttributeValue(null, "beats"); 
							if (beats == null) beats = "4";
							loops = reader.getAttributeValue(null, "loops"); 
							if (loops == null) loops = "-1";
							rhythm = reader.getAttributeValue(null, "rhythm"); 
							if (rhythm == null) rhythm = "";
							if (!strbpm.equals("")) {
								Metronome metronome = new Metronome(strbpm, ifSet, ifNotSet, Integer.parseInt(beats), Integer.parseInt(loops), rhythm, ifAfter, ifBefore);
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
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							page = new Page(pageId, ifSet, ifNotSet, Set, UnSet, guide.getAutoSetPage(), ifAfter, ifBefore);
							debugShell.addPagesCombo(pageId);
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
									} else if (reader.getName().getLocalPart().equals("PageSound")) {
										reader.next();
										if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
											guideSettings.setPageSound(Boolean.parseBoolean(reader.getText()));
										} else {
											guideSettings.setPageSound(true); 
										}
									} else if (reader.getName().getLocalPart().equals("ForceStartPage")) {
										reader.next();
										if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
											guideSettings.setForceStartPage(Boolean.parseBoolean(reader.getText()));
										} else {
											guideSettings.setPageSound(false); 
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
							ifSet = reader.getAttributeValue(null, "if-set");
							if (ifSet == null) ifSet = "";
							ifNotSet = reader.getAttributeValue(null, "if-not-set");
							if (ifNotSet == null) ifNotSet = "";
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";

							if (reader.getName().getLocalPart().equals("Text")) {
								String text = processText(reader, "Text");
								page.addText(new Text(text, ifSet, ifNotSet, ifBefore, ifAfter));
								logger.trace("loadXML " + PresName + " Text " + text);
							}
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Text Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case LeftText:
						try {
							ifSet = reader.getAttributeValue(null, "if-set");
							if (ifSet == null) ifSet = "";
							ifNotSet = reader.getAttributeValue(null, "if-not-set");
							if (ifNotSet == null) ifNotSet = "";
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";

							if (reader.getName().getLocalPart().equals("LeftText")) {
								String text = processText(reader, "LeftText");
								page.addText(new Text(text, ifSet, ifNotSet, ifBefore, ifAfter));
								logger.trace("loadXML " + PresName + " Left Text " + text);
							}
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Left Text Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case Video:
						try {
							String strId;
							String strStartAt;
							String strStopAt;
							String strTarget;
							String scriptVar;
							String volumeString;
							int volume;
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
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							scriptVar = reader.getAttributeValue(null, "scriptvar");
							if (scriptVar == null) scriptVar = "";
							String loops = reader.getAttributeValue(null, "loops"); 
							if (loops == null) loops = "0";
							volumeString = reader.getAttributeValue(null, "volume");
							if (volumeString == null) volumeString = "100";
							try
							{
								volume = Integer.parseInt(volumeString);
								if (volume > 100) volume = 100;
								if (volume < 0) volume = 0;
							} catch (Exception ex) 
							{
								volume = 100;
							}
							String javascript = reader.getAttributeValue(null, "onTriggered");
							if (javascript == null) javascript = "";
							Video video = new Video(strId, strStartAt, strStopAt, strTarget, ifSet, ifNotSet, "", "", loops, javascript, ifAfter, ifBefore, scriptVar, volume);
							page.addVideo(video);
							logger.trace("loadXML " + PresName + " Video " + strId + "|" + strStartAt + "|" + strStopAt + "|" + strTarget + "|" + ifSet + "|" + ifNotSet + "|" + "" + "|" + "" + "|" + loops + "|" + javascript + "|" + volume);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Video Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case Webcam:
						try {
							ifSet = reader.getAttributeValue(null, "if-set");
							if (ifSet == null) ifSet = "";
							ifNotSet = reader.getAttributeValue(null, "if-not-set"); 
							if (ifNotSet == null) ifNotSet = "";
							ifBefore = reader.getAttributeValue(null, "if-before");
							if (ifBefore == null) ifBefore = "";
							ifAfter = reader.getAttributeValue(null, "if-after");
							if (ifAfter == null) ifAfter = "";
							Webcam webcam = new Webcam(ifSet, ifNotSet, ifAfter, ifBefore);
							page.addWebcam(webcam);
							logger.trace("loadXML " + PresName + " Webcam " + ifSet + "|" + ifNotSet + "|" + ifBefore + "|" + ifAfter);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Webcam Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case javascript:
						try {
							if (reader.getName().getLocalPart().equals("javascript")) {
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
								logger.trace("loadXML " + PresName + " javascript " + javascript);
								if (! javascript.equals("")) {
									page.setjScript(javascript);
								}
							}
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Text Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case GlobalJavascript:
						try {
							if (reader.getName().getLocalPart().equals("GlobalJavascript")) {
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
										if (reader.getName().getLocalPart().equals("GlobalJavascript")) break;
									}
								}
								logger.trace("loadXML " + PresName + " GlobalJavascript " + javascript);
								if (! javascript.equals("")) {
									javascript = guide.getGlobaljScript() + javascript;
									guide.setGlobaljScript(javascript);
								}
							}
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " Text Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case CSS:
						try {
							reader.next();
							String gcss = "";
							while (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
								gcss  = gcss + reader.getText();
								reader.next();
							}
							guide.setCss(gcss);
							logger.trace("loadXML " + PresName + " CSS " + gcss);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " CSS Exception " + e1.getLocalizedMessage(), e1);
						}
						break;
					case Include:
						try {
							String incFileName;
							String dataDirectory;
							String prefix = "";
							String fileSeparator = appSettings.getFileSeparator();
							dataDirectory = appSettings.getDataDirectory();
							if (dataDirectory.startsWith("/")) {
								prefix = "/";
							}
							dataDirectory = prefix + comonFunctions.fixSeparator(appSettings.getDataDirectory(), fileSeparator);
							String mediaDirectory = comonFunctions.fixSeparator(guide.getMediaDirectory(), fileSeparator);
							dataDirectory = dataDirectory + fileSeparator + mediaDirectory;
							if (!dataDirectory.endsWith(fileSeparator)) {
								dataDirectory = dataDirectory + fileSeparator;
							}
							
							//Handle wild cards
							incFileName = comonFunctions.fixSeparator(reader.getAttributeValue(null, "file"), fileSeparator);
							if (incFileName.toLowerCase().endsWith("*.js")) {
								ArrayList<String> filesList = new ArrayList<String>();
							    try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(
							            Paths.get(dataDirectory), incFileName)) {
							        dirStream.forEach(path -> filesList.add(path.toString()));
							    }
							    for(String filePath:filesList)
							    {
							    	includeJavaScript(guide, filePath);
							    }
							} else if (incFileName.toLowerCase().endsWith("*.xml")) {
								ArrayList<String> filesList = new ArrayList<String>();
							    try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(
							            Paths.get(dataDirectory), incFileName)) {
							        dirStream.forEach(path -> filesList.add(path.toString()));
							    }
							    for(String filePath:filesList)
							    {
									parseFile(filePath, guide, PresName, chapter, appSettings);
							    }
							} else {
								incFileName = dataDirectory + incFileName;
								if (incFileName.toLowerCase().endsWith(".js"))
								{
									includeJavaScript(guide, incFileName);
								}
								else
								{
									parseFile(incFileName, guide, PresName, chapter, appSettings);
								}
							}
							logger.trace("loadXML " + PresName + " include " + incFileName);
						} catch (Exception e1) {
							logger.error("loadXML " + PresName + " include Exception " + e1.getLocalizedMessage(), e1);
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
			if (!strTmpTitle.equals("") || !strTmpAuthor.equals("")) {
				guide.setTitle(strTmpTitle + ", " + strTmpAuthor);
			}
			//Clean up
			reader.close();		
			ubis.close();
			fis.close();
		} catch (Exception e) {
			logger.error("loadXML " + xmlFileName + " Exception ", e);
		}
		
		
		
	}

	private String processText(XMLStreamReader reader, String tagName) throws XMLStreamException {
		String text = "";
		ArrayList<String> tag = new ArrayList<String>();
		boolean emptyTagTest = false;
		boolean finished = false;
		int tagCount = -1;
		int eventType2 = reader.next();
		while (true) {
			switch (eventType2) {
			case XMLStreamConstants.START_ELEMENT:
				if (emptyTagTest) {
					text = text + ">";
				}
				emptyTagTest = true;
				tagCount++;
				if (tag.size() < tagCount + 1) {
					tag.add(reader.getName().toString());
				} else {
					tag.add(tagCount, reader.getName().toString());
				}
				text = text + "<" + tag.get(tagCount);
				for (int i=0; i < reader.getAttributeCount(); i++) {
					text = text + " " + reader.getAttributeName(i) + "=\"" + reader.getAttributeValue(i) + "\"";
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				if (reader.getName().getLocalPart().equals(tagName)) {
					finished = true;
				} else {
					if (emptyTagTest) {
						text = text + "/>";
					} else {
						text = text + "</"  + tag.get(tagCount) + ">";
					}
					tagCount--;
					emptyTagTest = false;
				}
				break;
			case XMLStreamConstants.CHARACTERS:
				if (emptyTagTest) {
					text = text + ">";
				}
				emptyTagTest = false;
				text = text + reader.getText();
				break;
			}
			if (finished) {
				break;
			}
			eventType2 = reader.next();
			if (eventType2 == XMLStreamConstants.END_ELEMENT) {
				if (reader.getName().getLocalPart().equals(tagName)) break;
			}
		}
		return text;
	}
	
	private void includeJavaScript(Guide guide, String filePath)
	{
    	String javascript = comonFunctions.readFile(filePath, StandardCharsets.UTF_8);
    	String globalScript = guide.getGlobaljScript();
		javascript = globalScript.concat("\r\n").concat(javascript);
		guide.setGlobaljScript(javascript);
		
	}
}
