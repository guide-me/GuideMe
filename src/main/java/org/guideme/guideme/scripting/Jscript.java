package org.guideme.guideme.scripting;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.settings.UserSettings;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class Jscript {
	private static GuideSettings guideSettings;
	private static UserSettings userSettings;
	private static AppSettings appSettings;
	private static OverRide overRide;
	private static Logger logger = LogManager.getLogger();
	private static final Marker JSCRIPT_MARKER = MarkerManager.getMarker("JSCRIPT");
	private static Boolean inPrefGuide;

	public Jscript(GuideSettings IguideSettings, UserSettings IuserSettings, AppSettings IappSettings, Boolean IinPrefGuide) {
		super();
		guideSettings = IguideSettings;
		userSettings = IuserSettings;
		appSettings = IappSettings;
		inPrefGuide = IinPrefGuide;
	}


	public void changePreSettings(GuideSettings IguideSettings) {
		guideSettings = IguideSettings;
	}

	public static void jscriptLog(String strMessage) {
		logger.info(JSCRIPT_MARKER, strMessage);
	}

	public void runScript(String javaScriptText, String javaFunction, boolean pageloading) {
		try {
			logger.info(JSCRIPT_MARKER, "Chapter: " + guideSettings.getChapter());
			logger.info(JSCRIPT_MARKER, "Page: " + guideSettings.getPage());
			logger.info(JSCRIPT_MARKER, "javaFunction: " + javaFunction);
			logger.info(JSCRIPT_MARKER, "pageloading: " + pageloading);
			logger.info(JSCRIPT_MARKER, "javaScriptText: " + javaScriptText);
			ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
			HashMap<String, String> scriptVars;
			scriptVars = guideSettings.getScriptVariables();
			ContextFactory cntxFact = new ContextFactory();
			Context cntx = cntxFact.enterContext();
			Scriptable scope = cntx.initStandardObjects();
			if (! inPrefGuide) {
				UserSettings cloneUS = userSettings.clone();
				ScriptableObject.putProperty(scope, "userSettings", cloneUS);
			} else {
				ScriptableObject.putProperty(scope, "userSettings", userSettings);
			}
			@SuppressWarnings("rawtypes")
			Class[] cArg = new Class[1];
			cArg[0] = String.class;
			java.lang.reflect.Method tjlog = Jscript.class.getMethod("jscriptLog", cArg);
			FunctionObject jlog = new FunctionObject("jscriptLog", tjlog, scope);
			ScriptableObject.putProperty(scope, "guideSettings", guideSettings);
			ScriptableObject.putProperty(scope, "comonFunctions", comonFunctions);
			ScriptableObject.putProperty(scope, "scriptVars", scriptVars);
			ScriptableObject.putProperty(scope, "mediaDir", appSettings.getDataDirectory());
			ScriptableObject.putProperty(scope, "fileSeparator", java.lang.System.getProperty("file.separator"));
			ScriptableObject.putProperty(scope, "jscriptLog", jlog);
			logger.info(JSCRIPT_MARKER, "Starting ScriptVariables: " + scriptVars);
			logger.info(JSCRIPT_MARKER, "Starting Flags {" + guideSettings.getFlags() + "}");
			
			if (pageloading) {
				ScriptableObject.putProperty(scope, "overRide", overRide);
			}
			
			try {
				cntx.evaluateString(scope, javaScriptText, "script", 1, null);
				javaFunction = javaFunction.replace("()","");
				Object fObj = scope.get(javaFunction, scope);
				if ((fObj instanceof Function)) {
					Object empty[] = { "" };
					Function fct = (Function)fObj;
					fct.call(cntx, scope, scope, empty);
				} else {
					logger.error(JSCRIPT_MARKER, " Couldn't find function " + javaFunction);
				}
			}
			catch (EvaluatorException ex) {
				logger.error(JSCRIPT_MARKER, "JavaScriptError line " + ex.lineNumber() + " column " + ex.columnNumber() + " Source " + ex.lineSource() + " error " + ex.getLocalizedMessage());
			}
			catch (Exception ex) {
				logger.error(JSCRIPT_MARKER, " FileRunScript " + ex.getLocalizedMessage(), ex);
				logger.error(" FileRunScript " + ex.getLocalizedMessage(), ex);
			}
			logger.info(JSCRIPT_MARKER, "Ending ScriptVariables: " + scriptVars);
			logger.info(JSCRIPT_MARKER, "Ending Flags {" + guideSettings.getFlags() + "}");
			Context.exit();
			guideSettings.saveSettings();
			if (inPrefGuide) {
				userSettings.saveUserSettings();
			}
		}
		catch (Exception ex) {
			logger.error(" FileRunScript " + ex.getLocalizedMessage(), ex);
		}
	}


	public void setOverRide(OverRide overRide) {
		Jscript.overRide = overRide;
	}

}
