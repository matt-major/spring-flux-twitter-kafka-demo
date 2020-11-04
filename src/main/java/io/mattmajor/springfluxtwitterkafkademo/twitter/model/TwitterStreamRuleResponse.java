package io.mattmajor.springfluxtwitterkafkademo.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterStreamRuleResponse {
    private List<TwitterStreamRule> data;

    public TwitterStreamRuleResponse() {
    }

    public List<TwitterStreamRule> getData() {
        return data;
    }
}
