/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.ReportsDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Country;
import model.Customer;

/**
 * FXML Controller class
 *
 * @author brysa
 */
/**
 *
 * Controller class used to interact with the UI elements for our reports.
 * Including initialized list-view and labeled data. *
 *
 *
 */
public class Reports_Controller implements Initializable {

    @FXML
    private Label totalAppointmentsLbl;

    @FXML
    private ListView<Appointment> businessListview;

    @FXML
    private ListView<Appointment> quickListview;

    @FXML
    private ListView<Appointment> fullListview;

    @FXML
    private Label fullCtLbl;

    @FXML
    private Label quickCtLbl;

    @FXML
    private Label businessCtLbl;

    @FXML
    private Label janCtLbl;

    @FXML
    private Label febCtLbl;

    @FXML
    private Label marchCtLbl;

    @FXML
    private Label aprCtLbl;

    @FXML
    private Label mayCtLbl;

    @FXML
    private Label juneCtLbl;

    @FXML
    private Label julyCtLbl;

    @FXML
    private Label augCtLbl;

    @FXML
    private Label sepCtLbl;

    @FXML
    private Label octCtLbl;

    @FXML
    private Label novCtLbl;

    @FXML
    private Label decCtLbl;

    @FXML
    private ListView<Appointment> contact1ListView;

    @FXML
    private ListView<Appointment> contact2Listview;

    @FXML
    private ListView<Appointment> contact3Listview;

    @FXML
    private Label totalCustomersLbl;

    @FXML
    private Label usTotalCustomers;

    @FXML
    private Label ukTotalCustomers;

    @FXML
    private Label canadaTotalCustomers;

    @FXML
    private ListView<Appointment> newListView;

    @FXML
    private ListView<Customer> newPatientsListView;

    @FXML
    private ComboBox monthCombo;

    @FXML
    private ComboBox typesCombo;

    @FXML
    private ComboBox doctorsCombo;

    @FXML
    private ComboBox regionCombo;

    private Contact selectedDoctor;

    private Country selectedRegion;

    private String selectedMonth;
    private String selectedType;

    /**
     *
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll("January", "Feburary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        ObservableList<String> types = FXCollections.observableArrayList("Full Check-Up - 2 Hours", "Bloodwork/Vaccination - 30 Minutes", "Check-Up - 1 Hour");

        monthCombo.setItems(months);
        monthCombo.getSelectionModel().selectFirst();

        typesCombo.setItems(types);
        typesCombo.getSelectionModel().selectFirst();

        doctorsCombo.setItems(ContactDAO.getContacts());
        doctorsCombo.getSelectionModel().selectFirst();

        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            countries.add(CountryDAO.getCountry(38));
            countries.add(CountryDAO.getCountry(230));
            countries.add(CountryDAO.getCountry(231));
        } catch (SQLException ex) {
            Logger.getLogger(Reports_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        regionCombo.setItems(countries);
        regionCombo.getSelectionModel().selectFirst();

        
        

        // TODO
    }

    public void onBackToMainButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/view_controller/Main_Calendar.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void onDisplayApptsByMonthPushed(ActionEvent event) {
        selectedMonth = monthCombo.getSelectionModel().getSelectedItem().toString();
        newListView.setItems(ReportsDAO.getAllApptsByMonth(selectedMonth));
        totalAppointmentsLbl.setText(String.valueOf(newListView.getItems().size()));

    }

    public void onDisplayAllApptsPushed(ActionEvent event) {

        newListView.setItems(AppointmentDAO.getAppointments());
        totalAppointmentsLbl.setText(String.valueOf(newListView.getItems().size()));

    }

    public void onDisplayAllPatientsPushed(ActionEvent event) {

        newPatientsListView.setItems(CustomerDAO.getCustomers());
        totalCustomersLbl.setText(String.valueOf(newPatientsListView.getItems().size()));
        

    }

    public void onDisplayApptsByTypePushed(ActionEvent event) {
        selectedType = typesCombo.getSelectionModel().getSelectedItem().toString();
        newListView.setItems(ReportsDAO.getAptTypeReport(selectedType));
        totalAppointmentsLbl.setText(String.valueOf(newListView.getItems().size()));

    }

    public void onDisplayApptsByDoctorPushed(ActionEvent event) {
        selectedDoctor = (Contact) doctorsCombo.getSelectionModel().getSelectedItem();
        int id = selectedDoctor.getContactId();

        newListView.setItems(ReportsDAO.getContactSchedule(id));
        totalAppointmentsLbl.setText(String.valueOf(newListView.getItems().size()));

    }

    public void onDisplayPatientsByRegionPushed(ActionEvent event) {
        selectedRegion = (Country) regionCombo.getSelectionModel().getSelectedItem();
        if (selectedRegion.getId() == 38) {
            newPatientsListView.setItems(CustomerDAO.getCustomersFromCountry(663, 675));
        } else if (selectedRegion.getId() == 230) {
            newPatientsListView.setItems(CustomerDAO.getCustomersFromCountry(3805, 3918));
        } else if (selectedRegion.getId() == 231) {
            newPatientsListView.setItems(CustomerDAO.getCustomersFromCountry(3919, 3978));
        }
        totalCustomersLbl.setText(String.valueOf(newPatientsListView.getItems().size()));

    }

}
