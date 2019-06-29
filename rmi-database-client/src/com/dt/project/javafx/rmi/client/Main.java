
package com.dt.project.javafx.rmi.client;

import com.dt.projet.javafx.rmi.api.service.MensagemService;
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
    
    public static PersonService personService;
    public static MensagemService mensagemService;
    public static Stage stg;

    @Override
    public void start(Stage stage) throws Exception {
        

        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 6789); 
        personService = (PersonService) registry.lookup("service");
        /////////////////////////////////////////////////////////////////////////
        Registry registrym = LocateRegistry.getRegistry("127.0.0.1", 6889); 
        mensagemService = (MensagemService) registrym.lookup("mensagem");
        ////////////////////////////////////////////////////////////////////////////
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginForm.fxml"));
        Parent root = loader.load();
        
        /*LoginFormController controller = loader.getController();
        controller.setMain(this);*/
        
        stg=stage;
        stg.setScene(new Scene(root));
        stg.setTitle("Database RMI");
        stg.show();
        
    }
    
    public static PersonService getPersonService(){
        return personService;
        
    }

    public static MensagemService getMensagemService() {
        return mensagemService;
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
