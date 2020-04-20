package com.webchat.model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User extends Organization {

	private String username;

//	ArrayList<Team> teams;

	public String getUsername() { return username; };

//	public User(String username) throws SQLException, ClassNotFoundException, IOException {
//		super();
//		this.username = username;
//		setOrgID(getOrg());
//	}

	public User(String username, String orgName) throws SQLException, ClassNotFoundException, IOException {
		super(orgName);
		this.username = username;
	}

//	private int getOrg() throws SQLException {
//		String query = "SELECT org_id FROM user WHERE username = '" + username + "';";
//		ResultSet rs = dbService.runSelectQuery(query);
//		rs.first();
//		int id = rs.getInt(1);
//		rs.close();
//		return id;
//	}

//	private void setTeams() throws SQLException, ClassNotFoundException, IOException {
//		teams = new ArrayList<Team>();
//		String query = "SELECT Team_ID FROM UserTeam WHERE User_ID = '" + username + "';";
//		ResultSet rs = dbService.runSelectQuery(query);
//		while(rs.next()) {
//			int id = rs.getInt(1);
//			teams.add(new Team(id));
//		}
//		rs.close();
//	}

//	public ArrayList<Team> getTeams() throws SQLException, ClassNotFoundException, IOException {
//		setTeams();
//		return teams;
//	}

	public boolean isUsernamePresent() throws SQLException {
		boolean usernamePresent = true;
		String query = "SELECT * FROM user WHERE username = '" + username + "';";
 		ResultSet rs = dbService.runSelectQuery(query);
		if (rs.first()) {
			usernamePresent = false;
		}
		rs.close();
		return usernamePresent;
	}

	public void addUserToDB(String password) throws SQLException {
		String query = "INSERT INTO user VALUES ('" + username + "', '" + password + "', '" + orgId + "');";
		dbService.runInsertOrUpdateQuery(query);
	}

//	public boolean authenticate(String password) throws SQLException {
//		boolean isOK = false;
//		String query = "SELECT * FROM User WHERE username = '" + username + "' AND password = '" + password + "';";
//		ResultSet rs = dbService.runSelectQuery(query);
//		if (rs.first())	{
//			isOK = true;
//		}
//		rs.close();
//		return isOK;
//	}
}
