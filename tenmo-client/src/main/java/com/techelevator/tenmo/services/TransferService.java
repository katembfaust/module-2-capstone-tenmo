package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;

public interface TransferService {

    public void createTransfer(AuthenticatedUser authenticatedUser, Transfer transfer);
    public Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser);
    public Transfer getTransferById(AuthenticatedUser authenticatedUser, Transfer transferId);

}
