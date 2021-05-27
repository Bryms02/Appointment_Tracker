/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_ii_project;

import DAO.ContactDAO;
import DAO.DBConn;
import DAO.DBQuery;
import DAO.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Contact;
import model.User;


/**
 *
 * @author brysa
 */
/**
 *
 * Loads our initial screen to log in. Starts and closes the connection to the
 * Database.
 *
 *
 *
 */
public class Software_II_Project extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

        DBConn.startConnection();

        //UserDAO.createUser("test", "test");
        //ContactDAO.addContact("Doctor Sydney", "DocSydney@maysfamilymedicine.com");
        //ContactDAO.addContact("Doctor Beth", "DocBeth@maysfamilymedicine.com");
        //ContactDAO.addContact("Doctor Smith", "DocSmith@maysfamilymedicine.com");
       
       
        launch(args);

        DBConn.closeConnection();

    }

}
