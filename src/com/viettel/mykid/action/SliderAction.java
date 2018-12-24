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
import com.viettel.mykid.dao.ProductDao;
import com.viettel.mykid.dao.SliderDao;
import com.viettel.mykid.model.Product;
import com.viettel.mykid.model.Slider;
import com.viettel.mykid.ulti.ConnectDB;
import com.viettel.mykid.ulti.ConstantUtil;
import com.viettel.mykid.ulti.DbUtil;
import com.viettel.mykid.ulti.FileUploadUtil;
import com.viettel.mykid.ulti.SessionUtil;

public class SliderAction extends ActionSupport {
	
	// ==================================================================
	//                                                           CONSTANT
	// ==================================================================
			
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(SliderAction.class);
	private static int WIDTH_IMAGE_SLIDER = 1280;
	private static int HEIGHT_IMAGE_SLIDER = 900;
	
	// ==================================================================
	//                                                         PROPERTIES
	// ==================================================================
	private String errorMessage;
	private String message;
	private String id;
	private String sliderSuffix;
	private String keepSrc;
	
	private File fileImage;
	private String fileImageContentType; 
	private String fileImageFileName; 
	private List<String> sliderSuffixList;
		
	// ==================================================================
	//                                                     INITIAL OBJECT
	// ==================================================================
	private SliderDao sliderDao = new SliderDao();
	private Slider slider = new Slider();
	private List<Slider> sliderList = new ArrayList<Slider>();
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

		// Check user is ADM or Manager
		if (!SessionUtil.isManager()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}

		Connection connection = null;
		try {
			// Open connect to database
			connection = ConnectDB.getConnection();

			// Get information of the banner
			sliderList = sliderDao.getAllSlider(connection);

			// Set sliderSuffixList
			sliderSuffixList = new ArrayList<String>();
			sliderSuffixList.add("active");
			sliderSuffixList.add("proactive");
			sliderSuffixList.add("proactivede");
			sliderSuffixList.add("preactive");
			
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
	public String viewSliderDetail() {

		// Check user is ADM or Manager
		if (!SessionUtil.isManager()) {
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
			slider = sliderDao.getSliderById(connection, id);

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
	 * Update slider
	 * 
	 * @return
	 */
	public String updateSlider() {

		// Check user is ADM or Manager
		if (!SessionUtil.isManager()) {
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
				sliderDao.updateSliderNoFile(connection, id, sliderSuffix);
			} else {
				String src = ConstantUtil.IMAGES_FOLDER_SLIDER + fileImageFileName;
				sliderDao.updateSliderHasFile(connection, id, src, sliderSuffix);

				// Copy file to body folder
				FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_SLIDER,
						ConstantUtil.PATH_IMAGES_FOLDER_SLIDER + File.separator + fileImageFileName, WIDTH_IMAGE_SLIDER,
						HEIGHT_IMAGE_SLIDER);
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
	 * Add slider
	 * 
	 * @return
	 */
	public String addSlider() {

		// Check user is ADM or Manager
		if (!SessionUtil.isManager()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}

		// Validate parameter
		if (!validateAdd()) {
			
			// Keep data in form
			slider.setSliderSuffix(sliderSuffix);
			
			return execute();
		}

		Connection connection = null;
		try {
			// Open connection and disable auto commit
			connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);

			// Get productId
			product = productDao.getProduct(connection);

			String src = ConstantUtil.IMAGES_FOLDER_SLIDER + fileImageFileName;
			sliderDao.addSlider(connection, product.getId(), src, sliderSuffix);

			// Copy file to body folder
			FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_SLIDER,
					ConstantUtil.PATH_IMAGES_FOLDER_SLIDER + File.separator + fileImageFileName, WIDTH_IMAGE_SLIDER,
					HEIGHT_IMAGE_SLIDER);

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
	 * Delete slider
	 * 
	 * @return
	 */
	public String deleteSlider() {

		// Check user is ADM or Manager
		if (!SessionUtil.isManager()) {
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

			// Delete the slider
			sliderDao.deleteSlider(connection, id);

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
	 * Keep form update
	 */
	private void keepParamsUpdate() {
		if (StringUtils.isNumeric(id)) {
			slider.setId(Integer.parseInt(id));
			slider.setSrc(keepSrc);
		}
		slider.setSliderSuffix(sliderSuffix);
	}
	
	/**
	 * Validate parameter when add data
	 * @return
	 */
	private boolean validateAdd() {
		
		StringBuilder buiderMsg = new StringBuilder();
		
		if(StringUtils.isBlank(sliderSuffix)) {
			buiderMsg.append("Please choose slider suffix<br/>");
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
		
		if(StringUtils.isBlank(sliderSuffix)) {
			buiderMsg.append("Please choose slider suffix<br/>");
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

	public SliderDao getSliderDao() {
		return sliderDao;
	}

	public void setSliderDao(SliderDao sliderDao) {
		this.sliderDao = sliderDao;
	}

	public Slider getSlider() {
		return slider;
	}

	public void setSlider(Slider slider) {
		this.slider = slider;
	}

	public List<Slider> getSliderList() {
		return sliderList;
	}

	public void setSliderList(List<Slider> sliderList) {
		this.sliderList = sliderList;
	}

	public String getSliderSuffix() {
		return sliderSuffix;
	}

	public void setSliderSuffix(String sliderSuffix) {
		this.sliderSuffix = sliderSuffix;
	}

	public List<String> getSliderSuffixList() {
		return sliderSuffixList;
	}

	public void setSliderSuffixList(List<String> sliderSuffixList) {
		this.sliderSuffixList = sliderSuffixList;
	}

	public String getKeepSrc() {
		return keepSrc;
	}

	public void setKeepSrc(String keepSrc) {
		this.keepSrc = keepSrc;
	}
}
