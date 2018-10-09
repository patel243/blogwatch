package com.baeldung.vo;

public class LinkVO {

    String link;
    String LinkText;

    public LinkVO(String link, String linkText) {
        super();
        this.link = link;
        LinkText = linkText;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkText() {
        return LinkText;
    }

    public void setLinkText(String linkText) {
        LinkText = linkText;
    }

    @Override
    public String toString() {
        return LinkText + "( " + link + " )\n";
    }

}
