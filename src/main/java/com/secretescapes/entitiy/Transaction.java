package com.secretescapes.entitiy;

public class Transaction {

    private Payment payment;

    public Transaction(Payment payment) {
        this.payment = payment;
    }

    public Transaction(){}

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

}
