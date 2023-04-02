package com.mycompany.javafx_quick_start.utils.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.sql.*;

public class DbSource {
    private static Connection connection;
    private static DbSource instance;
    private static final Logger logger = LoggerFactory.getLogger(DbSource.class);

    private DbSource() {
        try {
            boolean shouldInit = false;
            String userHome = System.getProperty("user.home");
            String folderPath = userHome + File.separator + "javafx_quick_start";
            String dbPath = folderPath + File.separator + "storage.db";
            File folder = new File(folderPath);
            folder.mkdirs();

            File dbFile = new File(dbPath);
            if (!dbFile.exists()) {
                dbFile.createNewFile();
                shouldInit = true;
            }

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

            // Set PRAGMA foreign_keys to 1 for this connection
            Statement statement = connection.createStatement();
            statement.executeUpdate("PRAGMA foreign_keys = 1;");
            if(shouldInit) {
                statement.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                        "id INTEGER PRIMARY KEY,\n" +
                        "username TEXT UNIQUE NOT NULL,\n" +
                        "password TEXT NOT NULL,\n" +
                        "access_level TEXT DEFAULT 'basic' NOT NULL,\n" +
                        "CHECK (access_level IN ('full', 'basic'))\n" +
                        ");");
                statement.executeUpdate("INSERT INTO users (username, password, access_level) VALUES ('master', 'master', 'full');");
                statement.executeUpdate("INSERT INTO users (username, password, access_level) VALUES ('employee1', 'employee1', 'basic');");
            }
            statement.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static DbSource getInstance() {
        logger.info("Getting database connection...");
        if (instance == null) {
            instance = new DbSource();
        }
        return instance;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        logger.info("Executing database query");
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    public int executeUpdate(String sql) throws SQLException {
        logger.info("Executing create / update on database");
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        logger.info("Creating SQL prepared statement");

        return connection.prepareStatement(sql);
    }

    public int truncate(String tableName) throws SQLException {
        String sql = "DELETE FROM " + tableName + ";" +
                "DELETE FROM sqlite_sequence WHERE name='" + tableName + "'";

        logger.info("Truncating " + tableName + " data...");
        return this.executeUpdate(sql);
    }

    public void close() throws SQLException {
        logger.info("Closing database connection");
        if(connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
