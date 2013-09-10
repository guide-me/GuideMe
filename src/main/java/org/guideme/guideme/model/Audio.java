package org.guideme.guideme.model;

import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class Audio {
	private String Iid; //file name of the audio file
	private String IStartAt; //time to start audio at
	private String IStopAt; //time to stop audio at
	private String ITarget; //page to go to when the audio finishes
	private String ISet; //flags to set when the audio finishes
	private String IUnSet; //flags to unset when the audio finishes
	private String IRepeat; //number of times to repeat the audio
	private String IifSet; //only play the audio if theses flags are set
	private String IifNotSet; //don't play the audo if these flags are set
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();
	

	public Audio(String iid, String istartAt, String istopAt, String itarget, String iifSet, String iifNotSet, String Set, String UnSet, String Repeat) {
		Iid = iid;
		IStartAt = istartAt;
		IStopAt = istopAt;
		ITarget = itarget;
		IifSet = iifSet;
		IifNotSet = iifNotSet;
		ISet = Set;
		IUnSet = UnSet;
		IRepeat = Repeat;
	}

	public String getIid() {
		return Iid;
	}

	public String getIstartAt() {
		return IStartAt;
	}

	public String getIstopAt() {
		return IStopAt;
	}

	public String getTarget() {
		return ITarget;
	}
	
	//pass the current flags and check if we can play this audio
	public boolean canShow(ArrayList<String> setList) {
		return comonFunctions.canShow(setList, IifSet, IifNotSet);
	}

	//pass the current flags and do the set / unset on it
	public void setUnSet(ArrayList<String> setList) {
		comonFunctions.SetFlags(ISet, setList);
		comonFunctions.UnsetFlags(IUnSet, setList);
	}

	public String getRepeat() {
		return IRepeat;
	}

}
