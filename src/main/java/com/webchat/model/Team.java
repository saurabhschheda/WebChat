package com.webchat.model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class Team extends Organization {
	
	private int teamID;
	private String teamName;

	private void getTeamID() throws SQLException {
		String query = "SELECT ID FROM Team WHERE Name = '" + teamName + "' AND Org_ID = '" + orgId + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		rs.first();
		teamID = rs.getInt(1);
		rs.close();
		ps.close();
	}

	private void getTeamName() throws SQLException {
		String query = "SELECT Name FROM Team WHERE ID = '" + teamID + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		rs.first();
		teamName = rs.getString(1);
		rs.close();
		ps.close();
	}

	private int getOrg() throws SQLException {
		String query = "SELECT Org_ID FROM Team WHERE ID = '" + teamID + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		rs.first();
		int id = rs.getInt(1);
		rs.close();
		ps.close();
		return id;
	}

	Team(String teamName, String orgName) throws SQLException, ClassNotFoundException, IOException {
		super(orgName);
		this.teamName = teamName;
		getTeamID();
	}

	Team(int id) throws SQLException, ClassNotFoundException, IOException {
		super();
		this.teamID = id;
		getTeamName();
		setOrgID(getOrg());
	}

	public String getName() {
		return teamName;
	}

	public ArrayList<User> getUsers() throws SQLException, ClassNotFoundException, IOException {
		ArrayList<User> users = new ArrayList<User>();
		String query = "SELECT User_ID FROM UserTeam WHERE Team_ID = '" + teamID + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String username = rs.getString(1);
			users.add(new User(username));
		}
		rs.close();
		ps.close();
		return users;
	}

	@Override
	public void finalize() throws SQLException {
		con.close();
	}

}
