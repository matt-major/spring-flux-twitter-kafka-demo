package io.mattmajor.springfluxtwitterkafkademo.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterStreamRule {
    private String id;
    private String tag;
    private String value;

    public TwitterStreamRule() {
    }

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getValue() {
        return value;
    }
}
