package org.guideme.guideme.ui;

import java.awt.Frame;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

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
import org.guideme.guideme.readers.XmlGuideReader;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.settings.UserSettings;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

public class MainShell {
	private static Logger logger = LogManager.getLogger();
	private static org.eclipse.swt.graphics.Color colourBlack;
	private AppSettings appSettings;
	private int MintFontSize;
	private int MintHtmlFontSize;
	private String strGuidePath;
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
	private AudioMediaPlayerComponent audioPlayerComponent = new AudioMediaPlayerComponent();
	private Guide guide = new Guide();
	private MainShell mainShell;

	public Shell createShell(final Display display) {
		logger.trace("Enter createShell");
		// Initialise variable
		int[] intWeights1 = new int[2];
		int[] intWeights2 = new int[2];
		colourBlack = display.getSystemColor(SWT.COLOR_BLACK);
		try {
			appSettings = new AppSettings();

			// debug flag
			//blnDebug = appSettings.getDebug();

			// font size
			MintFontSize = appSettings.getFontSize();

			// font size
			MintHtmlFontSize = appSettings.getHtmlFontSize();

			// path to the xml files
			strGuidePath = appSettings.getDataDirectory();

			// array to hold the various flags
			guideSettings.setFlags("");

			intWeights1 = appSettings.getSash1Weights();
			intWeights2 = appSettings.getSash2Weights();

			userSettings = new UserSettings();

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
			Monitor primary = display.getPrimaryMonitor();
			Rectangle clientArea = primary.getClientArea();
			shell.setText("SWT Test");
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

			videoFrame = SWT_AWT.new_Frame(mediaPanel);         
			videoSurfaceCanvas = new Canvas();

			videoSurfaceCanvas.setBackground(java.awt.Color.black);
			videoFrame.add(videoSurfaceCanvas);

			mediaPlayerFactory = new MediaPlayerFactory("--no-video-title-show");
			mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

			videoSurface = mediaPlayerFactory.newVideoSurface(videoSurfaceCanvas);
			mediaPlayer.setVideoSurface(videoSurface);

			mediaPlayer.addMediaPlayerEventListener(new MediaListener());

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
				mediaPlayer.stop();
				mediaPlayer.release();
				mediaPlayerFactory.release();
			}
			catch (Exception ex) {
				logger.error("shellCloseListen ", ex);
			}
			super.shellClosed(e);
		}

