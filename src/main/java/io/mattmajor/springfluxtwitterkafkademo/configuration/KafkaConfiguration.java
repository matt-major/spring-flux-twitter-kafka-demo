package io.mattmajor.springfluxtwitterkafkademo.configuration;

import io.mattmajor.springfluxtwitterkafkademo.kafka.KafkaJsonSerializer;
import io.mattmajor.springfluxtwitterkafkademo.twitter.model.TwitterStreamEventData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {
    @Bean
    public KafkaProducer<String, TwitterStreamEventData> kafkaProducer(final Properties producerProperties) {
        return new KafkaProducer<>(producerProperties);
    }

    @Bean
    public Properties producerProperties() {
        final var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSerializer.class.getName());

        return properties;
    }
}
