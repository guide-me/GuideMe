package org.guideme.guideme.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import uk.co.caprica.vlcj.component.AudioMediaListPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;

public class AudioPlayer  implements Runnable {
	//Class to play audio on a separate thread
	private static Logger logger = LogManager.getLogger();
	private AudioMediaListPlayerComponent audioPlayer = new AudioMediaListPlayerComponent();
	private MediaListPlayer mediaListPlayer;
	private MediaList mediaList;
	private Boolean isPlaying = true;
	private String audioFile = "";
	private String mediaOptions;
	private int loops = 0;
	private int loopCount = 0;
	private String target;
	private MainShell mainShell;
	private String jscript;
	private Display display;

	public AudioPlayer(String audioFile, String mediaOptions, int loops, String target, MainShell mainShell, String jscript, Display display) {
		//function to allow us to pass stuff to the new thread
		this.audioFile = audioFile;
		this.mediaOptions = mediaOptions; 
		this.loops = loops;
		this.mainShell = mainShell;
		this.target = target;
		this.jscript = jscript;
		this.display = display;
	}

	public void audioStop() {
		//stop the audio 
		if (mediaListPlayer != null) {
			if (mediaListPlayer.isPlaying()) {
				mediaListPlayer.stop();
			}
			isPlaying = false;
		}
	}

	public void run() {
		try {
			//Play the audio set up by AudioPlayer
			//use a media list to play loops
			mediaListPlayer = audioPlayer.getMediaListPlayer();
			mediaList = mediaListPlayer.getMediaList();
			mediaListPlayer.addMediaListPlayerEventListener(new MediaListListener());
			String[] options = mediaOptions.split(",");
			for (int i=0; i <= loops; i++) {
				if (mediaOptions.equals("")) {
					mediaList.addMedia(audioFile);
				} else {
					mediaList.addMedia(audioFile, options);
				}
			}
			loopCount = loops;
			mediaListPlayer.play();
			while (isPlaying) {
				// while the audio is still running carry on looping
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			logger.error("AudioPlayer run ", e);
		}
		if (mediaListPlayer != null) {
			if (mediaListPlayer.isPlaying()) {
				mediaListPlayer.stop();
			}
			mediaList.release();
			mediaListPlayer.release();
			mediaListPlayer = null;
		}
		if (audioPlayer != null) {
			audioPlayer.release(true);
			audioPlayer = null;
		}
	}
	
	
	class MediaListListener extends MediaListPlayerEventAdapter {

		@Override
		public void mediaStateChanged(MediaListPlayer mediaListPlayer,
				int newState) {
			super.mediaStateChanged(mediaListPlayer, newState);
			logger.debug("New State " + newState);
			logger.debug("loopCount " + loopCount);
			//listener to handle displaying a new page when the audio ends
			//only do it if we have done all the loops
			if (newState == 6) {
				if (loopCount == 0){
					isPlaying = false;
					if (!target.equals("")) {
						//run on the main UI thread
						display.syncExec(
								new Runnable() {
									public void run(){
										mainShell.runJscript(jscript);
										mainShell.displayPage(target);
									}
								});											}
					logger.debug("isPlaying " + isPlaying);
				} else {
					loopCount--;
				}
			}
			
			
		}

	}
	
}
