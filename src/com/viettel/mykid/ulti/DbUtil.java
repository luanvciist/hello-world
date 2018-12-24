package com.viettel.mykid.ulti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DbUtil {
	
	private static Logger LOGGER = Logger.getLogger(DbUtil.class);
	
	private DbUtil() {
	}
	
	
	public static void closeQuietly (PreparedStatement preparedStatement) {
		if(preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	public static void closeQuietly (Statement statement) {
		if(statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
	public static void closeQuietly (ResultSet resultSet) {
		if(resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
	public static void closeQuietly (Connection connection) {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
	public static void rollBack (Connection connection) {
		if(connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
