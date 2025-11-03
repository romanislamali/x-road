package com.example.Provider;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProviderController {

    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    // Public endpoint (no X-Road protection needed)
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "X-Road Provider");
        response.put("timestamp", Instant.now().toString());
        response.put("message", "Provider (/health) api successfully called!!");
        return ResponseEntity.ok(response);
    }

    // X-Road protected endpoint
    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> getData(
            @RequestHeader Map<String, String> headers) {

        // Log X-Road headers for monitoring
        logXRoadHeaders(headers);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Data from X-Road protected service");
        response.put("provider", "GOV/1234/PROVIDER-APP");
        response.put("timestamp", Instant.now().toString());
        response.put("data", Map.of(
                "id", 1,
                "name", "Sample Data",
                "value", 100.50
        ));

        logger.info("Data request processed successfully");
        return ResponseEntity.ok(response);
    }

    // X-Road protected POST endpoint
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processData(
            @RequestBody Map<String, Object> requestData,
            @RequestHeader Map<String, String> headers) {

        logXRoadHeaders(headers);
        logger.info("Processing request: {}", requestData);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "PROCESSED");
        response.put("received", requestData);
        response.put("processedAt", Instant.now().toString());
        response.put("confirmationId", "CONF-" + System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }

    private void logXRoadHeaders(Map<String, String> headers) {
        headers.forEach((key, value) -> {
            if (key.toLowerCase().contains("x-road")) {
                logger.debug("X-Road Header - {}: {}", key, value);
            }
        });
    }
}
