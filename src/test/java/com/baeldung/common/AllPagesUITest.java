package com.baeldung.common;

import static org.junit.jupiter.api.Assertions.fail;

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

import com.baeldung.base.BaseUITest;
import com.baeldung.util.Utils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class AllPagesUITest extends BaseUITest {

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
    @Tag("givenAllThePages_whenPageLods_thenImagesPointToCorrectEnv")
    public final void givenAllThePages_whenPageLods_thenImagesPointToCorrectEnv() throws IOException {
        do {
            List<WebElement> imgTags = page.findImagesPointingToInvalidEnvOnThePage();
            if (imgTags.size() > 0) {
                badURLs.put("givenAllThePages_whenArticleLods_thenImagesPointToCorrectEnv", page.getUrl() + " ( " + imgTags.stream().map(webElement -> webElement.getAttribute("src") + " , ").collect(Collectors.joining()) + " )");
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            fail("Failed test-->" + badURLs.toString());
        }
    }
    
    @Test
    @Tag("givenAllPages_whenPageLods_thenTheMetaDescriptionExists")
    public final void givenAllPages_whenPageLods_thenTheMetaDescriptionExists() throws IOException {
        do {           
            if (!page.findMetaDescriptionTag()) {
                badURLs.put("givenAllPages_whenPageLods_thenTheMetaDescriptionExists", page.getUrl());
            }
        } while (loadNextURL());

        if (!allTestsFlag && badURLs.size() > 0) {
            fail("Failed test-->" + badURLs.toString());
        }
    }

    @Test
    @Tag("givenTestsTargetedToAllPages_whenTheTestRuns_thenItPasses")
    @Tag("givenTestsTargetedToAllUrls_whenTheTestRuns_thenItPasses")
    public final void givenTestsTargetedToAllPages_whenTheTestRuns_thenItPasses() throws IOException {
        allTestsFlag = true;
        do {
            loadNextUrl = false;
            givenAllThePages_whenPageLods_thenImagesPointToCorrectEnv();
            givenAllPages_whenPageLods_thenTheMetaDescriptionExists();
            loadNextUrl = true;
        } while (loadNextURL());

        if (badURLs.size() > 0) {
            String testsResult = "";
            for (Map.Entry<String, Collection<String>> entry : badURLs.asMap().entrySet()) {
                testsResult = testsResult + entry.getKey() + "=" + entry.getValue().toString() + "\n";
            }
            fail("Failed tests-->" + testsResult);
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
