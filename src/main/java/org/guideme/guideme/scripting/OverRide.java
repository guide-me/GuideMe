package org.guideme.guideme.scripting;

import java.util.ArrayList;

import org.guideme.guideme.model.Audio;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Delay;
import org.guideme.guideme.model.Metronome;
import org.guideme.guideme.model.Video;

public class OverRide {
	private ArrayList<Button> button = new ArrayList<Button>();
	private Delay delay = null;
	private Video video = null;
	private Audio audio = null;
	private Metronome metronome = null;
	private String image = "";
	private String html = "";
	private String page = "";
	
	public void addButton(String target, String text, String set, String unSet, String jScript, String image) {
		Button button = new Button(target, text, "", "", set, unSet, jScript, image);
		this.button.add(button);
	}

	public Button getButton(int i) {
		return button.get(i);
	}
	
	public int buttonCount() {
		return button.size();
	}
	
	public void clear() {
		button = new ArrayList<Button>();
		delay = null;
		video = null;
		audio = null;
		metronome = null;
		image = "";
		html = "";
		page = "";
	}

	public Delay getDelay() {
		return delay;
	}

	public void setDelay(String target, String delay, String startWith, String style, String set, String unSet, String jScript) {
		this.delay = new Delay(target, delay, "", "", startWith, style, set, unSet, jScript);
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(String id, String startAt, String stopAt, String target, String set, String unSet, String repeat, String jscript) {
		this.video = new Video(id, startAt, stopAt, target, "", "", set, unSet, repeat, jscript);
	}

	public Audio getAudio() {
		return audio;
	}

	public void setAudio(String id, String startAt, String stopAt, String target, String set, String unSet, String repeat, String jscript) {
		this.audio = new Audio(id, startAt, stopAt, target, "", "", set, unSet, repeat, jscript);
	}

	public Metronome getMetronome() {
		return metronome;
	}

	public void setMetronome(String bpm, int resolution, int loops, String rhythm) {
		this.metronome = new Metronome(bpm, "", "", resolution, loops, rhythm);
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String id) {
		this.image = id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

}
