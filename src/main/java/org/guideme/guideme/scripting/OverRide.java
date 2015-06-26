package org.guideme.guideme.scripting;

import java.util.ArrayList;

import org.guideme.guideme.model.Audio;
import org.guideme.guideme.model.Button;
import org.guideme.guideme.model.Delay;
import org.guideme.guideme.model.Metronome;
import org.guideme.guideme.model.Timer;
import org.guideme.guideme.model.Video;


public class OverRide {
	private ArrayList<Button> button = new ArrayList<Button>();
	private ArrayList<Timer> timer = new ArrayList<Timer>();
	private Delay delay = null;
	private Video video = null;
	private Audio audio = null;
	private Metronome metronome = null;
	private String image = "";
	private String html = "";
	private String page = "";
	private String leftHtml = "";
	private String leftBody = "";
	private String rightCss = "";
	private String leftCss = "";
	
	/**
	 * Adds a button to the page
	 * 
	 * @param target Page to go to
	 * @param text Text displayed on the button
	 * @param set Flags to set if the button is pressed
	 * @param unSet Flags to clear if the button is pressed
	 * @param jScript Java Script function to run if the button is pressed
	 * @param image Background image for the button
	 */
	public void addButton(String target, String text, String set, String unSet, String jScript, String image) {
		addButton(target, text, set, unSet, jScript, image, "");
	}
	
	/**
	 * Adds a button to the page
	 * 
	 * @param target Page to go to
	 * @param text Text displayed on the button
	 * @param set Flags to set if the button is pressed
	 * @param unSet Flags to clear if the button is pressed
	 * @param jScript Java Script function to run if the button is pressed
	 * @param image Background image for the button
	 * @param hotKey Hot key assigned to the button
	 */
	public void addButton(String target, String text, String set, String unSet, String jScript, String image, String hotKey) {
		Button button = new Button(target, text, "", "", set, unSet, jScript, image, hotKey);
		this.button.add(button);
	}

	/**
	 * Adds a button to the page
	 * 
	 * @param target Page to go to
	 * @param text Text displayed on the button
	 * @param set Flags to set if the button is pressed
	 * @param unSet Flags to clear if the button is pressed
	 * @param jScript Java Script function to run if the button is pressed
	 * @param image Background image for the button
	 * @param hotKey Hot key assigned to the button
	 * @param sortOrder Sets the sort order to display the buttons
	 * @param disabled can the button be pressed
	 * @param id id String to manipulate button from javascript
	 */
	public void addButton(String target, String text, String set, String unSet, String jScript, String image, String hotKey, String sortOrder, boolean disabled, String id) {
		int order;
		try {
			order = Integer.parseInt(sortOrder);
		} catch (Exception e) {
			order = 1;
		}
		Button button = new Button(target, text, "", "", set, unSet, jScript, image, hotKey, "", "", "", "", "", order, "", "", disabled, id);
		this.button.add(button);
	}

	public Button getButton(int i) {
		return button.get(i);
	}
	
	public int buttonCount() {
		return button.size();
	}
	
	
	/**
	 * Adds a timer to change various aspects of the screen / run a javascript function
	 * 
	 * @param delay time in seconds before the timer triggers
	 * @param jScript javascript function to run when the timer triggers
	 * @param imageId image to change to when the timer triggers
	 * @param text html text to set the right html pane when the timer triggers
	 * @param set flags to set when the timer triggers
	 * @param unSet flags to clear when the timer triggers
	 */
	public void addTimer(String delay, String jScript, String imageId, String text, String set, String unSet, String id) {
		Timer timer = new Timer(delay, jScript, imageId, text, "", "", set, unSet, "", "", id);
		this.timer.add(timer);
	}

	public Timer getTimer(int i) {
		return timer.get(i);
	}
	
	public int timerCount() {
		return timer.size();
	}
		
	public void clear() {
		button = new ArrayList<Button>();
		timer = new ArrayList<Timer>();
		delay = null;
		video = null;
		audio = null;
		metronome = null;
		image = "";
		html = "";
		page = "";
		leftHtml = "";
		leftBody = "";
		rightCss = "";
	}

	public Delay getDelay() {
		return delay;
	}

	/**
	 * Add a delay / count down timer
	 * 
	 * @param target Page to go to if the delay counts down to 0
	 * @param delay Number of seconds for the delay
	 * 				Can be a range specify (n..n2) e.g. (5..10) for a random delay between 5 and 10 seconds
	 * @param startWith Don't show the true value but start with a higher one
	 * 					e.g. setting this to 50 with a 5 second delay would show a count starting at 50 but would reach 0 while displaying 45 seconds to go
	 * @param style Display style
	 * 				N Normal display the count down timer
	 * 				S Secret display ??:?? so user knows there is a timer
	 * 				H Hidden don't display anything
	 * @param set Flags to set if the delay counts down to 0
	 * @param unSet Flags to clear if delay counts down to 0
	 * @param jScript Java Script function to run if delay counts down to 0
	 */
	public void setDelay(String target, String delay, String startWith, String style, String set, String unSet, String jScript) {
		this.delay = new Delay(target, delay, "", "", startWith, style, set, unSet, jScript, "", "");
	}

	public Video getVideo() {
		return video;
	}

