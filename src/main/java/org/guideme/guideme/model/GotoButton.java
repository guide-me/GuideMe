package org.guideme.guideme.model;

public class GotoButton extends Button {
	
	private GotoButton(String target, String text, String ifSet,
			String ifNotSet, String Set, String UnSet, String jScript, String image) {
		super(target, text, ifSet, ifNotSet, Set, UnSet, jScript, image);
	}

	public final static String DEFAULT_TEXT = "continue";
	
	public GotoButton(String target) {
		this(target, DEFAULT_TEXT, "", "", "", "", "", "");
	}

}
