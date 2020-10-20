package org.guideme.guideme.model;

import org.guideme.guideme.settings.ComonFunctions;

import java.time.LocalTime;
import java.util.ArrayList;

public class Text
{
	private final String ifSet;
	private final String ifNotSet;
	private final LocalTime ifBefore; //Time of day must be before this time
	private final LocalTime ifAfter; //Time of day must be after this time
	private final String text;
	private final ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();

	public Text(String text)
	{
		this(text, "", "", "", "");
	}

	public Text(String text, String ifSet, String ifNotSet)
	{
		this(text, ifSet, ifNotSet, "", "");
	}

	public Text(String text, String ifSet, String ifNotSet, String ifBefore, String ifAfter)
	{
		this.text = text;
		this.ifNotSet = ifNotSet;
		this.ifSet = ifSet;
		this.ifBefore = ifBefore == null || ifBefore.isEmpty() ? null : LocalTime.parse(ifBefore);
		this.ifAfter = ifAfter == null || ifAfter.isEmpty() ? null : LocalTime.parse(ifAfter);
	}

	public boolean canShow(ArrayList<String> setList)
	{
		return comonFunctions.canShow(setList, ifSet, ifNotSet) && comonFunctions.canShowTime(ifBefore, ifAfter);
	}

	public String getText() {
		return this.text;
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

	public LocalTime getIfAfter() {
		return ifAfter;
	}
}
