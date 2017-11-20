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

public class LoginServlet extends HttpServlet implements DBConnection {

   	String username, password;
   	Connection con;

   	private boolean authenticate() throws SQLException {
       	boolean isOK = false;
       	String query = "SELECT * FROM User WHERE username = '" + username + "' AND password = '" + password + "';";
       	PreparedStatement ps = con.prepareStatement(query);
       	ResultSet rs = ps.executeQuery();
       	if (rs.first())
         	isOK = true;
       	ps.close();
       	return usernameValid;
   	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ValidationException {
		username = request.getParameter("username");
		password = request.getParameter("password");
		try {
			Class.forName("com.mysql.jdbc.Driver"); // loads mysql driver
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WebChat", USERNAME, PASSWORD);
			if (!authenticate())
				throw new ValidationException("Username and Password Do Not Match");
			request.getRequestDispatcher("chat.jsp").forward(request, response);
			con.close();
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
