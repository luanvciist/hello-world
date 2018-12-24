package com.viettel.mykid.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.viettel.mykid.model.Package;
import com.viettel.mykid.ulti.DbUtil;

public class PackageDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(PackageDao.class);
	
	/**
	 * Get all package of the product
	 * @param connection
	 * @return packageList
	 * @throws SQLException
	 */
	public List<Package> getAllPackage(Connection connection){
		
		Statement statement = null;
		ResultSet resultSet = null;
		List<Package> packageList = new ArrayList<Package>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(	"SELECT\r\n" + 
												"	package.ID,\r\n" + 
												"	package.ITEM_STATUS,\r\n" + 
												"	package.COLOR_SUFFIX,\r\n" + 
												"	package.BACKGROUND_URL,\r\n" + 
												"	package.TITLE,\r\n" + 
												"	package.TITLE_DETAIL,\r\n" + 
												"	package.CONTENT\r\n" + 
												"FROM\r\n" + 
												"	package \r\n" + 
												"	INNER JOIN product\r\n" + 
												"		ON package.PRODUCT_ID = product.ID\r\n" + 
												"WHERE\r\n" + 
												"	package.DEL_FLG = 0\r\n" + 
												"	AND product.DEL_FLG = 0\r\n" + 
												"	AND product.ID = (SELECT ID FROM product WHERE DEL_FLG = 0 ORDER BY ID LIMIT 1)\r\n" +
												"ORDER BY package.ID DESC");
			int id, itemStatus;
			String colorSuffix, backgroundUrl, title, titleDetail, content;
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
				itemStatus = resultSet.getInt("ITEM_STATUS");
				colorSuffix = resultSet.getString("COLOR_SUFFIX");
				backgroundUrl = resultSet.getString("BACKGROUND_URL");
				title = resultSet.getString("TITLE");
				titleDetail = resultSet.getString("TITLE_DETAIL");
				content = resultSet.getString("CONTENT");
				packageList.add(new Package(id, itemStatus, colorSuffix, backgroundUrl, title, titleDetail, content));
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(resultSet);
		}

		return packageList;
	}
	
	/**
	 * Get the package object
	 * @param connection
	 * @param packageId
	 * @return
	 * @throws SQLException
	 */
	public Package getPackageById(Connection connection, String packageId){

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM package WHERE ID = ? AND DEL_FLG = 0");
			preparedStatement.setInt(1, Integer.parseInt(packageId.trim()));
			resultSet = preparedStatement.executeQuery();

			int id, itemStatus;
			String colorSuffix, backgroundUrl, title, titleDetail, content;
			if (resultSet.next()) {
				id = resultSet.getInt("ID");
				itemStatus = resultSet.getInt("ITEM_STATUS");
				colorSuffix = resultSet.getString("COLOR_SUFFIX");
				backgroundUrl = resultSet.getString("BACKGROUND_URL");
				title = resultSet.getString("TITLE");
				titleDetail = resultSet.getString("TITLE_DETAIL");
				content = resultSet.getString("CONTENT");
				return new Package(id, itemStatus, colorSuffix, backgroundUrl, title, titleDetail, content);
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
			DbUtil.closeQuietly(resultSet);
		}

		return null;
	}
	
	/**
	 * Update package when no file
	 * @param connection
	 * @param title
	 * @param titleDetail
	 * @param itemStatus
	 * @param colorSuffix
	 * @param content
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updatePackageNoFile(Connection connection, Package packageUpdate){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE package SET TITLE = ?, TITLE_DETAIL = ?, ITEM_STATUS = ?, COLOR_SUFFIX = ?, CONTENT = ? WHERE ID = ?");
			preparedStatement.setNString(1, packageUpdate.getTitle().trim());
			preparedStatement.setNString(2, packageUpdate.getTitleDetail().trim());
			preparedStatement.setInt(3, packageUpdate.getItemStatus());
			preparedStatement.setString(4, packageUpdate.getColorSuffix().trim());
			preparedStatement.setNString(5, packageUpdate.getContent().trim());
			preparedStatement.setInt(6, packageUpdate.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Update package object when has file upload
	 * @param connection
	 * @param title
	 * @param titleDetail
	 * @param itemStatus
	 * @param colorSuffix
	 * @param backgroundUrl
	 * @param content
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updatePackageHasFile(Connection connection, Package packageUpdate, String backgroundUrl) {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE package SET TITLE = ?, TITLE_DETAIL = ?, ITEM_STATUS = ?, COLOR_SUFFIX = ?, BACKGROUND_URL=?,CONTENT = ? WHERE ID = ?");
			preparedStatement.setNString(1, packageUpdate.getTitle().trim());
			preparedStatement.setNString(2, packageUpdate.getTitleDetail().trim());
			preparedStatement.setInt(3, packageUpdate.getItemStatus());
			preparedStatement.setString(4, packageUpdate.getColorSuffix().trim());
			preparedStatement.setString(5, backgroundUrl.trim());
			preparedStatement.setNString(6, packageUpdate.getContent().trim());
			preparedStatement.setInt(7, packageUpdate.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Add package
	 * @param connection
	 * @param productId
	 * @param title
	 * @param titleDetail
	 * @param itemStatus
	 * @param colorSuffix
	 * @param backgroundUrl
	 * @param content
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addPackage(Connection connection, int productId, Package packageAdd){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO package (PRODUCT_ID, TITLE, TITLE_DETAIL, ITEM_STATUS, COLOR_SUFFIX, BACKGROUND_URL, CONTENT) VALUES (?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, productId);
			preparedStatement.setNString(2, packageAdd.getTitle().trim());
			preparedStatement.setNString(3, packageAdd.getTitleDetail().trim());
			preparedStatement.setInt(4, packageAdd.getItemStatus());
			preparedStatement.setString(5, packageAdd.getColorSuffix().trim());
			preparedStatement.setString(6, packageAdd.getBackgroundUrl().trim());
			preparedStatement.setNString(7, packageAdd.getContent().trim());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Delete the package
	 * @param connection
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deletePackage(Connection connection, String id) {
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE package SET DEL_FLG = 1 WHERE ID = ?");
			preparedStatement.setInt(1, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
}
