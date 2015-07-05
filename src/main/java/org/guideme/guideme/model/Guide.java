package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.ui.MainShell;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

/**
 * @author James
 *
 */
/**
 * @author James
 *
 */
public class Guide {
	private String title;
	private String authorName;
	private List<String> keywords = new ArrayList<String>();
	private String description;
	private String originalUrl;
	private String authorUrl;
	private Image thumbnail;
	private HashMap<String, Chapter> chapters = new HashMap<String, Chapter>();
	
	private String mediaDirectory; //Media subdirectory for current xml file
	private String delStyle; //style for currently running delay
	private String delTarget; //target for currently running delay
	private ArrayList<String> flags = new ArrayList<String>(); //current flags
	private Boolean autoSetPage;
	private String delaySet; //flags to set for currently running delay
	private String delayUnSet; //flags to clear for currently running delay
	private int delStartAtOffSet; //offset for currently running delay
	private String delayScriptVar;
	private String id; //name for current xml that is running
	private GuideSettings settings = new GuideSettings("startup"); //state for the currently running xml
	private String jScript;
	private String delayjScript;
	private String globaljScript;
	private static Guide guide;
	private String css; // css style sheet
	private Boolean inPrefGuide;
	private Scriptable scope;
	private static Logger logger = LogManager.getLogger();
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	private MainShell mainshell;

	private Guide() {
		ContextFactory cntxFact = new ContextFactory();
		Context cntx = cntxFact.enterContext();
		scope = cntx.initStandardObjects();

	}

