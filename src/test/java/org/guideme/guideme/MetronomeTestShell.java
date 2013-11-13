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
import org.guideme.guideme.ui.MetronomePlayer;

import com.snapps.swt.SquareButton;

public class MetronomeTestShell {
	private static Logger logger = LogManager.getLogger();
	private Shell shell = null;
	private Display myDisplay;
	private HashMap<String, FormData> appFormdata = new HashMap<String, FormData>();
	private HashMap<String, Control> appWidgets = new HashMap<String, Control>();
	private static Thread threadMetronome;
	private static MetronomePlayer metronome;

	public MetronomeTestShell() {
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

			shell.setText("Guide Me Metronome Test");
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
			//BPM
			AddTextField(composite, "Beate per minute", tmpWidget, tmpWidget2, "60", "BPM");
			
			//Instrument
			AddTextField(composite, "Instrument", appWidgets.get("BPMLbl"), appWidgets.get("BPMCtrl"), "76", "Instrument");

			//Loops
			AddTextField(composite, "Loops", appWidgets.get("InstrumentLbl"), appWidgets.get("InstrumentCtrl"), "-1", "Loops");

			//Resolution
			AddTextField(composite, "Resolution", appWidgets.get("LoopsLbl"), appWidgets.get("LoopsCtrl"), "4", "Resolution");

			//Rhythm
			AddTextField(composite, "Rhythm", appWidgets.get("ResolutionLbl"), appWidgets.get("ResolutionCtrl"), "", "Rhythm");


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
				int metronomeBPM; //Beats per minute
				int instrument; //instrument number (uses midi percussion http://en.wikipedia.org/wiki/General_MIDI#Percussion)
				int loops; // number of times it repeats (e.g. 3 loops would play it 4 times in total)
				int resolution; // ticks per beat
				Text txtTmp;
				String rhythm; //comma separated list of when to play. 

				txtTmp = (Text) appWidgets.get("BPMCtrl");
				metronomeBPM = Integer.parseInt(txtTmp.getText());

				txtTmp = (Text) appWidgets.get("InstrumentCtrl");
				instrument = Integer.parseInt(txtTmp.getText());

				txtTmp = (Text) appWidgets.get("LoopsCtrl");
				loops = Integer.parseInt(txtTmp.getText());

				txtTmp = (Text) appWidgets.get("ResolutionCtrl");
				resolution = Integer.parseInt(txtTmp.getText());

				txtTmp = (Text) appWidgets.get("RhythmCtrl");
				rhythm = txtTmp.getText();

				if (metronome != null) {
					metronome.metronomeStop();
				}
				metronome = new MetronomePlayer(metronomeBPM, instrument, loops, resolution, rhythm, 127);
				//metronome = new MetronomePlayer(120, 49, -1, 4, "");
				threadMetronome = new Thread(metronome);
				threadMetronome.start();
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
				if (metronome != null) {
					metronome.metronomeStop();
					metronome = null;
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
