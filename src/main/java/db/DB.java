package db;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

    private static Properties connectionProperties = null;

    public static Connection getConnection() {
        try {
            if (connectionProperties == null) {
                connectionProperties = loadProperties();
            }

            String url = connectionProperties.getProperty("dburl");

            Connection conn = DriverManager.getConnection(url, connectionProperties);


            return conn;

        } catch (SQLException e) {
            throw new DbException("Error when connecting with bank: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        System.out.println("✅ Database connections managed per-operation");
    }

    private static Properties loadProperties() {
        try (InputStream is = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                throw new DbException("db.properties not found.");
            }
            Properties props = new Properties();
            props.load(is);
            return props;
        } catch (IOException e) {
            throw new DbException("load properties error:  " + e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException("Error to close statement: " + e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException("Error to close  ResultSet: " + e.getMessage());
            }
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException("Error to close connection: " + e.getMessage());
            }
        }
    }
}