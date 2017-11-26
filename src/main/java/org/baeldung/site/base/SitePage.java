package org.baeldung.site.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class SitePage extends BlogBaseDriver {

    @Override
    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public WebElement findContentDiv() {
        return this.getWebDriver().findElement(By.xpath(".//section[1]/div[contains(@class, 'short_box short_start')][1]"));
    }
    
    public WebElement findPopupCloseButton() {
        return this.getWebDriver().findElement(By.xpath(".//*[@id='tve_editor']/div[1][contains(@class, 'tve_ea_thrive_leads_form_close')]"));
    }
}
