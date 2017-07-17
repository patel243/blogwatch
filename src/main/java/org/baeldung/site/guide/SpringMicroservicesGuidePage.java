package org.baeldung.site.guide;

import org.baeldung.site.base.BlogBaseDriver;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringMicroservicesGuidePage extends BlogBaseDriver {
    
    @Value("${site.guide.spring.microservices}")
    private String SpringMicroservicesGuideURL;
    
    public void clickAccessTheGuideButton() {
        this.getWebDriver().findElement(By.xpath("//div[@id='tve_editor']/div[2]/div/div[3]/div/div/div/div[2]/div[2]/div/a")).click();
    }
    
    public String findFirstImagePath(){
        return this.getWebDriver().findElement(By.xpath("/html/body/div[4]/div/div/div/div[2]/div/article/div/div/div/div/div/div[1]/div[1]/div/span/img")).getAttribute("src");
    }
    
    public String find2ndImagePath(){
        return this.getWebDriver().findElement(By.xpath("/html/body/div[4]/div/div/div/div[2]/div/article/div/div/div/div/div/div[1]/div[2]/div/span/img")).getAttribute("src");
    }

    @Override
    protected String getPageURL() {
        // TODO Auto-generated method stub
        return this.SpringMicroservicesGuideURL;
    }

}
