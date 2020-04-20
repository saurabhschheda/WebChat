//package com.webchat.validate;
//
//import com.webchat.model.User;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.SQLException;
//
//public class LoginServlet extends HttpServlet {
//
//	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException, ValidationException {
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		try {
//			User user = new User(username);
//			if (!user.authenticate(password)) {
//				throw new ValidationException("Username and Password Do Not Match");
//			}
//			request.setAttribute("username", username);
//			request.getRequestDispatcher("chat.jsp").forward(request, response);
//		} catch (ClassNotFoundException | SQLException e) {
//			throw new ValidationException();
//		}
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		try {
//			processRequest(request, response);
//		} catch (ValidationException e) {
//			request.setAttribute("error", e.getMessage());
//			request.getRequestDispatcher("fail.jsp").forward(request, response);
//		}
//	}
//
//}
