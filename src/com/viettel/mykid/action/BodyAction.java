package com.viettel.mykid.action;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.viettel.mykid.dao.BodyDao;
import com.viettel.mykid.model.Body;
import com.viettel.mykid.ulti.ConnectDB;
import com.viettel.mykid.ulti.ConstantUtil;
import com.viettel.mykid.ulti.DbUtil;
import com.viettel.mykid.ulti.FileUploadUtil;
import com.viettel.mykid.ulti.SessionUtil;

public class BodyAction extends ActionSupport {
	
	// ==================================================================
	//                                                           CONSTANT
	// ==================================================================
			
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(BodyAction.class);
	private static int WIDTH_IMAGE_BODY = 833;
	private static int HEIGHT_IMAGE_BODY = 731;
	
	// ==================================================================
	//                                                         PROPERTIES
	// ==================================================================
	private String errorMessage;
	private String message;
	private String id;
	private String keepSrc;
	
	private File fileImage;
	private String fileImageContentType; 
	private String fileImageFileName; 
		
	// ==================================================================
	//                                                     INITIAL OBJECT
	// ==================================================================
	private BodyDao bodyDao = new BodyDao();
	private Body body = new Body();
	private List<Body> bodyList = new ArrayList<Body>();
	
	// ==================================================================
	//                                                     EXECUTE METHOD
	// ==================================================================
		
	/**
	 * Go to body page
	 */
	@Override
	public String execute() {

		// Check user is ADM
		if (!SessionUtil.isAdmin()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}

		Connection connection = null;
		try {
			// Open connect to database
			connection = ConnectDB.getConnection();

			// Get information of the banner
			bodyList = bodyDao.getBodyList(connection);

		} catch (ClassNotFoundException | SQLException e) {
			errorMessage = e.getMessage();
			LOGGER.error(e.getMessage(), e);
			return ConstantUtil.ERROR_PAGE;
		} finally {
			DbUtil.closeQuietly(connection);
		}

		return ConstantUtil.SUCCESS_PAGE;
	}
		
	/**
	 * Display detail body
	 */
	public String viewBodyDetail() {

		// Check user is ADM
		if (!SessionUtil.isAdmin()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}

		// Validate id
		if (!validateId()) {
			return execute();
		}

		Connection connection = null;
		try {
			// Open connect to database
			connection = ConnectDB.getConnection();

			// Get body object by id
			body = bodyDao.getBodyById(connection, id);

		} catch (ClassNotFoundException | SQLException e) {
			errorMessage = e.getMessage();
			LOGGER.error(e.getMessage(), e);
			return ConstantUtil.ERROR_PAGE;
		} finally {
			DbUtil.closeQuietly(connection);
		}

		return execute();
	}
		
	/**
	 * Update body
	 * @return
	 */
	public String updateBody() {

		// Check user is ADM
		if (!SessionUtil.isAdmin()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}

		// Validate parameters
		if (!validateParams()) {
			keepParamsUpdate();
			return execute();
		}

		Connection connection = null;
		try {
			// Open connection and disable auto commit
			connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);

			String src = ConstantUtil.IMAGES_FOLDER_BODY + fileImageFileName;
			bodyDao.updateBody(connection, id, src);

			// Copy file to body folder
			FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_BODY,
					ConstantUtil.PATH_IMAGES_FOLDER_BODY + File.separator + fileImageFileName, WIDTH_IMAGE_BODY,
					HEIGHT_IMAGE_BODY);

			// Commit data
			connection.commit();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			DbUtil.rollBack(connection);
			errorMessage = e.getMessage();
			LOGGER.error(e.getMessage(), e);
			return ConstantUtil.ERROR_PAGE;
		} finally {
			DbUtil.closeQuietly(connection);
		}

		message = ConstantUtil.UPDATE_SUCCESS;
		return execute();
	}
		
	// ==================================================================
	//                                                     PRIVATE METHOD
	// ==================================================================
		
	/**
	 * Keep form update
	 */
	private void keepParamsUpdate() {
		if (StringUtils.isNumeric(id)) {
			body.setId(Integer.parseInt(id));
			body.setSrc(keepSrc);
		}
	}
	
	/**
	 * Validate ID
	 * @param id
	 * @return
	 */
	private boolean validateId() {
		
		// Check empty
		if(StringUtils.isBlank(id) || StringUtils.equals(id, "0")) {
			message = "Please view any image on right table<br/>";
			return false;
		}
		
		// Check is numeric
		if (!StringUtils.isNumeric(id)) {
			message = "Id must be numeric<br/>";
			return false;
		}
		
		return true;
	}
	
	/**
	 * Validate parameters
	 * @return
	 */
	private boolean validateParams() {
		
		StringBuilder buiderMsg = new StringBuilder();
		
		if(StringUtils.isBlank(id) || StringUtils.equals(id, "0")) {
			message = "Please choose new image to update";
			return false;
		}
		
		// Validate other parameters
		if (fileImage == null) {
			buiderMsg.append("Please choose new image to update<br/>");
		} else {
			
			if (!FileUploadUtil.getListExtensionImageFile().contains(fileImageContentType)) {
				buiderMsg.append("New image is invalid format<br/>");
			} else if(fileImage.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
				buiderMsg.append("New image is exceed 2Mb<br/>");
			}
		}
		// If has error then set value for message and return false
		if (!StringUtils.isEmpty(buiderMsg)) {
			message = buiderMsg.toString();
			return false;
		}

		return true;
	}
	
	// ==================================================================
	//                                                     GET/SET METHOD
	// ==================================================================
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BodyDao getBodyDao() {
		return bodyDao;
	}

	public void setBodyDao(BodyDao bodyDao) {
		this.bodyDao = bodyDao;
	}

	public List<Body> getBodyList() {
		return bodyList;
	}

	public void setBodyList(List<Body> bodyList) {
		this.bodyList = bodyList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public File getFileImage() {
		return fileImage;
	}

	public void setFileImage(File fileImage) {
		this.fileImage = fileImage;
	}

	public String getFileImageContentType() {
		return fileImageContentType;
	}

	public void setFileImageContentType(String fileImageContentType) {
		this.fileImageContentType = fileImageContentType;
	}

	public String getFileImageFileName() {
		return fileImageFileName;
	}

	public void setFileImageFileName(String fileImageFileName) {
		this.fileImageFileName = fileImageFileName;
	}

	public String getKeepSrc() {
		return keepSrc;
	}

	public void setKeepSrc(String keepSrc) {
		this.keepSrc = keepSrc;
	}
}
