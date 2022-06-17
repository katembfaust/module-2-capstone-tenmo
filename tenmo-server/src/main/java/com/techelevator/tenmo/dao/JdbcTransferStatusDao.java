package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcTransferStatusDao implements TransferStatusDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TransferStatus getTransferStatus() {
        String sql = " SELECT * FROM transfer_status;";
        TransferStatus transferStatus = null;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            if (results.next()) {
                transferStatus = mapRowToTransferStatus(results);
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing database");
        }
        return transferStatus;
    }

    @Override
    public TransferStatus getTransferStatusById(Long transferId) {
        String sql = " SELECT transfer_status_desc from transfer_status JOIN transfer ON " +
                "transfer.transfer_status_id = transfer_status.transfer_status_id WHERE transfer_id = ?; ";
        TransferStatus transferStatus = null;
//        try {
             transferStatus = jdbcTemplate.queryForObject(sql, TransferStatus.class, transferId);
//        } catch (DataAccessException e) {
//            System.out.println("Error accessing database");
//        }
        return transferStatus;
    }



    private TransferStatus mapRowToTransferStatus(SqlRowSet results) {
        TransferStatus transferStatus = new TransferStatus();
       transferStatus.setTransferStatusId(results.getLong("transfer_status_id"));
       transferStatus.getTransferStatusDesc(results.getString("transfer_status_desc"));
       return transferStatus;
    }
}
