package com.cptd.persistence;

import java.sql.DriverManager;
import java.sql.Connection;

public class DbHelper {
	private static DbHelper helper = null;
	private Connection connection;

	private DbHelper() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/monitor", "root", "");
		} catch (Exception e) {

		}
	}

	public static DbHelper getHelper() {
		if (helper == null) {
			helper = new DbHelper();
		}
		return helper;
	}
	
	public Connection getConnection()
	{
		return connection;
	}

}
