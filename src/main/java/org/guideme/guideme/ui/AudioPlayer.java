package org.guideme.guideme.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class AudioPlayer  implements Runnable {
	
	private static Logger logger = LogManager.getLogger();
	private AudioMediaPlayerComponent audioPlayerComponent = new AudioMediaPlayerComponent();
	private MediaPlayer mediaPlayer;
	private Boolean isPlaying = true;
	private String audioFile = "";
	private String mediaOptions;

	public AudioPlayer(String audioFile, String mediaOptions) {
		this.audioFile = audioFile;
		this.mediaOptions = mediaOptions; 
	}

	public void audioStop() {
		//stop the audio 
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
		isPlaying = false;
	}

	public void run() {
		try {
			mediaPlayer = audioPlayerComponent.getMediaPlayer();
			mediaPlayer.addMediaPlayerEventListener(new MediaListener());
			if (mediaOptions.equals("")) {
				mediaPlayer.playMedia(audioFile);
			} else {
				mediaPlayer.playMedia(audioFile, mediaOptions);
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
				isPlaying = false;
			}
			catch (Exception ex) {
				logger.error(" MediaListener finished " + ex.getLocalizedMessage(), ex);
			}
		}
	}


}
