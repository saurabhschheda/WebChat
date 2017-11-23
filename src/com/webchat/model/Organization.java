package com.webchat.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Organization implements DBConnection {
	
	protected int orgId;
	protected String orgName;
	protected Connection con;

	private void setOrgID() throws SQLException {
		String query = "SELECT MAX(ID) FROM Organization;";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		rs.first();
		orgId = rs.getInt(1);
        orgId += 1;
		rs.close();
		ps.close();
	}

	private void addNewOrganization() throws SQLException {
		setOrgID();
		String query = "INSERT INTO Organization VALUES ('" + orgId + "', '" + orgName + "');";
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
		ps.close();
	}

	private void getOrgID() throws SQLException {
		String query = "SELECT ID FROM Organization WHERE Name = '" + orgName + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if (rs.first()) {
			orgId = rs.getInt(1);
		} else {
			addNewOrganization();
		}
		rs.close();
		ps.close();
	}

	Organization(String orgName) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WebChat", USERNAME, PASSWORD);
		this.orgName = orgName;
		getOrgID();
	}

	Organization() {
		orgId = 0;
		orgName = "";
		con = null;
	}

}
