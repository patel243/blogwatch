package com.baeldung.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

import com.baeldung.config.GlobalConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class Utils {

    public static Stream<String> fetchSampleArtilcesList() throws IOException {
        File file = new File(Utils.class.getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.SAMPLE_ARTICLES_FILE_NAME).getPath());
        return Files.lines(Paths.get(file.getAbsolutePath()));
    }

    public static Stream<String> fetchAllArtilcesList() throws IOException {
        File file = new File(Utils.class.getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.ALL_ARTICLES_FILE_NAME).getPath());
        return Files.lines(Paths.get(file.getAbsolutePath()));
    }

    public static Stream<String> fetchAllPagesList() throws IOException {
        File file = new File(Utils.class.getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.ALL_PAGES_FILE_NAME).getPath());
        return Files.lines(Paths.get(file.getAbsolutePath()));
    }

    public static ListIterator<String> fetchAllArtilcesAsListIterator() throws IOException {
        File file = new File(Utils.class.getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.ALL_ARTICLES_FILE_NAME).getPath());
        return Files.readAllLines(Paths.get(file.getAbsolutePath())).listIterator();
    }

    public static ListIterator<String> fetchAllPagesAsListIterator() throws IOException {
        File file = new File(Utils.class.getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.ALL_PAGES_FILE_NAME).getPath());
        return Files.readAllLines(Paths.get(file.getAbsolutePath())).listIterator();
    }

    public static File getCoursePagesBuyLinksJsonAsFile() {
        return new File(Utils.class.getClassLoader().getResource(GlobalConstants.BLOG_URL_LIST_RESOUCE_FOLDER_PATH + GlobalConstants.COURSE_PAGES_BUY_LINKS).getPath());
    }

    public static Multimap<String, String> getCoursePagesBuyLinksTestData() {

        Multimap<String, String> testData = ArrayListMultimap.create();
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode pageJson = mapper.readTree(Utils.getCoursePagesBuyLinksJsonAsFile());
            for (JsonNode topNode : pageJson.get("coursePages")) {
                String urlKey = topNode.get("url").textValue();
                for (JsonNode pageNode : topNode.get("onClickCall")) {
                    testData.put(urlKey, pageNode.textValue());
                }
            }
            return testData;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean excludePage(String url, List<String> labelList) {
        for (String label : labelList) {
            if (url.contains(label)) {
                return true;
            }
        }
        return false;
    }

}
