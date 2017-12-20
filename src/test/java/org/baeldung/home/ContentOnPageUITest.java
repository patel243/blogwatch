package org.baeldung.home;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.baeldung.config.GlobalConstants;
import org.baeldung.config.MainConfig;
import org.baeldung.site.base.SitePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MainConfig.class })
public class ContentOnPageUITest {

    @Autowired
    SitePage page;

    @BeforeEach
    public void loadNewWindow() {
        page.openNewWindow();
    }

    @Test
    @Tag(GlobalConstants.TAG_MULTI_URL)
    public final void whenPageLoads_thenContentDivExists() {
        Stream<String> URLs = null;
        try {
            List<String> urlsWithNoContent = new ArrayList<String>();
            File file = new File(getClass().getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.SAMPLE_ARTICLES_FILE_NAME).getPath());
            URLs = Files.lines(Paths.get(file.getAbsolutePath()));
            URLs.forEach(URL -> {
                try {
                    page.setPageURL(page.getBaseURL() + URL);
                    // page.loadPage();
                    page.loadPageWithThrottling();
                    assertTrue(page.findContentDiv().isDisplayed());
                } catch (Exception e) {
                    urlsWithNoContent.add(page.getBaseURL() + URL);
                }
            });
            if (urlsWithNoContent.size() > 0) {
                fail("URL with No content--->" + urlsWithNoContent.stream().collect(Collectors.joining("\n")));
            }
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            if (null != URLs) {
                URLs.close();
            }
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void whenPageWithPopup_thenPopupHasCloseButton() {
        try {
            System.out.println(page.isLaunchFlag());
            if (page.isLaunchFlag()) {
                return;
            }
            page.setPageURL(page.getBaseURL() + "/rest-with-spring-series/");
            page.loadPage();
            WebDriverWait wait = new WebDriverWait(page.getWebDriver(), 40);
            wait.until(ExpectedConditions.visibilityOf(page.findPopupCloseButton()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    // <pre> tags in article generates HTML table with div having value either 1 or blank or space
    @Test
    @Tag(GlobalConstants.TAG_MULTI_URL)
    public final void whenPageLods_thenNoEmptyDivs() {

        Stream<String> URLs = null;
        try {
            List<String> urlsWithEmptyDivs = new ArrayList<String>();
            File file = new File(getClass().getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.SAMPLE_ARTICLES_FILE_NAME).getPath());
            URLs = Files.lines(Paths.get(file.getAbsolutePath()));
            URLs.forEach(URL -> {
                try {
                    // System.out.println("Page=" + URL);
                    page.setPageURL(page.getBaseURL() + URL);
                    // page.loadPage();
                    page.loadPageWithThrottling();
                    List<WebElement> potentiallyEmptyDivs = page.findPotentiallyEmptyDivs();
                    potentiallyEmptyDivs.forEach(webElement -> {
                        // System.out.println("value="+webElement.getText()+"=");
                        // assertFalse(webElement.getText().equals(GlobalConstants.NUMBER_ONE));
                        assertFalse(StringUtils.isBlank(webElement.getText().trim()));
                    });
                } catch (Exception e) {
                    urlsWithEmptyDivs.add(page.getBaseURL() + URL);
                }
            });
            if (urlsWithEmptyDivs.size() > 0) {
                fail("URL with No content--->" + urlsWithEmptyDivs.stream().collect(Collectors.joining("\n")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        } finally {
            if (null != URLs) {
                URLs.close();
            }
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_SINGLE_URL)
    public final void givePageWithNoTitle_whenPageLoads_thenItDoesNotContainNotitleText() {
        try {
            page.setPageURL(page.getBaseURL() + "/java-weekly-sponsorship/");
            page.loadPage();
            List<WebElement> pageWithNoTitleInBody = page.pagesWithNotitleTextInBody();
            if (pageWithNoTitleInBody.size() > 0) {
                fail("Page found with '[No Title]: ID' text in body, URL:->" + page.getPageURL());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Tag(GlobalConstants.TAG_ALL_URLS)
    public final void givenTheURL_thenPageLoadsSuccessfully() {
        Stream<String> allArticlesFileContent = null;
        Stream<String> allPagesFileContent = null;
        page.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MICROSECONDS);
        try {
            List<String> badURLs = new ArrayList<String>();
            File allArticlesFile = new File(getClass().getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.ALL_ARTICLES_FILE_NAME).getPath());
            File allPagesFile = new File(getClass().getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.ALL_PAGES_FILE_NAME).getPath());
            allArticlesFileContent = Files.lines(Paths.get(allArticlesFile.getAbsolutePath()));
            allPagesFileContent = Files.lines(Paths.get(allPagesFile.getAbsolutePath()));
            Stream.concat(allArticlesFileContent, allPagesFileContent).forEach(URL -> {
                try {
                    page.setPageURL(page.getBaseURL() + URL);
                    page.loadPageWithThrottling();
                    try {
                        if (page.findPageNotFoundElement().isDisplayed()) {
                            badURLs.add(page.getBaseURL() + URL);
                        }
                    } catch (NoSuchElementException e) {
                        //
                    }
                } catch (Exception e) {
                    badURLs.add(page.getBaseURL() + URL);
                }
            });
            if (badURLs.size() > 0) {
                fail("URLs found with potential 404--->" + badURLs.stream().collect(Collectors.joining("\n")));
            }
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            if (null != allPagesFileContent) {
                allPagesFileContent.close();
            }
            if (null != allArticlesFileContent) {
                allArticlesFileContent.close();
            }
        }
    }

    @AfterEach
    public void closeWindow() {
        page.quiet();
    }
}
