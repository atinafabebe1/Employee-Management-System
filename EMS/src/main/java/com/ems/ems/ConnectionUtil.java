package com.ems.ems;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionUtil {
    Connection conn = null;
    public static Connection conDB() throws  ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con= null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS", "root", "root");
            return con;
        } catch (SQLException ex) {
            System.err.println("ConnectionUtil : "+ex.getMessage());
           return null;
        }
    }
    //make sure you add the lib
}
