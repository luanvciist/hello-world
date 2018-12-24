package com.viettel.mykid.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.viettel.mykid.model.User;
import com.viettel.mykid.ulti.ConnectDB;
import com.viettel.mykid.ulti.DbUtil;

public class UserDao implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;
	private static Logger LOGGER = Logger.getLogger(UserDao.class);

	public User getUser(String username, String password) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectDB.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE USERNAME = ? AND PASSWORD = ? AND DEL_FLG = 0");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			resultSet = preparedStatement.executeQuery();

			int role;
			String src;
			if (resultSet.next()) {
				role = resultSet.getInt("ROLE");
				src = resultSet.getString("SRC");
				return new User(username, password, src, role);
			}

		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			DbUtil.closeQuietly(connection);
			DbUtil.closeQuietly(preparedStatement);
			DbUtil.closeQuietly(resultSet);
		}

		return null;
	}
}
