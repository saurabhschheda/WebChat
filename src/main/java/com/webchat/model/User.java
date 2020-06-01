package com.webchat.model;

import com.webchat.db.impl.MariaDBClient;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User extends Organization {

	private String username;

	private String password;

	private List<Team> teams;

	public User(String username, String password, Organization org) throws ClassNotFoundException, SQLException, IOException {
		super(org);
		this.username = username;
		this.password = password;
		this.teams = new ArrayList<>();
	}

	public void register() throws SQLException {
		String query = "INSERT INTO user VALUES ('" + username + "', '" + password + "', '" + orgId + "');";
		dbService.runInsertOrUpdateQuery(query);
	}

	public static User findUser(String userId) throws ClassNotFoundException, SQLException, IOException {
		String query = "SELECT  username, password, organization.id, organization.name FROM user " +
				"INNER JOIN organization ON user.org_id = organization.id " +
				"WHERE username = '" + userId + "';";
		ResultSet rs = MariaDBClient.getInstance().runSelectQuery(query);
		rs.first();
		Organization organization = new Organization(rs.getInt(3), rs.getString(4));
		User user = new User(rs.getString(1), rs.getString(2), organization);
		rs.close();
		user.initializeTeams();
		return user;
	}

	private void initializeTeams() throws SQLException, ClassNotFoundException, IOException {
		String query = "SELECT username, team.id, team.name FROM userteam " +
				"INNER JOIN user ON userteam.user=user.username " +
				"INNER JOIN team ON userteam.team=team.id " +
				"WHERE username = '" + username + "';";
		ResultSet rs = dbService.runSelectQuery(query);
		while (rs.next()) {
			Team team = new Team(rs.getInt(2), rs.getString(3));
			teams.add(team);
		}
		rs.close();
	}

	public List<Team> getTeams() {
		return teams;
	}

	public Team findTeamByName(String name) {
		for (Team team: this.teams) {
			if (name.equals(team.getTeamName())) {
				return team;
			}
		}
		return null;
	}

}
