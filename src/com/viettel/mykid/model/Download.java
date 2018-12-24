package com.viettel.mykid.model;

import java.io.Serializable;

public class Download implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	
	private int id;
	private int productId;
	private String os;
	private String classSuffix;
	private String href;
	private String src;
	private String delFlg;
	
	public Download() {
	}

	public Download(int id, String os, String classSuffix, String href, String src) {
		this.id = id;
		this.os = os;
		this.classSuffix = classSuffix;
		this.href = href;
		this.src = src;
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

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getClassSuffix() {
		return classSuffix;
	}

	public void setClassSuffix(String classSuffix) {
		this.classSuffix = classSuffix;
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

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	
}
