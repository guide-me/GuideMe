package org.guideme.guideme;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
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

	public static void main(String[] args) {
		Control tmpWidget;
		Control tmpWidget2;
		Display display = new Display();
		AppSettings appSettings = new AppSettings();
		Font sysFont = display.getSystemFont();
		FontData[] fD = sysFont.getFontData();
		fD[0].setHeight(appSettings.getFontSize());
		controlFont = new Font(display, fD);
		UserSettings userSettings = new UserSettings();
		String dataDirectory = appSettings.getDataDirectory();
		appSettings.setDataDirectory(appSettings.getUserDir());
		appSettings.saveSettings();
		GuideSettings guideSettings = new GuideSettings("GuideTest");
		try {
			String source = ComonFunctions.readFile("test.js", Charset.defaultCharset());
			Jscript jscript = new Jscript(guideSettings, userSettings, appSettings);
			jscript.runScript(source);
		} catch (IOException e) {
			logger.error(" Run Script " + e.getLocalizedMessage(), e);
		}				
		
		shell = new Shell(display, SWT.APPLICATION_MODAL + SWT.DIALOG_TRIM + SWT.RESIZE);
		ScrolledComposite sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		FormData scFormData = new FormData();
		scFormData.top = new FormAttachment(0,5);
		scFormData.left = new FormAttachment(0,5);
		scFormData.right = new FormAttachment(100,-5);
		scFormData.bottom = new FormAttachment(100,-5);
		sc.setLayoutData(scFormData);

		Composite composite = new Composite(sc, SWT.NONE);
		composite.setLayout(new FormLayout());

		//App Settings Group
		Group grpApp = new Group(composite, SWT.SHADOW_IN);
		FormData grpAppFormData = new FormData();
		grpAppFormData.top = new FormAttachment(0,5);
		grpAppFormData.left = new FormAttachment(0,5);
		grpAppFormData.right = new FormAttachment(100,-5);
		grpAppFormData.bottom = new FormAttachment(100,-5);
		grpApp.setLayoutData(grpAppFormData);
		grpApp.setText("Application");
		FormLayout layout5 = new FormLayout();
		grpApp.setLayout(layout5);
		tmpWidget = grpApp;
		tmpWidget2 = grpApp;
		
		AddTextField(grpApp, "Html", tmpWidget, tmpWidget2, guideSettings.getHtml(), "key temp", false);		
		shell.pack();
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
		txtTmp = new Text(group, SWT.SINGLE);
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
