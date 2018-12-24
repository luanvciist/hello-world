package com.viettel.mykid.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.viettel.mykid.model.Banner;
import com.viettel.mykid.ulti.DbUtil;

public class BannerDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(BannerDao.class);

	/**
	 * Get information to display on the banner screen
	 * @param connection
	 * @return Banner
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Banner getBanner(Connection connection) {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM banner WHERE DEL_FLG = 0 ORDER BY ID LIMIT 1");

			int id;
			String backgroundUrl, parentLogoSrc, childLogoSrc, title, slogan, imageSrc;
			if (resultSet.next()) {
				id = resultSet.getInt("ID");
				backgroundUrl = resultSet.getString("BACKGROUND_URL");
				parentLogoSrc = resultSet.getString("PARENT_LOGO_SRC");
				childLogoSrc = resultSet.getString("CHILD_LOGO_SRC");
				title = resultSet.getString("TITLE");
				slogan = resultSet.getString("SLOGAN");
				imageSrc = resultSet.getString("IMAGE_SRC");
				return new Banner(id, backgroundUrl, parentLogoSrc, childLogoSrc, title, slogan, imageSrc);
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
	 * Update information on the banner screen
	 * @param condition
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void updateBanner(Connection connection, StringBuilder condition, String title, String slogan, String id){
		
		// Execute update query
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE banner SET " + condition + " TITLE = ?, SLOGAN = ? WHERE ID = ? ");
			preparedStatement.setNString(1, title.trim());
			preparedStatement.setNString(2, slogan.trim());
			preparedStatement.setInt(3, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			 DbUtil.closeQuietly(preparedStatement);
		}
	}
}
