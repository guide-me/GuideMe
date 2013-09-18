package org.guideme.guideme;

import java.io.File;
import java.util.Calendar;

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
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;
import org.guideme.guideme.ui.MainShell;

public class MainLogic {
	private static Logger logger = LogManager.getLogger();
	private static MainLogic mainLogic;
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();

	private MainLogic() {
	}
	
	public static synchronized MainLogic getMainLogic() {
		if (mainLogic == null) {
			mainLogic = new MainLogic();
		}
		return mainLogic;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public void displayPage(String pageName, Boolean reDisplay, Guide guide, MainShell mainShell, AppSettings appSettings) {
		displayPage("default", pageName, reDisplay, guide, mainShell, appSettings);
	}
	
	public void displayPage(String chapterName, String pageName, Boolean reDisplay, Guide guide, MainShell mainShell, AppSettings appSettings) {
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
		String strPageName;
		String strDelStartAt;
		boolean blnVideo;
		boolean blnDelay;
		boolean blnMetronome;
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
		
		logger.debug("displayPage PagePassed " + pageName);
		logger.debug("displayPage Flags " + comonFunctions.GetFlags(guide.getFlags()));

		try {
			mainShell.stopAll();
			// handle random page
			strPageName = pageName;
			strPre = "";
			strPost = "";
			intPos1 = strPageName.indexOf("(");
			if (intPos1 > -1) {
				intPos2 = strPageName.indexOf("..", intPos1);
				if (intPos2 > -1) {
					intPos3 = strPageName.indexOf(")", intPos2);
					if (intPos3 > -1) {
						strMin = strPageName.substring(intPos1 + 1, intPos2);
						intMin = Integer.parseInt(strMin);
						strMax = strPageName.substring(intPos2 + 2, intPos3);
						intMax = Integer.parseInt(strMax);
						if (intPos1 > 0) {
							strPre = strPageName.substring(0, intPos1);
						} else {
							strPre = "";
						}
						strPost = strPageName.substring(intPos3 + 1);
						logger.debug("displayPage Random Page Min " + strMin + " Max " + strMax + " Pre " + strPre + " strPost " + strPost);
						String[] strPageArray;
						strPageArray = new String[intMax];
						int intPageArrayCount = -1;
						Page tmpPage;
						// Check if we are allowed to display the pages
						for (int i = intMin; i <= intMax; i++) {
							strPageName = strPre + i + strPost;
							if (guide.getChapters().get(chapterName).getPages().containsKey(strPageName)) {
								tmpPage = guide.getChapters().get(chapterName).getPages().get(strPageName);
								if (tmpPage.canShow(guide.getFlags())) {
									logger.debug("displayPage PageAllowed " + strPageName + " Yes");
									intPageArrayCount++;
									strPageArray[intPageArrayCount] = strPageName;
								} else {
									logger.debug("displayPage PageAllowed " + strPageName + " No");
								}
							} else {
								logger.debug("displayPage PageAllowed " + strPageName + " No");
							}
						}
						int i1 = 0;
						if (intPageArrayCount > 0) {
							// show one of the allowed random pages
							i1 = comonFunctions.getRandom("(0.." + intPageArrayCount +")");
							//i1 = rndGen.nextInt(intPageArrayCount + 1);
							logger.debug("random number between 0 and " + intPageArrayCount + " generates " + i1);
						}
						strPageName = strPageArray[i1];
						logger.debug("displayPage PageChosen " + strPageName);
					}
				}
			}

			// display page
			objCurrPage = guide.getChapters().get(chapterName).getPages().get(strPageName);
			
			// delay
			mainShell.setLblLeft("");
			blnDelay = false;
			intDelSeconds = 1;
			if (objCurrPage.getDelayCount() > 0) {
				try {
					for (int i2 = 0; i2 < objCurrPage.getDelayCount(); i2++) {
						objDelay = objCurrPage.getDelay(i2);
						if (objDelay.canShow(guide.getFlags())) {
							blnDelay = true;
							logger.debug("displayPage Delay");
							guide.setDelStyle(objDelay.getstyle());
							guide.setDelTarget(objDelay.getTarget());
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
							break;
						} else {
							mainShell.setLblLeft("");
						}
					}
				} catch (Exception e1) {
					logger.error("displayPage Delay Exception " + e1.getLocalizedMessage(), e1);
					mainShell.setLblLeft("");
				}
			}


			if (!(intDelSeconds == 0)) { 
				// Video
				blnVideo = false;
				if (objCurrPage.getVideoCount() > 0) {
					for (int i2 = 0; i2 < objCurrPage.getVideoCount(); i2++) {
						objVideo = objCurrPage.getVideo(i2);
						if (objVideo.canShow(guide.getFlags())) {
							blnVideo = true;
							strImage = objVideo.getId();
							logger.info("displayPage Video " + strImage);
							String strStartAt = objVideo.getStartAt();
							logger.info("displayPage Video Start At " + strStartAt);
							int intStartAt = 0;
							try {
								if (strStartAt != "") {
									intStartAt = comonFunctions.getMilisecFromTime(strStartAt);
								}
							} catch (Exception e1) {
								intStartAt = 0;
								logger.info("displayPage startat Exception " + e1.getLocalizedMessage());
							}

							String strStopAt = objVideo.getStopAt();
							logger.info("displayPage Video Stop At " + strStopAt);
							int intStopAt = 0;
							try {
								if (strStopAt != "") {
									intStopAt = comonFunctions.getMilisecFromTime(strStopAt);
								}
							} catch (Exception e1) {
								intStopAt = 0;
								logger.info("displayPage stopat Exception " + e1.getLocalizedMessage());
							}


							strImage = strImage.replace("\\", fileSeparator);
							logger.info("displayPage Video " + strImage);
							int intSubDir = strImage.lastIndexOf(fileSeparator);
							String strSubDir;
							if (intSubDir > -1) {
								strSubDir = strImage.substring(0, intSubDir + 1);
								if (!strSubDir.startsWith(fileSeparator)) {
									strSubDir = fileSeparator + strSubDir;
								}
								strImage = strImage.substring(intSubDir + 1);
							} else {
								strSubDir = fileSeparator;
							}
							
							// String strSubDir
							// Handle wildcard *
							imgPath = "";
							if (strImage.indexOf("*") > -1) {
								// get the directory
								File f = new File(appSettings.getDataDirectory() + guide.getMediaDirectory() + strSubDir);
								// wildcard filter class handles the filtering
								WildCardFileFilter wildCardfilter = new WildCardFileFilter();
								wildCardfilter.setFilePatern(strImage);
								if (f.isDirectory()) {
									// return a list of matching files
									File[] children = f.listFiles(wildCardfilter);
									// return a random image
									int intFile = comonFunctions.getRandom("(0.." + (children.length - 1) + ")" );
									logger.info("displayPage Random Video Index " + intFile);
									imgPath = appSettings.getDataDirectory() + guide.getMediaDirectory() + strSubDir + children[intFile].getName();
									logger.info("displayPage Random Video Chosen " + imgPath);
								}
							} else {
								// no wildcard so just use the file name
								imgPath = appSettings.getDataDirectory() + guide.getMediaDirectory() + strSubDir + strImage;
								logger.info("displayPage Non Random Video " + imgPath);
							}
							imgPath = imgPath.replace("\\", fileSeparator);

							try {
								guide.setMediaStartAt(intStartAt);
								guide.setMediaStopAt(intStopAt);
								guide.setMediaTarget(objVideo.getTarget());
								// Play video
								mainShell.playVideo("file:///" + imgPath);
							} catch (Exception e1) {
								logger.info("displayPage Video Exception " + e1.getLocalizedMessage());
							}
							break;
						}
					}
				} 

		        if (!blnVideo) {
					// image
					if (objCurrPage.getImageCount() > 0) {
						for (int i2 = 0; i2 < objCurrPage.getImageCount(); i2++) {
							objImage = objCurrPage.getImage(i2);
							if (objImage.canShow(guide.getFlags())) {
								strImage = objImage.getId();
								logger.debug("displayPage Image " + strImage);
								int intSubDir = strImage.lastIndexOf("\\");
								String strSubDir;
								if (intSubDir > -1) {
									strSubDir = strImage.substring(0, intSubDir + 1);
									strImage = strImage.substring(intSubDir + 1);
								} else {
									strSubDir = "\\";
								}
								// String strSubDir
								// Handle wildcard *
								if (strImage.indexOf("*") > -1) {
									// get the directory
									File f = new File(appSettings.getDataDirectory() + guide.getMediaDirectory() + strSubDir);
									// wildcard filter class handles the filtering
									WildCardFileFilter wildCardfilter = new WildCardFileFilter();
									wildCardfilter.setFilePatern(strImage);
									if (f.isDirectory()) {
										// return a list of matching files
										File[] children = f.listFiles(wildCardfilter);
										// return a random image
										int intFile = comonFunctions.getRandom("(0.." + (children.length - 1) + ")");
										logger.debug("displayPage Random Image Index " + intFile);
										imgPath = appSettings.getDataDirectory() + guide.getMediaDirectory() + strSubDir + children[intFile].getName();
										logger.debug("displayPage Random Image Chosen " + imgPath);
									}
								} else {
									// no wildcard so just use the file name
									imgPath = appSettings.getDataDirectory() + guide.getMediaDirectory() + strSubDir + strImage;
									logger.debug("displayPage Non Random Image " + imgPath);
								}
								File flImage = new File(imgPath);
								if (flImage.exists()){
									try {
										mainShell.setImageLabel(imgPath, strImage);
									} catch (Exception e1) {
										logger.error("displayPage Image Exception " + e1.getLocalizedMessage(), e1);
										mainShell.clearImage();
									}
								} else {
									// No image
									mainShell.clearImage();
								}
							} else {
								// No image
								mainShell.clearImage();
							}
						}
					} else {
						mainShell.clearImage();
						// No image
					}
				}


				// text
		        mainShell.setBrwsText(objCurrPage.getText());

				// buttons
				// remove old buttons
				mainShell.removeButtons();

				// add new buttons
				for (int i1 = objCurrPage.getButtonCount() - 1; i1 >= 0; i1--) {
					try {
						objButton = objCurrPage.getButton(i1);

							if (objButton.canShow(guide.getFlags())) {
								mainShell.addButton(objButton);
							}
					} catch (Exception e1) {
						logger.error("displayPage Buttons Exception " + e1.getLocalizedMessage(), e1);
					}
				}
				try {
					if (appSettings.getDebug()) {
						// add a button to trigger the delay target
						if (blnDelay) {
							mainShell.addDelayButton(guide);
						}
						mainShell.setLblCentre(" " + strPageName);
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
				if (objCurrPage.getMetronomeCount() > 0) {
					for (int i2 = 0; i2 < objCurrPage.getMetronomeCount(); i2++) {
						objMetronome = objCurrPage.getMetronome(i2);
						if (objMetronome.canShow(guide.getFlags())) {
							blnMetronome = true;
							// Metronome
							int intbpm = objMetronome.getbpm();
							logger.debug("displayPage Metronome " + intbpm + " BPM");
							try {
								mainShell.setMetronomeBPM(objMetronome.getbpm(), objMetronome.getInstrument(), objMetronome.getLoops(), objMetronome.getResolution(), objMetronome.getRhythm());
							} catch (IllegalArgumentException e) {
								logger.error("displayPage IllegalArgumentException ", e);
							} catch (IllegalStateException e) {
								logger.error("displayPage IllegalStateException ", e);
							} catch (Exception e) {
								logger.error("displayPage Exception ", e);
							}
						}
					}
				}
			
			if (!blnMetronome) {
				// Audio
				if (objCurrPage.getAudioCount() > 0) {
					for (int i2 = 0; i2 < objCurrPage.getAudioCount(); i2++) {
						objAudio = objCurrPage.getAudio(i2);
						if (objAudio.canShow(guide.getFlags())) {
							try {
								//TODO code properly
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
								

								strAudio = strAudio.replace("\\", fileSeparator);
								logger.debug("displayPage Audio " + strAudio);
								int intSubDir = strAudio.lastIndexOf(fileSeparator);
								String strSubDir;
								if (intSubDir > -1) {
									strSubDir = strAudio.substring(0, intSubDir + 1);
									if (!strSubDir.startsWith(fileSeparator)) {
										strSubDir = fileSeparator + strSubDir;
									}
									strAudio = strAudio.substring(intSubDir + 1);
								} else {
									strSubDir = fileSeparator;
								}
								// String strSubDir
								// Handle wildcard *
								if (strAudio.indexOf("*") > -1) {
									// get the directory
									File f = new File(appSettings.getDataDirectory() + guide.getMediaDirectory() + strSubDir);
									// wildcard filter class handles the filtering
									WildCardFileFilter WildCardfilter = new WildCardFileFilter();
									WildCardfilter.setFilePatern(strAudio);
									if (f.isDirectory()) {
										// return a list of matching files
										File[] children = f.listFiles(WildCardfilter);
										// return a random image
										int intFile = comonFunctions.getRandom("(0.." + (children.length - 1) + ")");
										logger.debug("displayPage Random Audio Index " + intFile);
										imgPath = appSettings.getDataDirectory() + guide.getMediaDirectory() + strSubDir + children[intFile].getName();
										logger.debug("displayPage Random Audio Chosen " + imgPath);
									}
								} else {
									// no wildcard so just use the file name
									imgPath = appSettings.getDataDirectory() + guide.getMediaDirectory() + strSubDir + strAudio;
									logger.debug("displayPage Non Random Video " + imgPath);
								}
								strAudio = imgPath;
								strAudioTarget = objAudio.getTarget();
								mainShell.playAudio(strAudio,startAtSeconds, stopAtSeconds, intAudioLoops, strAudioTarget);
								logger.debug("displayPage Audio target " + strAudioTarget);
							} catch (Exception e1) {
								logger.error("displayPage Audio Exception " + e1.getLocalizedMessage(), e1);
							}
						}
					}
				}
			}
			
			}
			// Save current page and flags
			// set page
			if (guide.getAutoSetPage()) {
				guide.getFlags().add(strPageName);
			}
			// do page set / unset
			try {
				objCurrPage.setUnSet(guide.getFlags());
			} catch (Exception e1) {
				logger.error("displayPage PageFlags Exception " + e1.getLocalizedMessage(), e1);
			}

			guide.getSettings().setPage(strPageName);
			strFlags = comonFunctions.GetFlags(guide.getFlags());
			logger.debug("displayPage End Flags " + strFlags);
			guide.getSettings().setFlags(strFlags);
			guide.getSettings().saveSettings();
			appSettings.saveSettings();
			mainShell.layoutButtons();
		} catch (Exception e) {
			logger.error("displayPage Exception ", e);
		}
	}

	// Wildecard filter
	private static class WildCardFileFilter implements java.io.FileFilter {
		private String strFilePatern;
		
		public void setFilePatern(String strFilePatern) {
			this.strFilePatern = strFilePatern;
		}

		public boolean accept(File f) {
			try {
				String strPattern = strFilePatern.toLowerCase();
				String text = f.getName().toLowerCase();
				String strFile = text;
				strPattern = strPattern.replace("*", ".*");
				if (!text.matches(strPattern)) {
					logger.debug("WildCardFileFilter accept No Match " + strFile);
					return false;
				}
				
				logger.debug("WildCardFileFilter accept Match " + strFile);
				return true;
			} catch (Exception e) {
				logger.error("WildCardFileFilter.accept Exception ", e);
				return false;
			}
		}
	}
}
