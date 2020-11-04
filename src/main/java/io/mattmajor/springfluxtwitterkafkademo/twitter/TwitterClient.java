package io.mattmajor.springfluxtwitterkafkademo.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mattmajor.springfluxtwitterkafkademo.twitter.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class TwitterClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterClient.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final String bearerToken;
    private final String baseUrl;
    private final RestTemplate restTemplate;

    public TwitterClient(@Value("${twitter.bearer-token}") final String bearerToken,
                         @Value("${twitter.base-url}") final String baseUrl,
                         final RestTemplate restTemplate) {
        this.bearerToken = bearerToken;
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    public void stream(final Consumer<TwitterStreamEventData> callback) {
        final var webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken)
                .build();

        final var stream = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("").queryParam("tweet.fields", "created_at").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMapMany(r -> r.bodyToFlux(byte[].class));

        stream.subscribe(r -> {
            try {
                final var event = OBJECT_MAPPER.readValue(r, TwitterStreamEvent.class);
                callback.accept(event.getData());
            } catch (final Exception e) {
                LOGGER.error("Ignoring event due to exception: {}", e.getMessage());
            }
        });
    }

    public void setupStreamRules() {
        final var existingRules = getStreamRules();
        if (existingRules.getData().size() > 0) {
            deleteStreamRules(existingRules.getData());
        }
        createStreamRules();
    }

    private TwitterStreamRuleResponse getStreamRules() {
        final var response = restTemplate.exchange(
                baseUrl + "/rules",
                HttpMethod.GET,
                new HttpEntity<>(getHeaders()),
                TwitterStreamRuleResponse.class
        );

        return response.getBody();
    }

    private void deleteStreamRules(final List<TwitterStreamRule> rules) {
        final var ruleIds = rules.stream().map(TwitterStreamRule::getId).collect(Collectors.toList());
        final var requestData = new TwitterStreamDeleteRuleRequest(ruleIds);

        final var response = restTemplate.exchange(
                baseUrl + "/rules",
                HttpMethod.POST,
                new HttpEntity<>(requestData, getHeaders()),
                String.class
        );

        LOGGER.info(response.getBody());
    }

    private void createStreamRules() {
        final var rule1 = new TwitterStreamAddRule("#election2020 -is:retweet lang:en sample:25", "election1");
        final var rule2 = new TwitterStreamAddRule("#vote2020 -is:retweet lang:en sample:25", "election2");
        final var rule3 = new TwitterStreamAddRule("#electionday -is:retweet lang:en sample:25", "election3");
        final var rule4 = new TwitterStreamAddRule("GO VOTE -is:retweet lang:en sample:25", "election4");
        final var rule5 = new TwitterStreamAddRule("trump -is:retweet lang:en sample:25", "election5");
        final var rule6 = new TwitterStreamAddRule("biden -is:retweet lang:en sample:25", "election6");

        final var requestData = new TwitterStreamAddRuleRequest(Arrays.asList(
                rule1,
                rule2,
                rule3,
                rule4,
                rule5,
                rule6
        ));

        final var response = restTemplate.exchange(
                baseUrl + "/rules",
                HttpMethod.POST,
                new HttpEntity<>(requestData, getHeaders()),
                String.class
        );

        LOGGER.info(response.getBody());
    }

    private HttpHeaders getHeaders() {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(bearerToken);
        return headers;
    }
}
