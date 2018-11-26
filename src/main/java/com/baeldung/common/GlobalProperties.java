package com.baeldung.common;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class GlobalProperties {

    public static Map<String, List<String>> properties = new HashMap<>();

    static {
        try {
            Yaml yaml = new Yaml();
            InputStream inputStream = GlobalProperties.class.getClassLoader().getResourceAsStream("ignore-list.yaml");
            properties = yaml.load(inputStream);
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
