package org.guideme.guideme.scripting;

import java.util.ArrayList;

import org.guideme.guideme.model.Button;

public class Buttons {
	private ArrayList<Button> button = new ArrayList<Button>();

	public void addButton(String target, String text, String ifSet, String ifNotSet, String set, String unSet, String jScript, String image) {
		Button button = new Button(target, text, ifSet, ifNotSet, set, unSet, jScript, image);
		this.button.add(button);
	}

	public Button getButton(int i) {
		return button.get(i);
	}
	
	public int buttonCount() {
		return button.size();
	}
	
	public void clear() {
		button = new ArrayList<Button>();
	}
	
}
