package com.baeldung.common.vo;

import java.util.List;

public class FooterLinksDataVO {
    private List<String> urls;
    private String footerTag;
    private List<link> footerLinks;

    
    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }    

    public String getFooterTag() {
        return footerTag;
    }

    public void setFooterTag(String footerTag) {
        this.footerTag = footerTag;
    }

    public List<link> getFooterLinks() {
        return footerLinks;
    }

    public void setFooterLinks(List<link> footerLinks) {
        this.footerLinks = footerLinks;
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
