/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.Alert;

/**
 *
 * @author brysa
 */
/**
 *
 * Interface created to demonstrate lambda expression making alerts a little
 * easier to create if there are multiple being used. The method created takes
 * an alert type, and two strings.
 *
 *
 */
public interface AlertInterface {

    void displayAlert(Alert.AlertType type, String s, String x);

}
