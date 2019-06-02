package org.guideme.guideme.settings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
//import java.util.LinkedHashSet;
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
import org.guideme.guideme.model.Preference;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.CharacterData;
import org.xml.sax.SAXException;

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
	private HashMap<String, Preference> Prefs = new HashMap<String, Preference>(); 
	private boolean pageSound = true;
	private boolean forceStartPage = false;
	private boolean globalScriptLogged = false;
	private static Logger logger = LogManager.getLogger();
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	private Scriptable scope;
	private Scriptable globalScope;

	public GuideSettings(String GuideId) {
		super();
		name = GuideId;
		Element elProp;
		String key;
		String value;
		String type;
		String desc;
		String order;
		int sortOrder;
		String dataDirectory;
		AppSettings appSettings = AppSettings.getAppSettings();
		ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
		if (appSettings.isStateInDataDir())
		{
			dataDirectory = appSettings.getTempDir();
			filename = dataDirectory + GuideId + ".state";
		}
		else
		{
			dataDirectory = appSettings.getDataDirectory();
			String prefix = "";
			if (dataDirectory.startsWith("/")) {
				prefix = "/";
			}
			dataDirectory = prefix + comonFunctions.fixSeparator(dataDirectory, appSettings.getFileSeparator());
			filename = dataDirectory + appSettings.getFileSeparator() + GuideId + ".state";
		}
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
				
				logger.trace("GuideSettings scriptVariables");
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
								logger.trace(new StringBuilder("GuideSettings scriptVariables strName ").append(strName).append(" strType ").append(strType).toString());
								CharacterData elChar;
								elChar = (CharacterData) elVar.getFirstChild();
								if (elChar != null) {
									strValue = elChar.getData();
									objValue = comonFunctions.getSavedObject(strValue, strType, getGlobalScope());
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
							order = elProp.getAttribute("sortOrder");
							try {
								sortOrder = Integer.parseInt(order);
							}
							catch (Exception ex) {
								sortOrder = 0;
							}
							if (type.equals("String")) {
								Preference pref = new Preference(key, type, sortOrder, desc, null, null, value);
								Prefs.put(key, pref);
							}
							if (type.equals("Boolean")) {
								Preference pref = new Preference(key, type, sortOrder, desc, Boolean.parseBoolean(value), null, null);
								Prefs.put(key, pref);
							}
							if (type.equals("Number")) {
								Preference pref = new Preference(key, type, sortOrder, desc, null, Double.parseDouble(value), null);
								Prefs.put(key, pref);
							}
						}
					    Node nextChild = childNode.getNextSibling();
					    childNode = nextChild;
					}				
				}

				logger.trace("GuideSettings scriptVariables scope");
				Element elScope = comonFunctions.getElement("//scope", rootElement);
				if (elScope != null) {
					CharacterData elChar;
					String strValue;
					Object objValue;
					elChar = (CharacterData) elScope.getFirstChild();
					if (elChar != null) {
						strValue = elChar.getData();
						objValue = comonFunctions.getSavedObject(strValue, "Scope", getGlobalScope());
					} else {
						objValue = null;
					}
					scope = (Scriptable) objValue;
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
	
	public Set<String> getKeys() {
		return Prefs.keySet();
	}
	
	/*
	public LinkedHashSet<String> getStringKeys() {
		return userStringKeys;
	}
	
	
	public LinkedHashSet<String> getNumberKeys() {
		return userNumericKeys;
	}
	
	
	public LinkedHashSet<String> getBooleanKeys() {
		return userBooleanKeys;
	}
	*/
	
	
	public String getPrefType(String key) {
		String value = "";
		if (Prefs.containsKey(key)) {
			value = Prefs.get(key).getType();
		}
		return value;
	}
	
	public String getPref(String key) {
		String value = "";
		if (Prefs.containsKey(key)) {
			Preference pref = Prefs.get(key);
			if (pref.getType().equals("String")) {
				value = pref.getstrValue();
			}
		}
		return value;
	}

	public Double getPrefNumber(String key) {
		Double value = (double) 0;
		if (Prefs.containsKey(key)) {
			Preference pref = Prefs.get(key);
			if (pref.getType().equals("Number")) {
				value = pref.getDblValue();
			}
		}
		return value;
	}

	public Boolean isPref(String key) {
		Boolean value = false;
		if (Prefs.containsKey(key)) {
			Preference pref = Prefs.get(key);
			if (pref.getType().equals("Boolean")) {
				value = pref.getBlnValue();
			}
		}
		return value;
	}

	public void setPref(String key, String value) {
		if (Prefs.containsKey(key)) {
			Preference pref = Prefs.get(key);
			if (pref.getType().equals("String")) {
				pref.setstrValue(value);
			}
		}
	}

	public void setPref(String key, Boolean value) {
		if (Prefs.containsKey(key)) {
			Preference pref = Prefs.get(key);
			if (pref.getType().equals("Boolean")) {
				pref.setBlnValue(value);
			}
		}
	}

	public void setPref(String key, Double value) {
		if (Prefs.containsKey(key)) {
			Preference pref = Prefs.get(key);
			if (pref.getType().equals("Number")) {
				pref.setDblValue(value);
			}
		}
	}
	
	public void setPrefOrder(String key, Integer value) {
		if (Prefs.containsKey(key)) {
			Preference pref = Prefs.get(key);
			pref.setSortOrder(value);
		}
	}
	
	public void addPref(String key, String value, String screenDesc, int sortOrder) {
		Preference pref = new Preference(key, "String", sortOrder, screenDesc, null, null, value);
		Prefs.put(key, pref);
	}
	
	public void addPref(String key, Boolean value, String screenDesc, int sortOrder) {
		Preference pref = new Preference(key, "Boolean", sortOrder, screenDesc, value, null, null);
		Prefs.put(key, pref);
	}
	
	public void addPref(String key, Double value, String screenDesc, int sortOrder) {
		Preference pref = new Preference(key, "Number", sortOrder, screenDesc, null, value, null);
		Prefs.put(key, pref);
	}
	
	public String getScreenDesc(String key) {
		String desc = "";
		if (Prefs.containsKey(key)) {
			Preference pref = Prefs.get(key);
			desc = pref.getScreenDesc();
		}
		return desc;
	}
	
	public ArrayList<Preference> getPrefArray() {
		ArrayList<Preference> prefs = new ArrayList<Preference>();
		Preference pref;
		Set<String> keys = Prefs.keySet();
		for (String s : keys) {
			pref = Prefs.get(s);
			prefs.add(pref);
		}
		Collections.sort(prefs);
		return prefs;
	}
	
	public Boolean keyExists(String key, String type) {
		//comment
		Boolean exists = false;
		if (Prefs.containsKey(key)) {
			Preference pref = Prefs.get(key);
			if (pref.getType().equals(type)) {
				exists = true;
			}
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
		    
			logger.trace("GuideSettings saveSettings scriptVariables");
		    Iterator<String> it = scriptVariables.keySet().iterator();
		    Element elVar;
		    while (it.hasNext()) {
		    	String key = it.next();
		    	Object value = scriptVariables.get(key);
		    	String strType;
		    	String strValue;
		    	if (value == null) {
			    	strType = "Null";
			    	strValue = "";
		    	} else {
			    	strType = value.getClass().getName();
			    	strValue = comonFunctions.createSaveObject(value, strType, getGlobalScope());
		    	}
		    	if (!strValue.equals("ignore"))
		    	{
			    	elVar = comonFunctions.addElement("Var", elScriptVariables, doc);
			    	elVar.setAttribute("id", key);
			    	comonFunctions.addCdata(strValue, elVar, doc);
			    	elVar.setAttribute("type", strType);
		    	}
		    }		    

		    Element elScope = comonFunctions.getElement("//scope", rootElement);
			logger.trace("GuideSettings saveSettings scope");

		    if (elScope != null) {
		    	rootElement.removeChild(elScope);
		    }
		    elScope = comonFunctions.addElement("scope", rootElement, doc);
	    	String strValue = comonFunctions.createSaveObject(scope, "Scope", getGlobalScope());
	    	comonFunctions.addCdata(strValue, elScope, doc);
		    
		    Element elscriptPreferences = comonFunctions.getElement("//scriptPreferences", rootElement);
		    if (elscriptPreferences != null) {
		    	rootElement.removeChild(elscriptPreferences);
		    }
		    elscriptPreferences = comonFunctions.addElement("scriptPreferences", rootElement, doc);
			for (Map.Entry<String, Preference> entry : Prefs.entrySet()) {
				Preference pref = entry.getValue();
			    Element elPref = comonFunctions.addElement("pref", elscriptPreferences, doc);
			    elPref.setAttribute("key",  pref.getKey());
			    elPref.setAttribute("screen",  pref.getScreenDesc());
			    elPref.setAttribute("type",  pref.getType());
			    elPref.setAttribute("sortOrder",  String.valueOf(pref.getSortOrder()));
			    if (pref.getType().equals("String")) {
				    elPref.setAttribute("value",  pref.getstrValue());
			    }
			    if (pref.getType().equals("Boolean")) {
				    elPref.setAttribute("value",  String.valueOf(pref.getBlnValue()));
			    }
			    if (pref.getType().equals("Number")) {
				    elPref.setAttribute("value",  String.valueOf(pref.getDblValue()));
			    }
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

	public boolean isForceStartPage() {
		return forceStartPage;
	}

	public void setForceStartPage(boolean forceStartPage) {
		this.forceStartPage = forceStartPage;
	}

	public boolean isGlobalScriptLogged() {
		return globalScriptLogged;
	}

	public void setGlobalScriptLogged(boolean globalScriptLogged) {
		this.globalScriptLogged = globalScriptLogged;
	}

	public void setScriptVar(String key, Object var) {
		scriptVariables.put(key, var);
	}

	public Scriptable getScope() {
		return scope;
	}

	public void setScope(Scriptable scope) {
		this.scope = scope;
	}

	public Scriptable getGlobalScope() {
		if (globalScope == null)
		{
		    ContextFactory factory = new ContextFactory();
		    Context context = factory.enterContext();
		    globalScope = context.initStandardObjects();
		    Context.exit();
		}
		return globalScope;
	}

}

