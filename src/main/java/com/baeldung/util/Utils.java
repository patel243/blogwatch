package com.baeldung.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ListIterator;
import java.util.stream.Stream;

import com.baeldung.config.GlobalConstants;

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

}
