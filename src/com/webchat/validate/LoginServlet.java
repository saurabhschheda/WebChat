package com.webchat.validate;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import com.webchat.model.User;

public class LoginServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ValidationException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			if (!User.authenticate(username, password)) {
				throw new ValidationException("Username and Password Do Not Match");
			}
			request.getRequestDispatcher("chat.html").forward(request, response);
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
