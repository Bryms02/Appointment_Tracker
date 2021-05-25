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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

/**
 * Class for interacting with divisions in the database, and performing queries
 * to get a list of divisions, or get a particular division.  *
 *
 *
 */
/**
 *
 * @author brysa
 */
public class DivisionDAO {

    private static ObservableList<Division> divList = FXCollections.observableArrayList();

    public static ObservableList<Division> getAllDivisions() {
        return divList;
    }

    public static ObservableList<Division> getDivisions(int countryId) throws SQLException {
        //ObservableList<Division> divList = FXCollections.observableArrayList();

        String sql = "SELECT Division_ID, Division, COUNTRY_ID FROM first_level_divisions WHERE COUNTRY_ID = ?";

        Connection conn = DBConn.getConnection();

        DBQuery.setPreparedStatement(conn, sql);

        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1, countryId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");

            Division d = new Division(divisionId, divisionName, countryId);

            divList.add(d);

        }

        return divList;

    }

    public static Division getDiv(int id) throws SQLException {

        String sql = "SELECT Division_ID, Division, COUNTRY_ID FROM first_level_divisions WHERE Division_ID = ?";

        Connection conn = DBConn.getConnection();

        DBQuery.setPreparedStatement(conn, sql);

        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        rs.next();

        int divisionId = rs.getInt("Division_ID");
        String divisionName = rs.getString("Division");
        int countryId = rs.getInt("COUNTRY_ID");

        Division division = new Division(divisionId, divisionName, countryId);

        return division;

    }

}
