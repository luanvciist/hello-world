package com.viettel.mykid.model;

import java.io.Serializable;

public class Feature implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;

	private int id;
	private int productId;
	private String title;
	private String divClass;
	private int mainStatus;
	private String href;
	private String src;
	private String h6Suffix;
	private String popup;
	private int delFlg;
	
	public Feature() {
	}
	
	public Feature(String title, String divClass, int mainStatus, String href, String src, String h6Suffix, String popup) {
		this.title = title;
		this.divClass = divClass;
		this.mainStatus = mainStatus;
		this.href = href;
		this.src = src;
		this.h6Suffix = h6Suffix;
		this.popup = popup;
	}
	
	public Feature(int id, String title, String divClass, int mainStatus, String href, String h6Suffix,
			String popup) {
		this.id = id;
		this.title = title;
		this.divClass = divClass;
		this.mainStatus = mainStatus;
		this.href = href;
		this.h6Suffix = h6Suffix;
		this.popup = popup;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDivClass() {
		return divClass;
	}

	public void setDivClass(String divClass) {
		this.divClass = divClass;
	}

	public int getMainStatus() {
		return mainStatus;
	}

	public void setMainStatus(int mainStatus) {
		this.mainStatus = mainStatus;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getH6Suffix() {
		return h6Suffix;
	}

	public void setH6Suffix(String h6Suffix) {
		this.h6Suffix = h6Suffix;
	}

	public String getPopup() {
		return popup;
	}

	public void setPopup(String popup) {
		this.popup = popup;
	}
	
	public int getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(int delFlg) {
		this.delFlg = delFlg;
	}
}
