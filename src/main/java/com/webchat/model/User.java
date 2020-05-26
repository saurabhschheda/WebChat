package com.webchat.model;

import com.webchat.validate.ValidationException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User extends Organization {

	private String username;

	private String password;

//	ArrayList<Team> teams;

	public String getUsername() { return username; };

	public User(String username, String password, String orgName) throws SQLException, ClassNotFoundException, IOException {
		super(orgName);
		this.username = username;
		this.password = password;
	}

	public void register() throws SQLException {
		String query = "INSERT INTO user VALUES ('" + username + "', '" + password + "', '" + orgId + "');";
		dbService.runInsertOrUpdateQuery(query);
	}

//	public User(String username, String orgName) throws SQLException, ClassNotFoundException, IOException {
//		super(orgName);
//		this.username = username;
//	}

//	private int getOrg() throws SQLException {
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

}
