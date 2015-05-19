package org.guideme.guideme.model;

import java.time.LocalTime;
import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class Video
{
	private String id;
	private String startAt;
	private String stopAt;
	private String target;
	private String ifSet;
	private String ifNotSet;
	private String set;
	private String unSet;
	private String repeat;
	private String jscript;
	private LocalTime ifBefore; //Time of day must be before this time
	private LocalTime ifAfter; //Time of day must be after this time
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();

	public Video(String id, String startAt, String stopAt, String target, String ifSet, String ifNotSet, String set, String unSet, String repeat, String jscript, String ifAfter, String ifBefore)
	{
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

	public String getId() {
		return this.id;
	}

	public String getStartAt() {
		return this.startAt;
	}

	public String getStopAt() {
		return this.stopAt;
	}

	public String getTarget() {
		return this.target;
	}

	public boolean canShow(ArrayList<String> setList) {
		return comonFunctions.canShow(setList, this.ifSet, this.ifNotSet);
	}

	public void setUnSet(ArrayList<String> setList) {
		comonFunctions.SetFlags(this.set, setList);
		comonFunctions.UnsetFlags(this.unSet, setList);
	}

	public String getRepeat() {
		return this.repeat;
	}

	public String getJscript() {
		return jscript;
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

}