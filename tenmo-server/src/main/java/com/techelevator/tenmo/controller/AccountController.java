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
//@PreAuthorize("isAuthenticated")
public class AccountController {

   @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;

    private TransferDao transferDao;

    private TransferTypeDao transferTypeDao;

    private TransferStatusDao transferStatusDao;


    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal user) {
        System.out.println(user.getName());
        BigDecimal balance = new BigDecimal(String.valueOf(accountDao.getBalance(user.getClass().getModifiers())));
        return balance;
    }

    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
    public Account getAccountByAccountId(@Valid @PathVariable int accountId) {
        return accountDao.getAccountByAccountId(accountId);
    }

    @RequestMapping(path = "account/user/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@Valid @PathVariable int userId) {
        return accountDao.getAccountByUserId(userId);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers() { return userDao.findAll(); }







}
