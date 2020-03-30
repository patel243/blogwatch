package com.baeldung.common;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.yaml.snakeyaml.Yaml;

public class YAMLProperties {

    public static Map<String, List<String>> exceptionsForEmptyReadmeTest = new HashMap<>();
    public static Map<String, List<String>> exceptionsForTestHittingAllUrls = new HashMap<>();
    public static Map<String, String> redirectsTestData = new HashMap<>();

    static {

        Yaml yaml = new Yaml();
        InputStream ignoreListYml = YAMLProperties.class.getClassLoader().getResourceAsStream("ignore-list.yaml");
        exceptionsForEmptyReadmeTest = yaml.load(ignoreListYml);

        InputStream redirectsFileStream = YAMLProperties.class.getClassLoader().getResourceAsStream("redirects.yaml");
        redirectsTestData = yaml.load(redirectsFileStream);

        InputStream testExceptionUrls = YAMLProperties.class.getClassLoader().getResourceAsStream("exceptions-for-tests-hitting-all-urls.yaml");
        Map<String, List<String>> testExceptionUrlsMap = yaml.load(testExceptionUrls);
        testExceptionUrlsMap.forEach((key, urlList) -> {
            if (CollectionUtils.isNotEmpty(urlList)) {
                exceptionsForTestHittingAllUrls.merge(key, urlList, (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                });
            }
        });

    }

}
