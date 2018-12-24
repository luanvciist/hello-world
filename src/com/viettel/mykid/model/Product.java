package com.viettel.mykid.model;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;

	private int id;
	private String productName;
	private String productContent;
	private String advantageContent;
	private String specificationContent;
	private String specificationSrc;
	private int delFlg;

	public Product() {

	}

	public Product(int id, String productName, String productContent, String advantageContent, String specificationContent,
			String specificationSrc) {
		this.id = id;
		this.productName = productName;
		this.productContent = productContent;
		this.advantageContent = advantageContent;
		this.specificationContent = specificationContent;
		this.specificationSrc = specificationSrc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductContent() {
		return productContent;
	}

	public void setProductContent(String productContent) {
		this.productContent = productContent;
	}

	public String getAdvantageContent() {
		return advantageContent;
	}

	public void setAdvantageContent(String advantageContent) {
		this.advantageContent = advantageContent;
	}

	public String getSpecificationContent() {
		return specificationContent;
	}

	public void setSpecificationContent(String specificationContent) {
		this.specificationContent = specificationContent;
	}

	public String getSpecificationSrc() {
		return specificationSrc;
	}

	public void setSpecificationSrc(String specificationSrc) {
		this.specificationSrc = specificationSrc;
	}

	public int getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(int delFlg) {
		this.delFlg = delFlg;
	}
}
