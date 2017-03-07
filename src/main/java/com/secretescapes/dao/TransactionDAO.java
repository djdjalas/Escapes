package com.secretescapes.dao;

import com.secretescapes.entitiy.Transaction;

import java.util.Collection;

/**
 * Created by DjAlas on 07/03/2017.
 */
public interface TransactionDAO {
    void recordTransaction(Transaction payment);

    Collection<Transaction> getAllTransactions();
}
