package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

    @Override
    public BigDecimal getBalance(int userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        BigDecimal balance = null;
        try {
            balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
        } catch (DataAccessException e) {
                System.out.println("Error accessing database");
            }
            return balance;
        }

    @Override
    public Account getAccountByAccountId(int accountId) {
        String sql = "SELECT * FROM account where account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        Account account = null;
        if(results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT * FROM account where user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        Account account = null;
        if(results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public void updateAccount(Account account) {
            String sql = "UPDATE accounts " +
                    "SET balance = ? " +
                    "WHERE account_id = ?";

            jdbcTemplate.update(sql, account.getBalance(), account.getAccountId());
    }


    private Account mapRowToAccount(SqlRowSet results) {
        int accountId = results.getInt("account_id");
        int userId = results.getInt("user_id");

        Balance balance = new Balance();
        String accountBal = results.getString("balance");
        balance.setBalance(new BigDecimal(accountBal));
        return new Account(accountId,userId,balance);
    }
}
