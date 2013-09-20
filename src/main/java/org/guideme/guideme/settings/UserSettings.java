package org.guideme.guideme.settings;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class UserSettings implements Cloneable{
	private String userSettingsLocation = "usersettings.xml";
	private Logger logger = LogManager.getLogger();
	private HashMap<String, String> userStringPrefs = new HashMap<String, String>(); 
	private HashMap<String, String> userStringDesc = new HashMap<String, String>(); 
	private HashMap<String, Boolean> userBooleanPrefs = new HashMap<String, Boolean>(); 
	private HashMap<String, String> userBooleanDesc = new HashMap<String, String>(); 
	private HashMap<String, Double> userNumericPrefs = new HashMap<String, Double>(); 
	private HashMap<String, String> userNumericDesc = new HashMap<String, String>();
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	private static UserSettings userSettings;
	
	//constants
	public static final int STRING = 1;
	public static final int NUMBER = 2;
	public static final int BOOLEAN = 3;

	public static synchronized UserSettings getUserSettings() {
		if (userSettings == null) {
			userSettings = new UserSettings();
		}
		return userSettings;
	}
	
	private UserSettings() {
		super();
		try {
			File xmlFile = new File(userSettingsLocation);
			Element elProp;
			String key;
			String value;
			String type;
			String desc;

			if (xmlFile.exists()) {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(xmlFile);
				Element rootElement = doc.getDocumentElement();
				rootElement.normalize();
				
				for(Node childNode = rootElement.getFirstChild(); childNode!=null;){
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						elProp = (Element) childNode;
						key = elProp.getAttribute("key");
						value = elProp.getAttribute("value");
						type = elProp.getAttribute("type");
						desc = elProp.getAttribute("screen");
						if (type.equals("String")) {
							userStringPrefs.put(key, value);
							userStringDesc.put(key, desc);
						}
						if (type.equals("Boolean")) {
							userBooleanPrefs.put(key, Boolean.parseBoolean(value));
							userBooleanDesc.put(key, desc);
						}
						if (type.equals("Number")) {
							userNumericPrefs.put(key, Double.parseDouble(value));
							userNumericDesc.put(key, desc);
						}
					}
				    Node nextChild = childNode.getNextSibling();
				    childNode = nextChild;
				}				

			}

		} catch (ParserConfigurationException pce) {
			logger.error(pce.getLocalizedMessage());
		} catch (SAXException e) {
			logger.error(e.getLocalizedMessage());
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
		saveUserSettings();
	}

	public Set<String> getStringKeys() {
		return userStringPrefs.keySet();
	}
	
	
	public Set<String> getNumberKeys() {
		return userNumericPrefs.keySet();
	}
	
	
	public Set<String> getBooleanKeys() {
		return userBooleanPrefs.keySet();
	}
	
	
	public String getPref(String key) {
		String value;
		if (userStringPrefs.containsKey(key)) {
			value = userStringPrefs.get(key);
		} else {
			value = "";
		}
		return value;
	}

	public Double getPrefNumber(String key) {
		Double value;
		if (userNumericPrefs.containsKey(key)) {
			value = userNumericPrefs.get(key);
		} else {
			value = (double) 0;
		}
		return value;
	}

	public void setPref(String key, String value) {
		if (userStringPrefs.containsKey(key)) {
			value = userStringPrefs.put(key, value);
		}
	}

	public Boolean isPref(String key) {
		Boolean value;
		if (userBooleanPrefs.containsKey(key)) {
			value = userBooleanPrefs.get(key);
		} else {
			value = false;
		}
		return value;
	}

	public void setPref(String key, Boolean value) {
		if (userBooleanPrefs.containsKey(key)) {
			userBooleanPrefs.put(key, value);
		}
	}

	public void setPref(String key, Double value) {
		if (userNumericPrefs.containsKey(key)) {
			userNumericPrefs.put(key, value);
		}
	}

	public String getScreenDesc(String key, int type) {
		String desc = "";
		if (type == UserSettings.STRING) {
			desc = userStringDesc.get(key);
		}
		if (type == UserSettings.BOOLEAN) {
			desc = userBooleanDesc.get(key);
		}
		if (type == UserSettings.NUMBER) {
			desc = userNumericDesc.get(key);
		}
		return desc;
	}
	
	
	public void saveUserSettings(){
	    try {
			File xmlFile = new File(userSettingsLocation);
			Element rootElement;
			Document doc;

			// if the file exists then use the current one, otherwise create a new one.
			// if nodes do not exist it will add them
			if (xmlFile.exists())
			{
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				doc = docBuilder.parse(xmlFile);
				rootElement = doc.getDocumentElement();
				rootElement.normalize();
			} else {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				doc = docBuilder.newDocument();
				rootElement = doc.createElement("SETTINGS");
				doc.appendChild(rootElement);
			}

			String keyVal;
			for (Map.Entry<String, String> entry : userStringPrefs.entrySet()) {
				keyVal = entry.getKey();
			    Element elPref = comonFunctions.getOrAddElement("//pref[@key='" + keyVal + "']", "pref", rootElement, doc);
			    elPref.setAttribute("value",  entry.getValue());
			}

			for (Map.Entry<String, Boolean> entry : userBooleanPrefs.entrySet()) {
				keyVal = entry.getKey();
			    Element elPref = comonFunctions.getOrAddElement("//pref[@key='" + keyVal + "']", "pref", rootElement, doc);
			    elPref.setAttribute("value",  String.valueOf(entry.getValue()));
			}

			for (Map.Entry<String, Double> entry : userNumericPrefs.entrySet()) {
				keyVal = entry.getKey();
			    Element elPref = comonFunctions.getOrAddElement("//pref[@key='" + keyVal + "']", "pref", rootElement, doc);
			    elPref.setAttribute("value",  String.valueOf(entry.getValue()));
			}


			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(userSettingsLocation));
			transformer.transform(source, result);

		} catch (TransformerException ex) {
			logger.error(ex.getLocalizedMessage());
		} catch (Exception ex ) {
			logger.error(ex.getLocalizedMessage());
		}
	}
	
	@Override
	public UserSettings clone() {
	    try {
			return (UserSettings) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}	
}