		public void handleEvent(Event event) {
		}
	}

	class mediaPanelListener extends ControlAdapter {

		@Override
		public void controlResized(ControlEvent e) {
			super.controlResized(e);
			Rectangle rect = mediaPanel.getClientArea();
			videoFrame.setSize(rect.width, rect.height);
		}

	}
	class MediaListener extends MediaPlayerEventAdapter {

		@Override
		public void finished(MediaPlayer mediaPlayer) {
			super.finished(mediaPlayer);
			try {
				videoFrame.setVisible(false);
			}
			catch (Exception ex) {
				logger.error(" MediaListener finished " + ex.getLocalizedMessage(), ex);
			}
		}
	}

	class FileLoadListener  extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter Menu Load");
				FileDialog dialog = new FileDialog (shell, SWT.OPEN);
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
							strGuidePath = dialog.getFilterPath() + appSettings.getFileSeparator();
							String strPage = XmlGuideReader.loadXML(strFileToLoad, guide);
							MainLogic.displayPage(strPage , false, guide, mainShell, appSettings);
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
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter Menu Restart");
				calCountDown = null;
		        lblLeft.setText("");
		        guide.getFlags().clear();
		        guide.getSettings().setPage("start");
		        guide.getSettings().setFlags(ComonFunctions.GetFlags(guide.getFlags()));
				HashMap<String, String> scriptVariables = new HashMap<String, String>();
				guide.getSettings().setScriptVariables(scriptVariables);
				guide.getSettings().saveSettings();
				MainLogic.displayPage("start", false, guide, mainShell, appSettings);
			}
			catch (Exception ex) {
				logger.error("Restart error " + ex.getLocalizedMessage(), ex);
			}
			logger.trace("Exit Menu Restart");
			super.widgetSelected(e);
		}

	}
	
	class FilePreferences  extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter FilePreferences");
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

		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				logger.trace("Enter FileGuidePreferences");
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
		@Override
		public void run() {
			try {
				//logger.trace("Enter shellTimer");
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				if (calCountDown != null) {
					if (cal.after(calCountDown)){
						calCountDown = null;
						lblLeft.setText("");
						ComonFunctions.SetFlags(guide.getDelaySet(), guide.getFlags());
						ComonFunctions.UnsetFlags(guide.getDelayUnSet(), guide.getFlags());
						MainLogic.displayPage(guide.getDelTarget(), false, guide, mainShell, appSettings);
					} else {
						
						if (guide.getDelStyle().equals("normal")) {
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
							lblLeft.setText("??:??");
						} else {
							lblLeft.setText("");
						}
						
					}
				}
				lblRight.setText(dateFormat.format(cal.getTime()));
				myDisplay.timerExec(100, this);
			}
			catch (Exception ex) {
				logger.error(" shellTimer " + ex.getLocalizedMessage(), ex);
			}
			//logger.trace("Exit shellTimer");
		}
	}

	public Calendar getCalCountDown() {
		return calCountDown;
	}

	public void setCalCountDown(Calendar calCountDown) {
		this.calCountDown = calCountDown;
	}

	public void setLblLeft(String lblLeft) {
		this.lblLeft.setText(lblLeft);
	}

	public void setLblCentre(String lblCentre) {
		this.lblCentre.setText(lblCentre);
	}

	public void setLblRight(String lblRight) {
		this.lblRight.setText(lblRight);
	}

	public void setImageLabel(String imgPath, String strImage) {
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

	public void playVideo(String video) {
		this.imageLabel.setVisible(false);
		this.videoFrame.setVisible(true);
		mediaPlayer.playMedia(video);         
	}

	public void clearImage() {
		imageLabel.setBackgroundImage(null);		
	}

	
	
	public void playAudio(String audio) {
		
	}
	
	public void setBrwsText(String brwsText) {
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
			Set<String> set = userSettings.getStringKeys(); 
			for (String s : set) {
				strTemp = strTemp.replace("#" + s + "#", userSettings.getPref(s));
			}
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
        for (Control kid : btnComp.getChildren()) {
            kid.dispose();
          }
	}
	
	public void addDelayButton(Guide guide) {
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
		btnDynamic.addSelectionListener(new DynamicButtonListner());
	}
	
	public void addButton(Button button) {
		String strBtnTarget;
		String strBtnText;
		strBtnTarget = button.getTarget();
		strBtnText = button.getText();
        com.snapps.swt.SquareButton btnDynamic = new com.snapps.swt.SquareButton(btnComp, SWT.PUSH );
        btnDynamic.setFont(controlFont);
        btnDynamic.setText(strBtnText);

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

		logger.debug("displayPage Button Text " + strBtnText + " Target " + strBtnTarget + " Set " + strButtonSet + " UnSet " + strButtonUnSet);

		btnDynamic.setData("Target", strBtnTarget);
		btnDynamic.addSelectionListener(new DynamicButtonListner());
		
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
					ComonFunctions.SetFlags(strTag, guide.getFlags());
				}
				strTag = (String) btnClicked.getData("UnSet");
				if (!strTag.equals("")) {
					ComonFunctions.UnsetFlags(strTag, guide.getFlags());
				}
				strTag = (String) btnClicked.getData("Target");
				MainLogic.displayPage(strTag, false, guide, mainShell, appSettings);
			}
			catch (Exception ex) {
				logger.error(" DynamicButtonListner " + ex.getLocalizedMessage(), ex);
			}
			logger.trace("Exit DynamicButtonListner");
		}
	}

	public void layoutButtons() {
		btnComp.layout();
	    Control[] controls = this.btnComp.getChildren();
	    if (controls.length > 0) {
	      controls[0].setFocus();
	    }
	    controls = null;
	}
}