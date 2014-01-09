package org.guideme.guideme.ui;

import java.awt.Frame;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import java.awt.Canvas;
import java.io.File;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Control;
import org.guideme.guideme.MainLogic;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.readers.XmlGuideReader;
import org.guideme.guideme.scripting.Jscript;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.settings.UserSettings;

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

public class MainShell {
	/*
	 Main screen and UI thread.
	 exposes methods that allow other components to update the screen components
	 and play video and music
	 */
	private static Logger logger = LogManager.getLogger();
	private static org.eclipse.swt.graphics.Color colourBlack;
	private static org.eclipse.swt.graphics.Color colourWhite;
	private AppSettings appSettings;
	private int MintFontSize;
	private int MintHtmlFontSize;
	private String strGuidePath;
	private int videoLoops = 0;
	private int videoStartAt = 0;
	private int videoStopAt = 0;
	private String videoTarget = "";
	private String videoJscript = "";
	private Boolean videoPlay = false;
	private GuideSettings guideSettings = new GuideSettings("startup");
	private UserSettings userSettings = null;
	private Label lblLeft;
	private Label lblCentre;
	private Label lblRight;
	private Browser imageLabel;
	private Browser brwsText;
	private SashForm sashform;
	private SashForm sashform2;
	private Composite btnComp;
	private Composite leftFrame;
	private Calendar calCountDown = null;
	private Shell shell;
	private Display myDisplay;
	private Font controlFont;
	private Composite mediaPanel;
	private MediaPlayerFactory mediaPlayerFactory;
	private EmbeddedMediaPlayer mediaPlayer;
	private Frame videoFrame;
	private Canvas videoSurfaceCanvas;
	private CanvasVideoSurface videoSurface;
	private Guide guide = Guide.getGuide();
	private MainShell mainShell;
	private MainLogic mainLogic = MainLogic.getMainLogic();
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	private XmlGuideReader xmlGuideReader = XmlGuideReader.getXmlGuideReader();
	private MetronomePlayer metronome;
	private Thread threadMetronome;
	private AudioPlayer audioPlayer;
	private Thread threadAudioPlayer;
	private Boolean videoOn = true;
	private String style = "";
	private String defaultStyle = "";
	private Double imgOffSet = 0.96; 
	private Boolean imgOverRide = false;
	private HashMap<String, com.snapps.swt.SquareButton> hotKeys = new HashMap<String, com.snapps.swt.SquareButton>();

