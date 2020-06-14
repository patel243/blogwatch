package com.baeldung.jsoup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.common.dto.DependencyDto;
import com.baeldung.common.dto.DependencyVersionDto;
import com.baeldung.jsoup.config.JSoupMainConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JSoupMainConfig.class)
class ArticleNonMatchingDependencyVersionExtractorTest {
    private final ModuleArticleUrlsExtractor moduleArticleUrlsExtractor;
    private final ArticleNonMatchingDependencyVersionExtractor articleNonMatchingDependencyVersionExtractor;
    private final String searchedModules;
    private final DependencyVersionDto searchedDependencyVersion;

    @Autowired
    ArticleNonMatchingDependencyVersionExtractorTest(
      ModuleArticleUrlsExtractor moduleArticleUrlsExtractor,
      ArticleNonMatchingDependencyVersionExtractor articleNonMatchingDependencyVersionExtractor,
      @Value("${modules:https://github.com/eugenp/tutorials/tree/master/akka-http}") String searchedModules,
      @Value("${artifactId:akka-http-jackson_2.12}") String searchedArtifactId,
      @Value("${version:10.1.12}") String searchedVersion) {
        this.moduleArticleUrlsExtractor = moduleArticleUrlsExtractor;
        this.articleNonMatchingDependencyVersionExtractor = articleNonMatchingDependencyVersionExtractor;
        this.searchedModules = searchedModules;
        this.searchedDependencyVersion = new DependencyVersionDto(new DependencyDto(searchedArtifactId), searchedVersion);
    }

    @Test
    void givenModulesAndAMavenDependency_whenAnalysingArticles_thenArticlesMatchingTheMavenDependencyReturned() {
        List<URL> moduleToExtractUrls = Arrays.stream(searchedModules.split("\r?\n"))
          .map(this::toUrl)
          .collect(Collectors.toList());

        Set<URL> articleUrlsInModules = moduleArticleUrlsExtractor.findArticleUrlsInModules(moduleToExtractUrls);
        System.out.println("Considered articles: ");
        articleUrlsInModules.forEach(articleUrl -> System.out.println("- " + articleUrl));

        Map<URL, List<DependencyVersionDto>> notMatchingDependenciesByArticle = articleUrlsInModules
          .stream()
          .collect(Collectors.toMap(
            Function.identity(),
            articleUrl -> articleNonMatchingDependencyVersionExtractor.extractNonMatchingDependencyVersions(searchedDependencyVersion, articleUrl)
          )).entrySet()
          .stream()
          .filter(entry -> !entry.getValue().isEmpty())
          .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue
          ));

        System.out.println("Found dependencies not matching the given version:");
        notMatchingDependenciesByArticle
          .forEach((articleUrl, dependencies) -> System.out.println("- " + articleUrl + ": " + dependencies));
    }

    private URL toUrl(String moduleUrl) {
        try {
            return new URL(moduleUrl);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("The module URL couldn't be parsed: " + moduleUrl);
        }
    }
}