package org.baeldung.article;

import org.baeldung.base.BlogBaseDriver;
import org.openqa.selenium.WebDriver;

public class ArticlePageDriver extends BlogBaseDriver<ArticlePageDriver> {
    private final String name;

    public ArticlePageDriver(final WebDriver driverToSet, final String name) {
        super(driverToSet);

        this.name = name;
    }

    // API

    public String getName() {
        return name;
    }

    // template methods

    @Override
    protected String getElementId() {
        return "post-author";
    }

    @Override
    protected String getBaseUri() {
        return BaeldungArticleMapping.mapToUrl(name);
    }

}
