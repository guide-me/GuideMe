package org.guideme.guideme;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guideme.guideme.model.Audio;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Delay;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Image;
import org.guideme.guideme.model.Metronome;
import org.guideme.guideme.model.Page;
import org.guideme.guideme.model.Video;
import org.guideme.guideme.scripting.OverRide;
import org.guideme.guideme.scripting.Jscript;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.settings.GuideSettings;
import org.guideme.guideme.settings.UserSettings;
import org.guideme.guideme.ui.DebugShell;
import org.guideme.guideme.ui.MainShell;

public class MainLogic {
	private static Logger logger = LogManager.getLogger();
	private static MainLogic mainLogic;
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	private OverRide overRide = new OverRide();
    private static Clip song; // Sound player
    private static URL songPath;

	//singleton class stuff (force there to be only one instance without making it static)
	private MainLogic() {
	}
	
	public static synchronized MainLogic getMainLogic() {
		if (mainLogic == null) {
			mainLogic = new MainLogic();
			songPath =  MainLogic.class.getClassLoader().getResource("tick.wav");
			logger.info("MainLogic getMainLogic songPath " + songPath);
			AudioInputStream audioIn;
			try {
				audioIn = AudioSystem.getAudioInputStream(songPath);
				song = AudioSystem.getClip();
				song.open(audioIn);
				FloatControl gainControl = (FloatControl) song.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-35.0f);			
	        } catch (UnsupportedAudioFileException e) {
				logger.error("audio clip Exception ", e);
			} catch (IOException e) {
				logger.error("audio clip Exception ", e);
			} catch (LineUnavailableException e) {
				logger.error("audio clip Exception ", e);
			}
		}
		return mainLogic;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	//display page without a chapter
	public void displayPage(String pageId, Boolean reDisplay, Guide guide, MainShell mainShell, AppSettings appSettings, UserSettings userSettings, GuideSettings guideSettings, DebugShell debugShell) {
		displayPage("default", pageId, reDisplay, guide, mainShell, appSettings, userSettings, guideSettings, debugShell);
	}
	
