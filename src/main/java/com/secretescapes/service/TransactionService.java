package com.secretescapes.service;

import com.secretescapes.dao.AccountsDAO;
import com.secretescapes.dao.TransactionDAO;
import com.secretescapes.entitiy.Account;
import com.secretescapes.entitiy.Payment;
import com.secretescapes.entitiy.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    public TransactionDAO transactionDAO;

    @Autowired
    private AccountsDAO accountsDAO;

    @Autowired
    private PaymentService paymentService;

    public Collection<Transaction> getTransactionAssociatedWithAccount(String accountId) {
        //TODO Check if user exists
        List<Transaction> transactions = transactionDAO.getAllTransactions()
                .stream()
                .filter(transaction -> {
                    String from = transaction.getPayment().getFrom().getEmail().getEmailAddress();
                    String to = transaction.getPayment().getTo().getEmail().getEmailAddress();
                    return from.equals(accountId) || to.equals(accountId);
                })
                .collect(Collectors.toList());
        return transactions;
    }

    public Collection<Transaction> getTransactions() {
        return transactionDAO.getAllTransactions();
    }

    public String transferMoney(Double amountToTransfer, String fromId, String toId) {
        Account fromAccount = accountsDAO.getAccountById(fromId);
        boolean verifyFunds = isEnoughFunds(amountToTransfer, fromAccount);
        if(verifyFunds) {
            Account toAccount = accountsDAO.getAccountById(toId);
            Payment payment = new Payment(fromAccount, toAccount, amountToTransfer);
            paymentService.transferFunds(payment);
            return "successful";

        }
        return "unsuccessful";
    }


    private boolean isEnoughFunds(double amount, Account fromAccount) {
        return amount <= fromAccount.getBalance() && amount > fromAccount.getOverdraftLimit();
    }
}
