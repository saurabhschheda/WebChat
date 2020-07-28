package com.webchat.db.impl;

import com.webchat.db.DBService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class MariaDBClient implements DBService {

    private static MariaDBClient instance;
    private static Connection con;
    private static PreparedStatement ps;
    private static Logger logger = LogManager.getLogger("MariaDBClient");

    public static MariaDBClient getInstance() throws ClassNotFoundException, SQLException, IOException {
        if (instance == null) {
            instance = new MariaDBClient();
        }
        return instance;
    }

    private MariaDBClient() throws ClassNotFoundException, SQLException, IOException {
        connectToDB();
    }

    private void connectToDB() throws ClassNotFoundException, SQLException, IOException {
        logger.info("Connecting to database ...");
        Class.forName("org.mariadb.jdbc.Driver");
        String propertiesPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath() + "db.properties";
        Properties dbProperties = new Properties();
        dbProperties.load(new FileInputStream(propertiesPath));
        String dbUrl = "jdbc:" + dbProperties.getProperty("dbType") + "://" + dbProperties.getProperty("dbHost") + "/" + dbProperties.getProperty("dbName");
        logger.info("Attempting to connect to " + dbUrl);
        con = DriverManager.getConnection(dbUrl, dbProperties.getProperty("dbUserName"), dbProperties.getProperty("dbPassword"));
        if (con.isClosed()) {
            logger.error("Could not connect to database");
            throw new SQLException();
        }
        logger.info("Successfully connected to database");
    }

    @Override
    public ResultSet runSelectQuery(String sql) throws SQLException {
        ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }

    @Override
    public void runInsertOrUpdateQuery(String sql) throws SQLException {
        ps = con.prepareStatement(sql);
        ps.executeUpdate();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        ps.close();
        con.close();
    }
}
