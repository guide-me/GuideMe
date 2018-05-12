package org.guideme.guideme.settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppSettings {
	private Logger logger = LogManager.getLogger();
	private int FontSize = 20;
	private int HtmlFontSize = 20;
	private int timerFontSize = 20;
	private int buttonFontSize = 20;
	private int midiVolume = 100;
	private int midiInstrument = 76;
	private int musicVolume = 100;
	private int videoVolume = 100;
	private int mainMonitor = 1;
	private int maxImageScale = 0;
	private int jsDebugHeight = 600;
	private int jsDebugWidth = 800;
	private int thumbnailSize = 200;
	private double imgOffset = 0.99;
	
	
	private boolean Debug = false;
	private boolean JsDebug = false;
	private boolean JsDebugError = true;
	private boolean JsDebugEnter = false;
	private boolean JsDebugExit = false;
	private boolean video = false;
	private boolean webcam = false;
	private boolean hideMenu = false;
	private String DataDirectory;
	private int[] sash1Weights = new int[2];
	private int[] sash2Weights = new int[3];
	private Properties appSettingsProperties = new Properties();
	private String settingsLocation;
	private String userDir;
	private String userHome;
	private String userName;
	private String fileSeparator;
	private static AppSettings appSettings;
	private boolean fullScreen = false;
	private boolean multiMonitor = false;
	private boolean pageSound = true;
	private boolean toclipboard = false;
	private boolean monitorChanging = false;
	private boolean clock = true;
	private boolean metronome = true;
	private String ComandLineGuide = "";
	private String tempDir;
	private boolean stateInDataDir;
	private String language;
	private String country;

	public void setDisplayText(ResourceBundle displayText) {
		this.displayText = displayText;
	}

	private ResourceBundle displayText;
	

	public static synchronized AppSettings getAppSettings() {
		if (appSettings == null) {
			appSettings = new AppSettings(false);
		}
		return appSettings;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	protected  AppSettings(Boolean overrideconstructor) {
		super();
		if (!overrideconstructor) {
			Properties properties = java.lang.System.getProperties();
			userDir = String.valueOf(properties.get("user.dir"));
			userHome = String.valueOf(properties.get("user.home"));
			userName = String.valueOf(properties.get("user.name"));
			fileSeparator = String.valueOf(properties.get("file.separator"));
			settingsLocation = "data" + fileSeparator + "settings.properties";
			tempDir = "data" + fileSeparator;
			logger.debug("AppSettings userDir: " + userDir);
			logger.debug("AppSettings userHome: " + userHome);
			logger.debug("AppSettings userName: " + userName);
			logger.debug("AppSettings fileSeparator: " + fileSeparator);
			logger.debug("AppSettings settingsLocation: " + settingsLocation);
			try {
				try {
					appSettingsProperties.loadFromXML(new FileInputStream(settingsLocation));
				}
				catch (IOException ex) {
					//failed to load file so just carry on
					logger.error(ex.getLocalizedMessage(), ex);
				}
				FontSize = Integer.parseInt(appSettingsProperties.getProperty("FontSize", "20"));
				HtmlFontSize = Integer.parseInt(appSettingsProperties.getProperty("HtmlFontSize", "20"));
				timerFontSize = Integer.parseInt(appSettingsProperties.getProperty("timerFontSize", "20"));
				buttonFontSize = Integer.parseInt(appSettingsProperties.getProperty("buttonFontSize", "20"));
				midiInstrument = Integer.parseInt(appSettingsProperties.getProperty("midiInstrument", "76"));
				midiVolume = Integer.parseInt(appSettingsProperties.getProperty("midiVolume", "100"));
				musicVolume = Integer.parseInt(appSettingsProperties.getProperty("musicVolume", "100"));
				videoVolume = Integer.parseInt(appSettingsProperties.getProperty("videoVolume", "100"));
				Debug = Boolean.parseBoolean(appSettingsProperties.getProperty("Debug", "false"));
				JsDebug = Boolean.parseBoolean(appSettingsProperties.getProperty("JsDebug", "false"));
				jsDebugHeight = Integer.parseInt(appSettingsProperties.getProperty("jsDebugHeight", "600"));
				jsDebugWidth = Integer.parseInt(appSettingsProperties.getProperty("jsDebugWidth", "800"));
				video = Boolean.parseBoolean(appSettingsProperties.getProperty("Video", "true"));
				webcam = Boolean.parseBoolean(appSettingsProperties.getProperty("Webcam", "false"));
				mainMonitor = Integer.parseInt(appSettingsProperties.getProperty("mainMonitor", "1"));
				fullScreen = Boolean.parseBoolean(appSettingsProperties.getProperty("fullScreen", "false"));
				multiMonitor = Boolean.parseBoolean(appSettingsProperties.getProperty("multiMonitor", "false"));
				clock = Boolean.parseBoolean(appSettingsProperties.getProperty("clock", "true"));
				metronome = Boolean.parseBoolean(appSettingsProperties.getProperty("metronome", "true"));
				pageSound = Boolean.parseBoolean(appSettingsProperties.getProperty("pageSound", "true"));
				toclipboard = Boolean.parseBoolean(appSettingsProperties.getProperty("toclipboard", "false"));
				DataDirectory = appSettingsProperties.getProperty("DataDirectory", userDir);
				stateInDataDir = Boolean.parseBoolean(appSettingsProperties.getProperty("stateInDataDir", "true"));
				sash1Weights[0] = Integer.parseInt(appSettingsProperties.getProperty("sash1Weights0", "350"));
				sash1Weights[1] = Integer.parseInt(appSettingsProperties.getProperty("sash1Weights1", "350"));
				sash2Weights[0] = Integer.parseInt(appSettingsProperties.getProperty("sash2Weights0", "150"));
				sash2Weights[1] = Integer.parseInt(appSettingsProperties.getProperty("sash2Weights1", "700"));
				sash2Weights[2] = Integer.parseInt(appSettingsProperties.getProperty("sash2Weights2", "150"));
				maxImageScale = Integer.parseInt(appSettingsProperties.getProperty("maxImageScale", "0"));
				hideMenu = Boolean.parseBoolean(appSettingsProperties.getProperty("hideMenu", "false"));
				thumbnailSize = Integer.parseInt(appSettingsProperties.getProperty("thumbnailSize", "200"));
				imgOffset = Double.parseDouble(appSettingsProperties.getProperty("imgOffset", "0.99"));
				language = appSettingsProperties.getProperty("language", "en");
				country = appSettingsProperties.getProperty("country", "UK");
			}
			catch (Exception ex) {
				logger.error(ex.getLocalizedMessage(), ex);
			}
			saveSettings();
		}
	}

	public int getFontSize() {
		return FontSize;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public ResourceBundle getDisplayText() {
		return displayText;
	}

	public void setFontSize(int fontSize) {
		FontSize = fontSize;
	}

	public boolean getDebug() {
		return Debug;
	}

	public void setDebug(boolean debug) {
		Debug = debug;
	}

	public boolean getJsDebug() {
		return JsDebug;
	}

	public void setJsDebug(boolean jsdebug) {
		JsDebug = jsdebug;
	}

	public boolean getJsDebugError() {
		return JsDebugError;
	}

	public void setJsDebugError(boolean jsDebugError) {
		JsDebugError = jsDebugError;
	}

	public boolean getJsDebugEnter() {
		return JsDebugEnter;
	}

	public void setJsDebugEnter(boolean jsDebugEnter) {
		JsDebugEnter = jsDebugEnter;
	}

	public boolean getJsDebugExit() {
		return JsDebugExit;
	}

	public void setJsDebugExit(boolean jsDebugExit) {
		JsDebugExit = jsDebugExit;
	}

	public int getJsDebugHeight() {
		return jsDebugHeight;
	}

	public void setJsDebugHeight(int jsDebugHeight) {
		this.jsDebugHeight = jsDebugHeight;
	}

	public int getJsDebugWidth() {
		return jsDebugWidth;
	}

	public void setJsDebugWidth(int jsDebugWidth) {
		this.jsDebugWidth = jsDebugWidth;
	}

	public String getDataDirectory() {
		return DataDirectory;
	}

	public void setDataDirectory(String dataDirectory) {
		DataDirectory = dataDirectory;
	}

	public int[] getSash1Weights() {
		return sash1Weights;
	}

	public void setSash1Weights(int[] sash1Weights) {
		this.sash1Weights = sash1Weights;
	}

	public int[] getSash2Weights() {
		return sash2Weights;
	}

	public void setSash2Weights(int[] sash2Weights) {
		this.sash2Weights = sash2Weights;
	}

	public void saveSettings() {
		try {
			appSettingsProperties.setProperty("FontSize", String.valueOf(FontSize));
			appSettingsProperties.setProperty("HtmlFontSize", String.valueOf(HtmlFontSize));
			appSettingsProperties.setProperty("timerFontSize", String.valueOf(timerFontSize));
			appSettingsProperties.setProperty("buttonFontSize", String.valueOf(buttonFontSize));
			appSettingsProperties.setProperty("midiInstrument", String.valueOf(midiInstrument));
			appSettingsProperties.setProperty("midiVolume", String.valueOf(midiVolume));
			appSettingsProperties.setProperty("musicVolume", String.valueOf(musicVolume));
			appSettingsProperties.setProperty("videoVolume", String.valueOf(videoVolume));
			appSettingsProperties.setProperty("Debug", String.valueOf(Debug));
			appSettingsProperties.setProperty("JsDebug", String.valueOf(JsDebug));
			appSettingsProperties.setProperty("jsDebugHeight", String.valueOf(jsDebugHeight));
			appSettingsProperties.setProperty("jsDebugWidth", String.valueOf(jsDebugWidth));
			appSettingsProperties.setProperty("Video", String.valueOf(video));
			appSettingsProperties.setProperty("Webcam", String.valueOf(webcam));
			appSettingsProperties.setProperty("mainMonitor", String.valueOf(mainMonitor));
			appSettingsProperties.setProperty("fullScreen", String.valueOf(fullScreen));
			appSettingsProperties.setProperty("multiMonitor", String.valueOf(multiMonitor));
			appSettingsProperties.setProperty("clock", String.valueOf(clock));
			appSettingsProperties.setProperty("metronome", String.valueOf(metronome));
			appSettingsProperties.setProperty("pageSound", String.valueOf(pageSound));
			appSettingsProperties.setProperty("toclipboard", String.valueOf(toclipboard));
			appSettingsProperties.setProperty("DataDirectory", DataDirectory);
			appSettingsProperties.setProperty("stateInDataDir", String.valueOf(stateInDataDir));
			appSettingsProperties.setProperty("sash1Weights0", String.valueOf(sash1Weights[0]));
			appSettingsProperties.setProperty("sash1Weights1", String.valueOf(sash1Weights[1]));
			appSettingsProperties.setProperty("sash2Weights0", String.valueOf(sash2Weights[0]));
			appSettingsProperties.setProperty("sash2Weights1", String.valueOf(sash2Weights[1]));
			appSettingsProperties.setProperty("sash2Weights2", String.valueOf(sash2Weights[2]));
			appSettingsProperties.storeToXML(new FileOutputStream(settingsLocation), null);
			appSettingsProperties.setProperty("maxImageScale", String.valueOf(maxImageScale));
			appSettingsProperties.setProperty("hideMenu", String.valueOf(hideMenu));
			appSettingsProperties.setProperty("thumbnailSize", String.valueOf(thumbnailSize));
			appSettingsProperties.setProperty("imgOffset", String.valueOf(imgOffset));
			appSettingsProperties.setProperty("country", country);
			appSettingsProperties.setProperty("language", language);
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}

	public int getHtmlFontSize() {
		return HtmlFontSize;
	}

	public void setHtmlFontSize(int htmlFontSize) {
		HtmlFontSize = htmlFontSize;
	}

	public String getUserDir() {
		return userDir;
	}

	public String getUserHome() {
		return userHome;
	}

	public String getUserName() {
		return userName;
	}

	public String getFileSeparator() {
		return fileSeparator;
	}

	public boolean getVideoOn() {
		return video;
	}

	public void setVideoOn(boolean video) {
		this.video = video;
	}

	public boolean getWebcamOn() {
		return webcam;
	}

	public void setWebcamOn(boolean webcam) {
		this.webcam = webcam;
	}

	public int getMidiVolume() {
		return midiVolume;
	}

	public void setMidiVolume(int midiVolume) {
		this.midiVolume = midiVolume;
	}

	public int getMidiInstrument() {
		return midiInstrument;
	}

	public void setMidiInstrument(int midiInstrument) {
		this.midiInstrument = midiInstrument;
	}

	public int getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(int musicVolume) {
		this.musicVolume = musicVolume;
	}

	public int getVideoVolume() {
		return videoVolume;
	}

	public void setVideoVolume(int videoVolume) {
		this.videoVolume = videoVolume;
	}

	public boolean isFullScreen() {
		return fullScreen;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}

	public boolean isPageSound() {
		return pageSound;
	}

	public void setPageSound(boolean pageSound) {
		this.pageSound = pageSound;
	}

	public boolean isToclipboard() {
		return toclipboard;
	}

	public void setToclipboard(boolean toclipboard) {
		this.toclipboard = toclipboard;
	}

	public int getTimerFontSize() {
		return timerFontSize;
	}

	public void setTimerFontSize(int timerFontSize) {
		this.timerFontSize = timerFontSize;
	}

	public int getButtonFontSize() {
		return buttonFontSize;
	}

	public void setButtonFontSize(int buttonFontSize) {
		this.buttonFontSize = buttonFontSize;
	}

	public boolean isMultiMonitor() {
		return multiMonitor;
	}

	public void setMultiMonitor(boolean multiMonitor) {
		this.multiMonitor = multiMonitor;
	}

	public boolean isMonitorChanging() {
		return monitorChanging;
	}

	public void setMonitorChanging(boolean monitorChanging) {
		this.monitorChanging = monitorChanging;
	}

	public int getMainMonitor() {
		return mainMonitor;
	}

	public void setMainMonitor(int mainMonitor) {
		this.mainMonitor = mainMonitor;
	}

	public boolean isClock() {
		return clock;
	}

	public void setClock(boolean clock) {
		this.clock = clock;
	}

	public boolean isMetronome() {
		return metronome;
	}

	public void setMetronome(boolean metronome) {
		this.metronome = metronome;
	}

	public int getMaxImageScale() {
		return maxImageScale;
	}

	public void setMaxImageScale(int maxImageScale) {
		this.maxImageScale = maxImageScale;
	}

	public String getComandLineGuide() {
		return ComandLineGuide;
	}

	public void setComandLineGuide(String ComandLineGuide) {
		this.ComandLineGuide = ComandLineGuide;
	}

	public boolean isHideMenu() {
		return hideMenu;
	}

	public void setHideMenu(boolean hideMenu) {
		this.hideMenu = hideMenu;
	}

	public String getTempDir() {
		return tempDir;
	}

	public boolean isStateInDataDir() {
		return stateInDataDir;
	}

	public void setStateInDataDir(boolean stateInDataDir) {
		this.stateInDataDir = stateInDataDir;
	}

	public int getThumbnailSize() {
		return thumbnailSize;
	}

	public void setThumbnailSize(int thumbnailSize) {
		this.thumbnailSize = thumbnailSize;
	}

	public double getImgOffset() {
		return imgOffset;
	}

	public void setImgOffset(double imgOffset) {
		this.imgOffset = imgOffset;
	}

}
