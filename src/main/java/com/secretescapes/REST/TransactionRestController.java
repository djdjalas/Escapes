package com.secretescapes.REST;

import com.secretescapes.entitiy.Transaction;
import com.secretescapes.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("transaction")
public class TransactionRestController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(path = "/{id:.+}", method = RequestMethod.GET)
    public Collection<Transaction> getAccountById(@PathVariable String id) {
        return transactionService.getTransactionAssociatedWithAccount(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Transaction> getAccountById() {
        return transactionService.getTransactions();
    }

    @RequestMapping(value = "/pay/{amount}/from/{from:.+}/to/{to:.+}", method = RequestMethod.POST)
    public HttpEntity<String> getAccountById(@PathVariable Double amount, @PathVariable String from, @PathVariable String to) {
        return new HttpEntity<>(transactionService.transferMoney(amount, from, to));
    }
}
