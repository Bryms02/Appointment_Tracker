/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author brysa
 */
/**
 *
 *
 *
 * Used to connect to the database, sets up methods to start, get, and close
 * connections.
 *
 *
 *
 */
public class DBConn {

    // JDBC URL Parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ07XOX";

    private static final String jdbcUrl = protocol + vendorName + ipAddress + dbName + "?connectionTimeZone=SERVER";

    //Driver interface reference
    private static final String MYSQLJDBCDRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U07XOX"; //Username
    private static final String password = "53689158072";

    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDRIVER);
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            //System.out.println(o.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //System.out.println(o.getMessage());
        }

        return conn;
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void closeConnection() {

        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(o.getMessage());

    }

}
