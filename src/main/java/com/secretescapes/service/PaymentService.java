package com.secretescapes.service;

import com.secretescapes.dao.TransactionDAO;
import com.secretescapes.dao.TransactionDAOImpl;
import com.secretescapes.entitiy.Account;
import com.secretescapes.entitiy.Payment;
import com.secretescapes.entitiy.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private TransactionDAO transactionDAO;

    public void transferFunds(Payment payment) {
        Account from = payment.getFrom();
        double amountToDeduct = payment.getAmount();
        from.setBalance(from.getBalance() - amountToDeduct);
        Account to = payment.getTo();
        to.setBalance(to.getBalance() + amountToDeduct);
        recordTransaction(payment);
    }

    public void recordTransaction(Payment payment) {
        Transaction transaction = new Transaction(payment);
        transactionDAO.recordTransaction(transaction);
        //TODO send email in the future
    }
}
