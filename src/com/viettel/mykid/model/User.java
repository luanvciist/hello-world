package com.viettel.mykid.model;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;

	private int id;
	private String username;
	private String password;
	private String src;
	private int role;
	private int delFlg;
	
	public User() {
	}

	public User(String username, String password, String src, int role) {
		this.username = username;
		this.password = password;
		this.src = src;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(int delFlg) {
		this.delFlg = delFlg;
	}
}
