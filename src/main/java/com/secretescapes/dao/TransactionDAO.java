package com.secretescapes.dao;

import com.secretescapes.entitiy.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;


@Repository
public class TransactionDAO {

    private static Collection<Transaction> transactions;

    static {
        transactions = new ArrayList<>();
        transactions.add(new Transaction(new Payment(
                new Account("Current", new User("Anna", "Smith"), new Email(null, "smith@gmail.com")),
                new Account("Current", new User("Alex", "Song"), new Email(null, "song@gmail.com")),
                80000.00

        )));
    }

    public void recordTransaction(Transaction payment) {
        transactions.add(payment);
    }

    public Collection<Transaction> getAllTransactions() {
        return transactions;
    }
}
