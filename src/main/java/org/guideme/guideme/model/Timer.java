package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.Calendar;

import org.guideme.guideme.settings.ComonFunctions;

public class Timer {
	private String delay;
	private String jScript;
	private Calendar timerEnd;
	private String imageId; //file name of image
	private String text; //text to display on right
	private String ifSet;
	private String ifNotSet;
	private String set;
	private String unSet;
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	
	public Timer(String delay, String jScript) {
		this(delay, jScript, "", "", "", "", "", "");
	}

	public Timer(String delay, String jScript, String imageId, String text, String ifSet, String ifNotSet, String set, String unSet) {
		this.delay = delay;
		this.jScript = jScript;
		this.imageId = imageId;
		this.text = text;
		this.ifSet = ifSet;
		this.ifNotSet  =ifNotSet;
		this.set = set;
		this.unSet = unSet;
	}

	public int getTimerSec() {
		return comonFunctions.getRandom(delay);
	}
	

	public String getjScript() {
		return jScript;
	}

	public Calendar getTimerEnd() {
		return timerEnd;
	}

	public void setTimerEnd(Calendar timerEnd) {
		this.timerEnd = timerEnd;
	}

	public String getImageId() {
		return imageId;
	}

	public String getText() {
		return text;
	}

	public String getIfSet() {
		return ifSet;
	}

	public String getIfNotSet() {
		return ifNotSet;
	}

	public boolean canShow(ArrayList<String> setList) {
		return comonFunctions.canShow(setList, ifSet, ifNotSet);
	}

	public String getSet() {
		return set;
	}

	public String getUnSet() {
		return unSet;
	}
	
	
}