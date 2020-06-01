package com.webchat.model;

import com.webchat.db.DBService;
import com.webchat.db.impl.MariaDBClient;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team {

    private int teamID;

    private String teamName;

    public List<String> getMembers() {
        return members;
    }

    private List<String> members;

    Team(int teamID, String teamName) throws ClassNotFoundException, IOException, SQLException {
        this.teamID = teamID;
        this.teamName = teamName;
        this.members = new ArrayList<>();
        readMembersFromDB();
    }

    public String getTeamName() {
        return teamName;
    }



//	private void getTeamID() throws SQLException {
//		String query = "SELECT ID FROM Team WHERE Name = '" + teamName + "' AND Org_ID = '" + orgId + "';";
//		PreparedStatement ps = con.prepareStatement(query);
//		ResultSet rs = ps.executeQuery();
//		rs.first();
//		teamID = rs.getInt(1);
//		rs.close();
//		ps.close();
//	}
//
//	private void getTeamName() throws SQLException {
//		String query = "SELECT Name FROM Team WHERE ID = '" + teamID + "';";
//		PreparedStatement ps = con.prepareStatement(query);
//		ResultSet rs = ps.executeQuery();
//		rs.first();
//		teamName = rs.getString(1);
//		rs.close();
//		ps.close();
//	}
//
//	private int getOrg() throws SQLException {
//		String query = "SELECT Org_ID FROM Team WHERE ID = '" + teamID + "';";
//		PreparedStatement ps = con.prepareStatement(query);
//		ResultSet rs = ps.executeQuery();
//		rs.first();
//		int id = rs.getInt(1);
//		rs.close();
//		ps.close();
//		return id;
//	}
//
//	Team(int id) throws SQLException, ClassNotFoundException, IOException {
//		super();
//		this.teamID = id;
//		getTeamName();
//		setOrgID(getOrg());
//	}
//
//	public String getName() {
//		return teamName;
//	}

	private void readMembersFromDB() throws SQLException, ClassNotFoundException, IOException {
		String query = "SELECT user FROM userteam WHERE team = '" + this.teamID + "';";
		ResultSet rs = MariaDBClient.getInstance().runSelectQuery(query);
		while (rs.next()) {
			members.add(rs.getString(1));
		}
		rs.close();
	}
//
//	@Override
//	public void finalize() throws SQLException {
//		con.close();
//	}

}
