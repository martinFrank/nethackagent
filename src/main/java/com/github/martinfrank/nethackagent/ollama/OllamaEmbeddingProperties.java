package com.github.martinfrank.nethackagent.ollama;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ollama.embeddings")
public class OllamaEmbeddingProperties {
    private String baseUrl;
    private String model;

    // Getter und Setter
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}
