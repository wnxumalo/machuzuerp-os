package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.Client;
import static com.machuzuerp.controllers.jsf.Systems.connection;
import static com.machuzuerp.controllers.jsf.Systems.statement;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named
public class ClientsBean implements Serializable {

    private Client selectedClient;
    private List<Client> clients;
    ResultSet results;
    
    String query = "";
    
    String idNumber = "";

    public ClientsBean() {
        clients = new ArrayList<Client>();

        clients.clear();
        clients.add(new Client("0", "N/A","","","",""));
        try {

            connection = Systems.initConnection();
            
            query = "SELECT recnum,name, surname, telephone, cellphone, postaladdress, email FROM clients order by name, surname";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {              
                clients.add(new Client(results.getString(1), results.getString(2) + " " + results.getString(3), results.getString(4), results.getString(5), results.getString(6), results.getString(7)));

            }

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }
    }
    
    public void getClientByID() {               
        
        clients = new ArrayList<Client>();

        clients.clear();
        try {

            connection = Systems.initConnection();
            
            query = "SELECT vatnum,name, surname FROM clients where vatnum like '%" + getIDNumber() + "%'";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {              
                clients.add(new Client(results.getString(1), results.getString(2) + " " + results.getString(3),"","","",""));

            }

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }
    }
    
    public void resetClients() {
        clients = new ArrayList<Client>();

        clients.clear();
        clients.add(new Client("0", "N/A","","","",""));
        try {

            connection = Systems.initConnection();
            
            query = "SELECT idnumber,name, surname FROM clients order by name, surname";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {              
                clients.add(new Client(results.getString(1), results.getString(2),"","","",""));

            }

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Client getClient(String id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Client client : clients) {
            if (id.equals(client.getId())) {
                return client;
            }
        }
        return null;
    }
    
    public void setIDNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    public String getIDNumber() {
        return idNumber;
    }

}
