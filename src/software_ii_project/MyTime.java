/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_ii_project;

import DAO.DBConn;
import DAO.DBQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author brysa
 */

/**
 *
 * Time class created for time conversion. It seems that it is not necessary
 * with 8.23 driver, however I could be wrong.  *
 *
 *
 */
public class MyTime {

    public static LocalDateTime convertLdt(LocalDateTime ldt) {

        ZoneId origin = ZoneId.of("America/New_York");
        ZoneId target = ZoneId.systemDefault();

        ZonedDateTime zdt = ldt.atZone(origin);

        zdt = zdt.withZoneSameInstant(target);

        ldt = zdt.toLocalDateTime();

        return ldt;

    }
    
        public static LocalDateTime convertLdtUtc(LocalDateTime ldt) {

        ZoneId origin = ZoneId.of("UTC");
        ZoneId target = ZoneId.systemDefault();

        ZonedDateTime zdt = ldt.atZone(origin);

        zdt = zdt.withZoneSameInstant(target);

        ldt = zdt.toLocalDateTime();

        return ldt;

    }

}
