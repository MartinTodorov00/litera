package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJbdc {

    private static final GetProperty properties = new GetProperty();

    public static Connection getConnection() throws SQLException, IOException {
        properties.getPropValues();
        return DriverManager.getConnection(properties.getDb_url(), properties.getDb_username(), properties.getDb_password());
    }

    public static Connection getConnection(String name) throws SQLException, IOException {
        properties.getPropValues();
        return DriverManager.getConnection(properties.getDb_url() + name, properties.getDb_username(), properties.getDb_password());
    }
}
