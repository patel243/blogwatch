package com.baeldung.crawler4j.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.baeldung.common.Utils;
import com.baeldung.common.vo.JavaConstruct;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class CrawlerForFindingJavaCode extends BaseCrawler {

    protected final static Pattern FILTERS_ADDITIONAL_DIRECTORIES = Pattern.compile(".*(\\/src).*");
    public static List<String> CODE_SNIPPETS = null;
    public static String baseURL = null;
    public static List<JavaConstruct> javaConstructsOnTheBaeldungPage;
    private List<JavaConstruct> javaConstructsInTheGitHub = new ArrayList<>();

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String pageURL = url.getURL().toLowerCase();
        String referringPageURL = referringPage.getWebURL().getURL();
        // @formatter:off
        return super.commonPredicate(pageURL, referringPageURL)                                
                && FILTERS_ADDITIONAL_DIRECTORIES.matcher(pageURL).matches()
                && (pageURL.toLowerCase().startsWith(baseURL.toLowerCase()) || pageURL.toLowerCase().replace("/blob/", "/tree/").startsWith(baseURL.toLowerCase()));
        // @formatter:on
    }

    @Override
    public void visit(Page page) {
        try {
            String pageURL = page.getWebURL().getURL();

            if (pageURL.endsWith(".java")) {
                rateLimiter.acquire();
                HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                Document doc = Jsoup.parseBodyFragment(htmlParseData.getHtml(), Utils.getProtocol(pageURL) + page.getWebURL().getDomain());
                Element rawLink = doc.select("a[href$='.java']:contains(raw)").get(0);
                List<JavaConstruct> javaConstructs = Utils.getJavaConstructsFromGitHubRawUrl(rawLink.absUrl("href"));
                javaConstructsInTheGitHub.addAll(javaConstructs);
            }
        } catch (Exception e) {
            logger.error("Error occureed while parsing page:" + page.getWebURL().getURL());
        }

    }

    @Override
    public List<JavaConstruct> getMyLocalData() {
        return javaConstructsInTheGitHub;
    }

}
