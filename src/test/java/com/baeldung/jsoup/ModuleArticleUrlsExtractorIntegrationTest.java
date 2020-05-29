package com.baeldung.jsoup;

import com.baeldung.jsoup.config.JSoupMainConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JSoupMainConfig.class)
class ModuleArticleUrlsExtractorIntegrationTest {
    @Autowired
    private ModuleArticleUrlsExtractor moduleArticleUrlsExtractor;

    @Test
    void givenReadmesForModuleUrls_whenFindArticleUrlsInModules_thenAllArticleUrlsReturned() throws Exception {
        assertThat(moduleArticleUrlsExtractor.findArticleUrlsInModules(moduleUrls()))
          .containsExactlyElementsOf(expectedUrls());
    }

    private List<URL> moduleUrls() throws Exception {
        return Arrays.asList(
          algorithmMiscellaneousModuleUrl(),
          coreJavaCollectionsModuleUrl()
        );
    }

    private URL algorithmMiscellaneousModuleUrl() throws Exception {
        return new URL("https://github.com/eugenp/tutorials/tree/master/algorithms-miscellaneous-1");
    }

    private URL coreJavaCollectionsModuleUrl() throws Exception {
        return new URL("https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-collections-2");
    }

    private HashSet<URL> expectedUrls() throws MalformedURLException {
        return new HashSet<>(Arrays.asList(
          new URL("https://www.baeldung.com/java-finite-automata"),
          new URL("https://www.baeldung.com/java-hill-climbing-algorithm"),
          new URL("https://www.baeldung.com/java-minimax-algorithm"),
          new URL("https://www.baeldung.com/java-levenshtein-distance"),
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