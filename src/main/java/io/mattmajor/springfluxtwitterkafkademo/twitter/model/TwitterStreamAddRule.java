package io.mattmajor.springfluxtwitterkafkademo.twitter.model;

public class TwitterStreamAddRule {
    private String value;
    private String tag;

    public TwitterStreamAddRule() {
    }

    public TwitterStreamAddRule(final String value, final String tag) {
        this.value = value;
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public String getTag() {
        return tag;
    }
}
