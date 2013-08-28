package org.guideme.guideme.settings;

import java.util.HashMap;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
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
import org.eclipse.swt.widgets.Button;

import com.snapps.swt.SquareButton;

public class PreferenceShell {
	private Shell shell = null;
	private Display myDisplay;
	private UserSettings myUserSettings;
	private AppSettings myAppSettings;
	private static Logger logger = LogManager.getLogger();
	private Font controlFont;
	private HashMap<String, FormData> appFormdata = new HashMap<String, FormData>();
	private HashMap<String, Control> appWidgets = new HashMap<String, Control>();

	public PreferenceShell() {
		super();
	}

	public Shell createShell(final Display display, UserSettings userSettings, AppSettings appSettings) {
		logger.trace("Enter createShell");
		try {
			Control tmpWidget;
			Control tmpWidget2;
			
			
			//Create the main UI elements
			myDisplay = display;
			myUserSettings = userSettings;
			myAppSettings = appSettings;
			shell = new Shell(myDisplay, SWT.APPLICATION_MODAL + SWT.DIALOG_TRIM + SWT.RESIZE);

			shell.setText("Guide Me Preferences");
			FormLayout layout = new FormLayout();
			shell.setLayout(layout);
			Font sysFont = display.getSystemFont();
			FontData[] fD = sysFont.getFontData();
			fD[0].setHeight(appSettings.getFontSize());
			controlFont = new Font(display, fD);
			
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
			grpApp.setLayoutData(grpAppFormData);
			grpApp.setText("Application");
			FormLayout layout5 = new FormLayout();
			grpApp.setLayout(layout5);
			tmpWidget = grpApp;
			tmpWidget2 = grpApp;
			
			//Font Size
			AddTextField(grpApp, "Font Size", grpApp, grpApp, String.valueOf(myAppSettings.getFontSize()), "AppFontSize", true);
			
			//HTML Font Size
			AddTextField(grpApp, "HTML Font Size", appWidgets.get("AppFontSizeNumLbl"), appWidgets.get("AppFontSizeNumCtrl"), String.valueOf(myAppSettings.getHtmlFontSize()), "AppHtmlFontSize", true);

			//Debug
			AddBooleanField(grpApp, "Debug", appWidgets.get("AppHtmlFontSizeNumLbl"), appWidgets.get("AppHtmlFontSizeNumCtrl"), myAppSettings.getDebug(), "AppDebug");			

			//Data Directory
			AddTextField(grpApp, "Data Directory", appWidgets.get("AppDebugBlnLbl"), appWidgets.get("AppDebugBlnCtrl"), myAppSettings.getDataDirectory(), "AppDataDir", false);

			Group grpNames = new Group(composite, SWT.SHADOW_IN);
			FormData grpNamesFormData = new FormData();
			grpNamesFormData.top = new FormAttachment(grpApp,5);
			grpNamesFormData.left = new FormAttachment(0,5);
			grpNamesFormData.right = new FormAttachment(100,-5);
			grpNames.setLayoutData(grpNamesFormData);
			grpNames.setText("Strings");
			FormLayout layout2 = new FormLayout();
			grpNames.setLayout(layout2);

			tmpWidget = grpNames;
			tmpWidget2 = grpNames;
			Set<String> set = myUserSettings.getStringKeys();
			for (String s : set) {
				AddTextField(grpNames, myUserSettings.getScreenDesc(s, "s"), tmpWidget, tmpWidget2, userSettings.getPref(s), s, false);
				tmpWidget = appWidgets.get(s + "Lbl");
				tmpWidget2 = appWidgets.get(s + "Ctrl");
			}
			
			Group grpPrefs = new Group(composite, SWT.SHADOW_IN);
			FormData grpPrefsFormData = new FormData();
			grpPrefsFormData.top = new FormAttachment(grpNames,5);
			grpPrefsFormData.left = new FormAttachment(0,5);
			grpPrefsFormData.right = new FormAttachment(100,-5);
			grpPrefs.setLayoutData(grpPrefsFormData);
			grpPrefs.setText("Preferences");
			FormLayout layout3 = new FormLayout();
			grpPrefs.setLayout(layout3);

			tmpWidget = grpPrefs;
			tmpWidget2 = grpPrefs;
			Set<String> set2 = myUserSettings.getBooleanKeys();
			for (String s : set2) {
				AddBooleanField(grpPrefs, myUserSettings.getScreenDesc(s, "b"), tmpWidget, tmpWidget2, userSettings.isPref(s), s);
				tmpWidget = appWidgets.get(s + "BlnLbl");
				tmpWidget2 = appWidgets.get(s + "BlnCtrl");
			}

			Group grpDoubles = new Group(composite, SWT.SHADOW_IN);
			FormData grpDoublesFormData = new FormData();
			grpDoublesFormData.top = new FormAttachment(grpPrefs,5);
			grpDoublesFormData.left = new FormAttachment(0,5);
			grpDoublesFormData.right = new FormAttachment(100,-5);
			grpDoubles.setLayoutData(grpDoublesFormData);
			grpDoubles.setText("Numbers");
			FormLayout layout4 = new FormLayout();
			grpDoubles.setLayout(layout4);

			tmpWidget = grpDoubles;
			tmpWidget2 = grpDoubles;
			Set<String> set3 = myUserSettings.getNumberKeys();
			for (String s : set3) {
				AddTextField(grpDoubles, myUserSettings.getScreenDesc(s, "n"), tmpWidget, tmpWidget2, String.valueOf(userSettings.getPrefNumber(s)), s, true);
				tmpWidget = appWidgets.get(s + "NumLbl");
				tmpWidget2 = appWidgets.get(s + "NumCtrl");
			}

			SquareButton btnCancel = new SquareButton(composite, SWT.PUSH);
			btnCancel.setText("Cancel");
			btnCancel.setFont(controlFont);
			FormData btnCancelFormData = new FormData();
			btnCancelFormData.top = new FormAttachment(grpDoubles,5);
			//btnCancelFormData.bottom = new FormAttachment(100,-5);
			btnCancelFormData.right = new FormAttachment(100,-5);
			btnCancel.setLayoutData(btnCancelFormData);
			btnCancel.addSelectionListener(new CancelButtonListener());

			SquareButton btnSave = new SquareButton(composite, SWT.PUSH);
			btnSave.setText("Save");
			btnSave.setFont(controlFont);
			FormData btnSaveFormData = new FormData();
			btnSaveFormData.top = new FormAttachment(grpDoubles,5);
			//btnSaveFormData.bottom = new FormAttachment(100,-5);
			btnSaveFormData.right = new FormAttachment(btnCancel,-5);
			btnSave.setLayoutData(btnSaveFormData);
			btnSave.addSelectionListener(new SaveButtonListener());

			sc.setContent(composite);
			sc.setExpandHorizontal(true);
			sc.setExpandVertical(true);
			sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));			

