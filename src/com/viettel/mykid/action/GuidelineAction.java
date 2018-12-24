package com.viettel.mykid.action;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.viettel.mykid.dao.GuidelineDao;
import com.viettel.mykid.dao.ProductDao;
import com.viettel.mykid.model.Guideline;
import com.viettel.mykid.model.Product;
import com.viettel.mykid.ulti.ConnectDB;
import com.viettel.mykid.ulti.ConstantUtil;
import com.viettel.mykid.ulti.DbUtil;
import com.viettel.mykid.ulti.FileUploadUtil;
import com.viettel.mykid.ulti.SessionUtil;

public class GuidelineAction extends ActionSupport {
	
	// ==================================================================
	//                                                           CONSTANT
	// ==================================================================
			
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(GuidelineAction.class);
	private static int WIDTH_IMAGE_GUIDELINE = 800;
	private static int HEIGHT_IMAGE_GUIDELINE = 500;
	
	// ==================================================================
	//                                                         PROPERTIES
	// ==================================================================
	private String errorMessage;
	private String message;
	private String id;
	private String title;
	private String content;
	private String src;
	private String href;
	private String popupStatus;
	private String popup;
	
	private File fileImage;
	private String fileImageContentType; 
	private String fileImageFileName; 
		
	private File filePdf;
	private String filePdfContentType; 
	private String filePdfFileName; 
	
	private String keepSrc;
	
