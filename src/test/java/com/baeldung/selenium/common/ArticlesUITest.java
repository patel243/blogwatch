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
import com.baeldung.common.Utils;
import com.baeldung.utility.TestUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ArticlesUITest extends BaseUISeleniumTest {

    @Value("#{'${site.excluded.authors}'.split(',')}")
    private List<String> excludedListOfAuthors;

    @Value("${single-url-to-run-all-tests}")
    private String singleURL;

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
    public final void givenAllArticles_whenAnArticleLoads_thenArticleHasNoEmptyDiv() throws IOException {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenArticleHasNoEmptyDiv);

        do {
            if (page.findEmptyDivs().size() > 0) {
                badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenArticleHasNoEmptyDiv, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheTop() throws IOException {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheTop);

        do {
            if (Utils.excludePage(page.getUrl(), GlobalConstants.ARTILCE_JAVA_WEEKLY, false) || Utils.excludePage(page.getUrl(), GlobalConstants.URLS_EXCLUDED_FROM_SHORT_CODE_AT_THE_TOP_TEST, true)) {
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
            if (Utils.excludePage(page.getUrl(), GlobalConstants.ARTILCE_JAVA_WEEKLY, false) || Utils.excludePage(page.getUrl(), GlobalConstants.URLS_EXCLUDED_FROM_SHORT_CODE_AT_THE_END_TEST, true)) {
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
    public final void givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv() throws IOException {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv);

        do {
            List<WebElement> imgTags = page.findImagesPointingToInvalidEnvOnTheArticle();

            if (imgTags.size() > 0) {
                badURLs.put(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv,
                        page.getUrlWithNewLineFeed() + " ( " + imgTags.stream().map(webElement -> webElement.getAttribute("src") + " , ").collect(Collectors.joining()) + ")\n");
            }

            if (Utils.excludePage(page.getUrl(), GlobalConstants.POSTS_TO_BE_EXCUDED_FOR_ANCHORS_LINK_TO_CORRECT_ENV, false)) {
                continue;
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
    public final void givenAllArticles_whenAnArticleLoads_thenTheMetaDescriptionExists() throws IOException {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheMetaDescriptionExists);

        do {
            if (!Utils.excludePage(page.getUrl(), GlobalConstants.URLS_EXCLUDED_FROM_META_DESCRIPTION_TEST, true) && !page.findMetaDescriptionTag()) {
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
    public final void givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle() throws IOException {

        log(GlobalConstants.givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle);
        log(GlobalConstants.givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheArticleTitleAndGitHubLinkMatch);

        String articleHeading = null;
        String articleRelativeUrl = null;
        List<String> findLinksToTheGithubModule = null;
        do {
            if (Utils.excludePage(page.getUrl(), GlobalConstants.ARTILCE_JAVA_WEEKLY, false) || Utils.excludePage(page.getUrl(), GlobalConstants.URL_EXCLUDED_FROM_ARTICELS_GITHUB_LINKS_TEST, true)) {
                continue;
            }
            articleHeading = page.getArticleHeading();
            articleRelativeUrl = page.getRelativeUrl();
            findLinksToTheGithubModule = page.findLinksToTheGithubModule();

            if (CollectionUtils.isEmpty(findLinksToTheGithubModule)) {
                continue;
            }

            if (!TestUtils.articleLinkFoundOnTheGitHubModule(findLinksToTheGithubModule, articleRelativeUrl, page)) {
                badURLs.put(GlobalConstants.givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle, page.getUrlWithNewLineFeed());
            } else if (!page.articleTitleMatchesWithTheGitHubLink(articleHeading, articleRelativeUrl)) { // note: the TestUtils.articleLinkFoundOnGitHubModule will have the GitHub page loaded in the browser
                badURLs.put(GlobalConstants.givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheArticleTitleAndGitHubLinkMatch, page.getUrlWithNewLineFeed());
            }

        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList() throws IOException {

        log(GlobalConstants.givenAllTheArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList);

        do {
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
    public final void givenAllArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath() throws IOException {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath);

        do {
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
    public final void givenAllArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations() throws IOException {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations);

        do {

            if (Utils.excludePage(page.getUrl(), GlobalConstants.POSTS_TO_BE_EXCUDED_FOR_WRONG_QUOTATIONS_TEST, true)) {
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
    public final void givenAllArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization() throws IOException {

        log(GlobalConstants.givenAllArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization);

        do {
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
    public final void givenAllArticles_whenAnalyzingCategories_thenTheArticleDoesNotContainUnnecessaryCategory() throws IOException {

        log(GlobalConstants.givenAllArticles_whenAnalyzingCategories_thenTheArticleDoesNotContainUnnecessaryCategory);

        do {
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

        return true;

    }

    private void log(String testName) {
        if (testingSingleURL) {
            logger.info("Running Test - " + testName);
        }

    }

}
