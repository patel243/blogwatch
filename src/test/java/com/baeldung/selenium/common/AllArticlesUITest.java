package com.baeldung.selenium.common;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.Utils;
import com.baeldung.utility.TestUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class AllArticlesUITest extends BaseUISeleniumTest {

    @Value("#{'${site.excluded.authors}'.split(',')}")
    private List<String> excludedListOfAuthors;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ListIterator<String> allArticlesList;
    Multimap<String, String> badURLs = ArrayListMultimap.create();

    boolean loadNextUrl = true;
    boolean allTestsFlag = false;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        logger.info("inside loadNewWindow()");
        allTestsFlag = false;
        page.openNewWindow();
        allArticlesList = Utils.fetchAllArtilcesAsListIterator();
        badURLs.clear();
        loadNextURL();
    }

    @AfterEach
    public void closeWindow() {
        page.quiet();
    }

    @Test
    public final void givenAllTheArticles_whenArticleLoads_thenArticleHasNoEmptyDiv() throws IOException {
        do {
            if (page.findEmptyDivs().size() > 0) {
                badURLs.put(GlobalConstants.givenAllTheArticles_whenArticleLoads_thenArticleHasNoEmptyDiv, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticleList_whenArticleLoads_thenItHasSingleShortcodeAtTheTop() throws IOException {
        do {
            if (Utils.excludePage(page.getUrl(), GlobalConstants.ARTILCE_JAVA_WEEKLY, false) || Utils.excludePage(page.getUrl(), GlobalConstants.URLS_EXCLUDED_FROM_SHORT_CODE_AT_THE_TOP_TEST, true)) {
                continue;
            }
            if (page.findShortCodesAtTheTopOfThePage().size() != 1) {
                badURLs.put(GlobalConstants.givenAllArticleList_whenArticleLoads_thenItHasSingleShortcodeAtTheTop, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticleList_whenArticleLoads_thenItHasSingleShortcodeAtTheEnd() throws IOException {
        do {
            if (Utils.excludePage(page.getUrl(), GlobalConstants.ARTILCE_JAVA_WEEKLY, false) || Utils.excludePage(page.getUrl(), GlobalConstants.URLS_EXCLUDED_FROM_SHORT_CODE_AT_THE_END_TEST, true)) {
                continue;
            }
            if (page.findShortCodesAtTheEndOfThePage().size() != 1) {
                badURLs.put(GlobalConstants.givenAllArticleList_whenArticleLoads_thenItHasSingleShortcodeAtTheEnd, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllTheArticles_whenArticleLoads_thenImagesPointToCorrectEnv() throws IOException {
        do {
            List<WebElement> imgTags = page.findImagesPointingToInvalidEnvOnTheArticle();

            if (imgTags.size() > 0) {
                badURLs.put(GlobalConstants.givenAllTheArticles_whenArticleLoads_thenImagesPointToCorrectEnv,
                        page.getUrlWithNewLineFeed() + " ( " + imgTags.stream().map(webElement -> webElement.getAttribute("src") + " , ").collect(Collectors.joining()) + ")\n");
            }

            if (Utils.excludePage(page.getUrl(), GlobalConstants.POSTS_TO_BE_EXCUDED_FOR_ANCHORS_LINK_TO_CORRECT_ENV, false)) {
                continue;
            }
            List<WebElement> anchorTags = page.findAnchorsPointingToAnImageAndInvalidEnvOnTheArticle();
            if (anchorTags.size() > 0) {
                badURLs.put(GlobalConstants.givenAllTheArticles_whenArticleLoads_thenImagesPointToCorrectEnv,
                        page.getUrlWithNewLineFeed() + " ( " + anchorTags.stream().map(webElement -> webElement.getAttribute("href") + " , ").collect(Collectors.joining()) + ")\n");
            }

        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllArticles_whenArticleLoads_thenTheMetaDescriptionExists() throws IOException {
        do {
            if (!Utils.excludePage(page.getUrl(), GlobalConstants.URLS_EXCLUDED_FROM_META_DESCRIPTION_TEST, true) && !page.findMetaDescriptionTag()) {
                badURLs.put(GlobalConstants.givenAllArticles_whenArticleLoads_thenTheMetaDescriptionExists, page.getUrlWithNewLineFeed());
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
    public final void givenAllTheArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList() throws IOException {
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
    public final void givenAllTheArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath() throws IOException {
        do {
            if (!page.findMetaTagWithOGImagePointingToTheAbsolutePath() || !page.findMetaTagWithTwitterImagePointingToTheAbsolutePath()) {
                logger.info("og:image or twitter:image check failed for: " + page.getUrl());
                badURLs.put(GlobalConstants.givenAllTheArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllTheArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations() throws IOException {
        do {

            if (Utils.excludePage(page.getUrl(), GlobalConstants.POSTS_TO_BE_EXCUDED_FOR_WRONG_QUOTATIONS_TEST, true)) {
                continue;
            }

            if (page.findInvalidCharactersInTheArticle()) {
                badURLs.put(GlobalConstants.givenAllTheArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllTheArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization() throws IOException {
        do {
            try {
                List<String> invalidTitles = page.findInvalidTitles();
                if (invalidTitles.size() > 0) {
                    badURLs.put(GlobalConstants.givenAllTheArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization, Utils.formatResultsForCapatalizationTest(page.getUrl(), invalidTitles));
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
    public final void givenAllTheArticles_whenAnArticleLoads_thenTheArticleHasUnnecessaryLabels() throws IOException {
        do {
            if (page.hasUnnecessaryLabels()) {
                logger.info("URL found with Spring and other more specific label:" + page.getUrlWithNewLineFeed());
                badURLs.put(GlobalConstants.givenAllTheArticles_whenAnArticleLoads_thenTheArticleHasUnnecessaryLabels, page.getUrlWithNewLineFeed());
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
                givenAllTheArticles_whenArticleLoads_thenArticleHasNoEmptyDiv();
                givenAllArticleList_whenArticleLoads_thenItHasSingleShortcodeAtTheTop();
                givenAllArticleList_whenArticleLoads_thenItHasSingleShortcodeAtTheEnd();
                givenAllTheArticles_whenArticleLoads_thenImagesPointToCorrectEnv();
                givenAllArticles_whenArticleLoads_thenTheMetaDescriptionExists();
                givenAllTheArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList();
                givenAllTheArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath();
                givenAllTheArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations();
                givenAllTheArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization();
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

    private boolean loadNextURL() {
        if (!allArticlesList.hasNext() || !loadNextUrl) {
            return false;
        }

        page.setUrl(page.getBaseURL() + allArticlesList.next());
        logger.info(page.getUrl());
        page.loadUrlWithThrottling();

        return true;

    }

}
