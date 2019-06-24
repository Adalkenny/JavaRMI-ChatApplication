/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.server.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author linuxkenny
 */
public class DatabaseConnection {
    
    private static Connection connection;
    public static Connection getConnection(){
        
        if(connection == null ){
            try{
            
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver()); 
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafxrmi","javafx","MariaJose001++");
            }catch(SQLException ex){
                
                ex.printStackTrace();
            }
            
        }
        return connection;
        
    }
    
}
