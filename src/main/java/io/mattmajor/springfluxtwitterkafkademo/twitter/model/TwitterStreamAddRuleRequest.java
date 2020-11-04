package io.mattmajor.springfluxtwitterkafkademo.twitter.model;

import java.util.List;

public class TwitterStreamAddRuleRequest {
    private List<TwitterStreamAddRule> add;

    public TwitterStreamAddRuleRequest() {
    }

    public TwitterStreamAddRuleRequest(final List<TwitterStreamAddRule> add) {
        this.add = add;
    }

    public List<TwitterStreamAddRule> getAdd() {
        return add;
    }
}
