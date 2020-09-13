package org.guideme.guideme.model;

import java.time.LocalTime;
import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class Page {
	private String id; //Page Name
	private ArrayList<Text> text = new ArrayList<Text>(); //HTML to display
	private ArrayList<Text> leftText = new ArrayList<Text>(); //HTML to display in the left pane instead of an image
	private ArrayList<Button> button = new ArrayList<Button>();
	private ArrayList<WebcamButton> webcamButton = new ArrayList<WebcamButton>();
	private ArrayList<Delay> delay = new ArrayList<Delay>(); 
	private ArrayList<Timer> timer = new ArrayList<Timer>(); 
	private ArrayList<Video> video = new ArrayList<Video>();
	private ArrayList<Webcam> webcam = new ArrayList<Webcam>();
	private ArrayList<Image> image = new ArrayList<Image>();
	private ArrayList<LoadGuide> loadGuide = new ArrayList<LoadGuide>();
	private ArrayList<Audio> audio = new ArrayList<Audio>();
	private ArrayList<Audio> audio2 = new ArrayList<Audio>();
	private ArrayList<Metronome> metronome = new ArrayList<Metronome>();
	private String ifSet;
	private String ifNotSet;
	private LocalTime ifBefore; //Time of day must be before this time
	private LocalTime ifAfter; //Time of day must be after this time
	private String set;
	private String unSet;
	private String jScript = "";
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	
	
	public Text getLeftText(int txtIndex) {
		return leftText.get(txtIndex);
	}

	public void addLeftText(Text leftText) {
		this.leftText.add(leftText);
	}

	public int getLeftTextCount() {
		return this.leftText.size();
	}

	public Page(String id) {
		this.id = id;
	}

	public Page(String id, String ifSet, String ifNotSet, String set, String unSet, boolean autoSet, String ifAfter, String ifBefore) {
		this.id = id;
		this.ifSet = ifSet;
		this.ifNotSet = ifNotSet;
		this.set = set;
		this.unSet = unSet;
		if (ifBefore.equals("")) {
			this.ifBefore = null;
		} else {
			this.ifBefore = LocalTime.parse(ifBefore);
		}
		if (ifAfter.equals("")) {
			this.ifAfter = null;
		} else {
			this.ifAfter = LocalTime.parse(ifAfter);
		}
		
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

	public WebcamButton getWebcamButton(int butIndex) {
		return webcamButton.get(butIndex);
	}

	public void addWebcamButton(WebcamButton button) {
		this.webcamButton.add(button);
	}

	public Delay getDelay(int delIndex) {
		return delay.get(delIndex);
	}

	public void addDelay(Delay delay) {
		this.delay.add(delay);
	}

	public Timer getTimer(int timIndex) {
		return timer.get(timIndex);
	}

	public void addTimer(Timer timer) {
		this.timer.add(timer);
	}

	public Video getVideo(int vidIndex) {
		return video.get(vidIndex);
	}
	
	public void addVideo(Video video) {
		this.video.add(video);
	}

	public Webcam getWebcam(int vidIndex) {
		return webcam.get(vidIndex);
	}
	
	public void addWebcam(Webcam webcam) {
		this.webcam.add(webcam);
	}

	public Image getImage(int imgIndex) {
		return image.get(imgIndex);
	}
	
	public void addImage(Image image) {
		this.image.add(image);
	}

	public LoadGuide getLoadGuide(int guideIndex) {
		return loadGuide.get(guideIndex);
	}
	
	public void addLoadGuide(LoadGuide loadGuide) {
		this.loadGuide.add(loadGuide);
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

	public int getWebcamButtonCount() {
		return webcamButton.size();
	}

	public int getDelayCount() {
		return delay.size();
	}

	public int getTimerCount() {
		return timer.size();
	}

	public int getVideoCount() {
		return video.size();
	}

	public int getWebcamCount() {
		return webcam.size();
	}

	public int getImageCount() {
		return image.size();
	}

	public int getLoadGuideCount() {
		return loadGuide.size();
	}

	public int getMetronomeCount() {
		return metronome.size();
	}

	public boolean canShow(ArrayList<String> setList) {
		boolean retVal = comonFunctions.canShowTime(ifBefore, ifAfter);
		if (retVal) {
			String ifNotSetPage = id;
			if (ifNotSet.length() > 0)
			{
				ifNotSetPage = ifNotSetPage + "+" + ifNotSet;
			}
			retVal =  comonFunctions.canShow(setList, ifSet, ifNotSetPage);
		}
		return retVal;
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

	public Text getText(int txtIndex) {
		return text.get(txtIndex);
	}

	public void addText(Text text) {
		this.text.add(text);
	}

	public int getTextCount() {
		return this.text.size();
	}

	public String getjScript() {
		return jScript;
	}

	public void setjScript(String jScript) {
		this.jScript = jScript;
	}

	public String getIfSet() {
		return ifSet;
	}

	public String getIfNotSet() {
		return ifNotSet;
	}

	public String getSet() {
		return set;
	}

	public String getUnSet() {
		return unSet;
	}

	public LocalTime getIfBefore() {
		return ifBefore;
	}

	public void setIfBefore(String ifBefore) {
		if (ifBefore.equals("")) {
			this.ifBefore = null;
		} else {
			this.ifBefore = LocalTime.parse(ifBefore);
		}
	}

	public LocalTime getIfAfter() {
		return ifAfter;
	}

	public void setIfAfter(String ifAfter) {
		if (ifAfter.equals("")) {
			this.ifAfter = null;
		} else {
			this.ifAfter = LocalTime.parse(ifAfter);
		}
	}

	public Audio getAudio2(int audIndex) {
		return audio2.get(audIndex);
	}
	
	public void addAudio2(Audio audio) {
		this.audio2.add(audio);
	}

	public int getAudio2Count() {
		return audio2.size();
	}
}
