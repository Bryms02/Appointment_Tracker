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
import model.Division;

/**
 *
 * @author brysa
 */
/**
 *
 *
 *
 * ContactDAO class. This class has static methods that allow us to get our
 * contacts in different ways. We can do a SELECT query to get all of them, add
 * a contact through a name/email parameter, get the name of the contact based
 * on the ID, and get the actual contact object through the ID. All of these
 * methods use queries to interact with the database contact data.
 *
 *
 *
 *
 */
public class ContactDAO {

    public static ObservableList<Contact> getContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try {

            String sqlSelect = "SELECT * FROM contacts";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sqlSelect);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact contact = new Contact(id, name, email);
                contactList.add(contact);

            }

        } catch (SQLException e) {
            e.getMessage();
        }

        return contactList;

    }

    public static void addContact(String name, String email) {

        try {

            String insertSql = "INSERT INTO contacts (Contact_ID, Contact_Name, Email) VALUES (NULL, ?, ?)";

            Connection conn = DBConn.getConnection();

            PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, email);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            rs.next();

            int id = rs.getInt(1);

            Contact contact = new Contact(id, name, email);

            getContacts().add(contact);

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public static String getContactName(int id) {

        try {

            String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts WHERE Contact_ID = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            rs.next();

            return rs.getString("Contact_Name");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static Contact getContact(int id) {

        try {

            String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts WHERE Contact_ID = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            rs.next();

            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact contact = new Contact(id, contactName, email);

            return contact;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

}
