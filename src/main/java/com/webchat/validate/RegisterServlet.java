package com.webchat.validate;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import com.webchat.model.Organization;
import com.webchat.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger("RegisterServlet");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ValidationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String orgName = request.getParameter("team");
        try {
            Authenticator authenticator = Authenticator.getInstance();
            if (!authenticator.isUsernamePresent(username)) {
                logger.info("Username already exists");
                throw new ValidationException("Username already Exists");
            }
            Organization organization = new Organization(orgName);
            User newUser = new User(username, password, organization);
            newUser.register();
            logger.info("User " + username + " registered");
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
