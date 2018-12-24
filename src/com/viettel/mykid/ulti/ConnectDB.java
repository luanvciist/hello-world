package com.viettel.mykid.ulti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

	private ConnectDB() {
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
//		Connection conn = null;
//		String connectionUrl = "jdbc:mysql://10.61.184.23:8081/web";
//		// Load driver
//		Class.forName("com.mysql.jdbc.Driver");
//		// Open connect
//		conn = DriverManager.getConnection(connectionUrl, "outsource_dev", "outsource#123");
//		return conn;

		Connection conn = null;
		String connectionUrl = ConstantUtil.getConfigFile().getProperty("database.connectionUrl");
		// Load driver
		Class.forName(ConstantUtil.getConfigFile().getProperty("database.driver"));
		// Open connect
		conn = DriverManager.getConnection(connectionUrl, ConstantUtil.getConfigFile().getProperty("database.username"), ConstantUtil.getConfigFile().getProperty("database.password"));
		return conn;
	}
}
