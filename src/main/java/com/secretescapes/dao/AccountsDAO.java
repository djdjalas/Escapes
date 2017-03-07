package com.secretescapes.dao;

import com.secretescapes.entitiy.Account;

import java.util.Collection;

/**
 * Created by DjAlas on 07/03/2017.
 */
public interface AccountsDAO {
    Collection<Account> getAccounts();

    long getNumberOfCreatedAccounts();

    void addAccount(Account account);

    Account getAccountById(String id);
}
