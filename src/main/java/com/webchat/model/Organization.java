package com.webchat.model;

import com.webchat.db.DBService;
import com.webchat.db.impl.MariaDBClient;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Organization {
	
	protected int orgId;
	protected String orgName;

	protected static DBService dbService;

	private void addToDB() throws SQLException {
		String query = "INSERT INTO organization(name) VALUES ('" + orgName + "');";
		dbService.runInsertOrUpdateQuery(query);
		readIDFromDB();
	}

	private void readIDFromDB() throws SQLException {
		String query = "SELECT ID FROM organization WHERE name = '" + orgName + "';";
		ResultSet rs = dbService.runSelectQuery(query);
		if (rs.first()) {
			orgId = rs.getInt(1);
		} else {
			addToDB();
		}
		rs.close();
	}

//	private void getName() throws SQLException {
//		String query = "SELECT Name FROM Organization WHERE ID = '" + orgId + "';";
//		PreparedStatement ps = con.prepareStatement(query);
//		ResultSet rs = ps.executeQuery();
//		rs.first();
//		orgName = rs.getString(1);
//		rs.close();
//		ps.close();
//	}

//	public void setOrgName(String orgName) throws SQLException {
//		this.orgName = orgName;
//		setIDFromDB();
//	}

//	public void setOrgID(int orgID) throws SQLException {
//		this.orgId = orgID;
//		getName();
//	}

//	public String getOrgName() {
//		return orgName;
//	}

	Organization(String orgName) throws ClassNotFoundException, SQLException, IOException {
		dbService = MariaDBClient.getInstance();
		this.orgName = orgName;
		readIDFromDB();
	}

//	Organization() throws ClassNotFoundException, SQLException, IOException {
//		orgId = -1;
//		orgName = "";
//		dbService = MariaDBClient.getInstance();
//	}

}
