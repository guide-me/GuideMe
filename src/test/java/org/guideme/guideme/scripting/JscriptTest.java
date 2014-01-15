package org.guideme.guideme.scripting;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.scripting.Jscript;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.settings.UserSettings;

public class JscriptTest {
	private static Logger logger = LogManager.getLogger();
	private static Shell shell;
	private static Font controlFont;
	private static HashMap<String, FormData> appFormdata = new HashMap<String, FormData>();
	private static HashMap<String, Control> appWidgets = new HashMap<String, Control>();
	private static OverRide overRide = new OverRide();

	public static void main(String[] args) {
		Control tmpWidget;
		Control tmpWidget2;
		ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
		Display display = new Display();
		AppSettings appSettings = AppSettings.getAppSettings();
		Font sysFont = display.getSystemFont();
		FontData[] fD = sysFont.getFontData();
		fD[0].setHeight(appSettings.getFontSize());
		controlFont = new Font(display, fD);
		UserSettings userSettings = UserSettings.getUserSettings();
		String dataDirectory = appSettings.getDataDirectory();
		appSettings.setDataDirectory(appSettings.getUserDir());
		appSettings.saveSettings();
		Guide guide = Guide.getGuide();
		GuideSettings guideSettings = new GuideSettings("GuideTest");
		guide.setSettings(guideSettings);
		try {
			String source = comonFunctions.readFile("test.js", Charset.defaultCharset());
			Jscript jscript = new Jscript(guide, userSettings, appSettings, false);
			jscript.setOverRide(overRide);
			jscript.runScript(source, "test", true);
		} catch (IOException e) {
			logger.error(" Run Script " + e.getLocalizedMessage(), e);
		}				
		
		shell = new Shell(display, SWT.APPLICATION_MODAL + SWT.DIALOG_TRIM + SWT.RESIZE);
		shell.setText("J Script Harness");
		FormLayout layout = new FormLayout();
		shell.setLayout(layout);
		
		ScrolledComposite sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL|SWT.BORDER);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		FormData scFormData = new FormData();
		scFormData.top = new FormAttachment(0,5);
		scFormData.left = new FormAttachment(0,5);
		scFormData.right = new FormAttachment(100,-5);
		scFormData.bottom = new FormAttachment(100,-5);
		sc.setLayoutData(scFormData);
		sc.setLayout(new FormLayout());

		Group grpNames = new Group(sc, SWT.SHADOW_IN);
		FormData grpNamesFormData = new FormData();
		grpNamesFormData.top = new FormAttachment(0,5);
		grpNamesFormData.left = new FormAttachment(0,5);
		grpNamesFormData.right = new FormAttachment(100,-5);
		grpNames.setLayoutData(grpNamesFormData);
		grpNames.setText("Variables");
		FormLayout layout2 = new FormLayout();
		grpNames.setLayout(layout2);

		tmpWidget = grpNames;
		tmpWidget2 = grpNames;

		AddTextField(grpNames, "ScriptVars" , tmpWidget, tmpWidget2, guideSettings.getScriptVariables().toString(), "ScriptVars", false);
		tmpWidget = appWidgets.get("ScriptVars" + "Lbl");
		tmpWidget2 = appWidgets.get("ScriptVars" + "Ctrl");
		AddTextField(grpNames, "Html", tmpWidget, tmpWidget2, overRide.getHtml(), "html", false);
		tmpWidget = appWidgets.get("html" + "Lbl");
		tmpWidget2 = appWidgets.get("html" + "Ctrl");
		AddTextField(grpNames, "page", tmpWidget, tmpWidget2, guideSettings.getPage(), "page", false);
		tmpWidget = appWidgets.get("page" + "Lbl");
		tmpWidget2 = appWidgets.get("page" + "Ctrl");
		AddTextField(grpNames, "flags", tmpWidget, tmpWidget2, guideSettings.getFlags(), "flags", false);
		tmpWidget = appWidgets.get("flags" + "Lbl");
		tmpWidget2 = appWidgets.get("flags" + "Ctrl");
		AddTextField(grpNames, "name", tmpWidget, tmpWidget2, guideSettings.getName(), "name", false);
		sc.setContent(grpNames);
		sc.setMinSize(grpNames.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		shell.layout();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		appSettings.setDataDirectory(dataDirectory);
		appSettings.saveSettings();
}
	// Click event code for the dynamic buttons

	class CancelButtonListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			try {
				logger.trace("Enter CancelButtonListener");
				shell.close();
			}
			catch (Exception ex) {
				logger.error(" CancelButtonListener " + ex.getLocalizedMessage(), ex);
			}
			logger.trace("Exit CancelButtonListener");
		}
	}


	private static void AddTextField(Group group, String labelText, Control prevControl, Control prevControl2, String value, String key, Boolean addNewmeric) {
		Label lblTmp;
		Text txtTmp;
		String lblSufix;
		String ctrlSufix;
		FormData lblTmpFormData;
		FormData txtTmpFormData;

		lblTmp = new Label(group, SWT.LEFT);
		lblTmp.setText(labelText);
		lblTmp.setFont(controlFont);
		lblTmpFormData = new FormData();
		lblTmpFormData.top = new FormAttachment(prevControl,5);
		lblTmpFormData.left = new FormAttachment(0,5);
		lblTmp.setLayoutData(lblTmpFormData);
		txtTmp = new Text(group, SWT.MULTI | SWT.WRAP);
		txtTmp.setFont(controlFont);
		txtTmp.setText(value);
		txtTmpFormData = new FormData();
		txtTmpFormData.top = new FormAttachment(prevControl2,5);
		txtTmpFormData.left = new FormAttachment(lblTmp,10);
		txtTmpFormData.right = new FormAttachment(100,-5);
		txtTmp.setLayoutData(txtTmpFormData);
		lblSufix = "Lbl";
		ctrlSufix = "Ctrl";
		appFormdata.put(key + lblSufix, lblTmpFormData);
		appFormdata.put(key + ctrlSufix, txtTmpFormData);
		appWidgets.put(key + lblSufix, lblTmp);
		appWidgets.put(key + ctrlSufix, txtTmp);
	}

}
