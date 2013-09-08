package org.guideme.guideme.model;

import java.util.ArrayList;

import org.guideme.guideme.settings.ComonFunctions;

public class Image {
	private String id; //file name of image
	private String ifSet;
	private String ifNotSet;

	public Image(String id, String ifSet, String ifNotSet) {
		this.id = id;
		this.ifSet = ifSet;
		this.ifNotSet = ifNotSet;
	}

	public String getId() {
		return id;
	}

	public boolean canShow(ArrayList<String> setList) {
		return ComonFunctions.canShow(setList, ifSet, ifNotSet);
	}

}
