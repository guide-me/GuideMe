package org.guideme.guideme.model;

import java.time.LocalTime;
import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class Delay {
	private String ifSet;
	private String ifNotSet;
	private String set;
	private String unSet;
	private String delay;
	private String target;
	private String startWith;
	private String style;
	private String jScript;
	private LocalTime ifBefore; //Time of day must be before this time
	private LocalTime ifAfter; //Time of day must be after this time
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	private String scriptVar;
	
	
	public Delay(String target, String delay, String ifSet, String ifNotSet, String startWith, String style, String set, String unSet, String jScript, String ifAfter, String ifBefore, String scriptVar) {
		this.target = target;
		this.delay = delay;
		this.ifNotSet = ifNotSet;
		this.ifSet = ifSet;
		this.startWith = startWith;
		this.style = style;
		this.set = set;
		this.unSet = unSet;
		this.jScript = jScript;
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
		this.scriptVar = scriptVar;

	}

	public boolean canShow(ArrayList<String> setList) {
		boolean retVal = comonFunctions.canShowTime(ifBefore, ifAfter);
		if (retVal) {
			retVal =  comonFunctions.canShow(setList, ifSet, ifNotSet);
		}
		return retVal;
	}

	public int getDelaySec() {
		return comonFunctions.getRandom(delay);
	}

	public String getTarget() {
		return target;
	}

	public String getStartWith() {
		return startWith;
	}

	public String getstyle() {
		return style;
	}
	
	public void setUnSet(ArrayList<String> setList) {
		//pass in the current flags and either add or remove the ones for this delay
		comonFunctions.SetFlags(set, setList);
		comonFunctions.UnsetFlags(unSet, setList);
	}

	public String getSet() {
		return set;
	}

	public String getUnSet() {
		return unSet;
	}

	public String getjScript() {
		return jScript;
	}

	public String getIfSet() {
		return ifSet;
	}

	public String getIfNotSet() {
		return ifNotSet;
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

	public String getScriptVar() {
		return scriptVar;
	}



}