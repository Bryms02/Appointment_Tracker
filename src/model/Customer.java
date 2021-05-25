/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author brysa
 */
/**
 *
 *
 *
 * Class created to correspond with customer data containing an ID, name,
 * address, postal code, phone number, division ID, and a city for each
 * customer, as well as methods to get or set each of those variables.
 *
 *
 *
 *
 */
public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postal;
    private String phone;
    private int divId;
    private String city;

    public Customer(int customerId, String customerName, String address, String postal, String phone, int divId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.divId = divId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivId() {
        return divId;
    }

    public void setDivId(int divId) {
        this.divId = divId;
    }

    public String getCitySubstring(String address) {

        int firstCommaIndex = address.indexOf(',');

        int secondCommaIndex = address.lastIndexOf(',');

        if (firstCommaIndex == secondCommaIndex) {
            return address.substring(firstCommaIndex + 2, address.length());
        } else {
            return address.substring(firstCommaIndex + 2, secondCommaIndex);
        }

    }

    public String getAddressSubstring(String address) {
        int commaIndex = address.indexOf(',');

        String addressSub = address.substring(0, commaIndex);

        return addressSub;
    }

    @Override
    public String toString() {
        return ("ID: " + customerId + " || Name: " + customerName + " || Address: " + address + " || Postal: " + postal + " || Phone: " + phone + " || Division_ID: " + divId);
    }

}
