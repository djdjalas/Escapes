package com.secretescapes.entitiy;

public class Email {
    private User emailOwner;
    private String emailAddress;

    public Email() {
    }

    public Email(User emailOwner, String emailAddress) {
        this.emailOwner = emailOwner;
        this.emailAddress = emailAddress;
    }

    public User getEmailOwner() {
        return emailOwner;
    }

    public void setEmailOwner(User emailOwner) {
        this.emailOwner = emailOwner;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
