package com.metroservice.metro.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    NewTopic sosAlertTopic() {
        return TopicBuilder.name("sos-alerts")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic fareProcessingTopic() {
        return TopicBuilder.name("fare-processing")
                .partitions(3)
                .replicas(1)
                .build();
    }
}