	//main display page
	//TODO currently chapters are ignored, need to implement
	public void displayPage(String chapterName, String pageId, Boolean reDisplay, Guide guide, MainShell mainShell, AppSettings appSettings, UserSettings userSettings, GuideSettings guideSettings, DebugShell debugShell) {
		// Main code that displays a page
		String strImage;
		int intDelSeconds = 0;
		int intPos1;
		int intPos2;
		int intPos3;
		int intMin;
		int intMax;
		String strMin;
		String strMax;
		String strPre;
		String strPost;
		String strPageId;
		String strDelStartAt;
		boolean blnVideo;
		boolean blnDelay;
		boolean blnMetronome;
		boolean blnImage;
		boolean blnAudio;
		String imgPath = null;
		String strFlags;
		Page objCurrPage;
		Delay objDelay;
		Image objImage;
		Button objButton;
		Video objVideo;
		Metronome objMetronome;
		Audio objAudio;
		String fileSeparator = appSettings.getFileSeparator();

		logger.debug("displayPage PagePassed " + pageId);
		logger.debug("displayPage Flags " + comonFunctions.GetFlags(guide.getFlags()));

		try {
			//Display display = Display.getDefault();
			mainShell.stopAll();
			overRide.clear();
			guideSettings.setChapter(chapterName);
			// handle random page
			strPageId = pageId;
			strPre = "";
			strPost = "";
			intPos1 = strPageId.indexOf("(");
			if (intPos1 > -1) {
				intPos2 = strPageId.indexOf("..", intPos1);
				if (intPos2 > -1) {
					intPos3 = strPageId.indexOf(")", intPos2);
					if (intPos3 > -1) {
						strMin = strPageId.substring(intPos1 + 1, intPos2);
						intMin = Integer.parseInt(strMin);
						strMax = strPageId.substring(intPos2 + 2, intPos3);
						intMax = Integer.parseInt(strMax);
						if (intPos1 > 0) {
							strPre = strPageId.substring(0, intPos1);
						} else {
							strPre = "";
						}
						strPost = strPageId.substring(intPos3 + 1);
						logger.debug("displayPage Random Page Min " + strMin + " Max " + strMax + " Pre " + strPre + " strPost " + strPost);
						String[] strPageArray;
						strPageArray = new String[(intMax - intMin) + 1];
						int intPageArrayCount = -1;
						Page tmpPage;
						// Check if we are allowed to display the pages
						for (int i = intMin; i <= intMax; i++) {
							strPageId = strPre + i + strPost;
							if (guide.getChapters().get(chapterName).getPages().containsKey(strPageId)) {
								tmpPage = guide.getChapters().get(chapterName).getPages().get(strPageId);
								if (tmpPage.canShow(guide.getFlags())) {
									logger.debug("displayPage PageAllowed " + strPageId + " Yes");
									intPageArrayCount++;
									strPageArray[intPageArrayCount] = strPageId;
								} else {
									logger.debug("displayPage PageAllowed " + strPageId + " No");
								}
							} else {
								logger.debug("displayPage PageAllowed " + strPageId + " No");
							}
						}
						int i1 = 0;
						if (intPageArrayCount > 0) {
							// show one of the allowed random pages
							i1 = comonFunctions.getRandom("(0.." + intPageArrayCount +")");
							//i1 = rndGen.nextInt(intPageArrayCount + 1);
							logger.debug("random number between 0 and " + intPageArrayCount + " generates " + i1);
						}
						strPageId = strPageArray[i1];
						logger.debug("displayPage PageChosen " + strPageId);
					}
				}
			}

			// get the page to display
			objCurrPage = guide.getChapters().get(chapterName).getPages().get(strPageId);
			debugShell.setPage(strPageId, true);
			guideSettings.setPrevPage(guideSettings.getCurrPage());
			guideSettings.setCurrPage(strPageId);
			

			//run the pageLoad script
			try {
				String pageJavascript = objCurrPage.getjScript();
				if (! pageJavascript.equals("")) {
					if (pageJavascript.contains("pageLoad")) {
						Jscript jscript = new Jscript(guide, userSettings, appSettings, guide.getInPrefGuide());
						jscript.setOverRide(overRide);
						jscript.runScript(pageJavascript, "pageLoad", true);
					}
				}
			} catch (Exception e) {
				logger.error("displayPage javascript Exception " + e.getLocalizedMessage(), e);
			}

			//PageChangeClick
			if (appSettings.isPageSound()  && guideSettings.isPageSound()) {
				song.setFramePosition(0);
				song.start();
			}

			// delay
			mainShell.setLblRight("");
			blnDelay = false;
			intDelSeconds = 1;
			// override page
			try {
				if (!overRide.getPage().equals("")) {
					intDelSeconds = 0;
					guide.setDelStyle("hidden");
					guide.setDelTarget(overRide.getPage());
					guide.setDelayjScript("");
					guide.setDelStartAtOffSet(0);
					guide.setDelaySet("");
					guide.setDelayUnSet("");
					Calendar calCountDown = Calendar.getInstance();
					calCountDown.add(Calendar.SECOND, intDelSeconds);
					mainShell.setCalCountDown(calCountDown);
				} else {
					objDelay = overRide.getDelay();
					if (objDelay != null) {
						blnDelay = true;
					} else {
						if (objCurrPage.getDelayCount() > 0) {
							for (int i2 = 0; i2 < objCurrPage.getDelayCount(); i2++) {
								objDelay = objCurrPage.getDelay(i2);
								if (objDelay.canShow(guide.getFlags())) {
									blnDelay = true;
									break;
								}
							}
						}
					}
					if (blnDelay) {
						logger.debug("displayPage Delay");
						guide.setDelStyle(objDelay.getstyle());
						guide.setDelTarget(objDelay.getTarget());
						guide.setDelayjScript(objDelay.getjScript());
						strDelStartAt = objDelay.getStartWith();
						intDelSeconds = objDelay.getDelaySec();
						try {
							guide.setDelStartAtOffSet(Integer.parseInt(strDelStartAt));
							guide.setDelStartAtOffSet(guide.getDelStartAtOffSet() - intDelSeconds);
						} catch (Exception etemp) {
							guide.setDelStartAtOffSet(0);
						}

						// record any delay set / unset
						guide.setDelaySet(objDelay.getSet());
						guide.setDelayUnSet(objDelay.getUnSet());
						logger.debug("displayPage Delay Seconds " + intDelSeconds + " Style " + guide.getDelStyle() + " Target " + guide.getDelTarget() + " Set " + guide.getDelaySet() + " UnSet " + guide.getDelayUnSet());
						Calendar calCountDown = Calendar.getInstance();
						calCountDown.add(Calendar.SECOND, intDelSeconds);
						mainShell.setCalCountDown(calCountDown);
					} else {
						mainShell.setLblLeft("");
					}
				}
			} catch (Exception e1) {
				logger.error("displayPage Delay Exception " + e1.getLocalizedMessage(), e1);
				mainShell.setLblLeft("");
			}

			if (!(intDelSeconds == 0)) { 
				if (overRide.getLeftHtml().equals("")) {
					// Video
					blnVideo = false;
					objVideo = overRide.getVideo();
					try {
						if (objVideo != null) {
							blnVideo = true;
						} else {
							if (objCurrPage.getVideoCount() > 0) {
								for (int i2 = 0; i2 < objCurrPage.getVideoCount(); i2++) {
									objVideo = objCurrPage.getVideo(i2);
									if (objVideo.canShow(guide.getFlags())) {
										blnVideo = true;
										break;
									}
								}
							} 
						}
						if (blnVideo) {
							strImage = objVideo.getId();
							logger.trace("displayPage Video " + strImage);
							String strStartAt = objVideo.getStartAt();
							logger.trace("displayPage Video Start At " + strStartAt);
							int intStartAt = 0;
							try {
								if (strStartAt != "") {
									intStartAt = comonFunctions.getMilisecFromTime(strStartAt) / 1000;
								}
							} catch (Exception e1) {
								intStartAt = 0;
								logger.trace("displayPage startat Exception " + e1.getLocalizedMessage());
							}

							String strStopAt = objVideo.getStopAt();
							logger.trace("displayPage Video Stop At " + strStopAt);
							int intStopAt = 0;
							try {
								if (strStopAt != "") {
									intStopAt = comonFunctions.getMilisecFromTime(strStopAt) / 1000;
								}
							} catch (Exception e1) {
								intStopAt = 0;
								logger.trace("displayPage stopat Exception " + e1.getLocalizedMessage());
							}

							imgPath = getMediaFullPath(strImage, fileSeparator, appSettings, guide);

							String loops = objVideo.getRepeat();
							int repeat = 0;
							try {
								repeat = Integer.parseInt(loops);
							} 
							catch (NumberFormatException nfe) {
							}
							// Play video
							mainShell.playVideo(imgPath, intStartAt, intStopAt, repeat, objVideo.getTarget(), objVideo.getJscript());
						}
					} catch (Exception e1) {
						logger.trace("displayPage Video Exception " + e1.getLocalizedMessage());
					}

					try {
						if (!blnVideo) {
							// image
							// if there is an image over ride from javascript use it
							blnImage = false;
							strImage = overRide.getImage();
							if (!strImage.equals("")) {
								imgPath = getMediaFullPath(strImage, fileSeparator, appSettings, guide);
								File flImage = new File(imgPath);
								if (flImage.exists()){
									blnImage = true;
								}
							} 
							if (!blnImage) {
								if (objCurrPage.getImageCount() > 0) {
									for (int i2 = 0; i2 < objCurrPage.getImageCount(); i2++) {
										objImage = objCurrPage.getImage(i2);
										if (objImage.canShow(guide.getFlags())) {
											strImage = objImage.getId();
											imgPath = getMediaFullPath(strImage, fileSeparator, appSettings, guide);
											File flImage = new File(imgPath);
											if (flImage.exists()){
												blnImage = true;
												break;
											}
										}
									}
								}
							}
							if (blnImage) {
								try {
									mainShell.setImageLabel(imgPath, strImage);
								} catch (Exception e1) {
									logger.error("displayPage Image Exception " + e1.getLocalizedMessage(), e1);
									mainShell.clearImage();
								}
							} else {
								mainShell.clearImage();
								// No image
							}
						} else {
							mainShell.clearImage();
							// No image
						}
					} catch (Exception e) {
						logger.error("displayPage Image Exception " + e.getLocalizedMessage(), e);
						mainShell.clearImage();
					}
				} else {
					String leftHtml = overRide.getLeftHtml();
					// Media Directory
					try {
						String mediaPath;
						mediaPath = getMediaFullPath("", fileSeparator, appSettings, guide);
						mediaPath = mediaPath.replace("\\", "/");
						leftHtml = leftHtml.replace("\\MediaDir\\", mediaPath);
					} catch (Exception e) {
						logger.error("displayPage Over ride lefthtml Media Directory Exception " + e.getLocalizedMessage(), e);
					}
					
					// Guide CSS Directory
					try {
						leftHtml = leftHtml.replace("\\GuideCSS\\", guide.getCss());
					} catch (Exception e) {
						logger.error("displayPage Over ride lefthtml Guide CSS Exception " + e.getLocalizedMessage(), e);
					}
					
					mainShell.setImageHtml(leftHtml);
				}


				// Browser text
				//Replace any string pref in the HTML with the user preference
				//they are encoded #prefName# 
				try {
					String displayText = "";
					if (overRide.getHtml().equals("")) {
						displayText = objCurrPage.getText();
					} else {
						displayText = overRide.getHtml();
						overRide.setHtml("");
					}

					// Media Directory
					try {
						String mediaPath;
						mediaPath = getMediaFullPath("", fileSeparator, appSettings, guide);
						displayText = displayText.replace("\\MediaDir\\", mediaPath);
					} catch (Exception e) {
						logger.error("displayPage BrwsText Media Directory Exception " + e.getLocalizedMessage(), e);
					}
					
					displayText = substituteTextVars(displayText, guideSettings, userSettings);
					
//					// Script Variables
//					Set<String> set = guideSettings.getScriptVariables().keySet();
//					String varValue;
//					for (String s :set) {
//						try {
//							Object objVar =guideSettings.getScriptVariables().get(s);
//							varValue = comonFunctions.getVarAsString(objVar);
//							if (!varValue.equals("")) {
//								displayText = displayText.replace("<span>" + s + "</span>", varValue);
//							}
//						} catch (Exception e) {
//							logger.error("displayPage BrwsText ScriptVariables Exception " + s + " " + e.getLocalizedMessage(), e);
//						}
//					}
//
//					// String Guide Preferences
//					set = guideSettings.getStringKeys(); 
//					for (String s : set) {
//						try {
//							displayText = displayText.replace("<span>" + s + "</span>", guideSettings.getPref(s));
//						} catch (Exception e) {
//							logger.error("displayPage BrwsText String Guide Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
//						}
//					}
//
//					// Number Guide Preferences
//					set = guideSettings.getNumberKeys(); 
//					String numberRet = "";
//					for (String s : set) {
//						try {
//						numberRet = FormatNumPref(guideSettings.getPrefNumber(s));
//							displayText = displayText.replace("<span>" + s + "</span>", numberRet);
//						} catch (Exception e) {
//							logger.error("displayPage BrwsText Number Guide Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
//						}
//					}
//
//					// String User Preferences
//					set = userSettings.getStringKeys(); 
//					for (String s : set) {
//						try {
//							displayText = displayText.replace("<span>" + s + "</span>", userSettings.getPref(s));
//						} catch (Exception e) {
//							logger.error("displayPage BrwsText String User Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
//						}
//					}
//
//					// Number User Preferences
//					set = userSettings.getNumberKeys(); 
//					for (String s : set) {
//						try {
//						displayText = displayText.replace("<span>" + s + "</span>", FormatNumPref(userSettings.getPrefNumber(s)));
//						} catch (Exception e) {
//							logger.error("displayPage BrwsText Number User Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
//						}
//					}

					mainShell.setBrwsText(displayText, overRide.getRightCss());
				} catch (Exception e) {
					logger.error("displayPage BrwsText Exception " + e.getLocalizedMessage(), e);
					mainShell.setBrwsText("", "");
				}

				// overRide
				// remove old buttons
				mainShell.removeButtons();

				// add new buttons
				for (int i1 = objCurrPage.getButtonCount() - 1; i1 >= 0; i1--) {
					try {
						objButton = objCurrPage.getButton(i1);
						if (objButton.canShow(guide.getFlags())) {
							String javascriptid = objButton.getjScript();
							String btnText = objButton.getText();
							btnText = substituteTextVars(btnText, guideSettings, userSettings);
							objButton.setText(btnText);
							mainShell.addButton(objButton, javascriptid);
						}
					} catch (Exception e1) {
						logger.error("displayPage Button Exception " + e1.getLocalizedMessage(), e1);
					}
				}

				//add any buttons added by javascript
				for (int i1 = overRide.buttonCount() - 1; i1 >= 0; i1--) {
					try {
						objButton = overRide.getButton(i1);
						if (objButton.canShow(guide.getFlags())) {
							String javascriptid = objButton.getjScript();
							String btnText = objButton.getText();
							btnText = substituteTextVars(btnText, guideSettings, userSettings);
							objButton.setText(btnText);
							mainShell.addButton(objButton, javascriptid);
						}
					} catch (Exception e1) {
						logger.error("displayPage OverRide Exception " + e1.getLocalizedMessage(), e1);
					}
				}

				try {
					if (appSettings.getDebug()) {
						// add a button to trigger the delay target if debug is set by the user
						if (blnDelay) {
							mainShell.addDelayButton(guide);
						}
						mainShell.setLblCentre(" " + strPageId);
					} else {
						mainShell.setLblCentre(guide.getTitle());
					}
				} catch (Exception e1) {
					logger.error("displayPage Debug Exception " + e1.getLocalizedMessage(), e1);
				}

			}

			if (!reDisplay) {
				// Audio / Metronome
				blnMetronome = false;
				try {
					objMetronome = overRide.getMetronome();
					if (objMetronome != null) {
						blnMetronome = true;
					} else {
						if (objCurrPage.getMetronomeCount() > 0) {
							for (int i2 = 0; i2 < objCurrPage.getMetronomeCount(); i2++) {
								objMetronome = objCurrPage.getMetronome(i2);
								if (objMetronome.canShow(guide.getFlags())) {
									blnMetronome = true;
									break;
								}
							}
						}
					}
					if (blnMetronome) {
						// Metronome
						int intbpm = objMetronome.getbpm();
						logger.debug("displayPage Metronome " + intbpm + " BPM");
						try {
							mainShell.setMetronomeBPM(objMetronome.getbpm(), objMetronome.getLoops(), objMetronome.getResolution(), objMetronome.getRhythm());
						} catch (IllegalArgumentException e) {
							logger.error("displayPage Metronome IllegalArgumentException ", e);
						} catch (IllegalStateException e) {
							logger.error("displayPage Metronome IllegalStateException ", e);
						} catch (Exception e) {
							logger.error("displayPage Metronome Exception ", e);
						}
					}
				} catch (Exception e) {
					logger.error("displayPage Metronome Exception ", e);
				}

				if (!blnMetronome) {
					// Audio
					blnAudio = false;
					objAudio = overRide.getAudio();
					try {
						if (objAudio != null) {
							blnAudio = true;
						} else {
							if (objCurrPage.getAudioCount() > 0) {
								for (int i2 = 0; i2 < objCurrPage.getAudioCount(); i2++) {
									objAudio = objCurrPage.getAudio(i2);
									if (objAudio.canShow(guide.getFlags())) {
										blnAudio = true;
										break;
									}
								}
							}
						}
						if (blnAudio) {
							int intAudioLoops;
							String strAudio;
							String strAudioTarget;
							String strIntAudio = objAudio.getRepeat();
							if (strIntAudio.equals("")) {
								intAudioLoops = 0;
							} else {
								intAudioLoops = Integer.parseInt(strIntAudio);
							}
							strAudio = objAudio.getId();
							logger.debug("displayPage Audio " + strAudio);
							String strStartAt = objAudio.getStartAt();
							int startAtSeconds;
							if (!strStartAt.equals("")) {
								startAtSeconds = comonFunctions.getMilisecFromTime(strStartAt) / 1000;
							} else {
								startAtSeconds = 0;
							}
							String strStopAt = objAudio.getStopAt();
							int stopAtSeconds;
							if (!strStopAt.equals("")) {
								stopAtSeconds = comonFunctions.getMilisecFromTime(strStopAt) / 1000;
							} else {
								stopAtSeconds = 0;
							}

							imgPath = getMediaFullPath(strAudio, fileSeparator, appSettings, guide);
							strAudioTarget = objAudio.getTarget();
							mainShell.playAudio(imgPath,startAtSeconds, stopAtSeconds, intAudioLoops, strAudioTarget, objAudio.getJscript());
							logger.debug("displayPage Audio target " + strAudioTarget);
						}
					} catch (Exception e) {
						logger.error("displayPage Audio Exception " + e.getLocalizedMessage(), e);
					}
				}

			}

			// Save current page and flags
			// set page
			if (guide.getAutoSetPage()) {
				comonFunctions.SetFlags(strPageId, guide.getFlags());
				//guide.getFlags().add(strPageId);//TODO
			}

			// do page set / unset
			try {
				objCurrPage.setUnSet(guide.getFlags());
			} catch (Exception e1) {
				logger.error("displayPage PageFlags Exception " + e1.getLocalizedMessage(), e1);
			}

			guide.getSettings().setPage(strPageId);
			strFlags = comonFunctions.GetFlags(guide.getFlags());
			logger.debug("displayPage End Flags " + strFlags);
			guide.getSettings().setFlags(strFlags);
			guide.getSettings().saveSettings();
			appSettings.saveSettings();
			guide.getSettings().formFieldsReset();
			mainShell.layoutButtons();
		} catch (Exception e) {
			logger.error("displayPage Exception ", e);
		}
	}

	
	private String substituteTextVars(String inString, GuideSettings guideSettings, UserSettings userSettings) {
		String retString = inString;
		// Script Variables
		Set<String> set = guideSettings.getScriptVariables().keySet();
		String varValue;
		for (String s :set) {
			try {
				Object objVar =guideSettings.getScriptVariables().get(s);
				varValue = comonFunctions.getVarAsString(objVar);
				if (!varValue.equals("")) {
					retString = retString.replace("<span>" + s + "</span>", varValue);
				}
			} catch (Exception e) {
				logger.error("displayPage BrwsText ScriptVariables Exception " + s + " " + e.getLocalizedMessage(), e);
			}
		}

		// String Guide Preferences
		set = guideSettings.getStringKeys(); 
		for (String s : set) {
			try {
				retString = retString.replace("<span>" + s + "</span>", guideSettings.getPref(s));
			} catch (Exception e) {
				logger.error("displayPage BrwsText String Guide Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
			}
		}

		// Number Guide Preferences
		set = guideSettings.getNumberKeys(); 
		String numberRet = "";
		for (String s : set) {
			try {
			numberRet = FormatNumPref(guideSettings.getPrefNumber(s));
			retString = retString.replace("<span>" + s + "</span>", numberRet);
			} catch (Exception e) {
				logger.error("displayPage BrwsText Number Guide Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
			}
		}

		// String User Preferences
		set = userSettings.getStringKeys(); 
		for (String s : set) {
			try {
				retString = retString.replace("<span>" + s + "</span>", userSettings.getPref(s));
			} catch (Exception e) {
				logger.error("displayPage BrwsText String User Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
			}
		}

		// Number User Preferences
		set = userSettings.getNumberKeys(); 
		for (String s : set) {
			try {
				retString = retString.replace("<span>" + s + "</span>", FormatNumPref(userSettings.getPrefNumber(s)));
			} catch (Exception e) {
				logger.error("displayPage BrwsText Number User Preferences Exception " + s + " " + e.getLocalizedMessage(), e);
			}
		}
		return retString;
	}
	
	private String FormatNumPref(double prefNumber)
	{
	    if(prefNumber == (int) prefNumber)
	        return String.format("%d",(int) prefNumber);
	    else
	        return String.format("%s",prefNumber);
	}	
	
	public String getMediaFullPath(String mediaFile, String fileSeparator, AppSettings appSettings, Guide guide) {
		String mediaFound = "";
		String dataDirectory;
		String prefix = "";
		if (guide.getInPrefGuide()) {
			dataDirectory = appSettings.getUserDir();
		} else { 
			dataDirectory = appSettings.getDataDirectory();
			if (dataDirectory.startsWith("/")) {
				prefix = "/";
			}
			dataDirectory = prefix + comonFunctions.fixSeparator(appSettings.getDataDirectory(), fileSeparator);
		}
		String mediaDirectory = comonFunctions.fixSeparator(guide.getMediaDirectory(), fileSeparator);
		dataDirectory = dataDirectory + fileSeparator + mediaDirectory;
		
		
		String media = comonFunctions.fixSeparator(mediaFile, fileSeparator);
		logger.debug("displayPage getMediaFullPath " + media);
		int intSubDir = media.lastIndexOf(fileSeparator);
		String strSubDir;
		if (intSubDir > -1) {
			strSubDir = comonFunctions.fixSeparator(media.substring(0, intSubDir + 1), fileSeparator);
			media = media.substring(intSubDir + 1);
		} else {
			strSubDir = "";
		}
		// String strSubDir
		// Handle wildcard *
		if (media.indexOf("*") > -1) {
			mediaFound = comonFunctions.GetRandomFile(media, strSubDir, true);
		} else {
			// no wildcard so just use the file name
			if (strSubDir.equals("")) {
				mediaFound = dataDirectory + fileSeparator + media;
			} else {
				mediaFound = dataDirectory + fileSeparator + strSubDir + fileSeparator + media;
			}
			logger.debug("displayPage Non Random Media " + mediaFound);
		}
		
		return mediaFound;
	}
	
}
