package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TransferServiceREST implements TransferService {
    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser authenticatedUser;

    public TransferServiceREST(String baseUrl, AuthenticatedUser authenticatedUser) {
        this.baseUrl = baseUrl;
        this.authenticatedUser = authenticatedUser;
    }

@Override
    public Transfer createTransfer(Long transferTypeId, Long transferStatusId, Long accountTo, Long accountFrom, Double amount) {
        String transferURL = baseUrl + "transfer/" + transferTypeId + "/" + transferStatusId + "/" +
                accountTo + "/" + accountFrom + "/" + amount;
         Transfer transfer =  new Transfer();
        try {
           transfer = restTemplate.exchange(transferURL, HttpMethod.POST, makeAuthEntity(), Transfer.class).getBody();
        } catch(RestClientResponseException e) {
            if (e.getMessage().contains("We know capitalism is hard. You don't have any money. :( ")) {
                System.out.println("It looks like you don't have enough money for that transfer, babe.");
            }
        }
        return transfer;
    }

    @Override
    public Transfer[] getAllTransfers() {
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(baseUrl + "transfer/", HttpMethod.GET,
                    makeAuthEntity(), Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("We could not complete this request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("We could complete this request due to a network error. Please try again.");
        }
        return transfers;
    }

    public Transfer getTransferById(AuthenticatedUser authenticatedUser, Transfer transferId) {
        Transfer transfer = null;
        try {
            transfer = restTemplate.exchange(baseUrl + "transfer/" + transferId,
                    HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("We could not complete this request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("We could complete this request due to a network error. Please try again.");
        }
        return transfer;
    }

    public Transfer[] getTransfersByAccountId(Long accountId) {
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(baseUrl + "transfers/" + "account/" + accountId,
                    HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("We could not complete this request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("We could complete this request due to a network error. Please try again.");
        }
        return transfers;
    }

    public Transfer[] getTransfersByUserId(Long userId) {
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(baseUrl + "transfers/" + "user/" + userId,
                    HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("We could not complete this request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("We could complete this request due to a network error. Please try again.");
        }
        return transfers;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeAuthEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transfer,headers);
    }

}
