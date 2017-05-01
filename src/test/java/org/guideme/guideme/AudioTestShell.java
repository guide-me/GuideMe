package org.guideme.guideme;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.guideme.guideme.ui.AudioPlayer;

import com.snapps.swt.SquareButton;

public class AudioTestShell {
	private static Logger logger = LogManager.getLogger();
	private Shell shell = null;
	private Display myDisplay;
	private HashMap<String, FormData> appFormdata = new HashMap<String, FormData>();
	private HashMap<String, Control> appWidgets = new HashMap<String, Control>();
	private static Thread threadAudio;
	private static AudioPlayer audio;

	public AudioTestShell() {
		super();
	}

	public Shell createShell(final Display display) {
		logger.trace("Enter createShell");
		try {
			Control tmpWidget;
			Control tmpWidget2;
			
			
			//Create the main UI elements
			myDisplay = display;
			shell = new Shell(myDisplay, SWT.APPLICATION_MODAL + SWT.DIALOG_TRIM + SWT.RESIZE);

			shell.setText("Guide Me Audio Test");
			FormLayout layout = new FormLayout();
			shell.setLayout(layout);
			
			ScrolledComposite sc = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			FormData scFormData = new FormData();
			scFormData.top = new FormAttachment(0,5);
			scFormData.left = new FormAttachment(0,5);
			scFormData.right = new FormAttachment(100,-5);
			scFormData.bottom = new FormAttachment(100,-5);
			sc.setLayoutData(scFormData);

			Composite composite = new Composite(sc, SWT.NONE);
			composite.setLayout(new FormLayout());

			/*
			 */
			tmpWidget = composite;
			tmpWidget2 = composite;
			//File
			AddTextField(composite, "Audio File", tmpWidget, tmpWidget2, "f:\\test\\music.mp3", "File");
			
			//start at
			AddTextField(composite, "Audio Start at", appWidgets.get("FileLbl"), appWidgets.get("FileCtrl"), "108", "StartAt");

			//stop at
			AddTextField(composite, "Audio Stop at", appWidgets.get("StartAtLbl"), appWidgets.get("StartAtCtrl"), "131", "StopAt");

			//Loops
			AddTextField(composite, "Audio Loops", appWidgets.get("StopAtLbl"), appWidgets.get("StopAtCtrl"), "0", "Loops");

			SquareButton btnCancel = new SquareButton(composite, SWT.PUSH);
			btnCancel.setText("Start");
			FormData btnCancelFormData = new FormData();
			btnCancelFormData.bottom = new FormAttachment(100 , -5);
			btnCancelFormData.right = new FormAttachment(100,-5);
			btnCancel.setLayoutData(btnCancelFormData);
			btnCancel.addSelectionListener(new StartButtonListener());

			SquareButton btnSave = new SquareButton(composite, SWT.PUSH);
			btnSave.setText("Stop");
			FormData btnSaveFormData = new FormData();
			btnSaveFormData.bottom = new FormAttachment(100, -5);
			btnSaveFormData.right = new FormAttachment(btnCancel,-5);
			btnSave.setLayoutData(btnSaveFormData);
			btnSave.addSelectionListener(new StopButtonListener());

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
	
	// Click event code for the dynamic buttons
	class StartButtonListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			try {
				logger.trace("Enter StartButtonListener");
				String file; //filename and path to sound file
				int startAt; //start at in seconds
				int stopAt; //stop at in seconds
				int loops; //number of times to loop
				Text txtTmp;

				txtTmp = (Text) appWidgets.get("FileCtrl");
				file = txtTmp.getText();

				txtTmp = (Text) appWidgets.get("StartAtCtrl");
				startAt = Integer.parseInt(txtTmp.getText());
				
				txtTmp = (Text) appWidgets.get("StopAtCtrl");
				stopAt = Integer.parseInt(txtTmp.getText());

				txtTmp = (Text) appWidgets.get("LoopsCtrl");
				loops = Integer.parseInt(txtTmp.getText());
				

				if (audio != null) {
					audio.audioStop();
				}
				audio = new AudioPlayer(file, startAt, stopAt, loops, "", null, "", "");
				threadAudio = new Thread(audio);
				threadAudio.start();
			}
			catch (Exception ex) {
				logger.error(" StartButtonListener " + ex.getLocalizedMessage(),ex);
			}
			logger.trace("Exit StartButtonListener");
		}
	}

	// Click event code for the dynamic buttons
	class StopButtonListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			try {
				logger.trace("Enter StopButtonListener");
				if (audio != null) {
					audio.audioStop();
					audio = null;
				}
			}
			catch (Exception ex) {
				logger.error(" StopButtonListener " + ex.getLocalizedMessage(),ex);
			}
			logger.trace("Exit StopButtonListener");
		}
	}

	private void AddTextField(Composite group, String labelText, Control prevControl, Control prevControl2, String value, String key) {
		Label lblTmp;
		Text txtTmp;
		String lblSufix;
		String ctrlSufix;
		FormData lblTmpFormData;
		FormData txtTmpFormData;

		lblTmp = new Label(group, SWT.LEFT);
		lblTmp.setText(labelText);
		lblTmpFormData = new FormData();
		lblTmpFormData.top = new FormAttachment(prevControl,5);
		lblTmpFormData.left = new FormAttachment(0,5);
		//lblTmpFormData.right = new FormAttachment(25,0);
		lblTmp.setLayoutData(lblTmpFormData);
		txtTmp = new Text(group, SWT.SINGLE);
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
