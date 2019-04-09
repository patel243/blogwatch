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
import com.baeldung.common.vo.JavaConstructs;
import com.baeldung.crawler4j.crawler.CrawlerForFindingGitHubModulesWithNoneOrEmptyReadme;
import com.baeldung.crawler4j.crawler.CrawlerForFindingJavaCode;

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
        Map<String, List<JavaConstructs>> pagesWithIssues = new HashMap<>();

        for (String entry : Utils.fetchAllArticlesAsList()) {
            try {
                url = codeSnippetCrawlerController.getBaseURL() + entry;
                if (Utils.excludePage(url, GlobalConstants.ARTILCE_JAVA_WEEKLY, false) && !Utils.excludePage(url, GlobalConstants.URL_EXCLUDED_FROM_ARTICELS_GITHUB_LINKS_TEST, true)) {
                    continue;
                }
                Document jSoupDocument = Utils.getJSoupDocument(url);
                List<JavaConstructs> javaConstructsOnPost = Utils.getJavaConstructsFromPreTagsInTheJSoupDocument(jSoupDocument);

                String gitHubUrl = Utils.getGitHubModuleUrl(jSoupDocument);
                codeSnippetCrawlerController.setSeedURL(gitHubUrl);
                CrawlerForFindingJavaCode.baseURL = gitHubUrl;
                codeSnippetCrawlerController.startCrawlingWithAFreshController(CrawlerForFindingJavaCode.class, Runtime.getRuntime().availableProcessors());
                List<JavaConstructs> javaConstructsOnGitHub = Utils.getDiscoveredJavaArtifacts(codeSnippetCrawlerController.getDiscoveredJacaConstructs());

                Utils.filterAndCollectJacaConstructsNotFoundOnGitHub(javaConstructsOnPost, javaConstructsOnGitHub, pagesWithIssues, entry);

            } catch (Exception e) {
                logger.error("Error occurened while process:" + url + " .Error message:" + e.getMessage());
            }

        }

        if (pagesWithIssues.size() > 0) {
            Utils.triggerTestFailure(pagesWithIssues);
        }

    }

}
