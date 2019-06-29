/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.server.service;

import com.dt.project.javafx.rmi.server.utilities.DatabaseConnection;
import com.dt.projet.javafx.rmi.api.entity.Menssagem;
import com.dt.projet.javafx.rmi.api.entity.Person;
import com.dt.projet.javafx.rmi.api.service.MensagemService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author linuxkenny
 */
public class MensagemServiceImpl extends UnicastRemoteObject implements MensagemService {

    public MensagemServiceImpl() throws RemoteException {
    }

    @Override
    public Menssagem insertMensagemM(Menssagem mensagem) throws RemoteException {

        PreparedStatement statement = null;
        String sql = "insert into mensagem (id ,de,para,sms,data_envio) values (null, ? , ?, ?, ?)";

        try {

            statement = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, mensagem.getDe());
            statement.setString(2, mensagem.getPara());
            statement.setString(3, mensagem.getSms());
            statement.setDate(4, Date.valueOf(mensagem.getDataEnvio().toString()));

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();

            if (result.next()) {
                mensagem.setId(result.getLong(1));

            }
            result.close();
            return mensagem;

        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    public boolean insertMensagem(String de, String para, String sms, LocalDate data_envio) throws RemoteException {

        Menssagem mensagem = new Menssagem();
        PreparedStatement statement = null;
        String sql = "insert into mensagem (id ,de ,para ,sms ,data_envio) values (null, ? , ?, ?, ?)";

        try {

            statement = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, de);
            statement.setString(2, para);
            statement.setString(3, sms);
            statement.setDate(4, Date.valueOf(data_envio.toString()));

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();

            if (result.next()) {
                mensagem.setId(result.getLong(1));

                result.close();
                return true;

            } else {
                result.close();
                return false;
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
            return false;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    public void deleteMensagem(Long id) throws RemoteException {
        
        PreparedStatement stmt = null;
        String sql = "delete from mensagem where id=?";
        
        try {
            stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            
            if (stmt != null) {
                
                try {
                    
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    public Menssagem getMensagemById(Long id) throws RemoteException {
        return null;

    }

    @Override
    public List<Menssagem> getMensagens() throws RemoteException {
        return null;

    }

    @Override
    public List<Menssagem> getAllMensagem(Person person) throws RemoteException {
        
       
        PreparedStatement stmt = null;
        String sql = "select * from mensagem where de=? or para=? or para='todos'";

        try {
            stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getFirstName());

            ResultSet result = stmt.executeQuery();
            List<Menssagem> list = new ArrayList<>();

            while (result.next()) {
                
                Menssagem mensagem = new Menssagem();
                mensagem.setId(result.getLong("id"));
                mensagem.setDe(result.getString("de"));
                mensagem.setPara(result.getString("para"));
                mensagem.setSms(result.getString("sms"));
                mensagem.setDataEnvio(LocalDate.parse(result.getDate("data_envio").toString()));

                list.add(mensagem);
            }
            
            result.close();
            return list;

        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException ex) {

                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    public List<Menssagem> getAllMensagemOnlyName(Menssagem mensagem) throws RemoteException {

       
        PreparedStatement stmt = null;
        String sql = "select * from mensagem where de=? or para=? or para='todos'";

        try {
            stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, mensagem.getDe());
            stmt.setString(2, mensagem.getPara());

            ResultSet result = stmt.executeQuery();
            List<Menssagem> list = new ArrayList<>();

            while (result.next()) {
                //person = new Person();
                mensagem.setId(result.getLong("id"));
                mensagem.setDe(result.getString("de"));
                mensagem.setPara(result.getString("para"));
                mensagem.setSms(result.getString("sms"));
                mensagem.setDataEnvio(LocalDate.parse(result.getDate("data_envio").toString()));

                list.add(mensagem);

            }
            result.close();
            return list;

        } catch (SQLException ex) {

            ex.printStackTrace();
            return null;

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();

                } catch (SQLException ex) {

                    ex.printStackTrace();
                }
            }
        }

    }

}
