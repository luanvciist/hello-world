package com.viettel.mykid.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.viettel.mykid.model.Product;
import com.viettel.mykid.ulti.DbUtil;

public class ProductDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(ProductDao.class);
	
	/**
	 * Get information of the product
	 * @param connection
	 * @return Product
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Product getProduct(Connection connection){
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM product WHERE DEL_FLG = 0 ORDER BY ID LIMIT 1");
			
			int id;
			String productName, productContent, advantageContent, specificationContent, specificationSrc;
			if (resultSet.next()) {
				id = resultSet.getInt("ID");
				productName = resultSet.getString("PRODUCT_NAME");
				productContent = resultSet.getString("PRODUCT_CONTENT");
				advantageContent = resultSet.getString("ADVANTAGE_CONTENT");
				specificationContent = resultSet.getString("SPECIFICATION_CONTENT");
				specificationSrc = resultSet.getString("SPECIFICATION_SRC");
				return new Product(id, productName,productContent, advantageContent, specificationContent, specificationSrc);
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(resultSet);
		}
		
		return null;
	}
	
	/**
	 * Update introduce
	 * @param connection
	 * @param productName
	 * @param productContent
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateIntroduce(Connection connection, String productName,String productContent, String id) {
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE product SET PRODUCT_NAME = ?, PRODUCT_CONTENT = ? WHERE ID = ?");
			preparedStatement.setNString(1, productName.trim());
			preparedStatement.setNString(2, productContent.trim());
			preparedStatement.setInt(3, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Update advantage of the product
	 * @param connection
	 * @param advantageContent
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateAdvantage(Connection connection, String advantageContent, String id) {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE product SET ADVANTAGE_CONTENT = ? WHERE ID = ?");
			preparedStatement.setNString(1, advantageContent.trim());
			preparedStatement.setInt(2, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Update Specification when has file 
	 * @param connection
	 * @param specificationSrc
	 * @param specificationContent
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateSpecificationHasFile(Connection connection, String specificationSrc, String specificationContent, String id) {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE product SET SPECIFICATION_SRC = ?, SPECIFICATION_CONTENT = ? WHERE ID = ?");
			preparedStatement.setString(1, specificationSrc.trim());
			preparedStatement.setNString(2, specificationContent.trim());
			preparedStatement.setInt(3, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Update Specification when no file
	 * @param connection
	 * @param specificationContent
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateSpecificationNoFile(Connection connection, String specificationContent, String id) {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE product SET SPECIFICATION_CONTENT = ? WHERE ID = ?");
			preparedStatement.setNString(1, specificationContent.trim());
			preparedStatement.setInt(2, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
}
