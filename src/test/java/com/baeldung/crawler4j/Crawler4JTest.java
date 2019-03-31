package com.baeldung.crawler4j;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.Utils;
import com.baeldung.crawler4j.crawler.CrawlerForFindingGitHubModulesWithNoneOrEmptyReadme;

public class Crawler4JTest extends BaseCrawler4JTest {

    @Test
    @Tag(GlobalConstants.TAG_MONTHLY)
    @Tag("empty-or-none-readme")
    public final void givenTheGitHubModule_theModuleHasANonEmptyReadme() throws IOException {

        tutorialsRepoCrawlerController.startCrawlingWithAFreshController(CrawlerForFindingGitHubModulesWithNoneOrEmptyReadme.class, Runtime.getRuntime().availableProcessors());

        List<String> modulesWithNoneOrEmptyReadme = Utils.getDiscoveredLinks(tutorialsRepoCrawlerController.getDiscoveredURLs());
        if (modulesWithNoneOrEmptyReadme.size() > 0) {
            fail("\n Modules found with missing or empty READMEs \n" + modulesWithNoneOrEmptyReadme.stream().collect(Collectors.joining("\n")));

        }
    }

    @Test
    @Tag("givenAllTheArticles_whenAnArticleLoads_thenJavaClassesAndMethodsCanBeFoundOnGitHub")
    public final void givenAllTheArticles_whenAnArticleLoads_thenJavaClassesAndMethodsCanBeFoundOnGitHub() throws IOException {
        String url = null;
        /*for (String entry : Utils.fetchAllArticlesAsList()) {
            try {
                url = codeSnippetCrawlerController.getBaseURL() + entry;
                if (!Utils.excludePage(url, GlobalConstants.ARTILCE_JAVA_WEEKLY, false) && !Utils.excludePage(url, GlobalConstants.URL_EXCLUDED_FROM_ARTICELS_GITHUB_LINKS_TEST, true)) {
        
                    Document doc = Jsoup.connect(url).get();// Utils.getJSoupDocument(page.getWebDriver().getPageSource(), page.getUrl());
                    doc.getElementsByClass("brush: java; gutter: true");
        
                    codeSnippetCrawlerController.setSeedURL(url);
                    CrawlerForFindingJavaCode.baseURL = url;
                    codeSnippetCrawlerController.startCrawlingWithAFreshController(CrawlerForFindingJavaCode.class, Runtime.getRuntime().availableProcessors());
                }
            } catch (Exception e) {
                logger.error("Error occurened while process:" + url + " .Error message:" + e.getMessage());
            }
        
        }*/

    }

}
