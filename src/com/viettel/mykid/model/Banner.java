package com.viettel.mykid.model;

import java.io.Serializable;

public class Banner implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;

	private int id;
	private String backgroundUrl;
	private String parentLogoSrc;
	private String childLogoSrc;
	private String title;
	private String slogan;
	private String imageSrc;
	private int delFlg;

	public Banner() {
	}

	public Banner(int id, String backgroundUrl, String parentLogoSrc, String childLogoSrc, String title, String slogan,
			String imageSrc) {
		this.id = id;
		this.backgroundUrl = backgroundUrl;
		this.parentLogoSrc = parentLogoSrc;
		this.childLogoSrc = childLogoSrc;
		this.title = title;
		this.slogan = slogan;
		this.imageSrc = imageSrc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public String getParentLogoSrc() {
		return parentLogoSrc;
	}

	public void setParentLogoSrc(String parentLogoSrc) {
		this.parentLogoSrc = parentLogoSrc;
	}

	public String getChildLogoSrc() {
		return childLogoSrc;
	}

	public void setChildLogoSrc(String childLogoSrc) {
		this.childLogoSrc = childLogoSrc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public int getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(int delFlg) {
		this.delFlg = delFlg;
	}
	
}
