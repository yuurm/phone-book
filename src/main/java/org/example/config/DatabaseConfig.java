package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/config/db.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                get("db.url"),
                get("db.user"),
                get("db.password")
        );
        connection.setTransactionIsolation(parseTransactionIsolationLevel(get("db.transaction.isolation")));
        return connection;
    }

    private static int parseTransactionIsolationLevel(String level) {
        return switch (level) {
            case "TRANSACTION_READ_COMMITTED" -> Connection.TRANSACTION_READ_COMMITTED;
            case "TRANSACTION_REPEATABLE_READ" -> Connection.TRANSACTION_REPEATABLE_READ;
            case "TRANSACTION_SERIALIZABLE" -> Connection.TRANSACTION_SERIALIZABLE;
            default -> Connection.TRANSACTION_NONE;
        };
    }
}
