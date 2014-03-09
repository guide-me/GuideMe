package org.guideme.guideme.model;

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
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();

	public Video(String id, String startAt, String stopAt, String target, String ifSet, String ifNotSet, String set, String unSet, String repeat, String jscript)
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


}