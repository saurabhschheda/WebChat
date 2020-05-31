package com.webchat.validate;

import com.webchat.db.DBService;
import com.webchat.db.impl.MariaDBClient;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authenticator {

    private static DBService dbService;
    private static Authenticator instance;

    private Authenticator() throws ClassNotFoundException, SQLException, IOException {
        dbService = MariaDBClient.getInstance();
    }

    static Authenticator getInstance() throws ClassNotFoundException, SQLException, IOException {
        if (instance == null) {
            instance = new Authenticator();
        }
        return instance;
    }

    boolean isUsernamePresent(String username) throws SQLException {
        boolean usernamePresent = true;
        String query = "SELECT * FROM user WHERE username = '" + username + "';";
        ResultSet rs = dbService.runSelectQuery(query);
        if (rs.first()) {
            usernamePresent = false;
        }
        rs.close();
        return usernamePresent;
    }

    boolean authenticate(String username, String password) throws SQLException {
        boolean isAuthenticated = false;
        String query = "SELECT * FROM user WHERE " +
                " username = '" + username + "' AND password = '" + password + "';";
        ResultSet rs = dbService.runSelectQuery(query);
        if (rs.first()) {
            isAuthenticated = true;
        }
        rs.close();
        return isAuthenticated;
    }
}
