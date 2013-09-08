package org.guideme.guideme.model;

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
	
	public Delay(String target, String delay, String ifSet, String ifNotSet, String startWith, String style, String set, String unSet, String jScript) {
		this.target = target;
		this.delay = delay;
		this.ifNotSet = ifNotSet;
		this.ifSet = ifSet;
		this.startWith = startWith;
		this.style = style;
		this.set = set;
		this.unSet = unSet;
		this.jScript = jScript;
	}

	public boolean canShow(ArrayList<String> setList) {
		return ComonFunctions.canShow(setList, ifSet, ifNotSet);
	}

	public int getDelaySec() {
		return ComonFunctions.getRandom(delay);
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
		ComonFunctions.SetFlags(set, setList);
		ComonFunctions.UnsetFlags(unSet, setList);
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

}