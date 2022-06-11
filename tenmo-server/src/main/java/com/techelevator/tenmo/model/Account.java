package com.techelevator.tenmo.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public class Account {

    @NotNull
    private int accountId;
    @NotNull
    private int userId;
    @NotNull
    private Balance balance;

    public Account() {}

    public Account(int accountId, int userId, Balance balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    //  getters & setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                '}';
    }
}

