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

import com.viettel.mykid.model.Guideline;
import com.viettel.mykid.ulti.DbUtil;

public class GuidelineDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(GuidelineDao.class);

	/**
	 * Get all guideline of the product
	 * @param connection
	 * @return guidelineList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Guideline> getAllGuideline(Connection connection) {
		
		Statement statement = null;
		ResultSet resultSet = null;
		List<Guideline> guidelineList = new ArrayList<Guideline>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(	"SELECT\r\n" + 
												"	guideline.ID,\r\n" + 
												"	guideline.TITLE,\r\n" + 
												"   guideline.CONTENT,\r\n" + 
												"   guideline.SRC,\r\n" + 
												"   guideline.HREF,\r\n" + 
												"   guideline.POPUP_STATUS,\r\n" + 
												"   guideline.POPUP\r\n" + 
												"FROM\r\n" + 
												"	guideline \r\n" + 
												"	INNER JOIN product\r\n" + 
												"		ON guideline.PRODUCT_ID = product.ID\r\n" + 
												"WHERE\r\n" + 
												"	guideline.DEL_FLG = 0\r\n" + 
												"	AND product.DEL_FLG = 0\r\n" + 
												"	AND product.ID = (SELECT ID FROM product WHERE DEL_FLG = 0 ORDER BY ID LIMIT 1)\r\n" +
												"ORDER BY guideline.ID DESC");
			int id, popupStatus;
			String title, content, src, href, popup;
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
				title = resultSet.getString("TITLE");
				content = resultSet.getString("CONTENT");
				src = resultSet.getString("SRC");
				href = resultSet.getString("HREF");
				popupStatus = resultSet.getInt("POPUP_STATUS");
				popup = resultSet.getString("POPUP");
				guidelineList.add(new Guideline(id, title, content, src, href, popupStatus, popup));
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(resultSet);
		}
		

		return guidelineList;
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
			preparedStatement = connection.prepareStatement("SELECT * FROM guideline WHERE HREF = ? AND DEL_FLG = 0");
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
	 * @throws SQLException
	 */
	public boolean isExistLink(Connection connection, String href, String id) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM guideline WHERE HREF = ? AND DEL_FLG = 0 AND ID != ?");
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
	 * Get Guideline object by id
	 * @param connection
	 * @param guidelineId
	 * @return
	 * @throws SQLException
	 */
	public Guideline getGuidelineById(Connection connection, String guidelineId) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM guideline WHERE ID = ? AND DEL_FLG = 0");
			preparedStatement.setInt(1, Integer.parseInt(guidelineId.trim()));
			resultSet = preparedStatement.executeQuery();

			int id, popupStatus;
			String title, content, src, href, popup;
			if (resultSet.next()) {
				id = resultSet.getInt("ID");
				title = resultSet.getString("TITLE");
				content = resultSet.getString("CONTENT");
				src = resultSet.getString("SRC");
				href = resultSet.getString("HREF");
				popupStatus = resultSet.getInt("POPUP_STATUS");
				popup = resultSet.getString("POPUP");
				return new Guideline(id, title, content, src, href, popupStatus, popup);
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
	 * Update Guideline when has file upload
	 * @param connection
	 * @param title
	 * @param content
	 * @param href
	 * @param popupStatus
	 * @param src
	 * @param popup
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateGuidelineHasFile(Connection connection, Guideline guideline, String src){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE guideline SET TITLE = ?, CONTENT = ?, POPUP_STATUS = ?, SRC = ?, HREF = ?, POPUP = ? WHERE ID = ?");
			preparedStatement.setNString(1, guideline.getTitle().trim());
			preparedStatement.setNString(2, guideline.getContent().trim());
			preparedStatement.setInt(3, guideline.getPopupStatus());
			preparedStatement.setString(4, src.trim());
			preparedStatement.setString(5, guideline.getHref().trim());
			preparedStatement.setNString(6, guideline.getPopup().trim());
			preparedStatement.setInt(7, guideline.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
		
	}
	
	/**
	 * Update Guideline when no upload file
	 * @param connection
	 * @param title
	 * @param content
	 * @param href
	 * @param popupStatus
	 * @param popup
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateGuidelineNoFile(Connection connection, Guideline guideline) {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE guideline SET TITLE = ?, CONTENT = ?, POPUP_STATUS = ?, HREF = ?, POPUP = ? WHERE ID = ?");
			preparedStatement.setNString(1, guideline.getTitle().trim());
			preparedStatement.setNString(2, guideline.getContent().trim());
			preparedStatement.setInt(3, guideline.getPopupStatus());
			preparedStatement.setString(4, guideline.getHref().trim());
			preparedStatement.setNString(5, guideline.getPopup().trim());
			preparedStatement.setInt(6, guideline.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Add Guideline
	 * @param connection
	 * @param productId
	 * @param title
	 * @param content
	 * @param href
	 * @param popupStatus
	 * @param popup
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addGuideline(Connection connection, int productId, Guideline guideline){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO guideline (PRODUCT_ID , TITLE, CONTENT, HREF, POPUP_STATUS, SRC, POPUP) VALUES (?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, productId);
			preparedStatement.setNString(2, guideline.getTitle().trim());
			preparedStatement.setNString(3, guideline.getContent().trim());
			preparedStatement.setString(4, guideline.getHref().trim());
			preparedStatement.setInt(5, guideline.getPopupStatus());
			preparedStatement.setString(6, guideline.getSrc().trim());
			preparedStatement.setNString(7, guideline.getPopup().trim());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}

	/**
	 * Delete Guideline
	 * @param connection
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteGuideline(Connection connection, String id){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE guideline SET DEL_FLG = 1 WHERE ID = ?");
			preparedStatement.setInt(1, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
		 
	}
	
}
