package com.viettel.mykid.action;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.viettel.mykid.dao.BannerDao;
import com.viettel.mykid.model.Banner;
import com.viettel.mykid.ulti.ConnectDB;
import com.viettel.mykid.ulti.ConstantUtil;
import com.viettel.mykid.ulti.DbUtil;
import com.viettel.mykid.ulti.FileUploadUtil;
import com.viettel.mykid.ulti.SessionUtil;

public class BannerAction extends ActionSupport {

	// ==================================================================
	//                                                           CONSTANT
	// ==================================================================
		
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(BannerAction.class);
	private static int WIDTH_IMAGE_BACKGROUND = 686;
	private static int HEIGHT_IMAGE_BACKGROUND = 688;
	private static int WIDTH_IMAGE_PARENT_LOGO = 421;
	private static int HEIGHT_IMAGE_PARENT_LOGO = 259;
	private static int WIDTH_IMAGE_CHILD_LOGO = 594;
	private static int HEIGHT_IMAGE_CHILD_LOGO = 746;
	private static int WIDTH_IMAGE = 695;
	private static int HEIGHT_IMAGE = 534;
	
	// ==================================================================
	//                                                         PROPERTIES
	// ==================================================================
	private String errorMessage;
	private String message;

	private File fileBackground;
	private String fileBackgroundContentType; 
	private String fileBackgroundFileName; 
	
	private File fileParentLogo;
	private String fileParentLogoContentType; 
	private String fileParentLogoFileName; 
	
	private File fileChildLogo;
	private String fileChildLogoContentType; 
	private String fileChildLogoFileName; 
	
	private File fileImage;
	private String fileImageContentType; 
	private String fileImageFileName; 
	
	private String title;
	private String slogan;
	private String id;
	
	// ==================================================================
	//                                                     INITIAL OBJECT
	// ==================================================================

	private BannerDao bannerDao = new BannerDao();
	private Banner banner = new Banner();
	
	// ==================================================================
	//                                                     EXECUTE METHOD
	// ==================================================================
	
