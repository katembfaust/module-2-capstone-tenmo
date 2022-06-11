package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferTypeREST implements TransferType {
    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    public TransferTypeREST(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public TransferType getTransferType(AuthenticatedUser authenticatedUser, String description) {
        TransferType transferType = null;
        try {
            String transTypeURL = baseUrl + "/transferstatus/filter?description=" + description;
            transferType = restTemplate.exchange(transTypeURL, HttpMethod.GET,
                    makeEntity(authenticatedUser), TransferType.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferType;
    }

    @Override
    public TransferType getTransferTypeById(AuthenticatedUser authenticatedUser, Long transferTypeId) {
        TransferType transferType = null;
        try {
            transferType = restTemplate.exchange(baseUrl + "/transfertype/" + transferTypeId, HttpMethod.GET,
                    makeEntity(authenticatedUser), TransferType.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferType;
    }

    public HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
