package org.guideme.guideme.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.guideme.guideme.model.Audio;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Delay;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Image;
import org.guideme.guideme.model.Metronome;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.model.Video;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;

import com.snapps.swt.SquareButton;

public class DebugShell {
	private ComonFunctions comonFuctions;
	private Shell shell = null;
	private Display myDisplay;
	private static Logger logger = LogManager.getLogger();
	private Combo pagesCombo;
	private Page dispPage;
	private Guide guide;
	private Text txtText;
	private Text txtScript;
	private Text txtScriptConsole;
	private Text txtVarKey;
	private Text txtVarValue;
	private MainShell mainShell;
	private Composite tableComp;
	private Composite varComp;
	private ScrolledComposite varScrlComp;
	private TabFolder  tabFolder;
	private Table varTable;
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	private static DebugShell debugShell;
	private Boolean keepShellOpen;
	private Table btnTable;
	
	public static synchronized DebugShell getDebugShell() {
		if (debugShell == null) {
			debugShell = new DebugShell();
		}
		return debugShell;
	}
	
	protected  DebugShell() {
		super();
	}

	public void createShell(final Display display, MainShell mainshell) {
		logger.trace("Enter createShell");
		try {
			keepShellOpen = true;
			comonFuctions = ComonFunctions.getComonFunctions();
			AppSettings appSettings = AppSettings.getAppSettings();
			ResourceBundle displayText = appSettings.getDisplayText();
			
			//Create the main UI elements
			myDisplay = display;
			//myUserSettings = userSettings;
			//shell = new Shell(myDisplay, SWT.MODELESS + SWT.RESIZE + SWT.TITLE);
			//shell = new Shell(myDisplay, SWT.RESIZE + SWT.TITLE);
			
			guide = Guide.getGuide();
			mainShell = mainshell;
			shell = new Shell(myDisplay);
			shell.setSize(appSettings.getJsDebugWidth(), appSettings.getJsDebugHeight());
		    shell.addListener(SWT.Close, new Listener() {
		        public void handleEvent(Event event) {
		        	shell.setVisible(false);
		        	event.doit = !keepShellOpen;
		        }
		      });			

			FormLayout layout = new FormLayout();
			shell.setLayout(layout);

			pagesCombo = new Combo(shell, SWT.DROP_DOWN);
			pagesCombo.addSelectionListener(new debugSelectListener());

			//Set the layout and how it responds to screen resize
			FormData pagesComboFormData = new FormData();
			pagesComboFormData.top = new FormAttachment(0,0);
			pagesComboFormData.left = new FormAttachment(0,2);
			pagesComboFormData.right = new FormAttachment(80, -2);
			pagesCombo.setLayoutData(pagesComboFormData);

			SquareButton btnGo = new SquareButton(shell, SWT.PUSH);
			btnGo.setText(displayText.getString("DebugShellButtonGo"));
			FormData btnGoFormData = new FormData();
			btnGoFormData.top = new FormAttachment(0,0);
			btnGoFormData.right = new FormAttachment(90, -2);
			btnGoFormData.left = new FormAttachment(80, 0);
			btnGo.setLayoutData(btnGoFormData);
			btnGo.addSelectionListener(new GoButtonListener());

			SquareButton btnCurrent = new SquareButton(shell, SWT.PUSH);
			btnCurrent.setText(displayText.getString("DebugShellButtonReset"));
			FormData btnCurrentFormData = new FormData();
			btnCurrentFormData.top = new FormAttachment(0,0);
			btnCurrentFormData.right = new FormAttachment(100, -2);
			btnCurrentFormData.left = new FormAttachment(90, 0);
			btnCurrent.setLayoutData(btnCurrentFormData);
			btnCurrent.addSelectionListener(new CurrentButtonListener());

			tabFolder = new TabFolder (shell, SWT.NONE);
			FormLayout mainlayout = new FormLayout();
			tabFolder.setLayout(mainlayout);
			FormData mainCompFormData = new FormData();
			mainCompFormData.top = new FormAttachment(pagesCombo,0);
			mainCompFormData.left = new FormAttachment(0,0);
			mainCompFormData.right = new FormAttachment(100, 0);
			mainCompFormData.bottom = new FormAttachment(100, 0);
			tabFolder.setLayoutData(mainCompFormData);

			//Main Tab
			TabItem tabMain = new TabItem(tabFolder, SWT.NONE);
			tabMain.setText(displayText.getString("DebugShellTabMain"));

			tableComp = new Composite(tabFolder, SWT.SHADOW_NONE);
			FormLayout tbllayout = new FormLayout();
			tableComp.setLayout(tbllayout);
			FormData tableCompFormData = new FormData();
			tableCompFormData.top = new FormAttachment(0,0);
			tableCompFormData.left = new FormAttachment(0,0);
			tableCompFormData.right = new FormAttachment(100, 0);
			tableCompFormData.bottom = new FormAttachment(100,0);
			tableComp.setLayoutData(tableCompFormData);

			tabMain.setControl(tableComp);

			//Text Tab
			TabItem tabText = new TabItem(tabFolder, SWT.NONE);
			tabText.setText(displayText.getString("DebugShellTabText"));

			txtText = new Text(tabFolder, SWT.LEFT + SWT.MULTI + SWT.WRAP + SWT.READ_ONLY + SWT.V_SCROLL);
			FormData lblTexctFormData = new FormData();
			lblTexctFormData.top = new FormAttachment(0,0);
			lblTexctFormData.left = new FormAttachment(0,0);
			lblTexctFormData.right = new FormAttachment(100,0);
			lblTexctFormData.bottom = new FormAttachment(100,0);
			txtText.setLayoutData(lblTexctFormData);

			tabText.setControl(txtText);

			//Script Tab
			TabItem tabScript = new TabItem(tabFolder, SWT.NONE);
			tabScript.setText("JavaScript");

			txtScript = new Text(tabFolder, SWT.LEFT + SWT.MULTI + SWT.WRAP + SWT.READ_ONLY + SWT.V_SCROLL);
			FormData txtScriptFormData = new FormData();
			txtScriptFormData.top = new FormAttachment(0,0);
			txtScriptFormData.left = new FormAttachment(0,0);
			txtScriptFormData.right = new FormAttachment(100,0);
			txtScriptFormData.bottom = new FormAttachment(100,0);
			txtScript.setLayoutData(txtScriptFormData);

			tabScript.setControl(txtScript);

			//Variables Tab
			TabItem tabVariables = new TabItem(tabFolder, SWT.NONE);
			tabVariables.setText(displayText.getString("DebugShellTabVariables"));
			
			varScrlComp = new ScrolledComposite(tabFolder, SWT.V_SCROLL);
		    
			varComp = new Composite(varScrlComp, SWT.NONE);
			FormLayout varlayout = new FormLayout();
			varComp.setLayout(varlayout);
			FormData varCompFormData = new FormData();
			varCompFormData.top = new FormAttachment(0,0);
			varCompFormData.left = new FormAttachment(0,0);
			varCompFormData.right = new FormAttachment(100, 0);
			varCompFormData.bottom = new FormAttachment(100,0);
			varComp.setLayoutData(varCompFormData);
			
			
			varTable = new Table(varComp, SWT.NONE);
			varTable.setLinesVisible (true);
			varTable.setHeaderVisible (true);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 200;
			varTable.setLayoutData(data);
			String[]titles = {displayText.getString("DebugShellTableName"), displayText.getString("DebugShellTableValue")};
			for (int i=0; i<titles.length; i++) {
				TableColumn column = new TableColumn (varTable, SWT.NONE);
				column.setText (titles [i]);
			}	
			FormData varTableFormData = new FormData();
			varTableFormData.top = new FormAttachment(0,0);
			varTableFormData.left = new FormAttachment(0,0);
			varTableFormData.right = new FormAttachment(100,0);
			varTable.setLayoutData(varTableFormData);
			varTable.addSelectionListener(new varTableListener());

			txtVarKey = new Text(varComp, SWT.LEFT + SWT.SINGLE + SWT.BORDER);
			FormData txtVarKeyFormData = new FormData();
			txtVarKeyFormData.top = new FormAttachment(varTable,0);
			txtVarKeyFormData.left = new FormAttachment(0,0);
			txtVarKeyFormData.right = new FormAttachment(30,-2);
			txtVarKey.setLayoutData(txtVarKeyFormData);

			txtVarValue = new Text(varComp, SWT.LEFT + SWT.SINGLE + SWT.BORDER);
			FormData txtVarValueFormData = new FormData();
			txtVarValueFormData.top = new FormAttachment(varTable,0);
			txtVarValueFormData.left = new FormAttachment(30,0);
			txtVarValueFormData.right = new FormAttachment(90,-2);
			txtVarValue.setLayoutData(txtVarValueFormData);

			SquareButton btnSet = new SquareButton(varComp, SWT.PUSH);
			btnSet.setText(displayText.getString("DebugShellButtonSetValue"));
			FormData btnSetFormData = new FormData();
			btnSetFormData.top = new FormAttachment(varTable,0);
			btnSetFormData.right = new FormAttachment(100, -2);
			btnSetFormData.left = new FormAttachment(90, 0);
			btnSet.setLayoutData(btnSetFormData);
			btnSet.addSelectionListener(new SetButtonListener());
			
			varScrlComp.setContent(varComp);
			varScrlComp.setAlwaysShowScrollBars(true);
			varScrlComp.setExpandHorizontal(true);
			varScrlComp.setExpandVertical(true);
			tabVariables.setControl(varScrlComp);
			
			//Jscript console Tab
			TabItem tabConsole = new TabItem(tabFolder, SWT.NONE);
			tabConsole.setText("JavaScript Console");

			txtScriptConsole = new Text(tabFolder, SWT.LEFT + SWT.MULTI + SWT.WRAP + SWT.READ_ONLY + SWT.V_SCROLL);
			FormData txtScriptConsoleFormData = new FormData();
			txtScriptConsoleFormData.top = new FormAttachment(0,0);
			txtScriptConsoleFormData.left = new FormAttachment(0,0);
			txtScriptConsoleFormData.right = new FormAttachment(100,0);
			txtScriptConsoleFormData.bottom = new FormAttachment(100,0);
			txtScriptConsole.setLayoutData(txtScriptConsoleFormData);

			tabConsole.setControl(txtScriptConsole);

			varComp.layout();
			varScrlComp.layout();
			tabFolder.layout();
			shell.layout();
			shell.open();
			shell.setVisible(false);
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
		}
		logger.trace("Exit createShell");
		//return shell;
	}

