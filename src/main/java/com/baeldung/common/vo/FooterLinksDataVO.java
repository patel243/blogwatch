package com.baeldung.common.vo;

import java.util.List;

public class FooterLinksDataVO {
    private String url;
    private List<link> footerLinks;

    public String getUrl() {
        return url;
    }

    public List<link> getFooterLinks() {
        return footerLinks;
    }

    public void setFooterLinks(List<link> footerLinks) {
        this.footerLinks = footerLinks;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class link {
        private String anchorText;
        private String anchorLink;

        public String getAnchorText() {
            return anchorText;
        }

        public void setAnchorTest(String anchorText) {
            this.anchorText = anchorText;
        }

        public String getAnchorLink() {
            return anchorLink;
        }

        public void setAnchorLink(String anchorLink) {
            this.anchorLink = anchorLink;
        }

    }
}
