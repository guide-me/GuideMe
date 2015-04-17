package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guideme.guideme.MainLogic;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.GuideSettings;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

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
			Page page404 = new Page("GuideMe404Error","", "", "", "", false);
			chapter.getPages().put(page404.getId(), page404);
			Page start = new Page("start","", "", "", "", false);
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
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
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
		MainLogic mainLogic = MainLogic.getMainLogic();
		mediaPath = mainLogic.getMediaFullPath("", appSettings.getFileSeparator(), appSettings, guide);
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

}
