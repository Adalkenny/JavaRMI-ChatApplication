/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.projet.javafx.rmi.api.service;

import com.dt.projet.javafx.rmi.api.entity.Person;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author linuxkenny
 */
public interface PersonService extends Remote {
    
    Person insertPerson(Person person) throws RemoteException;
    void updatePerson(Person person) throws RemoteException;
    
    void deletePerson(Long id) throws RemoteException;
    
    Person getPersonById(Long id) throws RemoteException;
    
    Person getPersonByName(String firstName) throws RemoteException;
    
    List<Person> getAllPerson() throws RemoteException; 
    
}
