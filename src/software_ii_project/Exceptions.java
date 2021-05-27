/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_ii_project;

/**
 *
 * @author brysa
 */
/**
 *
 *
 *
 * Class created that has one static method to perform exception control on string and integer textfields. 
 *
 *
 *
 *
 */
public class Exceptions {
    
    public static boolean isInt(String s)
    {
        try{
            int n = Integer.parseInt(s);
            return true;
        }
        catch(NumberFormatException e)
        {
            
           
        }
        return false;
    }
    
    
     
    
}
