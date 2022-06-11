package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

public class JdbcTransferTypeDao implements TransferTypeDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferTypeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TransferType getTransferType(String user, String description) {
        String sql = " SELECT transfer_type_id, transfer_type_desc FROM transfer_type WHERE transfer_type_desc = ?;";
        TransferType transferType = null;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, description);
            if (results.next()) {
                int transferTypeId = results.getInt("transfer_type_id");
                String transferTypeDescription = results.getString("transfer_type_desc");

                transferType = new TransferType(transferTypeId, transferTypeDescription);
            }
        } catch (DataAccessException e) {
            System.out.println("Error accessing database");
        }
        return transferType;
    }

    @Override
    public TransferType getTransferTypeById(String user, Long transferTypeId) {
        String sql = "SELECT transfer_type_id, transfer_type_desc FROM transfer_types WHERE transfer_type_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferTypeId);

        TransferType transferType = null;
        if (result.next()) {

            int transferTyId = result.getInt("transfer_type_id");
            String transferTypeDesc = result.getString("transfer_type_desc");

            transferType = new TransferType(transferTyId, transferTypeDesc);
        }
        return transferType;
    }
}
