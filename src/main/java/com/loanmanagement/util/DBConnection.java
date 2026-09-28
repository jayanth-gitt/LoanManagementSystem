package com.loanmanagement.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static final Logger logger =
            LoggerFactory.getLogger(DBConnection.class);

    Connection con = null;

    public Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();

            InputStream input =
                    getClass().getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new RuntimeException("db.properties file not found");
            }
            properties.load(input);

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            con = DriverManager.getConnection(url, username, password);
            logger.info("Database connection established successfully");

        } catch (Exception e) {
            logger.error("Error while connecting to database", e);
            throw new RuntimeException("Failed to connect to database", e);
        }

        return con;
    }
}
