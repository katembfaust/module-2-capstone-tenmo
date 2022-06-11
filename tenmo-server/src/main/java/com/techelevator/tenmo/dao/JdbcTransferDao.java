package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createTransfer(Transfer transfer) {
        String sql = " INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, amount_from, amount_to, amount)" +
                " VALUES(?,?,?,?,?,?);";
       try {
           jdbcTemplate.update(sql, transfer.getTransferId(), transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                   transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
       } catch (DataAccessException e) {
           System.out.println("Error accessing database");
       }
    }

    @Override
    public List<Transfer> getAllTransfers() {
        String sql = " SELECT * FROM transfer;";
        List<Transfer> transfers = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                transfers.add(mapRowToTransfer(results));
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing database");
        }
        return transfers;
    }

    @Override
    public Transfer getTransferById(int userId, Transfer transferId) {
        String sql = " SELECT * FROM transfer WHERE transfer_id = ? ;";
        Transfer transfer = null;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if (results.next()) {
                transfer = mapRowToTransfer(results);
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing database");
        }
        return transfer;
    }

    @Override
    public List<Transfer> getTransfersByUserId(int userId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers " +
                "JOIN accounts ON accounts.account_id = transfers.account_from OR accounts.account_id = transfers.account_to " +
                "WHERE user_id = ?";
        List<Transfer> transfers = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                transfers.add(mapRowToTransfer(results));
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing database");
        }
            return transfers;
        }

    @Override
    public List<Transfer> getPendingTransfers(int userId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfers.transfer_status_id, account_from, account_to, amount " +
                "FROM transfers " +
                "JOIN accounts ON accounts.account_id = transfers.account_from " +
                "JOIN transfer_statuses ON transfers.transfer_status_id = transfer_statuses.transfer_status_id " +
                "WHERE user_id = ? AND transfer_status_desc = 'Pending'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        List<Transfer> transfers = new ArrayList<>();

        while(results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    @Override
    public void updateTransfer(Transfer transfer) {
        String sql = "UPDATE transfers " +
                "SET transfer_status_id = ? " +
                "WHERE transfer_id = ?";

        jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId());
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        int transferId = results.getInt("transfer_id");
        int transferTypeId = results.getInt("transfer_type_id");
        int transferStatusId = results.getInt("transfer_status_id");
        int accountFrom = results.getInt("account_from");
        int accountTo = results.getInt("account_to");
        BigDecimal amount = results.getBigDecimal("amount");

        Transfer transfer = new Transfer(transferId, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
        return transfer;
    }
}
