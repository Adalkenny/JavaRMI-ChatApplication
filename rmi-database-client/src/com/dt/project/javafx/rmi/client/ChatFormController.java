/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.client;

import com.dt.projet.javafx.rmi.api.entity.Menssagem;
import com.dt.projet.javafx.rmi.api.entity.Person;
import com.dt.projet.javafx.rmi.api.service.MensagemService;
import com.dt.projet.javafx.rmi.api.service.PersonService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author linuxkenny
 */
public class ChatFormController implements Initializable {

    @FXML
    private JFXButton btntimeline;
    @FXML
    private JFXButton btnsms;
    @FXML
    private JFXButton btndefinicao;
    @FXML
    private JFXButton btnsair;
    @FXML
    private JFXButton btneliminar;
    @FXML
    private JFXButton btncontacto;
    @FXML
    private JFXListView<Person> listView;//lista de amigos
    @FXML
    private JFXListView<Menssagem> msmlistview;//lista de mensagem
    @FXML
    private JFXTextField txtPesquisa;
    @FXML
    private JFXTextField txtsmsfield;
    @FXML
    private JFXButton btnSearch;
    ////////////////////////////////////
    private PersonService personService;
    private MensagemService mensagemService;
    ////////////////////////////////////
    private ObservableList<Person> list;
    private ObservableList<Menssagem> listsms;
    ///////////////////////////////////////////
    private Person pessoalogado = new Person();
    private Person pessoaSelecionado = new Person();
    private Menssagem mensagemSelecionado = new Menssagem();
    //pessoalogado=LoginFormController.getPessoalogado();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pessoalogado = LoginFormController.getPessoalogado();

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observable, Person oldperson, Person newperson) {

                if (newperson != null) {

                    newperson.setId(newperson.getId());
                    newperson.setFirstName(newperson.getFirstName());
                    newperson.setLastName(newperson.getLastName());
                    newperson.setBirthDate(newperson.getBirthDate());

                    pessoaSelecionado = newperson;

                }
            }
        });

        msmlistview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Menssagem>() {
            @Override
            public void changed(ObservableValue<? extends Menssagem> observable, Menssagem oldtext, Menssagem newtext) {

                if (newtext != null) {

                    newtext.setId(newtext.getId());
                    newtext.setDe(newtext.getDe());
                    newtext.setPara(newtext.getPara());
                    newtext.setSms(newtext.getSms());
                    newtext.setDataEnvio(newtext.getDataEnvio());

                    mensagemSelecionado = newtext;

                }
            }
        });
    }

    @FXML
    void btnReload(ActionEvent event) {

        try {
            msmlistview.getItems().setAll(mensagemService.getAllMensagem(pessoalogado));

        } catch (RemoteException ex) {

            ex.printStackTrace();
        }
        clearField();
    }

    @FXML
    private void btnTimeLine(ActionEvent event) {
    }

    @FXML
    private void btnSMS(ActionEvent event) {

    }

    @FXML
    void btnSend(ActionEvent event) {

        String para = "todos";
        pessoaSelecionado = listView.getSelectionModel().getSelectedItem();
        if (mensagemValido()) {

            if (pessoaSelecionado != null) {

                para = pessoaSelecionado.getFirstName();
            }

            Date data = new Date(System.currentTimeMillis());
            SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(formatarDate.format(data));

            try {
                Menssagem mensagem = new Menssagem();
                mensagem.setDe(pessoalogado.getFirstName());
                mensagem.setPara(para);
                mensagem.setSms(txtsmsfield.getText());
                mensagem.setDataEnvio(localDate);

                mensagem = mensagemService.insertMensagemM(mensagem);

                msmlistview.getItems().add(mensagem);
                clearField();

            } catch (RemoteException ex) {

                ex.printStackTrace();
            }

        }
    }

    public void setMain() {

        pessoalogado = LoginFormController.getPessoalogado();
        this.personService = Main.getPersonService();
        this.mensagemService = Main.getMensagemService();

        try {

            listView.getItems().setAll(personService.getAllPersonOnlyName());

            if (pessoalogado != null) {

                msmlistview.getItems().setAll(mensagemService.getAllMensagem(pessoalogado));

            } else {

                JOptionPane.showMessageDialog(null, "Nao pegou a pessoa logado!");
            }

        } catch (RemoteException ex) {

            ex.printStackTrace();
        }

    }

    @FXML
    private void btnDefinicao(ActionEvent event) {
    }

    private boolean mensagemValido() {

        if (txtsmsfield.getText() == null || txtsmsfield.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Digite alguma coisa!");
            return false;

        } else {

            return true;
        }

    }

    @FXML
    private void btnSair(ActionEvent event) {

        if (pessoalogado != null) {

            pessoalogado = null;

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("loginForm.fxml"));
                Parent root = loader.load();

                Stage st = new Stage();
                st.setScene(new Scene(root));
                Main.stg = st;
                Main.stg.show();
                LoginFormController.stagio.close();

            } catch (IOException ex) {

                ex.printStackTrace();
            }

        }
    }

    @FXML
    void btntxtpesquisar(ActionEvent event) {//campo de pesquisa
    }

    @FXML
    void btntxtmsmfield(KeyEvent event) {
        
        if (event.getCode() == KeyCode.ENTER) {

            String para = "todos";
            pessoaSelecionado = listView.getSelectionModel().getSelectedItem();
            
            if (mensagemValido()) {

                if (pessoaSelecionado != null) {

                    para = pessoaSelecionado.getFirstName();
                }

                Date data = new Date(System.currentTimeMillis());
                SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(formatarDate.format(data));

                try {
                    Menssagem mensagem = new Menssagem();
                    mensagem.setDe(pessoalogado.getFirstName());
                    mensagem.setPara(para);
                    mensagem.setSms(txtsmsfield.getText());
                    mensagem.setDataEnvio(localDate);

                    mensagem = mensagemService.insertMensagemM(mensagem);

                    msmlistview.getItems().add(mensagem);
                    clearField();

                } catch (RemoteException ex) {

                    ex.printStackTrace();
                }

            }

        }

    }

    @FXML
    void txtfieldEnviar(ActionEvent event) {//campo de mensagem

    }

    @FXML
    private void btnDelete(ActionEvent event) {

        try {
            mensagemSelecionado = msmlistview.getSelectionModel().getSelectedItem();

            if (mensagemSelecionado == null) {
                JOptionPane.showMessageDialog(null, "Nenhuma conversa selecionada");
                return;
            }

            mensagemService.deleteMensagem(mensagemSelecionado.getId());

            msmlistview.getItems().remove(mensagemSelecionado);

            clearField();

        } catch (RemoteException ex) {

            ex.printStackTrace();
        }

    }

    @FXML
    private void btnContact(ActionEvent event) {
    }

    @FXML
    private void btnPesquisa(KeyEvent event) {//campo de texto pesquisar "ONKEYTYPE"

    }

    @FXML
    private void btnDIGSearch(ActionEvent event) {
    }

    @FXML
    void btnlistviewselect(MouseEvent event) {

    }

    private void clearField() {
        txtsmsfield.setText("");
    }

    private void preencherListView() {

        /*
        personService = Main.getPersonService();
        mensagemService = Main.getMensagemService();

        try {

            listnames = personService.getAllPersonOnlyName();
            list = FXCollections.observableArrayList(listnames);
            listView.getItems().setAll(list);
            //preencherSMSListview();
        

            try {
                 //forma correta de prenchaer uma list view.
                listmensagem = mensagemService.getAllMensagemOnlyName(pessoalogado.getFirstName(), pessoalogado.getFirstName());
                listsms = FXCollections.observableArrayList(listmensagem);
                msmlistview.getItems().setAll(listsms);

            } catch (RemoteException ex) {

                ex.printStackTrace();
            }

        } catch (RemoteException ex) {

            ex.printStackTrace();
        }*/
    }
}
