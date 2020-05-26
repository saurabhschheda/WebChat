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
        String orgName = request.getParameter("team");
        try {
            Authenticator authenticator = Authenticator.getInstance();
            if (!authenticator.isUsernamePresent(username)) {
                throw new ValidationException("Username already Exists");
            }
            User newUser = new User(username, password, orgName);
            newUser.register();
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
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("fail.jsp").forward(request, response);
        }
    }

}
