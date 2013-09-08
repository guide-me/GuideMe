package org.guideme.guideme.model;

public class GotoButton extends Button {
	
	private GotoButton(String target, String text, String ifSet,
			String ifNotSet, String Set, String UnSet, String jScript) {
		super(target, text, ifSet, ifNotSet, Set, UnSet, jScript);
	}

	public final static String DEFAULT_TEXT = "continue";
	
	public GotoButton(String target) {
		this(target, DEFAULT_TEXT, "", "", "", "", "");
	}

}
