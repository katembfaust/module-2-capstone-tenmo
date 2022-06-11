package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class JdbcTransferStatusDao implements TransferStatusDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
        String sql = " SELECT * FROM transfer_status WHERE transfer_id = ?;";
        TransferStatus transferStatus = null;
        try {
             transferStatus = jdbcTemplate.queryForObject(sql, TransferStatus.class, transferId);

        } catch (DataAccessException e) {
            System.out.println("Error accessing database");
        }
        return transferStatus;
    }

    private TransferStatus mapRowToTransferStatus(SqlRowSet results) {
        int transferStatusId = results.getInt("transfer_status_id");
       String transferStatusDesc = results.getString("transfer_status_desc");

        TransferStatus transferStatus = new TransferStatus(transferStatusId, transferStatusDesc);
        return transferStatus;
    }
}
