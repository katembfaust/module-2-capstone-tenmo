package com.techelevator.tenmo.services;

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

public class TransferServiceREST implements TransferService {
    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    public TransferServiceREST(String baseUrl) {
        this.baseUrl = baseUrl;
    }

@Override
    public void createTransfer(AuthenticatedUser authenticatedUser, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer,headers);

        String transferURL = baseUrl + "/transfers/" + transfer.getTransferId();

        try {
            restTemplate.exchange(transferURL, HttpMethod.POST, entity, Transfer.class);
        } catch(RestClientResponseException e) {
            if (e.getMessage().contains("We know capitalism is hard. You don't have any money. :( ")) {
                System.out.println("It looks like you don't have enough money for that transfer, babe.");
            }
        }
    }

    @Override
    public Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser) {
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(baseUrl + "/transfers", HttpMethod.GET,
                    makeEntity(authenticatedUser), Transfer[].class).getBody();
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
            transfer = restTemplate.exchange(baseUrl + "/transfers/" + transferId,
                    HttpMethod.GET, makeEntity(authenticatedUser), Transfer.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("We could not complete this request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("We could complete this request due to a network error. Please try again.");
        }
        return transfer;
    }

    public HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }

}
