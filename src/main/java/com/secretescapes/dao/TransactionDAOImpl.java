package com.secretescapes.dao;

import com.secretescapes.entitiy.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;


@Repository
public class TransactionDAOImpl implements TransactionDAO {

    private static Collection<Transaction> transactions;

    static {
        transactions = new ArrayList<>();
        transactions.add(new Transaction(new Payment(
                new Account("Current", new User("Anna", "Smith"), new Email(null, "smith@gmail.com")),
                new Account("Current", new User("Alex", "Song"), new Email(null, "song@gmail.com")),
                80000.00

        )));
    }

    @Override
    public void recordTransaction(Transaction payment) {
        transactions.add(payment);
    }

    @Override
    public Collection<Transaction> getAllTransactions() {
        return transactions;
    }
}
