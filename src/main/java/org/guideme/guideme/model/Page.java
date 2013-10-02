package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.guideme.guideme.settings.ComonFunctions;

public class Page {
	private String text = ""; //HTML to display
	private String id; //Page Name
	private ArrayList<Button> button = new ArrayList<Button>();
	private ArrayList<Delay> delay = new ArrayList<Delay>(); 
	private ArrayList<Video> video = new ArrayList<Video>();
	private ArrayList<Image> image = new ArrayList<Image>();
	private ArrayList<Audio> audio = new ArrayList<Audio>();
	private ArrayList<Metronome> metronome = new ArrayList<Metronome>();
	private String ifSet;
	private String ifNotSet;
	private String set;
	private String unSet;
	private String jScript = "";
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	
	
	public Page(String id) {
		this.id = id;
	}

	public Page(String id, String ifSet, String ifNotSet, String set, String unSet, boolean autoSet) {
		this.id = id;
		this.ifSet = ifSet;
		this.ifNotSet = ifNotSet;
		this.set = set;
		this.unSet = unSet;
		
		if (autoSet) {
			if (this.set.length() == 0) {
				this.set = id;
			} else {
				this.set = this.set + "," + id;
			}
		}
	}


	public Button getButton(int butIndex) {
		return button.get(butIndex);
	}

	public void addButton(Button button) {
		this.button.add(button);
	}

	public Delay getDelay(int delIndex) {
		return delay.get(delIndex);
	}

	public void addDelay(Delay delay) {
		this.delay.add(delay);
	}

	public Video getVideo(int vidIndex) {
		return video.get(vidIndex);
	}
	
	public void addVideo(Video video) {
		this.video.add(video);
	}

	public Image getImage(int imgIndex) {
		return image.get(imgIndex);
	}
	
	public void addImage(Image image) {
		this.image.add(image);
	}

	public Audio getAudio(int audIndex) {
		return audio.get(audIndex);
	}
	
	public void addAudio(Audio audio) {
		this.audio.add(audio);
	}

	public Metronome getMetronome(int metIndex) {
		return metronome.get(metIndex);
	}
	
	public void addMetronome(Metronome metronome) {
		this.metronome.add(metronome);
	}

	public String getId() {
		return id;
	}

	public int getButtonCount() {
		return button.size();
	}

	public int getDelayCount() {
		return delay.size();
	}

	public int getVideoCount() {
		return video.size();
	}

	public int getImageCount() {
		return image.size();
	}

	public int getMetronomeCount() {
		return metronome.size();
	}

	public boolean canShow(ArrayList<String> setList) {
		return comonFunctions.canShow(setList, ifSet, ifNotSet, id);
	}

	public void setUnSet(ArrayList<String> setList) {
		comonFunctions.SetFlags(set, setList);
		comonFunctions.UnsetFlags(unSet, setList);
	}

	@Override
	public String toString() {
		return "page [Page Name=" + id + "]";
	}

	public int getAudioCount() {
		return audio.size();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getjScript() {
		return jScript;
	}

	public void setjScript(String jScript) {
		this.jScript = jScript;
	}

}
