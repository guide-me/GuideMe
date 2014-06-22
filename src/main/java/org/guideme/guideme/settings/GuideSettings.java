package org.guideme.guideme.settings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

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
import org.guideme.guideme.model.Guide;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.serialize.ScriptableInputStream;
import org.mozilla.javascript.serialize.ScriptableOutputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.CharacterData;
import org.xml.sax.SAXException;
import org.apache.commons.codec.binary.Base64;

public class GuideSettings{
	/**
	 * 
	 */
	//State information for xml file, stored in a .state file in xml format
	private String chapter = ""; //current chapter
	private String page = "start"; //current page
	private String currPage = "start"; //current page
	private String prevPage = "start"; //current page
	private String flags = ""; //current flags
	private String filename; //name of file to store persistent state
	private String name; //GuideId for these settings
	private HashMap<String, String> formFields = new HashMap<String, String>(); 
	private HashMap<String, Object> scriptVariables = new HashMap<String, Object>(); //variables used by javascript
	private HashMap<String, String> userStringPrefs = new HashMap<String, String>(); 
	private HashMap<String, String> userStringDesc = new HashMap<String, String>(); 
	private LinkedHashSet<String> userStringKeys = new LinkedHashSet<String>(); 
	private HashMap<String, Boolean> userBooleanPrefs = new HashMap<String, Boolean>(); 
	private HashMap<String, String> userBooleanDesc = new HashMap<String, String>(); 
	private LinkedHashSet<String> userBooleanKeys = new LinkedHashSet<String>(); 
	private HashMap<String, Double> userNumericPrefs = new HashMap<String, Double>(); 
	private HashMap<String, String> userNumericDesc = new HashMap<String, String>();
	private LinkedHashSet<String> userNumericKeys = new LinkedHashSet<String>(); 
	private boolean pageSound = true;
	private static Logger logger = LogManager.getLogger();
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();