	public Shell createShell(final Display display) {
		logger.trace("Enter createShell");
		// Initialise variable
		int[] intWeights1 = new int[2];
		int[] intWeights2 = new int[2];
		colourBlack = display.getSystemColor(SWT.COLOR_BLACK);
		colourWhite = display.getSystemColor(SWT.COLOR_WHITE);
		try {
			logger.trace("MainShell Get appSettings");
			appSettings = AppSettings.getAppSettings();

			//video flag
			videoOn = appSettings.getVideoOn();

			// font size
			MintFontSize = appSettings.getFontSize();

			// font size
			MintHtmlFontSize = appSettings.getHtmlFontSize();

			// path to the xml files
			strGuidePath = appSettings.getDataDirectory();

			// array to hold the various flags
			guideSettings.setFlags("");

			//width and height of the sizable containers on the screen
			intWeights1 = appSettings.getSash1Weights();
			intWeights2 = appSettings.getSash2Weights();

			userSettings = UserSettings.getUserSettings();

		} catch (NumberFormatException e) {
			logger.error("OnCreate NumberFormatException ", e);
		} catch (Exception e) {
			logger.error("OnCreate Exception ", e);
		}


		try {
			//Create the main UI elements
			myDisplay = display;
			if (appSettings.isFullScreen()) {
				shell = new Shell(myDisplay, SWT.NO_TRIM);
			} else {
				shell = new Shell(myDisplay);
			}
			shell.addShellListener(new shellCloseListen());
			display.addFilter(SWT.KeyDown, new shellKeyEventListener());
			
			//get primary monitor and its size
			Monitor primary = display.getPrimaryMonitor();
			Rectangle clientArea = primary.getClientArea();
			shell.setText("Guide Me (" + ComonFunctions.getVersion() + ")");
			FormLayout layout = new FormLayout();
			shell.setLayout(layout);

			Font sysFont = display.getSystemFont();
			FontData[] fD = sysFont.getFontData();
			fD[0].setHeight(MintFontSize);
			controlFont = new Font(display, fD);

			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			lblLeft = new Label(shell, SWT.LEFT);
			lblLeft.setBackground(colourBlack);
			lblLeft.setForeground(colourWhite);
			lblLeft.setFont(controlFont);
			lblLeft.setText(dateFormat.format(cal.getTime()));

			lblCentre = new Label(shell, SWT.CENTER);
			lblCentre.setBackground(colourBlack);
			lblCentre.setForeground(colourWhite);
			lblCentre.setFont(controlFont);
			lblCentre.setText("Title, Author");

			lblRight = new Label(shell, SWT.RIGHT);
			lblRight.setBackground(colourBlack);
			lblRight.setForeground(colourWhite);
			lblRight.setFont(controlFont);
			lblRight.setAlignment(SWT.CENTER);

			sashform = new SashForm(shell, SWT.HORIZONTAL);
			sashform.setBackground(colourBlack);
			
			leftFrame = new Composite(sashform,  SWT.SHADOW_NONE);
			leftFrame.setBackground(colourBlack);
			FormLayout layoutLF = new FormLayout();
			leftFrame.setLayout(layoutLF);
			
			
			mediaPanel = new Composite(leftFrame, SWT.EMBEDDED);
			FormLayout  layout3 = new FormLayout();
			mediaPanel.setLayout(layout3);
			mediaPanel.setBackground(colourBlack);
			mediaPanel.addControlListener(new mediaPanelListener());

			defaultStyle = "html { overflow-y: auto; } body { color: white; background-color: black; font-family: Tahoma; font-size:" + MintHtmlFontSize + "px } html, body, #wrapper { height:100%; width: 100%; margin: 0; padding: 0; border: 0; } #wrapper td { vertical-align: middle; text-align: center; }";
			style = defaultStyle;
			String strHtml = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html  xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" /><title></title><style type=\"text/css\">" + style + "</style></head><body></body></html>";
			imageLabel = new Browser(leftFrame, 0);
			imageLabel.setText(strHtml);
			imageLabel.setBackground(colourBlack);
			//imageLabel.setAlignment(SWT.CENTER);

			sashform2 = new SashForm(sashform, SWT.VERTICAL);
			sashform2.setBackground(colourBlack);

			brwsText = new Browser(sashform2, 0);
			brwsText.setText(strHtml);
			brwsText.setBackground(colourBlack);

			btnComp = new Composite(sashform2, SWT.SHADOW_NONE);
			btnComp.setBackground(colourBlack);
			RowLayout layout2 = new RowLayout();
			btnComp.setLayout(layout2);

			if (videoOn) {
				videoFrame = SWT_AWT.new_Frame(mediaPanel);         
				videoSurfaceCanvas = new Canvas();
	
				videoSurfaceCanvas.setBackground(java.awt.Color.black);
				videoFrame.add(videoSurfaceCanvas);
	
				mediaPlayerFactory = new MediaPlayerFactory("--no-video-title-show");
				mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
	
				videoSurface = mediaPlayerFactory.newVideoSurface(videoSurfaceCanvas);

				mediaPlayer.setVideoSurface(videoSurface);

				mediaPlayer.addMediaPlayerEventListener(new MediaListener());
			}

			//Set the layout and how it responds to screen resize
			FormData lblLeftFormData = new FormData();
			lblLeftFormData.top = new FormAttachment(0,0);
			lblLeftFormData.left = new FormAttachment(0,0);
			lblLeftFormData.right = new FormAttachment(33,0);
			lblLeft.setLayoutData(lblLeftFormData);

			FormData lblCentreFormData = new FormData();
			lblCentreFormData.top = new FormAttachment(0,0);
			lblCentreFormData.left = new FormAttachment(lblLeft,0);
			lblCentreFormData.right = new FormAttachment(lblRight,0);
			lblCentre.setLayoutData(lblCentreFormData);

			FormData lblRightFormData = new FormData();
			lblRightFormData.top = new FormAttachment(0,0);
			lblRightFormData.left = new FormAttachment(66, 0);
			lblRightFormData.right = new FormAttachment(100,0);
			lblRight.setLayoutData(lblRightFormData);

			FormData SashFormData = new FormData();
			SashFormData.top = new FormAttachment(lblLeft,0);
			SashFormData.left = new FormAttachment(0,0);
			SashFormData.right = new FormAttachment(100,0);
			SashFormData.bottom = new FormAttachment(100,0);
			sashform.setLayoutData(SashFormData);

			FormData SashFormData2 = new FormData();
			SashFormData2.top = new FormAttachment(sashform,0);
			SashFormData2.left = new FormAttachment(sashform,0);
			SashFormData2.right = new FormAttachment(sashform,0);
			SashFormData2.bottom = new FormAttachment(sashform,0);
			sashform2.setLayoutData(SashFormData2);

			FormData btnCompFormData = new FormData();
			btnCompFormData.top = new FormAttachment(sashform2,0);
			btnCompFormData.left = new FormAttachment(sashform2,0);
			btnCompFormData.right = new FormAttachment(sashform2, 0);
			btnCompFormData.bottom = new FormAttachment(sashform2, 0);
			btnComp.setLayoutData(btnCompFormData);

			FormData MediaPanelFormData = new FormData();
			MediaPanelFormData.top = new FormAttachment(0,0);
			MediaPanelFormData.left = new FormAttachment(0, 0);
			MediaPanelFormData.right = new FormAttachment(100,0);
			MediaPanelFormData.bottom = new FormAttachment(100,0);
			mediaPanel.setLayoutData(MediaPanelFormData);
			
			FormData imageLabelFormData = new FormData();
			imageLabelFormData.top = new FormAttachment(0,0);
			imageLabelFormData.left = new FormAttachment(0, 0);
			imageLabelFormData.right = new FormAttachment(100,0);
			imageLabelFormData.bottom = new FormAttachment(100,0);
			imageLabel.setLayoutData(imageLabelFormData);
			
			//Menu Bar
			Menu MenuBar = new Menu (shell, SWT.BAR);

			//Top Level File drop down
			MenuItem fileItem = new MenuItem (MenuBar, SWT.CASCADE);
			fileItem.setText ("&File");

			//Sub Menu for File
			Menu fileSubMenu = new Menu (shell, SWT.DROP_DOWN);
			//Associate it with the top level File menu
			fileItem.setMenu (fileSubMenu);

			//File Load menu item
			MenuItem fileLoadItem = new MenuItem (fileSubMenu, SWT.PUSH);
			fileLoadItem.setText ("&Load");
			fileLoadItem.addSelectionListener(new FileLoadListener());

			//File Restart menu item
			MenuItem fileRestartItem = new MenuItem (fileSubMenu, SWT.PUSH);
			fileRestartItem.setText ("&Restart");
			fileRestartItem.addSelectionListener(new FileRestartListener());

			//File Preferences menu item
			MenuItem filePreferencesItem = new MenuItem (fileSubMenu, SWT.PUSH);
			filePreferencesItem.setText ("&Application Preferences");
			filePreferencesItem.addSelectionListener(new FilePreferences());

			//File Preferences Guide menu item
			MenuItem filePreferencesGuideItem = new MenuItem (fileSubMenu, SWT.PUSH);
			filePreferencesGuideItem.setText ("&User Preferences");
			filePreferencesGuideItem.addSelectionListener(new FilePreferencesGuide());

			//File Guide Preferences menu item
			MenuItem fileGuidePreferencesItem = new MenuItem (fileSubMenu, SWT.PUSH);
			fileGuidePreferencesItem.setText ("&Guide Preferences");
			fileGuidePreferencesItem.addSelectionListener(new FileGuidePreferences());

			//File Exit menu item
			MenuItem fileExitItem = new MenuItem (fileSubMenu, SWT.PUSH);
			fileExitItem.setText ("&Exit");
			fileExitItem.addListener (SWT.Selection, new Listener () {
				public void handleEvent (Event e) {
					logger.trace("Enter Menu Exit");
					shell.close();
					logger.trace("Exit Menu Exit");
				}
			});

			// Add the menu bar to the shell
			shell.setMenuBar (MenuBar);

			// Resize the image if the control containing it changes size
			imageLabel.addControlListener(new ImageControlAdapter());

			// tell SWT to display the correct screen info
			shell.pack();
			shell.setMaximized(true);
			shell.setBounds(clientArea);
			try {
				sashform.setWeights(intWeights1);
			}
			catch (Exception ex2) {
				logger.error(ex2.getLocalizedMessage(), ex2);
			}
			try {
				sashform2.setWeights(intWeights2);
			}
			catch (Exception ex2) {
				logger.error(ex2.getLocalizedMessage(), ex2);
			}
			// timer that updates the clock field and handles any timed events
			// when loading wait 2 seconds before running it
			myDisplay.timerExec(2000, new shellTimer());
			metronome = new MetronomePlayer();
			threadMetronome = new Thread(metronome, "metronome");
			threadMetronome.start();
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
		}
		logger.trace("Exit createShell");
		mainShell = this;
		return shell;
	}

