/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AlertInterface;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import software_ii_project.Exceptions;
import software_ii_project.MyTime;

/**
 * FXML Controller class
 *
 * @author brysa
 */
/**
 *
 * Class to control the appointments within the interface. Through here, we can
 * save/update/delete appointments. As well as set up our UI elements like
 * tables, buttons, text-fields, etc.
 *
 *
 *
 */
public class Appointments_Controller implements Initializable {

    @FXML
    private TableView<Appointment> appointmentsTableview;

    @FXML
    private TableColumn<Appointment, Integer> aptIdCol;

    @FXML
    private TableColumn<Appointment, String> aptTitleCol;

    @FXML
    private TableColumn<Appointment, String> aptDescCol;

    @FXML
    private TableColumn<Appointment, String> aptLocationCol;

    @FXML
    private TableColumn<Appointment, String> aptContactCol;

    @FXML
    private TableColumn<Appointment, String> aptTypeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> aptStartTimeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> aptEndTimeCol;

    @FXML
    private TableColumn<Appointment, Integer> aptCustomerIdCol;

    @FXML
    private TextField aptIdTextfield;

    @FXML
    private TextField aptTitleTextfield;

    @FXML
    private TextField aptDescTextfield;

    @FXML
    private TextField aptLocationTextfield;

    @FXML
    private ComboBox<Contact> aptContactCombo;

    @FXML
    private ComboBox<String> aptTypeCombo;

    @FXML
    private DatePicker aptDatePicker;

    @FXML
    private ComboBox<LocalTime> aptStartTime;

    @FXML
    private TextField aptUserIdTextfield;

    @FXML
    private ComboBox<Customer> aptCustomerIdCombo;

    @FXML
    private ComboBox<LocalTime> aptEndTime;

    @FXML
    private ComboBox<User> aptUserIdCombo;

    @FXML
    private RadioButton ApptsByWeekRadio;

    @FXML
    private RadioButton ApptsByMonthRadio;

    @FXML
    private RadioButton ApptsByAllRadio;

    private ToggleGroup group = new ToggleGroup();

    private int storedSelectedItemId = 0;
    
    private ObservableList<String> types = FXCollections.observableArrayList("Full Check-Up - 2 Hours", "Bloodwork/Vaccination - 30 Minutes", "Check-Up - 1 Hour");

    AlertInterface newAlert = (type, s, x) -> {
        Alert alert = new Alert(type);
        alert.setTitle(s);
        alert.setContentText(x);
        alert.showAndWait();
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ZoneId origin = ZoneId.of("America/Vancouver");
        ZoneId target = ZoneId.systemDefault();

        LocalDateTime ldt = LocalDateTime.now();

        ZonedDateTime zdt = ldt.atZone(origin);

        zdt = zdt.withZoneSameInstant(target);

        ldt = zdt.toLocalDateTime();

        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        aptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        aptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        aptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        aptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        aptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        ApptsByAllRadio.setToggleGroup(group);
        ApptsByWeekRadio.setToggleGroup(group);
        ApptsByMonthRadio.setToggleGroup(group);
        ApptsByAllRadio.setSelected(true);

        //Declare two lists for contacts and types of appointments
        

        ObservableList<Customer> customers = CustomerDAO.getCustomers();

        aptIdTextfield.setDisable(true);
        aptIdTextfield.setText("AutoGen-Disabled");

        aptCustomerIdCombo.setItems(customers);
        aptCustomerIdCombo.setPromptText("Select Patient..");
        aptCustomerIdCombo.setVisibleRowCount(4);

        aptContactCombo.setItems(ContactDAO.getContacts());
        aptContactCombo.setPromptText("Select Doctor...");

        aptTypeCombo.setItems(types);
        aptTypeCombo.getSelectionModel().selectFirst();
        //  aptTypeCombo.setPromptText("Select Meeting type...");

        aptUserIdCombo.setItems(UserDAO.getUsers());
        aptUserIdCombo.setPromptText("Select User...");

        appointmentsTableview.setItems(AppointmentDAO.getAppointments());

        LocalDate date = aptDatePicker.getValue();

        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        while (startTime.isBefore(endTime.plusMinutes(1))) {
            aptStartTime.getItems().add(startTime);
            startTime = startTime.plusMinutes(30);

        }
        LocalTime selStartTime = aptStartTime.getValue();

        storedSelectedItemId = 0;

        // TODO
    }
    

