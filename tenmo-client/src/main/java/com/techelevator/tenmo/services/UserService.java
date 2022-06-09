package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class UserService {

    private String baseUrl = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    public User[] getAllUsers(AuthenticatedUser authenticatedUser) {
        User[] users = null;
        try {
            users = restTemplate.exchange(baseUrl + "/users", HttpMethod.GET, makeEntity(authenticatedUser),
                    User[].class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    public User getByUserId(AuthenticatedUser authenticatedUser, Long userId) {
        User user = null;
        try {
            user = restTemplate.exchange(baseUrl + "/user/" + userId, HttpMethod.GET,
                    makeEntity(authenticatedUser), User.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;
    }

    private HttpEntity<User> makeEntity(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }


}
