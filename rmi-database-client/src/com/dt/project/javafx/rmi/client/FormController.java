/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.client;

import com.dt.projet.javafx.rmi.api.entity.Person;
import com.dt.projet.javafx.rmi.api.service.PersonService;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author linuxkenny Leader@off@free@focus
 *
 */
public class FormController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private DatePicker dptBristDate;
    @FXML
    private TableView<Person> TableView;
    @FXML
    private TableColumn<Person, Long> colId;
    @FXML
    private TableColumn<Person, String> colFirstName;
    @FXML
    private TableColumn<Person, String> colLastName;
    @FXML
    private TableColumn<Person, LocalDate> colBirthdate;
    @FXML
    private Button btnInsert;

    private Main main;

    private PersonService personService;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<Person, Long>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        TableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observable, Person oldperson, Person newperson) {

                if (newperson != null) {

                    txtId.setText(Long.toString(newperson.getId()));
                    txtFirstName.setText(newperson.getFirstName());
                    txtLastName.setText(newperson.getLastName());
                    dptBristDate.setValue(newperson.getBirthDate());

                } else {
                    
                    clearField();
                }
            }
        });

    }

    @FXML
    private void onInsert(ActionEvent event) {

        if (isFieldValid()) {

            try {
                Person person = new Person();
                person.setFirstName(txtFirstName.getText());
                person.setLastName(txtLastName.getText());
                person.setBirthDate(dptBristDate.getValue());

                person = personService.insertPerson(person);

                TableView.getItems().add(person);
                clearField();

            } catch (RemoteException ex) {

                ex.printStackTrace();
            }

        }

    }

    @FXML
    private void onUpdate(ActionEvent event) {

        int index = TableView.getSelectionModel().getSelectedIndex();

        if (index == -1) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Warning Update");
            alert.setHeaderText("Nenhuma pessoa foi selecionada");
            alert.setContentText("Por favor selecione uma pessoa");
            alert.showAndWait();

        }
        if (isFieldValid()) {
            try {
                Person person = new Person();
                person.setId(Long.valueOf(txtId.getText()));
                person.setFirstName(txtFirstName.getText());
                person.setLastName(txtLastName.getText());
                person.setBirthDate(dptBristDate.getValue());

                personService.updatePerson(person);

                TableView.getItems().set(index, person);
                clearField();

            } catch (RemoteException ex) {

                ex.printStackTrace();
            }

        }
    }

    @FXML
    private void onDelete(ActionEvent event) {

        try {
            Person person = TableView.getSelectionModel().getSelectedItem();
            if (person == null) {
                return;
            }

            personService.deletePerson(person.getId());

            TableView.getItems().remove(person);

            clearField();

        } catch (RemoteException ex) {

            ex.printStackTrace();
        }
    }

    @FXML
    private void onRefresh(ActionEvent event) {

        try {
            TableView.getItems().setAll(personService.getAllPerson());
        } catch (RemoteException ex) {

            ex.printStackTrace();
        }
        clearField();

    }

    public void setMain(Main main) {
        this.main = main;
        this.personService = main.getPersonService();

        try {
            TableView.getItems().setAll(personService.getAllPerson());
        } catch (RemoteException ex) {

            ex.printStackTrace();
        }

    }

    private void clearField() {
        txtId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        dptBristDate.setValue(null);
    }

    private boolean isFieldValid() {

        String errorMessage = "";
        if (txtFirstName.getText() == null || txtFirstName.getText().isEmpty()) {
            errorMessage += "No valid first name!\n";
        }

        if (dptBristDate.getValue() == null) {
            errorMessage += "No valid birth date!\n";
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
