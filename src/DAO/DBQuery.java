/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author brysa
 */
/**
 *
 * DBQuery class created to set and get our prepared statements used throughout
 * the DAO classes.
 *
 *
 *
 */
public class DBQuery {

    private static PreparedStatement statement;

    //Create statement object 
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);

    }

    public static PreparedStatement getPreparedStatement() {
        return statement;
    }

}
