package com.baeldung.selenium.common;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.Utils;
import com.baeldung.selenium.base.BaseUISeleniumTest;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class AllPagesUITest extends BaseUISeleniumTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ListIterator<String> allPagesList;
    Multimap<String, String> badURLs = ArrayListMultimap.create();

    boolean loadNextUrl = true;
    boolean allTestsFlag = false;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        logger.info("inside loadNewWindow()");
        allTestsFlag = false;
        page.openNewWindow();
        allPagesList = Utils.fetchAllPagesAsListIterator();
        badURLs.clear();
        loadNextURL();
    }

    @AfterEach
    public void closeWindow() {
        page.quiet();
    }

    @Test
    @Tag("givenAllThePages_whenPageLoads_thenImagesPointToCorrectEnv")
    @Tag("givenAllTheURLs_whenURLLoads_thenImagesPointToCorrectEnv")
    public final void givenAllThePages_whenPageLoads_thenImagesPointToCorrectEnv() throws IOException {
        do {
            List<WebElement> imgTags = page.findImagesPointingToInvalidEnvOnThePage();
            if (imgTags.size() > 0) {
                badURLs.put("givenAllThePages_whenPageLoads_thenImagesPointToCorrectEnv", page.getUrlWithNewLineFeed() + " ( " + imgTags.stream().map(webElement -> webElement.getAttribute("src") + " , ").collect(Collectors.joining()) + " )\n\n");
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs.toString());
        }
    }

    @Test
    @Tag("givenAllPages_whenPageLoads_thenTheMetaDescriptionExists")
    @Tag("givenAllTheURLs_whenURLLoads_thenTheMetaDescriptionExists")
    public final void givenAllPages_whenPageLoads_thenTheMetaDescriptionExists() throws IOException {
        do {
            if (!Utils.excludePage(page.getUrl(), GlobalConstants.PAGES_THANK_YOU, false) && !Utils.excludePage(page.getUrl(), GlobalConstants.URLS_EXCLUDED_FROM_META_DESCRIPTION_TEST, false) && !page.findMetaDescriptionTag()) {
                badURLs.put("givenAllPages_whenPageLoads_thenTheMetaDescriptionExists", page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs.toString());
        }
    }
    
    @Test
    @Tag("givenAllThePages_whenAPageLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath")
    @Tag("givenAllTheURls_whenAURLLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath")
    public final void givenAllThePages_whenAPageLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath() throws IOException {
        do {
            if (!page.findMetaTagWithOGImagePointingToTheAbsolutePath() || !page.findMetaTagWithTwitterImagePointingToTheAbsolutePath()) {
                badURLs.put("givenAllThePages_whenAPageLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath", page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs.toString());
        }
    }

    @Test
    @Tag("givenTestsTargetedToAllPages_whenTheTestRuns_thenItPasses")
    @Tag("givenTestsTargetedToAllUrls_whenTheTestRuns_thenItPasses")
    @Tag(GlobalConstants.TAG_BI_MONTHLY)
    public final void givenTestsTargetedToAllPages_whenTheTestRuns_thenItPasses() throws IOException {
        allTestsFlag = true;
        do {
            loadNextUrl = false;
            try {
                givenAllThePages_whenPageLoads_thenImagesPointToCorrectEnv();
                givenAllPages_whenPageLoads_thenTheMetaDescriptionExists();
                givenAllThePages_whenAPageLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath();
            } catch (Exception e) {
                logger.error("Error occurened while process:" + page.getUrl() + " error message:" + e.getMessage());
            }
            loadNextUrl = true;
        } while (loadNextURL());

        if (badURLs.size() > 0) {
            String testsResult = "\n\n\n";
            for (Map.Entry<String, Collection<String>> entry : badURLs.asMap().entrySet()) {
                testsResult = testsResult + entry.getKey() + "=" + entry.getValue().toString() + "\n\n\n";
            }
            Utils.triggerTestFailure(testsResult);
        }
    }

    private boolean loadNextURL() {
        if (!allPagesList.hasNext() || !loadNextUrl) {
            return false;
        }

        page.setUrl(page.getBaseURL() + allPagesList.next());
        logger.info(page.getUrl());

        page.loadUrlWithThrottling();

        return true;

    }

}
