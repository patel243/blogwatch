package com.baeldung.jsoup;

import com.baeldung.common.dto.DependencyDto;
import com.baeldung.common.dto.DependencyVersionDto;
import com.baeldung.jsoup.config.JSoupMainConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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
    void givenReadmesForModuleUrls_whenFindArticleUrlsInModules_thenAllArticleUrlsReturned() {
        List<URL> moduleToExtractUrls = Arrays.stream(searchedModules.split("\r?\n"))
          .map(this::toUrl)
          .collect(Collectors.toList());

        moduleArticleUrlsExtractor.findArticleUrlsInModules(moduleToExtractUrls)
          .stream()
          .collect(Collectors.toMap(
            Function.identity(),
            articleUrl -> articleNonMatchingDependencyVersionExtractor.extractNonMatchingDependencyVersions(searchedDependencyVersion, articleUrl)
          )).entrySet()
          .stream()
          .filter(entry -> !entry.getValue().isEmpty())
          .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    private URL toUrl(String moduleUrl) {
        try {
            return new URL(moduleUrl);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("The module URL couldn't be parsed: " + moduleUrl);
        }
    }
}