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
 *
 * @author brysa
 */
/**
 *
 *
 *
 * CountryDAO class. This class contains static methods to access Country data.
 * This may have been unnecessary while I could have just removed the countries
 * we were not using within the application. But I wanted to get a better
 * understanding of using SQL and prepared statements.
 *
 *
 *
 *
 */
public class CountryDAO {

    public static ObservableList<Country> getCountries() {
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT Country_ID, Country FROM countries WHERE Country_ID = 38 OR Country_ID = 230 OR Country_ID = 231";

            Connection conn = DBConn.getConnection();

            DBQuery.setPreparedStatement(conn, sql);

            PreparedStatement ps = DBQuery.getPreparedStatement();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country c = new Country(countryId, countryName);

                countryList.add(c);

            }

        } catch (SQLException e) {
            e.getStackTrace();
        }

        return countryList;

    }

    public static Country getCountry(int id) throws SQLException {

        try{
        String sql = "SELECT Country_ID, Country FROM countries WHERE Country_ID = ?";

        Connection conn = DBConn.getConnection();

        DBQuery.setPreparedStatement(conn, sql);

        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        rs.next();

        int countryId = rs.getInt("Country_ID");
        String countryName = rs.getString("Country");

        Country country = new Country(countryId, countryName);
         return country;
        
          } catch (SQLException e) {
            e.getStackTrace();
        }

       return null;

    }

}
