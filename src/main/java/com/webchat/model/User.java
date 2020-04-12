package com.webchat.model;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

public class User extends Organization {

	private String username;
	ArrayList<Team> teams;

	public String getUsername() { return username; };

	public User(String username) throws SQLException, ClassNotFoundException, IOException {
		super();
		this.username = username;
		setOrgID(getOrg());
	}

	private int getOrg() throws SQLException {
		String query = "SELECT Org_ID FROM User WHERE Username = '" + username + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		rs.first();
		int id = rs.getInt(1);
		rs.close();
		ps.close();
		return id;
	}

	public User(String username, String orgName) throws SQLException, ClassNotFoundException, IOException {
		super(orgName);
		this.username = username;
	}

	private void setTeams() throws SQLException, ClassNotFoundException, IOException {
		teams = new ArrayList<Team>();
		String query = "SELECT Team_ID FROM UserTeam WHERE User_ID = '" + username + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			int id = rs.getInt(1);
			teams.add(new Team(id));
		}
		rs.close();
		ps.close();
	}

	public ArrayList<Team> getTeams() throws SQLException, ClassNotFoundException, IOException {
		setTeams(); 
		return teams;
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

	public void addUserToDB(String password) throws SQLException {
		String query = "INSERT INTO User VALUES ('" + username + "', '" + password + "', '" + orgId + "');";
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
		ps.close();
	}

	public boolean authenticate(String password) throws SQLException {
		boolean isOK = false;
		String query = "SELECT * FROM User WHERE username = '" + username + "' AND password = '" + password + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if (rs.first())	{
			isOK = true;
		}
		rs.close();
		ps.close();
		return isOK;
	}

	@Override
	public void finalize() throws SQLException {
		con.close();
	}

}
