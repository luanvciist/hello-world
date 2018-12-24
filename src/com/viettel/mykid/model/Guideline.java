package com.viettel.mykid.model;

import java.io.Serializable;

public class Guideline implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;

	private int id;
	private int productId;
	private String title;
	private String content;
	private String src;
	private String href;
	private int popupStatus;
	private String popup;
	private int delFlg;

	public Guideline() {

	}

	public Guideline(int id, String title, String content, String src, String href, int popupStatus, String popup) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.src = src;
		this.href = href;
		this.popupStatus = popupStatus;
		this.popup = popup;
	}
	
	public Guideline(String title, String content, String src, String href, int popupStatus, String popup) {
		this.title = title;
		this.content = content;
		this.src = src;
		this.href = href;
		this.popupStatus = popupStatus;
		this.popup = popup;
	}
	
	public Guideline(int id, String title, String content, String href, int popupStatus, String popup) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.href = href;
		this.popupStatus = popupStatus;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getPopupStatus() {
		return popupStatus;
	}

	public void setPopupStatus(int popupStatus) {
		this.popupStatus = popupStatus;
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
