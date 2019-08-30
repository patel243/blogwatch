package com.baeldung.common;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.yaml.snakeyaml.Yaml;

public class GlobalProperties {

    public static Map<String, List<String>> properties = new HashMap<>();

    static {
        try {
            Yaml yaml = new Yaml();
            InputStream ignoreListYml = GlobalProperties.class.getClassLoader().getResourceAsStream("ignore-list.yaml");
            properties = yaml.load(ignoreListYml);
            InputStream testExceptionUrls = GlobalProperties.class.getClassLoader().getResourceAsStream("exceptions-for-tests-hitting-all-urls.yaml");
            Map<String, List<String>> testExceptionUrlsMap = yaml.load(testExceptionUrls);
            testExceptionUrlsMap.forEach((key, urlList) -> {
                if (CollectionUtils.isNotEmpty(urlList)) {
                    properties.merge(key, urlList, (list1, list2) -> {
                        list1.addAll(list2);
                        return list1;
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        /* for(Map.Entry<String, List<String>> entry:properties.entrySet()) {
            System.out.println("---------------------------");
            System.out.println(entry.getKey());
            for(String s: entry.getValue()) {
                System.out.println(s);
            }
        }*/

    }

}