	public GuideSettings(String GuideId) {
		super();
		name = GuideId;
		Element elProp;
		String key;
		String value;
		String type;
		String desc;
		AppSettings appSettings = AppSettings.getAppSettings();
		String dataDirectory = appSettings.getDataDirectory();
		String prefix = "";
		if (dataDirectory.startsWith("/")) {
			prefix = "/";
		}
		ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
		dataDirectory = prefix + comonFunctions.fixSeparator(dataDirectory, appSettings.getFileSeparator());
		filename = dataDirectory + appSettings.getFileSeparator() + GuideId + ".state";
		Logger logger = LogManager.getLogger();
		logger.debug("GuideSettings appSettings.getDataDirectory(): " + appSettings.getDataDirectory());
		logger.debug("GuideSettings dataDirectory: " + dataDirectory);
		logger.debug("GuideSettings appSettings.getFileSeparator(): " + appSettings.getFileSeparator());
		logger.debug("GuideSettings GuideId: " + GuideId);
		logger.debug("GuideSettings filename: " + filename);

		try {
			//if a state file already exists use it 
			File xmlFile = new File(filename);

			if (xmlFile.exists()) {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(xmlFile);
				Element rootElement = doc.getDocumentElement();
				rootElement.normalize();

				Element elPage = comonFunctions.getElement("//Page", rootElement);
				if (elPage != null) {
					setPage(elPage.getTextContent());
				}

				Element elCurrPage = comonFunctions.getElement("//CurrPage", rootElement);
				if (elCurrPage != null) {
					setCurrPage(elCurrPage.getTextContent());
				}

				Element elPrevPage = comonFunctions.getElement("//PrevPage", rootElement);
				if (elPrevPage != null) {
					setPrevPage(elPrevPage.getTextContent());
				}

				Element elFlags = comonFunctions.getElement("//Flags", rootElement);
				if (elFlags != null) {
					setFlags(elFlags.getTextContent());
				}
				
				Element elScriptVariables = comonFunctions.getElement("//scriptVariables", rootElement);
				if (elScriptVariables != null) {
					NodeList nodeList = elScriptVariables.getElementsByTagName("Var");
					String strName;
					String strType;
					String strValue;
					Object objValue;
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node currentNode = nodeList.item(i);
						if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
							try {
								Element elVar = (Element) currentNode;
								strName = elVar.getAttribute("id");
								strType = elVar.getAttribute("type");
								CharacterData elChar;
								elChar = (CharacterData) elVar.getFirstChild();
								if (elChar != null) {
									strValue = elChar.getData();
									objValue = getSavedObject(strValue, strType);
								} else {
									objValue = null;
								}
								scriptVariables.put(strName, objValue);
							} catch (Exception e) {
								logger.error("GuideSettings scriptVariables " + e.getLocalizedMessage(), e);
							}
						}
					}
				}
				
				Element elPrefVariables = comonFunctions.getElement("//scriptPreferences", rootElement);
				if (elPrefVariables != null) {
					for(Node childNode = elPrefVariables.getFirstChild(); childNode!=null;){
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							elProp = (Element) childNode;
							key = elProp.getAttribute("key");
							value = elProp.getAttribute("value");
							type = elProp.getAttribute("type");
							desc = elProp.getAttribute("screen");
							if (type.equals("String")) {
								userStringPrefs.put(key, value);
								userStringDesc.put(key, desc);
								userStringKeys.add(key);
							}
							if (type.equals("Boolean")) {
								userBooleanPrefs.put(key, Boolean.parseBoolean(value));
								userBooleanDesc.put(key, desc);
								userBooleanKeys.add(key);
							}
							if (type.equals("Number")) {
								userNumericPrefs.put(key, Double.parseDouble(value));
								userNumericDesc.put(key, desc);
								userNumericKeys.add(key);
							}
						}
					    Node nextChild = childNode.getNextSibling();
					    childNode = nextChild;
					}				
				}

			}

		} catch (ParserConfigurationException pce) {
			logger.error(pce.getLocalizedMessage(), pce);
		} catch (SAXException e) {
			logger.error(e.getLocalizedMessage(),e);
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage(),e);
		}
		saveSettings();
	}

	private Object getSavedObject(String attribute, String strType) {
		Object returned;
		
		returned = attribute;
		
		if (strType.equals("org.mozilla.javascript.NativeArray")) {
			try {
				byte[] decodedBytes = Base64.decodeBase64(attribute.getBytes());
				ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
				ObjectInputStream oInputStream = new ScriptableInputStream(bis, Guide.getGuide().getScope());
				NativeArray restored_array = (NativeArray) oInputStream.readObject();			
				oInputStream.close();
				returned = restored_array;
			} catch (Exception ex ) {
				logger.error(ex.getLocalizedMessage(),ex);
			}
		    
		}
		

		if (strType.equals("org.mozilla.javascript.NativeObject")) {
			try {
				byte[] decodedBytes = Base64.decodeBase64(attribute.getBytes());
				ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
				ObjectInputStream oInputStream = new ScriptableInputStream(bis, Guide.getGuide().getScope());
				NativeObject restored_array = (NativeObject) oInputStream.readObject();			
				oInputStream.close();
				returned = restored_array;
			} catch (Exception ex ) {
				logger.error(ex.getLocalizedMessage(),ex);
			}
		    
		}
		
		if (strType.equals("org.mozilla.javascript.NativeDate")) {
			try {
				byte[] decodedBytes = Base64.decodeBase64(attribute.getBytes());
				ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
				ObjectInputStream oInputStream = new ScriptableInputStream(bis, Guide.getGuide().getScope());
				Object restored_array = oInputStream.readObject();			
				oInputStream.close();
				returned = restored_array;
			} catch (Exception ex ) {
				logger.error(ex.getLocalizedMessage(),ex);
			}
		    
		}
		
		if (strType.equals("java.lang.Double")) {
			Double restored_double = Double.parseDouble(attribute);
			returned = restored_double;
		}

		if (strType.equals("java.lang.Boolean")) {
			Boolean restored_boolean = Boolean.parseBoolean(attribute);
			returned = restored_boolean;
		}

		// TODO Auto-generated method stub
		return returned;
	}

	private String createSaveObject(Object value, String strType) {
		String returnVal;
		returnVal = value.toString();
		if (strType.equals("org.mozilla.javascript.NativeArray") || strType.equals("org.mozilla.javascript.NativeObject") || strType.equals("org.mozilla.javascript.NativeDate") ) {
			try {
			    ByteArrayOutputStream bos = new ByteArrayOutputStream();
			    ScriptableOutputStream  os = new ScriptableOutputStream (bos, Guide.getGuide().getScope());
			    os.writeObject(value);
			    byte[] encodedBytes = Base64.encodeBase64(bos.toByteArray());
			    String fromApacheBytes = new String(encodedBytes);
			    returnVal = fromApacheBytes;
			    os.close();
			} catch (Exception ex ) {
				logger.error(ex.getLocalizedMessage(),ex);
			}
		    
		}

		
		return returnVal;
	}


	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public HashMap<String, Object> getScriptVariables() {
		return scriptVariables;
	}

	public void setScriptVariables(HashMap<String, Object> scriptVariables) {
		this.scriptVariables = scriptVariables;
	}
	public LinkedHashSet<String> getStringKeys() {
		return userStringKeys;
	}
	
	
	public LinkedHashSet<String> getNumberKeys() {
		return userNumericKeys;
	}
	
	
	public LinkedHashSet<String> getBooleanKeys() {
		return userBooleanKeys;
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
	
	public void addPref(String key, String value, String screenDesc) {
		userStringPrefs.put(key, value);
		userStringDesc.put(key, screenDesc);
		userStringKeys.add(key);
	}
	
	public void addPref(String key, Boolean value, String screenDesc) {
		userBooleanPrefs.put(key, value);
		userBooleanDesc.put(key, screenDesc);
		userBooleanKeys.add(key);
	}
	
	public void addPref(String key, Double value, String screenDesc) {
		userNumericPrefs.put(key, value);
		userNumericDesc.put(key, screenDesc);
		userNumericKeys.add(key);
	}
	
	public String getScreenDesc(String key, String type) {
		String desc = "";
		if (type.equals("String")) {
			desc = userStringDesc.get(key);
		}
		if (type.equals("Boolean")) {
			desc = userBooleanDesc.get(key);
		}
		if (type.equals("Number")) {
			desc = userNumericDesc.get(key);
		}
		return desc;
	}
	
	
	public Boolean keyExists(String key, String type) {
		//comment
		Boolean exists = false;
		if (type.equals("String")) {
			exists = userStringKeys.contains(key);
		}
		if (type.equals("Boolean")) {
			exists = userBooleanKeys.contains(key);
		}
		if (type.equals("Number")) {
			exists = userNumericKeys.contains(key);
		}
		return exists;
	}
	
	
	public String getName() {
		return name;
	}

	public void saveSettings(){
	    try {
			File xmlFile = new File(filename);
			logger.trace("GuideSettings saveSettings filename: " + filename);
			Element rootElement;
			Document doc;

			// if the file exists then use the current one, otherwise create a new one.
			// if nodes do not exist it will add them
			if (xmlFile.exists())
			{
				logger.trace("GuideSettings saveSettings file exists ");
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				doc = docBuilder.parse(xmlFile);
				rootElement = doc.getDocumentElement();
				rootElement.normalize();
			} else {
				logger.trace("GuideSettings saveSettings does not file exist ");
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				doc = docBuilder.newDocument();
				rootElement = doc.createElement("SETTINGS");
				doc.appendChild(rootElement);
			}

		    Element elPage = comonFunctions.getOrAddElement("//Page", "Page", rootElement, doc);
		    elPage.setTextContent(getPage());

		    Element elCurrPage = comonFunctions.getOrAddElement("//CurrPage", "CurrPage", rootElement, doc);
		    elCurrPage.setTextContent(getCurrPage());

		    Element elPrevPage = comonFunctions.getOrAddElement("//PrevPage", "PrevPage", rootElement, doc);
		    elPrevPage.setTextContent(getPrevPage());

		    Element elFlags = comonFunctions.getOrAddElement("//Flags", "Flags", rootElement, doc);
		    elFlags.setTextContent(getFlags());

		    Element elScriptVariables = comonFunctions.getElement("//scriptVariables", rootElement);
		    if (elScriptVariables != null) {
		    	rootElement.removeChild(elScriptVariables);
		    }
		    elScriptVariables = comonFunctions.addElement("scriptVariables", rootElement, doc);
		    
		    Iterator<String> it = scriptVariables.keySet().iterator();
		    Element elVar;
		    while (it.hasNext()) {
		    	String key = it.next();
		    	elVar = comonFunctions.addElement("Var", elScriptVariables, doc);
		    	elVar.setAttribute("id", key);
		    	Object value = scriptVariables.get(key);
		    	String strType;
		    	String strValue;
		    	if (value == null) {
			    	strType = "Null";
			    	strValue = "";
		    	} else {
			    	strType = value.getClass().getName();
			    	strValue = createSaveObject(value, strType);
		    	}
		    	comonFunctions.addCdata(strValue, elVar, doc);
		    	elVar.setAttribute("type", strType);
		    }		    

		    
			String keyVal;
			String desc;
		    Element elscriptPreferences = comonFunctions.getElement("//scriptPreferences", rootElement);
		    if (elscriptPreferences != null) {
		    	rootElement.removeChild(elscriptPreferences);
		    }
		    elscriptPreferences = comonFunctions.addElement("scriptPreferences", rootElement, doc);
			for (Map.Entry<String, String> entry : userStringPrefs.entrySet()) {
				keyVal = entry.getKey();
				desc = userStringDesc.get(keyVal);
			    Element elPref = comonFunctions.addElement("pref", elscriptPreferences, doc);
			    elPref.setAttribute("key",  keyVal);
			    elPref.setAttribute("screen",  desc);
			    elPref.setAttribute("type",  "String");
			    elPref.setAttribute("value",  entry.getValue());
			}

			for (Map.Entry<String, Boolean> entry : userBooleanPrefs.entrySet()) {
				keyVal = entry.getKey();
				desc = userBooleanDesc.get(keyVal);
			    Element elPref = comonFunctions.addElement("pref", elscriptPreferences, doc);
			    elPref.setAttribute("key",  keyVal);
			    elPref.setAttribute("screen",  desc);
			    elPref.setAttribute("type",  "Boolean");
			    elPref.setAttribute("value",  String.valueOf(entry.getValue()));
			}

			for (Map.Entry<String, Double> entry : userNumericPrefs.entrySet()) {
				keyVal = entry.getKey();
				desc = userNumericDesc.get(keyVal);
			    Element elPref = comonFunctions.addElement("pref", elscriptPreferences, doc);
			    elPref.setAttribute("key",  keyVal);
			    elPref.setAttribute("screen",  desc);
			    elPref.setAttribute("type",  "Number");
			    elPref.setAttribute("value",  String.valueOf(entry.getValue()));
			}
		    
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			logger.trace("GuideSettings saveSettings save file: " + filename);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);

		} catch (TransformerException ex) {
			logger.error(ex.getLocalizedMessage(),ex);
		} catch (Exception ex ) {
			logger.error(ex.getLocalizedMessage(),ex);
		}
	}

	public void formFieldsReset() {
		formFields = new HashMap<String, String>();
	}
	
	public String getFormField(String key) {
		String value = formFields.get(key);
		if (value == null) {
			value = "";
		}
		return value;
	}
	
	public void setFormField(String key, String value) {
		formFields.put(key, value);
	}

	public boolean isPageSound() {
		return pageSound;
	}

	public void setPageSound(boolean pageSound) {
		this.pageSound = pageSound;
	}

	public String getCurrPage() {
		return currPage;
	}

	public void setCurrPage(String currPage) {
		this.currPage = currPage;
	}

	public String getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(String prevPage) {
		this.prevPage = prevPage;
	}

	public String getFilename() {
		return filename;
	}

	
}

