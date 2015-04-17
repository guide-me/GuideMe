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
	private String fontName;
	private String fontHeight;
	private org.eclipse.swt.graphics.Color bgColor1;
	private org.eclipse.swt.graphics.Color bgColor2;
	private org.eclipse.swt.graphics.Color fontColor;
	private ComonFunctions comonFunctions = ComonFunctions.getComonFunctions();

	public Button(String target, String text, String ifSet, String ifNotSet, String set, String unSet, String jScript, String image, String hotKey)
	{
		this(target, text, ifSet, ifNotSet, set, unSet, jScript, image, hotKey, "", "", "", "", "");
	}

	
	public Button(String target, String text, String ifSet, String ifNotSet, String set, String unSet, String jScript, String image, String hotKey, String fontName, String fontHeight, String fontColor, String bgColor1, String bgColor2)
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
		this.fontName = fontName;
		this.fontHeight = fontHeight;
		if (bgColor1 == "") {
			this.bgColor1 = comonFunctions.getColor("white");
		} else {
			this.bgColor1 = comonFunctions.getColor(bgColor1);
		}
		if (bgColor2 == "") {
			this.bgColor2 = this.bgColor1;
		} else {
			this.bgColor2 = comonFunctions.getColor(bgColor2);
		}
		if (fontColor == "") {
			this.fontColor = comonFunctions.getColor("black");
		} else {
			this.fontColor = comonFunctions.getColor(fontColor);
		}
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

	public void setText(String text) {
		this.text = text;
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


	public String getFontName() {
		return fontName;
	}


	public void setFontName(String fontName) {
		this.fontName = fontName;
	}


	public String getFontHeight() {
		return fontHeight;
	}


	public void setFontHeight(String fontHeight) {
		this.fontHeight = fontHeight;
	}


	public org.eclipse.swt.graphics.Color getbgColor1() {
		return bgColor1;
	}


	public void setbgColor1(String bgColor1) {
		this.bgColor1 = comonFunctions.getColor(bgColor1);
	}


	public org.eclipse.swt.graphics.Color getbgColor2() {
		return bgColor2;
	}


	public void setbgColor2(String bgColor2) {
		this.bgColor2 = comonFunctions.getColor(bgColor2);
	}


	public org.eclipse.swt.graphics.Color getfontColor() {
		return fontColor;
	}


	public void setfontColor(String fontColor) {
		this.fontColor = comonFunctions.getColor(fontColor);
	}


}