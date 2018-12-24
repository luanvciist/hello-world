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
import com.viettel.mykid.dao.BannerDao;
import com.viettel.mykid.dao.BodyDao;
import com.viettel.mykid.dao.ColorDao;
import com.viettel.mykid.dao.DownloadDao;
import com.viettel.mykid.dao.FeatureDao;
import com.viettel.mykid.dao.GuidelineDao;
import com.viettel.mykid.dao.PackageDao;
import com.viettel.mykid.dao.ProductDao;
import com.viettel.mykid.dao.SliderDao;
import com.viettel.mykid.dao.StructureDao;
import com.viettel.mykid.model.Banner;
import com.viettel.mykid.model.Color;
import com.viettel.mykid.model.Download;
import com.viettel.mykid.model.Feature;
import com.viettel.mykid.model.Guideline;
import com.viettel.mykid.model.Package;
import com.viettel.mykid.model.Product;
import com.viettel.mykid.model.Slider;
import com.viettel.mykid.model.Structure;
import com.viettel.mykid.ulti.ConnectDB;
import com.viettel.mykid.ulti.ConstantUtil;
import com.viettel.mykid.ulti.DbUtil;
import com.viettel.mykid.ulti.FileUploadUtil;
import com.viettel.mykid.ulti.SessionUtil;

public class ProductAction extends ActionSupport {

	// ==================================================================
	//                                                           CONSTANT
	// ==================================================================
	
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = Logger.getLogger(ProductAction.class);
	private static int WIDTH_IMAGE_SPECIFICATION = 460;
	private static int HEIGHT_IMAGE_SPECIFICATION = 545;

	// ==================================================================
	//                                                         PROPERTIES
	// ==================================================================
	private String errorMessage;
	private String message;
	private String srcImage1;
	private String srcImage2;
	private String srcImage3;

	private String id;
	private String productName;
	private String productContent;
	private String advantageContent;
	private String specificationContent;
	
	private File fileImage;
	private String fileImageContentType; 
	private String fileImageFileName; 
	
	private String keepSrc;
	
	// ==================================================================
	//                                                     INITIAL OBJECT
	// ==================================================================

	private BannerDao bannerDao = new BannerDao();
	private BodyDao bodyDao = new BodyDao();
	private ProductDao productDao = new ProductDao();
	private StructureDao structureDao = new StructureDao();
	private FeatureDao featureDao = new FeatureDao();
	private PackageDao packageDao = new PackageDao();
	private DownloadDao downloadDao = new DownloadDao();
	private SliderDao sliderDao = new SliderDao();
	private ColorDao colorDao = new ColorDao();
	private GuidelineDao guidelineDao = new GuidelineDao();
	private Banner banner = new Banner();
	private List<String> bodyList = new ArrayList<String>();
	private Product product = new Product();
	private List<Structure> structureList = new ArrayList<Structure>();
	private List<Feature> featureList = new ArrayList<Feature>();
	private List<Package> packageList = new ArrayList<Package>();
	private List<Download> downloadList = new ArrayList<Download>();
	private List<Slider> sliderList = new ArrayList<Slider>();
	private List<Color> colorList = new ArrayList<Color>();
	private List<Guideline> guidelineList = new ArrayList<Guideline>();

	// ==================================================================
	//                                                     EXECUTE METHOD
	// ==================================================================

