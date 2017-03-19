package org.guideme.guideme.settings;

import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Library;
import org.imgscalr.Scalr;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ComonFunctions{
	/**
	 * 
	 */
	private SecureRandom mRandom = new SecureRandom();
	private static Logger logger = LogManager.getLogger();
    private XPathFactory factory = XPathFactory.newInstance();
    private XPath xpath = factory.newXPath();
    private static final String version = "0.3.0c";
    private osFamily os;
    public static enum osFamily {Windows, Mac, Unix, Unknown};

	private static ComonFunctions comonFunctions;
	private Display display;

	private ComonFunctions() {
		os = getOS();
	}
	public static synchronized ComonFunctions getComonFunctions() {
		if (comonFunctions == null) {
			comonFunctions = new ComonFunctions();
		}
		return comonFunctions;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

   private osFamily getOS() {
	   osFamily osval = osFamily.Unknown;
	   String OS = System.getProperty("os.name").toLowerCase();
	   if (OS.indexOf("win") >= 0) {
		   osval = osFamily.Windows;
	   } else if (OS.indexOf("mac") >= 0) {
		   osval = osFamily.Mac;
	   } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ){
		   osval = osFamily.Unix;
	   }
	   return osval;
   }
    
    //checks to see if the flags match
    public boolean canShow(ArrayList<String> setList, String IifSet, String IifNotSet) {
    	boolean icanShow = false;
    	boolean blnSet = true;
    	boolean blnNotSet = true;

    	try {
    		if (!IifSet.trim().equals("")) {
    			blnSet = MatchesIfSetCondition(IifSet.trim(), setList);
    		}
    		if (!IifNotSet.trim().equals("")) {
    			blnNotSet = MatchesIfNotSetCondition(IifNotSet.trim(), setList);
    		}
    		if (blnSet && blnNotSet) {
    			icanShow = true;
    		} else {
    			icanShow = false;
    		}
    	}
    	catch (Exception ex){
    		logger.error(ex.getLocalizedMessage(), ex);
    	}
    	return icanShow;
    }
    
    public boolean canShowTime(LocalTime ifBefore, LocalTime ifAfter) {
    	boolean before = true;
    	boolean after = true;
    	boolean show = false;
    	LocalTime now = LocalTime.now();
    	if (ifBefore != null) {
    		if (ifBefore.isBefore(now)) {
    			before = false;
    		}
    	}
    	if (ifAfter != null) {
    		if (ifAfter.isAfter(now)) {
    			after = false;
    		}
    	}
    	
    	if (before && after) {
    		show = true;
    	} else {
    		show = false;
    	}
    	return show;
    }

    
    // Overloaded function checks pages as well 
    public boolean canShow(ArrayList<String> setList, String IifSet, String IifNotSet, String IPageId) {
    	boolean icanShow = false;
    	try {
    		icanShow = canShow(setList, IifSet, IifNotSet);
    		if (icanShow) {
    			if (IPageId.equals("")) {
    				icanShow = true;
    			} else {
    				icanShow = MatchesIfNotSetCondition(IPageId, setList);
    			}
    		}
    	}
    	catch (Exception ex){
    		logger.error(ex.getLocalizedMessage(), ex);
    	}
    	return icanShow;
	}

    public boolean isSet(String condition, ArrayList<String> setList) {
    	return MatchesIfSetCondition(condition, setList);
    }
    
    public boolean isNotSet(String condition, ArrayList<String> setList) {
    	return MatchesIfNotSetCondition(condition, setList);
    }
    
    //checks list of flags to see if they match
	private boolean MatchesIfSetCondition(String condition, ArrayList<String> setList) {
		boolean blnReturn = false;
		boolean blnAnd = false;
		boolean blnOr = false;
		String[] conditions;

		try {
			if (condition.indexOf("|") > -1) {
				blnOr = true;
				condition = condition.replace("|", ",");
				conditions = condition.split(",", -1);
				for (int i = 0; i < conditions.length; i++) {
					if (setList.contains(conditions[i].trim())) {
						blnReturn = true;
						break;
					}
				}
			}

			if (condition.indexOf("+") > -1) {
				blnAnd = true;
				blnReturn = true;
				condition = condition.replace("+", ",");
				conditions = condition.split(",", -1);
				for (int i = 0; i < conditions.length; i++) {
					if (!setList.contains(conditions[i].trim())) {
						blnReturn = false;
						break;
					}
				}
			}

			if (!blnAnd && !blnOr) {
				blnReturn = setList.contains(condition);
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}

		return blnReturn;
	}
	
	//checks a list of flags to make sure they don't match
	private boolean MatchesIfNotSetCondition(String condition, ArrayList<String> setList) {
		boolean blnReturn = false;
		boolean blnAnd = false;
		boolean blnOr = false;
		String[] conditions;

		try {
			if (condition.indexOf("+") > -1) {
				blnAnd = true;
				blnReturn = true;
				condition = condition.replace("+", ",");
				conditions = condition.split(",", -1);
				for (int i = 0; i < conditions.length; i++) {
					if (setList.contains(conditions[i].trim())) {
						blnReturn = false;
						break;
					}
				}
			}

			if (condition.indexOf("|") > -1) {
				blnOr = true;
				condition = condition.replace("|", ",");
				conditions = condition.split(",", -1);
				for (int i = 0; i < conditions.length; i++) {
					if (!setList.contains(conditions[i].trim())) {
						blnReturn = true;
						break;
					}
				}
			}

			if (!blnAnd && !blnOr) {
				blnReturn = !setList.contains(condition);
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}

		return blnReturn;
	}

	// functions to handle set flags go here
	public void SetFlags(String flagNames, ArrayList<String> setList) {
		String[] flags;
		try {
			flags = flagNames.split(",", -1);
			for (int i = 0; i < flags.length; i++) {
				if (!flags[i].trim().equals("")) {
					if (!setList.contains(flags[i].trim())) {
						setList.add(flags[i].trim());
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(),e);
		}
	}

	public String GetFlags(ArrayList<String> setList) {
		String strFlags = "";
		try {
			for (int i = 0; i < setList.size(); i++) {
				if (i==0) {
					strFlags = setList.get(i);
				} else {
					strFlags = strFlags + "," + setList.get(i);
				}
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(),e);
		}
		return strFlags;
	}

	public void UnsetFlags(String flagNames, ArrayList<String> setList) {
		String[] flags;
		try {
			flags = flagNames.split(",", -1);
			for (int i = 0; i < flags.length; i++) {
				if (!flags[i].trim().equals("")) {
					if (setList.contains(flags[i].trim())) {
						setList.remove(flags[i].trim());
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(),e);
		}
	}
	
	//Get random number between x and y where Random is (x..y)
	//if just a number is passed in it returns that number
	//this is so we can just pass the parameter in for things like delays where 
	//"15" would be a delay of 15 seconds but "(5..15)" would be a random delay between 5 and 15 seconds 
	public int getRandom(String random) {
		int intRandom = 0;
		int intPos1;
		int intPos2;
		int intPos3;
		int intMin;
		int intMax;
		String strMin;
		String strMax;
		
		try {
			intPos1 = random.indexOf("(");
			if (intPos1 > -1) {
				intPos2 = random.indexOf("..", intPos1);
				if (intPos2 > -1) {
					intPos3 = random.indexOf(")", intPos2);
					if (intPos3 > -1) {
						strMin = random.substring(intPos1 + 1, intPos2);
						intMin = Integer.parseInt(strMin);
						strMax = random.substring(intPos2 + 2, intPos3);
						intMax = Integer.parseInt(strMax);
						int i1 = mRandom.nextInt(intMax - intMin + 1) + intMin;
						intRandom = i1;
					}
				}
			} else {
				intRandom = Integer.parseInt(random);
			}
		} catch (NumberFormatException en) {
			intRandom = 0;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(),e);
		}
		
		return intRandom;
	}
	
	//gets a random number between intMin and intMax inclusive
	public int getRandom(int intMin, int intMax)
	{
		return mRandom.nextInt(intMax - intMin + 1) + intMin;
	}
	
	//gets a random number between 1 and intMax inclusive
	public int getRandom(int intMax)
	{
		return mRandom.nextInt(intMax - 1 + 1) + 1;
	}
	
	public int getMilisecFromTime(String iTime) {
		int intPos1;
		int intPos2;
		String strHour;
		String strMinute;
		String strSecond;
		int intTime = 0;
		
		try {
			intPos1 = iTime.indexOf(":");
			if (intPos1 > -1) {
				intPos2 = iTime.indexOf(":", intPos1 + 1);
				if (intPos2 > -1) {
					strHour = iTime.substring(0, intPos1);
					strMinute = iTime.substring(intPos1 + 1, intPos2);
					strSecond = iTime.substring(intPos2 + 1, iTime.length());
					intTime = Integer.parseInt(strSecond) * 1000;
					intTime = intTime + Integer.parseInt(strMinute) * 1000 * 60;
					intTime = intTime + Integer.parseInt(strHour) * 1000 * 60 * 60;
				}
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(),e);
		}
		return intTime;
	}

	public Element getOrAddElement(String xPath, String nodeName, Element parent, Document doc) {
		//xml helper function
		try {
			Element elToSet = getElement(xPath, parent);
			if (elToSet == null) {
				elToSet = addElement(nodeName, parent, doc);
			}
			return elToSet;
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(),ex);
			return null;
		}
	}
	
	public Element getElement(String xPath, Element parent) {
		//xml helper function
		try {
			XPathExpression expr = xpath.compile(xPath);
			Object Xpathresult = expr.evaluate(parent, XPathConstants.NODESET);
			NodeList nodes = (NodeList) Xpathresult;
			Element elToSet = null;
			if (nodes.getLength() > 0) {
				elToSet = (Element) nodes.item(0);
			}
			return elToSet;
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(),ex);
			return null;
		}
	}
	
	public Element addElement(String nodeName, Element parentNode, Document doc) {
		//xml helper function
		try {
			Element elToSet;
			elToSet = doc.createElement(nodeName);
			parentNode.appendChild(elToSet);
			return elToSet;
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(),ex);
			return null;
		}
	}

	public CDATASection addCdata(String cdataContent, Element parentNode, Document doc) {
		CDATASection newNode;
		newNode = doc.createCDATASection(cdataContent);
		parentNode.appendChild(newNode);
		return newNode;
	}
	
	public String readFile(String path, Charset encoding) {
		//returns the contents of a file as a String
		String returnVal = "";
		try {
			Thread.interrupted();
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			returnVal =  encoding.decode(ByteBuffer.wrap(encoded)).toString();
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(),ex);
		}
		return returnVal;
	}

	public String jsReadFile(String path) {
		return jsReadFile(path, "UTF-8");
	}

	public String jsReadFile(String fileName, String encoding) {
		AppSettings appSettings = AppSettings.getAppSettings();
		Guide guide = Guide.getGuide(); 
		String fileSeparator = appSettings.getFileSeparator();
		
		String dataDirectory;
		String prefix = "";
		dataDirectory = appSettings.getDataDirectory();
		if (dataDirectory.startsWith("/")) {
			prefix = "/";
		}
		dataDirectory = prefix + fixSeparator(appSettings.getDataDirectory(), fileSeparator);
		String mediaDirectory = fixSeparator(guide.getMediaDirectory(), fileSeparator);
		dataDirectory = dataDirectory + fileSeparator + mediaDirectory;
		
		
		String media = fixSeparator(fileName, fileSeparator);
		logger.debug("CommonFunctions fileExists getMediaFullPath " + media);
		int intSubDir = media.lastIndexOf(fileSeparator);
		String strSubDir;
		if (intSubDir > -1) {
			strSubDir = fixSeparator(media.substring(0, intSubDir + 1), fileSeparator);
			media = media.substring(intSubDir + 1);
		} else {
			strSubDir = "";
		}
		// String strSubDir
		// no wildcard so just use the file name
		if (strSubDir.equals("")) {
			fileName = dataDirectory + fileSeparator + media;
		} else {
			fileName = dataDirectory + fileSeparator + strSubDir + fileSeparator + media;
		}

		Charset encodeSet;
		switch (encoding) {
		case "ISO_8859_1":
			encodeSet = StandardCharsets.ISO_8859_1;
			break;
		case "US_ASCII":
			encodeSet = StandardCharsets.US_ASCII;
			break;
		case "UTF_16":
			encodeSet = StandardCharsets.UTF_16;
			break;
		case "UTF_16BE":
			encodeSet = StandardCharsets.UTF_16BE;
			break;
		case "UTF_16LE":
			encodeSet = StandardCharsets.UTF_16LE;
			break;
		default:
			encodeSet = StandardCharsets.UTF_8;
			break;

		}
		String fileToReturn = "";
		
		try {
			fileToReturn = readFile(fileName, encodeSet);
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(),ex);
		}
		
		return fileToReturn;
	}
	
	public String[] jsReadFileArray(String fileName, String encoding) {
		String[] retrn = null;
		AppSettings appSettings = AppSettings.getAppSettings();
		Guide guide = Guide.getGuide(); 
		String fileSeparator = appSettings.getFileSeparator();
		
		String dataDirectory;
		String prefix = "";
		dataDirectory = appSettings.getDataDirectory();
		if (dataDirectory.startsWith("/")) {
			prefix = "/";
		}
		dataDirectory = prefix + fixSeparator(appSettings.getDataDirectory(), fileSeparator);
		String mediaDirectory = fixSeparator(guide.getMediaDirectory(), fileSeparator);
		dataDirectory = dataDirectory + fileSeparator + mediaDirectory;
		
		
		String media = fixSeparator(fileName, fileSeparator);
		logger.debug("CommonFunctions fileExists getMediaFullPath " + media);
		int intSubDir = media.lastIndexOf(fileSeparator);
		String strSubDir;
		if (intSubDir > -1) {
			strSubDir = fixSeparator(media.substring(0, intSubDir + 1), fileSeparator);
			media = media.substring(intSubDir + 1);
		} else {
			strSubDir = "";
		}
		// String strSubDir
		// no wildcard so just use the file name
		if (strSubDir.equals("")) {
			fileName = dataDirectory + fileSeparator + media;
		} else {
			fileName = dataDirectory + fileSeparator + strSubDir + fileSeparator + media;
		}

		Charset encodeSet;
		switch (encoding) {
		case "ISO_8859_1":
			encodeSet = StandardCharsets.ISO_8859_1;
			break;
		case "US_ASCII":
			encodeSet = StandardCharsets.US_ASCII;
			break;
		case "UTF_16":
			encodeSet = StandardCharsets.UTF_16;
			break;
		case "UTF_16BE":
			encodeSet = StandardCharsets.UTF_16BE;
			break;
		case "UTF_16LE":
			encodeSet = StandardCharsets.UTF_16LE;
			break;
		default:
			encodeSet = StandardCharsets.UTF_8;
			break;

		}
		
		try {
			Path filePath = new File(fileName).toPath();
			List<String> stringList = Files.readAllLines(filePath, encodeSet);
			retrn = stringList.toArray(new String[]{});
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(),ex);
		}		
		return retrn;
	}

	public String[] jsReadFileArray(String fileName) {
		return jsReadFileArray(fileName, "UTF-8");
	}

	
	
	
	public String fixSeparator(String path, String fileSeparator) {
		String retrn = path;
		retrn = retrn.replace("\\", fileSeparator);
		retrn = retrn.replace("/", fileSeparator);
		if (retrn.startsWith(fileSeparator)) {
			retrn = retrn.substring(1, retrn.length());
		}
		if (retrn.endsWith(fileSeparator)) {
			retrn = retrn.substring(0, retrn.length() - 1);
		}
		return retrn;
	}
	
	public Boolean fileExists(String fileName) {
		return fileExists(fileName, false);
	}

	public Boolean directoryExists(String fileName) {
		return fileExists(fileName, true);
	}

	public Boolean fileExists(String fileName, boolean directory) {
		AppSettings appSettings = AppSettings.getAppSettings();
		Guide guide = Guide.getGuide(); 
		String fileSeparator = appSettings.getFileSeparator();
		
		String dataDirectory;
		String prefix = "";
		dataDirectory = appSettings.getDataDirectory();
		if (dataDirectory.startsWith("/")) {
			prefix = "/";
		}
		dataDirectory = prefix + fixSeparator(appSettings.getDataDirectory(), fileSeparator);
		String mediaDirectory = fixSeparator(guide.getMediaDirectory(), fileSeparator);
		dataDirectory = dataDirectory + fileSeparator + mediaDirectory;
		
		
		String media = fixSeparator(fileName, fileSeparator);
		logger.debug("CommonFunctions fileExists getMediaFullPath " + media);
		int intSubDir = media.lastIndexOf(fileSeparator);
		String strSubDir;
		if (intSubDir > -1) {
			strSubDir = fixSeparator(media.substring(0, intSubDir + 1), fileSeparator);
			media = media.substring(intSubDir + 1);
		} else {
			strSubDir = "";
		}
		// String strSubDir
		// no wildcard so just use the file name
		if (strSubDir.equals("")) {
			fileName = dataDirectory + fileSeparator + media;
		} else {
			fileName = dataDirectory + fileSeparator + strSubDir + fileSeparator + media;
		}
		File f = new File(fileName);
		Boolean fileexists = false;
		if (f.exists()) {
			if (f.isFile()  && !directory ) {
				fileexists = true;
			}
			if (f.isDirectory()  && directory ) {
				fileexists = true;
			}
		}
		logger.debug("ComonFunctions FileExists check " + fileName + " " + fileexists);
		return fileexists;
	}

	public static String getVersion() {
		return version;
	}
	
	public String getVarAsString(Object objPassed) {
		String returnVal = "";
		try {
			if (objPassed == null){
				returnVal = "null";
			} else {
				if (objPassed instanceof String) {
					returnVal = (String) objPassed;
				}
				if (objPassed instanceof Integer) {
					returnVal = String.valueOf(objPassed);
				}
				if (objPassed instanceof Double) {
					returnVal = String.valueOf(objPassed);
				}
				if (objPassed instanceof Float) {
					returnVal = String.valueOf(objPassed);
				}
				if (objPassed instanceof Long) {
					returnVal = String.valueOf(objPassed);
				}
				if (objPassed instanceof Boolean) {
					returnVal = String.valueOf(objPassed);
				}
				if (objPassed instanceof NativeArray) {
					NativeArray objArray = (NativeArray) objPassed;
					if (objArray.getLength() > 0) {
						returnVal = "[";
						for (int i=0; i < objArray.getLength(); i++) {
							returnVal = returnVal + "[" + objArray.get(i, objArray) + "]";
						}
						returnVal = returnVal + "]";
					}
				}
				if (objPassed instanceof NativeObject) {
				    if(objPassed != null) {
						returnVal = "{";
				        Object[] propIds = NativeObject.getPropertyIds((Scriptable) objPassed);
				        for(Object propId: propIds) {
				            String key = propId.toString();
				            String value = NativeObject.getProperty((Scriptable) objPassed, key).toString();
				            returnVal = returnVal + key + ": " + value  + ",";
				        }
						returnVal = returnVal + "}";
				    }			
				}
				if (objPassed.getClass().getName().equals("org.mozilla.javascript.NativeDate")) {
					Date dateRet = (Date) Context.jsToJava(objPassed, Date.class);
					returnVal = dateRet.toString();
				}
			}
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(),ex);
		}
		return returnVal;
	}
	
	public String ListSubFolders(String FolderName) {
		return ListSubFolders(FolderName, true);
	}
	
	public String ListSubFolders(String FolderName, Boolean blnArr) {
		String folders = "";
		AppSettings appSettings = AppSettings.getAppSettings();
		Guide guide = Guide.getGuide(); 
		String fileSeparator = appSettings.getFileSeparator();
		
		String dataDirectory;
		String prefix = "";
		dataDirectory = appSettings.getDataDirectory();
		if (dataDirectory.startsWith("/")) {
			prefix = "/";
		}
		dataDirectory = prefix + fixSeparator(appSettings.getDataDirectory(), fileSeparator);
		String mediaDirectory = fixSeparator(guide.getMediaDirectory(), fileSeparator);
		dataDirectory = dataDirectory + fileSeparator + mediaDirectory;
		
		
		String media = fixSeparator(FolderName, fileSeparator);
		FolderName = dataDirectory + fileSeparator + media;
		logger.debug("CommonFunctions ListSubFolders full Path " + FolderName);
		File file = new File(FolderName);
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		StringBuffer builder = new StringBuffer();
		if (blnArr) {
			builder.append("[\"");
			for(String s : directories) {
			    builder.append(s);
			    builder.append("\", \"");
			}
			int length = builder.length();
			if (length > 2) {
				builder.delete(length - 3, length);
			}
		    builder.append("]");
		} else {
			for(String s : directories) {
			    builder.append(s);
			    builder.append(",");
			}
			int length = builder.length();
			if (length > 0) {
				builder.delete(length - 1, length);
			}
		}
		
		folders = builder.toString();
		logger.debug("CommonFunctions ListSubFolders returned " + folders);
		return folders;
		
	}
	
	public String ListFiles(String FolderName) {
		String files = "";
		AppSettings appSettings = AppSettings.getAppSettings();
		Guide guide = Guide.getGuide(); 
		String fileSeparator = appSettings.getFileSeparator();
		
		String dataDirectory;
		String prefix = "";
		dataDirectory = appSettings.getDataDirectory();
		if (dataDirectory.startsWith("/")) {
			prefix = "/";
		}
		dataDirectory = prefix + fixSeparator(appSettings.getDataDirectory(), fileSeparator);
		String mediaDirectory = fixSeparator(guide.getMediaDirectory(), fileSeparator);
		dataDirectory = dataDirectory + fileSeparator + mediaDirectory;
		
		
		String media = fixSeparator(FolderName, fileSeparator);
		FolderName = dataDirectory + fileSeparator + media;
		logger.debug("CommonFunctions ListFiles full Path " + FolderName);
		File file = new File(FolderName);
		String[] filesList = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isFile();
		  }
		});
		StringBuffer builder = new StringBuffer();
		for(String s : filesList) {
		    builder.append(s);
		    builder.append(",");
		}
		int length = builder.length();
		if (length > 0) {
			builder.delete(length - 1, length);
		}
		
		files = builder.toString();
		logger.debug("CommonFunctions ListFiles returned " + files);
		return files;
		
	}

	public ArrayList<Library> ListGuides() {
		AppSettings appSettings = AppSettings.getAppSettings();
		String fileSeparator = appSettings.getFileSeparator();
		ArrayList<Library> guides = new ArrayList<Library>();
		
		String dataDirectory;
		String prefix = "";
		dataDirectory = appSettings.getDataDirectory();
		if (dataDirectory.startsWith("/")) {
			prefix = "/";
		}
		dataDirectory = prefix + fixSeparator(appSettings.getDataDirectory(), fileSeparator);
		
		String libraryDir = dataDirectory + appSettings.getFileSeparator() + "__GuideLibrary";
		Path thumbsDir = Paths.get(libraryDir);
		if (Files.notExists(thumbsDir))
		{
			try {
				Files.createDirectories(thumbsDir);
			} catch (IOException ex) {
				logger.error(ex.getLocalizedMessage(),ex);
			}
		}
		
		logger.debug("CommonFunctions ListGuides " + dataDirectory);
		File file = new File(dataDirectory);
		String[] filesList = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
			 boolean valid = new File(current, name).isFile() && name.endsWith(".xml");
		    return valid;
		  }
		});
		
		for (int i = 0; i < filesList.length; i++)
		{
			String errorFile = filesList[i];
			boolean addGuide = false;
			String image = "";
			String title = "";
			String fileName = dataDirectory + fileSeparator + filesList[i];
			String media = "";
			String author = "";
			boolean foundTitle = false;
			boolean foundimage = false;
			boolean foundmedia = false;
			boolean foundAuthor = false;
			String fileNameRoot = filesList[i].substring(0, filesList[i].length() - 4);
			String tumbFileName = libraryDir + fileSeparator + fileNameRoot + ".jpg";
			String titleFileName = libraryDir + fileSeparator + fileNameRoot + ".txt";
			Path thumbf = Paths.get(tumbFileName);
			Path titlef = Paths.get(titleFileName); 
			
			if (!Files.exists(thumbf) || !Files.exists(titlef))
			{
				try {
			        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			        InputStream in = new FileInputStream(fileName);
			        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
			        while (streamReader.hasNext()) 
			        {
			        	if (streamReader.isStartElement()) 
			        	{
			                switch (streamReader.getLocalName()) {
			                	case "Title":
			                		title = streamReader.getElementText().trim();
			                		foundTitle = true;
				                    break;
			                	case "Name":
			                		author = streamReader.getElementText().trim();
			                		foundAuthor = true;
			                		break;
			                	case "MediaDirectory":
			                		media = streamReader.getElementText().trim();
			                		foundmedia = true;
				                    break;
			                	case "Image":
			                		image = streamReader.getAttributeValue(null, "id").trim();
									if (image == null) image = "";		                		
			                		if (!image.contains("*") && image.endsWith(".jpg"))
			                		{
			                			File fileCheck = new File(dataDirectory + fileSeparator + media + fileSeparator + image);
			                			if (fileCheck.isFile() && fileCheck.length() > 10000)
			                			{
			                				foundimage = true;
			                			}
			                		}
				                    break;
			                }
		                }
			        	//if we have found everything exit the while
			        	if (foundTitle && foundimage && foundmedia && foundAuthor) break;
			            streamReader.next();
			        }
			        if (foundTitle && foundimage && foundmedia)
			        {
						BufferedImage img = null;
						int thumb = appSettings.getThumbnailSize();
						int oldHeight;
						int oldWidth;
						int newWidth = thumb;
						int newHeight = thumb;
						double factor;
						image = dataDirectory + fileSeparator + media + fileSeparator + image;
						try {
							img = ImageIO.read(new File(image));
							oldHeight = img.getHeight();
							oldWidth = img.getWidth();
							factor = (double) oldHeight / (double) oldWidth;
							if (factor < 1)
							{
								factor = (double) oldWidth / (double) oldHeight;
								newWidth = (int) ((double) thumb * factor);
								newHeight = thumb;
							}
							else
							{
								newWidth = thumb;
								newHeight = (int) ((double) thumb * factor);
							}
						
							BufferedImage imageNew =
									Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
											newWidth, newHeight, Scalr.OP_ANTIALIAS);
							File outputfile = new File(tumbFileName);
							ImageIO.write(imageNew, "jpg", outputfile);
							image = tumbFileName;
							String titleFile = title + "\r\n" + author;
					        writeFile(titleFileName, titleFile);
					        addGuide = true;
						} catch (IOException ex) {
							logger.error("Guide:" + errorFile + " " + ex.getLocalizedMessage(),ex);
						}
			        }
			    } catch (FileNotFoundException ex) {
					logger.error("Guide:" + errorFile + " " + ex.getLocalizedMessage(),ex);
				} catch (XMLStreamException ex) {
					logger.error("Guide:" + errorFile + " " + ex.getLocalizedMessage(),ex);
				}
			}
			else
			{
				image = tumbFileName;
				String titleFile = readFile(titleFileName, StandardCharsets.UTF_8);
				String lines[] = titleFile.split("\\r?\\n");
				if (lines.length > 0) title = lines[0];
				if (lines.length > 1) author = lines[1];
				addGuide = true;
			}
			if (addGuide)
			{
				Library guide = new Library(image, title, fileName, author);
				guides.add(guide);
			}
		}
		
		logger.debug("CommonFunctions ListFiles returned " + filesList.toString());
		return guides;
		
	}
	
	public static ImageData convertToSWT(BufferedImage bufferedImage) {
	    if (bufferedImage.getColorModel() instanceof DirectColorModel) {
	        /*
	        DirectColorModel colorModel = (DirectColorModel)bufferedImage.getColorModel();
	        PaletteData palette = new PaletteData(
	                colorModel.getRedMask(),
	                colorModel.getGreenMask(),
	                colorModel.getBlueMask());
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
	                colorModel.getPixelSize(), palette);
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[3];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                raster.getPixel(x, y, pixelArray);
	                int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
	                data.setPixel(x, y, pixel);
	            }
	        }
	        */
	        DirectColorModel colorModel = (DirectColorModel)bufferedImage.getColorModel();
	        PaletteData palette = new PaletteData(
	                colorModel.getRedMask(),
	                colorModel.getGreenMask(),
	                colorModel.getBlueMask());
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
	                colorModel.getPixelSize(), palette);
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                int rgb = bufferedImage.getRGB(x, y);
	                int pixel = palette.getPixel(new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF));
	                data.setPixel(x, y, pixel);
	                if (colorModel.hasAlpha()) {
	                    data.setAlpha(x, y, (rgb >> 24) & 0xFF);
	                }
	            }
	        }
	        return data;
	    }
	    else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
	        IndexColorModel colorModel = (IndexColorModel)bufferedImage.getColorModel();
	        int size = colorModel.getMapSize();
	        byte[] reds = new byte[size];
	        byte[] greens = new byte[size];
	        byte[] blues = new byte[size];
	        colorModel.getReds(reds);
	        colorModel.getGreens(greens);
	        colorModel.getBlues(blues);
	        RGB[] rgbs = new RGB[size];
	        for (int i = 0; i < rgbs.length; i++) {
	            rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
	        }
	        PaletteData palette = new PaletteData(rgbs);
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
	                colorModel.getPixelSize(), palette);
	        data.transparentPixel = colorModel.getTransparentPixel();
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[1];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                raster.getPixel(x, y, pixelArray);
	                data.setPixel(x, y, pixelArray[0]);
	            }
	        }
	        return data;
	    }
	    else if (bufferedImage.getColorModel() instanceof ComponentColorModel) {
	        ComponentColorModel colorModel = (ComponentColorModel)bufferedImage.getColorModel();
	        //ASSUMES: 3 BYTE BGR IMAGE TYPE
	        PaletteData palette = new PaletteData(0x0000FF, 0x00FF00,0xFF0000);
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
	                colorModel.getPixelSize(), palette);
	        //This is valid because we are using a 3-byte Data model with no transparent pixels
	        data.transparentPixel = -1;
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[3];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                raster.getPixel(x, y, pixelArray);
	                int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
	                data.setPixel(x, y, pixel);
	            }
	        }
	        return data;
	    }
	    return null;
	}	
	

	
	private void writeFile(String fileName, String contents)
	{
	    BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(fileName));
		    out.write(contents);  //Replace with the string 
		    out.close();
		} catch (IOException ex) {
			logger.error(ex.getLocalizedMessage(),ex);
		}
		
	}
	
	public String GetRandomFile(String wildcard, String strSubDir) {
		return GetRandomFile(wildcard, strSubDir, false);
	}
	
	
	public String GetRandomFile(String wildcard, String strSubDir, boolean fullPath) {
		String mediaFound = "";
		AppSettings appSettings = AppSettings.getAppSettings();
		Guide guide = Guide.getGuide(); 
		String fileSeparator = appSettings.getFileSeparator();
		
		String dataDirectory;
		String prefix = "";
		dataDirectory = appSettings.getDataDirectory();
		if (dataDirectory.startsWith("/")) {
			prefix = "/";
		}
		dataDirectory = prefix + fixSeparator(appSettings.getDataDirectory(), fileSeparator);
		String mediaDirectory = fixSeparator(guide.getMediaDirectory(), fileSeparator);
		dataDirectory = dataDirectory + fileSeparator + mediaDirectory;

		// get the directory
		File f = new File(dataDirectory + fileSeparator + strSubDir);
		// wildcard filter class handles the filtering
		WildCardFileFilter WildCardfilter = new WildCardFileFilter();
		WildCardfilter.setFilePatern(wildcard);
		if (f.isDirectory()) {
			// return a list of matching files
			File[] children = f.listFiles(WildCardfilter);
			// return a random image
			int intFile = comonFunctions.getRandom(0,(children.length - 1));
			logger.debug("displayPage Random Media Index " + intFile);
			if (strSubDir.equals("")) {
				if (fullPath) {
					mediaFound = dataDirectory + fileSeparator + children[intFile].getName();
				} else {
					mediaFound = children[intFile].getName();
				}
			} else {
				if (fullPath) {
					mediaFound = dataDirectory + fileSeparator + strSubDir + fileSeparator + children[intFile].getName();
				} else {
					mediaFound = strSubDir + fileSeparator + children[intFile].getName();
				}
			}
			logger.debug("GetRandomFile Random Media Chosen " + mediaFound);
		}
		return mediaFound;
	
	}
	
	// Wildecard filter
	public class WildCardFileFilter implements java.io.FileFilter {
		//Apply the wildcard filter to the file list
		private String strFilePatern;
		
		public void setFilePatern(String strFilePatern) {
			//regular patern to search for
			this.strFilePatern = strFilePatern;
		}

		public boolean accept(File f) {
			try {
				//ignore hidden files and directories
				if (f.isHidden() || f.isDirectory() || f.getName().toLowerCase().equals("thumbs.db"))
				{
					logger.debug("WildCardFileFilter No Match " + f.getName().toLowerCase());
					return false;
				}
				//convert the regular patern to regex
				String strPattern = strFilePatern.toLowerCase();
				String text = f.getName().toLowerCase();
				String strFile = text;
				//.* in regex matches any number of characters
				strPattern = strPattern.replace("*", ".*");
				//test for a match
				if (!text.matches(strPattern)) {
					logger.debug("WildCardFileFilter No Match " + strFile);
					return false;
				}
				
				logger.debug("WildCardFileFilter Match " + strFile);
				return true;
			} catch (Exception e) {
				logger.error("WildCardFileFilter.accept Exception ", e);
				return false;
			}
		}
	}

	public osFamily getOs() {
		return os;
	}
	
	public Boolean onWindows() {
		return os == osFamily.Windows;
	}
	
	public Boolean onMac() {
		return os == osFamily.Mac;
	}
	
	public Boolean onUnix() {
		return os == osFamily.Unix;
	}
	
	public long dateDifference(String type, Object jsdate1, Object jsdate2) {
		Date date1 = (Date) Context.jsToJava(jsdate1, Date.class);
		Date date2 = (Date) Context.jsToJava(jsdate2, Date.class);
		long diffInMillies = date2.getTime() - date1.getTime();
		long returnVal = 0;
		if (type.equals("d")) {
			returnVal = TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
		}
		if (type.equals("h")) {
			returnVal = TimeUnit.HOURS.convert(diffInMillies,TimeUnit.MILLISECONDS);
		}
		if (type.equals("m")) {
			returnVal = TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
		}
		if (type.equals("s")) {
			returnVal = TimeUnit.SECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS);
		}
		return returnVal;
		
	}
	
	public org.eclipse.swt.graphics.Color getColor(String color) { 
		org.eclipse.swt.graphics.Color swtColor = display.getSystemColor(SWT.COLOR_WHITE);
		switch (color) {
		case "white":
			swtColor = display.getSystemColor(SWT.COLOR_WHITE);
			break;
		case "black":
			swtColor = display.getSystemColor(SWT.COLOR_BLACK);
			break;
		case "dark_red":
			swtColor = display.getSystemColor(SWT.COLOR_DARK_RED);
			break;
		case "dark_green":
			swtColor = display.getSystemColor(SWT.COLOR_DARK_GREEN);
			break;
		case "dark_yellow":
			swtColor = display.getSystemColor(SWT.COLOR_DARK_YELLOW);
			break;
		case "dark_blue":
			swtColor = display.getSystemColor(SWT.COLOR_DARK_BLUE);
			break;
		case "dark_magenta":
			swtColor = display.getSystemColor(SWT.COLOR_DARK_MAGENTA);
			break;
		case "dark_cyan":
			swtColor = display.getSystemColor(SWT.COLOR_DARK_CYAN);
			break;
		case "dark_gray":
			swtColor = display.getSystemColor(SWT.COLOR_DARK_GRAY);
			break;
		case "gray":
			swtColor = display.getSystemColor(SWT.COLOR_GRAY);
			break;
		case "red":
			swtColor = display.getSystemColor(SWT.COLOR_RED);
			break;
		case "green":
			swtColor = display.getSystemColor(SWT.COLOR_GREEN);
			break;
		case "yellow":
			swtColor = display.getSystemColor(SWT.COLOR_YELLOW);
			break;
		case "blue":
			swtColor = display.getSystemColor(SWT.COLOR_BLUE);
			break;
		case "magenta":
			swtColor = display.getSystemColor(SWT.COLOR_MAGENTA);
			break;
		case "cyan":
			swtColor = display.getSystemColor(SWT.COLOR_CYAN);
			break;
		}
		return swtColor;
	}
	public Display getDisplay() {
		return display;
	}
	public void setDisplay(Display display) {
		this.display = display;
	}

	public String getMediaFullPath(String mediaFile, String fileSeparator, AppSettings appSettings, Guide guide) {
		String mediaFound = "";
		String dataDirectory;
		String prefix = "";
		if (guide.getInPrefGuide()) {
			dataDirectory = appSettings.getUserDir();
		} else { 
			dataDirectory = appSettings.getDataDirectory();
			if (dataDirectory.startsWith("/")) {
				prefix = "/";
			}
			dataDirectory = prefix + comonFunctions.fixSeparator(appSettings.getDataDirectory(), fileSeparator);
		}
		String mediaDirectory = comonFunctions.fixSeparator(guide.getMediaDirectory(), fileSeparator);
		dataDirectory = dataDirectory + fileSeparator + mediaDirectory;
		
		
		String media = comonFunctions.fixSeparator(mediaFile, fileSeparator);
		logger.debug("displayPage getMediaFullPath " + media);
		int intSubDir = media.lastIndexOf(fileSeparator);
		String strSubDir;
		if (intSubDir > -1) {
			strSubDir = comonFunctions.fixSeparator(media.substring(0, intSubDir + 1), fileSeparator);
			media = media.substring(intSubDir + 1);
		} else {
			strSubDir = "";
		}
		// String strSubDir
		// Handle wildcard *
		if (media.indexOf("*") > -1) {
			mediaFound = comonFunctions.GetRandomFile(media, strSubDir, true);
		} else {
			// no wildcard so just use the file name
			if (strSubDir.equals("")) {
				mediaFound = dataDirectory + fileSeparator + media;
			} else {
				mediaFound = dataDirectory + fileSeparator + strSubDir + fileSeparator + media;
			}
			logger.debug("displayPage Non Random Media " + mediaFound);
		}
		
		return mediaFound;
	}
	
	public String substituteTextVars(String inString, GuideSettings guideSettings, UserSettings userSettings) {
		String retString = inString;
		// Script Variables
		Set<String> set = guideSettings.getScriptVariables().keySet();
		String varValue;
		for (String s :set) {
			try {
				Object objVar =guideSettings.getScriptVariables().get(s);
				varValue = comonFunctions.getVarAsString(objVar);
				if (!varValue.equals("")) {
					retString = retString.replace("<span>" + s + "</span>", varValue);
				}
			} catch (Exception e) {
				logger.error("displayPage BrwsText ScriptVariables Exception " + s + " " + e.getLocalizedMessage(), e);
			}
		}

		// Guide Preferences
		set = guideSettings.getKeys(); 
		String numberRet;
		String type;
		for (String s : set) {
			try {
				type = guideSettings.getPrefType(s);
				if (type.equals("String")) {
					retString = retString.replace("<span>" + s + "</span>", guideSettings.getPref(s));
				}
				if (type.equals("Number")) {
					numberRet = FormatNumPref(guideSettings.getPrefNumber(s));
					retString = retString.replace("<span>" + s + "</span>", numberRet);
				}
			} catch (Exception e) {
				logger.error("displayPage BrwsText String Guide Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
			}
		}

		// String User Preferences
		set = userSettings.getStringKeys(); 
		for (String s : set) {
			try {
				retString = retString.replace("<span>" + s + "</span>", userSettings.getPref(s));
			} catch (Exception e) {
				logger.error("displayPage BrwsText String User Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
			}
		}

		// Number User Preferences
		set = userSettings.getNumberKeys(); 
		for (String s : set) {
			try {
				retString = retString.replace("<span>" + s + "</span>", FormatNumPref(userSettings.getPrefNumber(s)));
			} catch (Exception e) {
				logger.error("displayPage BrwsText Number User Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
			}
		}
		return retString;
	}

	private String FormatNumPref(double prefNumber)
	{
	    if(prefNumber == (int) prefNumber)
	        return String.format("%d",(int) prefNumber);
	    else
	        return String.format("%s",prefNumber);
	}	
	
	//Set script variables from the scriptVar node in the guide xml
	public void processSrciptVars(String scriptVars, GuideSettings guidesettings) {
		try {
			String[] vars = scriptVars.split(",");
			for (String var : vars) {
				String[] parts = var.split("=");
				try {
					if (!parts[0].equals("")) {
						guidesettings.setScriptVar(parts[0], parts[1]);
					}
				} catch (Exception e) {
					logger.error("scriptVar can't set " + var + " " + e.getLocalizedMessage(), e);
				}
			}
		}
		catch (Exception ex) {
			logger.error("scriptVar processSrciptVars " + ex.getLocalizedMessage(), ex);
		}
	}
	

    public boolean searchGuide(String searchText, String path){
    	boolean found = false;
    	try{
	    	String file = readFile(path, StandardCharsets.UTF_8);
	    	found = searchText(searchText, file);
		}
		catch (Exception ex) {
			logger.error("searchGuide " + ex.getLocalizedMessage(), ex);
		}
        return found;
    }

    public boolean searchText(String searchText, String text){
    	boolean found = false;
    	try{
	    	String[] splitSearch = searchText.split(",");
	    	String lowerText = text.toLowerCase();
	    	int includeCount = 0;
	    	for (String test : splitSearch)
	    	{
	    		if (!test.startsWith("-") && !test.startsWith("+")) includeCount++;
	    	}
	    	boolean include = includeCount == 0;
	    	boolean exclude = false;
	    	for (String test : splitSearch)
	    	{
	    		if (test.startsWith("-"))
	    		{
	    			exclude = lowerText.contains(test.substring(1).toLowerCase());
	    			if (exclude) break;
	    		}
	    		else
	    		{
	    			if (test.startsWith("+"))
	    			{
	    				exclude = !lowerText.contains(test.substring(1).toLowerCase());
	    				if (exclude) break;
	    			}
	    			include = include || lowerText.contains(test.toLowerCase());
	    		}
	    	}
	    	found = include && !exclude;
    	}
		catch (Exception ex) {
			logger.error("searchText " + ex.getLocalizedMessage(), ex);
		}
        return found;
    }


    public Image cropImageWidth(Image originalImage, int width)
    {
    	Image newImage = originalImage;
    	try
    	{
	    	int height = originalImage.getBounds().height;
	    	newImage = new Image(null, width, height);
	    	GC gc = new GC(newImage);
	    	int cropWidth = originalImage.getBounds().width - width;
	    	
	    	gc.drawImage(originalImage, (cropWidth / 2), 0, originalImage.getBounds().width - cropWidth, originalImage.getBounds().height,0 , 0, width, height);
	    	gc.dispose();
    	}
		catch (Exception ex) {
			logger.error("cropImageWidth " + ex.getLocalizedMessage(), ex);
		}
    	return newImage;
    }
    
    public String splitButtonText(String text, int width)
    {
    	String splitText = "";
    	try
    	{
	    	String remainingText = text;
	    	if (text.length() <= width)
	    	{
	    		return text;
	    	}
	    	while (remainingText.length() > 0)
	    	{
	    		if (splitText.length() > 0 )
	    		{
	    			splitText = splitText + "\n";
	    		}
	    		if (remainingText.length() > width)
	    		{
			    	int pos = remainingText.lastIndexOf(" ", width);
			    	if (pos > 0)
			    	{
			    		splitText = splitText + remainingText.substring(0, pos);
			    		remainingText = remainingText.substring(pos + 1);
			    	}
			    	else
			    	{
				    		splitText = splitText + remainingText.substring(0, width);
				    		remainingText = remainingText.substring(width + 1);
			    	}
	    		}
	    		else
	    		{
	    			splitText = splitText + remainingText;
	    			remainingText = "";
	    		}
	    	}
    	}
		catch (Exception ex) {
			logger.error("splitButtonText " + ex.getLocalizedMessage(), ex);
		}
    	return splitText;
    }
    
    /*
	public Object xmlFileToObject(String xmlFileName) { 

		String strTag;
		String strTop = "";
		String strValue;
		String fullFileName;

		try {
			AppSettings appSettings = AppSettings.getAppSettings();
			Guide guide = Guide.getGuide(); 
			String fileSeparator = appSettings.getFileSeparator();
			
			String dataDirectory;
			String prefix = "";
			dataDirectory = appSettings.getDataDirectory();
			if (dataDirectory.startsWith("/")) {
				prefix = "/";
			}
			dataDirectory = prefix + fixSeparator(appSettings.getDataDirectory(), fileSeparator);
			String mediaDirectory = fixSeparator(guide.getMediaDirectory(), fileSeparator);
			dataDirectory = dataDirectory + fileSeparator + mediaDirectory;
			
			
			fullFileName = fixSeparator(xmlFileName, fileSeparator);
			fullFileName = dataDirectory + fileSeparator + fullFileName;
			logger.trace("loadXML: " + fullFileName);

			FileInputStream fis = new FileInputStream(fullFileName);
			UnicodeBOMInputStream ubis = new UnicodeBOMInputStream(fis);
			ubis.skipBOM();

			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(ubis);

			while (reader.hasNext()) {
				int eventType = reader.next(); 
				switch (eventType) {
				case XMLStreamConstants.START_DOCUMENT:
					logger.trace("loadXML " + xmlFileName + " Start document ");
					break;
				case XMLStreamConstants.END_DOCUMENT:
					logger.trace("loadXML " + xmlFileName + " End document");
					break;
				case XMLStreamConstants.START_ELEMENT:
					strTag = reader.getName().getLocalPart();
					if (strTop.equals("")) {
						strTop = strTag;
					}
					try {
						reader.next();
						if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
							strValue = reader.getText();
						} else {
							strValue = "";
						}
					} catch (Exception e1) {
						logger.error("loadXML " + xmlFileName + " Tag Exception " + e1.getLocalizedMessage(), e1);
					}
					logger.trace("loadXML " + xmlFileName + " Tag: "  + strTag + " Value: " + strValue);
					break;
				case XMLStreamConstants.END_ELEMENT:
					break;
				case XMLStreamConstants.CHARACTERS:
					break;
				}
			}
		} catch (Exception e) {
			logger.error("loadXML " + xmlFileName + " Exception ", e);
		}
	}
	*/
	
	
}
