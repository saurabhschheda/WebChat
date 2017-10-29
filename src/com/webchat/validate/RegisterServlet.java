/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.validate;

import java.io.IOException;
import java.io.PrintWriter;
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

public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        String teamName = request.getParameter("team");

        // inserting data into mysql database
        try {
            Class.forName("com.mysql.jdbc.Driver"); // loads mysql driver
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WebChat", "root", "QutabMinar5");

            String query = "SELECT ID FROM Organization WHERE Name = '" + teamName + "';";
            PreparedStatement ps = con.prepareStatement(query); // generates sql query
            ResultSet rs = ps.executeQuery(); // execute it on test database
            int orgID = 0;
            while (rs.next()) {
                orgID = rs.getInt(1);
            }
            ps.close();

            query = "INSERT INTO User VALUES ('" + username + "', '" + pass + "', '" + orgID + "');";
            ps = con.prepareStatement(query); // generates sql query
            ps.executeUpdate(); // execute it on test database
            ps.close();
            
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            RequestDispatcher rd = request.getRequestDispatcher("fail.html");
            rd.forward(request,response);
        }
        out.println("Successfully Registered");
    }

    /**
     * Handles the HTTP GET method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
