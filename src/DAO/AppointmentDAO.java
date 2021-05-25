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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import static java.time.temporal.TemporalQueries.zone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;
import software_ii_project.MyTime;


/**
 *
 * @author brysa
 */
/**
 *
 *
 *
 * Class created to interact with the database with the appointments we create,
 * update, or delete, using proper SQL queries to do so. This is a heavy class.
 * There are many different queries being performed through the methods in this
 * class. Including getting appointments by current month/week, and finding
 * appointments that have overlapping times with each other. This was the class I
 * spent the most amount of time on.
 *
 *
 *
 *
 */
public class AppointmentDAO {

    public static ObservableList<Appointment> getAppointments() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {

            String selectSql = "SELECT * FROM appointments";

            Connection conn = DBConn.getConnection();
            DBQuery.setPreparedStatement(conn, selectSql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String cName = ContactDAO.getContactName(rs.getInt("Contact_ID"));
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appointmentId, title, desc, location, type, cName, start, end, customerId, userId, contactId);

                appointmentsList.add(appointment);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;

    }

    public static ObservableList<Appointment> getApptsByMonthNew() {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        for (int i = 0; i < getAppointments().size(); i++) {
            if (getAppointments().get(i).getStartDateTime().getMonth() == LocalDateTime.now().getMonth()) {
                appointmentsList.add(getAppointments().get(i));

            }

        }

        return appointmentsList;

    }

    public static ObservableList<Appointment> getApptsByMonth() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {

            String selectSql = "SELECT * FROM appointments WHERE MONTH(Start - INTERVAL 2 HOUR) = MONTH(NOW())";

            Connection conn = DBConn.getConnection();
            DBQuery.setPreparedStatement(conn, selectSql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String cName = ContactDAO.getContactName(rs.getInt("Contact_ID"));
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appointmentId, title, desc, location, type, cName, start, end, customerId, userId, contactId);

                appointmentsList.add(appointment);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;

    }

    public static ObservableList<Appointment> getApptsByWeek() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {

            String selectSql = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(CURRENT_DATE())";

            Connection conn = DBConn.getConnection();
            DBQuery.setPreparedStatement(conn, selectSql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String cName = ContactDAO.getContactName(rs.getInt("Contact_ID"));
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appointmentId, title, desc, location, type, cName, start, end, customerId, userId, contactId);

                appointmentsList.add(appointment);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;

    }

    public static LocalDateTime updateStartTime(LocalDateTime ldt, int id) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String updateSql = "UPDATE appointments SET Start = ? WHERE Appointment_ID = ?";
        try {
            Connection conn = DBConn.getConnection();
            DBQuery.setPreparedStatement(conn, updateSql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.execute();

            return ldt;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void addAppointment(String title, String desc, String location, String type, LocalDateTime start,
            LocalDateTime end, int customerId, int userId, int contactId, String contactName) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {

            String insertSql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) "
                    + "VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Connection conn = DBConn.getConnection();

            PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf((start).format(dtf)));
            ps.setTimestamp(6, Timestamp.valueOf((end).format(dtf)));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();

            rs.next();

            int appointmentId = rs.getInt(1);

            Appointment newAppointment = new Appointment(appointmentId, title, desc, type, contactName, location, start, end, customerId, userId, contactId);

            getAppointments().add(newAppointment);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateAppointment(int appointmentId, String title, String desc, String location, String type, LocalDateTime start,
            LocalDateTime end, int customerId, int userId, int contactId, String contactName) {
        try {

            String updateSql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, updateSql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.setInt(10, appointmentId);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ObservableList<Appointment> checkOverlappingAppointments(LocalDateTime ldtStart, LocalDateTime ldtEnd, int id, int aptId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {
            String selectSql = "SELECT * FROM appointments WHERE (Customer_ID = ?) AND (START BETWEEN ? AND ? OR END BETWEEN ? AND ?) AND (Appointment_ID <> ?)";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, selectSql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setTimestamp(2, Timestamp.valueOf(ldtStart));
            ps.setTimestamp(3, Timestamp.valueOf(ldtEnd));

            ps.setTimestamp(4, Timestamp.valueOf(ldtStart));
            ps.setTimestamp(5, Timestamp.valueOf(ldtEnd));

            ps.setInt(1, id);
            ps.setInt(6, aptId);

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

                System.out.println(appointmentId + " FOUND");

                appointmentsList.add(appointment);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;

    }

    public static void deleteAppointment(Appointment appointment, int id) {
        try {

            String sqlDelete = "DELETE FROM appointments WHERE Appointment_ID = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sqlDelete);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, id);

            ps.execute();

            getAppointments().remove(appointment);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ObservableList<Appointment> getUpcomingAppointments() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {
            String selectSql = "SELECT * FROM appointments WHERE Start BETWEEN ? AND ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, selectSql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            LocalDateTime ldtn = LocalDateTime.now();
            LocalDateTime ldt = LocalDateTime.now().plusMinutes(15);

            ps.setTimestamp(1, Timestamp.valueOf(ldtn));
            ps.setTimestamp(2, Timestamp.valueOf(ldt));

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

                appointmentsList.add(appointment);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;

    }

    public static Appointment getAppointment(int id) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {

            String selectSql = "SELECT * FROM appointments WHERE Appointment_ID = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, selectSql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, id);

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

                return appointment;

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;

    }

}
