package com.example.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class XRoadConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(XRoadConsumerService.class);

    private final RestTemplate restTemplate;
    private final XRoadConfig xRoadConfig;

    public XRoadConsumerService(RestTemplate restTemplate, XRoadConfig xRoadConfig) {
        this.restTemplate = restTemplate;
        this.xRoadConfig = xRoadConfig;
    }

    public String callProviderService(String endpoint) {
        try {
            String fullUrl = xRoadConfig.getSecurityServerUrl() +
                    xRoadConfig.getProviderServiceUrl() +
                    endpoint;

            logger.info("Calling X-Road service: {}", fullUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Road-Client", xRoadConfig.getClientId());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    fullUrl, HttpMethod.GET, entity, String.class);

            logger.info("X-Road response received: {}", response.getStatusCode());
            return response.getBody();

        } catch (Exception e) {
            logger.error("X-Road service call failed: {}", e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    public String postToProviderService(String endpoint, Object requestBody) {
        try {
            String fullUrl = xRoadConfig.getSecurityServerUrl() +
                    xRoadConfig.getProviderServiceUrl() +
                    endpoint;

            logger.info("POST to X-Road service: {}", fullUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Road-Client", xRoadConfig.getClientId());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    fullUrl, HttpMethod.POST, entity, String.class);

            return response.getBody();

        } catch (Exception e) {
            logger.error("X-Road POST call failed: {}", e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}