    public void onSelectStartTimeCombo(ActionEvent event) {

        try {
            aptEndTime.getItems().clear();

            LocalTime selStartTime = aptStartTime.getSelectionModel().getSelectedItem();

            LocalTime newEndTime = selStartTime.plusMinutes(meetingTimes());

            aptEndTime.getItems().add(newEndTime);
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    /**
     * public int checkFields() { try { if ("Select
     * Contact...".equals(aptContactCombo.getPromptText())) { return 1; }
     *
     * if (aptDatePicker.getValue().equals(null)) { return 2; }
     *
     * if (aptStartTime.getPromptText().equals("Start Time") ||
     * aptEndTime.getPromptText().equals("End Time")) { return 3; } if
     * (aptUserIdCombo.getPromptText().equals("Select User...")) { return 4; }
     * if (aptCustomerIdCombo.getPromptText().equals("Select Customer...")) {
     * return 5; } } catch (NullPointerException e) { e.getMessage(); } return
     * 0; }
    *
     */
    public void onDatePickerSelected(ActionEvent event) {
        DayOfWeek saturday = DayOfWeek.SATURDAY;
        DayOfWeek sunday = DayOfWeek.SUNDAY;

        DayOfWeek dow = aptDatePicker.getValue().getDayOfWeek();
        if (dow == saturday || dow == sunday) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Cannot schedule appointments on Weekends");
            alert.showAndWait();

        }

    }

  
    

    public void onGetApptsByMonthComboPushed(ActionEvent event) {
        appointmentsTableview.setItems(AppointmentDAO.getApptsByMonth());

    }

    public void onGetApptsByWeekComboPushed(ActionEvent event) {
        appointmentsTableview.setItems(AppointmentDAO.getApptsByWeek());

    }

    public void onGetAllApptsComboPushed(ActionEvent event) {
        appointmentsTableview.setItems(AppointmentDAO.getAppointments());

    }

    public LocalDateTime getLdt() {
        ZoneId origin = ZoneId.of("America/Vancouver");
        ZoneId target = ZoneId.systemDefault();

        LocalDateTime ldt = LocalDateTime.now();

        ZonedDateTime zdt = ldt.atZone(origin);

        zdt = zdt.withZoneSameInstant(target);

        ldt = zdt.toLocalDateTime();

        return ldt;

    }

    public void onSaveAppointmentButtonPushed(ActionEvent event) {
        /**
         * switch (checkFields()) { case 1:
         * newAlert.displayAlert(Alert.AlertType.ERROR, "Contact", "Please
         * select an associated Contact."); break; case 2:
         * newAlert.displayAlert(Alert.AlertType.ERROR, "Date", "Please select a
         * date."); break; case 3: newAlert.displayAlert(Alert.AlertType.ERROR,
         * "Start/End Time", "Please select a start/end time"); break; case 4:
         * newAlert.displayAlert(Alert.AlertType.ERROR, "User", "Please select
         * an associated User."); break; case 5:
         * newAlert.displayAlert(Alert.AlertType.ERROR, "Customer", "Please
         * select an associated Customer."); break; default:
            * *
         */

        try {
            if (errorChecks()) {
                if (aptIdTextfield.getText().contains("AutoGen-Disabled")) {

                    AppointmentDAO.addAppointment(aptTitleTextfield.getText(), aptDescTextfield.getText(), aptLocationTextfield.getText(), aptTypeCombo.getSelectionModel().getSelectedItem(),
                            (LocalDateTime.of(aptDatePicker.getValue(), aptStartTime.getSelectionModel().getSelectedItem())), LocalDateTime.of(aptDatePicker.getValue(), aptEndTime.getSelectionModel().getSelectedItem()),
                            aptCustomerIdCombo.getSelectionModel().getSelectedItem().getCustomerId(), aptUserIdCombo.getSelectionModel().getSelectedItem().getUserId(),
                            aptContactCombo.getSelectionModel().getSelectedItem().getContactId(), aptContactCombo.getSelectionModel().getSelectedItem().getContactName());

                    appointmentsTableview.getItems().clear();
                    appointmentsTableview.setItems(AppointmentDAO.getAppointments());

                    clearFields();

                } else {

                    AppointmentDAO.updateAppointment(Integer.parseInt(aptIdTextfield.getText()), aptTitleTextfield.getText(), aptDescTextfield.getText(), aptLocationTextfield.getText(), aptTypeCombo.getSelectionModel().getSelectedItem(),
                            LocalDateTime.of(aptDatePicker.getValue(), aptStartTime.getSelectionModel().getSelectedItem()), LocalDateTime.of(aptDatePicker.getValue(), aptEndTime.getSelectionModel().getSelectedItem()),
                            aptCustomerIdCombo.getSelectionModel().getSelectedItem().getCustomerId(), aptUserIdCombo.getSelectionModel().getSelectedItem().getUserId(),
                            aptContactCombo.getSelectionModel().getSelectedItem().getContactId(), aptContactCombo.getSelectionModel().getSelectedItem().getContactName());

                    appointmentsTableview.getItems().clear();
                    appointmentsTableview.setItems(AppointmentDAO.getAppointments());
                }
            }
        } catch (NullPointerException e) {
            newAlert.displayAlert(Alert.AlertType.ERROR, "User/Customer/Contact", "Please check if an associated Contact/User/Customer has been selected");
        }
    }

    public void onUpdateAppointmentButtonPushed(ActionEvent event) {

        storedSelectedItemId = appointmentsTableview.getSelectionModel().getSelectedItem().getAppointmentId();

        Appointment appointment = appointmentsTableview.getSelectionModel().getSelectedItem();

        aptIdTextfield.setText(String.valueOf(appointment.getAppointmentId()));
        aptTitleTextfield.setText(appointment.getTitle());
        aptDescTextfield.setText(appointment.getDescription());
        aptLocationTextfield.setText(appointment.getLocation());
        aptTypeCombo.getSelectionModel().select(appointment.getType());
        aptStartTime.getSelectionModel().select(appointment.getStartDateTime().toLocalTime());
        aptEndTime.getSelectionModel().select(appointment.getEndDateTime().toLocalTime());
        aptCustomerIdCombo.getSelectionModel().select(CustomerDAO.getCustomerFromID(appointment.getCustomerId()));
        aptDatePicker.setValue(appointment.getStartDateTime().toLocalDate());
        aptContactCombo.getSelectionModel().select(ContactDAO.getContact(appointment.getContactId()));
        aptUserIdCombo.getSelectionModel().select(UserDAO.getUserFromID(appointment.getUserId()));

    }

    public void onDeleteAppointmentPushed(ActionEvent event) {
        AppointmentDAO.deleteAppointment(appointmentsTableview.getSelectionModel().getSelectedItem(), appointmentsTableview.getSelectionModel().getSelectedItem().getAppointmentId());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Canceled");
        alert.setContentText("Appointment: " + appointmentsTableview.getSelectionModel().getSelectedItem().getAppointmentId() + " -- "
                + appointmentsTableview.getSelectionModel().getSelectedItem().getType() + " -- has been canceled");
        alert.showAndWait();

        appointmentsTableview.getItems().clear();
        appointmentsTableview.setItems(AppointmentDAO.getAppointments());

    }

    public void onClearFieldsButtonPushed(ActionEvent event) {

        clearFields();

    }

    public void clearFields() {
        aptIdTextfield.setText("AutoGen-Disabled");

        aptTitleTextfield.clear();
        aptDescTextfield.clear();
        aptLocationTextfield.clear();
        aptTypeCombo.getSelectionModel().selectFirst();
        aptStartTime.getSelectionModel().select(null);
        aptEndTime.getSelectionModel().select(null);
        aptCustomerIdCombo.getSelectionModel().select(null);
        aptDatePicker.setValue(null);
        aptContactCombo.getSelectionModel().select(null);
        aptUserIdCombo.getSelectionModel().select(null);

        storedSelectedItemId = 0;

    }

    public void onTypeComboChosen(ActionEvent event) {
        aptStartTime.getItems().clear();
        try {
            LocalTime startTime = LocalTime.of(8, 0);
            LocalTime endTime = LocalTime.of(20, 0);

            if (aptTypeCombo.getSelectionModel().getSelectedIndex() == 0) {
                startTime = LocalTime.of(8, 0);
                endTime = LocalTime.of(20, 0);
            }
            if (aptTypeCombo.getSelectionModel().getSelectedIndex() == 1) {
                startTime = LocalTime.of(8, 0);
                endTime = LocalTime.of(21, 30);
            }
            if (aptTypeCombo.getSelectionModel().getSelectedIndex() == 2) {
                startTime = LocalTime.of(8, 0);
                endTime = LocalTime.of(21, 00);
            }

            while (startTime.isBefore(endTime.plusMinutes(1))) {
                aptStartTime.getItems().add(startTime);
                startTime = startTime.plusMinutes(30);

            }

            aptStartTime.getSelectionModel().select(null);
            aptEndTime.getSelectionModel().select(null);
        } catch (NullPointerException e) {
            e.getMessage();
        }

    }

    public void onBackToMainButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/view_controller/Main_Calendar.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public int meetingTimes() {
        int meetingTime;

        if (aptTypeCombo.getSelectionModel().getSelectedItem() == "Full Check-Up - 2 Hours") {
            meetingTime = 120;
        } else if (aptTypeCombo.getSelectionModel().getSelectedItem() == "Bloodwork/Vaccination - 30 Minutes") {
            meetingTime = 30;
        } else if (aptTypeCombo.getSelectionModel().getSelectedItem() == "Check-Up - 1 Hour") {
            meetingTime = 60;
        } else {
            return -1;
        }

        return meetingTime;
    }

    public boolean errorChecks() {

        DayOfWeek dow = aptDatePicker.getValue().getDayOfWeek();
        if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Cannot schedule appointments on Weekends");
            alert.showAndWait();
            return false;

        }

        ObservableList<Appointment> checkedAppts = AppointmentDAO.checkOverlappingAppointments(LocalDateTime.of(aptDatePicker.getValue(), aptStartTime.getSelectionModel().getSelectedItem()),
                LocalDateTime.of(aptDatePicker.getValue(), aptEndTime.getSelectionModel().getSelectedItem()),
                aptCustomerIdCombo.getSelectionModel().getSelectedItem().getCustomerId(), storedSelectedItemId);

        if (!checkedAppts.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Overlapping appointments, please schedule a different time");
            alert.showAndWait();
            return false;
        }

        return true;

    }

}
