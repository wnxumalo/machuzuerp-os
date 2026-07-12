package com.machuzuerp.controllers.jsf;

import com.machuzuerp.entities.Account;
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
public class AccountBean implements Serializable {

    private Account selectedAccount;
    private List<Account> accounts;
    ResultSet results;
    
    String query = "";

    public AccountBean() {
        accounts = new ArrayList<Account>();

        accounts.clear();
        accounts.add(new Account(0, "N/A"));
        try {

            connection = Systems.initConnection();
            query = "SELECT recnum, accounttype FROM accounts order by accounttype";
            statement = connection.createStatement();
            results = statement.executeQuery(query);

            while (results.next()) {
                accounts.add(new Account(Integer.parseInt(results.getString(1)), results.getString(2)));

            }

        } catch (NumberFormatException | SQLException sqle) {
            System.out.println(sqle);
        }
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public List<Account> getAccount() {
        return accounts;
    }

    public void setAccount(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account getAccount(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Account client : accounts) {
            if (id.equals(client.getId())) {
                return client;
            }
        }
        return null;
    }

}
