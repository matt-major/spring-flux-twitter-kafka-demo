package io.mattmajor.springfluxtwitterkafkademo.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterStreamEvent {
    private TwitterStreamEventData data;

    public TwitterStreamEvent() {
    }

    public TwitterStreamEventData getData() {
        return data;
    }

    public void setData(TwitterStreamEventData data) {
        this.data = data;
    }
}
