package com.webchat.validate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet implements DBConnection {

    String username, password;
    Connection con;

    private boolean isUsernameValid() throws SQLException {
        boolean usernameValid = true;
        String query = "SELECT * FROM User WHERE username = '" + username + "';";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.first()) usernameValid = false;
        ps.close();
        return usernameValid;
    }

    private int getOrgID(String teamName) throws SQLException, ValidationException {
        String query = "SELECT ID FROM Organization WHERE Name = '" + teamName + "';";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        int orgID = -1;
        if (rs.first()) orgID = rs.getInt(1);
        else throw new ValidationException("Invalid Organization");
        ps.close();
        return orgID;
    }

    private void addUser(String teamName) throws SQLException, ValidationException {
        if (!isUsernameValid()) throw new ValidationException("Username Already Taken");
        int orgID = getOrgID(teamName);
        String query = "INSERT INTO User VALUES ('" + username + "', '" + password + "', '" + orgID + "');";
        PreparedStatement ps = con.prepareStatement(query);
        ps.executeUpdate();
        ps.close();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ValidationException {
        username = request.getParameter("username");
        password = request.getParameter("password");
        String teamName = request.getParameter("team");
        try {
            Class.forName("com.mysql.jdbc.Driver"); // loads mysql driver
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WebChat", USERNAME, PASSWORD);
            addUser(teamName);
            con.close();
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ValidationException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ValidationException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("fail.jsp").forward(request, response);
        }
    }

}
