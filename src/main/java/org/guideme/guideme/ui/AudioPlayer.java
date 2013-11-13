package org.guideme.guideme.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.guideme.guideme.settings.AppSettings;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class AudioPlayer  implements Runnable {
	//Class to play audio on a separate thread
	private static Logger logger = LogManager.getLogger();
	private AudioMediaPlayerComponent audioPlayerComponent = new AudioMediaPlayerComponent();
	private MediaPlayer mediaPlayer;
	private Boolean isPlaying = true;
	private String audioFile = "";
	private int loops = 0;
	private int startAt = 0;
	private int stopAt = 0;
	private String target;
	private MainShell mainShell;
	private String jscript;

	public AudioPlayer(String audioFile, int startAt, int stopAt, int loops, String target, MainShell mainShell, String jscript) {
		//function to allow us to pass stuff to the new thread
		this.audioFile = audioFile;
		this.loops = loops;
		this.mainShell = mainShell;
		this.target = target;
		this.jscript = jscript;
		this.startAt = startAt;
		this.stopAt = stopAt;
	}

	public void audioStop() {
		//stop the audio 
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
			isPlaying = false;
		}
	}

	public void run() {
		try {
			//Play the audio set up by AudioPlayer
			//use a media list to play loops
			mediaPlayer = audioPlayerComponent.getMediaPlayer();
			mediaPlayer.addMediaPlayerEventListener(new MediaListener());
			mediaPlayer.setVolume(AppSettings.getAppSettings().getMusicVolume());
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
			while (isPlaying) {
				// while the audio is still running carry on looping
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			logger.error("AudioPlayer run ", e);
		}
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
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
									mainShell.runJscript(jscript);
									mainShell.displayPage(target);
								}
							});											
				}
		}
		}

	}
	
}
