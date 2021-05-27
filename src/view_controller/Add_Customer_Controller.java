/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.DivisionDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AlertInterface;

import model.Country;
import model.Customer;
import model.Division;
import software_ii_project.Exceptions;

/**
 * FXML Controller class
 *
 * @author brysa
 */
/**
 *
 * LAMBDA EXPRESSION HERE (Used in onDeleteCustomerButtonPusehed Method) This class is used to add, update, and delete customers in the database. It
 * also initializes table/text variables and data. *
 *
 *
 */
public class Add_Customer_Controller implements Initializable {

    @FXML
    private TableView<Customer> customersTableview;

    @FXML
    private TableColumn<Customer, Integer> idCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, Integer> divisionCol;

    @FXML
    private ComboBox<Country> customerCountryCombo;
    
    @FXML
    private TextField patientSearchField;

    @FXML
    private TextField customerIdTextfield;

    @FXML
    private TextField customerNameTextfield;

    @FXML
    private TextField customerAddressTextfield;

    @FXML
    private TextField customerCityTextfield;

    @FXML
    private ComboBox<Division> divisionCombo;

    @FXML
    private TextField customerPostalTextfield;

    @FXML
    private TextField customerPhoneTextfield;

    AlertInterface newAlert = (type, s, x) -> {
        Alert alert = new Alert(type);
        alert.setTitle(s);
        alert.setContentText(x);
        alert.showAndWait();
    };

