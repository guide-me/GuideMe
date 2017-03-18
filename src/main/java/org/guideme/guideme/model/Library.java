package org.guideme.guideme.model;

public class Library {
	public String image;
	public String title;
	public String file;
	public String author;

	public Library(String pimage, String ptitle, String pfile, String pauthor) {
		image = pimage;
		title = ptitle;
		file = pfile;
		author = pauthor;
	}

	/*
	@Override
	public int compareTo(Library other) {
		return title.compareToIgnoreCase(other.title);
	}
	*/
}
