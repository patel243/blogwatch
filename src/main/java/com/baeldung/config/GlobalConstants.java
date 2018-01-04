package com.baeldung.config;

public class GlobalConstants {

    // env properties
    public static String ENV_PROPERTY_BASE_URL = "base.url";
    public static String ENV_PROPERTY_SPRING_PROFILE = "spring.profiles.active";
    public static String ENV_PROPERTY_TARGET_ENV = "target.env";

    public static String DEFAULT_SPRING_PROFILE = "headless-browser";

    // targent env
    public static String TARGET_ENV_WINDOWS = "win";
    public static String TARGET_ENV_LINUX = "linux";

    public static String BAELDUNG_HOME_PAGE_URL = "http://www.baeldung.com";
    public static String LAUNCH_FLAG = "LAUNCH_FLAG";
    public static String NUMBER_ONE = "1";
    public static String STRING_WITH_SINGLE_SPACE = " ";

    public static String FULL_ARCHIVE_URL = "http://www.baeldung.com/full_archive";
    public static String PAGES_SITEMAP_URL = "http://www.baeldung.com/page.xml";

    // url files
    public static String ALL_ARTICLES_FILE_NAME = "all-articles.txt";
    public static String SAMPLE_ARTICLES_FILE_NAME = "sample-articles.txt";
    public static String ALL_PAGES_FILE_NAME = "all-pages.txt";
    public static String SAMPLE_PAGES_FILE_NAME = "sample-pages.txt";

    public static String BLOG_URL_LIST_RESOUCE_FOLDER_PATH = "./blog-url-list/";

    public static String BLOG_URL_LIST_RESOUCE_FOLDER_PATH_ENV_VARIABLE = "blog-url-list";

    /**
     * Tests using this tag should target a single URL. Refer to url local/class variable. 
     */
    public static final String TAG_SINGLE_URL = "singleURL";

    /**
     * Tests using this tag should target all the URLs listed in /resources/blog-url-list/sample-articles.txt
     */
    public static final String TAG_SAMPLE_ARTICLES = "sampleArticles";

    /**
     * Tests using this tag should target all the URLs listed in /resources/blog-url-list/sample-pages.txt
     */
    public static final String TAG_SAMPLE_PAGES = "samplePages";

    /**
     * Tests using this tag should target all the URLs listed in /resources/blog-url-list/all-articles.txt
     */
    public static final String TAG_ALL_ARTICLE = "allArticles";

    /**
     * Tests using this tag should target all the URLs listed in /resources/blog-url-list/all-pages.txt
     */
    public static final String TAG_ALL_PAGES = "allPages";

    // pages

    public static final String ARTICLE_WITH_POPUP = "/rest-with-spring-series/";
    public static final String ARTICLE_WITH_BLANK_TITLE = "/java-weekly-sponsorship/";
    public static final String ARTICLE_WITH_SERIES = "/spring-security-registration/";
    public static final String ARTICLE_WITH_PESISTENCE_EBOOK_DOWNLOAD = "/hibernate-spatial/";

    // for redirection tests
    public static String BAELDUNG_HOME_PAGE_URL_WITHOUT_WWW_PREFIX = "http://baeldung.com";
    public static String BAELDUNG_HOME_PAGE_URL_WITH_WWW_PREFIX = "http://www.baeldung.com";

    public static String BAELDUNG_FEED_URL = "http://www.baeldung.com/feed/";
    public static String BAELDUNG_FEED_FEEDBURNER_URL = "http://feeds.feedburner.com/Baeldung";

    // for Google Analytics

    public static final String ARTICLE_WITH_GOOGLE_ANALYTICS = "/persistence-layer-with-spring-and-hibernate/";
    public static final String PAGE_WITH_GOOGLE_ANALYTICS = "/rest-with-spring-course/";
    public static final String GOOGLE_ANALYTICS_CODE_SEARCH_STRING = "https://www.google-analytics.com/analytics.js";

    // for testing noindex on category/tag

    public static final String CATEGORY_URL = "/category/series/";
    public static final String TAG_ARTICLE_URL = "/tag/activiti/";

}
