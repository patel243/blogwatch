package org.baeldung.tools;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.baeldung.config.GlobalConstants;
import org.baeldung.config.SeleniumHeadlessBrowserConfig;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BlogLinksExtractor {

    SeleniumHeadlessBrowserConfig headLessBrowser;
    WebDriver webDriver;

    public static void main(String... args) {

        BlogLinksExtractor siteMapReader = new BlogLinksExtractor();
        siteMapReader.initize();
        siteMapReader.createArticlesList();
        siteMapReader.createPagesList();
        siteMapReader.cleanup();

    }

    private void cleanup() {
        webDriver.quit();
    }

    private void initize() {
        SeleniumHeadlessBrowserConfig headLessBrowser = new SeleniumHeadlessBrowserConfig();
        headLessBrowser.setTargetEnv(GlobalConstants.TARGET_ENV_WINDOWS);
        headLessBrowser.openNewWindow();
        webDriver = headLessBrowser.getDriver();
    }

    private void createPagesList() {
        // webDriver.get(GlobalConstants.PAGES_SITEMAP_URL);
        // Document document = saxBuilder.build(new ByteArrayInputStream(webDriver.getPageSource().getBytes()));
        HttpURLConnection conn;
        try {
            URL url = new URL(GlobalConstants.PAGES_SITEMAP_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla 5.0");

            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(conn.getInputStream());
            Namespace defaultNamespace = document.getRootElement().getNamespace();
            List<Element> urlElements = document.getRootElement().getChildren("url", defaultNamespace);

            File file = new File(System.getenv("resources.siteurl.path") + GlobalConstants.ALL_PAGES_FILE_NAME);
            Path allpagesFilePath = Paths.get(file.getAbsolutePath());
            Files.write(allpagesFilePath, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            urlElements.forEach(urlNode -> {
                try {
                    Files.write(allpagesFilePath, (urlNode.getChild("loc", defaultNamespace).getText().substring(GlobalConstants.BAELDUNG_HOME_PAGE_URL.length()) + "\n").getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createArticlesList() {
        webDriver.get(GlobalConstants.FULL_ARCHIVE_URL);
        List<WebElement> archiveURLElemets = webDriver.findElements(By.xpath("//ul[contains(@class, 'car-list')]//a"));
        try {

            File file = new File(System.getenv("resources.siteurl.path") + GlobalConstants.ALL_ARTICLES_FILE_NAME);
            Path allArtilcesFilePath = Paths.get(file.getAbsolutePath());
            Files.write(allArtilcesFilePath, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            archiveURLElemets.forEach(anchorTag -> {
                try {
                    Files.write(allArtilcesFilePath, (anchorTag.getAttribute("href").substring(GlobalConstants.BAELDUNG_HOME_PAGE_URL.length()) + "\n").getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
