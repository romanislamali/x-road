package com.example.Consumer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "xroad")
public class XRoadConfig {

    private String securityServerUrl;
    private String clientId;
    private String providerServiceUrl;

    // Getters and setters
    public String getSecurityServerUrl() {
        return securityServerUrl;
    }

    public void setSecurityServerUrl(String securityServerUrl) {
        this.securityServerUrl = securityServerUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getProviderServiceUrl() {
        return providerServiceUrl;
    }

    public void setProviderServiceUrl(String providerServiceUrl) {
        this.providerServiceUrl = providerServiceUrl;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
