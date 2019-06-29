/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.server;

import com.dt.project.javafx.rmi.server.service.MensagemServiceImpl;
import com.dt.project.javafx.rmi.server.service.PersonServiceImpl;
import com.dt.project.javafx.rmi.server.utilities.DatabaseConnection;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author linuxkenny
 * Leader@off@free@focus
 * 
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
      
        
        DatabaseConnection.getConnection();
        
        Registry registry = LocateRegistry.createRegistry(6789);//registro de crud sobre base de dados
        Registry registrym = LocateRegistry.createRegistry(6889);//registro de mensagem
         
        PersonServiceImpl personServiceImpl = new PersonServiceImpl();
        MensagemServiceImpl mensagemServiceImpl = new MensagemServiceImpl();
        
        //PersonService personService = (PersonService) UnicastRemoteObject.exportObject(personServiceImpl, 0);
        registry.rebind("service", personServiceImpl);
        registrym.rebind("mensagem", mensagemServiceImpl);
       
        
        System.out.println("Server is running...");
      
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
