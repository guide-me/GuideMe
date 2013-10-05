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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
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
	private String version = "0.0.1";
	/*
	 Main screen and UI thread.
	 exposes methods that allow other components to update the screen components
	 and play video and music
	 */
	private static Logger logger = LogManager.getLogger();
	private static org.eclipse.swt.graphics.Color colourBlack;
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
	private Label imageLabel;
	private Browser brwsText;
	private SashForm sashform;
	private SashForm sashform2;
	private Composite btnComp;
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

	public Shell createShell(final Display display) {
		logger.trace("Enter createShell");
		// Initialise variable
		int[] intWeights1 = new int[2];
		int[] intWeights2 = new int[2];
		colourBlack = display.getSystemColor(SWT.COLOR_BLACK);
		try {
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
			shell = new Shell(myDisplay);
			shell.addShellListener(new shellCloseListen());
			
			//get primary monitor and its size
			Monitor primary = display.getPrimaryMonitor();
			Rectangle clientArea = primary.getClientArea();
			shell.setText("Guide Me (" + version + ")");
			FormLayout layout = new FormLayout();
			shell.setLayout(layout);

			Font sysFont = display.getSystemFont();
			FontData[] fD = sysFont.getFontData();
			fD[0].setHeight(MintFontSize);
			controlFont = new Font(display, fD);

			lblLeft = new Label(shell, SWT.LEFT);
			lblLeft.setFont(controlFont);

			lblCentre = new Label(shell, SWT.CENTER);
			lblCentre.setFont(controlFont);
			lblCentre.setText("Title, Author");

			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			lblRight = new Label(shell, SWT.RIGHT);
			lblRight.setFont(controlFont);
			lblRight.setText(dateFormat.format(cal.getTime()));

			sashform = new SashForm(shell, SWT.HORIZONTAL);
			sashform.setBackground(colourBlack);
			
			mediaPanel = new Composite(sashform, SWT.EMBEDDED);
			FillLayout layout3 = new FillLayout();
			mediaPanel.setLayout(layout3);
			mediaPanel.setBackground(colourBlack);
			mediaPanel.addControlListener(new mediaPanelListener());

			imageLabel = new Label(mediaPanel, SWT.BORDER);
			imageLabel.setBackground(colourBlack);
			imageLabel.setAlignment(SWT.CENTER);

			sashform2 = new SashForm(sashform, SWT.VERTICAL);
			sashform2.setBackground(colourBlack);

			String strHtml = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html  xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" /><title></title><style type=\"text/css\"> body { color: white; background-color: black; font-family: Tahoma; font-size:"
					+ MintHtmlFontSize + "px } </style></head><body></body></html>";
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
			filePreferencesItem.setText ("&Preferences");
			filePreferencesItem.addSelectionListener(new FilePreferences());

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
			sashform.setWeights(intWeights1);
			sashform2.setWeights(intWeights2);
			shell.setBounds(clientArea);
			shell.setMaximized(true);
			// timer that updates the clock field and handles any timed events
			// when loading wait 2 seconds before running it
			myDisplay.timerExec(2000, new shellTimer());
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
				if (videoOn) {
					VideoRelease videoRelease = new VideoRelease();
					videoRelease.setVideoRelease(mediaPlayer, mediaPlayerFactory);
					Thread videoReleaseThread = new Thread(videoRelease);
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
	
	class VideoRelease implements Runnable {
		//Do the release of the Video stuff (VLC) on a different thread to prevent it blocking the main UI thread
		private MediaPlayerFactory mediaPlayerFactoryThread;
		private EmbeddedMediaPlayer mediaPlayerThread;

		@Override
		public void run() {
			mediaPlayerThread.release();
			mediaPlayerFactoryThread.release();
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
				videoFrame.setVisible(false);
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
			logger.debug("MediaListener newState " + newState + " videoPlay:" + videoPlay);
			try {
				if ((newState==5 || newState==6) && videoPlay){
						if (!videoTarget.equals(""))  {
							//run on the main UI thread
							myDisplay.syncExec(
									new Runnable() {
										public void run(){
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
							//load the file it will return the start page and populate the guide object
							//TODO Need to change this here to implement the new html format
							String strPage = xmlGuideReader.loadXML(strFileToLoad, guide);
							guideSettings = guide.getSettings();
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
			int newWidth;
			int newHeight;

			Label me = (Label)e.widget;
			Image myImage = (Image) me.getData("image");
			try {
				if (myImage != null) {
					double imageRatio = ((Double) me.getData("imageRatio")).doubleValue();
					Rectangle RectImage = me.getBounds();
					double dblScreenRatio = (double) RectImage.height / (double) RectImage.width;
					logger.info("dblScreenRatio: " + dblScreenRatio);
					logger.info("dblImageRatio: " + imageRatio);
					logger.info("Lable Height: " + RectImage.height);
					logger.info("Lable Width: " + RectImage.width);

					if (dblScreenRatio > imageRatio) {
						newHeight = (int) ((double) RectImage.width * imageRatio);
						newWidth = RectImage.width;
						logger.info("New GT Dimentions: H: " + newHeight + " W: " + newWidth);
					} else {
						newHeight = RectImage.height;
						newWidth = (int) ((double) RectImage.height / imageRatio);
						logger.info("New LT Dimentions: H: " + newHeight + " W: " + newWidth);
					}
					Image tmpImage = me.getImage();
					me.setImage(resize(myImage, newWidth, newHeight));
					tmpImage.dispose();
				}
			}
			catch (Exception ex7) {
				logger.error("Shell Resize error " + ex7.getLocalizedMessage(), ex7);
			}
			logger.trace("Exit addControlListener");
		}
	}

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
				if (calCountDown != null) {
					if (cal.after(calCountDown)){
						//Delay has reached zero
						calCountDown = null;
						lblLeft.setText("");
						comonFunctions.SetFlags(guide.getDelaySet(), guide.getFlags());
						comonFunctions.UnsetFlags(guide.getDelayUnSet(), guide.getFlags());
						String javascript = guide.getDelayjScript();
						mainShell.runJscript(javascript);
						mainLogic.displayPage(guide.getDelTarget(), false, guide, mainShell, appSettings, userSettings, guideSettings);
					} else {
						if (guide.getDelStyle().equals("normal")) {
							//Normal delay so display seconds left 
							//(plus any offset if you are being sneaky) 
							long diff = calCountDown.getTimeInMillis() - cal.getTimeInMillis();
							diff = diff + (guide.getDelStartAtOffSet() * 1000);
							int intSeconds = (int) ((diff / 1000) + 1);
							int intMinutes = intSeconds / 60;
							intSeconds = intSeconds - (intMinutes * 60);
							String strSeconds = String.valueOf(intSeconds);
							strSeconds = "0" + strSeconds;
							strSeconds = strSeconds.substring(strSeconds.length() - 2);
							String strMinutes = String.valueOf(intMinutes);
							strMinutes = "0" + strMinutes;
							strMinutes = strMinutes.substring(strMinutes.length() - 2);
							String strTimeLeft = strMinutes + ":" + strSeconds;
							lblLeft.setText(strTimeLeft);
						} else if (guide.getDelStyle().equals("secret")) {
							//secret timer so display ?? to show there is one but not how long
							lblLeft.setText("??:??");
						} else {
							//either no delay or a hidden one
							lblLeft.setText("");
						}
						
					}
				}
				lblRight.setText(dateFormat.format(cal.getTime()));
				//re run in 0.1 seconds
				myDisplay.timerExec(100, this);
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
		Image tmpImage = (Image) imageLabel.getData("image");
		Image memImage = new Image(myDisplay, imgPath);
		imageLabel.setData("image", memImage);
		if (tmpImage != null) {
			tmpImage.dispose();
		}
		try {
			ImageData imgData = memImage.getImageData();
			Rectangle RectImage = imageLabel.getBounds();
			double dblScreenRatio = (double) RectImage.height / (double) RectImage.width;
			logger.info("dblScreenRatio: " + dblScreenRatio);
			double dblImageRatio = (double) imgData.height / (double) imgData.width;
			imageLabel.setData("imageRatio", Double.valueOf(dblImageRatio));
			logger.info("Lable Height: " + RectImage.height);
			logger.info("Lable Width: " + RectImage.width);
			logger.info("Image Height: " + imgData.height);
			logger.info("Image Width: " + imgData.width);

			if (dblScreenRatio > dblImageRatio) {
				newHeight = (int) ((double) RectImage.width * dblImageRatio);
				newWidth = RectImage.width;
				logger.info("New GT Dimentions: H: " + newHeight + " W: " + newWidth);
			} else {
				newHeight = RectImage.height;
				newWidth = (int) ((double) RectImage.height / dblImageRatio);
				logger.info("New LT Dimentions: H: " + newHeight + " W: " + newWidth);
			}
			Image tmpImage2 = imageLabel.getImage();
			imageLabel.setImage(resize(memImage, newWidth, newHeight));
			memImage = null;
			if (tmpImage2 != null) {
				tmpImage2.dispose();
			}
			logger.info("Open: " + strImage);
		}
		catch (Exception ex6) {
			logger.error("Process Image error " + ex6.getLocalizedMessage(), ex6);
		}
		videoFrame.setVisible(false);
    	this.imageLabel.setVisible(true);
	}

	public void playVideo(String video, int startAt, int stopAt, int loops, String target, String jscript) {
		//plays a video in the area to the left of the screen
		//sets the number of loops, start / stop time and any page to display if the video finishes
		//starts the video using a non UI thread so VLC can't hang the application
		if (videoOn) {
			try {
				this.imageLabel.setVisible(false);
				this.videoFrame.setVisible(true);
				videoLoops = loops;
				videoTarget = target;
				videoStartAt = startAt;
				videoStopAt = stopAt;
				videoJscript = jscript;
				videoPlay = true;
				String mrlVideo = "file:///" + video;
				VideoPlay videoPlay = new VideoPlay();
				videoPlay.setVideoPlay(mediaPlayer, mrlVideo);
				Thread videoPlayThread = new Thread(videoPlay);
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
		imageLabel.setBackgroundImage(null);		
	}

	
	
	public void playAudio(String audio, int startAt, int stopAt, int loops, String target, String jscript) {
		// run audio on another thread
		try {
			audioPlayer = new AudioPlayer(audio, startAt, stopAt, loops, target, mainShell, jscript);
			threadAudioPlayer = new Thread(audioPlayer);
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
			strTemp = strTemp.replace("<P>", "");
			strTemp = strTemp.replace("<p>", "");
			strTemp = strTemp.replace("</P>", "<br>");
			strTemp = strTemp.replace("</p>", "<br>");
			strTemp = strTemp.replace("<DIV>", "");
			strTemp = strTemp.replace("<div>", "");
			strTemp = strTemp.replace("</DIV>", "<br>");
			strTemp = strTemp.replace("</div>", "<br>");
			if (strTemp.endsWith("<br>")) {
				strTemp = strTemp.substring(0, strTemp.length() - 4);
			}
				strHTML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html  xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" /><title></title><style type=\"text/css\"> body { color: white; background-color: black; font-family: Tahoma; font-size:"
						+ MintHtmlFontSize + "px } </style></head><body>" + strTemp + "</body></html>";
				this.brwsText.setText(strHTML);
		} catch (Exception e1) {
			logger.error("displayPage Text Exception " + e1.getLocalizedMessage(), e1);
			strHTML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html  xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" /><title></title><style type=\"text/css\"> body { color: white; background-color: rgba(0,0,0,0); font-family: Tahoma; font-size:"
					+ MintHtmlFontSize + "px } div {background-color: rgba(0,0,0,0.25); position: absolute; bottom: 0} </style></head><body><div></div></body></html>";
			this.brwsText.setText(strHTML);
		}
	}

	public void removeButtons() {
		//remove all the buttons displayed for the previous page
        try {
			for (Control kid : btnComp.getChildren()) {
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


	public void runJscript(String function) {
		if (function == null) function = "";
		if (! function.equals("")) {
			getFormFields();
			Jscript jscript = new Jscript(guideSettings, userSettings, appSettings);
			Page objCurrPage = guide.getChapters().get(guideSettings.getChapter()).getPages().get(guideSettings.getPage());
			String pageJavascript = objCurrPage.getjScript();
			jscript.runScript(pageJavascript, function, false);
		}
	}

	private void getFormFields() {
		String node = (String) brwsText.evaluate("var vforms = document.forms;var vreturn = '';for (var formidx = 0; formidx < vforms.length; formidx++) {var vform = vforms[formidx];for (var controlIdx = 0; controlIdx < vform.length; controlIdx++) {var control = vform.elements[controlIdx];vreturn = vreturn + control.name + '¬' + control.value + '¬' + control.type + '¬' + control.checked + '|';}}return vreturn;");
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
				logger.info(name + "|" + value +  "|" + type +  "|" + checked);
			}
		}
	}
		
		
	//force a redisplay of the button are
	//set focus to the first button
	public void layoutButtons() {
		btnComp.layout();
	    Control[] controls = this.btnComp.getChildren();
	    if (controls.length > 0) {
	      controls[0].setFocus();
	    }
	    controls = null;
	}

	public void setMetronomeBPM(int metronomeBPM, int instrument, int loops, int resolution, String Rhythm) {
		// run metronome on another thread
		try {
			metronome = new MetronomePlayer(metronomeBPM, instrument, loops, resolution, Rhythm);
			threadMetronome = new Thread(metronome);
			threadMetronome.start();
		} catch (Exception e) {
			logger.error(" setMetronomeBPM " + e.getLocalizedMessage(), e);
		}
	}

	public void displayPage(String target) {
		mainLogic.displayPage(target, false, guide, mainShell, appSettings, userSettings, guideSettings);
	}
	
	public void stopMetronome() {
		if (metronome != null) {
			metronome.metronomeStop();
		}
	}
	
	public void stopAudio() {
		if (audioPlayer != null) {
			audioPlayer.audioStop();
		}
	}

	public void stopVideo() {
		if (videoOn) {
			try {
				if (mediaPlayer != null) {
					videoLoops = 0;
					videoTarget = "";
					videoPlay = false;
					VideoStop videoStop = new VideoStop();
					videoStop.setMediaPlayer(mediaPlayer);
					Thread videoStopThread = new Thread(videoStop);
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
			mediaPlayer.stop();
		}
		
	}
	
	public void stopDelay() {
		calCountDown = null;
		lblLeft.setText("");
	}

	public void stopAll() {
		stopDelay();
		stopMetronome();
		stopAudio();
		stopVideo();
	}

}