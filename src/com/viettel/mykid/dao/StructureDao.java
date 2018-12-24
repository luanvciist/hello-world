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

import com.viettel.mykid.model.Structure;
import com.viettel.mykid.ulti.DbUtil;

public class StructureDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(StructureDao.class);
	
	/**
	 * Get list structureList of images structure
	 * 
	 * @param connection
	 * @return structureList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Structure> getAllSrc(Connection connection){
		
		Statement statement = null;
		ResultSet resultSet = null;
		List<Structure> structureList = new ArrayList<Structure>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(	"SELECT\r\n" + 
												"	structure.ID,\r\n" +
												"	structure.SRC\r\n" + 
												"FROM\r\n" + 
												"	structure \r\n" + 
												"	INNER JOIN product\r\n" + 
												"		ON structure.PRODUCT_ID = product.ID\r\n" + 
												"WHERE\r\n" + 
												"	structure.DEL_FLG = 0\r\n" + 
												"	AND product.DEL_FLG = 0\r\n" + 
												"	AND product.ID = (SELECT ID FROM product WHERE DEL_FLG = 0 ORDER BY ID LIMIT 1)\r\n" +
												"ORDER BY structure.ID DESC");
			int id;
			String src;
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
				src = resultSet.getString("SRC");
				structureList.add(new Structure(id, src));
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(resultSet);
		}

		return structureList;
	}
	
	/**
	 * Get structure object by id
	 * @param connection
	 * @param structureId
	 * @return
	 * @throws SQLException
	 */
	public Structure getStructureById(Connection connection, String structureId){

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM structure WHERE id = ?  AND DEL_FLG = 0");
			preparedStatement.setInt(1, Integer.parseInt(structureId.trim()));
			resultSet = preparedStatement.executeQuery();

			int id;
			String src;
			if (resultSet.next()) {
				id = resultSet.getInt("ID");
				src = resultSet.getString("SRC");
				return new Structure(id, src);
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
	 * Update structure when has upload file
	 * @param connection
	 * @param id
	 * @param src
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateStructure(Connection connection, String id, String src){
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE structure SET SRC= ? WHERE ID = ?");
			preparedStatement.setString(1, src.trim());
			preparedStatement.setInt(2, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Add structure
	 * @param connection
	 * @param productId
	 * @param src
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addStructure(Connection connection, int productId, String src){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO structure(PRODUCT_ID, SRC) VALUES (?, ?)");
			preparedStatement.setInt(1, productId);
			preparedStatement.setNString(2, src.trim());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Delete structure
	 * @param connection
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteStructure(Connection connection, String id){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE structure SET DEL_FLG = 1 WHERE ID = ?");
			preparedStatement.setInt(1, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
}
