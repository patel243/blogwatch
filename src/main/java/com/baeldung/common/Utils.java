package com.baeldung.common;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;

import com.baeldung.common.vo.EventTrackingVO;
import com.baeldung.common.vo.LinkVO;
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

    public static Multimap<String, List<EventTrackingVO>> getCoursePagesBuyLinksTestData() {

        Multimap<String, List<EventTrackingVO>> testData = ArrayListMultimap.create();
        List<EventTrackingVO> GATrackingVOs = null;
        EventTrackingVO gaTrackingVO = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode pageJson = mapper.readTree(Utils.getCoursePagesBuyLinksJsonAsFile());
            for (JsonNode topNode : pageJson.get("coursePages")) {
                String urlKey = topNode.get("url").textValue();
                GATrackingVOs = new ArrayList<>();
                for (JsonNode pageNode : topNode.get("trackingList")) {
                    gaTrackingVO = new EventTrackingVO();
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

    public static boolean excludePage(String url, List<String> entryList, BiPredicate<String, String> p) {

        if (CollectionUtils.isEmpty(entryList)) {
            return false;
        }

        if (!url.endsWith("/")) {
            url = url + "/";
        }

        for (String entry : entryList) {
            if (!entry.endsWith("/")) {
                entry = entry + "/";
            }

            if (p.test(url, entry)) {
                return true;
            }
        }

        return false;
    }

    public static boolean excludePage(String url, List<String> entryList, boolean compareAfterAddingTrailingSlash) {

        if (CollectionUtils.isEmpty(entryList)) {
            return false;
        }

        if (!url.endsWith("/") && compareAfterAddingTrailingSlash) {
            url = url + "/";
        }

        for (String entry : entryList) {
            if (!entry.endsWith("/") && compareAfterAddingTrailingSlash) {
                entry = entry + "/";
            }
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

    public static ListIterator<String> fetchGitHubModulesReadmeLinks() throws IOException {
        File file = new File(Utils.class.getClassLoader().getResource(GlobalConstants.README_LINKS_FOLDER_PATH + GlobalConstants.README_LINKS_FILE_NAME).getPath());
        return Files.readAllLines(Paths.get(file.getAbsolutePath())).listIterator();
    }

    public static String getTheParentOfReadme(String readmeURL) {
        return readmeURL.substring(0, readmeURL.length() - 10).replace("/blob/", "/tree/"); // length of /readme.md is 10;
    }

    public static void logErrorMessageForInvalidLinksInReadmeFiles(Multimap<String, LinkVO> badURLs) {

        if (badURLs.size() > 0) {
            String testsResult = "\n\n";
            for (Map.Entry<String, Collection<LinkVO>> entry : badURLs.asMap().entrySet()) {
                testsResult = testsResult + entry.getKey() + " \n" + entry.getValue().toString() + "\n\n";
            }
            fail("we found issues with following READMEs" + testsResult);
        }

    }

    public static String getProxyServerIP(String proxyServerAddress) {
        return proxyServerAddress.substring(0, proxyServerAddress.indexOf(":"));
    }

    public static String getProxyServerPort(String proxyServerAddress) {
        return proxyServerAddress.substring(proxyServerAddress.indexOf(":") + 1);
    }

    public static List<String> getDiscoveredLinks(List<Object> discoveredURLs) {
        List<String> urls = new ArrayList<>();
        for (Object object : discoveredURLs) {
            List<String> urlList = (List<String>) object;
            urlList.forEach(link -> {
                urls.add(link);
            });
        }

        return urls;
    }

    public static String getProtocol(String pageURL) {
        // TODO Auto-generated method stub
        return pageURL.substring(0, pageURL.indexOf(":") + 3);
    }

}
