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
import com.viettel.mykid.dao.DownloadDao;
import com.viettel.mykid.dao.ProductDao;
import com.viettel.mykid.model.Download;
import com.viettel.mykid.model.Product;
import com.viettel.mykid.ulti.ConnectDB;
import com.viettel.mykid.ulti.ConstantUtil;
import com.viettel.mykid.ulti.DbUtil;
import com.viettel.mykid.ulti.FileUploadUtil;
import com.viettel.mykid.ulti.SessionUtil;

public class DownloadAction extends ActionSupport {
	
	// ==================================================================
	//                                                           CONSTANT
	// ==================================================================
			
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(DownloadAction.class);
	private static int WIDTH_IMAGE_DOWNLOAD = 301;
	private static int HEIGHT_IMAGE_DOWNLOAD = 284;
	
	
	// ==================================================================
	//                                                         PROPERTIES
	// ==================================================================
	private String errorMessage;
	private String message;
	private String id;
	private String os;
	private String classSuffix;
	private String href;
	
	private File fileImage;
	private String fileImageContentType; 
	private String fileImageFileName; 
	
	private String keepSrc;
	
	private List<String> classSuffixList;
	
	// ==================================================================
	//                                                     INITIAL OBJECT
	// ==================================================================
	private DownloadDao downloadDao = new DownloadDao();
	private Download download= new Download();
	private List<Download> downloadList = new ArrayList<Download>();
	private Product product = new Product();
	private ProductDao productDao= new ProductDao();
	
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

			// Get information of the download
			downloadList = downloadDao.getAllDownload(connection);

			// Set classSuffixList
			classSuffixList = new ArrayList<String>();
			classSuffixList.add("col-md-offset-3");
			
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
	public String viewDownloadDetail() {

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
			download = downloadDao.getDownloadById(connection, id);

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
	 * Update Download
	 * 
	 * @return
	 */
	public String updateDownload() {

		// Check user is ADM
		if (!SessionUtil.isAdmin()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}

		// Validate parameters
		if (!validateUpdate()) {
			keepParamsUpdate();
			return execute();
		}

		Connection connection = null;
		try {
			// Open connection and disable auto commit
			connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);

			if (fileImage == null) {
				downloadDao.updateDownloadNoFile(connection, id, os, classSuffix, href);
			} else {
				String src = ConstantUtil.IMAGES_FOLDER_DOWNLOAD + fileImageFileName;
				downloadDao.updateDownloadHasFile(connection, id, os, classSuffix, href, src);

				// Copy file to body folder
				FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_DOWNLOAD,
						ConstantUtil.PATH_IMAGES_FOLDER_DOWNLOAD + File.separator + fileImageFileName,
						WIDTH_IMAGE_DOWNLOAD, HEIGHT_IMAGE_DOWNLOAD);
			}

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

	/**
	 * Add Download
	 * 
	 * @return
	 */
	public String addDownload() {

		// Check user is ADM
		if (!SessionUtil.isAdmin()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}

		// Validate parameter
		if (!validateAdd()) {
			keepParamsAdd();
			return execute();
		}

		Connection connection = null;
		try {
			// Open connection and disable auto commit
			connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);

			// Get productId
			product = productDao.getProduct(connection);

			String src = ConstantUtil.IMAGES_FOLDER_DOWNLOAD + fileImageFileName;
			downloadDao.addDownload(connection, product.getId(), os, classSuffix, href, src);

			// Copy file to body folder
			FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_DOWNLOAD,
					ConstantUtil.PATH_IMAGES_FOLDER_DOWNLOAD + File.separator + fileImageFileName, WIDTH_IMAGE_DOWNLOAD,
					HEIGHT_IMAGE_DOWNLOAD);

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

		message = ConstantUtil.ADD_SUCCESS;
		return execute();
	}

	/**
	 * Delete Download
	 * 
	 * @return
	 */
	public String deleteDownload() {

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
			// Open connection and disable auto commit
			connection = ConnectDB.getConnection();

			// Delete the Download
			downloadDao.deleteDownload(connection, id);

		} catch (ClassNotFoundException | SQLException e) {
			errorMessage = e.getMessage();
			LOGGER.error(e.getMessage(), e);
			return ConstantUtil.ERROR_PAGE;
		} finally {
			DbUtil.closeQuietly(connection);
		}

		message = ConstantUtil.DELETE_SUCCESS;
		return execute();
	}
	
	// ==================================================================
	//                                                     PRIVATE METHOD
	// ==================================================================
	
	/**
	 * Keep form add
	 */
	private void keepParamsAdd() {
		download.setId(0);
		download.setOs(os);
		download.setClassSuffix(classSuffix);
		download.setHref(href);
	}
	
	/**
	 * Keep form update
	 */
	private void keepParamsUpdate() {
		if (StringUtils.isNumeric(id)) {
			download.setId(Integer.parseInt(id));
			download.setSrc(keepSrc);
		}
		download.setOs(os);
		download.setClassSuffix(classSuffix);
		download.setHref(href);
	}
	
	
	/**
	 * Validate parameter when add data
	 * @return
	 */
	private boolean validateAdd() {
		
		StringBuilder buiderMsg = new StringBuilder();
		
		// Validate os
		if (StringUtils.isBlank(os)) {
			buiderMsg.append("Please input OS<br/>");
		}
		
		// Validate link
		if (StringUtils.isBlank(href)) {
			buiderMsg.append("Please input link download<br/>");
		}
		
		// Validate image
		if (fileImage == null) {
			buiderMsg.append("Please choose new image to update<br/>");
		} else {
			
			if (!FileUploadUtil.getListExtensionImageFile().contains(fileImageContentType)) {
				buiderMsg.append("New image is invalid format<br/>");
			} else if (fileImage.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
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
		
	/**
	 * Validate ID
	 * @param id
	 * @return
	 */
	private boolean validateId() {
		
		// Check empty
		if(StringUtils.isBlank(id) || StringUtils.equals(id, "0")) {
			message = "Id is invalid<br/>";
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
	private boolean validateUpdate() {

		StringBuilder buiderMsg = new StringBuilder();

		// Validate os
		if (StringUtils.isBlank(os)) {
			buiderMsg.append("Please input OS<br/>");
		}

		// Validate link
		if (StringUtils.isBlank(href)) {
			buiderMsg.append("Please input link download<br/>");
		}

		// Only check file when the file is selected
		if (fileImage != null) {

			if (!FileUploadUtil.getListExtensionImageFile().contains(fileImageContentType)) {
				buiderMsg.append("New image is invalid format<br/>");
			} else if (fileImage.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getClassSuffix() {
		return classSuffix;
	}

	public void setClassSuffix(String classSuffix) {
		this.classSuffix = classSuffix;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public DownloadDao getDownloadDao() {
		return downloadDao;
	}

	public void setDownloadDao(DownloadDao downloadDao) {
		this.downloadDao = downloadDao;
	}

	public Download getDownload() {
		return download;
	}

	public void setDownload(Download download) {
		this.download = download;
	}

	public List<Download> getDownloadList() {
		return downloadList;
	}

	public void setDownloadList(List<Download> downloadList) {
		this.downloadList = downloadList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public String getKeepSrc() {
		return keepSrc;
	}

	public void setKeepSrc(String keepSrc) {
		this.keepSrc = keepSrc;
	}

	public List<String> getClassSuffixList() {
		return classSuffixList;
	}

	public void setClassSuffixList(List<String> classSuffixList) {
		this.classSuffixList = classSuffixList;
	}
	
}
