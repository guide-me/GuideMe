package org.guideme.guideme.model;

import java.time.LocalTime;
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
	private LocalTime ifBefore; //Time of day must be before this time
	private LocalTime ifAfter; //Time of day must be after this time
	private String id;
	private String target;
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	
	public Timer(String delay, String jScript) {
		this(delay, jScript, "", "", "", "", "", "", "", "", "", "");
	}

	public Timer(String delay, String jScript, String imageId, String text, String ifSet, String ifNotSet, String set, String unSet, String ifAfter, String ifBefore, String id)
	{
		this(delay, jScript, imageId, text, ifSet, ifNotSet, set, unSet, ifAfter, ifBefore, id, "");
	}
	
	public Timer(String delay, String jScript, String imageId, String text, String ifSet, String ifNotSet, String set, String unSet, String ifAfter, String ifBefore, String id, String target) {
		this.delay = delay;
		this.jScript = jScript;
		this.imageId = imageId;
		this.text = text;
		this.ifSet = ifSet;
		this.ifNotSet  =ifNotSet;
		this.set = set;
		this.unSet = unSet;
		this.target = target;
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
		if (id.equals("")) {
			this.id = java.util.UUID.randomUUID().toString();
		} else {
			this.id = id;
		}
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

	public String getTarget() {
		return target;
	}

	public boolean canShow(ArrayList<String> setList) {
		boolean retVal = comonFunctions.canShowTime(ifBefore, ifAfter);
		if (retVal) {
			retVal =  comonFunctions.canShow(setList, ifSet, ifNotSet);
		}
		return retVal;
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

	public String getId() {
		return id;
	}

}