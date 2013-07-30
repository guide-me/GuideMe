package org.guideme.guideme.model;

public class Image {
	private String url;
	private int width;
	private int height;
	private String mimeType;
	
	public Image(String url, int width, int height, String mimeType) {
		this.url = url;
		this.width = width;
		this.height = height;
		this.mimeType = mimeType;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getSizeString() {
		return width + "x" + height;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	
}
