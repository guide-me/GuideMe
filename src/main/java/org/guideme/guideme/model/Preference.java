package org.guideme.guideme.model;

public class Preference implements Comparable<Preference> {
	private String key;
	private String type;
	private String screenDesc;
	private int sortOrder;
	private Boolean blnValue;
	private Double dblValue;
	private String strValue;

	public Preference(String key, String type, int sortOrder, String screenDesc, Boolean blnValue,
			Double dblValue, String strValue) {
		this.key = key;
		this.type = type;
		this.sortOrder = sortOrder;
		this.blnValue = blnValue;
		this.dblValue = dblValue;
		this.strValue = strValue;
		this.screenDesc = screenDesc;
	}

	@Override
	public int compareTo(Preference comparePref) {
		int compareOrder = comparePref.getSortOrder();
		return this.sortOrder-compareOrder;
	}

	public String getType() {
		return type;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public Boolean getBlnValue() {
		return blnValue;
	}

	public void setBlnValue(Boolean blnValue) {
		this.blnValue = blnValue;
	}

	public Double getDblValue() {
		return dblValue;
	}

	public void setDblValue(Double dblValue) {
		this.dblValue = dblValue;
	}

	public String getstrValue() {
		return strValue;
	}

	public void setstrValue(String strValue) {
		this.strValue = strValue;
	}

	public String getKey() {
		return key;
	}

	public String getScreenDesc() {
		return screenDesc;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}

