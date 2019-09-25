package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static Connection conn;

	public static Connection getConnection() {

		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(
					System.getenv("demodbURL"),
					System.getenv("demodbUserName"),
					System.getenv("demodbPassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}
}

