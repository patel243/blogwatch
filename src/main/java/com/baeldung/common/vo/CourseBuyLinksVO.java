package com.baeldung.common.vo;

import java.util.List;

public class CourseBuyLinksVO {
    private String courseUrl;
    private List<PurchaseLink> purchaseLinks;
        
    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public List<PurchaseLink> getPurchaseLinks() {
        return purchaseLinks;
    }

    public void setPurchaseLinks(List<PurchaseLink> purchaseLinks) {
        this.purchaseLinks = purchaseLinks;
    }



    public static class PurchaseLink {
        private String anchorText;
        private String anchorLink;
        private String redirectedTo;
        public String getAnchorText() {
            return anchorText;
        }
        public void setAnchorText(String anchorText) {
            this.anchorText = anchorText;
        }
        public String getAnchorLink() {
            return anchorLink;
        }
        public void setAnchorLink(String anchorLink) {
            this.anchorLink = anchorLink;
        }
        public String getRedirectedTo() {
            return redirectedTo;
        }
        public void setRedirectedTo(String redirectedTo) {
            this.redirectedTo = redirectedTo;
        }
        
        
    }

}
