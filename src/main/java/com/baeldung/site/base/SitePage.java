package com.baeldung.site.base;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.springframework.stereotype.Component;

import com.baeldung.GlobalConstants;
import com.baeldung.util.Utils;
import com.baeldung.vo.LinkVO;

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
            return this.getWebDriver().findElement(By.xpath("//a[contains(@class, 'article-series-header') and contains(@href, 'javascript:void(0);')]")).isDisplayed();
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

    public boolean findDivWithEventCalls(List<String> trackingCodes) {
        try {
            return this.getWebDriver().findElement(By.xpath("//div[contains(@class,\"" + trackingCodes.get(0) + "\") and contains(@class,\"" + trackingCodes.get(1) + "\")]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean findEventGenerationScript() {
        try {
            return this.getWebDriver().findElement(By.xpath("//script[contains(text(),\"_dcq.push(['track', DRIP[1], {myProp: DRIP[2]}]);\")]")).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<WebElement> findImagesPointingToInvalidEnvOnTheArticle() {
        String baseURLWithOutHttp = this.getBaseURL().substring(6);
        return this.getWebDriver().findElements(By.xpath("//section//img[( contains(@src, 'www.') or contains(@src, 'http:') or contains(@src, 'https:') ) and not(contains(@src, '" + this.getBaseURL() + "') or contains(@src, '"
                + GlobalConstants.BAELDUNG_HOME_PAGE_URL_WITHOUT_WWW_PREFIX + "') or contains(@src, '" + baseURLWithOutHttp
                + "')) and not(contains(@src, 'http://cdn')) and not(contains(@src, 's.w.org')) and not(contains(@src, 'postimg.org')) and not(contains(@src, 'github.com')) and not(contains(@src, 'githubusercontent.com'))  and not(contains(@src, 'spring.io'))]"));
    }

    public List<WebElement> findImagesPointingToInvalidEnvOnThePage() {
        String baseURLWithOutHttp = this.getBaseURL().substring(6);
        return this.getWebDriver().findElements(By.xpath("//article//img[( contains(@src, 'www.') or contains(@src, 'http:')  or contains(@src, 'https:') ) and not(contains(@src, '" + this.getBaseURL() + "') or contains(@src, '" + baseURLWithOutHttp
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
            // @formatter:off

            return this.getWebDriver() 
                            .findElement(By.xpath(
                                    "//a[(translate(@href, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')=translate('" + GlobalConstants.BAELDUNG_HOME_PAGE_URL_WITH_HTTP + articleRelativeURL + "', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) " + 
                                    " or (translate(@href, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')=translate('" + GlobalConstants.BAELDUNG_HOME_PAGE_URL + articleRelativeURL + "','ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'))]"))
                            .isDisplayed();
          // @formatter:on
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    public boolean fixedWidgetStopIDIsProvidedAsFooter() {
        return this.getWebDriver().findElements(By.xpath("//script[contains(text(), '\"stop_id\":\"footer\"')]")).size() > 0;
    }

    public String findAuthorOfTheArticle() {
        return this.getWebDriver().findElement(By.xpath("//a[contains(@rel, 'author')]")).getText().trim();
    }

    public boolean stickySidebarContainerClassPropertyIsSetupAsContent() {
        return this.getWebDriver().findElements(By.xpath("//script[contains(text(), '\"mystickyside_content_string\":\"#content\"')]")).size() > 0;
    }

    public int getDripScriptCount() {
        return this.getWebDriver().findElements(By.xpath("//script[contains(text(), \"" + GlobalConstants.DRIP_SCRPT_SEARCH_STRING + "\")]")).size();
    }

    public List<LinkVO> getLinksToTheBaeldungSite() {
        List<WebElement> anchorTags = this.getWebDriver().findElements(By.xpath("//a[contains(translate(@href, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'" + GlobalConstants.BAELDUNG_HOME_PAGE_URL_WIThOUT_THE_PROTOCOL + "')]"));
        return anchorTags.stream().map(tag -> new LinkVO(tag.getAttribute("href").toLowerCase(), tag.getText())).collect(Collectors.toList());
    }

    public boolean vatPricesAvailableThePage() throws Exception {
        Thread.sleep(15000);
        logger.info("currently loaded page-->" + this.getWebDriver().getCurrentUrl());
        logger.info("Page Title-->" + this.getWebDriver().getTitle());
        if (!this.getWebDriver().getTitle().equalsIgnoreCase(GlobalConstants.COURSE_PAGE_TITLE_FOR_VAT_TEST)) {
            throw new Exception("Course page not loaded correctly as the Page title is not correct");
        }
        String vatValue = this.getWebDriver().findElement(By.xpath("//span[contains(@class, 'price-with-vat')][1]")).getText();
        logger.info("VAT value: " + vatValue);
        return !vatValue.trim().isEmpty();
    }

    public String getGeoLocation() {
        try {
            this.setUrl("https://ipstack.com/");

            this.loadUrl();

            WebElement county = this.getWebDriver().findElement(By.xpath("//div[contains(@data-object, 'country_name')]/span"));
            return county.getText().isEmpty() ? county.getAttribute("innerHTML") : county.getText();
        } catch (Exception e) {
            return "Erroe while getting the Geo Location" + e.getMessage();
        }
    }

    public boolean geoIPProviderAPILoaded() {
        LogEntries browserLogentries = this.getWebDriver().manage().logs().get(LogType.BROWSER);
        // @formatter:off
        for (LogEntry logEntry : browserLogentries) {           
            if (StringUtils.isNotEmpty(logEntry.getMessage()) && logEntry.getLevel().equals(Level.INFO) ) {               
                if(GlobalConstants.GEOIP_API_PROVIDER_SUCCESS_LOGS.stream()
                            .filter(entry->logEntry.getMessage().toUpperCase().contains(entry))
                            .count() >= 1){
                    logger.info(logEntry.getMessage() );
                    return true;
                };
            }
        }
        // @formatter:on
        return false;
    }

    public boolean findMetaTagWithOGImagePointingToTheAbsolutePath() {
        try {
            return this.getWebDriver().findElement(By.xpath("//meta[(@property = 'og:image' and contains(@content, 'baeldung.com'))]")).isEnabled();
        } catch (NoSuchElementException e) {
            try {
                return !this.getWebDriver().findElement(By.xpath("//meta[@property = 'og:image']")).isEnabled();
            } catch (NoSuchElementException ne) {
                return true; // test shouldn't flag a post/article if meta og:image doesn't exits
            }
        }
    }

    public boolean findMetaTagWithTwitterImagePointingToTheAbsolutePath() {
        try {
            return this.getWebDriver().findElement(By.xpath("//meta[(@name = 'twitter:image' and contains(@content, 'baeldung.com'))]")).isEnabled();
        } catch (NoSuchElementException e) {
            try {
                return !this.getWebDriver().findElement(By.xpath("//meta[@name = 'twitter:image']")).isEnabled();
            } catch (NoSuchElementException ne) {
                return true; // test shouldn't flag a post/article if meta twitter:image doesn't exits
            }
        }
    }

}
