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
import com.viettel.mykid.dao.StructureDao;
import com.viettel.mykid.model.Product;
import com.viettel.mykid.model.Structure;
import com.viettel.mykid.ulti.ConnectDB;
import com.viettel.mykid.ulti.ConstantUtil;
import com.viettel.mykid.ulti.DbUtil;
import com.viettel.mykid.ulti.FileUploadUtil;
import com.viettel.mykid.ulti.SessionUtil;

public class StructureAction extends ActionSupport {
	
	// ==================================================================
	//                                                           CONSTANT
	// ==================================================================
			
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(StructureAction.class);
	private static int WIDTH_IMAGE_STRUCTURE = 3460;
	private static int HEIGHT_IMAGE_STRUCTURE = 1736;
	
	// ==================================================================
	//                                                         PROPERTIES
	// ==================================================================
	private String errorMessage;
	private String message;
	private String id;
	
	private File fileImage;
	private String fileImageContentType; 
	private String fileImageFileName; 
		
	// ==================================================================
	//                                                     INITIAL OBJECT
	// ==================================================================
	private StructureDao structureDao = new StructureDao();
	private Structure structure = new Structure();
	private List<Structure> structureList = new ArrayList<Structure>();
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

			// Get information of the structure product
			structureList = structureDao.getAllSrc(connection);

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
	public String viewStructureDetail() {

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
			structure = structureDao.getStructureById(connection, id);

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
	 * Update structure
	 * 
	 * @return
	 */
	public String updateStructure() {

		// Check user is ADM
		if (!SessionUtil.isAdmin()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}

		// Validate parameters
		if (!validateUpdate()) {
			return execute();
		}

		Connection connection = null;
		try {
			// Open connection and disable auto commit
			connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);

			String src = ConstantUtil.IMAGES_FOLDER_STRUCTURE + fileImageFileName;
			structureDao.updateStructure(connection, id, src);

			// Copy file to body folder
			FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_STRUCTURE,
					ConstantUtil.PATH_IMAGES_FOLDER_STRUCTURE + File.separator + fileImageFileName,
					WIDTH_IMAGE_STRUCTURE, HEIGHT_IMAGE_STRUCTURE);

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
	 * Add structure
	 * 
	 * @return
	 */
	public String addStructure() {

		// Check user is ADM
		if (!SessionUtil.isAdmin()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}

		// Validate parameter
		if (!validateAdd()) {
			return execute();
		}

		Connection connection = null;
		try {
			// Open connection and disable auto commit
			connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);

			// Get productId
			product = productDao.getProduct(connection);

			String src = ConstantUtil.IMAGES_FOLDER_STRUCTURE + fileImageFileName;
			structureDao.addStructure(connection, product.getId(), src);

			// Copy file to body folder
			FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_STRUCTURE,
					ConstantUtil.PATH_IMAGES_FOLDER_STRUCTURE + File.separator + fileImageFileName,
					WIDTH_IMAGE_STRUCTURE, HEIGHT_IMAGE_STRUCTURE);

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
	 * Delete structure
	 * 
	 * @return
	 */
	public String deleteStructure() {

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

			// Delete the structure
			structureDao.deleteStructure(connection, id);

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
	 * Validate parameter when add data
	 * @return
	 */
	private boolean validateAdd() {
		
		StringBuilder buiderMsg = new StringBuilder();
		
		// Validate other parameters
		if (fileImage == null) {
			message = "Please choose new image to update<br/>";
			return false;
		}

		if (!FileUploadUtil.getListExtensionImageFile().contains(fileImageContentType)) {
			buiderMsg.append("New image is invalid format<br/>");
		} else if (fileImage.length() > ConstantUtil.MAX_FILE_SIZE_IMAGE) {
			buiderMsg.append("New image is exceed 2Mb<br/>");
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
		
		// Validate other parameters
		if (fileImage == null) {
			buiderMsg.append("Please choose new image to update<br/>");
		} else {
			
			if (!FileUploadUtil.getListExtensionImageFile().contains(fileImageContentType)) {
				buiderMsg.append("New image is invalid format");
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

	public StructureDao getStructureDao() {
		return structureDao;
	}

	public void setStructureDao(StructureDao structureDao) {
		this.structureDao = structureDao;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public List<Structure> getStructureList() {
		return structureList;
	}

	public void setStructureList(List<Structure> structureList) {
		this.structureList = structureList;
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
}
