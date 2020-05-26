package com.webchat.validate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ValidationException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			Authenticator authenticator = Authenticator.getInstance();
			if (!authenticator.authenticate(username, password)) {
				throw new ValidationException("Invalid Username or Password");
			}
			request.setAttribute("username", username);
			request.getRequestDispatcher("chat.jsp").forward(request, response);
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
