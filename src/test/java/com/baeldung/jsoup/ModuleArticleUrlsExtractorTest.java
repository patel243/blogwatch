package com.baeldung.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.spy;

class ModuleArticleUrlsExtractorTest {
    private ModuleArticleUrlsExtractor moduleArticleUrlsExtractor;

    @BeforeEach
    void beforeEach() {
        moduleArticleUrlsExtractor = spy(new ModuleArticleUrlsExtractor());
    }

    @Test
    void givenReadmesForModuleUrls_whenFindArticleUrlsInModules_thenAllArticleUrlsReturned() throws Exception {
        willReturn(algorithmMiscellaneousReadmeDocument()).given(moduleArticleUrlsExtractor)
          .parseDocument(algorithmMiscellaneousReadmeUrl());
        willReturn(coreJavaCollectionsReadmeDocument()).given(moduleArticleUrlsExtractor)
          .parseDocument(coreJavaCollectionsReadmeUrl());

        assertThat(moduleArticleUrlsExtractor.findArticleUrlsInModules(moduleUrls()))
          .containsExactlyElementsOf(expectedUrls());
    }

    private Document algorithmMiscellaneousReadmeDocument() throws IOException {
        return Jsoup.parse(getClass().getResourceAsStream("algorithm-miscellaneous-readme.html"), "UTF-8", "");
    }

    private URL algorithmMiscellaneousReadmeUrl() throws Exception {
        return new URL(algorithmMiscellaneousModuleUrl().toString() + "/README.md");
    }

    private URL algorithmMiscellaneousModuleUrl() throws Exception {
        return new URL("https://github.com/eugenp/tutorials/tree/master/algorithms-miscellaneous");
    }

    private Document coreJavaCollectionsReadmeDocument() throws IOException {
        return Jsoup.parse(getClass().getResourceAsStream("core-java-collections-readme.html"), "UTF-8", "");
    }

    private URL coreJavaCollectionsReadmeUrl() throws Exception {
        return new URL(coreJavaCollectionsModuleUrl().toString() + "/README.md");
    }

    private URL coreJavaCollectionsModuleUrl() throws Exception {
        return new URL("https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-collections");
    }

    private List<URL> moduleUrls() throws Exception {
        return Arrays.asList(
          algorithmMiscellaneousModuleUrl(),
          coreJavaCollectionsModuleUrl()
        );
    }

    private HashSet<URL> expectedUrls() throws MalformedURLException {
        return new HashSet<>(Arrays.asList(
          new URL("https://www.baeldung.com/java-finite-automata"),
          new URL("http://www.baeldung.com/java-hill-climbing-algorithm"),
          new URL("https://www.baeldung.com/java-minimax-algorithm"),
          new URL("http://www.baeldung.com/java-levenshtein-distance"),
          new URL("https://www.baeldung.com/java-kth-largest-element"),
          new URL("https://www.baeldung.com/java-collection-remove-elements"),
          new URL("https://www.baeldung.com/java-collection-filtering"),
          new URL("https://www.baeldung.com/java-join-and-split"),
          new URL("https://www.baeldung.com/java-combine-multiple-collections"),
          new URL("https://www.baeldung.com/java-combine-collections"),
          new URL("https://www.baeldung.com/java-shuffle-collection"),
          new URL("https://www.baeldung.com/java-sorting"),
          new URL("https://www.baeldung.com/java-iterable-size"),
          new URL("https://www.baeldung.com/java-null-safe-streams-from-collections")
        ));
    }
}