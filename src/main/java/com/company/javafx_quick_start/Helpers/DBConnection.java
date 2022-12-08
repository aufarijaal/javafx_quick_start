package com.company.javafx_quick_start.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private Connection c = null;

    public DBConnection() {
    }

    public Connection getConnection() throws Exception {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\com\\company\\javafx_quick_start\\storage.sqlite3");
            return c;
    }
}
