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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    Person pessoa = new Person();

    public static Person pessoalogado;

   
    public static Stage stagio;

    String firstName, lastName;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnEntrar.setOnKeyPressed((KeyEvent k) -> {

            if (k.getCode() == KeyCode.ENTER) {

                if (!isFieldValid()) {

                } else {

                    login();
                }

            }
        });

        txtusername.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.ENTER) {
                if (!isFieldValid()) {

                } else {

                    login();
                }
            }
        });

        txtpwd.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.ENTER) {
                if (!isFieldValid()) {

                } else {

                    login();
                }
            }
        });

    }

    @FXML
    private void btnEntrarAction(ActionEvent event) {

        if (isFieldValid()) {

            login();

        }

    }

    @FXML
    private void btnConta(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistryForm.fxml"));
            Parent root = loader.load();

            Stage st = new Stage();
            st.setScene(new Scene(root));
            stagio = st;
            stagio.setResizable(false);
            stagio.show();
            stagio.setAlwaysOnTop(true);

        } catch (IOException ex) {

            ex.printStackTrace();
        }

    }

    private void login() {

        firstName = txtusername.getText();
        lastName = txtpwd.getText();
        pessoa.setFirstName(firstName);
        pessoa.setLastName(lastName);

        if (firstName.equalsIgnoreCase("Admin") && lastName.equalsIgnoreCase("Admin")) {
            //JOptionPane.showMessageDialog(null, "Utilizador valido");

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("form.fxml"));
                Parent root = loader.load();

                FormController frmcontroller = loader.getController();
                frmcontroller.setMain();

                Stage st = new Stage();
                st.setScene(new Scene(root));
                stagio=st;
                stagio.show();
                Main.stg.close();
                stagio.setAlwaysOnTop(true);

            } catch (IOException ex) {

                ex.printStackTrace();
            }
        } else {

            try {

                personService = Main.getPersonService();
                Person newpessoa = personService.getPersonByName(pessoa);

                if (newpessoa != null && newpessoa.getFirstName().equals(firstName) && newpessoa.getLastName().equals(lastName)) {

                    pessoalogado=pessoaLogado(newpessoa.getId());//para saber que utilizador esta no sistema
                    
                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatForm.fxml"));
                        Parent root = loader.load();
                        
                        ChatFormController cfc=loader.getController();
                        cfc.setMain();

                        Stage st = new Stage();
                        st.setScene(new Scene(root));
                        stagio=st;
                        stagio.show();
                        Main.stg.close();
                        stagio.setAlwaysOnTop(true);

                    } catch (IOException ex) {

                        ex.printStackTrace();
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Verefice se possui uma conta.\nVerefice seus dados\nTente novamente ");

                }

            } catch (RemoteException ex) {

                ex.printStackTrace();
            }

        }

    }

    private Person pessoaLogado(Long iduserlogado) {

        try {

           Person xpessoa = personService.getPersonById(iduserlogado);
           
             return xpessoa;
            
        } catch (RemoteException ex) {

            ex.printStackTrace();
              return null;
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

        if (txtpwd.getText().isEmpty() || txtpwd.getText() == null) {
            errorMessage += "Palavra-passe Invalido!\n";
        }

        if (errorMessage.length() == 0) {
            return true;

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Preencha todos os campos");
            alert.setHeaderText("Não possuí uma conta?\n Registe-se!");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
    
    public static Person getPessoalogado() {
        return pessoalogado;
    }


}
