package com.baeldung.site.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class SitePage extends BlogBaseDriver {

    @Override
    public void setUrl(String pageURL) {
        this.url = pageURL;
    }

    public WebElement findContentDiv() {
        return this.getWebDriver().findElement(By.xpath(".//section[1]/div[contains(@class, 'short_box short_start')][1]"));
    }

    public boolean isContentDivDisplayed() {
        try {
            return findContentDiv().isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public WebElement findPopupCloseButton() {
        return this.getWebDriver().findElement(By.xpath(".//*[@id='tve_editor']/div[1][contains(@class, 'tve_ea_thrive_leads_form_close')]"));
    }

    public List<WebElement> findEmptyDivs() {
        return this.getWebDriver().findElements(By.xpath("//div[(contains(@class, 'line number1 index0 alt2')) and ((.='\u00a0')  or (normalize-space(.)=''))]"));
    }

    public List<WebElement> elementsWithNotitleText() {
        return this.getWebDriver().findElements(By.xpath("//*[contains(text(), '[No Title]: ID')]"));
    }

    public int getCountOfElementsWithNotitleText() {
        return elementsWithNotitleText().size();
    }

    public WebElement findBodyElement() {
        return this.getWebDriver().findElement(By.xpath("//body"));
    }

    public boolean pageNotFoundElementDisplayed() {
        try {
            return this.getWebDriver().findElement(By.id("post-not-found")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean seriesPluginElementDisplayed() {
        try {
            return this.getWebDriver().findElement(By.id("article-series-toggle")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<WebElement> getPathOfPersistenceEBookImages() {
        return this.getWebDriver().findElements(By.xpath("//*[@id='tve_editor']/div/div[1]/span/img"));
    }

    public boolean metaWithRobotsNoindexEists() {
        try {
            return this.getWebDriver().findElement(By.xpath("//meta[@name='robots'][contains(@content,'noindex')]")).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public int getAnalyticsScriptCount() {
        return this.getWebDriver().findElements(By.xpath("//script[contains(text(), 'https://www.google-analytics.com/analytics.js')]")).size();
    }

    public List<WebElement> findShortCodesAtTheEndOfThePage() {
        return this.getWebDriver().findElements(By.xpath("//div[contains(@class, 'short_box short_end')]"));
    }

    public List<WebElement> findShortCodesAtTheTopOfThePage() {
        return this.getWebDriver().findElements(By.xpath("//div[contains(@class, 'short_box short_start')]"));
    }

    public boolean findAnchorWithGAEventCall(List<String> trackingCodes) {
        try {
            return this.getWebDriver().findElement(By.xpath("//a[contains(@onclick,\"" + trackingCodes.get(0) + "\") and contains(@onclick,\"" + trackingCodes.get(1) + "\")][contains(@class,'ga-custom-event')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean findGACustomScript() {
        try {
            return this.getWebDriver().findElement(By.xpath("//script[contains(text(),\"jQuery('.ga-custom-event').on('click', function(event)\")]")).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<WebElement> findImagesPointingToInvalidEnvOnTheArticle() {
        String baseURLWithOutHttp = this.getBaseURL().substring(6);
        return this.getWebDriver().findElements(By.xpath("//section//img[( contains(@src, 'www') or contains(@src, 'http') ) and not(contains(@src, '" + this.getBaseURL() + "') or contains(@src, '" + baseURLWithOutHttp
                + "')) and not(contains(@src, 'http://cdn')) and not(contains(@src, 's.w.org')) and not(contains(@src, 'postimg.org')) and not(contains(@src, 'github.com')) and not(contains(@src, 'githubusercontent.com'))  and not(contains(@src, 'spring.io'))]"));
    }

    public List<WebElement> findImagesPointingToInvalidEnvOnThePage() {
        String baseURLWithOutHttp = this.getBaseURL().substring(6);
        return this.getWebDriver().findElements(By.xpath("//article//img[( contains(@src, 'www') or contains(@src, 'http') ) and not(contains(@src, '" + this.getBaseURL() + "') or contains(@src, '" + baseURLWithOutHttp
                + "')) and not(contains(@src, 'http://cdn')) and not(contains(@src, 'https://s.w.org'))]"));
    }

    public boolean findMetaDescriptionTag() {
        try {
            return this.getWebDriver().findElement(By.xpath("//meta[@name = 'description']")).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<String> findLinksToTheGithubModule() {
        List<String> gitHubModuleLinks = new ArrayList<String>();
        List<WebElement> links = this.getWebDriver().findElements(By
                .xpath("//section//a[contains(translate(@href, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'github.com/baeldung') or contains(translate(@href, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'github.com/eugenp')]"));
        if (CollectionUtils.isEmpty(links)) {
            return gitHubModuleLinks;
        }

        String firstURL = links.get(links.size() - 1).getAttribute("href");
        if (StringUtils.isEmpty(firstURL)) {
            return gitHubModuleLinks;
        }
        if (firstURL.charAt(firstURL.length() - 1) == '/') {
            firstURL = firstURL.substring(0, firstURL.length());
        }
        gitHubModuleLinks.add(firstURL);
        int startingIndexOfMasterBranch = firstURL.indexOf("/master");
        if (startingIndexOfMasterBranch == -1) {
            return gitHubModuleLinks;
        }
        int mainModuleEndingIndex = firstURL.indexOf("/", startingIndexOfMasterBranch + 8);
        String secondURL = mainModuleEndingIndex == -1 ? firstURL : firstURL.substring(0, mainModuleEndingIndex);

        if (!firstURL.equalsIgnoreCase(secondURL)) {
            gitHubModuleLinks.add(secondURL);
        }

        return gitHubModuleLinks;
    }

    public boolean linkExistsInthePage(String articleRelativeURL) {
        try {
            return this.getWebDriver().findElement(By.xpath("//a[contains(translate(@href, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + articleRelativeURL + "')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
