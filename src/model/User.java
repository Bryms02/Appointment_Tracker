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
 * User class created to be able to construct user objects with an ID, username,
 * and password, used for logging into the application.
 *
 *
 *
 *
 */
public class User {

    private int userId;
    private String username;
    private String password;
    private boolean isCurrentUser;

    public User(int userId, String username, String password, boolean isCurrentUser) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isCurrentUser = false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsCurrentUser() {
        return isCurrentUser;
    }

    public void setIsCurrentUser(boolean isCurrentUser) {
        this.isCurrentUser = isCurrentUser;
    }

    @Override
    public String toString() {
        return ("User ID: " + userId);
    }

}
