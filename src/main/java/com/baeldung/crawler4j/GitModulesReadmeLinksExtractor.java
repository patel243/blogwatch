package com.baeldung.crawler4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.common.GlobalConstants;
import com.baeldung.common.Utils;
import com.baeldung.crawler4j.controller.TutorialsRepoCrawlerController;
import com.baeldung.crawler4j.crawler.CrawlerForFindingReadmeURLs;

@Component
public class GitModulesReadmeLinksExtractor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TutorialsRepoCrawlerController tutorialsRepoCrawlerController;

    public void findAndUpdateLinksToReadmeFiles() throws IOException {

        int totalURls = 0;
        tutorialsRepoCrawlerController.startCrawler(CrawlerForFindingReadmeURLs.class, Runtime.getRuntime().availableProcessors());
        File file = new File(Utils.class.getClassLoader().getResource(GlobalConstants.README_LINKS_FOLDER_PATH + GlobalConstants.README_LINKS_FILE_NAME).getPath());
        Path readmeLiksFilePath = Paths.get(file.getAbsolutePath());
        Files.write(readmeLiksFilePath, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        for (Object object : tutorialsRepoCrawlerController.getDiscoveredURLs()) {
            List<String> urlList = (List<String>) object;
            // logger.info("List Size: " + urlList.size());
            totalURls = totalURls + urlList.size();
            urlList.forEach(link -> {
                try {
                    Files.write(readmeLiksFilePath, (link + "\n").getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info(link);
            });
        }
        logger.info("Total README link: " + totalURls);
        File fileInSrcDirectory;
        if (Files.exists(Paths.get(Utils.getAbsolutePathToFileInSrc(GlobalConstants.README_LINKS_FILE_NAME)), LinkOption.NOFOLLOW_LINKS)) {
            fileInSrcDirectory = new File(Utils.getAbsolutePathToFileInSrc(GlobalConstants.README_LINKS_FILE_NAME));
            Path readmeLiksFilePathInSrcDirectory = Paths.get(fileInSrcDirectory.getAbsolutePath());
            Files.copy(readmeLiksFilePath, readmeLiksFilePathInSrcDirectory, StandardCopyOption.REPLACE_EXISTING);

        }

    }

}
