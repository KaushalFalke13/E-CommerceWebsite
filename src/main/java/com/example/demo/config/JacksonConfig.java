package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class JacksonConfig {
    @SuppressWarnings("deprecation")
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Increase nesting depth limit
        objectMapper.enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        objectMapper.getFactory().setStreamWriteConstraints(StreamWriteConstraints.builder()
            .maxNestingDepth(5000) // Increase to your desired depth
            .build());  
        return objectMapper;
    }
}

