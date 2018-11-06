package com.baeldung.crawler4j.crawler;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class CrawlerForFindingGitHubModulesWithNoneOrEmptyReadme extends BaseCrawler {       

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        String referringPageURL = referringPage.getWebURL().getURL();
        // @formatter:off
        return super.commonPredicate(href, referringPageURL);
        // @formatter:on
    }

    @Override
    public void visit(Page page) {    
        String pageURL = page.getWebURL().getURL();            
        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
        String html = htmlParseData.getHtml();
        Document doc = Jsoup.parseBodyFragment(html);
        String title = doc.title();
        System.out.println(new Date()+ "   "+ title);        
    }

}
