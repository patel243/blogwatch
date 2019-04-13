package com.baeldung.crawler4j;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.Utils;
import com.baeldung.common.vo.JavaConstruct;
import com.baeldung.crawler4j.crawler.CrawlerForFindingGitHubModulesWithNoneOrEmptyReadme;
import com.baeldung.crawler4j.crawler.CrawlerForFindingJavaCode;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

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

        Multimap<String, JavaConstruct> postsWithIssues = ArrayListMultimap.create();
        Multimap<String, String> gitHubModuleAndPostsMap = null;

        logger.info("Start - creating Map for GitHub modules and Posts");
        gitHubModuleAndPostsMap = Utils.createMapForGitHubModuleAndPosts(codeSnippetCrawlerController.getBaseURL(), rateLimiter);
        logger.info("Finished - creating Map for GitHub modules and Posts");

        gitHubModuleAndPostsMap.asMap().forEach((gitHubUrl, posts) -> {

            logger.info("Getting Java Constructs from Github Module:  " + gitHubUrl);
            codeSnippetCrawlerController.setSeedURL(gitHubUrl);
            CrawlerForFindingJavaCode.baseURL = gitHubUrl;

            // get Java constructs from GitHub module
            codeSnippetCrawlerController.startCrawlingWithAFreshController(CrawlerForFindingJavaCode.class, Runtime.getRuntime().availableProcessors());
            List<JavaConstruct> javaConstructsOnGitHub = Utils.getDiscoveredJavaArtifacts(codeSnippetCrawlerController.getDiscoveredJacaConstructs());
            codeSnippetCrawlerController.shutdownCrawler();
            for (String postUrl : posts) {
                rateLimiter.acquire();
                try {
                    logger.info("Getting Java Constructs from: " + postUrl);
                    // get HTML of the post
                    Document jSoupDocument = Utils.getJSoupDocument(postUrl);

                    // get Java constructs from a post
                    List<JavaConstruct> javaConstructsOnPost = Utils.getJavaConstructsFromPreTagsInTheJSoupDocument(jSoupDocument);

                    // find Java constructs not found in GitHub module
                    Utils.filterAndCollectJacaConstructsNotFoundOnGitHub(javaConstructsOnPost, javaConstructsOnGitHub, postsWithIssues, postUrl);
                } catch (Exception e) {
                    logger.error("Error occurened while process:" + postUrl + " .Error message:" + e.getMessage());
                }
            }

        });

        if (postsWithIssues.size() > 0) {
            Utils.triggerTestFailure(postsWithIssues, codeSnippetCrawlerController.getBaseURL());
        }

    }

}
