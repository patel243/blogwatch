package com.baeldung.util;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

import com.baeldung.config.GlobalConstants;
import com.baeldung.vo.GATrackingVO;
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

    public static Multimap<String, List<GATrackingVO>> getCoursePagesBuyLinksTestData() {

        Multimap<String, List<GATrackingVO>> testData = ArrayListMultimap.create();
        List<GATrackingVO> GATrackingVOs = null;
        GATrackingVO gaTrackingVO = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode pageJson = mapper.readTree(Utils.getCoursePagesBuyLinksJsonAsFile());
            for (JsonNode topNode : pageJson.get("coursePages")) {
                String urlKey = topNode.get("url").textValue();
                GATrackingVOs = new ArrayList<>();
                for (JsonNode pageNode : topNode.get("trackingList")) {
                    gaTrackingVO = new GATrackingVO();
                    List<String> trackingCodes = new ArrayList<>();
                    for (JsonNode trackingCode : pageNode.get("trackingCode")) {
                        trackingCodes.add(trackingCode.textValue());
                    }
                    gaTrackingVO.setTrackingCodes(trackingCodes);
                    gaTrackingVO.setLinkText(pageNode.get("linkText").textValue());
                    GATrackingVOs.add(gaTrackingVO);
                }
                testData.put(urlKey, GATrackingVOs);
            }
            return testData;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean excludePage(String url, List<String> entryList) {
        url = url + "/";
        for (String entry : entryList) {
            if (url.contains(entry)) {
                return true;
            }
        }
        return false;
    }

    public static void triggerTestFailure(String message) {
        fail("\n\nFailed tests-->" + message + "\n\n");
    }

    public static void removeTrailingSlash(String firstURL) {
        if (null != firstURL && firstURL.charAt(firstURL.length() - 1) == '/') {
            firstURL = firstURL.substring(0, firstURL.length());
        }
    }
    
    public static String getAbsolutePathToFileInSrc(String fileName) {
        return new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getParent() + "/src/main/resources/blog-url-list/" + fileName;
    }

}
