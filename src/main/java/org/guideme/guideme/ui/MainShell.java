package org.guideme.guideme.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

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
import org.guideme.guideme.model.Chapter;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.model.Timer;
import org.guideme.guideme.model.WebcamButton;
import org.guideme.guideme.readers.XmlGuideReader;
import org.guideme.guideme.scripting.Jscript;
import org.guideme.guideme.scripting.OverRide;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.settings.UserSettings;
import org.imgscalr.Scalr;
import org.mozilla.javascript.ContextFactory;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.linux.LinuxVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.mac.MacVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.windows.WindowsVideoSurfaceAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

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
	private int MintButtonFontSize;
	private int MintHtmlFontSize;
	private int MintTimerFontSize;
	private String strGuidePath;
	private int videoLoops = 0;
	private int videoStartAt = 0;
	private int videoStopAt = 0;
	private String videoTarget = "";
	private String videoJscript = "";
	private String videoScriptVar = "";
	private Boolean videoPlay = false;
	private Boolean webcamVisible = false;
	//private Boolean webcamRecording = false;
	private Guide guide = Guide.getGuide();
	private GuideSettings guideSettings = guide.getSettings();
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
	private Shell shell2;
	//private Shell shell3;
	private DebugShell debugShell;
	private Display myDisplay;
	private Font controlFont;
	private Font buttonFont;
	private Font timerFont;
	private Composite mediaPanel;
	//private MediaPlayerFactory mediaPlayerFactory;
	private SwtEmbeddedMediaPlayer mediaPlayer;
	//private Frame videoFrame;
	//private Canvas videoSurfaceCanvas;
	//private CanvasVideoSurface videoSurface;
	private Composite webcamPanel;
	private Webcam webcam;
	private WebcamPanel panel;
	private JRootPane webcamRoot;
	private MainShell mainShell;
	private MainLogic mainLogic = MainLogic.getMainLogic();
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	private XmlGuideReader xmlGuideReader = XmlGuideReader.getXmlGuideReader();
	private MetronomePlayer metronome;
	private Thread threadMetronome;
	private AudioPlayer audioPlayer;
	private Thread threadAudioPlayer;
	private AudioPlayer audioPlayer2;
	private Thread threadAudioPlayer2;
	private Boolean videoOn = true;
	private Boolean webcamOn = true;
	private String style = "";
	private String defaultStyle = "";
	private Boolean imgOverRide = false;
	private Boolean multiMonitor = true;
	private Boolean overlayTimer = false;
	private HashMap<String, com.snapps.swt.SquareButton> hotKeys = new HashMap<String, com.snapps.swt.SquareButton>();
	private HashMap<String, com.snapps.swt.SquareButton> buttons = new HashMap<String, com.snapps.swt.SquareButton>();
	private shellKeyEventListener keyListener;
	//private shellMouseMoveListener mouseListen;
	private Boolean showMenu = true;
	private Menu MenuBar;
	private HashMap<String, Timer> timer = new HashMap<String, Timer>();
	//private ArrayList<Timer> timer = new ArrayList<Timer>();
	private Rectangle clientArea;
	private Rectangle clientArea2;
	private boolean inPrefShell = false;
	private String rightHTML;
	private String leftHTML;
	private ContextFactory factory;
	private File oldImage;
	private File oldImage2;
	private boolean videoPlayed = false;
	private ResourceBundle displayText;
	private String ProcStatusText = "";
	//private boolean exitTriggered = false;

	public Shell createShell(final Display display) {
		logger.trace("Enter createShell");
		comonFunctions.setDisplay(display);
		// Initialise variable
		int[] intWeights1 = new int[2];
		int[] intWeights2 = new int[2];
		colourBlack = display.getSystemColor(SWT.COLOR_BLACK);
		colourWhite = display.getSystemColor(SWT.COLOR_WHITE);

		try {
			logger.trace("Get appSettings");
			appSettings = AppSettings.getAppSettings();

			Locale locale = new Locale(appSettings.getLanguage(), appSettings.getCountry());
			displayText = ResourceBundle.getBundle("DisplayBundle", locale);
			appSettings.setDisplayText(displayText);

			//video flag
			videoOn = appSettings.getVideoOn();

			//webcam flag
			webcamOn = appSettings.getWebcamOn();

			// font size
			MintFontSize = appSettings.getFontSize();

			// font size
			MintHtmlFontSize = appSettings.getHtmlFontSize();

			// font size
			MintTimerFontSize = appSettings.getTimerFontSize();

			// font size
			MintButtonFontSize = appSettings.getButtonFontSize();

			// path to the xml files
			strGuidePath = appSettings.getDataDirectory();

			// array to hold the various flags
			guideSettings.setFlags("");
			
			//width and height of the sizable containers on the screen
			intWeights1 = appSettings.getSash1Weights();
			intWeights2 = appSettings.getSash2Weights();

			logger.trace("Get userSettings");

			userSettings = UserSettings.getUserSettings();

		} catch (NumberFormatException e) {
			logger.error("OnCreate NumberFormatException ", e);
		} catch (Exception e) {
			logger.error("OnCreate Exception ", e);
		}


		try {
			//Create the main UI elements
			logger.trace("display");
			myDisplay = display;
			logger.trace("shell");
			if (appSettings.isFullScreen()) {
				shell = new Shell(myDisplay, SWT.NO_TRIM);
			} else {
				shell = new Shell(myDisplay);
			}
			logger.trace("shell add listener");
			shell.addShellListener(new shellCloseListen());
			logger.trace("key filter");
			keyListener = new shellKeyEventListener();
			myDisplay.addFilter(SWT.KeyDown, keyListener);
			//mouseListen = new shellMouseMoveListener();
			//myDisplay.addFilter(SWT.MouseMove, mouseListen);
			int mainMonitor = appSettings.getMainMonitor();
			
			clientArea2 = null;
			multiMonitor = appSettings.isMultiMonitor();
			Monitor monitors[] = display.getMonitors();
			if (multiMonitor) {
				//multi monitor
				if (monitors.length > 1) {
					shell2 = new Shell(myDisplay, SWT.NO_TRIM);
					if (mainMonitor > 1) {
						if (appSettings.isFullScreen()) {
							clientArea2 = monitors[0].getBounds();
						} else {
							clientArea2 = monitors[0].getClientArea();
						}
					} else {
						if (appSettings.isFullScreen()) {
							clientArea2 = monitors[1].getBounds();
						} else {
							clientArea2 = monitors[1].getClientArea();
						}
					}
					FormLayout layout2 = new FormLayout();
					shell2.setLayout(layout2);
				} else {
					multiMonitor = false;
				}
			}
			
			//debug shell
			debugShell = DebugShell.getDebugShell();
			debugShell.createShell(myDisplay, this);
			
			//get primary monitor and its size
			clientArea = monitors[0].getClientArea();
			if (mainMonitor == 1) {
				if (appSettings.isFullScreen()) {
					clientArea = monitors[0].getBounds();
				} else {
					clientArea = monitors[0].getClientArea();
				}
			} else {
				if (monitors.length > 1) {
					if (appSettings.isFullScreen()) {
						clientArea = monitors[1].getBounds();
					} else {
						clientArea = monitors[1].getClientArea();
					}
				} else {
					if (appSettings.isFullScreen()) {
						clientArea = monitors[0].getBounds();
					} else {
						clientArea = monitors[0].getClientArea();
					}
				}
			}
			shell.setText("Guide Me (" + ComonFunctions.getVersion() + ")");
			FormLayout layout = new FormLayout();
			shell.setLayout(layout);

			
			Font sysFont = display.getSystemFont();
			FontData[] fD = sysFont.getFontData();
			fD[0].setHeight(MintFontSize);
			controlFont = new Font(display, fD);
			fD[0].setHeight(MintTimerFontSize);
			timerFont = new Font(display, fD);
			fD[0].setHeight(MintButtonFontSize);
			buttonFont = new Font(display, fD);

			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			lblLeft = new Label(shell, SWT.LEFT);
			lblLeft.setBackground(colourBlack);
			lblLeft.setForeground(colourWhite);
			lblLeft.setFont(controlFont);
			lblLeft.setText(dateFormat.format(cal.getTime()));

			lblCentre = new Label(shell, SWT.RIGHT);
			lblCentre.setBackground(colourBlack);
			lblCentre.setForeground(colourWhite);
			lblCentre.setFont(controlFont);
			lblCentre.setText("Title, Author");

			if (!multiMonitor) {
				sashform = new SashForm(shell, SWT.HORIZONTAL);
				sashform.setBackground(colourBlack);
				leftFrame = new Composite(sashform,  SWT.SHADOW_NONE);
			} else {
				leftFrame = new Composite(shell,  SWT.SHADOW_NONE);
			}
			
			leftFrame.setBackground(colourBlack);
			FormLayout layoutLF = new FormLayout();
			leftFrame.setLayout(layoutLF);
			
			
			mediaPanel = new Composite(leftFrame, SWT.EMBEDDED);
			FormLayout  layout3 = new FormLayout();
			mediaPanel.setLayout(layout3);
			mediaPanel.setBackground(colourBlack);
			mediaPanel.addControlListener(new mediaPanelListener());

			webcamPanel = new Composite(leftFrame, SWT.EMBEDDED);
			FormLayout  layoutwebcam = new FormLayout();
			webcamPanel.setLayout(layoutwebcam);
			webcamPanel.setBackground(colourBlack);

			//defaultStyle
			try {
		        defaultStyle = comonFunctions.readFile("./defaultCSS.TXT", StandardCharsets.UTF_8);
		        defaultStyle = defaultStyle.replace("MintHtmlFontSize", String.valueOf(MintHtmlFontSize)); 
			}
			catch (Exception ex2) {
				defaultStyle = "";
				logger.error("Load defaultCSS.txt error:" + ex2.getLocalizedMessage(), ex2);
			}
	        
	        
			style = defaultStyle;

			//default HTML
			try {
		        rightHTML = comonFunctions.readFile("DefaultRightHtml.txt", StandardCharsets.UTF_8);
			}
			catch (Exception ex2) {
				rightHTML = "";
				logger.error("Load DefaultRightHtml.txt error:" + ex2.getLocalizedMessage(), ex2);
			}
			
			try {
		        leftHTML = comonFunctions.readFile("DefaultLeftHtml.txt", StandardCharsets.UTF_8);
			}
			catch (Exception ex2) {
				leftHTML = "";
				logger.error("Load DefaultLeftHtml.txt error:" + ex2.getLocalizedMessage(), ex2);
			}
			
			
			String strHtml = rightHTML.replace("BodyContent", "");
			strHtml = strHtml.replace("DefaultStyle", defaultStyle);
			imageLabel = new Browser(leftFrame, 0);
			imageLabel.setText("");
			imageLabel.setBackground(colourBlack);
			imageLabel.addStatusTextListener(new EventStatusTextListener());
			//imageLabel.setAlignment(SWT.CENTER);

			if (!multiMonitor) {
				sashform2 = new SashForm(sashform, SWT.VERTICAL);
			} else {
				sashform2 = new SashForm(shell2, SWT.VERTICAL);
			}
			sashform2.setBackground(colourBlack);

			if (!overlayTimer) {
				lblRight = new Label(sashform2, SWT.RIGHT);
			} else {
				if (!multiMonitor) {
					lblRight = new Label(shell, SWT.RIGHT);
				} else {
					lblRight = new Label(leftFrame, SWT.RIGHT);
				}
			}
			lblRight.setBackground(colourBlack);
			lblRight.setForeground(colourWhite);
			lblRight.setFont(timerFont);
			lblRight.setAlignment(SWT.CENTER);

			//brwsText = new Browser(sashform2, SWT.MOZILLA);
			brwsText = new Browser(sashform2, SWT.NONE);
			//brwsText = new Browser(sashform2, SWT.WEBKIT);
			brwsText.setText(strHtml);
			brwsText.setBackground(colourBlack);
			brwsText.addStatusTextListener(new EventStatusTextListener());

			btnComp = new Composite(sashform2, SWT.SHADOW_NONE);
			btnComp.setBackground(colourBlack);
			RowLayout layout2 = new RowLayout();
			btnComp.setLayout(layout2);

			if (videoOn) {
				logger.trace("Video Enter");
				try {
					/*
					videoFrame = SWT_AWT.new_Frame(mediaPanel);         
					videoSurfaceCanvas = new Canvas();
		
					videoSurfaceCanvas.setBackground(java.awt.Color.black);
					videoFrame.add(videoSurfaceCanvas);
		
					mediaPlayerFactory = new MediaPlayerFactory("--no-video-title-show");
					mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		
					videoSurface = mediaPlayerFactory.newVideoSurface(videoSurfaceCanvas);
	
					mediaPlayer.setVideoSurface(videoSurface);
	
					mediaPlayer.addMediaPlayerEventListener(new MediaListener());
					*/
					
					LibVlc libvlc = LibVlc.INSTANCE;
					libvlc_instance_t instance = libvlc.libvlc_new(0, null);
					
					mediaPlayer = new SwtEmbeddedMediaPlayer(libvlc, instance);
					mediaPlayer.setVideoSurface(new CompositeVideoSurface(mediaPanel, getVideoSurfaceAdapter()));
					mediaPlayer.addMediaPlayerEventListener(new MediaListener());
					
				}
				catch (Exception vlcex) {
					logger.error("VLC intialisation error " + vlcex.getLocalizedMessage(), vlcex);
				}
				logger.trace("Video Exit");
			}

			if (webcamOn)
			{
				try {
					Frame frame = SWT_AWT.new_Frame(webcamPanel);
	
					webcamRoot = new JRootPane();
					frame.add(webcamRoot);
	
				}
				catch (Exception webcamex) {
					logger.error("Webcam intialisation error " + webcamex.getLocalizedMessage(), webcamex);
				}
				
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
			lblCentreFormData.right = new FormAttachment(100,0);
			lblCentre.setLayoutData(lblCentreFormData);

			if (overlayTimer) {
				FormData lblRightFormData = new FormData();
				lblRightFormData.top = new FormAttachment(leftFrame,0);
				lblRightFormData.left = new FormAttachment(leftFrame, 0);
				lblRight.setLayoutData(lblRightFormData);
				lblRight.moveAbove(null);
			}

			if (!multiMonitor) {
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
			} else {

				FormData leftFrameFormData = new FormData();
				leftFrameFormData.top = new FormAttachment(lblLeft,0);
				leftFrameFormData.left = new FormAttachment(0,0);
				leftFrameFormData.right = new FormAttachment(100,0);
				leftFrameFormData.bottom = new FormAttachment(100,0);
				leftFrame.setLayoutData(leftFrameFormData);

				FormData SashFormData2 = new FormData();
				SashFormData2.top = new FormAttachment(0,0);
				SashFormData2.left = new FormAttachment(0,0);
				SashFormData2.right = new FormAttachment(100,0);
				SashFormData2.bottom = new FormAttachment(100,0);
				sashform2.setLayoutData(SashFormData2);
			}
			

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
			
			FormData WebcamPanelFormData = new FormData();
			WebcamPanelFormData.top = new FormAttachment(0,0);
			WebcamPanelFormData.left = new FormAttachment(0, 0);
			WebcamPanelFormData.right = new FormAttachment(100,0);
			WebcamPanelFormData.bottom = new FormAttachment(100,0);
			webcamPanel.setLayoutData(WebcamPanelFormData);
			
			FormData imageLabelFormData = new FormData();
			imageLabelFormData.top = new FormAttachment(0,0);
			imageLabelFormData.left = new FormAttachment(0, 0);
			imageLabelFormData.right = new FormAttachment(100,0);
			imageLabelFormData.bottom = new FormAttachment(100,0);
			imageLabel.setLayoutData(imageLabelFormData);
			
			//Menu Bar
			MenuBar = new Menu (shell, SWT.BAR);

			//Top Level File drop down
			MenuItem fileItem = new MenuItem (MenuBar, SWT.CASCADE);
			fileItem.setText (displayText.getString("MainFile"));

			//Sub Menu for File
			Menu fileSubMenu = new Menu (shell, SWT.DROP_DOWN);
			//Associate it with the top level File menu
			fileItem.setMenu (fileSubMenu);

			//File Load menu item
			MenuItem fileLoadItem = new MenuItem (fileSubMenu, SWT.PUSH);
			fileLoadItem.setText (displayText.getString("MainLoad"));
			fileLoadItem.addSelectionListener(new FileLoadListener());

			//File Library menu item
			MenuItem fileLibraryItem = new MenuItem (fileSubMenu, SWT.PUSH);
			fileLibraryItem.setText (displayText.getString("MainLibrary"));
			fileLibraryItem.addSelectionListener(new FileLibraryListener());

			//File Restart menu item
			MenuItem fileRestartItem = new MenuItem (fileSubMenu, SWT.PUSH);
			fileRestartItem.setText (displayText.getString("MainRestart"));
			fileRestartItem.addSelectionListener(new FileRestartListener());

			//File Preferences menu item
			MenuItem filePreferencesItem = new MenuItem (fileSubMenu, SWT.PUSH);
			filePreferencesItem.setText (displayText.getString("MainAppPref"));
			filePreferencesItem.addSelectionListener(new FilePreferences());

			//File Preferences Guide menu item
			MenuItem filePreferencesGuideItem = new MenuItem (fileSubMenu, SWT.PUSH);
			filePreferencesGuideItem.setText (displayText.getString("MainUserPref"));
			filePreferencesGuideItem.addSelectionListener(new FilePreferencesGuide());

			//File Guide Preferences menu item
			MenuItem fileGuidePreferencesItem = new MenuItem (fileSubMenu, SWT.PUSH);
			fileGuidePreferencesItem.setText (displayText.getString("MainGuidePref"));
			fileGuidePreferencesItem.addSelectionListener(new FileGuidePreferences());

			//File Exit menu item
			MenuItem fileExitItem = new MenuItem (fileSubMenu, SWT.PUSH);
			fileExitItem.setText (displayText.getString("MainExit"));
			fileExitItem.addListener (SWT.Selection, new Listener () {
				public void handleEvent (Event e) {
					logger.trace("Enter Menu Exit");
					shell.close();
					logger.trace("Exit Menu Exit");
				}
			});

			//Top Level Tools drop down
			MenuItem toolsItem = new MenuItem (MenuBar, SWT.CASCADE);
			toolsItem.setText (displayText.getString("MainTools"));

			//Sub Menu for Tools
			Menu toolsSubMenu = new Menu (shell, SWT.DROP_DOWN);
			//Associate it with the top level File menu
			toolsItem.setMenu (toolsSubMenu);
			
			//Tools Compress menu item
			MenuItem CompressGuideItem = new MenuItem (toolsSubMenu, SWT.PUSH);
			CompressGuideItem.setText (displayText.getString("MainToolsCompress"));
			CompressGuideItem.addSelectionListener(new CompressGuideListener());

			//Tools UnCompress menu item
			MenuItem UnCompressGuideItem = new MenuItem (toolsSubMenu, SWT.PUSH);
			UnCompressGuideItem.setText (displayText.getString("MainToolsUnCompress"));
			UnCompressGuideItem.addSelectionListener(new UnCompressGuideListener());

			//Tools Resize menu item
			MenuItem ResizeGuideItem = new MenuItem (toolsSubMenu, SWT.PUSH);
			ResizeGuideItem.setText (displayText.getString("MainToolsResizeImage"));
			ResizeGuideItem.addSelectionListener(new ResizeGuideListener());
			
			if (appSettings.getDebug())
			{
				//Top Level Debug drop down
				MenuItem debugItem = new MenuItem (MenuBar, SWT.CASCADE);
				debugItem.setText (displayText.getString("MainDebug"));
	
				//Sub Menu for Debug
				Menu debugSubMenu = new Menu (shell, SWT.DROP_DOWN);
				//Associate it with the top level File menu
				debugItem.setMenu (debugSubMenu);
				
				//Debug Debug Menu Item
			    final MenuItem debugCheck = new MenuItem(debugSubMenu, SWT.CHECK);
			    debugCheck.setText(displayText.getString("MainDebugDebug"));
			    debugCheck.setSelection(appSettings.getDebug());
			    debugCheck.addListener(SWT.Selection, new Listener() {
			      public void handleEvent(Event event) {
			        appSettings.setDebug(debugCheck.getSelection());
			      }
			    });			
				
			    //Debug Javascript Debug Menu Item
			    final MenuItem jsdebugCheck = new MenuItem(debugSubMenu, SWT.CHECK);
			    jsdebugCheck.setText(displayText.getString("MainDebugJava"));
			    jsdebugCheck.setSelection(appSettings.getJsDebug());
			    jsdebugCheck.addListener(SWT.Selection, new Listener() {
			      public void handleEvent(Event event) {
			        appSettings.setJsDebug(jsdebugCheck.getSelection());
			      }
			    });			
			    //Debug Javascript Debug Menu Error Item
			    final MenuItem jsdebugErrorCheck = new MenuItem(debugSubMenu, SWT.CHECK);
			    jsdebugErrorCheck.setText(displayText.getString("MainDebugException"));
			    jsdebugErrorCheck.setSelection(appSettings.getJsDebugError());
			    jsdebugErrorCheck.addListener(SWT.Selection, new Listener() {
			      public void handleEvent(Event event) {
			        appSettings.setJsDebugError(jsdebugErrorCheck.getSelection());
			      }
			    });			
			    //Debug Javascript Debug Menu Enter Item
			    final MenuItem jsdebugEnterCheck = new MenuItem(debugSubMenu, SWT.CHECK);
			    jsdebugEnterCheck.setText(displayText.getString("MainDebugEnter"));
			    jsdebugEnterCheck.setSelection(appSettings.getJsDebugEnter());
			    jsdebugEnterCheck.addListener(SWT.Selection, new Listener() {
			      public void handleEvent(Event event) {
			        appSettings.setJsDebugEnter(jsdebugEnterCheck.getSelection());
			      }
			    });			
			    //Debug Javascript Debug Menu Exit Item
			    final MenuItem jsdebugExitCheck = new MenuItem(debugSubMenu, SWT.CHECK);
			    jsdebugExitCheck.setText(displayText.getString("MainDebugExit"));
			    jsdebugExitCheck.setSelection(appSettings.getJsDebugExit());
			    jsdebugExitCheck.addListener(SWT.Selection, new Listener() {
			      public void handleEvent(Event event) {
			        appSettings.setJsDebugExit(jsdebugExitCheck.getSelection());
			      }
			    });			
			}
			// Add the menu bar to the shell
			shell.setMenuBar (MenuBar);

			// Resize the image if the control containing it changes size
			imageLabel.addControlListener(new ImageControlAdapter());

			// tell SWT to display the correct screen info
			shell.pack();
			shell.setMaximized(true);
			shell.setBounds(clientArea);
			if (!multiMonitor) {
				try {
					sashform.setWeights(intWeights1);
				}
				catch (Exception ex2) {
					logger.error(ex2.getLocalizedMessage(), ex2);
				}
			} else {
				shell2.pack();
				shell2.setMaximized(true);
				shell2.setBounds(clientArea2);
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
			threadMetronome.setName("threadMetronome");
			threadMetronome.start();
		}
		catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
		}
		logger.trace("Exit createShell");
		mainShell = this;
		if (!appSettings.getComandLineGuide().equals("")) {
			loadGuide(appSettings.getDataDirectory() + appSettings.getComandLineGuide());
		}
		mainShell.displayPage(guideSettings.getCurrPage());
		Chapter chapter = guide.getChapters().get("default");
		if (chapter != null)
		{
			for (Page page : chapter.getPages().values())
			{
				debugShell.addPagesCombo(page.getId());
			}
			debugShell.setPage(guide.getCurrPage(), true);			
		}
		return shell;
	}

	class shellMouseMoveListener implements Listener {

		@Override
		public void handleEvent(Event e) {
			Rectangle rect = shell.getBounds(); 
			Rectangle rect2; 
			if (appSettings.isHideMenu()  && !inPrefShell) {
				if (e.widget instanceof Control) {
					Point absolutePos = ((Control) e.widget).toDisplay(e.x, e.y);
					//String coord;
					if (absolutePos.y <= 40 && !showMenu) {
						if (!shell.isDisposed()) {
							shell.setMenuBar(MenuBar);
							shell.pack();
							//shell.setMaximized(true);
							shell.setBounds(rect);
						}
						if (multiMonitor) {
							if (!shell2.isDisposed()) {
								rect2 = shell2.getBounds();
								shell2.pack();
								//shell2.setMaximized(true);
								shell2.setBounds(rect2);
							}
						}
						showMenu = true;
					} else if (absolutePos.y > 100 && showMenu) {
						if (!shell.isDisposed()) {
							shell.setMenuBar(null);
							shell.pack();
							//shell.setMaximized(true);
							shell.setBounds(rect);
						}
						if (multiMonitor) {
							if (!shell2.isDisposed()) {
								rect2 = shell2.getBounds();
								shell2.pack();
								//shell2.setMaximized(true);
								shell2.setBounds(rect2);	        			}
						}
						showMenu = false;
					}
					//coord = "show " + showMenu.toString() + " "  + absolutePos.x + " , " + absolutePos.y;
					//mainShell.setLblRight(coord);
				}
			} else {
				if (!showMenu) {
					if (!shell.isDisposed()) {
						shell.setMenuBar(MenuBar);
						shell.pack();
						//shell.setMaximized(true);
						shell.setBounds(rect);
					}
					if (multiMonitor) {
						if (!shell2.isDisposed()) {
							rect2 = shell2.getBounds();
							shell2.pack();
							//shell2.setMaximized(true);
							shell2.setBounds(rect2);
						}
					}
					showMenu = true;
				}
			}
		} 

	}
	
	
	class shellCloseListen  extends ShellAdapter {
		// Clean up stuff when the application closes
		@Override
		public void shellClosed(ShellEvent e) {
			try {
				//exitTriggered = true;
				myDisplay.removeFilter(SWT.KeyDown, keyListener);
				keyListener = null;
				if (shell2 != null) {
					shell2.close();
				}
				debugShell.closeShell();
				int[] intWeights;
				if (!multiMonitor) {
					intWeights = sashform.getWeights();
					appSettings.setSash1Weights(intWeights);
					int[] intWeights2;
					intWeights2 = sashform2.getWeights();
					appSettings.setSash2Weights(intWeights2);
				}
				//appSettings.setDataDirectory(strGuidePath);
				appSettings.saveSettings();
				controlFont.dispose();
				timerFont.dispose();
				buttonFont.dispose();
				stopAll(true);
				metronome.metronomeKill();
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
			try {
				if (event.display.getActiveShell().getText().equals(shell.getText())) {

					logger.trace(
							event.character + "|" + event.keyCode + "|" + event.keyLocation + "|" + event.stateMask);
					if (event.keyCode == 13
							&& (event.widget.getClass().toString().equals("class org.eclipse.swt.browser.Browser"))) {
						event.doit = false;
					}
					;
					if (((event.stateMask & SWT.ALT) == SWT.ALT)) {
						switch (event.character) {
						/*
						 * case 'd' : shell3.setVisible(!shell3.getVisible());
						 * if (shell3.isVisible()) { shell3.setActive(); }
						 * break;
						 */
						case 'm':
						case 'M':
							Rectangle rect = shell.getBounds();
							Rectangle rect2;
							if (!showMenu) {
								if (!shell.isDisposed()) {
									shell.setMenuBar(MenuBar);
									shell.pack();
									// shell.setMaximized(true);
									shell.setBounds(rect);
								}
								if (multiMonitor) {
									if (!shell2.isDisposed()) {
										rect2 = shell2.getBounds();
										shell2.pack();
										// shell2.setMaximized(true);
										shell2.setBounds(rect2);
									}
								}
								showMenu = true;
							} else {
								if (!shell.isDisposed()) {
									shell.setMenuBar(null);
									shell.pack();
									// shell.setMaximized(true);
									shell.setBounds(rect);
								}
								if (multiMonitor) {
									if (!shell2.isDisposed()) {
										rect2 = shell2.getBounds();
										shell2.pack();
										// shell2.setMaximized(true);
										shell2.setBounds(rect2);
									}
								}
								showMenu = false;
							}
							break;
						}
						/*
						 * if (comonFunctions.onWindows() && event.character !=
						 * "d".charAt(0)) { //ignore } else {
						 * shell3.setVisible(!shell3.getVisible()); if
						 * (shell3.isVisible()) { shell3.setActive(); } }
						 */
					} else {
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
							runJscript(javascript, false);
							mainLogic.displayPage(strTag, false, guide, mainShell, appSettings, userSettings,
									guideSettings, debugShell);
						}
					}
				}
			} catch (Exception ex) {
				logger.error(" hot key " + ex.getLocalizedMessage(), ex);
			}
		}
	}	
	
	class VideoRelease implements Runnable {
		//Do the release of the Video stuff (VLC) on a different thread to prevent it blocking the main UI thread
		private MediaPlayerFactory mediaPlayerFactoryThread;
		private EmbeddedMediaPlayer mediaPlayerThread;

		@Override
		public void run() {
			try {
				mediaPlayerThread.stop();
				mediaPlayerThread.release();
				mediaPlayerFactoryThread.release();
				logger.trace("VideoRelease Exit");
			}
			catch (Exception ex) {
				logger.error("Video release " + ex.getLocalizedMessage(), ex);
			}
		}

		public void setVideoRelease(EmbeddedMediaPlayer mediaPlayer, MediaPlayerFactory mediaPlayerFactory) {
			try {
			mediaPlayerFactoryThread = mediaPlayerFactory;
			mediaPlayerThread = mediaPlayer;
			}
			catch (Exception ex) {
				logger.error("Video release " + ex.getLocalizedMessage(), ex);
			}
		}
		
	}

	class mediaPanelListener extends ControlAdapter {
		//resize the video if the container changes size
		@Override
		public void controlResized(ControlEvent e) {
			super.controlResized(e);
			if (videoOn) {
				try {
					//Rectangle rect = mediaPanel.getClientArea();
					//videoFrame.setSize(rect.width, rect.height);
				}
				catch (Exception ex) {
					logger.error("Video resize " + ex.getLocalizedMessage(), ex);
				}
			}
		}

	}
	
	
	class MediaListener extends MediaPlayerEventAdapter {
		//Video event listener
		
		//Video has finished
		@Override
		public void finished(MediaPlayer mediaPlayer) {
			logger.debug("MediaListener finished " + mediaPlayer.mrl());
			super.finished(mediaPlayer);
			try {
				if (!videoTarget.equals(""))  {
					//run on the main UI thread
					myDisplay.asyncExec(
							new Runnable() {
								public void run(){
									mediaPanel.setVisible(false);
									webcamPanel.setVisible(false);
									imageLabel.setVisible(true);
									leftFrame.layout(true);
									logger.debug("MediaListener Video Run: " + videoJscript + " videoTarget: " + videoTarget);
									mainShell.runJscript(videoJscript, false);
									mainShell.displayPage(videoTarget);
								}
							});											
				}
				comonFunctions.processSrciptVars(videoScriptVar, guideSettings);
				/*
				//run on the main UI thread
				myDisplay.syncExec(
						new Runnable() {
							public void run(){
								mediaPanel.setVisible(false);
								imageLabel.setVisible(true);
								leftFrame.layout(true);
							}
						});
				*/											
			}
			catch (Exception ex) {
				logger.error(" MediaListener finished " + ex.getLocalizedMessage(), ex);
			}
		}

		/*
		//newState 5 indicates the video has finished
		//videoPlay can be set to false outside the code to tell it to stop
		//if the video finishes loop round again if a number of repeats has been set
		@Override
		public void mediaStateChanged(MediaPlayer lmediaPlayer, int newState) {
			super.mediaStateChanged(lmediaPlayer, newState);
			logger.debug("MediaListener newState: " + newState + " videoPlay: " + videoPlay +  " VideoTarget: " + videoTarget + " file:" + lmediaPlayer.mrl());
			try {
				if ((newState==5 || newState==6) && videoPlay){
						if (!videoTarget.equals(""))  {
							//run on the main UI thread
							myDisplay.asyncExec(
									new Runnable() {
										public void run(){
											logger.debug("MediaListener Video Run: " + videoJscript + " videoTarget: " + videoTarget);
											mainShell.runJscript(videoJscript, false);
											mainShell.displayPage(videoTarget);
										}
									});											
						}
						comonFunctions.processSrciptVars(videoScriptVar, guideSettings);
				}
			} catch (Exception e) {
				logger.error("mediaStateChanged " + e.getLocalizedMessage(), e);
			}
		}
		*/

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
				dialog.setFilterPath (appSettings.getDataDirectory());
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
							loadGuide(strFileToLoad);
							debugShell.clearJConsole();
						}
					}
					catch (Exception ex5) {
						logger.error("Load Guide " + ex5.getLocalizedMessage(), ex5);
					}
				}
				catch (Exception ex4) {
					logger.error("Load Guide Dialogue error " + ex4.getLocalizedMessage(), ex4);
				}
			}
			catch (Exception ex3) {
				logger.error("Load Guide error " + ex3.getLocalizedMessage(), ex3);
			}
			logger.trace("Exit Menu Load");
			super.widgetSelected(e);
		}

	}


	class CompressGuideListener  extends SelectionAdapter {
		//File CompressGuide from the menu
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter Menu CompressGuide");
				//display a dialog to ask for a guide file to play
				FileDialog dialog = new FileDialog (shell, SWT.OPEN);
				String [] filterNames = new String [] {"XML Files"};
				String [] filterExtensions = new String [] {"*.xml"};
				dialog.setFilterNames (filterNames);
				dialog.setFilterExtensions (filterExtensions);
				dialog.setFilterPath (appSettings.getDataDirectory());
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
							comonFunctions.CompressGuide(strFileToLoad);
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
			logger.trace("Exit Menu CompressGuide");
			super.widgetSelected(e);
		}

	}

	class UnCompressGuideListener  extends SelectionAdapter {
		//File UnCompressGuide from the menu
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter Menu UnCompressGuide");
				//display a dialog to ask for a guide file to play
				FileDialog dialog = new FileDialog (shell, SWT.OPEN);
				String [] filterNames = new String [] {"ZIP Files"};
				String [] filterExtensions = new String [] {"*.zip"};
				dialog.setFilterNames (filterNames);
				dialog.setFilterExtensions (filterExtensions);
				dialog.setFilterPath (appSettings.getDataDirectory());
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
							comonFunctions.UnCompressGuide(strFileToLoad);
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
			logger.trace("Exit Menu UnCompressGuide");
			super.widgetSelected(e);
		}

	}
	
	class ResizeGuideListener  extends SelectionAdapter {
		//File CompressGuide from the menu
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter Menu CompressGuide");
				//display a dialog to ask for a guide file to play
				FileDialog dialog = new FileDialog (shell, SWT.OPEN);
				String [] filterNames = new String [] {"XML Files"};
				String [] filterExtensions = new String [] {"*.xml"};
				dialog.setFilterNames (filterNames);
				dialog.setFilterExtensions (filterExtensions);
				dialog.setFilterPath (appSettings.getDataDirectory());
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
							comonFunctions.ResizeGuideImages(strFileToLoad);
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
			logger.trace("Exit Menu CompressGuide");
			super.widgetSelected(e);
		}

	}
	
	
	// Is triggered by Java script writing to the Status Text 
	// Checks the text for "commands" that GuideMe understands
	// Original implementation is to move to HTML buttons where the onclick javascript needs to trigger Guideme
	// Needs a pipe | separated string containing the command and parameters
	// ButtonClick|Set|UnSet|scriptVar|javaScript
	class EventStatusTextListener implements StatusTextListener {
		@Override
		public void changed(StatusTextEvent event) {
			if (!ProcStatusText.equals(event.text))
			{
				String statusText = event.text;
				ProcStatusText = event.text;
				String[] eventArgs = statusText.split("\\|");
				if (eventArgs[0].equals("ButtonClick") && eventArgs.length > 5)
				{
					try {
						logger.trace("Enter DynamicButtonListner");
						String strTag;
						strTag = eventArgs[1];//Set
						if (!strTag.equals("")) {
							comonFunctions.SetFlags(strTag, guide.getFlags());
						}
						strTag = eventArgs[2];//UnSet
						if (!strTag.equals("")) {
							comonFunctions.UnsetFlags(strTag, guide.getFlags());
						}
						String scriptVar = eventArgs[3];//scriptVar
						comonFunctions.processSrciptVars(scriptVar, guideSettings);
						strTag = eventArgs[4];//Target
						String javascript = eventArgs[5];//javaScript
						runJscript(javascript, false);
						mainLogic.displayPage(strTag, false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
					}
					catch (Exception ex) {
						logger.error(" DynamicButtonListner " + ex.getLocalizedMessage(), ex);
					}
					logger.trace("Exit DynamicButtonListner");
				}
			}
		}
	}
	
	
	//Load the tease
	public void loadGuide(String fileToLoad) {
		try {
			debugShell.clearPagesCombo();
		}
		catch (Exception ex) {
			logger.error("Clear debug pages " + ex.getLocalizedMessage(), ex);
		}
		try {
			String strPage = xmlGuideReader.loadXML(fileToLoad, guide, appSettings, debugShell);
			guideSettings = guide.getSettings();
			if (guide.getCss().equals("")) {
				style = defaultStyle;
			} else {
				style = guide.getCss();
			}
			//display the first page
			mainLogic.displayPage(strPage , false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
		}
		catch (Exception ex) {
			logger.error("Load Guide " + ex.getLocalizedMessage(), ex);
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
				stopAll(false);
		        guide.getFlags().clear();
		        guide.getSettings().setPage("start");
		        guide.getSettings().setFlags(comonFunctions.GetFlags(guide.getFlags()));
		        //guide.getSettings().setScope(null);
				HashMap<String, Object> scriptVariables = new HashMap<String, Object>();
				guide.getSettings().setScriptVariables(scriptVariables);
				guide.getSettings().setScope(null);
				guide.getSettings().saveSettings();
				guideSettings = guide.getSettings();
				debugShell.clearJConsole();
				mainLogic.displayPage("start", false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
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
				//special guide for user preferences
				//this loads automatically from the application directory with a hard coded name.
				//
				debugShell.clearPagesCombo();
				String appDir = appSettings.getUserDir().replace("\\", "\\\\");
				String fileName = "userSettingsUI_" + appSettings.getLanguage() + "_" + appSettings.getCountry() + ".xml";
				File f = new File(appDir + appSettings.getFileSeparator() + fileName);
				if (!f.exists())
				{
					fileName = "userSettingsUI_" + appSettings.getLanguage() + ".xml";
					f = new File(appDir + appSettings.getFileSeparator() + fileName);
					if (!f.exists())
					{
						fileName = "userSettingsUI.xml";	
					}
				}
				xmlGuideReader.loadXML(fileName, guide, appSettings, debugShell);
				guide.setMediaDirectory("userSettings");
				guideSettings = guide.getSettings();
				if (guide.getCss().equals("")) {
					style = defaultStyle;
				} else {
					style = guide.getCss();
				}
				//flag to allow updating of user preferences which is normally disabled in guides 
				guide.setInPrefGuide(true);
				//display the first page
				mainLogic.displayPage("start" , false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
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
				inPrefShell = true;
				Shell prefShell = new PreferenceShell().createShell(myDisplay, userSettings, appSettings);
				prefShell.open();
				while (!prefShell.isDisposed()) {
					if (!myDisplay.readAndDispatch())
						myDisplay.sleep();
				}
				inPrefShell = false;
			}
			catch (Exception ex) {
				logger.error(" FilePreferences " + ex.getLocalizedMessage());
				inPrefShell = false;
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
				inPrefShell = true;
				Shell prefShell = new GuidePreferenceShell().createShell(myDisplay, guideSettings, appSettings);
				prefShell.open();
				while (!prefShell.isDisposed()) {
					if (!myDisplay.readAndDispatch())
						myDisplay.sleep();
				}
				inPrefShell = false;
			}
			catch (Exception ex) {
				logger.error(" FileGuidePreferences " + ex.getLocalizedMessage());
				inPrefShell = false;
			}
			super.widgetSelected(e);
		}

	}
	
	class FileLibraryListener  extends SelectionAdapter {
		//File Library from menu
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter FileLibraryListener");
				//Display a modal shell for the guide specific preferences
				Shell libShell = new LibraryShell().createShell(myDisplay, appSettings, mainShell);
				libShell.open();
				while (!libShell.isDisposed()) {
					if (!myDisplay.readAndDispatch())
						myDisplay.sleep();
				}
			}
			catch (Exception ex) {
				logger.error(" FileLibraryListener " + ex.getLocalizedMessage());
				inPrefShell = false;
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
				HashMap<String, Integer> imageDimentions;

				//Label me = (Label)e.widget;
				Browser me = (Browser)e.widget;
				//Image myImage = (Image) me.getData("image");
				try {
					if (me.getData("imgPath") != null) {
						String strHtml;
						String tmpImagePath;
						String imgPath = (String) me.getData("imgPath");
						double imageRatio = ((Double) me.getData("imageRatio")).doubleValue();
						Rectangle RectImage = me.getBounds();
						double dblScreenRatio = (double) RectImage.height / (double) RectImage.width;
						logger.trace("imgPath: " + imgPath);
						logger.trace("dblScreenRatio: " + dblScreenRatio);
						logger.trace("dblImageRatio: " + imageRatio);
						logger.trace("Lable Height: " + RectImage.height);
						logger.trace("Lable Width: " + RectImage.width);
						
						int maxImageScale = (int) imageLabel.getData("maxImageScale");
						logger.trace("maxImageScale: " + maxImageScale);
						int maxheight = (int) imageLabel.getData("maxheight");
						logger.trace("maxheight: " + maxheight);
						int maxwidth = (int) imageLabel.getData("maxwidth");
						logger.trace("maxwidth: " + maxwidth);
						
						imageDimentions = GetNewDimentions(imageRatio, RectImage.height, RectImage.width, appSettings.getImgOffset(), maxImageScale != 0, maxheight, maxwidth);
						
						//String strHtml = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html  xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\"><head><meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" /><title></title><style type=\"text/css\">" + defaultStyle + "</style></head><body><table id=\"wrapper\"><tr><td><img src=\"" + tmpImagePath + "\" height=\"" + newHeight + "\" width=\"" + newWidth + "\" /></td></tr></table></body></html>";
						if (imgPath.endsWith(".gif")) {
							tmpImagePath = imgPath;
							strHtml = leftHTML.replace("DefaultStyle", defaultStyle + " body { overflow:hidden }");
							strHtml = strHtml.replace("BodyContent", "<table id=\"wrapper\"><tr><td><img src=\"" + tmpImagePath + "\" height=\"" + imageDimentions.get("newHeight") + "\" width=\"" + imageDimentions.get("newWidth") + "\" /></td></tr></table>");
						} else {
							BufferedImage img = null;
							try {
								ImageIO.setUseCache(false);
							    img = ImageIO.read(new File(imgPath));
							} catch (IOException e1) {
							}
							if (img.getColorModel().hasAlpha()) {
								img = comonFunctions.dropAlphaChannel(img);
							}
							BufferedImage imagenew =
									  Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
											  imageDimentions.get("newWidth"), imageDimentions.get("newHeight"), Scalr.OP_ANTIALIAS);
							String imgType = imgPath.substring(imgPath.length() - 3);
							String tmpPath = appSettings.getTempDir();
							File newImage = File.createTempFile("tmpImage", imgType, new File(tmpPath));
							newImage.deleteOnExit();
							tmpImagePath = newImage.getAbsolutePath();
							ImageIO.write(imagenew, imgType, newImage);			
							strHtml = leftHTML.replace("DefaultStyle", defaultStyle + " body { overflow:hidden }");
							strHtml = strHtml.replace("BodyContent", "<table id=\"wrapper\"><tr><td><img src=\"" + tmpImagePath + "\" height=\"" + imageDimentions.get("newHeight") + "\" width=\"" + imageDimentions.get("newWidth") + "\" /></td></tr></table>");
						}
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
	
	private static HashMap<String, Integer> GetNewDimentions(double imageRatio, int labelHeight, int labelWidth, double imgOffSet, boolean maxScale, int maxHeight, int maxWidth )
	{
		HashMap<String, Integer> returnValue = new HashMap<String, Integer>();
		double dblScreenRatio = (double) labelHeight / (double) labelWidth;
		int newHeight;
		int newWidth;
		if (((labelHeight >  maxHeight) && (labelWidth >  maxWidth)) && maxScale) {
			if (dblScreenRatio > imageRatio) {
				logger.trace("Scale Choice: 1.1");
				newHeight = (int) (((double) (maxWidth) * imageRatio) * imgOffSet);
				newWidth = (int) ((double) (maxWidth) * imgOffSet);
				logger.trace("New GT Dimentions: H: " + newHeight + " W: " + newWidth);
			} else {
				logger.trace("Scale Choice: 1.2");
				newHeight = (int) ((double) (maxHeight) * imgOffSet);
				newWidth = (int) (((double) (maxHeight) / imageRatio) * imgOffSet);
				logger.trace("New LT Dimentions: H: " + newHeight + " W: " + newWidth);
			}
		} else {
			if (dblScreenRatio > imageRatio) {
				logger.trace("Scale Choice: 2.1");
				newHeight = (int) (((double) labelWidth * imageRatio) * imgOffSet);
				newWidth = (int) ((double) labelWidth * imgOffSet);
				logger.trace("New GT Dimentions: H: " + newHeight + " W: " + newWidth);
			} else {
				logger.trace("Scale Choice: 2.2");
				newHeight = (int) ((double) labelHeight * imgOffSet);
				newWidth = (int) (((double) labelHeight / imageRatio) * imgOffSet);
				logger.trace("New LT Dimentions: H: " + newHeight + " W: " + newWidth);
			}
		}
		returnValue.put("newHeight", newHeight);
		returnValue.put("newWidth", newWidth);
		return returnValue;
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
				if (!lblRight.isDisposed()) {
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
							try {
								calCountDown = null;
								lblRight.setText("");
								comonFunctions.SetFlags(guide.getDelaySet(), guide.getFlags());
								comonFunctions.UnsetFlags(guide.getDelayUnSet(), guide.getFlags());
								comonFunctions.processSrciptVars(guide.getDelayScriptVar(), guideSettings);
								javascript = guide.getDelayjScript();
								if (!javascript.equals("")) {
									mainShell.runJscript(javascript, false);
								}
								mainLogic.displayPage(guide.getDelTarget(), false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
							}
							catch (Exception ex) {
								logger.error(" shellTimer Delay Zero " + ex.getLocalizedMessage(), ex);
							}
						} else {
							try {
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
							catch (Exception ex) {
								logger.error(" shellTimer Update Count Down " + ex.getLocalizedMessage(), ex);
							}
							
						}
					}
					try {
						if (appSettings.isClock()) {
							lblLeft.setText(dateFormat.format(cal.getTime()));
						} else {
							lblLeft.setText("");
						}
					}
					catch (Exception ex) {
						logger.error(" shellTimer Update Clock " + ex.getLocalizedMessage(), ex);
					}
					
					try {
						//check timers
						if (getTimerCount() > 0) {
						    Iterator<Entry<String, Timer>> it = timer.entrySet().iterator();
						    while (it.hasNext()) {
						        Map.Entry<String, Timer> pair = it.next();
								Timer objTimer =  pair.getValue();
								Calendar calTemp =  objTimer.getTimerEnd();
								//logger.debug("Timer: " + objTimer.getId() + " End: " + calTemp.getTime());
								//logger.debug("Timer: " + objTimer.getId() + " Now: " + cal.getTime());
								if (cal.after(calTemp)) {
									logger.debug("Timer: " + objTimer.getId() + " Triggered");
									//add a year to the timer so we don't trigger it again
									calTemp.add(Calendar.YEAR, 1);
									pair.getValue().setTimerEnd(calTemp);
									comonFunctions.SetFlags(objTimer.getSet(), guide.getFlags());
									comonFunctions.UnsetFlags(objTimer.getUnSet(), guide.getFlags());
									String strImage = objTimer.getImageId();
									if (!strImage.equals("")) {
										String imgPath = comonFunctions.getMediaFullPath(strImage, appSettings.getFileSeparator(), appSettings, guide);
										File flImage = new File(imgPath);
										if (flImage.exists()){
											try {
												setImageLabel(imgPath, strImage);
											} catch (Exception e1) {
												logger.error("Timer Image Exception " + e1.getLocalizedMessage(), e1);
											}								
										}
									} 
									String displayText = objTimer.getText();
									if (!displayText.equals("")) {
										try {
											// Media Directory
											try {
												String mediaPath;
												mediaPath = comonFunctions.getMediaFullPath("", appSettings.getFileSeparator(), appSettings, guide);
												displayText = displayText.replace("\\MediaDir\\", mediaPath);
											} catch (Exception e) {
												logger.error("displayPage BrwsText Media Directory Exception " + e.getLocalizedMessage(), e);
											}
											
											displayText = comonFunctions.substituteTextVars(displayText, guideSettings, userSettings);
		
											setBrwsText(displayText, "");
										} catch (Exception e) {
											logger.error("Timer BrwsText Exception " + e.getLocalizedMessage(), e);
										}
									}
									javascript = objTimer.getjScript();
									if (!javascript.equals("")) {
										mainShell.runJscript(javascript, false);
									}
									String target = objTimer.getTarget();
									if (!target.equals("")) {
										lblRight.setText("");
										mainLogic.displayPage(target, false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
									}
								}
						        //it.remove(); // avoids a ConcurrentModificationException
							}
						}
					}
					catch (Exception ex) {
						logger.error(" shellTimer Timers " + ex.getLocalizedMessage(), ex);
					}
					dateFormat = null;
					cal = null;
					javascript = null;
					strSeconds = null;
					strMinutes = null;
					strTimeLeft = null;
					//re run in 0.1 seconds
					myDisplay.timerExec(100, new shellTimer());
				}
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
		//delete old image if it exists
		if (oldImage != null && oldImage.exists())
		{
			oldImage.delete();
			oldImage = null;
		}
		if (oldImage2 != null && oldImage2.exists())
		{
			oldImage2.delete();
			oldImage2 = null;
		}
		
		//display an image in the area to the left of the screen
		HashMap<String, Integer> imageDimentions;
		imgOverRide = false;
		
		
		if (imgPath.lastIndexOf(".") == -1)
		{
			try {
				boolean newFile = false;
				String extension = "";
				File tmpFile = new File(imgPath);
				URLConnection con = tmpFile.toURI().toURL().openConnection();
				String mimeType = con.getContentType();
				con = null;

				
				switch (mimeType)
				{
				case "image/jpeg":
					extension = ".jpg";
					newFile = true;
					break;
				case "image/bmp":
					extension = ".bmp";
					newFile = true;
					break;
				case "image/gif":
					extension = ".gif";
					newFile = true;
					break;
				case "image/png":
					extension = ".png";
					newFile = true;
					break;
				case "image/tiff":
					extension = ".tiff";
					newFile = true;
					break;
				}
				if (newFile)
				{
					String tmpPath = appSettings.getTempDir();
					oldImage2 = File.createTempFile("TempImage2", extension, new File(tmpPath));
					oldImage2.deleteOnExit();
					FileInputStream strSrc = new FileInputStream(tmpFile);
					FileChannel src = strSrc.getChannel();
					FileOutputStream strDest = new FileOutputStream(oldImage2); 
					FileChannel dest = strDest.getChannel();
					dest.transferFrom(src, 0, src.size());
					strSrc.close();
					strDest.close();
					src.close();
					dest.close();
					imgPath = oldImage2.getAbsolutePath();
				}
			} catch (IOException e1) {
			}
		}
		
		Image memImage = new Image(myDisplay, imgPath);
		imageLabel.setData("imgPath", imgPath);
		try {
			String tmpImagePath;
			String strHtml;
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
			
			int maxImageScale = appSettings.getMaxImageScale();
			int maxheight = (int) (imgData.height * ( (double) (maxImageScale / 100)));
			int maxwidth = (int) (imgData.width * ((double) (maxImageScale / 100)));
			
			imageLabel.setData("maxImageScale", maxImageScale);
			imageLabel.setData("maxheight", maxheight);
			imageLabel.setData("maxwidth", maxwidth);
			
			imageDimentions = GetNewDimentions(dblImageRatio, RectImage.height, RectImage.width, appSettings.getImgOffset(), maxImageScale != 0, maxheight, maxwidth);

			if (imgPath.endsWith(".gif")) {
				memImage.dispose();
				memImage = null;
				tmpImagePath = imgPath;
				strHtml = leftHTML.replace("DefaultStyle", defaultStyle + " body { overflow:hidden }");
				strHtml = strHtml.replace("BodyContent", "<table id=\"wrapper\"><tr><td><img src=\"" + tmpImagePath + "\" height=\"" + imageDimentions.get("newHeight") + "\" width=\"" + imageDimentions.get("newWidth") + "\" /></td></tr></table>");
			} else {
				BufferedImage img = null;
				try {
					ImageIO.setUseCache(false);
					img = ImageIO.read(new File(imgPath));
				} catch (IOException e) {
				}
				if (img.getColorModel().hasAlpha()) {
					img = comonFunctions.dropAlphaChannel(img);
				}
				BufferedImage imageNew =
						Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
								imageDimentions.get("newWidth"), imageDimentions.get("newHeight"), Scalr.OP_ANTIALIAS);
				String imgType = "";
				int pos = imgPath.lastIndexOf(".");
				if (pos > -1)
				{
					imgType = imgPath.substring(pos + 1);
				}
				String tmpPath = appSettings.getTempDir();
				File newImage = File.createTempFile("tmpImage", "." + imgType, new File(tmpPath));
				oldImage = newImage;
				newImage.deleteOnExit();
				tmpImagePath = newImage.getAbsolutePath();
				ImageIO.write(imageNew, imgType.toLowerCase(), newImage);
				//tmpImagePath = System.getProperty("user.dir") + File.pathSeparator + "tmpImage." + imgType;
				//ImageIO.write(imagenew, imgType, new File(tmpImagePath));			
				//Image tmpImage2 = imageLabel.getImage();
				//imageLabel.setImage(resize(memImage, newWidth, newHeight));
				memImage.dispose();
				memImage = null;
				//if (tmpImage2 != null) {
				//	tmpImage2.dispose();
				//}
				strHtml = leftHTML.replace("DefaultStyle", defaultStyle + " body { overflow:hidden }");
				strHtml = strHtml.replace("BodyContent", "<table id=\"wrapper\"><tr><td><img src=\"" + tmpImagePath + "\" height=\"" + imageDimentions.get("newHeight") + "\" width=\"" + imageDimentions.get("newWidth") + "\" /></td></tr></table>");
			}
			imageLabel.setText(strHtml, true);
			logger.trace("Open: " + imgPath);
		}
		catch (Exception ex6) {
			logger.error("Process Image error " + ex6.getLocalizedMessage(), ex6);
		}
		mediaPanel.setVisible(false);
		webcamPanel.setVisible(false);
		this.imageLabel.setVisible(true);
		leftFrame.layout(true);
	}

	public String getStyle() {
		return style;
	}

	public void setImageHtml(String leftHtml) {
		try {
			logger.trace("setImageHtml: " + leftHtml);
			imgOverRide = true;
			imageLabel.setText(leftHtml, true);
			mediaPanel.setVisible(false);
			webcamPanel.setVisible(false);
			this.imageLabel.setVisible(true);
			leftFrame.layout(true);
		}
		catch (Exception ex) {
			logger.error("setImageHtml error " + ex.getLocalizedMessage(), ex);
		}
	}
	
	public boolean showWebcam() {
		//displays the webcam in the area to the left of the screen
		if (webcamOn) {
			try {
				webcam = Webcam.getDefault();
				
				if (webcam != null)
				{
					mainShell.setLeftText("", "");
					this.imageLabel.setVisible(false);
					this.mediaPanel.setVisible(false);
					this.webcamPanel.setVisible(true);

					Dimension[] dimensions = webcam.getViewSizes();
					Dimension size = dimensions[dimensions.length - 1];
					webcam.setViewSize(size);
					panel = new WebcamPanel(webcam, size, false);
					panel.setMirrored(true);
					webcamRoot.getContentPane().add(panel);
					panel.start();
					leftFrame.layout(true);
					webcamRoot.validate();
					webcamVisible = true;
					logger.debug("MainShell playVideo: ShowWebcam");
					
				}
				else
				{
					mainShell.setLeftText("No Webcam detected", "");
					Webcam.getDiscoveryService().stop();
				}
				
			} catch (Exception e) {
				logger.error("showWebcam " + e.getLocalizedMessage(), e);		
			}
		}
		return webcam != null;
	}

	public void playVideo(String video, int startAt, int stopAt, int loops, String target, String jscript, String scriptVar, int volume) {
		//plays a video in the area to the left of the screen
		//sets the number of loops, start / stop time and any page to display if the video finishes
		//starts the video using a non UI thread so VLC can't hang the application
		if (videoOn) {
			try {
				mainShell.setLeftText("", "");
				this.imageLabel.setVisible(false);
				this.webcamPanel.setVisible(false);
				this.mediaPanel.setVisible(true);
				leftFrame.layout(true);
				videoLoops = loops;
				videoTarget = target;
				videoStartAt = startAt;
				videoStopAt = stopAt;
				videoJscript = jscript;
				videoScriptVar = scriptVar;
				videoPlay = true;
				String mrlVideo = "file:///" + video;
				logger.debug("MainShell playVideo: " + mrlVideo + " videoLoops: " + videoLoops + " videoTarget: " + videoTarget + " videoPlay: " + videoPlay);
				VideoPlay videoPlay = new VideoPlay();
				videoPlay.setVideoPlay(mediaPlayer, mrlVideo, volume);
				Thread videoPlayThread = new Thread(videoPlay, "videoPlay");
				videoPlayThread.setName("videoPlayThread");
				videoPlayThread.start();
				videoPlayed = true;
			} catch (Exception e) {
				logger.error("playVideo " + e.getLocalizedMessage(), e);		
			}
		}
	}

	class VideoPlay implements Runnable {
		//code to start the video on a separate thread
		private SwtEmbeddedMediaPlayer mediaPlayer;
		private String video;
		private int volume;
		
		@Override
		public void run() {
			try {
				logger.debug("MainShell VideoPlay new Thread " + video);
				int mediaVolume = appSettings.getVideoVolume();
				if (volume < 100)
				{
					if (volume == 0)
					{
						mediaVolume = 0;
					}
					else
					{
						mediaVolume = (int) ((double) mediaVolume * ((double) volume / (double) 100));
					}
				}
				
				mediaPlayer.setVolume(mediaVolume);
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
					myDisplay.syncExec(
							new Runnable() {
								public void run(){
									mediaPanel.setVisible(true);
									webcamPanel.setVisible(false);
									imageLabel.setVisible(false);
									leftFrame.layout(true);
								}
							});											
					 mediaPlayer.playMedia(video, vlcArgs.toArray(new String[vlcArgs.size()]));
						//run on the main UI thread
				}
			} catch (Exception e) {
				logger.error("VideoPlay run " + e.getLocalizedMessage(), e);		
			}
		}

		public void setVideoPlay(SwtEmbeddedMediaPlayer mediaPlayer, String video, int volume) {
			this.mediaPlayer = mediaPlayer;
			this.video = video;
			if (volume > 100)
			{
				this.volume = 100;
			} 
			else if (volume < 0)
			{
				volume = 0;
			}
			else
			{
				this.volume = volume;
			}
		}

	}
	
	public void clearImage() {
		//imageLabel.setImage(null);
		String strHTML = leftHTML.replace("DefaultStyle", defaultStyle + " body { overflow:hidden }");
		imageLabel.setText(strHTML, true);
	}

	
	
	public void playAudio(String audio, int startAt, int stopAt, int loops, String target, String jscript, String scriptVar, int volume) {
		// run audio on another thread
		try {
			if (audioPlayer != null) {
				audioPlayer.audioStop();
				logger.trace("playAudio audioStop");
			}
			audioPlayer = new AudioPlayer(audio, startAt, stopAt, loops, target, mainShell, jscript, scriptVar, volume);
			threadAudioPlayer = new Thread(audioPlayer, "audioPlayer");
			threadAudioPlayer.setName("threadAudioPlayer");
			threadAudioPlayer.start();
		} catch (Exception e) {
			logger.error("playAudio " + e.getLocalizedMessage(), e);		
		}
	}
	
	public void playAudio2(String audio, int startAt, int stopAt, int loops, String target, String jscript, String scriptVar, int volume) {
		// run audio on another thread
		try {
			if (audioPlayer2 != null) {
				audioPlayer2.audioStop();
				logger.trace("playAudio2 audioStop");
			}
			audioPlayer2 = new AudioPlayer(audio, startAt, stopAt, loops, target, mainShell, jscript, scriptVar, volume);
			threadAudioPlayer2 = new Thread(audioPlayer2, "audioPlayer");
			threadAudioPlayer2.setName("threadAudioPlayer2");
			threadAudioPlayer2.start();
		} catch (Exception e) {
			logger.error("playAudio2 " + e.getLocalizedMessage(), e);		
		}
	}
	

	public void setBrwsText(String brwsText, String overRideStyle) {
		//set HTML to be displayed in the browser control to the right of the screen
		if (overRideStyle.equals("")) {
			overRideStyle = style;
		}
		String strHTML;
		try {
			strHTML = rightHTML.replace("DefaultStyle", overRideStyle);
			strHTML = strHTML.replace("BodyContent", brwsText);
			this.brwsText.setText(strHTML);
			if (appSettings.isToclipboard()) {
				try {
					//copy text to clip board for use in TTS
					String htmlString = brwsText.replaceAll("\\<.*?\\>", " ");
				    StringSelection stringSelection = new StringSelection(htmlString);
				    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				    clipboard.setContents(stringSelection, stringSelection);
				} catch (Exception e2) {
					logger.error("copy to clip board " + e2.getLocalizedMessage(), e2);		
				}
			}
		} catch (Exception e1) {
			logger.error("displayPage Text Exception " + e1.getLocalizedMessage(), e1);
			strHTML = rightHTML.replace("DefaultStyle", overRideStyle);
			strHTML = strHTML.replace("BodyContent", "");
			this.brwsText.setText(strHTML);
		}
	}

	public void setRightHtml(String strHTML) {
		//set HTML to be displayed in the browser control to the left of the screen
		try {
			this.brwsText.setText(strHTML);
		} catch (Exception e1) {
			logger.error("displayPage Text Exception " + e1.getLocalizedMessage(), e1);
			strHTML = rightHTML.replace("DefaultStyle", style);
			strHTML = strHTML.replace("BodyContent", "");
			this.brwsText.setText(strHTML);
		}
	}
	
	public void setLeftText(String brwsText, String overRideStyle) {
		//set HTML to be displayed in the browser control to the left of the screen
		if (overRideStyle.equals("")) {
			overRideStyle = style;
		}
		String strHTML;
		try {
			strHTML = leftHTML.replace("DefaultStyle", overRideStyle);
			strHTML = strHTML.replace("BodyContent", brwsText);
			this.imageLabel.setText(strHTML);
			if (appSettings.isToclipboard()) {
				try {
					//copy text to clip board for use in TTS
					String htmlString = brwsText.replaceAll("\\<.*?\\>", " ");
				    StringSelection stringSelection = new StringSelection(htmlString);
				    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				    clipboard.setContents(stringSelection, stringSelection);
				} catch (Exception e2) {
					logger.error("copy to clip board " + e2.getLocalizedMessage(), e2);		
				}
			}
		} catch (Exception e1) {
			logger.error("displayPage Text Exception " + e1.getLocalizedMessage(), e1);
			strHTML = leftHTML.replace("DefaultStyle", overRideStyle);
			strHTML = strHTML.replace("BodyContent", "");
			this.imageLabel.setText(strHTML);
		}
		mediaPanel.setVisible(false);
		webcamPanel.setVisible(false);
		this.imageLabel.setVisible(true);
		leftFrame.layout(true);
	}

	public void setLeftHtml(String strHTML) {
		//set HTML to be displayed in the browser control to the left of the screen
		try {
			this.imageLabel.setText(strHTML);
		} catch (Exception e1) {
			logger.error("setLeftHtml Text Exception " + e1.getLocalizedMessage(), e1);
			strHTML = leftHTML.replace("DefaultStyle", style);
			strHTML = strHTML.replace("BodyContent", "");
			this.imageLabel.setText(strHTML);
		}
		mediaPanel.setVisible(false);
		webcamPanel.setVisible(false);
		this.imageLabel.setVisible(true);
		leftFrame.layout(true);
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
			btnDynamic.setFont(buttonFont);
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
			btnDynamic.setData("scriptVar", guide.getDelayScriptVar());
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
		Boolean isWebCamButton = button instanceof WebcamButton;
		try {
			strBtnTarget = button.getTarget();
			strBtnText = button.getText();
			strBtnImage = button.getImage();
			if (!strBtnImage.equals("")){
				String imgPath = comonFunctions.getMediaFullPath(strBtnImage, appSettings.getFileSeparator(), appSettings, guide);
				File flImage = new File(imgPath);
				if (flImage.exists()){
					strBtnImage = imgPath;
				} else {
					strBtnImage = "";
				}
			}
			com.snapps.swt.SquareButton btnDynamic = new com.snapps.swt.SquareButton(btnComp, SWT.PUSH );
			
			int fntHeight = 0;
			try {
				fntHeight = Integer.parseInt(button.getFontHeight());
			}
			catch (Exception e) {
				fntHeight = 0;
			}
			try {
				if (button.getFontName() == "" && fntHeight == 0) {
					btnDynamic.setFont(buttonFont);
				} else {
					FontData[] fontData = buttonFont.getFontData();
					if (fntHeight > 0) {
						fontData[0].setHeight(fntHeight);
					}
					if (button.getFontName() != "") {
						fontData[0].setName(button.getFontName());
					}
					
					final Font newFont = new Font(myDisplay, fontData);
					btnDynamic.setFont(newFont);
	
					// Since you created the font, you must dispose it
					btnDynamic.addDisposeListener(new DisposeListener() {
						@Override
						public void widgetDisposed(DisposeEvent e) {
							newFont.dispose();
						}
					});
				}
			}
			catch (Exception e) {
				logger.error("addButton set font" + e.getLocalizedMessage(), e);
			}
			
			try {
				org.eclipse.swt.graphics.Color bgColor1 = button.getbgColor1();
				org.eclipse.swt.graphics.Color bgColor2 = button.getbgColor2();
				org.eclipse.swt.graphics.Color fontColor = button.getfontColor();
				
				btnDynamic.setDefaultColors(bgColor1, bgColor2, btnDynamic.getBackground(), fontColor);
			}
			catch (Exception e) {
				logger.error("addButton set colors" + e.getLocalizedMessage(), e);
			}

			
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
			btnDynamic.setData("scriptVar", button.getScriptVar());
			btnDynamic.setData("javascript", javascript);
			logger.debug("displayPage Button Text " + strBtnText + " Target " + strBtnTarget + " Set " + strButtonSet + " UnSet " + strButtonUnSet);
			
			String hotKey = button.getHotKey();
			if (!hotKey.equals("")) {
				hotKeys.put(hotKey, btnDynamic);
			}

			String btnId = button.getId();
			if (!btnId.equals("")) {
				buttons.put(btnId, btnDynamic);
			}

			btnDynamic.setData("Target", strBtnTarget);
			if (isWebCamButton)
			{
				WebcamButton webcamButton = (WebcamButton) button;
				btnDynamic.setData("webcamFile", webcamButton.get_destination());
				switch (webcamButton.get_type())
				{
					case "Capture":
						btnDynamic.addSelectionListener(new WebcamCaptureListener());
						break;
				}
			}
			else
			{
				btnDynamic.addSelectionListener(new DynamicButtonListner());
			}
			btnDynamic.setEnabled(!button.getDisabled());
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
				String scriptVar = (String) btnClicked.getData("scriptVar");
				comonFunctions.processSrciptVars(scriptVar, guideSettings);
				strTag = (String) btnClicked.getData("Target");
				String javascript = (String) btnClicked.getData("javascript");
				runJscript(javascript, false);
				mainLogic.displayPage(strTag, false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
			}
			catch (Exception ex) {
				logger.error(" DynamicButtonListner " + ex.getLocalizedMessage(), ex);
			}
			logger.trace("Exit DynamicButtonListner");
		}
	}

	
	/*private static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
		BufferedImage image;

		// if the source image is already the target type, return the source image

		if (sourceImage.getType() == targetType)
			image = sourceImage;

		// otherwise create a new image of the target type and draw the new
		// image

		else {
			image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
		}

		return image;
	}	
	*/
	
	class WebcamCaptureListener extends DynamicButtonListner
	{
		public void widgetSelected(SelectionEvent event) {
			try {
				
				logger.trace("Enter WebcamCaptureListener");
				String strFile;
				com.snapps.swt.SquareButton btnClicked;
				btnClicked = (com.snapps.swt.SquareButton) event.widget;
				strFile = (String) btnClicked.getData("webcamFile");
				if (!comonFunctions.CanCreateFile(strFile)) {
					strFile = comonFunctions.getMediaFullPath(strFile, appSettings.getFileSeparator(), appSettings, guide);
				}
				
				try
				{
					BufferedImage image = webcam.getImage();
					ImageIO.write(image, "JPG", new File(strFile));
				}
				catch (Exception ex) {
					logger.error(" WebcamCaptureListener take and save image " + ex.getLocalizedMessage(), ex);
				}
				
				super.widgetSelected(event);
			}
			catch (Exception ex) {
				logger.error(" WebcamCaptureListener " + ex.getLocalizedMessage(), ex);
			}
			logger.trace("Exit WebcamCaptureListener");
		}
	}
	
	public void runJscript(String function, boolean pageLoading) {
		runJscript(function, null, pageLoading);
	}
	
	//run the javascript function passed 
	public void runJscript(String function, OverRide overRide, boolean pageLoading ) {
		try {
			getFormFields();
			refreshVars();
			if (function == null) function = "";
			if (! function.equals("")) {
				Page objCurrPage = guide.getChapters().get(guideSettings.getChapter()).getPages().get(guideSettings.getCurrPage());
				String pageJavascript = objCurrPage.getjScript();
				Jscript jscript = new Jscript(guide, userSettings, appSettings, guide.getInPrefGuide(), mainShell, overRide, pageJavascript, function, pageLoading);
				SwingUtilities.invokeLater(jscript);
				while (jscript.isRunning())
				{
					Display.getCurrent().readAndDispatch();
				}
			}
		}
		catch (Exception ex) {
			logger.error(" run java script " + ex.getLocalizedMessage(), ex);
		}
	}

	//run the javascript function passed 
	public void runJscript(String function, String pageJavascript ) {
		try {
			getFormFields();
			refreshVars();
			if (function == null) function = "";
			if (! function.equals("")) {
				Jscript jscript = new Jscript(guide, userSettings, appSettings, guide.getInPrefGuide(), mainShell, null, pageJavascript, function, false);
				SwingUtilities.invokeLater(jscript);
				while (jscript.isRunning())
				{
					Display.getCurrent().readAndDispatch();
				}
			}
		}
		catch (Exception ex) {
			logger.error(" run java script " + ex.getLocalizedMessage(), ex);
		}
	}

	//get any fields from the html form and store them in guide settings for use in the next java script call.
	private void getFormFields() {
		try {
			String evaluateScript = "" +
					"var vforms = document.forms;" +
					"var vreturn = '';" +
					"for (var formidx = 0; formidx < vforms.length; formidx++) {" +
					"var vform = vforms[formidx];" +
					"for (var controlIdx = 0; controlIdx < vform.length; controlIdx++) {" +
					"var control = vform.elements[controlIdx];" +
					"if (control.type === \"select-one\") {" +
					"var item = control.selectedIndex;" +
					"var value = \"\";" +
					"if (item > -1)" +
					"{" +
					"	value = control.item(item).value;" +
					"}" +
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
						guideSettings.setScriptVar(name, checked);
					}
					if (type.equals("radio")) {
						if (checked.equals("true")) {
							guideSettings.setFormField(name, value);
							guideSettings.setScriptVar(name, value);
						}
					}
					if (type.equals("text")) {
						guideSettings.setFormField(name, value);
						guideSettings.setScriptVar(name, value);
					}
					if (type.equals("file")) {
						guideSettings.setFormField(name, value);
						guideSettings.setScriptVar(name, value);
					}

					if (type.equals("select-one")) {
						guideSettings.setFormField(name, value);
						guideSettings.setScriptVar(name, value);
					}

					logger.trace(name + "|" + value +  "|" + type +  "|" + checked);
				}
			}
			String node2 = (String) imageLabel.evaluate(evaluateScript);
			fields = node2.split("\\|");
			for (int i = 0; i < fields.length; i++) {
				values = fields[i].split("¬");
				if (!fields[i].equals("")) {
					name = values[0];
					value = values[1];
					type = values[2];
					checked = values[3];
					if (type.equals("checkbox")) {
						guideSettings.setFormField(name, checked);
						guideSettings.setScriptVar(name, checked);
					}
					if (type.equals("radio")) {
						if (checked.equals("true")) {
							guideSettings.setFormField(name, value);
							guideSettings.setScriptVar(name, value);
						}
					}
					if (type.equals("text")) {
						guideSettings.setFormField(name, value);
						guideSettings.setScriptVar(name, value);
					}

					if (type.equals("file")) {
						guideSettings.setFormField(name, value);
						guideSettings.setScriptVar(name, value);
					}

					if (type.equals("select-one")) {
						guideSettings.setFormField(name, value);
						guideSettings.setScriptVar(name, value);
					}

					logger.trace(name + "|" + value +  "|" + type +  "|" + checked);
				}
			}
		}
		catch (Exception ex) {
			logger.error(" get form fields " + ex.getLocalizedMessage(), ex);
		}
	}


	//force a redisplay of the button are
	//set focus to the last button
	public void layoutButtons() {
		try {
			btnComp.layout();
			Control[] controls = this.btnComp.getChildren();
			if (controls.length > 0) {
				controls[0].setFocus();
			}
			controls = null;
		}
		catch (Exception ex) {
			logger.error(" layoutButtons " + ex.getLocalizedMessage(), ex);
		}
	}

	public void enableButton (String id) {
		try {
			com.snapps.swt.SquareButton button;
			button = buttons.get(id);
			if (button != null) {
				button.setEnabled(true);
			}
		}
		catch (Exception ex) {
			logger.error(" enable Button " + ex.getLocalizedMessage(), ex);
		}
	}

	public void changeButton (String id, boolean enable, String text, String target) {
		try {
			com.snapps.swt.SquareButton button;
			button = buttons.get(id);
			if (button != null) {
				button.setEnabled(enable);
				button.setText(text);
				button.setData("Target", target);
			}
		}
		catch (Exception ex) {
			logger.error(" change Button " + ex.getLocalizedMessage(), ex);
		}
	}

	public void disableButton (String id) {
		try {
			com.snapps.swt.SquareButton button;
			button = buttons.get(id);
			if (button != null) {
				button.setEnabled(false);
			}
		}
		catch (Exception ex) {
			logger.error(" disable Button " + ex.getLocalizedMessage(), ex);
		}
	}

	public void setMetronomeBPM(int metronomeBPM, int loops, int resolution, String Rhythm) {
		// run metronome on another thread
		try {
			logger.trace("setMetronomeBPM");
			if (appSettings.isMetronome()) {
				metronome.metronomeStart(metronomeBPM, appSettings.getMidiInstrument(), loops, resolution, Rhythm, appSettings.getMidiVolume());
			}
		} catch (Exception e) {
			logger.error(" setMetronomeBPM " + e.getLocalizedMessage(), e);
		}
	}

	public void displayPage(String target) {
		getFormFields();
		mainLogic.displayPage(target, false, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
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

		if (audioPlayer2 != null) {
			audioPlayer2.audioStop();
		}
		audioPlayer2 = null;
		threadAudioPlayer2 = null;
	}

	public void stopWebcam(boolean shellClosing)
	{
		if (webcamOn && webcamVisible)
		{
			panel.stop();
			webcamRoot.getContentPane().removeAll();
			webcamVisible = false;
		}
	}
	
	public void stopVideo(boolean shellClosing) {
		if (videoOn) {
			try {
				if (mediaPlayer != null) {
					videoLoops = 0;
					videoTarget = "";
					videoPlay = false;
					logger.debug("MainShell stopVideo " + mediaPlayer.mrl());
					if (mediaPlayer.mrl() != null && videoPlayed)
					{
						VideoStop videoStop = new VideoStop();
						videoStop.setMediaPlayer(mediaPlayer, shellClosing);
						Thread videoStopThread = new Thread(videoStop, "videoStop");
						videoStopThread.setName("videoStopThread");
						mediaPanel.setVisible(false);
						webcamPanel.setVisible(false);
						imageLabel.setVisible(true);
						leftFrame.layout(true);
						videoStopThread.start();
					}
				}
			} catch (Exception e) {
				logger.error(" stopVideo " + e.getLocalizedMessage(), e);
			}
		}
	}
	
	class VideoStop implements Runnable {
		private SwtEmbeddedMediaPlayer mediaPlayer;
		private boolean shellClosing;

		public void setMediaPlayer(SwtEmbeddedMediaPlayer mediaPlayer, boolean shellClosing) {
			this.mediaPlayer = mediaPlayer;
			this.shellClosing = shellClosing;
		}

		@Override
		public void run() {
			try {
				if (mediaPlayer != null && mediaPlayer.isPlayable()) {
					logger.debug("MainShell VideoStop run: Stopping media player " + mediaPlayer.mrl());
					mediaPlayer.stop();
					if (shellClosing)
					{
						mediaPlayer.release();
					}
				}
			} catch (Exception e) {
				logger.error(" MainShell VideoStop run: " + e.getLocalizedMessage(), e);
			}
		}
		
	}
	
	public void stopDelay() {
		try {
			calCountDown = null;
			if (!lblRight.isDisposed()) {
				lblRight.setText("");
			}
		}
		catch (Exception ex) {
			logger.error(" stopDelay " + ex.getLocalizedMessage(), ex);
		}
	}

	public void stopAll(boolean shellClosing) {
		try {
			hotKeys = new HashMap<String, com.snapps.swt.SquareButton>();
		}
		catch (Exception ex) {
			logger.error(" stophotKeys " + ex.getLocalizedMessage(), ex);
		}
		try {
			buttons = new HashMap<String, com.snapps.swt.SquareButton>();
		}
		catch (Exception ex) {
			logger.error(" stopbuttons " + ex.getLocalizedMessage(), ex);
		}
		try {
		stopDelay();
		}
		catch (Exception ex) {
			logger.error(" stopDelay " + ex.getLocalizedMessage(), ex);
		}
		try {
		stopMetronome();
		}
		catch (Exception ex) {
			logger.error(" stopMetronome " + ex.getLocalizedMessage(), ex);
		}
		try {
		stopAudio();
		}
		catch (Exception ex) {
			logger.error(" stopAudio " + ex.getLocalizedMessage(), ex);
		}
		try {
			stopVideo(shellClosing);
		}
		catch (Exception ex) {
			logger.error(" stopVideo " + ex.getLocalizedMessage(), ex);
		}
		try {
			stopWebcam(shellClosing);
		}
		catch (Exception ex) {
			logger.error(" stopWebcam " + ex.getLocalizedMessage(), ex);
		}
		try {
			timerReset();
		}
		catch (Exception ex) {
			logger.error(" timerReset " + ex.getLocalizedMessage(), ex);
		}
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setDefaultStyle() {
		this.style = defaultStyle;
	}

	public Shell getShell2() {
		return shell2;
	}

	public Boolean getMultiMonitor() {
		return multiMonitor;
	}

	public Timer getTimer(String timKey) {
		return timer.get(timKey);
	}

	public void addTimer(Timer timer) {
		String tmrId = timer.getId();
		if (tmrId.equals("")) {
			tmrId = java.util.UUID.randomUUID().toString();
		}
		this.timer.put(tmrId, timer);
	}

	public int getTimerCount() {
		return timer.size();
	}
	
	private void timerReset() {
		timer = new HashMap<String, Timer>();
	}
	
	public void resetTimer(String id, int delay) {
		Timer objTimer = timer.get(id);
		Calendar timCountDown = Calendar.getInstance();
		timCountDown.add(Calendar.SECOND, delay);
		objTimer.setTimerEnd(timCountDown);		
	}
	
	public void updateJConsole(String logText) {
		debugShell.updateJConsole(logText);
	}

	public void clearJConsole() {
		debugShell.clearJConsole();
	}

	public void refreshVars() {
		debugShell.refreshVars();
	}
	
	public GuideSettings getGuideSettings() {
		return guideSettings;
		
	}
	
	public void showDebug() {
		debugShell.showDebug();
	}

	public ContextFactory getContextFactory() {
		return factory;
	}


    private static VideoSurfaceAdapter getVideoSurfaceAdapter() {
        VideoSurfaceAdapter videoSurfaceAdapter;
        if(RuntimeUtil.isNix()) {
            videoSurfaceAdapter = new LinuxVideoSurfaceAdapter();
        }
        else if(RuntimeUtil.isWindows()) {
            videoSurfaceAdapter = new WindowsVideoSurfaceAdapter();
        }
        else if(RuntimeUtil.isMac()) {
            videoSurfaceAdapter = new MacVideoSurfaceAdapter();
        }
        else {
            throw new RuntimeException("Unable to create a media player - failed to detect a supported operating system");
        }
        return videoSurfaceAdapter;
    }

	public void setGuideSettings(GuideSettings guideSettings) {
		this.guideSettings = guideSettings;
	}	
	
}