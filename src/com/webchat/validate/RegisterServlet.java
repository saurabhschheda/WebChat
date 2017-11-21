package com.webchat.validate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import com.webchat.model.User;

public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ValidationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String teamName = request.getParameter("team");
        try {
            User newUser = new User(username, password, teamName);
            if (!newUser.isUsernameValid()) {
                throw new ValidationException("Username already Exists");
            }
            newUser.addUserToDB();
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