			shell.layout();;
			
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}
		logger.trace("Exit createShell");
		return shell;
	}
	
	class VerifyDoubleListener implements VerifyListener {

		@Override
		public void verifyText(VerifyEvent event) {
	        Text text = (Text) event.widget;

            // get old text and create new text by using the VerifyEvent.text
            final String previous = text.getText();
            String edited = previous.substring(0, event.start) + event.text + previous.substring(event.end);

            boolean isDouble = true;
            try
            {
                Double.parseDouble(edited);
            }
            catch(NumberFormatException ex)
            {
            	isDouble = false;
            }

            if(!isDouble)
                event.doit = false;
		}

	}
	
	// Click event code for the dynamic buttons
	class SaveButtonListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			try {
				logger.trace("Enter SaveButtonListner");
				Text txtTmp;
				Button btnTmp;

				txtTmp = (Text) appWidgets.get("AppFontSizeNumCtrl");
				myAppSettings.setFontSize(Integer.parseInt(txtTmp.getText()));
				txtTmp = (Text) appWidgets.get("AppHtmlFontSizeNumCtrl");
				myAppSettings.setHtmlFontSize(Integer.parseInt(txtTmp.getText()));

				btnTmp = (Button) appWidgets.get("AppDebugBlnCtrl");
				myAppSettings.setDebug(btnTmp.getSelection());
				
				txtTmp = (Text) appWidgets.get("AppDataDirCtrl");
				myAppSettings.setDataDirectory((txtTmp.getText()));

				Set<String> set = myUserSettings.getStringKeys();
				for (String s : set) {
					txtTmp = (Text) appWidgets.get(s + "Ctrl");
					myUserSettings.setPref(s, txtTmp.getText());
				}
				
				Set<String> set2 = myUserSettings.getBooleanKeys();
				for (String s : set2) {
					btnTmp = (Button) appWidgets.get(s + "BlnCtrl");
					myUserSettings.setPref(s, btnTmp.getSelection());
				}
				Set<String> set3 = myUserSettings.getNumberKeys();
				for (String s : set3) {
					txtTmp = (Text) appWidgets.get(s + "NumCtrl");
					myUserSettings.setPref(s, Double.parseDouble(txtTmp.getText()));
				}
				myUserSettings.saveUserSettings();
				myAppSettings.saveSettings();
				shell.close();
			}
			catch (Exception ex) {
				logger.error(" SaveButtonListner " + ex.getLocalizedMessage());
			}
			logger.trace("Exit SaveButtonListner");
		}
	}

	// Click event code for the dynamic buttons
	class CancelButtonListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			try {
				logger.trace("Enter CancelButtonListener");
				shell.close();
			}
			catch (Exception ex) {
				logger.error(" CancelButtonListener " + ex.getLocalizedMessage());
			}
			logger.trace("Exit CancelButtonListener");
		}
	}

	private void AddTextField(Group group, String labelText, Control prevControl, Control prevControl2, String value, String key, Boolean addNewmeric) {
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
		//lblTmpFormData.right = new FormAttachment(25,0);
		lblTmp.setLayoutData(lblTmpFormData);
		txtTmp = new Text(group, SWT.SINGLE);
		txtTmp.setFont(controlFont);
		txtTmp.setText(value);
		txtTmpFormData = new FormData();
		txtTmpFormData.top = new FormAttachment(prevControl2,5);
		txtTmpFormData.left = new FormAttachment(lblTmp,10);
		txtTmpFormData.right = new FormAttachment(100,-5);
		txtTmp.setLayoutData(txtTmpFormData);
		if (addNewmeric) {
			txtTmp.addVerifyListener(new VerifyDoubleListener());
			lblSufix = "NumLbl";
			ctrlSufix = "NumCtrl";
		} else {
			lblSufix = "Lbl";
			ctrlSufix = "Ctrl";
		}
		appFormdata.put(key + lblSufix, lblTmpFormData);
		appFormdata.put(key + ctrlSufix, txtTmpFormData);
		appWidgets.put(key + lblSufix, lblTmp);
		appWidgets.put(key + ctrlSufix, txtTmp);
	}

	private void AddBooleanField(Group group, String labelText, Control prevControl, Control prevControl2, Boolean value, String key) {
		Label lblTmp;
		Button btnTmp;
		FormData lblTmpFormData;
		FormData txtTmpFormData;

		lblTmp = new Label(group, SWT.LEFT);
		lblTmp.setText(labelText);
		lblTmp.setFont(controlFont);
		lblTmpFormData = new FormData();
		lblTmpFormData.top = new FormAttachment(prevControl,5);
		lblTmpFormData.left = new FormAttachment(0,5);
		//lblTmpFormData.right = new FormAttachment(25,0);
		lblTmp.setLayoutData(lblTmpFormData);
		btnTmp = new Button(group, SWT.CHECK);
		btnTmp.setFont(controlFont);
		btnTmp.setText("");
		btnTmp.setSelection(value);
		txtTmpFormData = new FormData();
		txtTmpFormData.top = new FormAttachment(prevControl2,5);
		txtTmpFormData.left = new FormAttachment(lblTmp,10);
		txtTmpFormData.right = new FormAttachment(100,-5);
		btnTmp.setLayoutData(txtTmpFormData);
		appFormdata.put(key + "BlnLbl", lblTmpFormData);
		appFormdata.put(key + "BlnCtrl", txtTmpFormData);
		appWidgets.put(key + "BlnLbl", lblTmp);
		appWidgets.put(key + "BlnCtrl", btnTmp);
	}

}