	class shellCloseListen  extends ShellAdapter {
		// Clean up stuff when the application closes
		@Override
		public void shellClosed(ShellEvent e) {
			try {
				int[] intWeights;
				intWeights = sashform.getWeights();
				appSettings.setSash1Weights(intWeights);
				int[] intWeights2;
				intWeights2 = sashform2.getWeights();
				appSettings.setSash2Weights(intWeights2);
				appSettings.setDataDirectory(strGuidePath);
				appSettings.saveSettings();
				controlFont.dispose();
				stopAll();
				metronome.metronomeKill();
				if (videoOn) {
					VideoRelease videoRelease = new VideoRelease();
					videoRelease.setVideoRelease(mediaPlayer, mediaPlayerFactory);
					Thread videoReleaseThread = new Thread(videoRelease, "videoRelease");
					videoReleaseThread.start();
				}
			}
			catch (Exception ex) {
				logger.error("shellCloseListen ", ex);
			}
			super.shellClosed(e);
		}

		public void handleEvent(Event event) {
		}
	}
	
	
	//hotkey stuff here
	class shellKeyEventListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			com.snapps.swt.SquareButton hotKeyButton;
			String key = String.valueOf(event.character);
			hotKeyButton = hotKeys.get(key);
			if (hotKeyButton != null) {
				String strTag;
				strTag = (String) hotKeyButton.getData("Set");
				if (!strTag.equals("")) {
					comonFunctions.SetFlags(strTag, guide.getFlags());
				}
				strTag = (String) hotKeyButton.getData("UnSet");
				if (!strTag.equals("")) {
					comonFunctions.UnsetFlags(strTag, guide.getFlags());
				}
				strTag = (String) hotKeyButton.getData("Target");
				String javascript = (String) hotKeyButton.getData("javascript");
				runJscript(javascript);
				mainLogic.displayPage(strTag, false, guide, mainShell, appSettings, userSettings, guideSettings);
			}
		}
	}
	
	
	class VideoRelease implements Runnable {
		//Do the release of the Video stuff (VLC) on a different thread to prevent it blocking the main UI thread
		private MediaPlayerFactory mediaPlayerFactoryThread;
		private EmbeddedMediaPlayer mediaPlayerThread;

		@Override
		public void run() {
			mediaPlayerThread.release();
			mediaPlayerFactoryThread.release();
			logger.trace("VideoRelease Exit");
		}

		public void setVideoRelease(EmbeddedMediaPlayer mediaPlayer, MediaPlayerFactory mediaPlayerFactory) {
			mediaPlayerFactoryThread = mediaPlayerFactory;
			mediaPlayerThread = mediaPlayer;
		}
		
	}

	class mediaPanelListener extends ControlAdapter {
		//resize the video if the container changes size
		@Override
		public void controlResized(ControlEvent e) {
			super.controlResized(e);
			if (videoOn) {
				Rectangle rect = mediaPanel.getClientArea();
				videoFrame.setSize(rect.width, rect.height);
			}
		}

	}
	
	
	class MediaListener extends MediaPlayerEventAdapter {
		//Video event listener
		
		//Video has finished
		@Override
		public void finished(MediaPlayer mediaPlayer) {
			logger.debug("MediaListener finished");
			super.finished(mediaPlayer);
			try {
				mediaPanel.setVisible(false);
				imageLabel.setVisible(true);
				leftFrame.layout(true);
			}
			catch (Exception ex) {
				logger.error(" MediaListener finished " + ex.getLocalizedMessage(), ex);
			}
		}

		//newState 5 indicates the video has finished
		//videoPlay can be set to false outside the code to tell it to stop
		//if the video finishes loop round again if a number of repeats has been set
		@Override
		public void mediaStateChanged(MediaPlayer lmediaPlayer, int newState) {
			super.mediaStateChanged(lmediaPlayer, newState);
			logger.debug("MediaListener newState: " + newState + " videoPlay: " + videoPlay +  " VideoTarget: " + videoTarget);
			try {
				if ((newState==5 || newState==6) && videoPlay){
						if (!videoTarget.equals(""))  {
							//run on the main UI thread
							myDisplay.syncExec(
									new Runnable() {
										public void run(){
											logger.debug("MediaListener Video Run: " + videoJscript + " videoTarget: " + videoTarget);
											mainShell.runJscript(videoJscript);
											mainShell.displayPage(videoTarget);
										}
									});											
						}
				}
			} catch (Exception e) {
				logger.error("mediaStateChanged " + e.getLocalizedMessage(), e);
			}
		}

		@Override
		public void error(MediaPlayer mediaPlayer) {
			logger.error("MediaPlayer error ");
			super.error(mediaPlayer);
		}
	}

	class FileLoadListener  extends SelectionAdapter {
		//File Load from the menu
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter Menu Load");
				//display a dialog to ask for a guide file to play
				FileDialog dialog = new FileDialog (shell, SWT.OPEN);
				//TODO Need to change this here to implement the new html format
				String [] filterNames = new String [] {"XML Files"};
				String [] filterExtensions = new String [] {"*.xml"};
				dialog.setFilterNames (filterNames);
				dialog.setFilterExtensions (filterExtensions);
				dialog.setFilterPath (strGuidePath);
				String strFileToLoad;
				try {
					strFileToLoad = dialog.open();
					try {
						if (strFileToLoad != null) {
							//if a guide file has been chosen load it 
							//default the initial directory for future loads to the current one
							strGuidePath = dialog.getFilterPath() + appSettings.getFileSeparator();
							appSettings.setDataDirectory(strGuidePath);
							//load the file it will return the start page and populate the guide object
							//TODO Need to change this here to implement the new html format
							String strPage = xmlGuideReader.loadXML(strFileToLoad, guide, appSettings);
							guideSettings = guide.getSettings();
							if (guide.getCss().equals("")) {
								style = defaultStyle;
							} else {
								style = guide.getCss();
							}
							//display the first page
							mainLogic.displayPage(strPage , false, guide, mainShell, appSettings, userSettings, guideSettings);
						}
					}
					catch (Exception ex5) {
						logger.error("Process Image error " + ex5.getLocalizedMessage(), ex5);
					}
				}
				catch (Exception ex4) {
					logger.error("Load Image Dialogue error " + ex4.getLocalizedMessage(), ex4);
				}
			}
			catch (Exception ex3) {
				logger.error("Load Image error " + ex3.getLocalizedMessage(), ex3);
			}
			logger.trace("Exit Menu Load");
			super.widgetSelected(e);
		}

	}

	// Restart 
	class FileRestartListener  extends SelectionAdapter {
		// File Restart From the menu
		// will restart the Guide from the start page
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter Menu Restart");
				//stop all activity for the current page to prevent timers jumping to a different page
				stopAll();
		        guide.getFlags().clear();
		        guide.getSettings().setPage("start");
		        guide.getSettings().setFlags(comonFunctions.GetFlags(guide.getFlags()));
				HashMap<String, String> scriptVariables = new HashMap<String, String>();
				guide.getSettings().setScriptVariables(scriptVariables);
				guide.getSettings().saveSettings();
				guideSettings = guide.getSettings();
				mainLogic.displayPage("start", false, guide, mainShell, appSettings, userSettings, guideSettings);
			}
			catch (Exception ex) {
				logger.error("Restart error " + ex.getLocalizedMessage(), ex);
			}
			logger.trace("Exit Menu Restart");
			super.widgetSelected(e);
		}

	}
	

	class FilePreferencesGuide  extends SelectionAdapter {
		//File Preferences from the menu
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter Preferences Guide Load");
				//display a dialog to ask for a guide file to play
				//load the file it will return the start page and populate the guide object
				//TODO Need to change this here to implement the new html format
				xmlGuideReader.loadXML("userSettingsUI.xml", guide, appSettings);
				guide.setMediaDirectory("userSettings");
				guideSettings = guide.getSettings();
				if (guide.getCss().equals("")) {
					style = defaultStyle;
				} else {
					style = guide.getCss();
				}
				//display the first page
				guide.setInPrefGuide(true);
				mainLogic.displayPage("start" , false, guide, mainShell, appSettings, userSettings, guideSettings);
			}
			catch (Exception ex3) {
				logger.error("Load Image error " + ex3.getLocalizedMessage(), ex3);
			}
			logger.trace("Exit Preferences Guide Load");
			super.widgetSelected(e);
		}

	}


	
	class FilePreferences  extends SelectionAdapter {
		//File Preferences from the menu
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter FilePreferences");
				//display a modal shell to change the preferences
				Shell prefShell = new PreferenceShell().createShell(myDisplay, userSettings, appSettings);
				prefShell.open();
				while (!prefShell.isDisposed()) {
					if (!myDisplay.readAndDispatch())
						myDisplay.sleep();
				}
			}
			catch (Exception ex) {
				logger.error(" FilePreferences " + ex.getLocalizedMessage());
			}
			super.widgetSelected(e);
		}

	}

	class FileGuidePreferences  extends SelectionAdapter {
		//File Guide Preferences from menu
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter FileGuidePreferences");
				//Display a modal shell for the guide specific preferences
				guideSettings = guide.getSettings();
				Shell prefShell = new GuidePreferenceShell().createShell(myDisplay, guideSettings, appSettings);
				prefShell.open();
				while (!prefShell.isDisposed()) {
					if (!myDisplay.readAndDispatch())
						myDisplay.sleep();
				}
			}
			catch (Exception ex) {
				logger.error(" FileGuidePreferences " + ex.getLocalizedMessage());
			}
			super.widgetSelected(e);
		}

	}
	
	// listener for the control that holds the image, 
	//will resize the image if the control is resized.
	class ImageControlAdapter extends ControlAdapter {
		public void controlResized(ControlEvent e) {
			logger.trace("Enter addControlListener");
			if (!imgOverRide) {
				int newWidth;
				int newHeight;

				//Label me = (Label)e.widget;
				Browser me = (Browser)e.widget;
				//Image myImage = (Image) me.getData("image");
				try {
					if (me.getData("imgPath") != null) {
						String imgPath = (String) me.getData("imgPath");
						double imageRatio = ((Double) me.getData("imageRatio")).doubleValue();
						Rectangle RectImage = me.getBounds();
						double dblScreenRatio = (double) RectImage.height / (double) RectImage.width;
						logger.trace("imgPath: " + imgPath);
						logger.trace("dblScreenRatio: " + dblScreenRatio);
						logger.trace("dblImageRatio: " + imageRatio);
						logger.trace("Lable Height: " + RectImage.height);
						logger.trace("Lable Width: " + RectImage.width);

						if (dblScreenRatio > imageRatio) {
							newHeight = (int) (((double) RectImage.width * imageRatio) * imgOffSet);
							newWidth = (int) ((double) RectImage.width * imgOffSet);
							logger.trace("New GT Dimentions: H: " + newHeight + " W: " + newWidth);
						} else {
							newHeight = (int) ((double) RectImage.height * imgOffSet);
							newWidth = (int) (((double) RectImage.height / imageRatio) * imgOffSet);
							logger.trace("New LT Dimentions: H: " + newHeight + " W: " + newWidth);
						}
						String strHtml = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html  xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" /><title></title><style type=\"text/css\">" + style + "</style></head><body><table id=\"wrapper\"><tr><td><img src=\"" + imgPath + "\" height=\"" + newHeight + "\" width=\"" + newWidth + "\" /></td></tr></table></body></html>";
						me.setText(strHtml, true);
						//Image tmpImage = me.getImage();
						//me.setImage(resize(myImage, newWidth, newHeight));
						//tmpImage.dispose();
					}
				}
				catch (Exception ex7) {
					logger.error("Shell Resize error " + ex7.getLocalizedMessage(), ex7);
				}
				logger.trace("Exit addControlListener");
			}
		}
	}

	/*
	// Returns the image passed in resized to the width and height passed in
	private static Image resize(Image image, int width, int height) {
		try {
			logger.trace("Enter resize");
			Image scaled = new Image(Display.getDefault(), width, height);
			GC gc = new GC(scaled);
			gc.setAntialias(SWT.ON);
			gc.setInterpolation(SWT.HIGH);
			gc.drawImage(image, 0, 0, 
					image.getBounds().width, image.getBounds().height, 
					0, 0, width, height);
			gc.dispose();
			logger.trace("Exit resize");
			return scaled;
		}
		catch (Exception ex) {
			logger.error(" resize " + ex.getLocalizedMessage(), ex);
			logger.trace("Exit resize");
			return null;
		}

	}
	*/

	class shellTimer implements Runnable {
		//Timer to update:
		//the clock
		//count down timer
		//handle going to new page when timer counts down to 0
		@Override
		public void run() {
			try {
				//logger.trace("Enter shellTimer");
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				String javascript;
				long diff;
				int intSeconds;
				int intMinutes;
				String strSeconds;
				String strMinutes;
				String strTimeLeft;
				if (calCountDown != null) {
					if (cal.after(calCountDown)){
						//Delay has reached zero
						calCountDown = null;
						lblRight.setText("");
						comonFunctions.SetFlags(guide.getDelaySet(), guide.getFlags());
						comonFunctions.UnsetFlags(guide.getDelayUnSet(), guide.getFlags());
						javascript = guide.getDelayjScript();
						mainShell.runJscript(javascript);
						mainLogic.displayPage(guide.getDelTarget(), false, guide, mainShell, appSettings, userSettings, guideSettings);
					} else {
						if (guide.getDelStyle().equals("hidden")) {
							//a hidden one
							lblRight.setText("");
						} else if (guide.getDelStyle().equals("secret")) {
							//secret timer so display ?? to show there is one but not how long
							lblRight.setText("??:??");
						} else {
							//Normal delay so display seconds left 
							//(plus any offset if you are being sneaky) 
							diff = calCountDown.getTimeInMillis() - cal.getTimeInMillis();
							diff = diff + (guide.getDelStartAtOffSet() * 1000);
							intSeconds = (int) ((diff / 1000) + 1);
							intMinutes = intSeconds / 60;
							intSeconds = intSeconds - (intMinutes * 60);
							strSeconds = String.valueOf(intSeconds);
							strSeconds = "0" + strSeconds;
							strSeconds = strSeconds.substring(strSeconds.length() - 2);
							strMinutes = String.valueOf(intMinutes);
							strMinutes = "0" + strMinutes;
							strMinutes = strMinutes.substring(strMinutes.length() - 2);
							strTimeLeft = strMinutes + ":" + strSeconds;
							lblRight.setText(strTimeLeft);
						}
						
					}
				}
				lblLeft.setText(dateFormat.format(cal.getTime()));
				dateFormat = null;
				cal = null;
				javascript = null;
				strSeconds = null;
				strMinutes = null;
				strTimeLeft = null;
				//re run in 0.1 seconds
				myDisplay.timerExec(100, new shellTimer());
			}
			catch (Exception ex) {
				logger.error(" shellTimer " + ex.getLocalizedMessage(), ex);
			}
			//logger.trace("Exit shellTimer");
		}
	}

	public Calendar getCalCountDown() {
		//return the time a delay should end
		return calCountDown;
	}

	public void setCalCountDown(Calendar calCountDown) {
		//set the time a delay should end
		this.calCountDown = calCountDown;
	}

	public void setLblLeft(String lblLeft) {
		//header to the left of the screen
		//usually displays teh delay
		this.lblLeft.setText(lblLeft);
	}

	public void setLblCentre(String lblCentre) {
		//header to the centre of the screen
		//usually displays the title and author
		this.lblCentre.setText(lblCentre);
	}

	public void setLblRight(String lblRight) {
		//header to the right of the screen
		//usually displays the clock 
		this.lblRight.setText(lblRight);
	}

	public void setImageLabel(String imgPath, String strImage) {
		//display an image in the area to the left of the screen
		int newWidth;
		int newHeight;
		imgOverRide = false;
		//Image tmpImage = (Image) imageLabel.getData("image");
		Image memImage = new Image(myDisplay, imgPath);
		imageLabel.setData("imgPath", imgPath);
		//imageLabel.setData("image", memImage);
		//if (tmpImage != null) {
		//	tmpImage.dispose();
		//}
		try {
			ImageData imgData = memImage.getImageData();
			Rectangle RectImage = imageLabel.getBounds();
			double dblScreenRatio = (double) RectImage.height / (double) RectImage.width;
			logger.trace("dblScreenRatio: " + dblScreenRatio);
			double dblImageRatio = (double) imgData.height / (double) imgData.width;
			imageLabel.setData("imageRatio", Double.valueOf(dblImageRatio));
			logger.trace("Lable Height: " + RectImage.height);
			logger.trace("Lable Width: " + RectImage.width);
			logger.trace("Image Height: " + imgData.height);
			logger.trace("Image Width: " + imgData.width);

			if (dblScreenRatio > dblImageRatio) {
				newHeight = (int) (((double) RectImage.width * dblImageRatio) * imgOffSet);
				newWidth = (int) ((double) RectImage.width * imgOffSet);
				logger.trace("New GT Dimentions: H: " + newHeight + " W: " + newWidth);
			} else {
				newHeight = (int) ((double) RectImage.height * imgOffSet);
				newWidth = (int) (((double) RectImage.height / dblImageRatio) * imgOffSet);
				logger.trace("New LT Dimentions: H: " + newHeight + " W: " + newWidth);
			}
			//Image tmpImage2 = imageLabel.getImage();
			//imageLabel.setImage(resize(memImage, newWidth, newHeight));
			memImage.dispose();
			memImage = null;
			//if (tmpImage2 != null) {
			//	tmpImage2.dispose();
			//}
			String strHtml = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html  xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" /><title></title><style type=\"text/css\">" + defaultStyle + "</style></head><body><table id=\"wrapper\"><tr><td><img src=\"" + imgPath + "\" height=\"" + newHeight + "\" width=\"" + newWidth + "\" /></td></tr></table></body></html>";
			imageLabel.setText(strHtml, true);
			logger.trace("Open: " + imgPath);
		}
		catch (Exception ex6) {
			logger.error("Process Image error " + ex6.getLocalizedMessage(), ex6);
		}
		mediaPanel.setVisible(false);
    	this.imageLabel.setVisible(true);
		leftFrame.layout(true);
	}

	public void setImageHtml(String leftHtml) {
		try {
			logger.trace("setImageHtml: " + leftHtml);
			imgOverRide = true;
			imageLabel.setText(leftHtml, true);
		}
		catch (Exception ex) {
			logger.error("setImageHtml error " + ex.getLocalizedMessage(), ex);
		}
	}
	
	public void playVideo(String video, int startAt, int stopAt, int loops, String target, String jscript) {
		//plays a video in the area to the left of the screen
		//sets the number of loops, start / stop time and any page to display if the video finishes
		//starts the video using a non UI thread so VLC can't hang the application
		if (videoOn) {
			try {
				this.imageLabel.setVisible(false);
				this.mediaPanel.setVisible(true);
				leftFrame.layout(true);
				Rectangle rect = mediaPanel.getClientArea();
				videoFrame.setSize(rect.width, rect.height);
				videoLoops = loops;
				videoTarget = target;
				videoStartAt = startAt;
				videoStopAt = stopAt;
				videoJscript = jscript;
				videoPlay = true;
				String mrlVideo = "file:///" + video;
				logger.debug("MainShell playVideo: " + mrlVideo + " videoLoops: " + videoLoops + " videoTarget: " + videoTarget + " videoPlay: " + videoPlay);
				VideoPlay videoPlay = new VideoPlay();
				videoPlay.setVideoPlay(mediaPlayer, mrlVideo);
				Thread videoPlayThread = new Thread(videoPlay, "videoPlay");
				videoPlayThread.start();
			} catch (Exception e) {
				logger.error("playVideo " + e.getLocalizedMessage(), e);		
			}
		}
	}

	class VideoPlay implements Runnable {
		//code to start the video on a separate thread
		private EmbeddedMediaPlayer mediaPlayer;
		private String video;
		
		@Override
		public void run() {
			try {
				mediaPlayer.setVolume(appSettings.getVideoVolume());
				mediaPlayer.setPlaySubItems(true);
				if (videoStartAt == 0 && videoStopAt == 0 && videoLoops == 0) {
					mediaPlayer.playMedia(video);
				} else {
					 List<String> vlcArgs = new ArrayList<String>();
					 if (videoStartAt > 0) {
						 vlcArgs.add("start-time=" + videoStartAt);
					 }
					 if (videoStopAt > 0) {
						 vlcArgs.add("stop-time=" + videoStopAt);
					 }
					 if (videoLoops > 0) {
						 vlcArgs.add("input-repeat=" + videoLoops);
					 }
					 mediaPlayer.playMedia(video, vlcArgs.toArray(new String[vlcArgs.size()]));
				}
			} catch (Exception e) {
				logger.error("VideoPlay run " + e.getLocalizedMessage(), e);		
			}
		}

		public void setVideoPlay(EmbeddedMediaPlayer mediaPlayer, String video) {
			this.mediaPlayer = mediaPlayer;
			this.video = video;
		}

	}
	
	public void clearImage() {
		//imageLabel.setImage(null);
		String strHTML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html  xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" /><title></title><style type=\"text/css\">" + style +  "</style></head><body></body></html>";
		imageLabel.setText(strHTML, true);
	}

	
	
	public void playAudio(String audio, int startAt, int stopAt, int loops, String target, String jscript) {
		// run audio on another thread
		try {
			if (audioPlayer != null) {
				audioPlayer.audioStop();
				logger.trace("playAudio audioStop");
			}
			audioPlayer = new AudioPlayer(audio, startAt, stopAt, loops, target, mainShell, jscript);
			threadAudioPlayer = new Thread(audioPlayer, "audioPlayer");
			threadAudioPlayer.start();
		} catch (Exception e) {
			logger.error("playAudio " + e.getLocalizedMessage(), e);		
		}
	}
	
	public void setBrwsText(String brwsText) {
		//set HTML to be displayed in the browser control to the right of the screen
		String strHTML;
		try {
			String strTemp = brwsText;
			//strTemp = strTemp.replace("<P>", "");
			//strTemp = strTemp.replace("<p>", "");
			//strTemp = strTemp.replace("</P>", "<br>");
			//strTemp = strTemp.replace("</p>", "<br>");
			//strTemp = strTemp.replace("<DIV>", "");
			//strTemp = strTemp.replace("<div>", "");
			//strTemp = strTemp.replace("</DIV>", "<br>");
			//strTemp = strTemp.replace("</div>", "<br>");
			//if (strTemp.endsWith("<br>")) {
			//	strTemp = strTemp.substring(0, strTemp.length() - 4);
			//}
				
				strHTML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html  xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" /><title></title><style type=\"text/css\">" + style +  "</style></head><body>" + strTemp + "</body></html>";
				this.brwsText.setText(strHTML);
		} catch (Exception e1) {
			logger.error("displayPage Text Exception " + e1.getLocalizedMessage(), e1);
			strHTML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html  xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" /><title></title><style type=\"text/css\">" + style +  "</style></head><body></body></html>";
			this.brwsText.setText(strHTML);
		}
	}

	public void removeButtons() {
		//remove all the buttons displayed for the previous page
        try {
			for (Control kid : btnComp.getChildren()) {
				Image bkgrndImage = kid.getBackgroundImage();
				if (bkgrndImage != null) {
					bkgrndImage.dispose();
				}
			    kid.dispose();
			  }
		} catch (Exception e) {
			logger.error("removeButtons " + e.getLocalizedMessage(), e);		
		}
	}
	
	public void addDelayButton(Guide guide) {
		//add a delay button
		//used when debug is on to simulate the delay without actually waiting for it
		try {
			com.snapps.swt.SquareButton btnDynamic = new com.snapps.swt.SquareButton(btnComp, SWT.PUSH );
			btnDynamic.setFont(controlFont);
			btnDynamic.setText("Delay");

			// record any button set / unset
			if (!guide.getDelaySet().equals("")) {
				btnDynamic.setData("Set", guide.getDelaySet());
			} else {
				btnDynamic.setData("Set", "");
			}
			if (!guide.getDelayUnSet().equals("")) {
				btnDynamic.setData("UnSet", guide.getDelayUnSet());
			} else {
				btnDynamic.setData("UnSet", "");
			}
			btnDynamic.setData("Target", guide.getDelTarget());
			btnDynamic.setData("javascript", guide.getDelayjScript());
			btnDynamic.addSelectionListener(new DynamicButtonListner());
		} catch (Exception e) {
			logger.error("addDelayButton " + e.getLocalizedMessage(), e);		
		}
	}
	
	public void addButton(Button button, String javascript) {
		//add a normal button to the screen 
		String strBtnTarget;
		String strBtnText;
		String strBtnImage;
		try {
			strBtnTarget = button.getTarget();
			strBtnText = button.getText();
			strBtnImage = button.getImage();
			if (!strBtnImage.equals("")){
				String imgPath = mainLogic.getMediaFullPath(strBtnImage, appSettings.getFileSeparator(), appSettings, guide);
				File flImage = new File(imgPath);
				if (flImage.exists()){
					strBtnImage = imgPath;
				} else {
					strBtnImage = "";
				}
			}
			com.snapps.swt.SquareButton btnDynamic = new com.snapps.swt.SquareButton(btnComp, SWT.PUSH );
			btnDynamic.setFont(controlFont);
			btnDynamic.setText(strBtnText);
			if (!strBtnImage.equals("")){
				btnDynamic.setBackgroundImage(new Image(myDisplay, strBtnImage));
				btnDynamic.setBackgroundImageStyle(com.snapps.swt.SquareButton.BG_IMAGE_FIT);
			}

			// record any button set / unset
			String strButtonSet;
			String strButtonUnSet;
			strButtonSet = button.getSet();
			if (!strButtonSet.equals("")) {
				btnDynamic.setData("Set", strButtonSet);
			} else {
				btnDynamic.setData("Set", "");
			}
			strButtonUnSet = button.getUnSet();
			if (!strButtonUnSet.equals("")) {
				btnDynamic.setData("UnSet", strButtonUnSet);
			} else {
				btnDynamic.setData("UnSet", "");
			}
			btnDynamic.setData("javascript", javascript);
			logger.debug("displayPage Button Text " + strBtnText + " Target " + strBtnTarget + " Set " + strButtonSet + " UnSet " + strButtonUnSet);
			
			String hotKey = button.getHotKey();
			if (!hotKey.equals("")) {
				hotKeys.put(hotKey, btnDynamic);
			}

			btnDynamic.setData("Target", strBtnTarget);
			btnDynamic.addSelectionListener(new DynamicButtonListner());
		} catch (Exception e) {
			logger.error("addButton " + e.getLocalizedMessage(), e);		
		}
		
	}

    // Click event code for the dynamic buttons
	class DynamicButtonListner extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			try {
				
				logger.trace("Enter DynamicButtonListner");
				String strTag;
				com.snapps.swt.SquareButton btnClicked;
				btnClicked = (com.snapps.swt.SquareButton) event.widget;
				strTag = (String) btnClicked.getData("Set");
				if (!strTag.equals("")) {
					comonFunctions.SetFlags(strTag, guide.getFlags());
				}
				strTag = (String) btnClicked.getData("UnSet");
				if (!strTag.equals("")) {
					comonFunctions.UnsetFlags(strTag, guide.getFlags());
				}
				strTag = (String) btnClicked.getData("Target");
				String javascript = (String) btnClicked.getData("javascript");
				runJscript(javascript);
				mainLogic.displayPage(strTag, false, guide, mainShell, appSettings, userSettings, guideSettings);
			}
			catch (Exception ex) {
				logger.error(" DynamicButtonListner " + ex.getLocalizedMessage(), ex);
			}
			logger.trace("Exit DynamicButtonListner");
		}
	}

	//run the javascript function passed 
	public void runJscript(String function) {
		if (function == null) function = "";
		if (! function.equals("")) {
			getFormFields();
			Jscript jscript = new Jscript(guide, userSettings, appSettings, guide.getInPrefGuide());
			Page objCurrPage = guide.getChapters().get(guideSettings.getChapter()).getPages().get(guideSettings.getPage());
			String pageJavascript = objCurrPage.getjScript();
			jscript.runScript(pageJavascript, function, false);
		}
	}

	//get any fields from the html form and store them in guide settings for use in the next java script call.
	private void getFormFields() {
		String evaluateScript = "" +
				"var vforms = document.forms;" +
				"var vreturn = '';" +
				"for (var formidx = 0; formidx < vforms.length; formidx++) {" +
					"var vform = vforms[formidx];" +
					"for (var controlIdx = 0; controlIdx < vform.length; controlIdx++) {" +
						"var control = vform.elements[controlIdx];" +
						"if (control.type === \"select-one\") {" +
							"var item = control.selectedIndex;" +
							"var value = control.item(item).value;" +
							"vreturn = vreturn + control.name + '¬' + value + '¬' + control.type + '¬' + control.checked + '|';" +
						"} else {" +
							"vreturn = vreturn + control.name + '¬' + control.value + '¬' + control.type + '¬' + control.checked + '|';" +
						"}" +
					"}" +
				"}" +
				"return vreturn;";
		String node = (String) brwsText.evaluate(evaluateScript);
		String fields[] = node.split("\\|");
		String values[]; 
		String name;
		String value;
		String type;
		String checked;
		for (int i = 0; i < fields.length; i++) {
			values = fields[i].split("¬");
			if (!fields[i].equals("")) {
				name = values[0];
				value = values[1];
				type = values[2];
				checked = values[3];
				if (type.equals("checkbox")) {
					guideSettings.setFormField(name, checked);
				}
				if (type.equals("radio")) {
					if (checked.equals("true")) {
						guideSettings.setFormField(name, value);
					}
				}
				if (type.equals("text")) {
					guideSettings.setFormField(name, value);
				}
				
				if (type.equals("select-one")) {
					guideSettings.setFormField(name, value);
				}

				logger.trace(name + "|" + value +  "|" + type +  "|" + checked);
			}
		}
	}
		
		
	//force a redisplay of the button are
	//set focus to the last button
	public void layoutButtons() {
		btnComp.layout();
	    Control[] controls = this.btnComp.getChildren();
	    if (controls.length > 0) {
	      controls[0].setFocus();
	    }
	    controls = null;
	}

	public void setMetronomeBPM(int metronomeBPM, int loops, int resolution, String Rhythm) {
		// run metronome on another thread
		try {
			logger.trace("setMetronomeBPM");
			metronome.metronomeStart(metronomeBPM, appSettings.getMidiInstrument(), loops, resolution, Rhythm, appSettings.getMidiVolume());
		} catch (Exception e) {
			logger.error(" setMetronomeBPM " + e.getLocalizedMessage(), e);
		}
	}

	public void displayPage(String target) {
		mainLogic.displayPage(target, false, guide, mainShell, appSettings, userSettings, guideSettings);
	}
	
	public void stopMetronome() {
		metronome.metronomeStop();
	}
	
	public void stopAudio() {
		if (audioPlayer != null) {
			audioPlayer.audioStop();
		}
		audioPlayer = null;
		threadAudioPlayer = null;
	}

	public void stopVideo() {
		if (videoOn) {
			try {
				if (mediaPlayer != null) {
					videoLoops = 0;
					videoTarget = "";
					videoPlay = false;
					logger.debug("MainShell stopVideo ");
					VideoStop videoStop = new VideoStop();
					videoStop.setMediaPlayer(mediaPlayer);
					Thread videoStopThread = new Thread(videoStop, "videoStop");
					mediaPanel.setVisible(false);
					imageLabel.setVisible(true);
					leftFrame.layout(true);
					videoStopThread.start();
				}
			} catch (Exception e) {
				logger.error(" stopVideo " + e.getLocalizedMessage(), e);
			}
		}
	}
	
	class VideoStop implements Runnable {
		private EmbeddedMediaPlayer mediaPlayer;

		public void setMediaPlayer(EmbeddedMediaPlayer mediaPlayer) {
			this.mediaPlayer = mediaPlayer;
		}

		@Override
		public void run() {
			logger.debug("MainShell VideoStop run: " + mediaPlayer.mrl());
			mediaPlayer.stop();
		}
		
	}
	
	public void stopDelay() {
		calCountDown = null;
		lblRight.setText("");
	}

	public void stopAll() {
		hotKeys = new HashMap<String, com.snapps.swt.SquareButton>();
		stopDelay();
		stopMetronome();
		stopAudio();
		stopVideo();
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setDefaultStyle() {
		this.style = defaultStyle;
	}

}