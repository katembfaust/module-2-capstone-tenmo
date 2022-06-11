package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;

public interface TransferTypeDao {

    TransferType getTransferType(String user, String description);

    TransferType getTransferTypeById(String user, Long transferTypeId);
}
