package com.viettel.mykid.ulti;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

public class SessionUtil {

	private SessionUtil() {
	}

	/**
	 * Check is Admin
	 * 
	 * @return
	 */
	public static boolean isAdmin() {
		HttpSession session = ServletActionContext.getRequest().getSession(true);
		if (session.getAttribute("username") == null) {
			return false;
		}

		if (!StringUtils.equals(session.getAttribute("role").toString(), "1")) {
			return false;
		}

		return true;
	}

	/**
	 * Check is Manager or Admin
	 * 
	 * @return
	 */
	public static boolean isManager() {
		HttpSession session = ServletActionContext.getRequest().getSession(true);
		if (session.getAttribute("username") == null) {
			return false;
		}

		if (!StringUtils.equals(session.getAttribute("role").toString(), "1")
				&& !StringUtils.equals(session.getAttribute("role").toString(), "2")) {
			return false;
		}

		return true;
	}

}
