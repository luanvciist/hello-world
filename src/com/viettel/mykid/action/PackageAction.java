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
import com.viettel.mykid.dao.PackageDao;
import com.viettel.mykid.dao.ProductDao;
import com.viettel.mykid.model.Package;
import com.viettel.mykid.model.Product;
import com.viettel.mykid.ulti.ConnectDB;
import com.viettel.mykid.ulti.ConstantUtil;
import com.viettel.mykid.ulti.DbUtil;
import com.viettel.mykid.ulti.FileUploadUtil;
import com.viettel.mykid.ulti.SessionUtil;

public class PackageAction extends ActionSupport {
	
	// ==================================================================
	//                                                           CONSTANT
	// ==================================================================
			
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(PackageAction.class);
	private static int WIDTH_IMAGE_BACKGROUND = 1920;
	private static int HEIGHT_IMAGE_BACKGROUND = 1080;
	
	// ==================================================================
	//                                                         PROPERTIES
	// ==================================================================
	private String errorMessage;
	private String message;
	private String id;
	private String title;
	private String titleDetail;
	private String itemStatus;
	private String colorSuffix;
	private String content;
	
	private File fileImage;
	private String fileImageContentType; 
	private String fileImageFileName; 
		
	private String keepSrc;
	private List<String> colorSuffixList;
	
	// ==================================================================
	//                                                     INITIAL OBJECT
	// ==================================================================
	private PackageDao packageDao = new PackageDao();
	private Package packageObj = new Package();
	private List<Package> packageList = new ArrayList<Package>();
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
			packageList = packageDao.getAllPackage(connection);

			// Set colorSuffixList
			colorSuffixList = new ArrayList<String>();
			colorSuffixList.add("iq-over-blue-80");
			colorSuffixList.add("iq-over-orange-80");
			colorSuffixList.add("iq-over-green-80");
			colorSuffixList.add("iq-over-purple-80");
			colorSuffixList.add("iq-over-yellow-80");
			colorSuffixList.add("iq-over-red-80");
			
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
	public String viewPackageDetail() {

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
			packageObj = packageDao.getPackageById(connection, id);
			
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
	 * Update Package
	 * 
	 * @return
	 */
	public String updatePackage() {

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

			Package packageUpdate = new Package(Integer.parseInt(id), Integer.parseInt(itemStatus), colorSuffix, title, titleDetail, content);
			if (fileImage == null) {
				packageDao.updatePackageNoFile(connection, packageUpdate);
			} else {
				String src = ConstantUtil.IMAGES_FOLDER_PACKAGE + fileImageFileName;
				packageDao.updatePackageHasFile(connection, packageUpdate, src);

				// Copy file to body folder
				FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_PACKAGE,
						ConstantUtil.PATH_IMAGES_FOLDER_PACKAGE + File.separator + fileImageFileName,
						WIDTH_IMAGE_BACKGROUND, HEIGHT_IMAGE_BACKGROUND);
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
	 * Add Package
	 * 
	 * @return
	 */
	public String addPackage() {

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

			String src = ConstantUtil.IMAGES_FOLDER_PACKAGE + fileImageFileName;
			Package packageAdd = new Package(Integer.parseInt(itemStatus), colorSuffix, src, title, titleDetail, content);
			packageDao.addPackage(connection, product.getId(), packageAdd);

			// Copy file to body folder
			FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_PACKAGE,
					ConstantUtil.PATH_IMAGES_FOLDER_PACKAGE + File.separator + fileImageFileName,
					WIDTH_IMAGE_BACKGROUND, HEIGHT_IMAGE_BACKGROUND);

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
	 * Delete Package
	 * 
	 * @return
	 */
	public String deletePackage() {

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

			// Delete the Package
			packageDao.deletePackage(connection, id);

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
		packageObj.setId(0);
		packageObj.setTitle(title);
		packageObj.setTitleDetail(titleDetail);
		packageObj.setItemStatus(StringUtils.isEmpty(itemStatus) ? 0: Integer.parseInt(itemStatus));
		packageObj.setColorSuffix(colorSuffix);
		packageObj.setContent(content);
	}
	
	/**
	 * Keep form update
	 */
	private void keepParamsUpdate() {
		if (StringUtils.isNumeric(id)) {
			packageObj.setId(Integer.parseInt(id));
			packageObj.setBackgroundUrl(keepSrc);
		}
		packageObj.setTitle(title);
		packageObj.setTitleDetail(titleDetail);
		packageObj.setItemStatus(StringUtils.isEmpty(itemStatus) ? 0: Integer.parseInt(itemStatus));
		packageObj.setColorSuffix(colorSuffix);
		packageObj.setContent(content);
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
		if (StringUtils.isBlank(titleDetail)) {
			buiderMsg.append("Please input title detail<br/>");
		}

		// Check itemStatus must be 0 or 1
		if (!StringUtils.equals(itemStatus, "0") && !StringUtils.equals(itemStatus, "1")) {
			buiderMsg.append("Item status is invalid<br/>");
		}

		// Check title is colorSuffix
		if (StringUtils.isBlank(colorSuffix)) {
			buiderMsg.append("Please choose color suffix<br/>");
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
		if (StringUtils.isBlank(content)) {
			buiderMsg.append("Please input content<br/>");
		} else if (content.length() > 500) {
			buiderMsg.append("Please input content is less than or equal 500 character<br/>");
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
		if (StringUtils.isBlank(titleDetail)) {
			buiderMsg.append("Please input title detail<br/>");
		}

		// Check itemStatus must be 0 or 1
		if (!StringUtils.equals(itemStatus, "0") && !StringUtils.equals(itemStatus, "1")) {
			buiderMsg.append("Item status is invalid<br/>");
		}

		// Check title is colorSuffix
		if (StringUtils.isBlank(colorSuffix)) {
			buiderMsg.append("Please choose color suffix<br/>");
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
		if (StringUtils.isBlank(content)) {
			buiderMsg.append("Please input content<br/>");
		} else if (content.length() > 500) {
			buiderMsg.append("Please input content is less than or equal 500 character<br/>");
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

	public String getTitleDetail() {
		return titleDetail;
	}

	public void setTitleDetail(String titleDetail) {
		this.titleDetail = titleDetail;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getColorSuffix() {
		return colorSuffix;
	}

	public void setColorSuffix(String colorSuffix) {
		this.colorSuffix = colorSuffix;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public PackageDao getPackageDao() {
		return packageDao;
	}

	public void setPackageDao(PackageDao packageDao) {
		this.packageDao = packageDao;
	}

	public Package getPackageObj() {
		return packageObj;
	}

	public void setPackageObj(Package packageObj) {
		this.packageObj = packageObj;
	}

	public List<Package> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<Package> packageList) {
		this.packageList = packageList;
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

	public List<String> getColorSuffixList() {
		return colorSuffixList;
	}

	public void setColorSuffixList(List<String> colorSuffixList) {
		this.colorSuffixList = colorSuffixList;
	}
}