	public static synchronized Guide getGuide() {
		if (guide == null) {
			AppSettings appSettings = AppSettings.getAppSettings();
			guide = new Guide();
			HashMap<String, Chapter> chapters = guide.getChapters();
			Chapter chapter = new Chapter("default");
			chapters.put("default", chapter);
			Page page404 = new Page("GuideMe404Error","", "", "", "", false, "", "");
			chapter.getPages().put(page404.getId(), page404);
			Page start = new Page("start","", "", "", "", false, "", "");
			String appImage = appSettings.getUserDir().replace("\\", "\\\\") + appSettings.getFileSeparator() + appSettings.getFileSeparator() + "userSettings" + appSettings.getFileSeparator() + appSettings.getFileSeparator() + "GuidemeBeta.jpg";
			String strLoadScript = "function pageLoad() {";
			strLoadScript = strLoadScript + "	var lefthtml = \"<!DOCTYPE html>\";";
			strLoadScript = strLoadScript + "	lefthtml = lefthtml + \"<html>\";";
			strLoadScript = strLoadScript + "	lefthtml = lefthtml + \"<head>\";";
			strLoadScript = strLoadScript + "	lefthtml = lefthtml + \"<meta http-equiv='Content-type' content='text/html;charset=UTF-8' />\";";
			strLoadScript = strLoadScript + "	lefthtml = lefthtml + \"<title>Guideme - Explore Yourself</title><style type='text/css'> html { overflow-y: auto; } body { color: white; background-color: black; font-family: Tahoma; font-size:16px } html, body, #wrapper { height:100%; width: 100%; margin: 0; padding: 0; border: 0; } #wrapper { vertical-align: middle; text-align: center; } #bannerimg { width: 90%; border-top: 3px solid #cccccc; border-right: 3px solid #cccccc; border-bottom: 3px solid #666666; border-left: 3px solid #666666; }</style>\";";
			strLoadScript = strLoadScript + "	lefthtml = lefthtml + \"</head>\";";
			strLoadScript = strLoadScript + "	lefthtml = lefthtml + \"<body>\";";
			strLoadScript = strLoadScript + "	lefthtml = lefthtml + \"<div id='wrapper' ><div id='bannerimg'><img src='" + appImage + "' /></div><div><h2>Welcome to Guideme!</h2>To get started, click File/Load and select a guide.</div></div>\";";
			strLoadScript = strLoadScript + "	lefthtml = lefthtml + \"</body>\";";
			strLoadScript = strLoadScript + "	lefthtml = lefthtml + \"</html>\";";
			strLoadScript = strLoadScript + "	overRide.leftHtml = lefthtml;";
			strLoadScript = strLoadScript + "}";
			start.setjScript(strLoadScript);
			guide.globaljScript = "";
			guide.inPrefGuide = false;
			guide.css = "";
			guide.autoSetPage = false;
			chapter.getPages().put(start.getId(), start);
			guide.setMediaDirectory(appSettings.getUserDir() + appSettings.getFileSeparator() + "userSettings" + appSettings.getFileSeparator());
		}
		return guide;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public void setMainshell(MainShell mainshell) {
		this.mainshell = mainshell;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public Collection<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(Collection<String> keywords) {
		this.keywords.clear();
		this.keywords.addAll(keywords);
	}
	
	public void setKeywords(String... keywords) {
		setKeywords(Arrays.asList(keywords));
	}
	public void setKeywordsString(String keywords) {
		this.keywords.clear();
		String[] tmp = keywords.split(",");
		for (int i = 0; i < tmp.length; i++) {
			this.keywords.add(tmp[i].trim());
		}
	}
	public String getKeywordsString() {
		String tmp = this.keywords.toString();
		return tmp.substring(1, tmp.length()-1);
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	
	public String getAuthorUrl() {
		return authorUrl;
	}
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}
	
	public Image getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public HashMap<String, Chapter> getChapters() {
		return chapters;
	}
	
	public void setChapters(HashMap<String, Chapter> chapters) {
		this.chapters = chapters;
	}
	
	/**
	 * Returns the media directory for the guide
	 * 
	 * @return Media Directory
	 */
	public String getMediaDirectory() {
		return mediaDirectory;
	}

	public void setMediaDirectory(String mediaDirectory) {
		this.mediaDirectory = mediaDirectory;
	}

	public String getDelStyle() {
		return delStyle;
	}

	public void setDelStyle(String delStyle) {
		this.delStyle = delStyle;
	}

	public String getDelTarget() {
		return delTarget;
	}

	public void setDelTarget(String delTarget) {
		this.delTarget = delTarget;
	}

	public ArrayList<String> getFlags() {
		return flags;
	}

	public void setFlags(ArrayList<String> flags) {
		this.flags = flags;
	}

	public Boolean getAutoSetPage() {
		return autoSetPage;
	}

	public void setAutoSetPage(Boolean autoSetPage) {
		this.autoSetPage = autoSetPage;
	}

	public String getDelaySet() {
		return delaySet;
	}

	public void setDelaySet(String delaySet) {
		this.delaySet = delaySet;
	}

	public String getDelayUnSet() {
		return delayUnSet;
	}

	public void setDelayUnSet(String delayUnSet) {
		this.delayUnSet = delayUnSet;
	}

	public int getDelStartAtOffSet() {
		return delStartAtOffSet;
	}

	public void setDelStartAtOffSet(int delStartAtOffSet) {
		this.delStartAtOffSet = delStartAtOffSet;
	}

	public String getId() {
		return id;
	}

	//we are loading a new xml so clear old settings
	public void reset(String id) {
		logger.trace("Guide reset id: " + id);
		try {
			this.id = id;
			settings = new GuideSettings(id);
			mediaDirectory = "";
			delStyle = "";
			delTarget = "";
			flags = new ArrayList<String>();
			autoSetPage = true;
			delaySet = "";
			delayUnSet = "";
			title = "";
			chapters = new HashMap<String, Chapter>(); 
			delStartAtOffSet = 0;
			jScript = "";
			css = "";
			inPrefGuide = false;
			globaljScript = "";
		} catch (Exception e) {
			logger.error("Guide reset " + e.getLocalizedMessage(), e);
		}
	}

	public GuideSettings getSettings() {
		return settings;
	}
	
	public String getjScript() {
		return jScript;
	}
	
	public void setjScript(String jScript) {
		this.jScript = jScript;
	}
	
	public void setSettings(GuideSettings settings) {
		this.settings = settings;
	}

	public String getDelayjScript() {
		return delayjScript;
	}

	public void setDelayjScript(String delayjScript) {
		this.delayjScript = delayjScript;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		String mediaPath;
		AppSettings appSettings = AppSettings.getAppSettings();
		mediaPath = comonFunctions.getMediaFullPath("", appSettings.getFileSeparator(), appSettings, guide);
		mediaPath = mediaPath.replace("\\", "/");
		//mediaPath = "file:///" + mediaPath;
		css = css.replace("\\MediaDir\\", mediaPath);
		this.css = css;
	}

	public Boolean getInPrefGuide() {
		return inPrefGuide;
	}

	public void setInPrefGuide(Boolean inPrefGuide) {
		this.inPrefGuide = inPrefGuide;
	}

	public String getGlobaljScript() {
		return globaljScript;
	}

	public void setGlobaljScript(String globaljScript) {
		this.globaljScript = globaljScript;
	}

	public Scriptable getScope() {
		return scope;
	}

	// Guide Setting Wrapper FOR JSCRIPT
	
	
	/**
	 * Gets a text guide preference  
	 * 
	 * @param key Name of the preference
	 * @return value (Text)
	 */
	public String getPref(String key) {
		return settings.getPref(key);
	}
	
	/**
	 * Sets an existing text guide preference
	 * 
	 * @param key Name of the preference
	 */
	public void setPref(String key, String value) {
		settings.setPref(key, value);
	}
	
	/**
	 * Adds a new text guide preference
	 * 
	 * @param key Name of the preference
	 * @param value Default value (Text) 
	 * @param screenDesc Text displayed on the screen
	 */
	public void addPref(String key, String value, String screenDesc, int sortOrder) {
		settings.addPref(key, value, screenDesc, sortOrder);
	}
	
	/**
	 * Gets a true / false guide preference  
	 * 
	 * @param key Name of the preference
	 * @return value (true / false)
	 */
	public Boolean isPref(String key) {
		return settings.isPref(key);
	}
	
	/**
	 * Sets an existing true / false guide preference
	 * 
	 * @param key Name of the preference
	 * @return value new value (true / false)
	 */
	public void setPref(String key, Boolean value) {
		settings.setPref(key, value);
	}
	
	/**
	 * Adds a new true / false guide preference
	 * 
	 * @param key Name of the preference
	 * @param value Default value (true / false) 
	 * @param screenDesc Text displayed on the screen
	 */
	public void addPref(String key, Boolean value, String screenDesc, int sortOrder) {
		settings.addPref(key, value, screenDesc, sortOrder);
	}
	
	/**
	 * Gets a numeric guide preference  
	 * 
	 * @param key Name of the preference
	 * @return value (Number)
	 */
	public Double getPrefNumber(String key) {
		return settings.getPrefNumber(key);
	}
	
	/**
	 * Sets an existing numeric guide preference
	 * 
	 * @param key Name of the preference
	 * @return value new value (Number)
	 */
	public void setPref(String key, Double value) {
		settings.setPref(key, value);
	}
	
	/**
	 * Adds a new numeric guide preference
	 * 
	 * @param key Name of the preference
	 * @param value Default value (Number) 
	 * @param screenDesc Text displayed on the screen
	 */
	public void addPref(String key, Double value, String screenDesc, int sortOrder) {
		settings.addPref(key, value, screenDesc, sortOrder);
	}
	
	/**
	 * Gets the value of an html form field from either the left or right pane
	 * 
	 * @param key Name of the html input field in the form
	 * @return value entered in the field
	 */
	public String getFormField(String key) {
		return settings.getFormField(key);
	}
	
	/**
	 * Returns the current page name
	 * 
	 * @return The name of the current page
	 */
	public String getCurrPage() {
		return settings.getCurrPage();
	}
	
	/**
	 * Returns the previous page name
	 * 
	 * @return Then name of the page you just came from
	 */
	public String getPrevPage() {
		return settings.getPrevPage();
	}
	
	// comonFunctions wrapper for javascript
	/**
	 * @param IifSet
	 * @param IifNotSet
	 * @return
	 */
	public boolean canShow(String IifSet, String IifNotSet) {
		return comonFunctions.canShow(flags, IifSet, IifNotSet);
	}
	
	/**
	 * @param flagNames
	 */
	public void setFlags(String flagNames) {
		comonFunctions.SetFlags(flagNames, flags);
	}
	
	/**
	 * @param flagNames
	 */
	public void unsetFlags(String flagNames) {
		comonFunctions.UnsetFlags(flagNames, flags);
	}
	
	/**
	 * @param flagNames
	 * @return
	 */
	public boolean isSet(String flagNames) {
		return comonFunctions.isSet(flagNames, flags);
	}
	
	/**
	 * @param flagNames
	 * @return
	 */
	public boolean isNotSet(String flagNames) {
		return comonFunctions.isNotSet(flagNames, flags);
	}
	
	/**
	 * @param type
	 * @param jsdate1
	 * @param jsdate2
	 * @return
	 */
	public long dateDifference(String type, Object jsdate1, Object jsdate2) {
		return comonFunctions.dateDifference(type, jsdate1, jsdate2);
	}
	
	/**
	 * @param random
	 * @return
	 */
	public int getRandom(String random) {
		return comonFunctions.getRandom(random);
	}

	/**
	 * @param iTime
	 * @return
	 */
	public int getMilisecFromTime(String iTime) {
		return comonFunctions.getMilisecFromTime(iTime);
	}
	
	/**
	 * @return
	 */
	public String getVersion() {
		return ComonFunctions.getVersion();
	}
	
	/**
	 * @param FolderName
	 * @return
	 */
	public String listFiles(String FolderName) {
		return comonFunctions.ListFiles(FolderName);
	}

	/**
	 * @param FolderName
	 * @return
	 */
	public String listSubFolders(String FolderName) {
		return comonFunctions.ListSubFolders(FolderName);
	}

	/**
	 * @param wildcard
	 * @param strSubDir
	 * @return
	 */
	public String getRandomFile(String wildcard, String strSubDir) {
		return comonFunctions.GetRandomFile(wildcard, strSubDir);
	}

	/**
	 * @param fileName
	 * @return
	 */
	public Boolean fileExists(String fileName) {
		return comonFunctions.fileExists(fileName);
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public Boolean directoryExists(String fileName) {
		return comonFunctions.directoryExists(fileName);
	}

	/* main shell functions to update screen directly from javascript */
	
	/**
	 * @param lblLeft
	 */
	public void setClockText(String lblLeft) {
		mainshell.setLblLeft(lblLeft);
	}
	
	/**
	 * @param lblCentre
	 */
	public void setTitleText(String lblCentre) {
		mainshell.setLblCentre(lblCentre);
	}
	
	/**
	 * @param lblRight
	 */
	public void setTimerText(String lblRight) {
		mainshell.setLblRight(lblRight);
	}
	
	/**
	 * @param leftHtml
	 */
	public void setLeftHtml(String leftHtml) {
		mainshell.setLeftHtml(leftHtml);
	}
	

	/**
	 * @param leftBody
	 * @param overRideStyle
	 */
	public void setLeftBody(String leftBody, String overRideStyle) {
		mainshell.setLeftText(leftBody, overRideStyle);
	}

	/**
	 * 
	 */
	public void clearImage() {
		mainshell.clearImage();
	}
	
	/**
	 * @param brwsText
	 * @param overRideStyle
	 */
	public void setRightHtml(String brwsText, String overRideStyle) {
		mainshell.setBrwsText(brwsText, overRideStyle);
	}
	
	/**
	 * @param brwsText
	 * @param overRideStyle
	 */
	public void setRightBody(String brwsText, String overRideStyle) {
		mainshell.setBrwsText(brwsText, overRideStyle);
	}
	
	/**
	 * @param path
	 * @return
	 */
	public String jsReadFile(String path) {
		return comonFunctions.jsReadFile(path);
	}
	
	/**
	 * @param fileName
	 * @param encoding
	 * @return
	 */
	public String jsReadFile(String fileName, String encoding) {
		return comonFunctions.jsReadFile(fileName, encoding);
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public String[] jsReadFileArray(String fileName) {
		return comonFunctions.jsReadFileArray(fileName);
	}

	/**
	 * @param fileName
	 * @param encoding
	 * @return
	 */
	public String[] jsReadFileArray(String fileName, String encoding) {
		return comonFunctions.jsReadFileArray(fileName, encoding);
	}
	
	public void enableButton(String id) {
		mainshell.enableButton(id);
	}

	public void disableButton(String id) {
		mainshell.disableButton(id);
	}

	/**
	 * Adds a timer to change various aspects of the screen / run a javascript function
	 * 
	 * @param delay time in seconds before the timer triggers
	 * @param jScript javascript function to run when the timer triggers
	 * @param imageId image to change to when the timer triggers
	 * @param text html text to set the right html pane when the timer triggers
	 * @param set flags to set when the timer triggers
	 * @param unSet flags to clear when the timer triggers
	 * @param id identifier to manipulate the timer later 
	 */
	public void addTimer(String delay, String jScript, String imageId, String text, String set, String unSet, String id) {
		Timer timer = new Timer(delay, jScript, imageId, text, "", "", set, unSet, "", "", id);
		Calendar timCountDown = Calendar.getInstance();
		timCountDown.add(Calendar.SECOND, timer.getTimerSec());
		timer.setTimerEnd(timCountDown);
		mainshell.addTimer(timer);		
	}

	/**
	 * Reset the count on a timer
	 * 
	 * @param id id used to create the timer
	 * @param delay time in seconds before the timer triggers
	 */
	public void resetTimer(String id, String delay) {
		mainshell.resetTimer(id, comonFunctions.getRandom(delay));
	}
	
	/**
	 * Write to the java script console in the debug window
	 * 
	 * This also gets called by the jscriptLog function which will write to the log and the console
	 * @param logText
	 */
	public void updateJConsole(String logText) {
		mainshell.updateJConsole(logText);
	}
	
	public void refreshVars() {
		mainshell.refreshVars();
	}

	public String getDelayScriptVar() {
		return delayScriptVar;
	}

	public void setDelayScriptVar(String delayScriptVar) {
		this.delayScriptVar = delayScriptVar;
	}

	/**
	 * Checks to see if a page exists within a chapter
	 * 
	 * @param chapter
	 * @param page
	 * @return
	 */
	public boolean pageExists(String chapter, String page) {
		try {
			return chapters.get(chapter).getPages().containsKey(page);
		}
		catch (Exception ex) {
			return false;
		}		
	}
	
	/**
	 * Checks to see if the pages exists within the default chapter
	 * 
	 * @param page
	 * @return
	 */
	public boolean pageExists(String page) {
		return pageExists("default", page);
	}
	
	
}
