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
public class AdminUser extends User {
    
    private String privelages;
    
    public AdminUser(int userId, String username, String password, boolean isCurrentUser, String privelages) {
        super(userId, username, password, isCurrentUser);
        this.privelages = "ALL";
    }

    public String getPrivelages() {
        return privelages;
    }

    public void setPrivelages(String privelages) {
        this.privelages = privelages;
    }
    
    
    
}
