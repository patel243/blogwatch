package com.baeldung.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ListIterator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.config.GlobalConstants;
import com.baeldung.config.context.MainConfig;
import com.baeldung.config.context.MyApplicationContextInitializer;
import com.baeldung.site.base.SitePage;

@ContextConfiguration(classes = { MainConfig.class }, initializers = MyApplicationContextInitializer.class)
@ExtendWith(SpringExtension.class)
public class ExperimentalTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ListIterator<String> allArticlesList;

    boolean loadNextUrl = true;

    @Autowired
    protected SitePage page;

    @BeforeEach
    public void loadNewWindow() throws IOException {
        page.openNewWindow();
        allArticlesList = fetchAllArtilcesAsList();
    }

    @AfterEach
    public void closeWindow() {
        page.quiet();
    }

    @Test
    @Tag("experimental")
    public final void givenAllTheArticles_whenArticleLods_thenArticleHasNoEmptyDiv() throws IOException {

        logger.info("givenAllTheArticles_whenArticleLods_thenArticleHasNoEmptyDiv");
        do {
            assertFalse("Test givenAllTheArticles_whenArticleLods_thenArticleHasNoEmptyDiv failed. URL-->" + page.getUrl(), page.findEmptyDivs().size() > 0);
        } while (loadNextURL());
    }

    @Test
    @Tag("experimental")
    public final void givenAllArticleList_whenArticleLoads_thenIthasContent() throws IOException {
        logger.info("givenTheSampleArticleList_whenArticleLoads_thenIthasContent");
        do {
            assertTrue("Test givenTheSampleArticleList_whenArticleLoads_thenIthasContent failed. URL-->" + page.getUrl(), page.isContentDivDisplayed());
        } while (loadNextURL());
    }

    @Test
    @Tag("experimental")
    public final void allTests() throws IOException {
        while (loadNextURL()) {
            loadNextUrl = false;
            givenAllTheArticles_whenArticleLods_thenArticleHasNoEmptyDiv();
            givenAllArticleList_whenArticleLoads_thenIthasContent();
            loadNextUrl = true;
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

    public ListIterator<String> fetchAllArtilcesAsList() throws IOException {
        File file = new File(ExperimentalTest.class.getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.ALL_ARTICLES_FILE_NAME).getPath());
        return Files.readAllLines(Paths.get(file.getAbsolutePath())).listIterator();
    }

}
