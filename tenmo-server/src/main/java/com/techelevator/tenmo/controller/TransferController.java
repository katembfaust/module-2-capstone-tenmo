package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated")
public class TransferController {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TransferDao transferDao;

    private TransferTypeDao transferTypeDao;

    private TransferStatusDao transferStatusDao;

    public TransferController(){}


    public TransferController(AccountDao accountDao, UserDao userDao, TransferDao transferDao, TransferTypeDao transferTypeDao, TransferStatusDao transferStatusDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.transferTypeDao = transferTypeDao;
        this.transferStatusDao = transferStatusDao;
    }

    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "transfer/{transferTypeId}/{transferStatusId}/{accountTo}/{accountFrom}/{amount}", method = RequestMethod.POST)
    public Transfer createTransfer(@Valid @RequestBody Transfer transfer, @PathVariable Long transferTypeId, @PathVariable Long transferStatusId, @PathVariable Long accountTo, @PathVariable Long accountFrom, @PathVariable Double amount) {
      return transferDao.createTransfer(transfer, transferTypeId, transferStatusId,  accountTo, accountFrom, amount);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "transfers/account/{id}", method = RequestMethod.GET)
    public List<Transfer> getTransfersByAccountId(@Valid @PathVariable Long id) {
        return transferDao.getTransfersByAccountId(id);
    }


}
