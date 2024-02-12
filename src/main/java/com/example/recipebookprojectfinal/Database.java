package com.example.recipebookprojectfinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/recipe_book";
        String username = "root";
        String password = "db1970M";
        return DriverManager.getConnection(url, username, password);
    }
}
