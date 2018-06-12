package com.baeldung.site.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.baeldung.config.GlobalConstants;
import com.baeldung.util.Utils;

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
        try {
            List<WebElement> links = this.getWebDriver().findElements(By.xpath("//section//a[contains(translate(@href, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + GlobalConstants.GITHUB_REPO_BAELDUNG
                    + "') or contains(translate(@href, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + GlobalConstants.GITHUB_REPO_EUGENP + "')]"));
            if (CollectionUtils.isEmpty(links)) {
                return gitHubModuleLinks;
            }

            // firstURL - the URL linked from the article
            String firstURL = links.get(links.size() - 1).getAttribute("href");
            if (StringUtils.isEmpty(firstURL)) {
                return gitHubModuleLinks;
            }
            Utils.removeTrailingSlash(firstURL);
            gitHubModuleLinks.add(firstURL);

            // secondURL- master module URL (immediate child of /master)
            int startingIndexOfMasterBranch = firstURL.indexOf("/master");
            String secondURL = null;
            if (startingIndexOfMasterBranch != -1) {
                int mainModuleEndingIndex = firstURL.indexOf("/", startingIndexOfMasterBranch + "master".length() + 2);
                if (mainModuleEndingIndex > -1) {
                    secondURL = firstURL.substring(0, mainModuleEndingIndex);

                    if (!urlAlreadyAdded(gitHubModuleLinks, secondURL)) {
                        gitHubModuleLinks.add(secondURL);
                    }
                }
            }

            // thirdURL - immediate parent module of initial(first) URL
            String thirdURL = firstURL.substring(0, firstURL.lastIndexOf("/"));
            Utils.removeTrailingSlash(thirdURL);
            if (!urlAlreadyAdded(gitHubModuleLinks, thirdURL) && !parentRepoURL(thirdURL)) {
                gitHubModuleLinks.add(thirdURL);
            }

            // fourthURL - immediate child of main repository
            String fourthURL = null;
            int endingIndexOfImmediateChildOfMainRepo = calculateEndingIndexOfImmediateChildOfMainRepo(firstURL);
            if (endingIndexOfImmediateChildOfMainRepo > -1) {
                fourthURL = firstURL.substring(0, endingIndexOfImmediateChildOfMainRepo);
                if (!urlAlreadyAdded(gitHubModuleLinks, fourthURL) && !parentRepoURL(thirdURL)) {
                    gitHubModuleLinks.add(fourthURL);
                }
            }

        } catch (Exception e) {
            logger.error("Error occurened while process:" + this.getWebDriver().getCurrentUrl() + " error message:" + e.getMessage());
        }

        return gitHubModuleLinks;
    }

    private boolean parentRepoURL(String url) {
        return url.endsWith(GlobalConstants.GITHUB_REPO_BAELDUNG) || url.endsWith(GlobalConstants.GITHUB_REPO_EUGENP);
    }

    private int calculateEndingIndexOfImmediateChildOfMainRepo(String firstURL) {
        firstURL = firstURL.toLowerCase();
        if (firstURL.indexOf(GlobalConstants.GITHUB_REPO_EUGENP.toLowerCase()) > -1) {
            return firstURL.indexOf("/", firstURL.indexOf(GlobalConstants.GITHUB_REPO_EUGENP.toLowerCase()) + GlobalConstants.GITHUB_REPO_EUGENP.length() + 2);
        }

        if (firstURL.indexOf(GlobalConstants.GITHUB_REPO_BAELDUNG.toLowerCase()) > -1) {
            return firstURL.indexOf("/", firstURL.indexOf(GlobalConstants.GITHUB_REPO_BAELDUNG.toLowerCase()) + GlobalConstants.GITHUB_REPO_BAELDUNG.length() + 2);
        }

        return -1;
    }

    private boolean urlAlreadyAdded(List<String> gitHubModuleLinks, String newURL) {
        for (String url : gitHubModuleLinks) {
            if (url.equalsIgnoreCase(newURL)) {
                return true;
            }
        }
        return false;
    }

    public boolean linkExistsInthePage(String articleRelativeURL) {
        try {
            return this.getWebDriver()
                    .findElement(By.xpath(
                            "//a[translate(@href, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')=translate('" + GlobalConstants.BAELDUNG_HOME_PAGE_URL + articleRelativeURL + "', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')]"))
                    .isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean fixedWidgetStopIDIsProvidedAsFooter() {
        return this.getWebDriver().findElements(By.xpath("//script[contains(text(), '\"stop_id\":\"footer\"')]")).size() > 0;
    }

}
