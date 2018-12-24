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

import com.viettel.mykid.model.Color;
import com.viettel.mykid.ulti.DbUtil;

public class ColorDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(ColorDao.class);
	
	/**
	 * Get all color of the product
	 * @param connection
	 * @return colorList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Color> getAllColor(Connection connection){
		
		Statement statement = null;
		ResultSet resultSet = null;
		List<Color> colorList = new ArrayList<Color>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(	"SELECT\r\n" + 
												"	color.ID,\r\n" + 
												"	color.CONTENT,\r\n" + 
												"   color.SRC\r\n" + 
												"FROM\r\n" + 
												"	color \r\n" + 
												"	INNER JOIN product\r\n" + 
												"		ON color.PRODUCT_ID = product.ID\r\n" + 
												"WHERE\r\n" + 
												"	color.DEL_FLG = 0\r\n" + 
												"	AND product.DEL_FLG = 0\r\n" + 
												"	AND product.ID = (SELECT ID FROM product WHERE DEL_FLG = 0 ORDER BY ID LIMIT 1)\r\n" + 
												"ORDER BY color.ID DESC");
			int id;
			String content, src;
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
				content = resultSet.getString("CONTENT");
				src = resultSet.getString("SRC");
				colorList.add(new Color(id, content, src));
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(resultSet);
		}
		
		return colorList;
	}
	
	/**
	 * Get Color object by Id
	 * @param connection
	 * @param colorId
	 * @return
	 * @throws SQLException
	 */
	public Color getColorById(Connection connection, String colorId){
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM color WHERE id = ?  AND DEL_FLG = 0");
			preparedStatement.setInt(1, Integer.parseInt(colorId.trim()));
			resultSet = preparedStatement.executeQuery();
			
			int id;
			String src, content;
			if (resultSet.next()) {
				id = resultSet.getInt("ID");
				content = resultSet.getString("CONTENT");
				src = resultSet.getString("SRC");
				return new Color(id, content, src);
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
	 * Update color when has upload file
	 * @param connection
	 * @param bodyId
	 * @param src
	 * @param content
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateColorHasFile(Connection connection, String id, String src, String content){
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE color SET CONTENT = ?, SRC= ? WHERE ID = ?");
			preparedStatement.setNString(1, content.trim());
			preparedStatement.setNString(2, src.trim());
			preparedStatement.setInt(3, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Update color when no upload file
	 * @param connection
	 * @param bodyId
	 * @param src
	 * @param content
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateColorNoFile(Connection connection, String id, String content){
		
		String updateColorSql = "UPDATE color SET CONTENT = ? WHERE ID = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(updateColorSql);
			preparedStatement.setNString(1, content.trim());
			preparedStatement.setInt(2, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Add color
	 * @param connection
	 * @param src
	 * @param content
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addColor(Connection connection, int productId, String src, String content){
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO color(PRODUCT_ID, CONTENT, SRC) VALUES (?, ?, ?)");
			preparedStatement.setInt(1, productId);
			preparedStatement.setNString(2, content.trim());
			preparedStatement.setNString(3, src.trim());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Delete color
	 * @param connection
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteColor(Connection connection, String id){
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE color SET DEL_FLG = 1 WHERE ID = ?");
			preparedStatement.setInt(1, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
}
