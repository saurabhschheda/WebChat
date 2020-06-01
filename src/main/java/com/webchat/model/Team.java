package com.webchat.model;

import com.webchat.db.impl.MariaDBClient;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team {

    private int teamID;
    private String teamName;
    private List<String> members;

    Team(int teamID, String teamName) throws ClassNotFoundException, IOException, SQLException {
        this.teamID = teamID;
        this.teamName = teamName;
        this.members = new ArrayList<>();
        readMembersFromDB();
    }

    public List<String> getMembers() {
        return members;
    }

    public String getTeamName() {
        return teamName;
    }

	private void readMembersFromDB() throws SQLException, ClassNotFoundException, IOException {
		String query = "SELECT user FROM userteam WHERE team = '" + this.teamID + "';";
		ResultSet rs = MariaDBClient.getInstance().runSelectQuery(query);
		while (rs.next()) {
			members.add(rs.getString(1));
		}
		rs.close();
	}

}
