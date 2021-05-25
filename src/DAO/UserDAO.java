/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.User;


/**
 *
 * @author brysa
 */
/**
  
Class used to interact with the database using the "user" class. Gets, created, as well as queries users when prompted to log in. 
  
  
  
*/
public class UserDAO {

    //  private static ObservableList<User> users = FXCollections.observableArrayList();
    public static ObservableList<User> getUsers() {

        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            String selectSql = "SELECT User_ID, User_Name, Password FROM users";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, selectSql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");

                User user = new User(id, username, password, false);

                users.add(user);

            }

        } catch (SQLException e) {
            e.getMessage();
        }
        return users;

    }

    public static void createUser(String username, String password) throws SQLException {
        //Add a new user to the observable list

        Connection conn = DBConn.getConnection();
        //Sql Insert Statement
        String insertStatement = "INSERT INTO users(User_ID, User_Name, Password) VALUES(NULL,?,?)";

        PreparedStatement ps = conn.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, username);
        ps.setString(2, password);

        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();

        int userId = rs.getInt(1);

        User user = new User(userId, username, password, false);

        getUsers().add(user);

    }

    public static String queryUser(String username, String password) throws SQLException {

        Connection conn = DBConn.getConnection();

        // Select statement to find where the username and password mathc the arguments
        String selectStatement = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";

        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, username);
        ps.setString(2, password);

        ps.execute();

        ResultSet rs = ps.getResultSet();

        if (rs.next()) {
            return username;
        } else {
            return null;
        }

    }

    public static User getUser(String username) {

        try {

            Connection conn = DBConn.getConnection();

            // Select statement to find where the username and password mathc the arguments
            String selectStatement = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ?";

            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            rs.next();

            int userId = rs.getInt("User_Id");
            String password = rs.getString("Password");

            User user = new User(userId, username, password, false);
            return user;

        } catch (SQLException e) {
            e.getMessage();
        }

        return null;

    }

    public static User getUserFromID(int id) {

        try {

            String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_ID = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            rs.next();

            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");

            User user = new User(id, userName, password, false);

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

}
