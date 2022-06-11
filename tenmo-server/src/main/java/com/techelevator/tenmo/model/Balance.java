package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Balance {

    @Min(value = 0)
    @NotNull
    BigDecimal balance;

    public Balance() {}

    public Balance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "balance=" + balance +
                '}';
    }
}
