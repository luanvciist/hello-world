package com.viettel.mykid.model;

import java.io.Serializable;

public class Body implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;

	private int id;
	private String src;
	private int delFlg;

	public Body() {
	}

	public Body(int id, String src) {
		this.id = id;
		this.src = src;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
