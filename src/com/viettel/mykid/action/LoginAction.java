package com.viettel.mykid.action;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.viettel.mykid.dao.UserDao;
import com.viettel.mykid.model.User;
import com.viettel.mykid.ulti.ConstantUtil;

public class LoginAction extends ActionSupport implements SessionAware{

	// ==================================================================
	//                                                           CONSTANT
	// ==================================================================
	
	private static final long serialVersionUID = 1L;
	
	// ==================================================================
	//                                                         PROPERTIES
	// ==================================================================
	
	private String errorMessage;
	private String username;
	private String password;
	private SessionMap<String, Object> sessionMap;
	private UserDao userDao = new UserDao();
	
	// ==================================================================
	//                                                     EXECUTE METHOD
	// ==================================================================
	
	@Override
	public void setSession(Map<String, Object> map) {
		sessionMap = (SessionMap<String, Object>) map;
	}
	
	/**
	 * Go to login page
	 */
	@Override
	public String execute() {
		return "success";
	}

	/**
	 * Execute login
	 * @return
	 */
	public String doLogin() {
		
		StringBuilder buiderMsg = new StringBuilder();
		if(StringUtils.isBlank(username)) {
			buiderMsg.append("User name must not be empty<br/>");
		}
		
		if(StringUtils.isBlank(password)) {
			buiderMsg.append("Password must not be empty");
		}
		
		// If has error then set value for message and return false
		if (!StringUtils.isEmpty(buiderMsg)) {
			errorMessage = buiderMsg.toString();
			return ConstantUtil.ERROR_PAGE;
		}
		
		// Get user from database
		User user = userDao.getUser(username, password);

		// Check exist in database
		if (user == null) {
			errorMessage = "User name and Password are not exist in the system<br/>";
			return ConstantUtil.ERROR_PAGE;
		}

		// Set session when user is correct
		sessionMap.put("username", username);
		sessionMap.put("role", user.getRole());
		sessionMap.put("src", user.getSrc());

		if (user.getRole() == 1) {
			return ConstantUtil.ADMIN_PAGE;
		}

		return ConstantUtil.MANAGER_PAGE;
	}
	
	/**
	 * Logout
	 * @return
	 */
	public String doLogout() {

		// Remove session
		sessionMap.remove("userName");
		sessionMap.invalidate();
		return "success";
	}
	
	// ==================================================================
	//                                                     GET/SET METHOD
	// ==================================================================
	
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
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
