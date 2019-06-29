/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.projet.javafx.rmi.api.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author linuxkenny
 */
public class Menssagem implements Externalizable{

    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty de = new SimpleStringProperty();
    private final StringProperty para = new SimpleStringProperty();
    private final StringProperty sms = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dataEnvio = new SimpleObjectProperty<>();

    public LocalDate getDataEnvio() {
        return dataEnvio.get();
    }

    public void setDataEnvio(LocalDate value) {
        dataEnvio.set(value);
    }

    public ObjectProperty dataEnvioProperty() {
        return dataEnvio;
    }
    

    public String getSms() {
        return sms.get();
    }

    public void setSms(String value) {
        sms.set(value);
    }

    public StringProperty smsProperty() {
        return sms;
    }
    

    public String getPara() {
        return para.get();
    }

    public void setPara(String value) {
        para.set(value);
    }

    public StringProperty paraProperty() {
        return para;
    }
    

    public String getDe() {
        return de.get();
    }

    public void setDe(String value) {
        de.set(value);
    }

    public StringProperty deProperty() {
        return de;
    }
    

    public long getId() {
        return id.get();
    }

    public void setId(long value) {
        id.set(value);
    }

    public LongProperty idProperty() {
        return id;
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        
        out.writeLong(getId());
        out.writeObject(getDe());
        out.writeObject(getPara());
        out.writeObject(getSms());
        out.writeObject(getDataEnvio());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        
        setId(in.readLong());
        setDe((String) in.readObject());
        setPara((String) in.readObject());
        setSms((String) in.readObject());
        setDataEnvio((LocalDate) in.readObject()); 
    }

    @Override
    public String toString() {
        return "De: " + getDe() + " Para: " +getPara() + " sms: " +getSms() + " DataEnvio: " +getDataEnvio();
    }
    
    
    
}
