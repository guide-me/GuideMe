package org.guideme.guideme.model;

import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class Audio {
	private String id; //file name of the audio file
	private String startAt; //time to start audio at
	private String stopAt; //time to stop audio at
	private String target; //page to go to when the audio finishes
	private String set; //flags to set when the audio finishes
	private String unSet; //flags to unset when the audio finishes
	private String repeat; //number of times to repeat the audio
	private String ifSet; //only play the audio if theses flags are set
	private String ifNotSet; //don't play the audo if these flags are set
	private String jscript; //javascript function to run on audio finish
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	

	public Audio(String id, String startAt, String stopAt, String target, String ifSet, String ifNotSet, String set, String unSet, String repeat, String jscript) {
		this.id = id;
		this.startAt = startAt;
		this.stopAt = stopAt;
		this.target = target;
		this.ifSet = ifSet;
		this.ifNotSet = ifNotSet;
		this.set = set;
		this.unSet = unSet;
		this.repeat = repeat;
		this.jscript = jscript;
	}

	public String getId() {
		return id;
	}

	public String getStartAt() {
		return startAt;
	}

	public String getStopAt() {
		return stopAt;
	}

	public String getTarget() {
		return target;
	}
	
	//pass the current flags and check if we can play this audio
	public boolean canShow(ArrayList<String> setList) {
		return comonFunctions.canShow(setList, ifSet, ifNotSet);
	}

	//pass the current flags and do the set / unset on it
	public void setUnSet(ArrayList<String> setList) {
		comonFunctions.SetFlags(set, setList);
		comonFunctions.UnsetFlags(unSet, setList);
	}

	public String getRepeat() {
		return repeat;
	}

	public String getJscript() {
		return jscript;
	}

}
