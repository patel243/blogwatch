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

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.Utils;
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
    public final void givenAllThePages_whenPageLoads_thenImagesPointToCorrectEnv() throws IOException {
        do {
            if (Utils.excludePage(page.getUrl(), GlobalConstants.PAGE_TO_BE_EXCUDED_FOR_IMAGES_LINK_TO_CORRECT_ENV, false)) {
                continue;
            }
            List<WebElement> imgTags = page.findImagesPointingToInvalidEnvOnThePage();
            List<WebElement> anchorTags = page.findAnchorsPointingToAnImageAndInvalidEnvOnTheArticle();

            if (imgTags.size() > 0) {
                badURLs.put(GlobalConstants.givenAllThePages_whenPageLoads_thenImagesPointToCorrectEnv, page.getUrlWithNewLineFeed() + " ( " + imgTags.stream().map(webElement -> webElement.getAttribute("src") + " , ").collect(Collectors.joining()) + " )\n");
            }

            if (anchorTags.size() > 0) {
                badURLs.put(GlobalConstants.givenAllThePages_whenPageLoads_thenImagesPointToCorrectEnv,
                        page.getUrlWithNewLineFeed() + " ( " + anchorTags.stream().map(webElement -> webElement.getAttribute("href") + " , ").collect(Collectors.joining()) + ")\n");
            }

        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllPages_whenPageLoads_thenTheMetaDescriptionExists() throws IOException {
        do {
            if (!Utils.excludePage(page.getUrl(), GlobalConstants.PAGES_THANK_YOU, false) && !Utils.excludePage(page.getUrl(), GlobalConstants.URLS_EXCLUDED_FROM_META_DESCRIPTION_TEST, false) && !page.findMetaDescriptionTag()) {
                badURLs.put(GlobalConstants.givenAllPages_whenPageLoads_thenTheMetaDescriptionExists, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    public final void givenAllThePages_whenAPageLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath() throws IOException {
        do {
            if (!page.findMetaTagWithOGImagePointingToTheAbsolutePath() || !page.findMetaTagWithTwitterImagePointingToTheAbsolutePath()) {
                badURLs.put(GlobalConstants.givenAllThePages_whenAPageLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath, page.getUrlWithNewLineFeed());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_BI_MONTHLY)
    public final void givenAllLongRunningTests_whenHittingAllPages_thenOK() throws IOException {
        allTestsFlag = true;
        do {
            loadNextUrl = false;
            try {
                givenAllThePages_whenPageLoads_thenImagesPointToCorrectEnv();
                givenAllPages_whenPageLoads_thenTheMetaDescriptionExists();
                givenAllThePages_whenAPageLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath();
            } catch (Exception e) {
                logger.error("Error occurened while process:" + page.getUrl() + " error message:" + StringUtils.substring(e.getMessage(), 0, 100));
            }
            loadNextUrl = true;
        } while (loadNextURL());

        if (badURLs.size() > 0) {
            Utils.triggerTestFailure(badURLs);
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