	/**
	 * Play a video
	 * 
	 * @param id File name for the video
	 * 				File must be in the media directory (or subdirectory)
	 * 				Wild cards can be used
	 * 				e.g. kate/home*.*  would select a video in the sub directory kate with a file name starting with home
	 * @param startAt Start time for the video hh:mm:ss
	 * 				e.g. to start 90 seconds in 00:01:30
	 * @param stopAt Stop time for video hh:mm:ss 
	 * 				e.g. to stop at 95 seconds into the video 00:01:35
	 * @param target Page to go to when the video stops
	 * @param set Flags to set when the video ends
	 * @param unSet Flags to clear when the video ends
	 * @param repeat Number of times to repeat the video
	 * @param jscript Java Script function to run when the video stops
	 */
	public void setVideo(String id, String startAt, String stopAt, String target, String set, String unSet, String repeat, String jscript) {
		this.video = new Video(id, startAt, stopAt, target, "", "", set, unSet, repeat, jscript, "", "");
	}

	public Audio getAudio() {
		return audio;
	}

	/**
	 * Play an audio file
	 * 
	 * @param id File name for the audio
	 * 				File must be in the media directory (or subdirectory)
	 * 				Wild cards can be used
	 * 				e.g. kate/home*.*  would select an audio file in the sub directory kate with a file name starting with home
	 * @param startAt Start time for the audio hh:mm:ss
	 * 				e.g. to start 90 seconds in 00:01:30
	 * @param stopAt Stop time for audio hh:mm:ss 
	 * 				e.g. to stop at 95 seconds into the video 00:01:35
	 * @param target Page to go to when the audio stops
	 * @param set Flags to set when the audio ends
	 * @param unSet Flags to clear when the audio ends
	 * @param repeat Number of times to repeat the audio
	 * @param jscript Java Script function to run when the audio stops
	 */
	public void setAudio(String id, String startAt, String stopAt, String target, String set, String unSet, String repeat, String jscript) {
		this.audio = new Audio(id, startAt, stopAt, target, "", "", set, unSet, repeat, jscript, "", "");
	}

	public Metronome getMetronome() {
		return metronome;
	}

	/**
	 * Play a metronome beat
	 *
	 * For complex beat patterns you need to set bpm, resolution and rhythm
	 * If bpm=60 and beats=4, we have 1 bar per second with 4 beats per bar, so a beat every 0.25 seconds
	 * If rhythm="1,5,9,13" we get 4 clicks once per second
	 * If rhythm="1,3,5,7,9,11,13" we get 7 clicks once every half second
	 * 
	 * @param bpm Beats per minute 60 will give one beat per second
	 * 			can also be a range (30..60) will give a random BPM between 30 and 60
	 * @param resolution Beats per bar (for a "normal" bpm this should be set to 4
	 * @param loops Number of times to loop the rhythm
	 * @param rhythm list of numbers to set the beat pattern
	 * 
	 */
	public void setMetronome(String bpm, int resolution, int loops, String rhythm) {
		this.metronome = new Metronome(bpm, "", "", resolution, loops, rhythm, "", "");
	}

	/**
	 * Play a metronome beat
	 * 
	 * @param bpm Beats per minute 60 will give one beat per second
	 * 			can also be a range (30..60) will give a random BPM between 30 and 60
	 */
	public void setMetronome(String bpm) {
		this.metronome = new Metronome(bpm, "", "", 4, 0, "", "", "");
	}
	
	public String getHtml() {
		return html;
	}

	/**
	 * Sets the html for the right hand pane
	 * 
	 * @param html Html to put in the right pane
	 */
	public void setHtml(String html) {
		this.html = html;
	}

	public String getImage() {
		return image;
	}

	/**
	 * Sets the image to be displayed in the left pane
	 * 
	 * @param id File name for the image
	 * 				File must be in the media directory (or subdirectory)
	 * 				Wild cards can be used
	 * 				e.g. kate/home*.*  would select an image in the sub directory kate with a file name starting with home
	 */
	public void setImage(String id) {
		this.image = id;
	}

	public String getPage() {
		return page;
	}

	/**
	 * Over rides the page to go to
	 * 
	 * Setting this will force a jump to this page 
	 * 
	 * @param page Page name
	 */
	public void setPage(String page) {
		this.page = page;
	}

	public String getLeftHtml() {
		return leftHtml;
	}

	/**
	 * Will set the left pane to the html provided
	 * 
	 * Instead of displaying an image the left pane will display the html
	 * 
	 * @param leftHtml Html to be displayed in the left pane
	 */
	public void setLeftHtml(String leftHtml) {
		this.leftHtml = leftHtml;
	}

	public String getRightCss() {
		return rightCss;
	}

	/**
	 * Over rides the CSS for the right pane
	 * 
	 * @param rightCss CSS to use instead of the default
	 */
	public void setRightCss(String rightCss) {
		this.rightCss = rightCss;
	}

	public String getLeftBody() {
		return leftBody;
	}

	/**
	 * Sets the content of the body node for the left pane
	 * 
	 * Rather than over ride the whole html this can be used to set just the body
	 * This will function in exactly the same way as setting the right html
	 * 
	 * @param leftBody html to replace the contents of the body node
	 * @param leftCSS
	 */
	public void setLeftBody(String leftBody, String leftCSS) {
		this.leftBody = leftBody;
		this.leftCss = leftCSS;
	}

	public String getLeftCss() {
		return leftCss;
	}

}
