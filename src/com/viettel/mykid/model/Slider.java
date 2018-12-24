package com.viettel.mykid.model;

import java.io.Serializable;

public class Slider implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	
	private int id;
	private int productId;
	private String sliderSuffix;
	private String src;
	private int delFlg;
	
	public Slider() {
	}

	public Slider(int id, String sliderSuffix, String src) {
		this.id = id;
		this.sliderSuffix = sliderSuffix;
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

	public String getSliderSuffix() {
		return sliderSuffix;
	}

	public void setSliderSuffix(String sliderSuffix) {
		this.sliderSuffix = sliderSuffix;
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
