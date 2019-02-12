package com.baeldung.common;

import java.util.List;

import com.baeldung.site.base.SitePage;

public class TestUtils {

    public static boolean articleLinkFoundOnTheGitHubModule(List<String> gitHubModuleLinks, String articleRelativeURL, SitePage page) {
        boolean articleLinkFoundOnGitHubModule = false;

        for (String url : gitHubModuleLinks) {
            page.getWebDriver().get(url); // load GitHub module URL
            if (page.linkExistsInthePage(articleRelativeURL)) {
                articleLinkFoundOnGitHubModule = true;
                break;
            }
        }
        return articleLinkFoundOnGitHubModule;
    }

}
