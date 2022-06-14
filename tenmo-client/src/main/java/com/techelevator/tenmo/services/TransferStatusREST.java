package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.UserCredentials;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferStatusREST implements TransferStatus {
    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    public TransferStatusREST(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public TransferStatus getTransferStatus(AuthenticatedUser authenticatedUser, String description) {
        HttpEntity entity = new HttpEntity<>(authenticatedUser);
        TransferStatus transferStatus = null;
        try {
            String transStatusURL = baseUrl + "/transferstatus/filter?description=" + description;
            transferStatus = restTemplate.exchange(transStatusURL, HttpMethod.GET, entity,
                    TransferStatus.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("We could not complete this request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("We could complete this request due to a network error. Please try again.");
        }
        return transferStatus;
    }

    @Override
    public TransferStatus getTransferStatusById(AuthenticatedUser authenticatedUser, Long transferId) {
        HttpEntity entity = new HttpEntity<>(authenticatedUser);
        TransferStatus transferStatus = null;
        try {
            transferStatus = restTemplate.exchange(baseUrl + "/transferstatus/" + transferId, HttpMethod.GET, entity,
                    TransferStatus.class).getBody();
        } catch (RestClientResponseException e) {
            System.out.println("We could not complete this request. Code: " + e.getRawStatusCode());
        } catch (ResourceAccessException e) {
            System.out.println("We could complete this request due to a network error. Please try again.");
        }
        return transferStatus;
    }

    public HttpEntity makeEntity(AuthenticatedUser authenticatedUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