	/**
	 * Get all data display on the screen
	 */
	@Override
	public String execute() {
		
		Connection connection = null;
		try {
			// Open connect to database
			connection = ConnectDB.getConnection();

			// Get information of the banner
			banner = bannerDao.getBanner(connection);
			if (banner == null) {
				errorMessage = "Missing banner in database";
				LOGGER.info("Not exist data in table banner");
				return ConstantUtil.ERROR_PAGE;
			}

			// Get list path of images in the body web
			bodyList = bodyDao.getAllSrc(connection);
			if (bodyList.isEmpty() || bodyList.size() < 3) {
				errorMessage = "Missing image in database";
				LOGGER.info("Not exist data or missing data in table body");
				return ConstantUtil.ERROR_PAGE;
			}
			srcImage1 = bodyList.get(0);
			srcImage2 = bodyList.get(1);
			srcImage3 = bodyList.get(2);

			// Get information of the product
			product = productDao.getProduct(connection);
			if (product == null) {
				errorMessage = "Missing product in database";
				LOGGER.info("Not exist data in table product");
				return ConstantUtil.ERROR_PAGE;
			}

			structureList = structureDao.getAllSrc(connection);
			if (structureList.isEmpty()) {
				errorMessage = "Missing image in database";
				LOGGER.info("Not exist data or missing data in table structure");
				return ConstantUtil.ERROR_PAGE;
			}

			// Get all feature of the product
			featureList = featureDao.getAllFeature(connection);
			if (featureList.isEmpty()) {
				errorMessage = "Missing feature in database";
				LOGGER.info("Not exist data in table feature");
				return ConstantUtil.ERROR_PAGE;
			}

			// Get all package of the product
			packageList = packageDao.getAllPackage(connection);
			if (packageList.isEmpty()) {
				errorMessage = "Missing package in database";
				LOGGER.info("Not exist data in table package");
				return ConstantUtil.ERROR_PAGE;
			}
			
			// Get all download of the product
			downloadList = downloadDao.getAllDownload(connection);
			if (downloadList.isEmpty()) {
				errorMessage = "Missing information app download in database";
				LOGGER.info("Not exist data in table download");
				return ConstantUtil.ERROR_PAGE;
			}

			// Get all image in slide
			sliderList = sliderDao.getAllSlider(connection);
			if (sliderList.isEmpty()) {
				errorMessage = "Missing slider in database";
				LOGGER.info("Not exist data in table slider");
				return ConstantUtil.ERROR_PAGE;
			}

			// Get all color of the product
			colorList = colorDao.getAllColor(connection);
			if (colorList.isEmpty()) {
				errorMessage = "Missing color type in database";
				LOGGER.info("Not exist data in table color");
				return ConstantUtil.ERROR_PAGE;
			}

			// Get all guideline of the product
			guidelineList = convertGuidelineList(guidelineDao.getAllGuideline(connection));
			if (guidelineList.isEmpty()) {
				errorMessage = "Missing manual in database";
				LOGGER.info("Not exist data in table manual");
				return ConstantUtil.ERROR_PAGE;
			}
		} catch (Exception e) {
			errorMessage = e.getMessage();
			LOGGER.error(e.getMessage(), e);
			return ConstantUtil.ERROR_PAGE;
		} finally {
			DbUtil.closeQuietly(connection);
		}

		return ConstantUtil.SUCCESS_PAGE;
	}
	
