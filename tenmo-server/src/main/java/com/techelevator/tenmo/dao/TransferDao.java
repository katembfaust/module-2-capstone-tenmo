package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransferDao {

    Transfer createTransfer( Transfer transfer, Long transferTypeId, Long transferStatusId, Long accountTo, Long accountFrom, Double amount);

    List<Transfer> getAllTransfers();

   Transfer getTransferById(Long transferId);

    List<Transfer> getTransfersByUserId(Long userId);

    List<Transfer> getPendingTransfers(Long userId);

    void updateTransfer(Transfer transfer);

    Transfer[]  getTransfersByAccountId(Long accountId);
}
