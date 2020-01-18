package com.baeldung.selenium.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.GlobalProperties;
import com.baeldung.common.Utils;
import com.baeldung.utility.TestUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ArticlesUITest extends BaseUISeleniumTest {

    @Value("#{'${site.excluded.authors}'.split(',')}")
    private List<String> excludedListOfAuthors;

    @Value("${single-url-to-run-all-tests}")
    private String singleURL;

    @Value("${ignore.urls.newer.than.weeks}")
    private int ignoreUrlsNewerThanWeeks;

    private ListIterator<String> allArticlesList;
    Multimap<String, String> badURLs = ArrayListMultimap.create();

    boolean loadNextUrl = true;
    boolean allTestsFlag = false;
    boolean testingSingleURL = false;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        logger.info("inside loadNewWindow()");
        allTestsFlag = false;
        page.openNewWindow();
        if (StringUtils.isNotEmpty(singleURL)) {
            if (!singleURL.contains(page.getBaseURL())) {
                Assertions.fail("Invalid URL passed to the test");
            }
            allArticlesList = Arrays.asList(singleURL.trim()).listIterator();
            testingSingleURL = true;
        } else {
            logger.info("The test will ignore URls newer than {} weeks", ignoreUrlsNewerThanWeeks);
            allArticlesList = Utils.fetchAllArtilcesAsListIterator();
        }
        badURLs.clear();
        loadNextURL();
    }

    @AfterEach
    public void closeWindow() {
        page.quiet();
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenArticleHasNoEmptyDiv() {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenArticleHasNoEmptyDiv);

        do {
            if (shouldSkipUrl(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenArticleHasNoEmptyDiv)) {
                continue;
            }
            if (page.findEmptyDivs().size() > 0) {
                badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenArticleHasNoEmptyDiv, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheTop() {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheTop);

        do {

            if (shouldSkipUrl(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheTop) || Utils.excludePage(page.getUrl(), GlobalConstants.ARTILCE_JAVA_WEEKLY, false)) {
                continue;
            }

            if (page.findShortCodesAtTheTopOfThePage().size() != 1) {
                badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheTop, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheEnd() throws IOException {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheEnd);

        do {
            if (shouldSkipUrl(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheEnd) || Utils.excludePage(page.getUrl(), GlobalConstants.ARTILCE_JAVA_WEEKLY, false)) {
                continue;
            }
            if (page.findShortCodesAtTheEndOfThePage().size() != 1) {
                badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheEnd, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv() {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv);

        do {
            List<WebElement> imgTags = page.findImagesPointingToInvalidEnvOnTheArticle();

            if (shouldSkipUrl(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv)) {
                continue;
            }

            if (imgTags.size() > 0) {
                badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv,
                        page.getUrlWithNewLineFeed() + " ( " + imgTags.stream().map(webElement -> webElement.getAttribute("src") + " , ").collect(Collectors.joining()) + ")\n");
            }

            List<WebElement> anchorTags = page.findAnchorsPointingToAnImageAndInvalidEnvOnTheArticle();
            if (anchorTags.size() > 0) {
                badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv,
                        page.getUrlWithNewLineFeed() + " ( " + anchorTags.stream().map(webElement -> webElement.getAttribute("href") + " , ").collect(Collectors.joining()) + ")\n");
            }

        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenTheMetaDescriptionExists() {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheMetaDescriptionExists);

        do {

            if (shouldSkipUrl(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheMetaDescriptionExists)) {
                continue;
            }

            if (!page.findMetaDescriptionTag()) {
                badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheMetaDescriptionExists, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    /**
     * The test looks into four locations for searching a back-link
     * First URL - the URL linked from the article
     * 2nd URL - the immediate parent of the first URL
     * 3rd URL - the master module, immediate child of \master\
     * 4th URL - the immediate child of the parent(eugenp or Baeldung) repository 
     */
    @Test
    public final void givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle() {

        log(GlobalConstants.givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle);
        log(GlobalConstants.givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheArticleTitleAndGitHubLinkMatch);

        String articleHeading = null;
        String articleRelativeUrl = null;
        List<String> linksToTheGithubModule = null;
        List<String> gitHubModulesLinkedOntheArticle = null;
        List<Integer> httpStatusCodesOtherThan200OK = null;
        do {

            gitHubModulesLinkedOntheArticle = page.gitHubModulesLinkedOnTheArticle();

            if (shouldSkipUrl(GlobalConstants.givenAllArticlesLinkingToGitHubModule_whenAnArticleLoads_thenLinkedGitHubModulesReturns200OK) || Utils.excludePage(page.getUrl(), GlobalConstants.ARTILCE_JAVA_WEEKLY, false)) {
                continue;
            }
            httpStatusCodesOtherThan200OK = TestUtils.getHTTPStatusCodesOtherThan200OK(gitHubModulesLinkedOntheArticle);
            if (httpStatusCodesOtherThan200OK.size() > 0) {
                badURLs.put(GlobalConstants.givenAllArticlesLinkingToGitHubModule_whenAnArticleLoads_thenLinkedGitHubModulesReturns200OK, page.getUrlWithNewLineFeed() + " ( HTTP status code received from GitHub module:  " + httpStatusCodesOtherThan200OK + " )");
            }

            if (shouldSkipUrl(GlobalConstants.givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle) || Utils.excludePage(page.getUrl(), GlobalConstants.ARTILCE_JAVA_WEEKLY, false)) {
                continue;
            }
            articleHeading = page.getArticleHeading();
            articleRelativeUrl = page.getRelativeUrl();
            linksToTheGithubModule = page.findLinksToTheGithubModule(gitHubModulesLinkedOntheArticle);

            if (CollectionUtils.isEmpty(linksToTheGithubModule)) {
                continue;
            }

            if (!TestUtils.articleLinkFoundOnTheGitHubModule(linksToTheGithubModule, articleRelativeUrl, page)) {
                badURLs.put(GlobalConstants.givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle, page.getUrlWithNewLineFeed());
            } else if (!shouldSkipUrl(GlobalConstants.givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheArticleTitleAndGitHubLinkMatch) && !page.articleTitleMatchesWithTheGitHubLink(articleHeading, articleRelativeUrl)) {
                badURLs.put(GlobalConstants.givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheArticleTitleAndGitHubLinkMatch, page.getUrlWithNewLineFeed());
            }

        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList() {

        log(GlobalConstants.givenAllTheArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList);

        do {

            if (shouldSkipUrl(GlobalConstants.givenAllTheArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList)) {
                continue;
            }

            String authorName = page.findAuthorOfTheArticle();
            if (excludedListOfAuthors.contains(authorName.toLowerCase())) {
                badURLs.put(GlobalConstants.givenAllTheArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath() {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath);

        do {

            if (shouldSkipUrl(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath)) {
                continue;
            }

            if (!page.findMetaTagWithOGImagePointingToTheAbsolutePath() || !page.findMetaTagWithTwitterImagePointingToTheAbsolutePath()) {
                logger.info("og:image or twitter:image check failed for: " + page.getUrl());
                badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations() {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations);

        do {

            if (shouldSkipUrl(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations)) {
                continue;
            }

            if (page.findInvalidCharactersInTheArticle()) {
                badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization() {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization);

        do {

            if (shouldSkipUrl(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization)) {
                continue;
            }

            try {
                List<String> invalidTitles = page.findInvalidTitles();
                if (invalidTitles.size() > 0) {
                    badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization, Utils.formatResultsForCapatalizationTest(page.getUrl(), invalidTitles));
                }
            } catch (Exception e) {
                logger.error("Error occurened in Title Capatilization test for: " + page.getUrl() + " error message:" + e.getMessage());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnalyzingCategories_thenTheArticleDoesNotContainUnnecessaryCategory() {

        log(GlobalConstants.givenAllArticles_whenAnalyzingCategories_thenTheArticleDoesNotContainUnnecessaryCategory);

        do {

            if (shouldSkipUrl(GlobalConstants.givenAllArticles_whenAnalyzingCategories_thenTheArticleDoesNotContainUnnecessaryCategory)) {
                continue;
            }

            if (page.hasUnnecessaryLabels()) {
                // logger.info("URL found with Spring and other more specific label:" + page.getUrlWithNewLineFeed());
                badURLs.put(GlobalConstants.givenAllArticles_whenAnalyzingCategories_thenTheArticleDoesNotContainUnnecessaryCategory, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnalyzingCodeBlocks_thenCodeBlocksAreRenderedProperly() throws InterruptedException {

        log(GlobalConstants.givenAllArticles_whenAnalyzingCodeBlocks_thenCodeBlocksAreRenderedProperly);

        do {

            if (shouldSkipUrl(GlobalConstants.givenAllArticles_whenAnalyzingCodeBlocks_thenCodeBlocksAreRenderedProperly) || Utils.excludePage(page.getUrl(), GlobalConstants.ARTILCE_JAVA_WEEKLY, false)) {
                continue;
            }

            if (!allTestsFlag) {
                Thread.sleep(1000);
            }

            if (page.hasBrokenCodeBlock()) {
                badURLs.put(GlobalConstants.givenAllArticles_whenAnalyzingCodeBlocks_thenCodeBlocksAreRenderedProperly, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_BI_MONTHLY)
    public final void givenAllLongRunningTests_whenHittingAllArticles_thenOK() throws IOException {
        allTestsFlag = true;
        do {
            loadNextUrl = false;
            try {
                givenAllArticles_whenAnArticleLoads_thenArticleHasNoEmptyDiv();
                givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheTop();
                givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheEnd();
                givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv();
                givenAllArticles_whenAnArticleLoads_thenTheMetaDescriptionExists();
                givenAllArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList();
                givenAllArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath();
                givenAllArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations();
                givenAllArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization();
                givenAllArticles_whenAnalyzingCategories_thenTheArticleDoesNotContainUnnecessaryCategory();
                givenAllArticles_whenAnalyzingCodeBlocks_thenCodeBlocksAreRenderedProperly();
                // note: this test should be called at the last because it loads a GitHub url
                givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle();
            } catch (Exception e) {
                logger.error("Error occurened while processing:" + page.getUrl() + " error message:" + StringUtils.substring(e.getMessage(), 0, 100));
            }
            loadNextUrl = true;
        } while (loadNextURL());

        if (badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }

    }

    @Test
    public final void givenAnArticle_whenAllTestsExecuted_thenOK() throws IOException {
        givenAllLongRunningTests_whenHittingAllArticles_thenOK();
    }

    private boolean loadNextURL() {
        if (!allArticlesList.hasNext() || !loadNextUrl) {
            return false;
        }

        if (StringUtils.isNotEmpty(singleURL)) {
            page.setUrl(allArticlesList.next());
        } else {
            page.setUrl(page.getBaseURL() + allArticlesList.next());
        }

        logger.info("Loading - " + page.getUrl());
        page.loadUrlWithThrottling();
        if (page.isNewerThan(ignoreUrlsNewerThanWeeks) && StringUtils.isEmpty(singleURL)) {
            logger.info("Skipping {} as it's newer than {} weeks", page.getUrl(), ignoreUrlsNewerThanWeeks);
            loadNextURL();
        }

        if (shouldSkipUrl(GlobalConstants.givenAllLongRunningTests_whenHittingAllArticles_thenOK)) {
            loadNextURL();
        }

        return true;

    }

    private void log(String testName) {
        if (testingSingleURL) {
            logger.info("Running Test - " + testName);
        }

    }

    protected boolean shouldSkipUrl(String testName) {
        if (!testingSingleURL && Utils.excludePage(page.getUrl(), GlobalProperties.properties.get(testName), true)) {
            logger.info("Skipping {} for test: {}", page.getUrl(), testName);
            return true;
        }
        return false;
    }

}