	/**
	 * Get information of the product
	 * @return
	 */
	public String getProductInfo() {

		// Check user is ADM or Manager
		if (!SessionUtil.isManager()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}

		Connection connection = null;
		try {
			// Open connect to database
			connection = ConnectDB.getConnection();

			product = productDao.getProduct(connection);
			if (product == null) {
				errorMessage = "Missing product in database";
				LOGGER.info("Not exist data in table product");
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
	
	public String updateIntroduce() {

		// Check user is ADM or Manager
		if (!SessionUtil.isManager()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}
		
		Connection connection = null;
		try {
			// Open connect to database
			connection = ConnectDB.getConnection();
			
			// Validate product information
			if(!validateIntroduce()) {
				
				// Keep data in form
				Product productForError = productDao.getProduct(connection);
				product.setId(productForError.getId());
				product.setProductName(productName);
				product.setProductContent(productContent);
				return ConstantUtil.SUCCESS_PAGE;
			}
			
			// Update introduce
			productDao.updateIntroduce(connection, productName, productContent, id);
			
		} catch (ClassNotFoundException | SQLException e) {
			errorMessage = e.getMessage();
			LOGGER.error(e.getMessage(), e);
			return ConstantUtil.ERROR_PAGE;
		} finally {
			DbUtil.closeQuietly(connection);
		}

		
		message = ConstantUtil.UPDATE_SUCCESS;
		return getProductInfo();
	}
	
	public String updateAdvantage() {

		// Check user is ADM
		if (!SessionUtil.isAdmin()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}
		
		Connection connection = null;
		try {
			// Open connect to database
			connection = ConnectDB.getConnection();
			
			// Validate product information
			if(!validateAdvantage()) {
				
				// Keep data in form
				Product productForError = productDao.getProduct(connection);
				product.setId(productForError.getId());
				product.setAdvantageContent(advantageContent);
				return ConstantUtil.SUCCESS_PAGE;
			}
			
			// Update introduce
			productDao.updateAdvantage(connection, advantageContent, id);
			
		} catch (ClassNotFoundException | SQLException e) {
			errorMessage = e.getMessage();
			LOGGER.error(e.getMessage(), e);
			return ConstantUtil.ERROR_PAGE;
		} finally {
			DbUtil.closeQuietly(connection);
		}

		
		message = ConstantUtil.UPDATE_SUCCESS;
		return getProductInfo();
	}
	
	
	public String updateSpecification() {

		// Check user is ADM
		if (!SessionUtil.isAdmin()) {
			errorMessage = ConstantUtil.MUST_LOGIN;
			return ConstantUtil.LOGIN_PAGE;
		}
		
		Connection connection = null;
		try {
			// Open connection and disable auto commit
			connection = ConnectDB.getConnection();
			connection.setAutoCommit(false);
			
			// Validate Specification
			if(!validateSpecification()) {
				
				// Keep data in form
				Product productForError = productDao.getProduct(connection);
				product.setId(productForError.getId());
				product.setSpecificationSrc(keepSrc);
				product.setSpecificationContent(specificationContent);
				return ConstantUtil.SUCCESS_PAGE;
			}

			if (fileImage == null) {
				productDao.updateSpecificationNoFile(connection, specificationContent, id);
			} else {
				String src = ConstantUtil.IMAGES_FOLDER_PRODUCT + fileImageFileName;
				productDao.updateSpecificationHasFile(connection, src, specificationContent, id);

				// Copy file to body folder
				FileUploadUtil.resize(fileImage, ConstantUtil.PATH_IMAGES_FOLDER_PRODUCT,
						ConstantUtil.PATH_IMAGES_FOLDER_PRODUCT + File.separator + fileImageFileName,
						WIDTH_IMAGE_SPECIFICATION, HEIGHT_IMAGE_SPECIFICATION);
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
		return getProductInfo();
	}
	
	// ==================================================================
	//                                                     PRIVATE METHOD
	// ==================================================================
	
	private boolean validateIntroduce() {

		StringBuilder buiderMsg = new StringBuilder();
		
		// Check productName is empty
		if (StringUtils.isBlank(productName)) {
			buiderMsg.append("Please input name<br/>");
		}

		// Check productContent is empty
		if (StringUtils.isBlank(productContent)) {
			buiderMsg.append("Please input introduce<br/>");
		} else if (productContent.length() > 3500) {
			buiderMsg.append("Please input introduce is less than or equal 3500 character<br/>");
		}

		// If has error then set value for message and return false
		if (!StringUtils.isEmpty(buiderMsg)) {
			message = buiderMsg.toString();
			return false;
		}

		return true;
	}

	private boolean validateAdvantage() {

		StringBuilder buiderMsg = new StringBuilder();
		
		// Check productName is empty
		if (StringUtils.isBlank(advantageContent)) {
			buiderMsg.append("Please input advantage<br/>");
		} else if (advantageContent.length() > 3000) {
			buiderMsg.append("Please input advantage is less than or equal 3000 character<br/>");
		}
		
		// If has error then set value for message and return false
		if (!StringUtils.isEmpty(buiderMsg)) {
			message = buiderMsg.toString();
			return false;
		}

		return true;
	}
	
	private boolean validateSpecification() {

		StringBuilder buiderMsg = new StringBuilder();
		
		// Check specificationContent is empty
		if (StringUtils.isBlank(specificationContent)) {
			buiderMsg.append("Please input specification content<br/>");
		} else if (specificationContent.length() > 1000) {
			buiderMsg.append("Please input specification is less than or equal 1000 character<br/>");
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
	//                                                     PRIVATE METHOD
	// ==================================================================
	
	/**
	 * Replace character fro 401 to ...
	 * @param guidelineList
	 * @return
	 */
	private List<Guideline> convertGuidelineList(List<Guideline> guidelineList){
		
		List<Guideline> changedlist = new ArrayList<Guideline>();
		
		for (Guideline guideline : guidelineList) {
			
			if (guideline.getContent().length() >= 400) {
				guideline.setContent(guideline.getContent().substring(0, 400) + ConstantUtil.DOTS);
			}
			changedlist.add(guideline);
		}
		
		return changedlist;
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

	public String getSrcImage1() {
		return srcImage1;
	}

	public void setSrcImage1(String srcImage1) {
		this.srcImage1 = srcImage1;
	}

	public String getSrcImage2() {
		return srcImage2;
	}

	public void setSrcImage2(String srcImage2) {
		this.srcImage2 = srcImage2;
	}

	public String getSrcImage3() {
		return srcImage3;
	}

	public void setSrcImage3(String srcImage3) {
		this.srcImage3 = srcImage3;
	}

	public BannerDao getBannerDao() {
		return bannerDao;
	}

	public void setBannerDao(BannerDao bannerDao) {
		this.bannerDao = bannerDao;
	}

	public BodyDao getBodyDao() {
		return bodyDao;
	}

	public void setBodyDao(BodyDao bodyDao) {
		this.bodyDao = bodyDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public StructureDao getStructureDao() {
		return structureDao;
	}

	public void setStructureDao(StructureDao structureDao) {
		this.structureDao = structureDao;
	}

	public FeatureDao getFeatureDao() {
		return featureDao;
	}

	public void setFeatureDao(FeatureDao featureDao) {
		this.featureDao = featureDao;
	}

	public PackageDao getPackageDao() {
		return packageDao;
	}

	public void setPackageDao(PackageDao packageDao) {
		this.packageDao = packageDao;
	}

	public DownloadDao getDownloadDao() {
		return downloadDao;
	}

	public void setDownloadDao(DownloadDao downloadDao) {
		this.downloadDao = downloadDao;
	}

	public SliderDao getSliderDao() {
		return sliderDao;
	}

	public void setSliderDao(SliderDao sliderDao) {
		this.sliderDao = sliderDao;
	}

	public ColorDao getColorDao() {
		return colorDao;
	}

	public void setColorDao(ColorDao colorDao) {
		this.colorDao = colorDao;
	}

	public GuidelineDao getGuidelineDao() {
		return guidelineDao;
	}

	public void setGuidelineDao(GuidelineDao guidelineDao) {
		this.guidelineDao = guidelineDao;
	}

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	public List<String> getBodyList() {
		return bodyList;
	}

	public void setBodyList(List<String> bodyList) {
		this.bodyList = bodyList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Structure> getStructureList() {
		return structureList;
	}

	public void setStructureList(List<Structure> structureList) {
		this.structureList = structureList;
	}

	public List<Feature> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(List<Feature> featureList) {
		this.featureList = featureList;
	}

	public List<Package> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<Package> packageList) {
		this.packageList = packageList;
	}

	public List<Download> getDownloadList() {
		return downloadList;
	}

	public void setDownloadList(List<Download> downloadList) {
		this.downloadList = downloadList;
	}

	public List<Slider> getSliderList() {
		return sliderList;
	}

	public void setSliderList(List<Slider> sliderList) {
		this.sliderList = sliderList;
	}

	public List<Color> getColorList() {
		return colorList;
	}

	public void setColorList(List<Color> colorList) {
		this.colorList = colorList;
	}

	public List<Guideline> getGuidelineList() {
		return guidelineList;
	}

	public void setGuidelineList(List<Guideline> guidelineList) {
		this.guidelineList = guidelineList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getProductContent() {
		return productContent;
	}

	public void setProductContent(String productContent) {
		this.productContent = productContent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAdvantageContent() {
		return advantageContent;
	}

	public void setAdvantageContent(String advantageContent) {
		this.advantageContent = advantageContent;
	}

	public String getSpecificationContent() {
		return specificationContent;
	}

	public void setSpecificationContent(String specificationContent) {
		this.specificationContent = specificationContent;
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
