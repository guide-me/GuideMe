package org.guideme.guideme.model;

import java.time.LocalTime;
import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class LoadGuide {

	private String guidePath; //path to guide to load
	private String target; //page to load in new guide
	private String returnTarget; //page to return to when this guide is loaded next
	private String prejScript;
	private String postjScript;
	private String ifSet;
	private String ifNotSet;
	private LocalTime ifBefore; //Time of day must be before this time
	private LocalTime ifAfter; //Time of day must be after this time
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();

	public LoadGuide(String guidePath, String target, String returnTarget, String ifSet, String ifNotSet, String ifAfter, String ifBefore, String prejScript, String postjScript) {
		this.guidePath = guidePath;
		this.target = target;
		this.returnTarget = returnTarget;
		this.prejScript = prejScript;
		this.postjScript = postjScript;
		this.ifSet = ifSet;
		this.ifNotSet = ifNotSet;
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
	}

	public String getGuidePath() {
		return guidePath;
	}

	public String getTarget() {
		return target;
	}

	public String getReturnTarget() {
		return returnTarget;
	}

	public boolean canShow(ArrayList<String> setList) {
		boolean retVal = comonFunctions.canShowTime(ifBefore, ifAfter);
		if (retVal) {
			retVal =  comonFunctions.canShow(setList, ifSet, ifNotSet);
		}
		return retVal;
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

	public String getPrejScript() {
		return prejScript;
	}

	public String getPostjScript() {
		return postjScript;
	}


}
