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

import com.viettel.mykid.model.Body;
import com.viettel.mykid.ulti.DbUtil;

public class BodyDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(BodyDao.class);
	
	/**
	 * Get list path of images in the body web
	 * @param connection
	 * @return srcList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<String> getAllSrc(Connection connection){
		
		Statement statement = null;
		ResultSet resultSet = null;
		List<String> srcList = new ArrayList<String>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT SRC FROM body WHERE DEL_FLG = 0 ORDER BY ID LIMIT 3");
			
			while (resultSet.next()) {
				srcList.add(resultSet.getString("SRC"));

			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(resultSet);
		}
		
		return srcList;
	}
	
	/**
	 * Get all image in body
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public List<Body> getBodyList(Connection connection){
		
		Statement statement = null;
		ResultSet resultSet = null;
		List<Body> bodyList = new ArrayList<Body>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM body WHERE DEL_FLG = 0 ORDER BY ID LIMIT 3");
			
			int id;
			String src;
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
				src = resultSet.getString("SRC");
				bodyList.add(new Body(id, src));
			}
			
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(resultSet);
		}
		
		return bodyList;
	}
	
	/**
	 * Get body object by id
	 * @param connection
	 * @param bodyId
	 * @return Body
	 * @throws SQLException
	 */
	public Body getBodyById(Connection connection, String bodyId){
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM body WHERE id = ? AND DEL_FLG = 0");
			preparedStatement.setInt(1, Integer.parseInt(bodyId.trim()));
			resultSet = preparedStatement.executeQuery();
			
			int id;
			String src;
			if (resultSet.next()) {
				id = resultSet.getInt("ID");
				src = resultSet.getString("SRC");
				return new Body(id, src);
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
	 * Update body
	 * @param connection
	 * @param bodyId
	 * @param src
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateBody(Connection connection, String bodyId, String src){
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE body SET SRC= ? WHERE ID = ?");
			preparedStatement.setString(1, src.trim());
			preparedStatement.setInt(2, Integer.parseInt(bodyId.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
		
	}
}
