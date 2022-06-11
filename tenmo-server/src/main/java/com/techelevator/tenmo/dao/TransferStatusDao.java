package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;

public interface TransferStatusDao {

    public TransferStatus getTransferStatus();

    public TransferStatus getTransferStatusById(Long transferId);


}
