package com.baeldung.site;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TeamOptInPageDriver extends BlogBaseDriver {

    @Override
    @Value("${team.optin.url}")
    protected void setUrl(String url) {
        this.url = this.getBaseURL() + url;
    }

    public void clickOnGetAccessLinkforSmallTeam() {
       // WebDriverWait wait = new WebDriverWait(this.getWebDriver(), 20);
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'tl-states-root')]")));
        this.getWebDriver().findElement(By.xpath("//div[contains(@class,'buy_team_small')]/a")).click();
    }

    public void closePopupOnRwSTeamOptInPage() {
        for (WebElement element : this.getWebDriver().findElements(By.xpath("//div[contains(@class, 'tve_ea_thrive_leads_form_close')]"))) {
            if (element.isDisplayed()) {
                element.click();
                break;
            }
        }
        WebDriverWait wait = new WebDriverWait(this.getWebDriver(), 20);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class, 'tl-lb-target')]")));
    }

    public boolean theSubmitButtonOnthePopupisDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(this.getWebDriver(), 10);
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(., 'WE MIGHT BE INTERESTED')]")));
            return button.isDisplayed();
        } catch (TimeoutException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public void clickOnGetAccessLinkforMediumTeam() {
        this.getWebDriver().findElement(By.xpath("//div[contains(@class,'buy_team_medium')]/a")).click();
    }

    public void clickOnGetAccessLinkforLargeTeam() {
        this.getWebDriver().findElement(By.xpath("//div[contains(@class,'buy_team_large')]/a")).click();
    }

}
