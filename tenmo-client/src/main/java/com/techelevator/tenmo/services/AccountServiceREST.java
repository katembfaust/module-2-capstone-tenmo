package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountServiceREST implements AccountService {

    private String baseUrl = "http://localhost8080";
    private RestTemplate restTemplate = new RestTemplate();


    public AccountServiceREST(String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public BigDecimal getBalance(AuthenticatedUser authenticatedUser) {
        HttpEntity entity = makeEntity(authenticatedUser);
       BigDecimal balance = null;
        try {
            balance = restTemplate.exchange(baseUrl + "/balance", HttpMethod.GET, entity, BigDecimal.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("We could not complete this request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("We could complete this request due to a network error. Please try again.");
        }
        return balance;
    }

    @Override
    public Account getAccountByAccountId(AuthenticatedUser authenticatedUser, Long accountId) {
        HttpEntity entity = new HttpEntity<>(authenticatedUser);
        Account account = null;
        try {
            account = restTemplate.exchange(baseUrl + "/account/" + accountId, HttpMethod.GET, entity, Account.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("We could not complete this request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("We could complete this request due to a network error. Please try again.");
        }
        return account;
    }

    @Override
    public Account getAccountByUserId(AuthenticatedUser authenticatedUser, Long userId) {
        HttpEntity entity = new HttpEntity<>(authenticatedUser);
        Account account = null;
        try {
            account = restTemplate.exchange(baseUrl + "/account/" + userId, HttpMethod.GET, entity, Account.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("We could not complete this request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("We could complete this request due to a network error. Please try again.");
        }
        return account;
    }

    public HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
