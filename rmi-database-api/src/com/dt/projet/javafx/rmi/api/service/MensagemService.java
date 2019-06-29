/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dt.projet.javafx.rmi.api.service;

import com.dt.projet.javafx.rmi.api.entity.Menssagem;
import com.dt.projet.javafx.rmi.api.entity.Person;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
 
/**
 *
 * @author linuxkenny
 * 
 */

public interface MensagemService extends Remote {
    
    Menssagem insertMensagemM(Menssagem mensagem) throws RemoteException;
    
    boolean insertMensagem(String de, String para, String sms ,LocalDate data_envio) throws RemoteException;
    
    void deleteMensagem(Long id) throws RemoteException;
    
    Menssagem getMensagemById(Long id) throws RemoteException;
    
    List<Menssagem> getMensagens() throws RemoteException;
    
    List<Menssagem> getAllMensagem(Person person) throws RemoteException; 
    
    List<Menssagem> getAllMensagemOnlyName(Menssagem mensagem) throws RemoteException;
    
}
