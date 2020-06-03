package com.baeldung.jsoup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.jsoup.config.JSoupMainConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JSoupMainConfig.class)
class ModuleArticleUrlsExtractorIntegrationTest {
    @Autowired
    private ModuleArticleUrlsExtractor moduleArticleUrlsExtractor;

    @Test
    void givenReadmesForModuleUrls_whenFindArticleUrlsInModules_thenAllArticleUrlsReturned() {
        List<URL> moduleToExtractUrls = Arrays.stream(System.getProperty("modules").split("\\r?\\n"))          
          .map(this::toUrl)
          .collect(Collectors.toList());

        moduleArticleUrlsExtractor.findArticleUrlsInModules(moduleToExtractUrls).forEach(System.out::println);
    }    

    private URL toUrl(String moduleUrl) {
        try {
            return new URL(moduleUrl);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("The module URL couldn't be parsed: " + moduleUrl);
        }
    }
}