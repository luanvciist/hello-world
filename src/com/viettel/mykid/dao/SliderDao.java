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

import com.viettel.mykid.model.Slider;
import com.viettel.mykid.ulti.DbUtil;

public class SliderDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(BannerDao.class);

	/**
	 * Get all image in slider
	 * @param connection
	 * @return srcList
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<Slider> getAllSlider(Connection connection){
		
		Statement statement = null;
		ResultSet resultSet = null;
		List<Slider> sliderList = new ArrayList<Slider>();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(	"SELECT\r\n" + 
												"	slider.ID,\r\n" + 
												"	slider.SLIDER_SUFFIX,\r\n" + 
												"	slider.SRC\r\n" + 
												"FROM\r\n" + 
												"	slider\r\n" + 
												"	INNER JOIN product\r\n" + 
												"		ON slider.PRODUCT_ID = product.ID\r\n" + 
												"WHERE\r\n" + 
												"	slider.DEL_FLG = 0\r\n" + 
												"	AND product.DEL_FLG = 0\r\n" + 
												"	AND product.ID = (SELECT ID FROM product WHERE DEL_FLG = 0 ORDER BY ID LIMIT 1)\r\n" + 
												"ORDER BY slider.ID DESC");
			int id;
			String sliderSuffix, src;
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
				sliderSuffix = resultSet.getString("SLIDER_SUFFIX");
				src = resultSet.getString("SRC");
				sliderList.add(new Slider(id, sliderSuffix, src));
			}
			
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(statement);
			DbUtil.closeQuietly(resultSet);
		}

		return sliderList;
	}
	
	/**
	 * Get Slider object by Id
	 * @param connection
	 * @param sliderId
	 * @return
	 * @throws SQLException
	 */
	public Slider getSliderById(Connection connection, String sliderId){
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM slider WHERE id = ?  AND DEL_FLG = 0");
			preparedStatement.setInt(1, Integer.parseInt(sliderId.trim()));
			resultSet = preparedStatement.executeQuery();
			
			int id;
			String src, sliderSuffix;
			if (resultSet.next()) {
				id = resultSet.getInt("ID");
				sliderSuffix = resultSet.getString("SLIDER_SUFFIX");
				src = resultSet.getString("SRC");
				return new Slider(id, sliderSuffix, src);
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
	 * Update slider when has upload file
	 * @param connection
	 * @param bodyId
	 * @param src
	 * @param sliderSuffix
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateSliderHasFile(Connection connection, String id, String src, String sliderSuffix){
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE slider SET SLIDER_SUFFIX = ?, SRC= ? WHERE ID = ?");
			preparedStatement.setString(1, sliderSuffix.trim());
			preparedStatement.setString(2, src.trim());
			preparedStatement.setInt(3, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Update slider when no upload file
	 * @param connection
	 * @param bodyId
	 * @param src
	 * @param sliderSuffix
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateSliderNoFile(Connection connection, String id, String sliderSuffix){
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE slider SET SLIDER_SUFFIX = ? WHERE ID = ?");
			preparedStatement.setString(1, sliderSuffix.trim());
			preparedStatement.setInt(2, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Add slider
	 * @param connection
	 * @param src
	 * @param sliderSuffix
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addSlider(Connection connection, int productId,String src, String sliderSuffix){
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO slider(PRODUCT_ID, SLIDER_SUFFIX, SRC) VALUES (?, ?, ?)");
			preparedStatement.setInt(1, productId);
			preparedStatement.setString(2, sliderSuffix.trim());
			preparedStatement.setString(3, src.trim());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
	
	/**
	 * Delete slider
	 * @param connection
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteSlider(Connection connection, String id) {
		
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("UPDATE slider SET DEL_FLG = 1 WHERE ID = ?");
			preparedStatement.setInt(1, Integer.parseInt(id.trim()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(preparedStatement);
		}
	}
}
