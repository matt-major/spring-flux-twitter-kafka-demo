package io.mattmajor.springfluxtwitterkafkademo.twitter.model;

import java.util.List;

public class TwitterStreamDeleteRuleRequest {
    private Delete delete;

    public TwitterStreamDeleteRuleRequest(final List<String> ids) {
        this.delete = new Delete(ids);
    }

    public Delete getDelete() {
        return delete;
    }

    public void setDelete(Delete delete) {
        this.delete = delete;
    }

    static class Delete {
        private List<String> ids;

        public Delete(List<String> ids) {
            this.ids = ids;
        }

        public List<String> getIds() {
            return ids;
        }

        public void setIds(List<String> ids) {
            this.ids = ids;
        }
    }
}
