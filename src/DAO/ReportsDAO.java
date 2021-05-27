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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;


/**
 *
 * @author brysa
 */
/**
 *
 * Class created to perform queries for certain kinds of reports based off
 * certain criteria. The criteria are typically easy to specify with SQL.
 *
 *
 *
 */
public class ReportsDAO {

    public static ObservableList<Appointment> getAptTypeReport(String type) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {

            String sqlSelect = "SELECT * FROM appointments WHERE Type = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sqlSelect);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, type);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");

                String cName = ContactDAO.getContactName(rs.getInt("Contact_ID"));
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), df);
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), df);
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appointmentId, title, desc, location, type, cName, start, end, customerId, userId, contactId);

                appointments.add(appointment);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;

    }

    public static int getAptByMonth(String month) {
        //  DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //   ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        int count = 0;

        try {

            String sqlSelect = "SELECT * FROM appointments WHERE monthname(Start - INTERVAL 2 HOUR) = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sqlSelect);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, month);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count++;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;

    }
    
    

    public static ObservableList<Appointment> getContactSchedule(int contactId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {

            String sqlSelect = "SELECT * FROM appointments WHERE Contact_ID = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sqlSelect);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, contactId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String cName = ContactDAO.getContactName(rs.getInt("Contact_ID"));
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), df);
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), df);
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointment appointment = new Appointment(appointmentId, title, desc, location, type, cName, start, end, customerId, userId, contactId);

                appointments.add(appointment);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return appointments;

    }
    
        public static ObservableList<Appointment> getAllApptsByMonth(String month) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {

            String sqlSelect = "SELECT * FROM appointments WHERE monthname(Start - INTERVAL 2 HOUR) = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sqlSelect);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, month);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String cName = ContactDAO.getContactName(rs.getInt("Contact_ID"));
                LocalDateTime start = LocalDateTime.parse(rs.getString("Start"), df);
                LocalDateTime end = LocalDateTime.parse(rs.getString("End"), df);
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appointmentId, title, desc, location, type, cName, start, end, customerId, userId, contactId);

                appointments.add(appointment);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return appointments;

    }
}
