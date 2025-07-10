package com.example.observability.inventory.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Value("${micrometer.topic}")
	private String topic;

	@Bean
	public NewTopic myTopic() {
		return TopicBuilder.name(topic).partitions(3).replicas(1).build();
	}
}
