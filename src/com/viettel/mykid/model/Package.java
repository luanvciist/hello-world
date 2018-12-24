package com.viettel.mykid.model;

import java.io.Serializable;

public class Package implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	
	private int id;
	private int productId;
	private int itemStatus;
	private String colorSuffix;
	private String backgroundUrl;
	private String title;
	private String titleDetail;
	private String content;
	private int delFlg;
	
	public Package() {
	}

	public Package(int id, int itemStatus, String colorSuffix, String backgroundUrl, String title, String titleDetail,
			String content) {
		this.id = id;
		this.itemStatus = itemStatus;
		this.colorSuffix = colorSuffix;
		this.backgroundUrl = backgroundUrl;
		this.title = title;
		this.titleDetail = titleDetail;
		this.content = content;
	}
	
	public Package(int itemStatus, String colorSuffix, String backgroundUrl, String title, String titleDetail,
			String content) {
		this.itemStatus = itemStatus;
		this.colorSuffix = colorSuffix;
		this.backgroundUrl = backgroundUrl;
		this.title = title;
		this.titleDetail = titleDetail;
		this.content = content;
	}
	
	public Package(int id, int itemStatus, String colorSuffix, String title, String titleDetail,
			String content) {
		this.id = id;
		this.itemStatus = itemStatus;
		this.colorSuffix = colorSuffix;
		this.title = title;
		this.titleDetail = titleDetail;
		this.content = content;
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

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getColorSuffix() {
		return colorSuffix;
	}

	public void setColorSuffix(String colorSuffix) {
		this.colorSuffix = colorSuffix;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleDetail() {
		return titleDetail;
	}

	public void setTitleDetail(String titleDetail) {
		this.titleDetail = titleDetail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(int delFlg) {
		this.delFlg = delFlg;
	}
}
