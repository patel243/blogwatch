package com.baeldung.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class GlobalConstants {

    // env properties
    public static final String ENV_PROPERTY_BASE_URL = "base.url";
    public static final String ENV_PROPERTY_SPRING_PROFILE = "spring.profiles.active";
    public static final String ENV_PROPERTY_TARGET_ENV = "target.env";
    public static final String ENV_PROPERTY_HEADLESS_BROWSER_NAME = "headless.browser.name";

    public static final String DEFAULT_SPRING_PROFILE = "headless-browser";

    // targent env
    public static final String TARGET_ENV_WINDOWS = "win";
    public static final String TARGET_ENV_LINUX = "linux";
    public static final String TARGET_ENV_DEFAULT_HEADLESS_BROWSER = GlobalConstants.HEADLESS_BROWSER_PHANTOMJS;

    // headless browser name
    public static final String HEADLESS_BROWSER_PHANTOMJS = "phantomJS";
    public static final String HEADLESS_BROWSER_HTMLUNIT = "HtmlUnit";
    public static final String HEADLESS_BROWSER_CHROME = "chrome-headless";

    public static final String BAELDUNG_HOME_PAGE_URL = "https://www.baeldung.com";
    public static final String BAELDUNG_HOME_PAGE_URL_WITH_HTTP = "http://www.baeldung.com";
    public static final String BAELDUNG_HOME_PAGE_URL_WIThOUT_THE_PROTOCOL = "www.baeldung.com";
    public static final String BAELDUNG_DOMAIN_NAME = "baeldung.com";
    public static final String LAUNCH_FLAG = "LAUNCH_FLAG";
    public static final String NUMBER_ONE = "1";
    public static final String STRING_WITH_SINGLE_SPACE = " ";

    public static final String PAGES_SITEMAP_URL = "https://www.baeldung.com/page.xml";

    // url files
    public static final String ALL_ARTICLES_FILE_NAME = "all-articles.txt";
    public static final String SAMPLE_ARTICLES_FILE_NAME = "sample-articles.txt";
    public static final String ALL_PAGES_FILE_NAME = "all-pages.txt";
    public static final String SAMPLE_PAGES_FILE_NAME = "sample-pages.txt";
    public static final String COURSE_PAGES_FILE_NAME = "course-pages.txt";

    public static final String BLOG_URL_LIST_RESOUCE_FOLDER_PATH = "./blog-url-list/";

    public static final String BLOG_URL_LIST_RESOUCE_FOLDER_PATH_ENV_VARIABLE = "blog-url-list";

    public static final String COURSE_PAGES_GA_TRACKINGS = "course-pages-ga-trackings.json";

    /**
     * Tests using this tag are executed every 30 minutes on Jenkins
     */
    public static final String TAG__EVERY_30_MINUTES = "every-30-minutes";

    /**
     * Tests using this tag are executed hourly on Jenkins
     */
    public static final String TAG_HOURLY = "hourly";

    /**
     * Tests using this tag are executed daily on Jenkins with PhantomJS
     */
    public static final String TAG_DAILY = "daily";

    /**
     * Tests using this tag are executed daily on Jenkins with HtmlUnit 
     */
    public static final String TAG_DAILY_HTMLUNIT = "daily-htmlunit";

    /**
     * Tests using this tag are executed daily on Jenkins with EU proxy 
     */
    public static final String TAG_DAILY_EU_PROXY = "daily-eu-proxy";

    /**
     * Tests using this tag are executed weekly on Jenkins
     */
    public static final String TAG_WEEKLY = "weekly";

    public static final String TAG_SITE_SMOKE_TEST = "site-smoke-test";

    public static final String TAG_SKIP_METRICS = "skip-metrics";

    /**
     * Tests using this tag can be executed using on Demand genkins job.
     */
    public static final String GA_TRACKING = "ga-tracking";

    public static final String TAG_GITHUB_RELATED = "github-related";
    public static final String TAG_EDITORIAL = "editorial";
    public static final String TAG_TECHNICAL = "technical";

    // pages

    public static final String ARTICLE_WITH_POPUP = "/rest-with-spring-series/";
    public static final String ARTICLE_WITH_SERIES = "/spring-security-registration/";
    public static final String ARTICLE_WITH_PESISTENCE_EBOOK_DOWNLOAD = "/hibernate-spatial/";

    public static final ImmutableList<String> PAGES_WITH_BLANK_TITLE = ImmutableList.of("/java-weekly-sponsorship/", "/webinar-rest-with-spring-thank-you", "/webinar-api-security-thank-you", "/webinar-api-discoverability-thank-you", "/webinar-cqrs-thank-you");

    // for redirection tests
    public static final String BAELDUNG_HOME_PAGE_URL_WITHOUT_WWW_PREFIX = "https://baeldung.com";
    public static final String BAELDUNG_HOME_PAGE_URL_WITH_WWW_PREFIX = "https://www.baeldung.com";

    public static final String BAELDUNG_FEED_URL = "https://www.baeldung.com/feed/";
    public static final String BAELDUNG_FEED_FEEDBURNER_URL = "feeds.feedburner.com/baeldung";
    public static final String BAELDUNG_RSS_FEED_URL = "https://www.baeldung.com/rss/";

    public static final String GOOGLE_ANALYTICS_CODE_SEARCH_STRING = "https://www.google-analytics.com/analytics.js";

    // for testing noindex on category/tag

    public static final String CATEGORY_URL = "/category/series/";
    public static final String TAG_ARTICLE_URL = "/tag/activiti/";

    // JW articles
    public static final List<String> ARTILCE_JAVA_WEEKLY = Arrays.asList(new String[] { "java-web-weekly", "java-weekly", "-week-review" });

    // URL should not included in the article list due to 1- article is written for 2- it's a page
    public static final List<String> flaggedArticles = Collections
            .unmodifiableList(Arrays.asList("/2016-week-review-2/", "/java-json/", "/guide-to-jayway-jsonpath/", "/conferences-in-may-2016/", "/content-editor-job/", "/video-creation-job/", "/technical-editor-job/"));

    // drip related
    public static final String DRIP_SUBSCRIPTION_QUERY_STRING = "?__s=pt6zwpj2bqmwzhwxgqnf";
    public static final String DRIP_CUTOM_FIELD = "job_role";
    public static final String DRIP_CUTOM_FIELD_VALUE_ARCHITECT = "job_role=architect";

    // thank-you page
    public static final List<String> PAGES_THANK_YOU = Arrays.asList(new String[] { "thank-you", "thanks" });

    public static final List<String> URLS_EXCLUDED_FROM_META_DESCRIPTION_TEST = Arrays.asList(new String[] { "/start-here/", "/spring-5/", "/swagger-test-on-a-page/", "/follow/", "/baeldung-company-info/", "/contact/", "/rest-course-almost-done/" });

    // Baeldung GitHub repos

    public static final String GITHUB_REPO_EUGENP = "github.com/eugenp";
    public static final String GITHUB_REPO_BAELDUNG = "github.com/baeldung";

    // for Q2W3 Fixed Widget test

    public static final String ARTICLE_FOR_FIXED_WIDGET_TEST = "/java-classloaders/";
    public static final String PAGE_FOR_FIXED_WIDGET_TEST = "/spring-tutorial";

    // for Sticky Sidebar plugin

    public static final String ARTICLE_FOR_STICKY_SIDEBAR_TEST = "/java-classloaders/";
    public static final String PAGE_FOR_STICKY_SIDEBAR_TEST = "/spring-tutorial";

    // for Drip script

    public static final String ARTICLE_WITH_DRIP_SCRIPT = "/spring-cache-tutorial/";
    public static final String PAGE_WITH_DRIP_SCRPT = "/spring-tutorial/";
    public static final String DRIP_SCRPT_SEARCH_STRING = "dc.src = '//tag.getdrip.com/9539554.js';";

    // for crawler4j
    public static final String README_LINKS_FOLDER_PATH = "./blog-url-list/";
    public static final String README_FILE_NAME_LOWERCASE = "readme.md";
    public static final String CRAWLER4J_STORAGE_FOLDER = "crawl/root";

    // VAT test
    public static final String COURSE_PAGE_FOR_VAT_TEST = "/learn-spring-security-course/";
    public static final String COURSE_PAGE_TITLE_FOR_VAT_TEST = "Learn Spring Security";

    public static final String REPO_GITHUB_TUTORIALS_LINK = "https://github.com/eugenp/tutorials";
    public static final String REPO_GITHUB_BAELDUNG_LINK = "https://github.com/baeldung";

    // GeoIP API provider logs
    public static final List<String> GEOIP_API_PROVIDER_SUCCESS_LOGS = Collections
            .unmodifiableList(Arrays.asList("VAT CALC NOTICE: GEOIP API PROVIDER IS SET TO IPDATA", "VAT CALC NOTICE: GEOIP API PROVIDER IS SET TO IPINFO.IO", "VAT CALC NOTICE: GEOIP API PROVIDER IS SET TO IPAPI.COM"));

    // ignore list for givenTheGitHubModule_theModuleHasANonEmptyReadme
    public static final String IGNORE_EMPTY_README_CONTAINING_LIST_KEY = "ignoreEmptyReadmeContaining";
    public static final String IGNORE_MISSING_README_CONTAINING_LIST_KEY = "ignoreMissingReadmeContaining";
    public static final String IGNORE_EMPTY_README_ENDING_WITH_LIST_KEY = "ignoreEmptyReadmeEndingWith";
    public static final String IGNORE_MISSING_README_ENDING_WITH_LIST_KEY = "ignoreMissingReadmeEndingWith";

    // cource page

    public static final String COURSE_RWS_PAGE = "/rest-with-spring-course/";
    public static final String COURSE_LSS_PAGE = "/learn-spring-security-course/";

    // exclude following domain for givenAllTheArticles_whenArticleLoads_thenImagesPointToCorrectEnv
    public static final ImmutableList<String> DOMAIN_LIST_TO_EXCLUDE = ImmutableList.of("http://cdn", "s.w.org", "postimg.org", "github.com", "githubusercontent.com", "spring.io", "eclipse.org", "://localhost", "wikimedia.org", "js.gleam.io");

    public static final String CONSTRUCT_TYPE_CLASS_OR_INTERFACE = "ClassOrInterface";
    public static final String CONSTRUCT_TYPE_METHOD = "Method";
    public static final String CONSTRUCT_DUMMY_CLASS_NAME = "DummyClass";
    public static final String CONSTRUCT_DUMMY_CLASS_START = "class DummyClass{ ";
    public static final String CONSTRUCT_DUMMY_CLASS_END = " }";

    // Baeldung Media Kit URL

    public static final String BAELDUNG_MEDIA_KIT_URL = "https://s3.amazonaws.com/baeldung.com/Baeldung+-+Media+Kit.pdf";

    //
    public static final List<String> springSubCategories = Arrays.asList("Spring Security", "Spring Boot", "Spring Cloud", "Spring Web", "Spring MVC");
    public static final String springCategoryOnTheSite = "Spring";

    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS_FOLLOWED_BY_SPACE = ") ";
    public static final String RIGHT_PARENTHESIS = ")";
    public static final String SPACE_DELIMITER = " ";

    public static enum TestMetricTypes {
        FAILED;
    }

    // contact us form
    public static final String CONTACT_US_FORM_URL = "/contact";

    // test names for
    public static final String givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv = "givenAllArticles_whenAnArticleLoads_thenImagesPointToCorrectEnv";
    public static final String givenAllTheURLs_whenURLLoads_thenImagesPointToCorrectEnv = "givenAllTheURLs_whenURLLoads_thenImagesPointToCorrectEnv";
    public static final String givenAllPages_whenAPageLoads_thenImagesPointToCorrectEnv = "givenAllPages_whenAPageLoads_thenImagesPointToCorrectEnv";
    public static final String givenTestsTargetedToAllUrls_whenTheTestRuns_thenItPasses = "givenTestsTargetedToAllUrls_whenTheTestRuns_thenItPasses";
    public static final String givenTestsTargetedToAllArticlesUrls_whenTheTestRuns_thenItPasses = "givenTestsTargetedToAllArticlesUrls_whenTheTestRuns_thenItPasses";
    public static final String givenTestsTargetedToAllPages_whenTheTestRuns_thenItPasses = "givenTestsTargetedToAllPages_whenTheTestRuns_thenItPasses";
    public static final String givenAllArticles_whenAnArticleLoads_thenArticleHasNoEmptyDiv = "givenAllArticles_whenAnArticleLoads_thenArticleHasNoEmptyDiv";
    public static final String givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheTop = "givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheTop";
    public static final String givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheEnd = "givenAllArticles_whenAnArticleLoads_thenItHasSingleShortcodeAtTheEnd";
    public static final String givenAllArticles_whenAnArticleLoads_thenTheMetaDescriptionExists = "givenAllArticles_whenAnArticleLoads_thenTheMetaDescriptionExists";
    public static final String givenAllTheURLs_whenURLLoads_thenTheMetaDescriptionExists = "givenAllTheURLs_whenURLLoads_thenTheMetaDescriptionExists";
    public static final String givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle = "givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle";
    public static final String givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheArticleTitleAndGitHubLinkMatch = "givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheArticleTitleAndGitHubLinkMatch";
    public static final String givenAllTheArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList = "givenAllTheArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList";
    public static final String givenAllArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath = "givenAllArticles_whenAnArticleLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath";
    public static final String givenAllTheURls_whenAURLLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath = "givenAllTheURls_whenAURLLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath";
    public static final String givenAllPages_whenAPageLoads_thenTheMetaDescriptionExists = "givenAllPages_whenAPageLoads_thenTheMetaDescriptionExists";
    public static final String givenAllPages_whenAPageLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath = "givenAllPages_whenAPageLoads_thenMetaOGImageAndTwitterImagePointToTheAbsolutePath";
    public static final String givenAllArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations = "givenAllArticles_whenAnArticleLoads_thenTheArticleDoesNotCotainWrongQuotations";
    public static final String givenAllArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization = "givenAllArticles_whenAnArticleLoads_thenTheArticleHasProperTitleCapitalization";
    public static final String givenAllArticles_whenAnalyzingCategories_thenTheArticleDoesNotContainUnnecessaryCategory = "givenAllArticles_whenAnalyzingCategories_thenTheArticleDoesNotContainUnnecessaryCategory";
    public static final String givenAllArticles_whenAnalyzingCodeBlocks_thenCodeBlocksAreRenderedProperly = "givenAllArticles_whenAnalyzingCodeBlocks_thenCodeBlocksAreRenderedProperly";;
    public static final String givenAllLongRunningTests_whenHittingAllArticles_thenOK = "givenAllLongRunningTests_whenHittingAllArticles_thenOK";
    public static final String givenAllLongRunningTests_whenHittingAllPages_thenOK = "givenAllLongRunningTests_whenHittingAllPages_thenOK";
    public static final String givenAllArticlesLinkingToGitHubModule_whenAnArticleLoads_thenLinkedGitHubModulesReturns200OK = "givenAllArticlesLinkingToGitHubModule_whenAnArticleLoads_thenLinkedGitHubModulesReturns200OK";
    public static final String givenAllPages_whenAPageLoads_thenItDoesNotContainOverlappingText = "givenAllPages_whenAPageLoads_thenItDoesNotContainOverlappingText";
    public static final String givenAllArticles_whenAnArticleLoads_thenItDoesNotContainOverlappingText = "givenAllArticles_whenAnArticleLoads_thenItDoesNotContainOverlappingText";

    public static final String givenAGoogleAnalyticsEnabledPage_whenAnalysingThePageSource_thenItHasTrackingCode = "givenAGoogleAnalyticsEnabledPage_whenAnalysingThePageSource_thenItHasTrackingCode";
    public static final Object givenAPage_whenThePageLoads_thenNoSevereMessagesInTheBrowserConsoleLog = "givenAPage_whenThePageLoads_thenNoSevereMessagesInTheBrowserConsoleLog";

}
