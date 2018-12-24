package com.viettel.mykid.ulti;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;

public class ConstantUtil {
	
	// ==================================================================
	//                                                        CONSTRUCTER
	// ==================================================================
	private ConstantUtil() {
	}

	// ==================================================================
	//                                                               PAGE
	// ==================================================================
	public static final String ERROR_PAGE = "error";
	public static final String SUCCESS_PAGE = "success";
	public static final String LOGIN_PAGE = "login";
	public static final String ADMIN_PAGE = "admin";
	public static final String MANAGER_PAGE = "manager";
	
	// ==================================================================
	//                                                             FOLDER
	// ==================================================================
	public static final String IMAGES_FOLDER = "./images/";
	public static final String IMAGES_FOLDER_BANNER = IMAGES_FOLDER + "banner/";
	public static final String IMAGES_FOLDER_BODY = IMAGES_FOLDER + "body/";
	public static final String IMAGES_FOLDER_SLIDER = IMAGES_FOLDER + "slider/";
	public static final String IMAGES_FOLDER_COLOR = IMAGES_FOLDER + "color/";
	public static final String IMAGES_FOLDER_STRUCTURE = IMAGES_FOLDER + "structure/";
	public static final String IMAGES_FOLDER_DOWNLOAD = IMAGES_FOLDER + "download/";
	public static final String IMAGES_FOLDER_PRODUCT = IMAGES_FOLDER + "product/";
	public static final String IMAGES_FOLDER_PACKAGE = IMAGES_FOLDER + "package/";
	public static final String IMAGES_FOLDER_FEATURE = IMAGES_FOLDER + "feature/";
	public static final String IMAGES_FOLDER_GUIDELINE = IMAGES_FOLDER + "guideline/";

	// ==================================================================
	//                                                               PATH
	// ==================================================================
	public static final String PATH_IMAGES_FOLDER = ServletActionContext.getServletContext().getRealPath("") + File.separator +"images";
	public static final String PATH_IMAGES_FOLDER_BANNER = PATH_IMAGES_FOLDER + File.separator + "banner";
	public static final String PATH_IMAGES_FOLDER_BODY = PATH_IMAGES_FOLDER + File.separator + "body";
	public static final String PATH_IMAGES_FOLDER_SLIDER = PATH_IMAGES_FOLDER + File.separator + "slider";
	public static final String PATH_IMAGES_FOLDER_COLOR = PATH_IMAGES_FOLDER + File.separator + "color";
	public static final String PATH_IMAGES_FOLDER_STRUCTURE = PATH_IMAGES_FOLDER + File.separator + "structure";
	public static final String PATH_IMAGES_FOLDER_DOWNLOAD = PATH_IMAGES_FOLDER + File.separator + "download";
	public static final String PATH_IMAGES_FOLDER_PRODUCT = PATH_IMAGES_FOLDER + File.separator + "product";
	public static final String PATH_IMAGES_FOLDER_PACKAGE = PATH_IMAGES_FOLDER + File.separator + "package";
	public static final String PATH_IMAGES_FOLDER_FEATURE = PATH_IMAGES_FOLDER + File.separator + "feature";
	public static final String PATH_IMAGES_FOLDER_GUIDELINE = PATH_IMAGES_FOLDER + File.separator + "guideline";
	
	// ==================================================================
	//                                                          FILE SIZE
	// ==================================================================
	public static final int MAX_FILE_SIZE_IMAGE = 2097152;
	public static final int MAX_FILE_SIZE_PDF = 20971520;
	
	
	// ==================================================================
	//                                                          CHARACTER
	// ==================================================================
	public static final String DOTS = "...";
	
	// ==================================================================
	//                                                            MESSAGE
	// ==================================================================
	public static final String MUST_LOGIN = "You must login to use this function";
	public static final String ADD_SUCCESS = "Add success";
	public static final String UPDATE_SUCCESS = "Update success";
	public static final String DELETE_SUCCESS = "Delete success";
	
	
	public static Properties getConfigFile() {
		Properties prop = new Properties();

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			prop.load(classLoader.getResourceAsStream("/config.properties"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return prop;
	}
}