	/**
	 * Go to logo page
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
			banner = bannerDao.getBanner(connection);
			
			if (banner == null) {
				message = "Missing banner in database";
				LOGGER.info("Not exist data in table banner");
				return ConstantUtil.ERROR_PAGE;
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			errorMessage = e.getMessage();
			LOGGER.error(e.getMessage(), e);
			return ConstantUtil.ERROR_PAGE;
		} finally {
			DbUtil.closeQuietly(connection);
		}
		
		return ConstantUtil.SUCCESS_PAGE;
	}
	
	public String update(){
		
		// Check user is ADM
		if (!SessionUtil.isAdmin()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}
		
		// Validate parameter
		if(!validateParams()) {
			return execute();
		}
		
		// Create query when each file is not null
		StringBuilder condition = new StringBuilder();
		if(fileBackground != null) {
			condition.append(" BACKGROUND_URL = '" + ConstantUtil.IMAGES_FOLDER_BANNER + fileBackgroundFileName + "',");
		}
		
		if (fileParentLogo != null) {
			condition.append(" PARENT_LOGO_SRC = '" + ConstantUtil.IMAGES_FOLDER_BANNER + fileParentLogoFileName + "',");
		}

		if (fileChildLogo != null) {
			condition.append(" CHILD_LOGO_SRC = '" + ConstantUtil.IMAGES_FOLDER_BANNER + fileChildLogoFileName + "',");
		}

		if (fileImage != null) {
			condition.append(" IMAGE_SRC = '" + ConstantUtil.IMAGES_FOLDER_BANNER + fileImageFileName + "',");
		}
		
		Connection connection = null;
		try {
			
			// Open connection and disable auto commit
			connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);

			// Update banner
			bannerDao.updateBanner(connection, condition, title, slogan, id);

			// Copy file to images folder
			if(fileBackground != null) {
				FileUploadUtil.resize(fileBackground, ConstantUtil.PATH_IMAGES_FOLDER_BANNER, ConstantUtil.PATH_IMAGES_FOLDER_BANNER + File.separator + fileBackgroundFileName,
						WIDTH_IMAGE_BACKGROUND, HEIGHT_IMAGE_BACKGROUND); 
			}
			
			if(fileParentLogo != null) {
				FileUploadUtil.resize(fileParentLogo, ConstantUtil.PATH_IMAGES_FOLDER_BANNER, ConstantUtil.PATH_IMAGES_FOLDER_BANNER + File.separator + fileParentLogoFileName,
						WIDTH_IMAGE_PARENT_LOGO, HEIGHT_IMAGE_PARENT_LOGO);
			}
			
			if(fileChildLogo != null) {
				FileUploadUtil.resize(fileChildLogo, ConstantUtil.PATH_IMAGES_FOLDER_BANNER, ConstantUtil.PATH_IMAGES_FOLDER_BANNER + File.separator + fileChildLogoFileName,
						WIDTH_IMAGE_CHILD_LOGO, HEIGHT_IMAGE_CHILD_LOGO);
			}
			
			if(fileImage != null) {
				FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_BANNER, ConstantUtil.PATH_IMAGES_FOLDER_BANNER + File.separator + fileImageFileName,
						WIDTH_IMAGE, HEIGHT_IMAGE);
			}

			// Commit data
			connection.commit();
		} catch (Exception e) {
			DbUtil.rollBack(connection);
			errorMessage = e.getMessage();
			LOGGER.error(e.getMessage(), e);
			return ConstantUtil.ERROR_PAGE;
		} finally {
			DbUtil.closeQuietly(connection);
		}
		
		// Set all item inform to null
		title = null;
		slogan = null;
		
		message = ConstantUtil.UPDATE_SUCCESS;
		return execute();
	}

	// ==================================================================
	//                                                     PRIVATE METHOD
	// ==================================================================
	
	/**
	 * Validate parameters
	 * @return
	 */
	private boolean validateParams() {
		
		StringBuilder buiderMsg = new StringBuilder();
		
		List<String> imageList = FileUploadUtil.getListExtensionImageFile();
		
		// Check fileBackground
		if (fileBackground != null) {

			if (!imageList.contains(fileBackgroundContentType)) {
				buiderMsg.append("Background file is invalid format<br/>");
			} else if (fileBackground.length() >= ConstantUtil.MAX_FILE_SIZE_IMAGE) {
				buiderMsg.append("Background file is exceed 2Mb<br/>");
			}
		}
		
		// Check fileParentLogo
		if (fileParentLogo != null) {

			if (!imageList.contains(fileParentLogoContentType)) {
				buiderMsg.append("Parent logo file is invalid format<br/>");
			} else if (fileParentLogo.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
				buiderMsg.append("Parent logo file is exceed 2Mb<br/>");
			}
		}
		
		// Check fileChildLogo
		if (fileChildLogo != null) {

			if (!imageList.contains(fileChildLogoContentType)) {
				buiderMsg.append("Child logo file is invalid format<br/>");
			} else if (fileChildLogo.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
				buiderMsg.append("Child logo file is exceed 2Mb<br/>");
			}
		}
		
		// Check fileImage
		if (fileImage != null) {

			if (!imageList.contains(fileImageContentType)) {
				buiderMsg.append("Image file is invalid format<br/>");
			} else if (fileImage.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
				buiderMsg.append("Image file is exceed 2Mb<br/>");
			}
		}
		
		if (StringUtils.isBlank(title)) {
			buiderMsg.append("Please input title<br/>");
		}

		if (StringUtils.isBlank(slogan)) {
			buiderMsg.append("Please input slogan<br/>");
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
	public File getFileBackground() {
		return fileBackground;
	}

	public void setFileBackground(File fileBackground) {
		this.fileBackground = fileBackground;
	}

	public String getFileBackgroundContentType() {
		return fileBackgroundContentType;
	}

	public void setFileBackgroundContentType(String fileBackgroundContentType) {
		this.fileBackgroundContentType = fileBackgroundContentType;
	}

	public String getFileBackgroundFileName() {
		return fileBackgroundFileName;
	}

	public void setFileBackgroundFileName(String fileBackgroundFileName) {
		this.fileBackgroundFileName = fileBackgroundFileName;
	}

	public File getFileParentLogo() {
		return fileParentLogo;
	}

	public void setFileParentLogo(File fileParentLogo) {
		this.fileParentLogo = fileParentLogo;
	}

	public String getFileParentLogoContentType() {
		return fileParentLogoContentType;
	}

	public void setFileParentLogoContentType(String fileParentLogoContentType) {
		this.fileParentLogoContentType = fileParentLogoContentType;
	}

	public String getFileParentLogoFileName() {
		return fileParentLogoFileName;
	}

	public void setFileParentLogoFileName(String fileParentLogoFileName) {
		this.fileParentLogoFileName = fileParentLogoFileName;
	}

	public File getFileChildLogo() {
		return fileChildLogo;
	}

	public void setFileChildLogo(File fileChildLogo) {
		this.fileChildLogo = fileChildLogo;
	}

	public String getFileChildLogoContentType() {
		return fileChildLogoContentType;
	}

	public void setFileChildLogoContentType(String fileChildLogoContentType) {
		this.fileChildLogoContentType = fileChildLogoContentType;
	}

	public String getFileChildLogoFileName() {
		return fileChildLogoFileName;
	}

	public void setFileChildLogoFileName(String fileChildLogoFileName) {
		this.fileChildLogoFileName = fileChildLogoFileName;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BannerDao getBannerDao() {
		return bannerDao;
	}

	public void setBannerDao(BannerDao bannerDao) {
		this.bannerDao = bannerDao;
	}

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
