package com.baeldung.crawler4j;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.Utils;
import com.baeldung.common.vo.JavaConstruct;
import com.baeldung.crawler4j.crawler.CrawlerForFindingGitHubModulesWithNoneOrEmptyReadme;
import com.baeldung.crawler4j.crawler.CrawlerForFindingJavaCode;

public class Crawler4JTest extends BaseCrawler4JTest {

    @Tag(GlobalConstants.TAG_MONTHLY)
    @Tag("empty-or-none-readme")
    @Test
    public final void givenTheGitHubModule_theModuleHasANonEmptyReadme() throws IOException {

        tutorialsRepoCrawlerController.startCrawlingWithAFreshController(CrawlerForFindingGitHubModulesWithNoneOrEmptyReadme.class, Runtime.getRuntime().availableProcessors());

        List<String> modulesWithNoneOrEmptyReadme = Utils.getDiscoveredLinks(tutorialsRepoCrawlerController.getDiscoveredURLs());
        if (modulesWithNoneOrEmptyReadme.size() > 0) {
            fail("\n Modules found with missing or empty READMEs \n" + modulesWithNoneOrEmptyReadme.stream().collect(Collectors.joining("\n")));

        }
    }

    @Tag("givenAllTheArticles_whenAnArticleLoads_thenJavaClassesAndMethodsCanBeFoundOnGitHub")
    @Test
    public final void givenAllTheArticles_whenAnArticleLoads_thenJavaClassesAndMethodsCanBeFoundOnGitHub() throws IOException {
        String url = null;
        Map<String, List<JavaConstruct>> pagesWithIssues = new HashMap<>();

        for (String entry : Utils.fetchAllArticlesAsList()) {
            try {
                url = codeSnippetCrawlerController.getBaseURL() + entry;
                logger.info("Processing:  " + url);
                if (Utils.excludePage(url, GlobalConstants.ARTILCE_JAVA_WEEKLY, false)) {
                    continue;
                }

                Document jSoupDocument = Utils.getJSoupDocument(url);
                List<JavaConstruct> javaConstructsOnPost = Utils.getJavaConstructsFromPreTagsInTheJSoupDocument(jSoupDocument);

                String gitHubUrl = Utils.getGitHubModuleUrl(jSoupDocument);
                logger.info("Github Module:  " + gitHubUrl);
                codeSnippetCrawlerController.setSeedURL(gitHubUrl);
                CrawlerForFindingJavaCode.baseURL = gitHubUrl;
                codeSnippetCrawlerController.startCrawlingWithAFreshController(CrawlerForFindingJavaCode.class, Runtime.getRuntime().availableProcessors());
                List<JavaConstruct> javaConstructsOnGitHub = Utils.getDiscoveredJavaArtifacts(codeSnippetCrawlerController.getDiscoveredJacaConstructs());

                Utils.filterAndCollectJacaConstructsNotFoundOnGitHub(javaConstructsOnPost, javaConstructsOnGitHub, pagesWithIssues, entry);

            } catch (Exception e) {
                logger.error("Error occurened while process:" + url + " .Error message:" + e.getMessage());
            }

        }

        if (pagesWithIssues.size() > 0) {
            Utils.triggerTestFailure(pagesWithIssues, codeSnippetCrawlerController.getBaseURL());
        }

    }

}
