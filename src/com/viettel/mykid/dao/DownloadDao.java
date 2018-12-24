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

import com.viettel.mykid.model.Download;
import com.viettel.mykid.ulti.DbUtil;

public class DownloadDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(DownloadDao.class);

	/**
	 * Get list download of the product 
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public List<Download> getAllDownload(Connection connection){
		
		Statement statement = null;
		ResultSet resultSet = null;
		List<Download> downloadList = new ArrayList<Download>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(	"SELECT\r\n" + 
												"	download.ID,\r\n" + 
												"	download.OS,\r\n" + 
												"   download.CLASS_SUFFIX,\r\n" + 
												"   download.HREF,\r\n" + 
												"   download.SRC\r\n" + 
												"FROM\r\n" + 
												"	download \r\n" + 
												"	INNER JOIN product\r\n" + 
												"		ON download.PRODUCT_ID = product.ID\r\n" + 
												"WHERE\r\n" + 
												"	download.DEL_FLG = 0\r\n" + 
												"	AND product.DEL_FLG = 0\r\n" + 
												"	AND product.ID = (SELECT ID FROM product WHERE DEL_FLG = 0 ORDER BY ID LIMIT 1)\r\n" +
												"ORDER BY download.ID DESC");

			int id;
			String os, classSuffix, href, src;
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
				os = resultSet.getString("OS");
				classSuffix = resultSet.getString("CLASS_SUFFIX");
				href = resultSet.getString("HREF");
				src = resultSet.getString("SRC");
				downloadList.add(new Download(id, os, classSuffix, href, src));
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(resultSet);
		}
		
		return downloadList;
	}
	
	
	/**
	 * Get download object by id
	 * @param connection
	 * @param downloadId
	 * @return
	 * @throws SQLException
	 */
	public Download getDownloadById(Connection connection, String downloadId){
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM download WHERE id = ?  AND DEL_FLG = 0");
			preparedStatement.setInt(1, Integer.parseInt(downloadId));
			resultSet = preparedStatement.executeQuery();
			
			int id;
			String os, classSuffix, href, src;
			if (resultSet.next()) {
				id = resultSet.getInt("ID");
				os = resultSet.getString("OS");
				classSuffix = resultSet.getString("CLASS_SUFFIX");
				href = resultSet.getString("HREF");
				src = resultSet.getString("SRC");
				return new Download(id, os, classSuffix, href, src);
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
	 * Update Download when has upload file
	 * @param connection
	 * @param bodyId
	 * @param src
	 * @param content
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateDownloadHasFile(Connection connection, String id, String os, String classSuffix, String href, String src){

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE download SET OS = ?, CLASS_SUFFIX = ?, HREF = ?, SRC = ? WHERE ID = ?");
			preparedStatement.setString(1, os.trim());
			preparedStatement.setString(2, classSuffix.trim());
			preparedStatement.setString(3, href.trim());
			preparedStatement.setString(4, src.trim());
			preparedStatement.setInt(5, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
		
	}

	/**
	 * Update Download when no upload file
	 * 
	 * @param connection
	 * @param bodyId
	 * @param src
	 * @param content
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateDownloadNoFile(Connection connection, String id, String os, String classSuffix, String href) {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE download SET OS = ?, CLASS_SUFFIX = ?, HREF = ? WHERE ID = ?");
			preparedStatement.setString(1, os.trim());
			preparedStatement.setString(2, classSuffix.trim());
			preparedStatement.setString(3, href.trim());
			preparedStatement.setInt(4, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Add Download
	 * @param connection
	 * @param src
	 * @param content
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addDownload(Connection connection, int productId, String os, String classSuffix, String href,
			String src) {
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO download(PRODUCT_ID, OS, CLASS_SUFFIX, HREF, SRC) VALUES (?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, productId);
			preparedStatement.setString(2, os.trim());
			preparedStatement.setString(3, classSuffix.trim());
			preparedStatement.setString(4, href.trim());
			preparedStatement.setNString(5, src.trim());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
		
	}
	
	/**
	 * Delete Download
	 * @param connection
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteDownload(Connection connection, String id) {
		
		// Execute update query
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE download SET DEL_FLG = 1 WHERE ID = ?");
			preparedStatement.setInt(1, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
}
