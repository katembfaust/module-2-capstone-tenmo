package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;

import java.math.BigDecimal;

public interface AccountDao {
    BigDecimal getBalance(int userId);
    Account getAccountByAccountId(int accountId);
    Account getAccountByUserId(int userId);
    void updateAccount(Account account);
}
