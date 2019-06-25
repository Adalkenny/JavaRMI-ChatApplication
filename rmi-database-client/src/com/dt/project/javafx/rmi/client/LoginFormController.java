/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.client;

import com.dt.projet.javafx.rmi.api.entity.Person;
import com.dt.projet.javafx.rmi.api.service.PersonService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author linuxkenny
 */
public class LoginFormController implements Initializable {

    @FXML
    private JFXTextField txtusername;
    @FXML
    private JFXPasswordField txtpwd;
    @FXML
    private JFXButton btnEntrar;
    @FXML
    private JFXButton btncriarConta;

    private PersonService personService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       
            
            btnEntrar.setOnKeyPressed((KeyEvent k) -> {
            if (k.getCode() == KeyCode.ENTER) {
                login();
            }
        });

        txtusername.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                login();
            }
        });

        txtpwd.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                login();
            }
        });

        
        
    }

    @FXML
    private void btnEntrarAction(ActionEvent event) {

    }

    @FXML
    private void btnConta(ActionEvent event) {
    }

    private void login() {

        Person pessoa = new Person();

        try {
            String firstName=txtusername.getText();;
             String lastName=txtpwd.getText();

            if (isFieldValid()) {
                
                String tal="'"+firstName+"'";
                JOptionPane.showMessageDialog(null,tal);
                pessoa = personService.getPersonByName(tal);
                

                if ((pessoa.getFirstName().equals(firstName)) && (pessoa.getLastName().equals(lastName))) {

                    if (pessoa.getFirstName().equalsIgnoreCase("Admin")) {
                        //JOptionPane.showMessageDialog(null, "Utilizador valido");
                        AnchorPane a;
                        try {
                            a = (AnchorPane) FXMLLoader.load(getClass().getResource("form.fxml"));
                            Scene sc = new Scene(a);
                            Stage st = new Stage();
                            st.setScene(sc);
                            st.show();

                        } catch (IOException ex) {

                            ex.printStackTrace();
                        }

                    } else {
                        AnchorPane a;

                        try {
                            a = (AnchorPane) FXMLLoader.load(getClass().getResource("form.fxml"));
                            Scene sc = new Scene(a);
                            Stage st = new Stage();
                            st.setScene(sc);
                            st.show();

                        } catch (IOException ex) {

                            ex.printStackTrace();
                        }

                    }

                } else {

                    String errorMessage = "Clique em Criar uma conta\n Para poder registar-se!";
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("Utilizador Inexistente!");
                    alert.setHeaderText("Por favor crie uma Conta!");
                    alert.setContentText(errorMessage);
                    alert.showAndWait();

                    clearField();
                }

            } else {
                String errorMessage = "Preencha todos os campos!";
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("Utilizador Inexistente!");
                    alert.setHeaderText("Por favor crie uma Conta!");
                    alert.setContentText(errorMessage);
                    alert.showAndWait();

                    clearField();
                
            }

        } catch (RemoteException ex) {

            ex.printStackTrace();
        }

    }

    private void clearField() {
        txtusername.setText("");
        txtpwd.setText("");

    }

    private boolean isFieldValid() {

        String errorMessage = "";
        if (txtusername.getText() == null || txtusername.getText().isEmpty()) {
            errorMessage += "Nome de utilizador Invalido!\n";
        }

        if (txtpwd.getText() == null) {
            errorMessage += "Palavra-passe Invalido!\n";
        }

        if (errorMessage.length() == 0) {
            return true;

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please correct those fieds");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

}
