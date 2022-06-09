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
                System.out.println("You don't have enough TEnmo Bucks");
            }
        }
    }

    @Override
    public Transfer[] getAllTransfers(AuthenticatedUser authenticatedUser) {
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(baseUrl + "/transfers", HttpMethod.GET,
                    makeEntity(authenticatedUser), Transfer[].class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer getTransferById(AuthenticatedUser authenticatedUser, Transfer transferId) {
        Transfer transfer = null;
        try {
            transfer = restTemplate.exchange(baseUrl + "/transfers/" + transfer.getTransferId(),
                    HttpMethod.GET, makeEntity(authenticatedUser), Transfer.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
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
