package com.baeldung.tools.articles;

import com.google.common.annotations.VisibleForTesting;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleArticleUrlsExtractor {
    private static final int TIMEOUT = 10000;
    private static final String LINK_TAG = "a";
    private static final String LINK_ATTRIBUTE = "href";

    public Set<URL> findArticleUrlsInModules(Collection<URL> moduleUrls) {
        return moduleUrls.stream()
          .map(this::parseDocument)
          .flatMap(this::selectArticleLinks)
          .map(this::toUrl)
          .collect(Collectors.toSet());
    }

    @VisibleForTesting
    Document parseDocument(URL url) {
        try {
            return Jsoup.parse(url, TIMEOUT);
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while parsing HTML document at URL " + url, e);
        }
    }

    private Stream<Element> selectArticleLinks(Document document) {
        return document
          .getElementsByTag(LINK_TAG)
          .stream()
          .filter(element -> element.attr(LINK_ATTRIBUTE).startsWith("https://www.baeldung.com/"));
    }

    private URL toUrl(Element element) {
        try {
            return new URL(element.attr(LINK_ATTRIBUTE));
        } catch (MalformedURLException e) {
            throw new IllegalStateException("An error occurred while parsing URL " + element.attr(LINK_ATTRIBUTE), e);
        }
    }
}
