package com.webchat.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class User extends Organization implements DBConnection {

	private String username, password;

	public User(String username, String password, String orgName) throws SQLException, ClassNotFoundException{
		super(orgName);
		this.username = username;
		this.password = password;
	}

	public boolean isUsernameValid() throws SQLException {
		boolean usernameValid = true;
		String query = "SELECT * FROM User WHERE username = '" + username + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if (rs.first()) {
			usernameValid = false;
		}
		rs.close();
		ps.close();
		return usernameValid;
	}

	public void addUserToDB() throws SQLException {
		String query = "INSERT INTO User VALUES ('" + username + "', '" + password + "', '" + orgId + "');";
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
		ps.close();
	}

	public static boolean authenticate(String username, String password) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WebChat", USERNAME, PASSWORD);
		boolean isOK = false;
      	String query = "SELECT * FROM User WHERE username = '" + username + "' AND password = '" + password + "';";
      	PreparedStatement ps = con.prepareStatement(query);
      	ResultSet rs = ps.executeQuery();
		if (rs.first())	{
			isOK = true;
		}
		rs.close();
		ps.close();
		con.close();
      	return isOK;
	}

	@Override
	public void finalize() throws SQLException {
		con.close();
	}

}
