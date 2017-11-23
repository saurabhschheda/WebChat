package com.webchat.model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Team extends Organization implements DBConnection {
	
	private int teamID;
	private String teamName;

	private void setTeamID() throws SQLException {
		String query = "SELECT MAX(ID) FROM Team;";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		rs.first();
		teamID = rs.getInt(1);
		teamID += 1;
		rs.close();
		ps.close();
	}

	private void addNewTeam() throws SQLException {
		setTeamID();
		String query = "INSERT INTO Team VALUES ('" + teamID + "', '" + teamName + "', '" + orgId + "');";
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
		ps.close();
	}

	private void getTeamID() throws SQLException {
		String query = "SELECT ID FROM Team WHERE Name = '" + teamName + "';";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if (rs.first()) {
			teamID = rs.getInt(1);
		} else {
			addNewTeam();
		}
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

	Team(String teamName, String orgName) throws SQLException, ClassNotFoundException {
		super(orgName);
		this.teamName = teamName;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WebChat", USERNAME, PASSWORD);
		getTeamID();
	}

	Team(int id) throws SQLException, ClassNotFoundException {
		this.teamID = id;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WebChat", USERNAME, PASSWORD);
		getTeamName();
	}

	public String getName() {
		return teamName;
	}

	@Override
	public void finalize() throws SQLException {
		con.close();
	}

}
