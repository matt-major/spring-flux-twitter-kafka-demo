package io.mattmajor.springfluxtwitterkafkademo;

import io.mattmajor.springfluxtwitterkafkademo.twitter.TwitterClient;
import io.mattmajor.springfluxtwitterkafkademo.twitter.model.TwitterStreamEventData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringFluxTwitterKafkaDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringFluxTwitterKafkaDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(final TwitterClient twitterClient,
                                    final KafkaProducer<String, TwitterStreamEventData> kafkaProducer) {
        return (args) -> {
            twitterClient.setupStreamRules();

            twitterClient.stream((event) -> kafkaProducer.send(new ProducerRecord<>(
                    "election_tweets",
                    event
            )));
        };
    }
}
