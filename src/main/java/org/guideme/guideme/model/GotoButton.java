package org.guideme.guideme.model;

public class GotoButton extends Button {
	
	public final static String DEFAULT_TEXT = "continue";
	
	protected String target;
	
	public GotoButton(String target) {
		this(target, DEFAULT_TEXT);
	}
	
	public GotoButton(String target, String text) {
		this.target = target;
		this.text = text;
	}
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}
