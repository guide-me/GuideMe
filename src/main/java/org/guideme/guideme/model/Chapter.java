package org.guideme.guideme.model;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	private List<Page> pages = new ArrayList<Page>();

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages.clear();
		this.pages.addAll(pages);
	}
}
