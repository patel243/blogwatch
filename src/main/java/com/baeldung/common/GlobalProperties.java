package com.baeldung.common;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.baeldung.util.Utils;

public class GlobalProperties {
    
    public static Map<String, List<String>> properties = new HashMap<>();

    static {
        Yaml yaml = new Yaml();
        InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream("ignore-list.yaml");        
                 
        properties =  yaml.load(inputStream);
        
         
       /* for(Map.Entry<String, List<String>> entry:properties.entrySet()) {
            System.out.println("---------------------------");
            System.out.println(entry.getKey());
            for(String s: entry.getValue()) {
                System.out.println(s);
            }
        }*/
        
      
    }
   
}
