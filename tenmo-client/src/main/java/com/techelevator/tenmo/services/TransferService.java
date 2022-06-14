package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;

public interface TransferService {

    public Transfer createTransfer(Long transferTypeId, Long transferStatusId, Long accountTo, Long accountFrom, Double amount);
    public Transfer[] getAllTransfers();
    public Transfer getTransferById(AuthenticatedUser authenticatedUser, Transfer transferId);
    Transfer[] getTransfersByAccountId(Long accountId);

}
