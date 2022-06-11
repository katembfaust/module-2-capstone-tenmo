package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    void createTransfer(Transfer transfer);

    List<Transfer> getAllTransfers();

   Transfer getTransferById(int userId, Transfer transferId);

    List<Transfer> getTransfersByUserId(int userId);

    List<Transfer> getPendingTransfers(int userId);

    void updateTransfer(Transfer transfer);
}
