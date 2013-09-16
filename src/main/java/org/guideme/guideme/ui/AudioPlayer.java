package org.guideme.guideme.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.caprica.vlcj.component.AudioMediaListPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
//import uk.co.caprica.vlcj.player.MediaPlayer;
//import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;

public class AudioPlayer  implements Runnable {
	
	private static Logger logger = LogManager.getLogger();
	private AudioMediaListPlayerComponent audioPlayer = new AudioMediaListPlayerComponent();
	//private MediaPlayer mediaPlayer;
	private MediaListPlayer mediaListPlayer;
	private MediaList mediaList;
	private Boolean isPlaying = true;
	private String audioFile = "";
	private String mediaOptions;
	private int loops = 0;
	private int loopCount = 0;

	public AudioPlayer(String audioFile, String mediaOptions, int loops) {
		this.audioFile = audioFile;
		this.mediaOptions = mediaOptions; 
		this.loops = loops;
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
			//mediaPlayer = audioPlayer.getMediaPlayer();
			mediaListPlayer = audioPlayer.getMediaListPlayer();
			//mediaPlayer.addMediaPlayerEventListener(new MediaListener());
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
			if (newState == 6) {
				if (loopCount == 0){
					isPlaying = false;
					logger.debug("isPlaying " + isPlaying);
				} else {
					loopCount--;
				}
			}
			
			
		}

	}
	
	/*
	class MediaListener extends MediaPlayerEventAdapter {

		@Override
		public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
			super.timeChanged(mediaPlayer, newTime);
		}
		
		@Override
		public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
			super.positionChanged(mediaPlayer, newPosition);
		}
		
		@Override
		public void finished(MediaPlayer mediaPlayer) {
			super.finished(mediaPlayer);
			try {
				//isPlaying = false;
			}
			catch (Exception ex) {
				logger.error(" MediaListener finished " + ex.getLocalizedMessage(), ex);
			}
		}
	}
	*/


}
