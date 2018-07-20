package com.example.mobilesafe.bean;

public class MenuInfo {
	private int drawableId;
	private String titleStr;
	public MenuInfo() {
		super();
	}
	public MenuInfo(int drawableId, String titleStr) {
		super();
		this.drawableId = drawableId;
		this.titleStr = titleStr;
	}
	public int getDrawableId() {
		return drawableId;
	}
	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}
	public String getTitleStr() {
		return titleStr;
	}
	public void setTitleStr(String titleStr) {
		this.titleStr = titleStr;
	}
	@Override
	public String toString() {
		return "MenuInfo [drawableId=" + drawableId + ", titleStr=" + titleStr + "]";
	}
	
}
