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

	public Organization(String orgName) throws ClassNotFoundException, SQLException, IOException {
		dbService = MariaDBClient.getInstance();
		this.orgName = orgName;
		readIDFromDB();
	}

	Organization(int orgId, String orgName) throws ClassNotFoundException, SQLException, IOException {
		dbService = MariaDBClient.getInstance();
		this.orgId = orgId;
		this.orgName = orgName;
	}

	Organization(Organization organization) throws ClassNotFoundException, SQLException, IOException {
		dbService = MariaDBClient.getInstance();
		this.orgId = organization.orgId;
		this.orgName = organization.orgName;
	}

}
