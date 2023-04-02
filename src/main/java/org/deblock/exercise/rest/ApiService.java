package org.deblock.exercise.rest;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T request(String requestUrl, T response, String...requestVariables) {
        // use restTemplate to prepare an HTTP request to {requestUrl}

        return response;
    }
}
