package org.baeldung.home;

import org.baeldung.article.ArticlePageDriver;
import org.baeldung.base.BlogBaseDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.selenium.util.Selenium2Utils;

public final class HomePageDriver extends BlogBaseDriver<HomePageDriver> {

    public HomePageDriver(final WebDriver driverToSet) {
        super(driverToSet);
    }

    // API

    public ArticlePageDriver toArticle(final String linkText) {
        final WebElement linkToArticle = getWebDriver().findElement(By.linkText(linkText));
        linkToArticle.click();
        return new ArticlePageDriver(getWebDriver(), linkText);
    }

    // template methods

    @Override
    public HomePageDriver wait(final int seconds) {
        Selenium2Utils.Wait.waitForElementFoundByXPath(this.getWebDriver(), ".//div[@id='main']/div[4]", seconds);
        return this;
    }

    @Override
    protected String getBaseUri() {
        return super.getBaseUri();
    }

}
