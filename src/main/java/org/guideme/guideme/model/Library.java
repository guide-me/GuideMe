package org.guideme.guideme.model;

import java.util.Date;;
public class Library {
	public String image;
	public String title;
	public String file;
	public String author;
	public Date date;

	public Library(String pimage, String ptitle, String pfile, String pauthor, Date pdate) {
		image = pimage;
		title = ptitle;
		file = pfile;
		author = pauthor;
		date = pdate;
	}

	/*
	@Override
	public int compareTo(Library other) {
		return title.compareToIgnoreCase(other.title);
	}
	*/
}
