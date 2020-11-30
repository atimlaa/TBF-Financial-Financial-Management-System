/*
 * This class creates a connection with mySql and java. We created a login info then set a connection in this class.
 */
package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInfo
{
	//login credentials
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String url = "jdbc:mysql://cse.unl.edu/zshresth";
	public static final String username = "zshresth";
	public static final String password = "A50lW9j1";
	 
	//method to create connection
	public static Connection getConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (InstantiationException e)
		{
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e)
		{
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e)
		{
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
