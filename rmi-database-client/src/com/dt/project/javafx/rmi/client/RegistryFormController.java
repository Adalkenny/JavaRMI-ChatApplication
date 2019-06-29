/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.client;

import com.dt.projet.javafx.rmi.api.entity.Person;
import com.dt.projet.javafx.rmi.api.service.PersonService;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author linuxkenny
 */
public class RegistryFormController implements Initializable {

    @FXML
    private JFXTextField txtusername;
    @FXML
    private JFXDatePicker dtpdatanascimento;
    @FXML
    private JFXPasswordField txtsenha;
    @FXML
    private JFXPasswordField txtconfirmarsenha;

    Person pessoa = new Person();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     * 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnconfirmar(ActionEvent event) {

        if (isFieldValid()) {

            if (!pessoaExiste()) {

                if (txtsenha.getText().equals(txtconfirmarsenha.getText())) {

                    PersonService personService = Main.getPersonService();

                    try {

                        Person newpessoa = personService.insertPerson(setPessoa());
                         
                        if(newpessoa != null){
                             JOptionPane.showMessageDialog(null, "Registo efetuado com sucesso!");
                             LoginFormController.stagio.close();
                        }else{
                             JOptionPane.showMessageDialog(null, "Nao conseguiu registrar por favor\nTente novamente!");
                        }
                       

                    } catch (RemoteException ex) {

                        ex.printStackTrace();
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Senhas nao combinam!");
                }

            } else {

                JOptionPane.showMessageDialog(null, "Utilizador ja existe\nTente outro nome de utilizador!");
                limparCampo();
            }
        }

    }

    @FXML
    private void btncancelar(ActionEvent event) {

        limparCampo();
        LoginFormController.stagio.close();

    }

    private void limparCampo() {

        txtusername.setText("");
        txtsenha.setText("");
        dtpdatanascimento.setValue(null);
        txtconfirmarsenha.setText("");

    }

    private Person setPessoa() {

        pessoa.setFirstName(txtusername.getText());
        pessoa.setLastName(txtsenha.getText());
        pessoa.setBirthDate(dtpdatanascimento.getValue());

        return pessoa;
    }

    private boolean isFieldValid() {

        String errorMessage = "";

        if (txtusername.getText() == null || txtusername.getText().isEmpty()) {
            errorMessage += "Nome de utilizador Invalido!\n";
        }

        if (dtpdatanascimento.getValue() == null) {
            errorMessage += "Data de nascimento Invalido!\n";
        }

        if (txtsenha.getText().isEmpty() || txtsenha.getText() == null) {
            errorMessage += "Palavra-passe Invalido!\n";
        }

        if (txtconfirmarsenha.getText().isEmpty() || txtconfirmarsenha.getText() == null) {
            errorMessage += "Confirme sua senha!\n";
        }

        if (errorMessage.length() == 0) {

            return true;

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Preencha todos os campos");
            alert.setHeaderText("Registo negado!");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }

    }

    private boolean pessoaExiste() {

        Person newperson = setPessoa();
        PersonService personService = Main.getPersonService();

        try {

            Person dbperson = personService.getPersonFirstName(newperson);

            if (dbperson != null) {

                return true;
            } else {

                return false;
            }

        } catch (RemoteException ex) {

            ex.printStackTrace();
            return false;

        }

    }

}
