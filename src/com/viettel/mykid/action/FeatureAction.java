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
import com.viettel.mykid.dao.FeatureDao;
import com.viettel.mykid.dao.ProductDao;
import com.viettel.mykid.model.Feature;
import com.viettel.mykid.model.Product;
import com.viettel.mykid.ulti.ConnectDB;
import com.viettel.mykid.ulti.ConstantUtil;
import com.viettel.mykid.ulti.DbUtil;
import com.viettel.mykid.ulti.FileUploadUtil;
import com.viettel.mykid.ulti.SessionUtil;

public class FeatureAction extends ActionSupport {
	
	// ==================================================================
	//                                                           CONSTANT
	// ==================================================================
			
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(FeatureAction.class);
	private static int WIDTH_IMAGE_ICON = 129;
	private static int HEIGHT_IMAGE_ICON = 129;
	
	// ==================================================================
	//                                                         PROPERTIES
	// ==================================================================
	private String errorMessage;
	private String message;
	private String id;
	private String title;
	private String divClass;
	private String mainStatus;
	private String href;
	private String h6Suffix;
	private String popup;
	
	private File fileImage;
	private String fileImageContentType; 
	private String fileImageFileName; 
		
	private String keepSrc;
	
	private List<String> divClassList;
	private List<String> h6SuffixList;
	
	// ==================================================================
	//                                                     INITIAL OBJECT
	// ==================================================================
	private FeatureDao featureDao = new FeatureDao();
	private Feature feature = new Feature();
	private List<Feature> featureList = new ArrayList<Feature>();
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

			// Get information of the banner
			featureList = featureDao.getAllFeature(connection);

			// Set divClassList
			divClassList = new ArrayList<String>();
			divClassList.add("col-xs-12 col-sm-4 col-md-6 hide-on-medium");
			divClassList.add("col-xs-6 col-sm-4 col-lg-3 col-md-3");
			divClassList.add("col-xs-6 col-sm-4 col-md-6 hide-on-xs");
			
