
package com.dt.project.javafx.rmi.client;

import com.dt.projet.javafx.rmi.api.service.PersonService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author linuxkenny
 * Leader@off@free@focus
 * 
 */
public class Main extends Application {
    
    private PersonService personService;

    @Override
    public void start(Stage stage) throws Exception {
        
       

        Registry registry = LocateRegistry.getRegistry("10.74.34.81", 6789); 
        personService = (PersonService) registry.lookup("service");
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginForm.fxml"));
        Parent root = loader.load();
        
        //FormController controller = loader.getController();
        //controller.setMain(this);
        
        stage.setScene(new Scene(root));
        stage.setTitle("Database RMI");
        
        stage.show();
        
    }
    
    public PersonService getPersonService(){
        return personService;
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
