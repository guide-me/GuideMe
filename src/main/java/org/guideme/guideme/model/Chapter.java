package org.guideme.guideme.model;

import java.util.HashMap;

public class Chapter {
	private String id;
	private HashMap<String, Page> pages = new HashMap<String, Page>();

	public Chapter() {
	}
	
	public Chapter(String id) {
		this.id = id;
	}
	
	public HashMap<String, Page> getPages() {
		return pages;
	}

	public void setPages(HashMap<String, Page> pages) {
		this.pages = pages;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
