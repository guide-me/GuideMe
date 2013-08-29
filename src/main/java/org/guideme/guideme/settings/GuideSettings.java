package org.guideme.guideme.settings;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GuideSettings {
	//State information for xml file, stored in a .state file in xml format
	private String page = "start"; //current page
	private String flags = ""; //current flags
	private String html = ""; //used to pass back replacement html from javascript 
	private String filename; //name of file to store persistent state
	private HashMap<String, String> scriptVariables = new HashMap<String, String>(); //variables used by javascript
	private Logger logger = LogManager.getLogger();

	public GuideSettings(String PresName) {
		super();
		AppSettings appSettings = new AppSettings();
		filename = appSettings.getDataDirectory() + appSettings.getFileSeparator() + PresName + ".state";
		try {
			//if a state file already exists use it 
			File xmlFile = new File(filename);

			if (xmlFile.exists()) {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(xmlFile);
				Element rootElement = doc.getDocumentElement();
				rootElement.normalize();

				Element elPage = ComonFunctions.getElement("//Page", rootElement);
				if (elPage != null) {
					setPage(elPage.getTextContent());
				}

				Element elFlags = ComonFunctions.getElement("//Flags", rootElement);
				if (elFlags != null) {
					setFlags(elFlags.getTextContent());
				}
				
				Element elScriptVariables = ComonFunctions.getElement("//scriptVariables", rootElement);
				if (elScriptVariables != null) {
					NodeList nodeList = elScriptVariables.getElementsByTagName("Var");
					String strName;
					String strValue;
					for (int i = 0; i < nodeList.getLength(); i++) {
						Node currentNode = nodeList.item(i);
						if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
							Element elVar = (Element) currentNode;
							strName = elVar.getAttribute("id");
							strValue = elVar.getAttribute("value");
							scriptVariables.put(strName, strValue);
						}
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

	public HashMap<String, String> getScriptVariables() {
		return scriptVariables;
	}

	public void setScriptVariables(HashMap<String, String> scriptVariables) {
		this.scriptVariables = scriptVariables;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
	public void saveSettings(){
	    try {
			File xmlFile = new File(filename);
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

		    Element elPage = ComonFunctions.getOrAddElement("//Page", "Page", rootElement, doc);
		    elPage.setTextContent(getPage());

		    Element elFlags = ComonFunctions.getOrAddElement("//Flags", "Flags", rootElement, doc);
		    elFlags.setTextContent(getFlags());

		    Element elScriptVariables = ComonFunctions.getElement("//scriptVariables", rootElement);
		    if (elScriptVariables != null) {
		    	rootElement.removeChild(elScriptVariables);
		    }
		    elScriptVariables = ComonFunctions.addElement("scriptVariables", rootElement, doc);
		    
		    Iterator<String> it = scriptVariables.keySet().iterator();
		    Element elVar;
		    while (it.hasNext()) {
		    	String key = it.next();
		    	elVar = ComonFunctions.addElement("Var", elScriptVariables, doc);
		    	elVar.setAttribute("id", key);
		    	Object value = scriptVariables.get(key);
		    	String strValue = String.valueOf(value);
		    	elVar.setAttribute("value", strValue);
		    }		    

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);

		} catch (TransformerException ex) {
			logger.error(ex.getLocalizedMessage(),ex);
		} catch (Exception ex ) {
			logger.error(ex.getLocalizedMessage(),ex);
		}
	}

}
