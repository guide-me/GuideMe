package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	private String id;
	private List<Page> pages = new ArrayList<Page>();

	public Chapter() {
	}
	
	public Chapter(String id) {
		this.id = id;
	}
	
	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages.clear();
		this.pages.addAll(pages);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
