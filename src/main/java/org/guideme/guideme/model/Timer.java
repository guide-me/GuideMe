package org.guideme.guideme.model;

import java.util.Calendar;

import org.guideme.guideme.settings.ComonFunctions;

public class Timer {
	private String delay;
	private String jScript;
	private Calendar timerEnd;
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	
	public Timer(String delay, String jScript) {
		this.delay = delay;
		this.jScript = jScript;
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

}