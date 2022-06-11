package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;

public interface TransferType {

    public TransferType getTransferType(AuthenticatedUser authenticatedUser, String description);
    public TransferType getTransferTypeById(AuthenticatedUser authenticatedUser, Long transferTypeId);
}
