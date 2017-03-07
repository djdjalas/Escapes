package com.secretescapes.dao;

import com.secretescapes.entitiy.Account;
import com.secretescapes.entitiy.Email;
import com.secretescapes.entitiy.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountsDAOImpl implements AccountsDAO {

    private static Map<String, Account> accounts;


    @Override
    public Collection<Account> getAccounts() {
        return accounts.values();
    }

    @Override
    public long getNumberOfCreatedAccounts() {
        return Account.getNumberOfCreatedAccounts();
    }

    @Override
    public void addAccount(Account account) {
        accounts.put(account.getEmail().getEmailAddress(), account);
    }

    public AccountsDAOImpl() {
        accounts = new HashMap<>();
        addAccount(new Account("Current", new User("Anna", "Smith"), new Email(null, "smith@gmail.com")));
        addAccount(new Account("Current", new User("Alex", "Song"), new Email(null, "song@gmail.com")));
        addAccount(new Account("Savings", new User("Annabela", "Jones"), new Email(null, "jones@gmail.com")));
    }

    @Override
    public Account getAccountById(String id) {
        return accounts.get(id);
    }
}
