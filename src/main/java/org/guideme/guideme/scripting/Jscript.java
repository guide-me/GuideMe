package org.guideme.guideme.scripting;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.settings.UserSettings;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class Jscript {
	private static GuideSettings guideSettings;
	private static UserSettings userSettings;
	private static AppSettings appSettings;
	private static Logger logger = LogManager.getLogger();
    private static final Marker JSCRIPT_MARKER = MarkerManager.getMarker("JSCRIPT");
	
	public Jscript(GuideSettings IguideSettings, UserSettings IuserSettings, AppSettings IappSettings) {
		super();
		guideSettings = IguideSettings;
		userSettings = IuserSettings;
		appSettings = IappSettings;
	}


	public void changePreSettings(GuideSettings IguideSettings) {
		guideSettings = IguideSettings;
	}
	
	public static void jscriptLog(String strMessage) {
		logger.info(JSCRIPT_MARKER, strMessage);
	}

	public void runScript(String javaScriptText) {
		try {
			HashMap<String, String> scriptVars;
			scriptVars = guideSettings.getScriptVariables();
			ContextFactory cntxFact = new ContextFactory();
			Context cntx = cntxFact.enterContext();
			Scriptable scope = cntx.initStandardObjects();
			UserSettings cloneUS = userSettings.clone();
			ScriptableObject.putProperty(scope, "userSettings", cloneUS);
			guideSettings.setHtml("");
			@SuppressWarnings("rawtypes")
			Class[] cArg = new Class[1];
	        cArg[0] = String.class;
			java.lang.reflect.Method tjlog = Jscript.class.getMethod("jscriptLog", cArg);
			FunctionObject jlog = new FunctionObject("jscriptLog", tjlog, scope);
			ScriptableObject.putProperty(scope, "guideSettings", guideSettings);
			ScriptableObject.putProperty(scope, "scriptVars", scriptVars);
			ScriptableObject.putProperty(scope, "mediaDir", appSettings.getDataDirectory());
			ScriptableObject.putProperty(scope, "fileSeparator", java.lang.System.getProperty("file.separator"));
			ScriptableObject.putProperty(scope, "jscriptLog", jlog);
			logger.info(JSCRIPT_MARKER, "ScriptVariables: " + scriptVars);
			logger.info(JSCRIPT_MARKER, "guideSettings.Flags {" + guideSettings.getFlags() + "}");
			try {
				cntx.evaluateString(scope, javaScriptText, "script", 1, null);
			}
			catch (EvaluatorException ex) {
				logger.error(JSCRIPT_MARKER, "JavaScriptError line " + ex.lineNumber() + " column " + ex.columnNumber() + " Source " + ex.lineSource() + " error " + ex.getLocalizedMessage());
			}
			catch (Exception ex) {
				logger.error(JSCRIPT_MARKER, " FileRunScript " + ex.getLocalizedMessage(), ex);
				logger.error(" FileRunScript " + ex.getLocalizedMessage(), ex);
			}
			logger.info(JSCRIPT_MARKER, "ScriptVariables: " + scriptVars);
			logger.info(JSCRIPT_MARKER, "guideSettings.Flags {" + guideSettings.getFlags() + "}");
			Context.exit();
			guideSettings.saveSettings();
			/*
			HashMap<String, String> scriptVars;
			scriptVars = guideSettings.getScriptVariables();
			ContextFactory cntxFact = new ContextFactory();
			Context cntx = cntxFact.enterContext();
			Scriptable scope = cntx.initStandardObjects();
			UserSettings cloneUS = userSettings.clone();
			ScriptableObject.putProperty(scope, "userSettings", cloneUS);
			guideSettings.setHtml("");
			ScriptableObject.putProperty(scope, "guideSettings", guideSettings);
			NativeObject nobj = new NativeObject();
			for (Map.Entry<String, String> entry : scriptVars.entrySet()) {
				nobj.defineProperty(entry.getKey(), entry.getValue(), NativeObject.READONLY);
			}
			ScriptableObject.putProperty(scope, "scriptVars", scriptVars);
			cntx.evaluateString(scope, javaScriptText, "script", 1, null);
			Context.exit();
			*/
		}
		catch (Exception ex) {
			logger.error(" FileRunScript " + ex.getLocalizedMessage(), ex);
		}
	}

}
