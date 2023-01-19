package jm.task.core.jdbc.util;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class Util {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/user_database";
    private final static String DB_NAME = "root";
    private final static String DB_PASS = "root";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                return connection = DriverManager.getConnection(DB_URL, DB_NAME, DB_PASS);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
