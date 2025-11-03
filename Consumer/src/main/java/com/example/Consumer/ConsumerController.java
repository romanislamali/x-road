package com.example.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/xroad")
public class ConsumerController {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    private final XRoadConsumerService xRoadService;

    public ConsumerController(XRoadConsumerService xRoadService) {
        this.xRoadService = xRoadService;
    }

    @GetMapping("/test-data")
    public String testDataService() {
        logger.info("Testing X-Road data service");
        return xRoadService.callProviderService("/api/data");
    }

    @PostMapping("/test-process")
    public String testProcessService(@RequestBody Map<String, Object> requestData) {
        logger.info("Testing X-Road process service with data: {}", requestData);
        return xRoadService.postToProviderService("/api/process", requestData);
    }

    @GetMapping("/test-health")
    public String testHealthService() {
        logger.info("Testing X-Road health service");
        return xRoadService.callProviderService("/api/health");
    }

    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "X-Road Consumer");
        response.put("timestamp", Instant.now().toString());
        return response;
    }
}
