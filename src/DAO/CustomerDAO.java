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
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;

/**
 *
 * @author brysa
 */
/**
 *
 * Class created that has multiple static methods to interact with database
 * Customer data. Select, update, delete, and insert queries are used throughout
 * this class.
 *
 *
 *
 */
public class CustomerDAO {

    //private static ObservableList<Customer> customersList = FXCollections.observableArrayList();
    public static ObservableList<Customer> getCustomers() {

        ObservableList<Customer> customersList = FXCollections.observableArrayList();

        try {
            String selectStatement = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, selectStatement);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divId = rs.getInt("Division_ID");

                Customer customer = new Customer(customerId, name, address, postal, phone, divId);

                customersList.add(customer);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customersList;

    }

    public static void createCustomer(String name, String address, String postal, String phone, String city, int divId) throws SQLException {

        try {
            String sqlInsert = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(NULL, ?, ?, ?, ?, ?)";
            Connection conn = DBConn.getConnection();

            PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setInt(5, divId);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int customerId = rs.getInt(1);

            Customer newCustomer = new Customer(customerId, name, address, postal, phone, divId);

            getCustomers().add(newCustomer);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateCustomer(int id, String name, String address, String postal, String phone, int divId) {
        try {
            String updateSql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, updateSql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setInt(5, divId);

            ps.setInt(6, id);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean deleteCustomer(Customer customer, int id) {
        try {
            String delStatement = "DELETE FROM customers WHERE Customer_ID = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, delStatement);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, id);

            ps.execute();

            getCustomers().remove(customer);

        } catch (SQLException e) {
            e.getMessage();
        }

        return checkCustomer(id);

    }

    public static Customer getCustomerFromID(int id) {

        try {

            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            rs.next();

            int customerId = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divId = rs.getInt("Division_ID");

            Customer customer = new Customer(customerId, name, address, postal, phone, divId);

            return customer;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static ObservableList<Customer> lookupCustomer(String customerName) {

        ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();

        for (Customer customer : getCustomers()) {
            if (customer.getCustomerName().contains(customerName)) {
                filteredCustomers.add(customer);
            }
        }
        return filteredCustomers;
    }

    public static void printCustomersList() {
        for (int i = 0; i < getCustomers().size(); i++) {
            System.out.println(getCustomers().get(i).getCustomerName());
        }
    }

    public static boolean checkCustomer(int id) {
        for (int i = 0; i < getCustomers().size(); i++) {
            if (getCustomers().get(i).getCustomerId() == id) {
                return true;
            }

        }
        return false;
    }

    public static int getApptsForCustomer(int id) {
        try {

            String sql = "SELECT DISTINCT customers.Customer_ID FROM customers, appointments WHERE customers.Customer_ID = appointments.Customer_ID";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return -1;

    }

    public static int getCountryCtFromCustomers(int from, int to) {
        try {

            int count = 0;

            String sql = "SELECT Customer_ID FROM customers WHERE Division_ID BETWEEN ? AND ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, from);
            ps.setInt(2, to);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count++;
            }
            return count;
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;

    }
    
        public static ObservableList<Customer> getCustomersFromCountry(int from, int to) {
            ObservableList<Customer> customersList = FXCollections.observableArrayList();
            
        try {

          

            String sql = "SELECT * FROM customers WHERE Division_ID BETWEEN ? AND ?";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setInt(1, from);
            ps.setInt(2, to);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divId = rs.getInt("Division_ID");

            Customer customer = new Customer(customerId, name, address, postal, phone, divId);
            
            customersList.add(customer);
            
            }
            
            
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return customersList;

    }

}
