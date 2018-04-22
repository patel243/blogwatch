package com.baeldung.base;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.baeldung.site.base.SitePage;

public class TestUtils {

    public static boolean articleLinkFoundOnGitHubModule(List<String> gitHubModuleLinks, String articleRelativeURL, SitePage page) {
        boolean articleLinkFoundOnGitHubModule = false;
        if (CollectionUtils.isEmpty(gitHubModuleLinks)) {
            return true;
        }
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