			// Set h6SuffixList
			h6SuffixList = new ArrayList<String>();
			h6SuffixList.add("iq-font-18 blue");
			h6SuffixList.add("iq-font-14 purple");
			h6SuffixList.add("iq-font-14 red");
			h6SuffixList.add("iq-font-14");
			
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
	public String viewFeatureDetail() {

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
			feature = featureDao.getFeatureById(connection, id);
			
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
	 * Update Feature
	 * 
	 * @return
	 */
	public String updateFeature() {

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
			
			if (featureDao.isExistLink(connection, href, id)) {
				message = "Link popup is existed in database, please input other link<br/>";
				keepParamsUpdate();
				return execute();
			}

			Feature featureUpdate = new Feature(Integer.parseInt(id), title, divClass, Integer.parseInt(mainStatus), href, h6Suffix, popup);
			if (fileImage == null) {
				featureDao.updateFeatureNoFile(connection, featureUpdate);
			} else {
				String src = ConstantUtil.IMAGES_FOLDER_FEATURE + fileImageFileName;
				featureDao.updateFeatureHasFile(connection, featureUpdate, src);

				// Copy file to body folder
				FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_FEATURE,
						ConstantUtil.PATH_IMAGES_FOLDER_FEATURE + File.separator + fileImageFileName, WIDTH_IMAGE_ICON,
						HEIGHT_IMAGE_ICON);
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
	 * Add Feature
	 * 
	 * @return
	 */
	public String addFeature() {

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
			
			// Check exist in database
			if (featureDao.isExistLink(connection, href)) {
				message = "Link popup is existed in database, please input other link<br/>";
				keepParamsAdd();
				return execute();
			}

			// Get productId
			product = productDao.getProduct(connection);

			String src = ConstantUtil.IMAGES_FOLDER_FEATURE + fileImageFileName;
			Feature featureAdd = new Feature(title, divClass, Integer.parseInt(mainStatus), href, src, h6Suffix, popup);
			featureDao.addFeature(connection, product.getId(), featureAdd);

			// Copy file to body folder
			FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_FEATURE,
					ConstantUtil.PATH_IMAGES_FOLDER_FEATURE + File.separator + fileImageFileName, WIDTH_IMAGE_ICON,
					HEIGHT_IMAGE_ICON);

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
	 * Delete Feature
	 * 
	 * @return
	 */
	public String deleteFeature() {

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

			// Delete the Feature
			featureDao.deleteFeature(connection, id);

		} catch (ClassNotFoundException | SQLException e) {
			errorMessage = e.getMessage();
			LOGGER.error(e.getMessage(), e);
			return ConstantUtil.ERROR_PAGE;
		} finally {
			DbUtil.closeQuietly(connection);;
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
		feature.setId(0);
		feature.setTitle(title);
		feature.setDivClass(divClass);
		feature.setMainStatus(StringUtils.isEmpty(mainStatus) ? 0: Integer.parseInt(mainStatus));
		feature.setHref(href);
		feature.setH6Suffix(h6Suffix);
		feature.setPopup(popup);
	}
	
	/**
	 * Keep form update
	 */
	private void keepParamsUpdate() {
		if (StringUtils.isNumeric(id)) {
			feature.setId(Integer.parseInt(id));
			feature.setSrc(keepSrc);
		}
		feature.setTitle(title);
		feature.setDivClass(divClass);
		feature.setMainStatus(StringUtils.isEmpty(mainStatus) ? 0: Integer.parseInt(mainStatus));
		feature.setHref(href);
		feature.setH6Suffix(h6Suffix);
		feature.setPopup(popup);
	}
	
	/**
	 * Validate parameter when add data
	 * @return
	 */
	private boolean validateAdd() {
		
		StringBuilder buiderMsg = new StringBuilder();
		
		// Check title is empty
		if (StringUtils.isBlank(title)) {
			buiderMsg.append("Please input title<br/>");
		}
		
		// Check title is empty
		if (StringUtils.isBlank(divClass)) {
			buiderMsg.append("Please choose div class<br/>");
		}
		
		// Check itemStatus must be 0 or 1
		if (!StringUtils.equals(mainStatus, "0") && !StringUtils.equals(mainStatus, "1")) {
			buiderMsg.append("Main status is invalid<br/>");
		}
		
		// Check title is empty
		if (StringUtils.isBlank(href)) {
			buiderMsg.append("Please input link popup<br/>");
		}
		
		if (StringUtils.isBlank(h6Suffix)) {
			buiderMsg.append("Please choose H6 suffix<br/>");
		}
		
		// Validate other parameters
		if (fileImage == null) {
			buiderMsg.append("Please choose new image to update<br/>");
		} else {
			
			if (!FileUploadUtil.getListExtensionImageFile().contains(fileImageContentType)) {
				buiderMsg.append("New image is invalid format<br/>");
			} else if (fileImage.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
				buiderMsg.append("New image is exceed 2Mb<br/>");
			}
		}
		
		// Check title is empty
		if (StringUtils.isBlank(popup)) {
			buiderMsg.append("Please input popup<br/>");
		} else if (popup.length() > 10000) {
			buiderMsg.append("Please input popup is less than or equal 10000 character<br/>");
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

		// Check title is empty
		if (StringUtils.isBlank(title)) {
			buiderMsg.append("Please input title<br/>");
		}

		// Check title is empty
		if (StringUtils.isBlank(divClass)) {
			buiderMsg.append("Please choose div class<br/>");
		}

		// Check itemStatus must be 0 or 1
		if (!StringUtils.equals(mainStatus, "0") && !StringUtils.equals(mainStatus, "1")) {
			buiderMsg.append("Main status is invalid<br/>");
		}
		
		if (StringUtils.isBlank(h6Suffix)) {
			buiderMsg.append("Please choose H6 suffix<br/>");
		}

		// Only check file when the file is selected
		if (fileImage != null) {

			if (!FileUploadUtil.getListExtensionImageFile().contains(fileImageContentType)) {
				buiderMsg.append("New image is invalid format<br/>");
			} else if (fileImage.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
				buiderMsg.append("New image is exceed 2Mb<br/>");
			}
		}

		// Check title is empty
		if (StringUtils.isBlank(popup)) {
			buiderMsg.append("Please input popup<br/>");
		} else if (popup.length() > 10000) {
			buiderMsg.append("Please input popup is less than or equal 10000 character<br/>");
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDivClass() {
		return divClass;
	}

	public void setDivClass(String divClass) {
		this.divClass = divClass;
	}

	public String getMainStatus() {
		return mainStatus;
	}

	public void setMainStatus(String mainStatus) {
		this.mainStatus = mainStatus;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getH6Suffix() {
		return h6Suffix;
	}

	public void setH6Suffix(String h6Suffix) {
		this.h6Suffix = h6Suffix;
	}

	public String getPopup() {
		return popup;
	}

	public void setPopup(String popup) {
		this.popup = popup;
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

	public FeatureDao getFeatureDao() {
		return featureDao;
	}

	public void setFeatureDao(FeatureDao featureDao) {
		this.featureDao = featureDao;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public List<Feature> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(List<Feature> featureList) {
		this.featureList = featureList;
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

	public List<String> getDivClassList() {
		return divClassList;
	}

	public void setDivClassList(List<String> divClassList) {
		this.divClassList = divClassList;
	}

	public List<String> getH6SuffixList() {
		return h6SuffixList;
	}

	public void setH6SuffixList(List<String> h6SuffixList) {
		this.h6SuffixList = h6SuffixList;
	}
}
