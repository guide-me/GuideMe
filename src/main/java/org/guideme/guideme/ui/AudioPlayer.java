package org.guideme.guideme.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.guideme.guideme.settings.AppSettings;
import org.guideme.guideme.settings.ComonFunctions;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class AudioPlayer  implements Runnable {
	//Class to play audio on a separate thread
	private static Logger logger = LogManager.getLogger();
	private AudioMediaPlayerComponent audioPlayerComponent = new AudioMediaPlayerComponent();
	private MediaListener mediaListener = new MediaListener();
	private MediaPlayer mediaPlayer;
	private Boolean isPlaying = true;
	private String audioFile = "";
	private int loops = 0;
	private int startAt = 0;
	private int stopAt = 0;
	private String target;
	private MainShell mainShell;
	private String jscript;
	private String scriptVar;
	private int volume;

	public AudioPlayer(String audioFile, int startAt, int stopAt, int loops, String target, MainShell mainShell, String jscript, String scriptVar, int volume) {
		//function to allow us to pass stuff to the new thread
		this.audioFile = audioFile;
		this.loops = loops;
		this.mainShell = mainShell;
		this.target = target;
		this.jscript = jscript;
		this.startAt = startAt;
		this.stopAt = stopAt;
		this.scriptVar = scriptVar;
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

	public void audioStop() {
		//stop the audio 
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
		}
		synchronized(this){
			isPlaying = false;
			//Thread.currentThread().interrupt();
			notifyAll();
		}
	}

	public void run() {
		try {
			//Play the audio set up by AudioPlayer
			//use a media list to play loops
			mediaPlayer = audioPlayerComponent.getMediaPlayer();
			mediaPlayer.addMediaPlayerEventListener(mediaListener);
			int mediaVolume = AppSettings.getAppSettings().getMusicVolume();
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
			if (startAt == 0 && stopAt == 0 && loops == 0) {
				mediaPlayer.playMedia(audioFile);
			} else {
				List<String> vlcArgs = new ArrayList<String>();
				if (startAt > 0) {
					vlcArgs.add("start-time=" + startAt);
				}
				if (stopAt > 0) {
					vlcArgs.add("stop-time=" + stopAt);
				}
				if (loops > 0) {
					vlcArgs.add("input-repeat=" + loops);
				}
				mediaPlayer.playMedia(audioFile, vlcArgs.toArray(new String[vlcArgs.size()]));
			}
			synchronized(this) {
				while (isPlaying) {
					// while the audio is still running carry on looping
					//Thread.sleep(5000);
					wait();
				}
			}
		} catch (Exception e) {
			logger.error("AudioPlayer run ", e);
		}
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
			mediaPlayer.removeMediaPlayerEventListener(mediaListener);
			mediaPlayer.release();
			mediaPlayer = null;
		}
		if (audioPlayerComponent != null) {
			audioPlayerComponent.release(true);
			audioPlayerComponent = null;
		}
	}


	class MediaListener extends MediaPlayerEventAdapter {

		@Override
		public void mediaStateChanged(MediaPlayer mediaPlayer, int newState) {
			super.mediaStateChanged(mediaPlayer, newState);
			logger.debug("New State " + newState);
			Display display = Display.getDefault();
			//listener to handle displaying a new page when the audio ends
			if ((newState==6) && isPlaying){
				if (!target.equals(""))  {
					//run on the main UI thread
					display.syncExec(
							new Runnable() {
								public void run(){
									mainShell.runJscript(jscript, false);
									mainShell.displayPage(target);
								}
							});											
				}
				ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
				comonFunctions.processSrciptVars(scriptVar, mainShell.getGuideSettings());
				isPlaying = false;
			}
			display = null;
		}

	}

}
