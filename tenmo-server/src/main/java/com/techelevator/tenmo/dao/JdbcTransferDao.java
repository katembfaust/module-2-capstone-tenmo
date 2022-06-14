package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer createTransfer(Transfer transfer, Long transferTypeId, Long transferStatusId, Long accountTo, Long accountFrom, Double amount) {
       transfer = new Transfer();
        String sql = " INSERT INTO transfer (transfer_type_id, transfer_status_id, account_to, account_from, amount)" +
                " VALUES(?,?,?,?,?);";

           jdbcTemplate.update(sql, transferTypeId, transferStatusId, accountTo,
                   accountFrom, amount);
//       } catch (DataAccessException e) {
//           System.out.println("Error accessing database");
//       }
       return null;
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
    public Transfer getTransferById(Long transferId) {
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
    public List<Transfer> getTransfersByUserId(Long userId) {
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

    public Transfer[] getTransfersByAccountId(Long accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers " +
                "JOIN accounts ON accounts.account_id = transfers.account_from  " +
                "WHERE account_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            while (results.next()) {
                transfers.add(mapRowToTransfer(results));
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing database");
        }
        return transfers.toArray(new Transfer[0]);
    }

    @Override
    public List<Transfer> getPendingTransfers(Long userId) {
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
        Long transferId = results.getLong("transfer_id");
        Long transferTypeId = results.getLong("transfer_type_id");
        Long transferStatusId = results.getLong("transfer_status_id");
        Long accountFrom = results.getLong("account_from");
        Long accountTo = results.getLong("account_to");
       Double amount = results.getDouble("amount");

        Transfer transfer = new Transfer(transferId, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
        return transfer;
    }
}