	class varTableListener extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			TableItem tmpItem;
			tmpItem = (TableItem) e.item;
			txtVarKey.setText(tmpItem.getText(0));
			txtVarValue.setText(tmpItem.getText(1));
			super.widgetSelected(e);
		}
		
	}
	
	class GoButtonListener extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				String strPage;
				strPage = pagesCombo.getItem(pagesCombo.getSelectionIndex());
				mainShell.displayPage(strPage);
			}
			catch (Exception ex) {
				logger.error(ex.getLocalizedMessage(), ex);
			}
		}

	}

	class SetButtonListener extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				String flags = comonFuctions.GetFlags(guide.getFlags());
				HashMap<String, Object> scriptVars;
				scriptVars = guide.getSettings().getScriptVariables();

				Color color = myDisplay.getSystemColor(SWT.COLOR_YELLOW);

				if (txtVarKey.getText().equals("Flags")) {
					ArrayList<String> flagsarray = new ArrayList<String>();
					comonFuctions.SetFlags(txtVarValue.getText(), flagsarray);
					guide.setFlags(flagsarray);
					flags = comonFuctions.GetFlags(guide.getFlags());
				} else {
					scriptVars.put(txtVarKey.getText(), txtVarValue.getText());
					guide.getSettings().setScriptVariables(scriptVars);
					scriptVars = guide.getSettings().getScriptVariables();
				}
				varTable.removeAll();

				TableItem item = new TableItem (varTable, SWT.NONE);
				item.setBackground(color);
				item.setText (0, "Flags");
				item.setText (1, flags);

				for (Entry<String, Object> entry : scriptVars.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue().toString();
					item = new TableItem (varTable, SWT.NONE);
					item.setBackground(color);
					item.setText (0, key);
					item.setText (1, value);
				}

				for (int i=0; i<2; i++) {
					varTable.getColumn (i).pack ();
				}

			}
			catch (Exception ex) {
				logger.error(ex.getLocalizedMessage(), ex);
			}
		}

	}

	class CurrentButtonListener extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			try{
				String strPage;
				strPage = guide.getSettings().getCurrPage();
				setPage(strPage, true);
			}
			catch (Exception ex) {
				logger.error(ex.getLocalizedMessage(), ex);
			}
		}

	}

	class debugSelectListener extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			try{
				String strPage;
				strPage = pagesCombo.getItem(pagesCombo.getSelectionIndex());
				setPage(strPage, false);
			}
			catch (Exception ex) {
				logger.error(ex.getLocalizedMessage(), ex);
			}
		}

	}

	public void clearPagesCombo() {
		try {
			this.pagesCombo.removeAll();
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
		}
	}

	public void addPagesCombo(String page) {
		try {
			this.pagesCombo.add(page);
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
		}
	}

	public void setPage(String page, Boolean currPage) {
		try {
			Boolean refreshVars = false;
			Color color = myDisplay.getSystemColor(SWT.COLOR_YELLOW);
			if (currPage) {
				int currPageIndex = this.pagesCombo.indexOf(page);
				this.pagesCombo.select(currPageIndex);
				refreshVars = true;
			}
			this.dispPage = guide.getChapters().get("default").getPages().get(page);
			StringBuilder txtBuilder = new StringBuilder();
			for (int i = 0; i < dispPage.getTextCount(); i++) {
				if (dispPage.getText(i).canShow(guide.getFlags())) {
					txtBuilder.append(dispPage.getText(i).getText());
				}
			}
			txtText.setText(txtBuilder.toString());
			removePageTables();
			Control prevWidget;
			prevWidget = tableComp;

			Table pgeTable = new Table(tableComp, SWT.HIDE_SELECTION + SWT.NO_SCROLL + SWT.V_SCROLL);
			pgeTable.setLinesVisible (true);
			pgeTable.setHeaderVisible (true);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 200;
			pgeTable.setLayoutData(data);
			String[] titles = {"Page", "set", "unset", "If Set", "If not set"};
			for (int i=0; i<titles.length; i++) {
				TableColumn column = new TableColumn (pgeTable, SWT.NONE);
				column.setText (titles [i]);
			}	
			TableItem item = new TableItem (pgeTable, SWT.NONE);
			item.setBackground(color);
			item.setText (0, dispPage.getId());
			item.setText (1, dispPage.getSet());
			item.setText (2, dispPage.getUnSet());
			item.setText (3, dispPage.getIfSet());
			item.setText (4, dispPage.getIfNotSet());
			for (int i=0; i<titles.length; i++) {
				pgeTable.getColumn (i).pack ();
			}
			FormData pgeTableFormData = new FormData();
			pgeTableFormData.top = new FormAttachment(prevWidget,0);
			pgeTableFormData.left = new FormAttachment(0,0);
			pgeTableFormData.right = new FormAttachment(100,0);
			pgeTable.setLayoutData(pgeTableFormData);
			prevWidget = pgeTable;

			//Buttons
			try {
					btnTable = new Table(tableComp, SWT.HIDE_SELECTION + SWT.NO_SCROLL + SWT.V_SCROLL);
					btnTable.setLinesVisible (true);
					btnTable.setHeaderVisible (true);
					data = new GridData(SWT.FILL, SWT.FILL, true, true);
					data.heightHint = 200;
					btnTable.setLayoutData(data);
					titles = new String[] {"Button", "target", "jScript", "set", "unset", "If Set", "If not set", "image", "hotkey"};
					for (int i=0; i<titles.length; i++) {
						TableColumn column = new TableColumn (btnTable, SWT.NONE);
						column.setText (titles [i]);
					}	
					if (dispPage.getButtonCount() > 0) {
						for (int i=0; i<dispPage.getButtonCount(); i++) {
							Button button = dispPage.getButton(i);
							item = new TableItem (btnTable, SWT.NONE);
							item.setBackground(color);
							item.setText (0, button.getText());
							item.setText (1, button.getTarget());
							item.setText (2, button.getjScript());
							item.setText (3, button.getSet());
							item.setText (4, button.getUnSet());
							item.setText (5, button.getIfSet());
							item.setText (6, button.getIfNotSet());
							item.setText (7, button.getImage());
							item.setText (8, button.getHotKey());
						}
					}
					for (int i=0; i<titles.length; i++) {
						btnTable.getColumn (i).pack ();
					}
					FormData btnTableFormData = new FormData();
					btnTableFormData.top = new FormAttachment(prevWidget,0);
					btnTableFormData.left = new FormAttachment(0,0);
					btnTableFormData.right = new FormAttachment(100,0);
					btnTable.setLayoutData(btnTableFormData);
					prevWidget = btnTable;
			}
			catch (Exception ex) {
				logger.error(ex.getLocalizedMessage(), ex);
			}
			//Delays
			if (dispPage.getDelayCount() > 0) {
				try {
					Table dlyTable = new Table(tableComp, SWT.HIDE_SELECTION + SWT.NO_SCROLL + SWT.V_SCROLL);
					dlyTable.setLinesVisible (true);
					dlyTable.setHeaderVisible (true);
					data = new GridData(SWT.FILL, SWT.FILL, true, true);
					data.heightHint = 200;
					dlyTable.setLayoutData(data);
					titles = new String[] {"Delay", "style", "target", "jScript", "startWith", "set", "unset", "If Set", "If not set"};
					for (int i=0; i<titles.length; i++) {
						TableColumn column = new TableColumn (dlyTable, SWT.NONE);
						column.setText (titles [i]);
					}	
					for (int i=0; i<dispPage.getDelayCount(); i++) {
						Delay delay = dispPage.getDelay(i);
						item = new TableItem (dlyTable, SWT.NONE);
						item.setBackground(color);
						item.setText (0, String.valueOf(delay.getDelaySec()));
						item.setText (1, delay.getstyle());
						item.setText (2, delay.getTarget());
						item.setText (3, delay.getjScript());
						item.setText (4, delay.getStartWith());
						item.setText (5, delay.getSet());
						item.setText (6, delay.getUnSet());
						item.setText (7, delay.getIfSet());
						item.setText (8, delay.getIfNotSet());
					}
					for (int i=0; i<titles.length; i++) {
						dlyTable.getColumn (i).pack ();
					}
					FormData dlyTableFormData = new FormData();
					dlyTableFormData.top = new FormAttachment(prevWidget,0);
					dlyTableFormData.left = new FormAttachment(0,0);
					dlyTableFormData.right = new FormAttachment(100,0);
					dlyTable.setLayoutData(dlyTableFormData);
					prevWidget = dlyTable;
				}
				catch (Exception ex) {
					logger.error(ex.getLocalizedMessage(), ex);
				}

			}
			//images
			if (dispPage.getImageCount() > 0) {
				try {
					Table imgTable = new Table(tableComp, SWT.HIDE_SELECTION + SWT.NO_SCROLL + SWT.V_SCROLL);
					imgTable.setLinesVisible (true);
					imgTable.setHeaderVisible (true);
					data = new GridData(SWT.FILL, SWT.FILL, true, true);
					data.heightHint = 200;
					imgTable.setLayoutData(data);
					titles = new String[] {"Image", "If Set", "If not set"};
					for (int i=0; i<titles.length; i++) {
						TableColumn column = new TableColumn (imgTable, SWT.NONE);
						column.setText (titles [i]);
					}	
					for (int i=0; i<dispPage.getImageCount(); i++) {
						Image image = dispPage.getImage(i);
						item = new TableItem (imgTable, SWT.NONE);
						item.setBackground(color);
						item.setText (0, image.getId());
						item.setText (1, image.getIfSet());
						item.setText (2, image.getIfNotSet());
					}
					for (int i=0; i<titles.length; i++) {
						imgTable.getColumn (i).pack ();
					}
					FormData imgTableFormData = new FormData();
					imgTableFormData.top = new FormAttachment(prevWidget,0);
					imgTableFormData.left = new FormAttachment(0,0);
					imgTableFormData.right = new FormAttachment(100,0);
					imgTable.setLayoutData(imgTableFormData);
					prevWidget = imgTable;
				}
				catch (Exception ex) {
					logger.error(ex.getLocalizedMessage(), ex);
				}

			}
			//Audio
			if (dispPage.getAudioCount() > 0) {
				try {
					Table sndTable = new Table(tableComp, SWT.HIDE_SELECTION + SWT.NO_SCROLL + SWT.V_SCROLL);
					sndTable.setLinesVisible (true);
					sndTable.setHeaderVisible (true);
					data = new GridData(SWT.FILL, SWT.FILL, true, true);
					data.heightHint = 200;
					sndTable.setLayoutData(data);
					titles = new String[] {"audio", "target", "jScript", "startAt", "stopAt", "repeat", "If Set", "If not set"};
					for (int i=0; i<titles.length; i++) {
						TableColumn column = new TableColumn (sndTable, SWT.NONE);
						column.setText (titles [i]);
					}	
					for (int i=0; i<dispPage.getAudioCount(); i++) {
						Audio audio = dispPage.getAudio(i);
						item = new TableItem (sndTable, SWT.NONE);
						item.setBackground(color);
						item.setText (0, audio.getId());
						item.setText (1, audio.getTarget());
						item.setText (2, audio.getJscript());
						item.setText (3, audio.getStartAt());
						item.setText (4, audio.getStopAt());
						item.setText (5, audio.getRepeat());
						item.setText (6, audio.getIfSet());
						item.setText (7, audio.getIfNotSet());
					}
					for (int i=0; i<titles.length; i++) {
						sndTable.getColumn (i).pack ();
					}
					FormData sndTableFormData = new FormData();
					sndTableFormData.top = new FormAttachment(prevWidget,0);
					sndTableFormData.left = new FormAttachment(0,0);
					sndTableFormData.right = new FormAttachment(100,0);
					sndTable.setLayoutData(sndTableFormData);
					prevWidget = sndTable;
				}
				catch (Exception ex) {
					logger.error(ex.getLocalizedMessage(), ex);
				}

			}
			//video
			if (dispPage.getVideoCount() > 0) {
				try {
					Table vidTable = new Table(tableComp, SWT.HIDE_SELECTION + SWT.NO_SCROLL + SWT.V_SCROLL);
					vidTable.setLinesVisible (true);
					vidTable.setHeaderVisible (true);
					data = new GridData(SWT.FILL, SWT.FILL, true, true);
					data.heightHint = 200;
					vidTable.setLayoutData(data);
					titles = new String[] {"video", "target", "jScript", "startAt", "stopAt", "repeat", "If Set", "If not set"};
					for (int i=0; i<titles.length; i++) {
						TableColumn column = new TableColumn (vidTable, SWT.NONE);
						column.setText (titles [i]);
					}	
					for (int i=0; i<dispPage.getVideoCount(); i++) {
						Video video = dispPage.getVideo(i);
						item = new TableItem (vidTable, SWT.NONE);
						item.setBackground(color);
						item.setText (0, video.getId());
						item.setText (1, video.getTarget());
						item.setText (2, video.getJscript());
						item.setText (3, video.getStartAt());
						item.setText (4, video.getStopAt());
						item.setText (5, video.getRepeat());
						item.setText (6, video.getIfSet());
						item.setText (7, video.getIfNotSet());
					}
					for (int i=0; i<titles.length; i++) {
						vidTable.getColumn (i).pack ();
					}
					FormData vidTableFormData = new FormData();
					vidTableFormData.top = new FormAttachment(prevWidget,0);
					vidTableFormData.left = new FormAttachment(0,0);
					vidTableFormData.right = new FormAttachment(100,0);
					vidTable.setLayoutData(vidTableFormData);
					prevWidget = vidTable;
				}
				catch (Exception ex) {
					logger.error(ex.getLocalizedMessage(), ex);
				}

			}
			//metronome
			if (dispPage.getMetronomeCount() > 0) {
				try {
					Table bpmTable = new Table(tableComp, SWT.HIDE_SELECTION + SWT.NO_SCROLL + SWT.V_SCROLL);
					bpmTable.setLinesVisible (true);
					bpmTable.setHeaderVisible (true);
					data = new GridData(SWT.FILL, SWT.FILL, true, true);
					data.heightHint = 200;
					bpmTable.setLayoutData(data);
					titles = new String[] {"metronome", "If Set", "If Unset", "Resolution", "Loops", "Rhythm"};
					for (int i=0; i<titles.length; i++) {
						TableColumn column = new TableColumn (bpmTable, SWT.NONE);
						column.setText (titles [i]);
					}	
					for (int i=0; i<dispPage.getMetronomeCount(); i++) {
						Metronome metronome = dispPage.getMetronome(i);
						item = new TableItem (bpmTable, SWT.NONE);
						item.setBackground(color);
						item.setText (0, String.valueOf(metronome.getbpm()));
						item.setText (1, metronome.getIfSet());
						item.setText (2, metronome.getIfNotSet());
						item.setText (3, String.valueOf(metronome.getResolution()));
						item.setText (4, String.valueOf(metronome.getLoops()));
						item.setText (5, metronome.getRhythm());
					}
					for (int i=0; i<titles.length; i++) {
						bpmTable.getColumn (i).pack ();
					}
					FormData bpmTableFormData = new FormData();
					bpmTableFormData.top = new FormAttachment(prevWidget,0);
					bpmTableFormData.left = new FormAttachment(0,0);
					bpmTableFormData.right = new FormAttachment(100,0);
					bpmTable.setLayoutData(bpmTableFormData);
					prevWidget = bpmTable;
				}
				catch (Exception ex) {
					logger.error(ex.getLocalizedMessage(), ex);
				}

			}
			tableComp.layout();

			//Java Script
			txtScript.setText(dispPage.getjScript());

			//variables
			if (refreshVars) {
				prevWidget = varTable;
			}
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
		}
		try {
			tabFolder.layout();
			tabFolder.pack();
			tabFolder.update();
			shell.layout();
			varScrlComp.setMinSize(varComp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
		}
	}
	
	public void updateJConsole(String logText) {
		String conText = txtScriptConsole.getText();
		String conDelim = txtScriptConsole.getLineDelimiter();
		if (txtScriptConsole.getLineCount() > 100) {
			int lastLine = conText.lastIndexOf(conDelim);
			lastLine = conText.lastIndexOf(conDelim, lastLine - 1);
			conText = conText.substring(0, lastLine);
		}
		conText = logText  + conDelim + conText;
		txtScriptConsole.setText(conText);
	}
	
	public void clearJConsole() {
		txtScriptConsole.setText("");
	}
	
	public void refreshVars() {
		try {
			//HashMap<String, Object> scriptVars;
			Color color = myDisplay.getSystemColor(SWT.COLOR_YELLOW);
			Map<String, Object> treeMap = new TreeMap<String, Object>(guide.getSettings().getScriptVariables());
			String flags = comonFuctions.GetFlags(guide.getFlags());

			varTable.removeAll();

			TableItem item = new TableItem (varTable, SWT.NONE);
			item.setBackground(color);
			item.setText (0, "Flags");
			item.setText (1, flags);

			for (Entry<String, Object> entry : treeMap.entrySet()) {
				try {
					String key = entry.getKey();
					String value;
					Object objVal = entry.getValue();
					if (objVal != null) {
						value = comonFunctions.getVarAsString(objVal);
					} else {
						value = "null";
					}
					item = new TableItem (varTable, SWT.NONE);
					item.setBackground(color);
					item.setText (0, key);
					item.setText (1, value);
				}
				catch (Exception ex) {
					logger.error(ex.getLocalizedMessage(), ex);
				}
			}

			for (int i=0; i<2; i++) {
				varTable.getColumn (i).pack ();
			}
			tabFolder.layout();
			tabFolder.pack();
			tabFolder.update();
			shell.layout();
			varScrlComp.setMinSize(varComp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
		}
	}

	public void removePageTables() {
		//remove all the tables displayed for the previous page
		try {
			for (Control kid : tableComp.getChildren()) {
				kid.dispose();
			}
		} catch (Exception e) {
			logger.error("removePageTables " + e.getLocalizedMessage(), e);		
		}
	}

	public void closeShell() {
		try {
			keepShellOpen = false;
			shell.close();
		}
		catch (Exception ex) {
			logger.error("close shell " + ex.getLocalizedMessage(), ex);
		}
	}
	
	public void showDebug(){
		try {
			if (shell == null) {
				createShell(myDisplay, mainShell);
			} else {
				shell.setVisible(!shell.getVisible());
				if (shell.isVisible()) {
					shell.setActive();
				}
			}
		}
		catch (Exception ex) {
			logger.error("showDebug " + ex.getLocalizedMessage(), ex);
		}
	}

	public void setKeepShellOpen(Boolean keepShellOpen) {
		this.keepShellOpen = keepShellOpen;
	}
	
	public void addOverrideButton(Button button)
	{
		Color color = myDisplay.getSystemColor(SWT.COLOR_YELLOW);
		TableItem item = new TableItem (btnTable, SWT.NONE);
		item.setBackground(color);
		item.setText (0, button.getText());
		item.setText (1, button.getTarget());
		item.setText (2, button.getjScript());
		item.setText (3, button.getSet());
		item.setText (4, button.getUnSet());
		item.setText (5, button.getIfSet());
		item.setText (6, button.getIfNotSet());
		item.setText (7, button.getImage());
		item.setText (8, button.getHotKey());
		try {
			tabFolder.layout();
			tabFolder.pack();
			tabFolder.update();
			shell.layout();
			varScrlComp.setMinSize(varComp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
		}

	}
}
