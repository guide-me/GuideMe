package org.guideme.guideme;

import java.util.ArrayList;
import java.util.List;

public class Page {
	private String id;
	private String text;
	private List<Button> buttons = new ArrayList<Button>();
	
	public Page(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public List<Button> getButtons() {
		return buttons;
	}
	public void setButtons(List<Button> buttons) {
		this.buttons.clear();
		this.buttons.addAll(buttons);
	}
	
}
