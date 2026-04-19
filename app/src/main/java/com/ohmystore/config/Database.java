package com.ohmystore.config;

import java.sql.*;

public class Database {
    private String url;
    private String user;
    private String password;

    public Database (String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection () throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
