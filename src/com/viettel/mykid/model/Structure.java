package com.viettel.mykid.model;

import java.io.Serializable;

public class Structure implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	
	private int id;
	private int productId;
	private String src;
	private int delFlg;
	
	public Structure() {
	}

	public Structure(int id, String src) {
		this.id = id;
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

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public int getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(int delFlg) {
		this.delFlg = delFlg;
	}
}
