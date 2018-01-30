package com.baeldung.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ListIterator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.base.BaseUITest;
import com.baeldung.util.Utils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class AllArticlesUITest extends BaseUITest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ListIterator<String> allArticlesList;
    Multimap<String, String> badURLs = ArrayListMultimap.create();

    boolean loadNextUrl = true;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        logger.info("inside loadNewWindow()");
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
    @Tag("givenAllTheArticles_whenArticleLods_thenArticleHasNoEmptyDiv")
    public final void givenAllTheArticles_whenArticleLods_thenArticleHasNoEmptyDiv() throws IOException {
        do {
            if (page.findEmptyDivs().size() > 0) {
                badURLs.put("givenAllTheArticles_whenArticleLods_thenArticleHasNoEmptyDiv", page.getUrl());
            }
        } while (loadNextURL());
    }

    @Test
    @Tag("givenAllArticleList_whenArticleLoads_thenIthasContent")
    public final void givenAllArticleList_whenArticleLoads_thenIthasContent() throws IOException {
        do {
            if (!page.isContentDivDisplayed()) {
                badURLs.put("givenAllArticleList_whenArticleLoads_thenIthasContent", page.getUrl());
            }
        } while (loadNextURL());
    }
    
    @Test
    @Tag("givenAllArticleList_whenArticleLoads_thenIthasSingleShortCodeAtTheEnd")
    public final void givenAllArticleList_whenArticleLoads_thenIthasSingleShortCodeAtTheEnd() throws IOException {
        do {
            if (page.findShortCodesAttheEndOfPage().size()>1) {
                badURLs.put("givenAllArticleList_whenArticleLoads_thenIthasSingleShortCodeAtTheEnd", page.getUrl());
            }
        } while (loadNextURL());
    }

    @Test
    @Tag("givenTestsTargetedToAllArticlesUrls_whenTheTestRuns_thenItPasses")
    public final void givenTestsTargetedToAllArticlesUrls_whenTheTestRuns_thenItPasses() throws IOException {
        do {
            loadNextUrl = false;
            givenAllTheArticles_whenArticleLods_thenArticleHasNoEmptyDiv();
            givenAllArticleList_whenArticleLoads_thenIthasContent();
            givenAllArticleList_whenArticleLoads_thenIthasSingleShortCodeAtTheEnd();
            loadNextUrl = true;
        } while (loadNextURL());

        if (badURLs.size() > 0) {
            fail("Failed test-->" + badURLs.toString());
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
