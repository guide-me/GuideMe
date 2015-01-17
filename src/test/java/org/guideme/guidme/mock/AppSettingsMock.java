package org.guideme.guidme.mock;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guideme.guideme.settings.AppSettings;

public class AppSettingsMock extends AppSettings {
	private Logger logger = LogManager.getLogger();
	private static AppSettingsMock appSettings;
	private int FontSize = 20;
	private int HtmlFontSize = 20;
	private boolean Debug = false;
	private boolean video = true;
	private String DataDirectory;
	private int[] sash1Weights = new int[2];
	private int[] sash2Weights = new int[2];
	private String userDir;
	private String userHome;
	private String userName;
	private String fileSeparator;
	
	
	
	public static synchronized AppSettingsMock getAppSettings() {
		if (appSettings == null) {
			appSettings = new AppSettingsMock();
		}
		return appSettings;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	protected AppSettingsMock() {
		super(true);
		logger.debug("AppSettingsMock enter constructor");
		Properties properties = java.lang.System.getProperties();
		userDir = String.valueOf(properties.get("user.dir"));
		userHome = String.valueOf(properties.get("user.home"));
		userName = String.valueOf(properties.get("user.name"));
		fileSeparator = String.valueOf(properties.get("file.separator"));
		sash1Weights[0] = 619;
		sash1Weights[1] = 380;
		sash2Weights[0] = 800;
		sash2Weights[1] = 200;
		logger.debug("AppSettingsMock Exit constructor");
	}


	@Override
	public int getFontSize() {
		logger.debug("AppSettingsMock getFontSize " + FontSize);
		return FontSize;
	}

	@Override
	public void setFontSize(int fontSize) {
		logger.debug("AppSettingsMock setFontSize " + fontSize);
		this.FontSize = fontSize;
	}

	@Override
	public boolean getDebug() {
		logger.debug("AppSettingsMock getDebug " + Debug);
		return Debug;
	}

	@Override
	public void setDebug(boolean debug) {
		logger.debug("AppSettingsMock setDebug " + debug);
		Debug = debug;
	}

	@Override
	public String getDataDirectory() {
		logger.debug("AppSettingsMock getDataDirectory " + DataDirectory);
		return DataDirectory;
	}

	@Override
	public void setDataDirectory(String dataDirectory) {
		logger.debug("AppSettingsMock setDataDirectory " + dataDirectory);
		DataDirectory = dataDirectory;
	}

	@Override
	public int[] getSash1Weights() {
		logger.debug("AppSettingsMock getSash1Weights " + sash1Weights);
		return sash1Weights;
	}

	@Override
	public void setSash1Weights(int[] sash1Weights) {
		logger.debug("AppSettingsMock setSash1Weights " + sash1Weights);
		this.sash1Weights = sash1Weights;;
	}

	@Override
	public int[] getSash2Weights() {
		logger.debug("AppSettingsMock getSash2Weights " + sash2Weights);
		return sash2Weights;
	}

	@Override
	public void setSash2Weights(int[] sash2Weights) {
		logger.debug("AppSettingsMock setSash2Weights " + sash2Weights);
		this.sash2Weights = sash2Weights;
	}

	@Override
	public void saveSettings() {
		logger.debug("AppSettingsMock saveSettings");
	}

	@Override
	public int getHtmlFontSize() {
		logger.debug("AppSettingsMock getHtmlFontSize " + HtmlFontSize);
		return HtmlFontSize;
	}

	@Override
	public void setHtmlFontSize(int htmlFontSize) {
		logger.debug("AppSettingsMock setHtmlFontSize " + htmlFontSize);
		HtmlFontSize = htmlFontSize;
	}

	@Override
	public String getUserDir() {
		logger.debug("AppSettingsMock getUserDir " + userDir);
		return userDir;
	}

	@Override
	public String getUserHome() {
		logger.debug("AppSettingsMock getUserHome " + userHome);
		return userHome;
	}

	@Override
	public String getUserName() {
		logger.debug("AppSettingsMock getUserName " + userName);
		return userName;
	}

	@Override
	public String getFileSeparator() {
		logger.debug("AppSettingsMock getFileSeparator " + fileSeparator);
		return fileSeparator;
	}

	@Override
	public boolean getVideoOn() {
		logger.debug("AppSettingsMock getVideoOn " + video);
		return video;
	}

	@Override
	public void setVideoOn(boolean video) {
		logger.debug("AppSettingsMock setVideoOn " + video);
		this.video = video;
	}

}
