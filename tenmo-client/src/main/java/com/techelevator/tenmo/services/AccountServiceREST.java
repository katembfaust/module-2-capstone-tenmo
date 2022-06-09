package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountServiceREST implements AccountService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public AccountServiceREST(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Balance getBalance(AuthenticatedUser authenticatedUser) {
        HttpEntity entity = new HttpEntity<>(authenticatedUser);
        Balance balance = null;
        try {
            balance = restTemplate.exchange(baseUrl + "/balance", HttpMethod.GET, entity, Balance.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    @Override
    public Account getAccountByAccountId(AuthenticatedUser authenticatedUser, Long accountId) {
        HttpEntity entity = new HttpEntity<>(authenticatedUser);
        Account account = null;
        try {
            account = restTemplate.exchange(baseUrl + "/account/" + accountId, HttpMethod.GET, entity, Account.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    @Override
    public Account getAccountByUserId(AuthenticatedUser authenticatedUser, Long userId) {
        HttpEntity entity = new HttpEntity<>(authenticatedUser);
        Account account = null;
        try {
            account = restTemplate.exchange(baseUrl + "/account/" + userId, HttpMethod.GET, entity, Account.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    private HttpEntity<UserCredentials> createCredentialsEntity(UserCredentials credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(credentials, headers);
    }
}