	// ==================================================================
	//                                                     INITIAL OBJECT
	// ==================================================================
	private GuidelineDao guidelineDao = new GuidelineDao();
	private Guideline guideline = new Guideline();
	private List<Guideline> guidelineList = new ArrayList<Guideline>();
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
			guidelineList = guidelineDao.getAllGuideline(connection);

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
	public String viewGuidelineDetail() {

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
			guideline = guidelineDao.getGuidelineById(connection, id);
			
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
	 * Update Guideline
	 * 
	 * @return
	 */
	public String updateGuideline() {

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
			
			// When choose popupStatus is no then must be pdf file
			if (StringUtils.equals(popupStatus, "0") && filePdf == null) {
				String pathPdf = guidelineDao.getGuidelineById(connection, id).getHref();
				
				String extension = "";
				int i = pathPdf.lastIndexOf('.');
				if (i > 0) {
					extension = pathPdf.substring(i + 1);
					if (!StringUtils.equalsIgnoreCase(extension, "pdf")) {
						message = "Please choose pdf file<br/>";
						keepParamsUpdate();
						return execute();
					}

				} else {
					message = "Please choose pdf file<br/>";
					keepParamsUpdate();
					return execute();
				}
			}
			
			// Get href when choose no popup
			if(filePdf != null) {
				href = ConstantUtil.IMAGES_FOLDER + filePdfFileName;
			} else {
				// Check exist in database
				if (guidelineDao.isExistLink(connection, href, id)) {
					message = "Link popup is existed in database, please input other link<br/>";
					keepParamsAdd();
					return execute();
				}
			}

			Guideline guidelineUpdate = new Guideline(Integer.parseInt(id), title, content, href, Integer.parseInt(popupStatus), popup);
			if (fileImage == null) {
				guidelineDao.updateGuidelineNoFile(connection, guidelineUpdate);
			} else {
				String srcGuideline = ConstantUtil.IMAGES_FOLDER_GUIDELINE + fileImageFileName;
				guidelineDao.updateGuidelineHasFile(connection, guidelineUpdate, srcGuideline);

				// Copy file to body folder
				FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_GUIDELINE,
						ConstantUtil.PATH_IMAGES_FOLDER_GUIDELINE + File.separator + fileImageFileName,
						WIDTH_IMAGE_GUIDELINE, HEIGHT_IMAGE_GUIDELINE);
			}
			
			if(filePdf != null) {
				// Copy pdf file to folder image
				FileUtils.copyFile(filePdf, new File(ConstantUtil.PATH_IMAGES_FOLDER, filePdfFileName));
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
	 * Add Guideline
	 * 
	 * @return
	 */
	public String addGuideline() {

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
			


			// Get href when choose no popup
			if(filePdf != null) {
				href = ConstantUtil.IMAGES_FOLDER + filePdfFileName;
			} else {
				
				// Check exist in database
				if (guidelineDao.isExistLink(connection, href)) {
					message = "Link popup is existed in database, please input other link<br/>";
					keepParamsAdd();
					return execute();
				}
			}
			
			// Get productId
			product = productDao.getProduct(connection);

			String srcGuideline = ConstantUtil.IMAGES_FOLDER_GUIDELINE + fileImageFileName;
			Guideline guidelineAdd = new Guideline(title, content, srcGuideline, href, Integer.parseInt(popupStatus), popup);
			guidelineDao.addGuideline(connection, product.getId(), guidelineAdd);

			// Copy file to body folder
			FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_GUIDELINE,
					ConstantUtil.PATH_IMAGES_FOLDER_GUIDELINE + File.separator + fileImageFileName,
					WIDTH_IMAGE_GUIDELINE, HEIGHT_IMAGE_GUIDELINE);
			
			if(filePdf != null) {
				// Copy pdf file to folder image
				FileUtils.copyFile(filePdf, new File(ConstantUtil.PATH_IMAGES_FOLDER, filePdfFileName));
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

		message = ConstantUtil.ADD_SUCCESS;
		return execute();
	}

	/**
	 * Delete Guideline
	 * 
	 * @return
	 */
	public String deleteGuideline() {

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

			// Delete the Guideline
			guidelineDao.deleteGuideline(connection, id);

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
		guideline.setId(0);
		guideline.setTitle(title);
		guideline.setContent(content);
		guideline.setHref(href);
		guideline.setPopupStatus(StringUtils.isEmpty(popupStatus) ? 0: Integer.parseInt(popupStatus));
		guideline.setPopup(popup);
	}
	
	/**
	 * Keep form update
	 */
	private void keepParamsUpdate() {
		if (StringUtils.isNumeric(id)) {
			guideline.setId(Integer.parseInt(id));
			guideline.setSrc(keepSrc);
		}
		guideline.setTitle(title);
		guideline.setContent(content);
		guideline.setHref(href);
		guideline.setPopupStatus(StringUtils.isEmpty(popupStatus) ? 0: Integer.parseInt(popupStatus));
		guideline.setPopup(popup);
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
		
		// Check popupStatus must be 0 or 1
		if (!StringUtils.equals(popupStatus, "0") && !StringUtils.equals(popupStatus, "1")) {
			buiderMsg.append("Popup status is invalid<br/>");
		}
		
		// Check title is href
		if (StringUtils.equals(popupStatus, "1") && StringUtils.isBlank(href)) {
			buiderMsg.append("Please input link popup<br/>");
		}
		
		if (StringUtils.equals(popupStatus, "0")) {
			
			// Validate other parameters
			if (filePdf == null) {
				buiderMsg.append("Please choose Pdf file to add<br/>");
			} else {
				
				if (!StringUtils.equalsIgnoreCase(filePdfContentType, "application/pdf")) {
					buiderMsg.append("Pdf file is invalid format<br/>");
				} else if (filePdf.length() > ConstantUtil.MAX_FILE_SIZE_PDF) {
					buiderMsg.append("Pdf file is exceed 20Mb<br/>");
				}
			}
		}
		
		// Validate other parameters
		if (fileImage == null) {
			buiderMsg.append("Please choose new image to add<br/>");
		} else {
			
			if (!FileUploadUtil.getListExtensionImageFile().contains(fileImageContentType)) {
				buiderMsg.append("New image is invalid format<br/>");
			} else if (fileImage.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
				buiderMsg.append("New image is exceed 2Mb<br/>");
			}
		}
		
		// Check content is empty
		if (StringUtils.isBlank(content)) {
			buiderMsg.append("Please input content<br/>");
		} else if (content.length() > 1000) {
			buiderMsg.append("Please input content is less than or equal 1000 character<br/>");
		}

		// Check popup is empty
		if (StringUtils.equals(popupStatus, "1") ) {
			
			if(StringUtils.isBlank(popup)) {
				buiderMsg.append("Please input popup<br/>");
			} else if (popup.length() > 10000) {
				buiderMsg.append("Please input popup is less than or equal 10000 character<br/>");
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

		// Check title is empty
		if (StringUtils.isBlank(title)) {
			buiderMsg.append("Please input title<br/>");
		}
		
		// Check popupStatus must be 0 or 1
		if (!StringUtils.equals(popupStatus, "0") && !StringUtils.equals(popupStatus, "1")) {
			buiderMsg.append("Popup status is invalid<br/>");
		}
		
		// Check title is href
		if (StringUtils.equals(popupStatus, "1") && StringUtils.isBlank(href)) {
			buiderMsg.append("Please input link popup<br/>");
		}

		if (StringUtils.equals(popupStatus, "0") && filePdf != null) {

			if (!StringUtils.equalsIgnoreCase(filePdfContentType, "application/pdf")) {
				buiderMsg.append("Pdf file is invalid format<br/>");
			} else if (filePdf.length() > ConstantUtil.MAX_FILE_SIZE_PDF) {
				buiderMsg.append("Pdf file is exceed 20Mb<br/>");
			}
		}
		
		// Only check file when the file is selected
		if (fileImage != null) {

			if (!FileUploadUtil.getListExtensionImageFile().contains(fileImageContentType)) {
				buiderMsg.append("New image is invalid format<br/>");
			} else if (fileImage.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
				buiderMsg.append("New image is exceed 2Mb<br/>");
			}
		}
		
		// Check title is content
		if (StringUtils.isBlank(content)) {
			buiderMsg.append("Please input content<br/>");
		} else if (content.length() > 1000) {
			buiderMsg.append("Please input content is less than or equal 1000 character<br/>");
		}

		// Check popup is empty
		if (StringUtils.equals(popupStatus, "1") ) {
			
			if(StringUtils.isBlank(popup)) {
				buiderMsg.append("Please input popup<br/>");
			} else if (popup.length() > 10000) {
				buiderMsg.append("Please input popup is less than or equal 10000 character<br/>");
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getPopupStatus() {
		return popupStatus;
	}

	public void setPopupStatus(String popupStatus) {
		this.popupStatus = popupStatus;
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

	public GuidelineDao getGuidelineDao() {
		return guidelineDao;
	}

	public void setGuidelineDao(GuidelineDao guidelineDao) {
		this.guidelineDao = guidelineDao;
	}

	public Guideline getGuideline() {
		return guideline;
	}

	public void setGuideline(Guideline guideline) {
		this.guideline = guideline;
	}

	public List<Guideline> getGuidelineList() {
		return guidelineList;
	}

	public void setGuidelineList(List<Guideline> guidelineList) {
		this.guidelineList = guidelineList;
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

	public File getFilePdf() {
		return filePdf;
	}

	public void setFilePdf(File filePdf) {
		this.filePdf = filePdf;
	}

	public String getFilePdfContentType() {
		return filePdfContentType;
	}

	public void setFilePdfContentType(String filePdfContentType) {
		this.filePdfContentType = filePdfContentType;
	}

	public String getFilePdfFileName() {
		return filePdfFileName;
	}

	public void setFilePdfFileName(String filePdfFileName) {
		this.filePdfFileName = filePdfFileName;
	}
}
