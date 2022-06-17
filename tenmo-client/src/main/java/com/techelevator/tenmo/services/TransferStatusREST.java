package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
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
    private AuthenticatedUser authenticatedUser;

    public TransferStatusREST(String baseUrl, AuthenticatedUser authenticatedUser) {
        this.baseUrl = baseUrl;
        this.authenticatedUser = authenticatedUser;
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
        TransferStatus transferStatus = null;
        HttpEntity entity = makeEntity(authenticatedUser);
        try {
            transferStatus = restTemplate.exchange(baseUrl + "transferstatus/" + transferId, HttpMethod.GET, entity,
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

    private HttpEntity<TransferStatus> makeAuthEntity(TransferStatus transferStatus) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transferStatus,headers);
    }
}
