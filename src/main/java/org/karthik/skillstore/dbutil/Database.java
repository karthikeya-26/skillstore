package org.karthik.skillstore.dbutil;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.karthik.skillstore.models.UserDetails;
import org.karthik.skillstore.utils.ApplicationPropertiesHelper;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Database {
    public static Properties properties = ApplicationPropertiesHelper.getProperties();
    private static final String URL = "jdbc:postgresql://localhost:5432/test";
    private static final String USER = "karthik";
    private static final String PASSWORD = "karthik@psql";

    private static  HikariDataSource dataSource;

    static {
        try {
            openPool();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void openPool() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("database_url"));
        config.setUsername(properties.getProperty("database_username"));
        config.setPassword(properties.getProperty("database_password"));
        config.setDriverClassName(properties.getProperty("database_driver"));
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);
        dataSource = new HikariDataSource(config);
    }

    public static void closePool() {
        if(dataSource != null) {
            dataSource.close();
        }
    }

//    public static void main(String[] args) {
//        try (Connection c = Database.getConnection()){
//            PreparedStatement ps = c.prepareStatement("select * from articles");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                System.out.println(rs.getString("title"));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static Map<String, UserDetails>  userDetailsMap = new HashMap<>();

    public static Map<String, UserDetails> getUserDetails() {
        return userDetailsMap;
    }
}
