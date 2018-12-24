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

import com.viettel.mykid.model.Feature;
import com.viettel.mykid.ulti.DbUtil;

public class FeatureDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(FeatureDao.class);

	/**
	 * Get all feature of the product
	 * 
	 * @param connection
	 * @return featureList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Feature> getAllFeature(Connection connection) {
	
		Statement statement = null;
		ResultSet resultSet = null;
		List<Feature> featureList = new ArrayList<Feature>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(	"SELECT\r\n" + 
															"	feature.ID,\r\n" + 
															"	feature.TITLE,\r\n" + 
															"	feature.DIV_CLASS,\r\n" + 
															"	feature.HREF,\r\n" + 
															"	feature.SRC,\r\n" + 
															"	feature.H6_SUFFIX,\r\n" + 
															"	feature.POPUP,\r\n" + 
															"	feature.MAIN_STATUS\r\n" + 
															"FROM\r\n" + 
															"	feature \r\n" + 
															"	INNER JOIN product\r\n" + 
															"		ON feature.PRODUCT_ID = product.ID\r\n" + 
															"WHERE \r\n" + 
															"	feature.DEL_FLG = 0\r\n" + 
															"	AND product.DEL_FLG = 0\r\n" + 
															"	AND product.ID = (SELECT ID FROM product WHERE DEL_FLG = 0 ORDER BY ID LIMIT 1)\r\n" +
															"ORDER BY feature.ID DESC");
			while (resultSet.next()) {
				Feature feature = new Feature();
				feature.setId(resultSet.getInt("ID"));
				feature.setDivClass(resultSet.getString("DIV_CLASS"));
				feature.setMainStatus(resultSet.getInt("MAIN_STATUS"));
				feature.setH6Suffix(resultSet.getString("H6_SUFFIX"));
				feature.setTitle(resultSet.getString("TITLE"));
				feature.setHref(resultSet.getString("HREF"));
				feature.setSrc(resultSet.getString("SRC"));
				feature.setPopup(resultSet.getString("POPUP"));
				featureList.add(feature);
			}
		
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(resultSet);
		}

		return featureList;
	}
	
	/**
	 * Get feature object by id
	 * @param connection
	 * @param featureId
	 * @return
	 * @throws SQLException
	 */
	public Feature getFeatureById(Connection connection, String featureId) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM feature WHERE ID = ? AND DEL_FLG = 0");
			preparedStatement.setInt(1, Integer.parseInt(featureId.trim()));
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				Feature feature = new Feature();
				feature.setId(resultSet.getInt("ID"));
				feature.setDivClass(resultSet.getString("DIV_CLASS"));
				feature.setMainStatus(resultSet.getInt("MAIN_STATUS"));
				feature.setH6Suffix(resultSet.getString("H6_SUFFIX"));
				feature.setTitle(resultSet.getString("TITLE"));
				feature.setHref(resultSet.getString("HREF"));
				feature.setSrc(resultSet.getString("SRC"));
				feature.setPopup(resultSet.getString("POPUP"));
				return feature;
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
	 * Check exist link
	 * @param connection
	 * @param href
	 * @return
	 * @throws SQLException
	 */
	public boolean isExistLink(Connection connection, String href) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM feature WHERE HREF = ? AND DEL_FLG = 0");
			preparedStatement.setString(1, href.trim());
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
			DbUtil.closeQuietly(resultSet);
		}

		return false;
	}
	
	
	/**
	 * Check exist link when update
	 * @param connection
	 * @param href
	 * @param id
	 * @return
	 */
	public boolean isExistLink(Connection connection, String href, String id) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM feature WHERE HREF = ? AND DEL_FLG = 0 AND ID != ?");
			preparedStatement.setString(1, href.trim());
			preparedStatement.setInt(2, Integer.parseInt(id.trim()));
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
			DbUtil.closeQuietly(resultSet);
		}

		return false;
	}
	
	/**
	 * Update feature when has file upload
	 * @param connection
	 * @param divClass
	 * @param mainStatus
	 * @param h6Suffix
	 * @param title
	 * @param href
	 * @param src
	 * @param popup
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateFeatureHasFile(Connection connection, Feature feature, String src){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE feature SET DIV_CLASS = ?, HREF = ?, MAIN_STATUS = ?, H6_SUFFIX = ?, TITLE = ?, SRC = ?, POPUP = ? WHERE ID = ?");
			preparedStatement.setString(1, feature.getDivClass().trim());
			preparedStatement.setString(2, feature.getHref().trim());
			preparedStatement.setInt(3, feature.getMainStatus());
			preparedStatement.setString(4, feature.getH6Suffix().trim());
			preparedStatement.setNString(5, feature.getTitle().trim());
			preparedStatement.setString(6, src.trim());
			preparedStatement.setNString(7, feature.getPopup().trim());
			preparedStatement.setInt(8, feature.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Update feature when no upload file
	 * @param connection
	 * @param divClass
	 * @param mainStatus
	 * @param h6Suffix
	 * @param title
	 * @param href
	 * @param popup
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateFeatureNoFile(Connection connection, Feature feature){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE feature SET DIV_CLASS = ?, HREF= ?, MAIN_STATUS = ?, H6_SUFFIX = ?, TITLE = ?, POPUP = ? WHERE ID = ?");
			preparedStatement.setString(1, feature.getDivClass().trim());
			preparedStatement.setString(2, feature.getHref().trim());
			preparedStatement.setInt(3, feature.getMainStatus());
			preparedStatement.setString(4, feature.getH6Suffix().trim());
			preparedStatement.setNString(5, feature.getTitle().trim());
			preparedStatement.setNString(6, feature.getPopup().trim());
			preparedStatement.setInt(7, feature.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
		
	}
	
	/**
	 * Add feature
	 * @param connection
	 * @param productId
	 * @param divClass
	 * @param mainStatus
	 * @param h6Suffix
	 * @param title
	 * @param href
	 * @param src
	 * @param popup
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addFeature(Connection connection, int productId, Feature feature){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO feature (PRODUCT_ID , DIV_CLASS, MAIN_STATUS, H6_SUFFIX, TITLE, HREF, SRC, POPUP) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, productId);
			preparedStatement.setString(2, feature.getDivClass().trim());
			preparedStatement.setInt(3, feature.getMainStatus());
			preparedStatement.setString(4, feature.getH6Suffix().trim());
			preparedStatement.setNString(5, feature.getTitle().trim());
			preparedStatement.setString(6, feature.getHref().trim());
			preparedStatement.setString(7, feature.getSrc().trim());
			preparedStatement.setNString(8, feature.getPopup().trim());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}

	/**
	 * Delete feature
	 * @param connection
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteFeature(Connection connection, String id) {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE feature SET DEL_FLG = 1 WHERE ID = ?");
			preparedStatement.setInt(1, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
		
	}
	
}
