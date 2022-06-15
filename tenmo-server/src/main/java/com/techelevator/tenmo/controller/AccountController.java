package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated")
public class AccountController {

   @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;

    private TransferDao transferDao;

    private TransferTypeDao transferTypeDao;

    private TransferStatusDao transferStatusDao;

    @PreAuthorize("permitAll")
    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Double getBalance(Principal user) {
        System.out.println(user.getName());
        return accountDao.getBalanceByUserId(userDao.findIdByUsername(user.getName()));
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "account/{id}", method = RequestMethod.GET)
    public Account getAccountByAccountId(@Valid @PathVariable Long id) {
        return accountDao.getAccountByAccountId(id);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "account/user/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@Valid @PathVariable Long id) {
        return accountDao.getAccountByUserId(id);
    }


    @PreAuthorize("permitAll")
    @RequestMapping(path = "users", method = RequestMethod.GET)
    public User[] getUsers() { return userDao.findAll(); }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "deposit/user/{id}/{amount}", method = RequestMethod.PUT)
    public Account depositAccount(@Valid @RequestBody Account account, @PathVariable Long id, @PathVariable Double amount) {
       return accountDao.depositAccount(account, id, amount);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "withdrawal/user/{id}/{amount}", method = RequestMethod.PUT)
    public Account withdrawalAccount(@Valid @RequestBody Account account, @PathVariable Long id, @PathVariable Double amount) {
        return accountDao.withdrawAccount(account, id, amount);
    }

    @PreAuthorize("permitAll")
    @RequestMapping(path = "accounts/", method = RequestMethod.GET)
    public Account[] findAllAccounts() { return accountDao.findAllAccounts(); }











}
