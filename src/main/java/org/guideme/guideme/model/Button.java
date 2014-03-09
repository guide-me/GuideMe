package org.guideme.guideme.model;

import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class Button
{
	private String ifSet;
	private String ifNotSet;
	private String set;
	private String unSet;
	private String text;
	private String target;
	private String jScript;
	private String image;
	private String hotKey;
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();

	public Button(String target, String text, String ifSet, String ifNotSet, String set, String unSet, String jScript, String image, String hotKey)
	{
		this.target = target;
		this.text = text;
		this.ifNotSet = ifNotSet;
		this.ifSet = ifSet;
		this.set = set;
		this.unSet = unSet;
		this.jScript = jScript;
		this.image = image;
		this.hotKey = hotKey;
	}

	public void setUnSet(ArrayList<String> setList)
	{
		comonFunctions.SetFlags(this.set, setList);
		comonFunctions.UnsetFlags(this.unSet, setList);
	}

	public String getSet() {
		return this.set;
	}

	public String getUnSet() {
		return this.unSet;
	}

	public boolean canShow(ArrayList<String> setList)
	{
		return comonFunctions.canShow(setList, this.ifSet, this.ifNotSet);
	}

	public String getText() {
		return this.text;
	}

	public String getTarget() {
		return this.target;
	}

	public String getjScript() {
		return jScript;
	}

	public String getImage() {
		return image;
	}

	public String getHotKey() {
		return hotKey;
	}

	public String getIfSet() {
		return ifSet;
	}

	public String getIfNotSet() {
		return ifNotSet;
	}

}