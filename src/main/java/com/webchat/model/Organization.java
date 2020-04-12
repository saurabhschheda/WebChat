package com.webchat.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Properties;

public class Organization {
	
	protected int orgId;
	protected String orgName;
	protected Connection con;

	private void setID() throws SQLException {
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
		setID();
		String query = "INSERT INTO Organization VALUES ('" + orgId + "', '" + orgName + "');";
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
		ps.close();
	}

	private void getID() throws SQLException {
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

	private void getName() throws SQLException {
		String query = "SELECT Name FROM Organization WHERE ID = '" + orgId + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		rs.first();
		orgName = rs.getString(1);
		rs.close();
		ps.close();
	}

	private void setConnection() throws ClassNotFoundException, SQLException, IOException {
		Class.forName("org.mariadb.jdbc.Driver");
		String propertiesPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath() + "db.properties";
		Properties dbProperties = new Properties();
		dbProperties.load(new FileInputStream(propertiesPath));
		String dbUrl = "jdbc:" + dbProperties.getProperty("dbType") + "://" + dbProperties.getProperty("dbHost") + "/" + dbProperties.getProperty("dbName");
		con = DriverManager.getConnection(dbUrl, dbProperties.getProperty("dbUserName"), dbProperties.getProperty("dbPassword"));
	}
	public void setOrgName(String orgName) throws SQLException {
		this.orgName = orgName;
		getID();
	}

	public void setOrgID(int orgID) throws SQLException {
		this.orgId = orgID;
		getName();
	}

	public String getOrgName() {
		return orgName;
	}

	Organization(String orgName) throws ClassNotFoundException, SQLException, IOException {
		setConnection();
		setOrgName(orgName);
	}

	Organization(int id) throws ClassNotFoundException, SQLException, IOException {
		setConnection();
		setOrgID(id);
	}

	Organization() throws ClassNotFoundException, SQLException, IOException {
		orgId = -1;
		orgName = "";
		setConnection();
	}

}
