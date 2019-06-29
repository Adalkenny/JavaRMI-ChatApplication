package com.dt.project.javafx.rmi.server.service;

import com.dt.project.javafx.rmi.server.utilities.DatabaseConnection;
import com.dt.projet.javafx.rmi.api.entity.Person;
import com.dt.projet.javafx.rmi.api.service.PersonService;
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
 * 
 */
public class PersonServiceImpl extends UnicastRemoteObject implements PersonService {

    public PersonServiceImpl() throws RemoteException {
    }

    @Override
    public Person insertPerson(Person person) throws RemoteException {

        PreparedStatement statement = null;
        String sql = "insert into person (id ,first_name,last_name,birth_date) values (null, ? , ?, ?)";

        try {

            statement = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setDate(3, Date.valueOf(person.getBirthDate().toString()));

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                person.setId(result.getLong(1));

            }
            result.close();
            return person;

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
    public void updatePerson(Person person) throws RemoteException {

        PreparedStatement statement = null;
        String sql = "update person set first_name=?, last_name = ?, birth_date=? where id=?";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setDate(3, Date.valueOf(person.getBirthDate().toString()));
            statement.setLong(4, person.getId());

            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();

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
    public void deletePerson(Long id) throws RemoteException {

        PreparedStatement stmt = null;
        String sql = "delete from person where id=?";
        try {
            stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {

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
    public Person getPersonById(Long id) throws RemoteException {

        PreparedStatement stmt = null;
        String sql = "select * from person where id = ?";

        try {
            stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet result = stmt.executeQuery();
            Person person = null;
            if (result.next()) {
                person = new Person();
                person.setId(result.getLong("id"));
                person.setFirstName(result.getString("first_name"));
                person.setLastName(result.getString("last_name"));
                person.setBirthDate(LocalDate.parse(result.getDate("birth_date").toString()));
            }
            result.close();
            return person;

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
    public List<Person> getAllPerson() throws RemoteException {

        Statement stmt = null;
        String sql = "select * from person";

        try {
            stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet result = stmt.executeQuery(sql);
            List<Person> list = new ArrayList<>();

            while (result.next()) {

                Person person = new Person();
                person.setId(result.getLong("id"));
                person.setFirstName(result.getString("first_name"));
                person.setLastName(result.getString("last_name"));
                person.setBirthDate(LocalDate.parse(result.getDate("birth_date").toString()));

                list.add(person);

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
    public Person getPersonByName(Person person) throws RemoteException {

        PreparedStatement stmt = null;
        String sql = "select * from person where first_name =? and last_name=?";

        try {
            stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                //person = new Person();

                person.setId(result.getLong("id"));
                person.setFirstName(result.getString("first_name"));
                person.setLastName(result.getString("last_name"));
                person.setBirthDate(LocalDate.parse(result.getDate("birth_date").toString()));

                result.close();
                return person;
                
            } else {
                result.close();
                person = null;
                return person;
            }

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
    public List<Person> getAllPersonOnlyName() throws RemoteException {
        
        Statement stmt = null;
        String sql = "select * from person";

        try {
            stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet result = stmt.executeQuery(sql);
            List<Person> list = new ArrayList<>();

            while (result.next()) {

                Person person = new Person();
                person.setId(result.getLong("id"));
                person.setFirstName(result.getString("first_name"));
                person.setLastName(result.getString("last_name"));
                person.setBirthDate(LocalDate.parse(result.getDate("birth_date").toString()));
                

                list.add(person);

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
    public Person getPersonFirstName(Person person) throws RemoteException {
        
        PreparedStatement stmt = null;
        String sql = "select * from person where first_name =?";

        try {
            stmt = DatabaseConnection.getConnection().prepareStatement(sql);
            stmt.setString(1, person.getFirstName());

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                //person = new Person();

                person.setId(result.getLong("id"));
                person.setFirstName(result.getString("first_name"));
                person.setLastName(result.getString("last_name"));
                person.setBirthDate(LocalDate.parse(result.getDate("birth_date").toString()));

                result.close();
                return person;
                
            } else {
                
                result.close();
                return null;
            }

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
