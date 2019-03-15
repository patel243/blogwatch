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
            fail("Modules found with missing or empty READMEs \n" + modulesWithNoneOrEmptyReadme.stream().collect(Collectors.joining("\n")));

        }
    }

}
