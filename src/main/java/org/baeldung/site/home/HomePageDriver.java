package org.baeldung.site.home;

import org.baeldung.site.base.BlogBaseDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HomePageDriver extends BlogBaseDriver {

    @Value("${site.home.page.url}")
    private String homePageURL;

    public void printHomePageTitle() {
        System.out.println(this.getTitle());
    }

    @Override
    public String getPageURL() {
        return this.homePageURL;
    }
}
