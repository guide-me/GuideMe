package org.guideme.guideme.model;

import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class Page {
	private String text; //HTML to display
	private String pageName; //Page Name
	private ArrayList<Button> button;
	private ArrayList<Delay> delay; 
	private ArrayList<Video> video;
	private ArrayList<Image> image;
	private ArrayList<Audio> audio;
	private ArrayList<Metronome> metronome;
	private int buttonCount;
	private int delayCount;
	private int videoCount;
	private int imageCount;
	private int audioCount;
	private int metronomeCount;
	private String ifSet;
	private String ifNotSet;
	private String set;
	private String unSet;
	
	public Page(String pageName, String ifSet, String ifNotSet, String set, String unSet, boolean autoSet) {
		this.pageName = pageName;
		button = new ArrayList<Button>();
		buttonCount = 0;
		delay = new ArrayList<Delay>();
		delayCount = 0;
		video = new ArrayList<Video>();
		videoCount = 0;
		image = new ArrayList<Image>();
		imageCount = 0;
		audio = new ArrayList<Audio>();
		audioCount = 0;
		metronome = new ArrayList<Metronome>();
		metronomeCount = 0;
		this.ifSet = ifSet;
		this.ifNotSet = ifNotSet;
		this.set = set;
		this.unSet = unSet;
		
		if (autoSet) {
			if (this.set.length() == 0) {
				this.set = pageName;
			} else {
				this.set = this.set + "," + pageName;
			}
		}
	}

	public Button getButton(int butIndex) {
		return button.get(butIndex);
	}

	public void addButton(Button button) {
		this.button.add(button);
		buttonCount++;
	}

	public Delay getDelay(int delIndex) {
		return delay.get(delIndex);
	}

	public void addDelay(Delay delay) {
		this.delay.add(delay);
		delayCount++;
	}

	public Video getVideo(int vidIndex) {
		return video.get(vidIndex);
	}
	
	public void addVideo(Video video) {
		this.video.add(video);
		videoCount++;
	}

	public Image getImage(int imgIndex) {
		return image.get(imgIndex);
	}
	
	public void addImage(Image image) {
		this.image.add(image);
		imageCount++;
	}

	public Audio getAudio(int audIndex) {
		return audio.get(audIndex);
	}
	
	public void addAudio(Audio audio) {
		this.audio.add(audio);
		audioCount++;
	}

	public Metronome getMetronome(int metIndex) {
		return metronome.get(metIndex);
	}
	
	public void addMetronome(Metronome metronome) {
		this.metronome.add(metronome);
		metronomeCount++;
	}

	public String getPageName() {
		return pageName;
	}

	public int getButtonCount() {
		return buttonCount;
	}

	public int getDelayCount() {
		return delayCount;
	}

	public int getVideoCount() {
		return videoCount;
	}

	public int getImageCount() {
		return imageCount;
	}

	public int getMetronomeCount() {
		return metronomeCount;
	}

	public boolean canShow(ArrayList<String> setList) {
		return ComonFunctions.canShow(setList, ifSet, ifNotSet, pageName);
	}

	public void setUnSet(ArrayList<String> setList) {
		ComonFunctions.SetFlags(set, setList);
		ComonFunctions.UnsetFlags(unSet, setList);
	}

	@Override
	public String toString() {
		return "page [Page Name=" + pageName + "]";
	}

	public int getAudioCount() {
		return audioCount;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
