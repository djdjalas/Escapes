package com.secretescapes.service;

import com.secretescapes.dao.AccountsDAO;
import com.secretescapes.dao.AccountsDAOImpl;
import com.secretescapes.entitiy.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AccountService {

    private AccountsDAO accountsDAO;

    public Collection<Account> retrieveAllAccounts() {
        return accountsDAO.getAccounts();
    }

    @Autowired
    public AccountService(AccountsDAOImpl accountsDAO) {
        this.accountsDAO = accountsDAO;
    }
}
