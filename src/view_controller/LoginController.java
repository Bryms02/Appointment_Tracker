/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import static java.time.temporal.TemporalQueries.zone;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.LoginInterface;

/**
 *
 * @author brysa
 */
/**
 *LAMBDA EXPRESSION HERE (Used in the Initialize method)
 * Login Controller - Controls the initial scene for logging into the
 * application. Prompts the user to enter a username and password, translates
 * the scene and error text to french if the language is adjusted on the user's
 * system. This is also where we write out login attempts to a text file within
 * the main project folder.
 *
 *
 *
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameTextfield;

    @FXML
    private TextField passwordTextfield;

    @FXML
    private Label loginLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private Button loginButton;

    @FXML
    private Label currentLocationLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label timeLbl;

    private ResourceBundle rb = ResourceBundle.getBundle("software_ii_project/Nat", Locale.getDefault());

    private int loginAttempts = 0;
    //Lambda Expression used
    LoginInterface login = (username, password) -> {
        usernameTextfield.setText(username);
        passwordTextfield.setText(password);

    };

    public void loginButtonPushed(ActionEvent event) throws SQLException, IOException {

        String filename = "login_activity.txt", attempt;

        FileWriter fw = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fw);

        if (UserDAO.queryUser(usernameTextfield.getText(), passwordTextfield.getText()) != null) {

            //Login successful write to file
            attempt = "Username: ---   " + usernameTextfield.getText() + "  ---   SUCCESSFULLY logged in at   ---   " + LocalDateTime.now();

            outputFile.println(attempt);

            outputFile.close();

            if (AppointmentDAO.getUpcomingAppointments().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming appointments");
                alert.setContentText("No Upcoming Appointments");

                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming appointments");
                alert.setContentText("You have upcoming " + AppointmentDAO.getUpcomingAppointments().size() + " appointment(s)\n" + displayUpcomingAppointments());

                alert.showAndWait();
            }

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/view_controller/Main_Calendar.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();

        } else {

            //Login attempt failed write to file
            attempt = "Username: ---   " + usernameTextfield.getText() + "  ---   FAILED log in attempt at   ---   " + LocalDateTime.now();

            outputFile.println(attempt);

            outputFile.close();

            if (Locale.getDefault().getLanguage().equals("fr")) {
                //ALERT NOTIFICATION HERE WITH LANGUAGE CHECK
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("invalid") + " " + rb.getString("username") + " " + rb.getString("or") + " " + rb.getString("password"));
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid Username / Password");
                alert.showAndWait();
            }

        }

       
    }
     /**
         * The lambda expression used here is meant to make it easier to set the
         * login and password of the login window. Through calling the setLogin
         * functional method on the login lambda expression, we can quickly set
         * it to the desired username and password using the two String
         * parameters.
         *
         */

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentLocationLbl.setText(String.valueOf(ZonedDateTime.now().getZone()));

        timeLbl.setText(String.valueOf(LocalDateTime.now()));

        //Lambda Expression used
        login.setLogin("test", "test");

        rb = this.rb;

        if (Locale.getDefault().getLanguage().equals("fr")) {
            loginLbl.setText(rb.getString("login"));
            usernameLbl.setText(rb.getString("username"));
            passwordLbl.setText(rb.getString("password"));
            loginButton.setText(rb.getString("login"));
            locationLbl.setText(rb.getString("location") + ":");
        }

        // TODO
    }

    public static String displayUpcomingAppointments() {
        ObservableList<Appointment> appointments = AppointmentDAO.getUpcomingAppointments();

        String text = "";

        for (int i = 0; i < appointments.size(); i++) {
            text = text + "Appointment ID: " + appointments.get(i).getAppointmentId() + "  ||  " + "Date/Time: " + appointments.get(i).getStartDateTime();

        }

        return text;

    }

    public void writeToFile() throws IOException {
        String filename = "login_activity.txt", attempt;

        FileWriter fw = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fw);
        attempt = "Username: ---   " + usernameTextfield.getText() + "  ---   SUCCESSFULLY logged in at   ---   " + LocalDateTime.now();

        outputFile.println(attempt);

        outputFile.close();

    }

}
