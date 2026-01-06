package com.dezavoo.bootcamp.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Configuration class for DynamoDB beans.
 * Provides DynamoDbEnhancedClient as a Spring Bean for dependency injection.
 */
@Configuration
public class DynamoDbConfig {

    /**
     * Creates a DynamoDbClient bean.
     * Uses default AWS credentials chain and region from environment.
     */
    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder().build();
    }

    /**
     * Creates a DynamoDbEnhancedClient bean.
     * Wraps the DynamoDbClient to provide enhanced features like table mapping.
     */
    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}