    public void countryComboboxChosen(ActionEvent event) {

        try {
            Country country = customerCountryCombo.getSelectionModel().getSelectedItem();

            divisionCombo.getItems().clear();

            divisionCombo.setItems(DivisionDAO.getDivisions(country.getId()));

            if (country.getId() == 38) {
                divisionCombo.setPromptText("Province");
            }
            if (country.getId() == 230) {
                divisionCombo.setPromptText("City");
            }

            if (country.getId() == 231) {
                divisionCombo.setPromptText("State");
            }

        } catch (NullPointerException e) {
            e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
        @FXML
    public void searchButtonPushed() {

        String q = patientSearchField.getText();

        ObservableList<Customer> customers = CustomerDAO.lookupCustomer(q);

        customersTableview.setItems(customers);

        patientSearchField.setText("");

        if (customers.size() == 0){
            try {

            {

                int index = Integer.parseInt(q);
                Customer customer = CustomerDAO.getCustomerFromID(index);
                if (customer != null) {
                    customers.add(customer);
                } else {
                    customersTableview.setItems(CustomerDAO.getCustomers());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error dialog");
                    alert.setContentText("No patient found");
                    alert.showAndWait();
                    

                }
            }
        } catch (NumberFormatException e) {
            customersTableview.setItems(CustomerDAO.getCustomers());
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error dialog");
                    alert.setContentText("No patient found");
                    alert.showAndWait();


        }
            

    }
    }

    public void setComboBoxes() {

        ObservableList<Country> countries = CountryDAO.getCountries();
        try {

            divisionCombo.getSelectionModel().select(null);
            customerCountryCombo.getSelectionModel().select(null);

            divisionCombo.getItems().clear();
            customerCountryCombo.getItems().clear();

            customerCountryCombo.setItems(countries);
            customerCountryCombo.setPromptText("Country");
            divisionCombo.setPromptText("Division");
        } catch (NullPointerException e) {
            e.getMessage();
        }

    }

    public int checkFields() {
        if (customerNameTextfield.getText().isEmpty() || customerAddressTextfield.getText().isEmpty() || customerCityTextfield.getText().isEmpty() || customerPostalTextfield.getText().isEmpty() || customerPhoneTextfield.getText().isEmpty()) {
            return 1;
        }
        if (Exceptions.isInt(customerNameTextfield.getText())) {
            return 2;
        }
        try {

        } catch (NullPointerException e) {
            e.getMessage();
        }
        return 0;

    }

    public void onAddCustomerButtonPushed(ActionEvent event) {

        switch (checkFields()) {
            case 1:
                newAlert.displayAlert(Alert.AlertType.ERROR, "Field(s) Empty", "Please fill in text fields.");
                break;
            case 2:
                newAlert.displayAlert(Alert.AlertType.ERROR, "Invalid name", "Please enter a valid name.");
                break;

            default:
                try {

                if (customerIdTextfield.getText().contains("AutoGen-Disabled")) {

                    if (customerCountryCombo.getSelectionModel().getSelectedItem().getId() == 230) {
                        CustomerDAO.createCustomer(customerNameTextfield.getText(), customerAddressTextfield.getText() + ", " + customerCityTextfield.getText()
                                + ", " + divisionCombo.getSelectionModel().getSelectedItem().getName(), customerPostalTextfield.getText(), customerPhoneTextfield.getText(), customerCityTextfield.getText(), divisionCombo.getSelectionModel().getSelectedItem().getId());
                    } else {
                        CustomerDAO.createCustomer(customerNameTextfield.getText(), customerAddressTextfield.getText()
                                + ", " + customerCityTextfield.getText(), customerPostalTextfield.getText(), customerPhoneTextfield.getText(), customerCityTextfield.getText(), divisionCombo.getSelectionModel().getSelectedItem().getId());
                    }
                    customerNameTextfield.clear();
                    customerCityTextfield.clear();
                    customerPostalTextfield.clear();
                    customerPhoneTextfield.clear();
                    customerAddressTextfield.clear();

                    setComboBoxes();

                    customersTableview.getItems().clear();
                    customersTableview.setItems(CustomerDAO.getCustomers());

                } else {

                    if (customerCountryCombo.getSelectionModel().getSelectedItem().getId() == 230) {

                        CustomerDAO.updateCustomer(Integer.parseInt(customerIdTextfield.getText()), customerNameTextfield.getText(), customerAddressTextfield.getText() + ", " + customerCityTextfield.getText() + ", " + divisionCombo.getSelectionModel().getSelectedItem().getName(), customerPostalTextfield.getText(), customerPhoneTextfield.getText(), divisionCombo.getSelectionModel().getSelectedItem().getId());
                    } else {
                        CustomerDAO.updateCustomer(Integer.parseInt(customerIdTextfield.getText()), customerNameTextfield.getText(), customerAddressTextfield.getText() + ", " + customerCityTextfield.getText(), customerPostalTextfield.getText(), customerPhoneTextfield.getText(), divisionCombo.getSelectionModel().getSelectedItem().getId());

                    }
                    customersTableview.getItems().clear();
                    customersTableview.setItems(CustomerDAO.getCustomers());
                }

            } catch (NullPointerException e) {
                newAlert.displayAlert(Alert.AlertType.ERROR, "Country/Division", "Select a Country/Division.");

            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            break;
        }
    }

    public void onEditCustomerButtonPushed(ActionEvent event) throws SQLException {
        Customer customer = customersTableview.getSelectionModel().getSelectedItem();

        customerIdTextfield.setText(String.valueOf(customer.getCustomerId()));
        customerNameTextfield.setText(customer.getCustomerName());
        customerAddressTextfield.setText(customer.getAddressSubstring(customer.getAddress()));
        customerPostalTextfield.setText(customer.getPostal());
        customerPhoneTextfield.setText(customer.getPhone());
        customerCityTextfield.setText(customer.getCitySubstring(customer.getAddress()));

        setComboBoxes();

        customerCountryCombo.getSelectionModel().select(CountryDAO.getCountry(DivisionDAO.getDiv(customer.getDivId()).getCountryId()));
        divisionCombo.getSelectionModel().select(DivisionDAO.getDiv(customer.getDivId()));

    }

    /**
     * The lambda expressions used in the below method are meant to make it
     * easier to display alerts. There are several fields that are used for the
     * alerts, two strings and an Alert type. When we call dislayAlert using the
     * fields, we can quickly create a new alert.
     *
     */
    public void onDeleteCustomerButtonPushed(ActionEvent event) {
        Customer customer = customersTableview.getSelectionModel().getSelectedItem();
        if (!CustomerDAO.deleteCustomer(customer, customer.getCustomerId())) {

            newAlert.displayAlert(Alert.AlertType.INFORMATION, "Success", "Customer has been deleted successfully");

        } else {

            newAlert.displayAlert(Alert.AlertType.ERROR, "Error Dialogue", "Error removing customer, remove customer appointments first");

        }
        // CustomerDAO.printCustomersList();
        customersTableview.getItems().clear();
        customersTableview.setItems(CustomerDAO.getCustomers());

    }

    public void onClearFieldsButtonPushed(ActionEvent event) {

        customerIdTextfield.setText("AutoGen-Disabled");

        customerNameTextfield.clear();
        customerCityTextfield.clear();
        customerPostalTextfield.clear();
        customerPhoneTextfield.clear();
        customerAddressTextfield.clear();

        setComboBoxes();

    }

    public void onBackToMainButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/view_controller/Main_Calendar.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ObservableList<Country> countries = CountryDAO.getCountries();

        customerCountryCombo.setItems(countries);
        customerCountryCombo.setPromptText("Country");
        divisionCombo.setPromptText("Division");

        customerIdTextfield.setDisable(true);
        customerIdTextfield.setText("AutoGen-Disabled");

        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divId"));

        customersTableview.getItems().clear();
        customersTableview.setItems(CustomerDAO.getCustomers());

    }

}
