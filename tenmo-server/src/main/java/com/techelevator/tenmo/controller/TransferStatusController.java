package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

public class TransferStatusController {

    @Autowired
    private TransferDao transferDao;
    @Autowired
    private TransferStatusDao transferStatusDao;
    @Autowired
    private AccountDao accountDao;


    @PreAuthorize("permitAll")
    @RequestMapping(path = "transferstatus/{id}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusById(@Valid @RequestBody @PathVariable Long id) {
        return transferStatusDao.getTransferStatusById(id);
    }

}
