package com.baeldung.common.vo;

import java.util.List;

public class AnchorLinksTestDataVO {
    private String url;
    private List<String> anchorsLinks;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getAnchorsLinks() {
        return anchorsLinks;
    }

    public void setAnchorsLinks(List<String> anchorsLinks) {
        this.anchorsLinks = anchorsLinks;
    }
}
