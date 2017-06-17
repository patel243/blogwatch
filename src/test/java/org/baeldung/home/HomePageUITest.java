package org.baeldung.home;

import static org.junit.Assert.assertTrue;

import org.baeldung.config.MainConfig;
import org.baeldung.site.home.HomePageDriver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MainConfig.class })
public final class HomePageUITest {

    @Autowired
    HomePageDriver homePageDriver;

    @Test
    public final void whenJavaWebWeeklySubscribePopup_thenEmailAndSubscribeElementsExist() {
        homePageDriver.getWebDriver().findElement(By.xpath("//div[@id='tve_editor']/div/div[2]/div/div/div/div[2]/div[2]/div[2]/div/div/a/span[2]")).click();
        homePageDriver.getWebDriver().findElement(By.xpath("//div[@id='tve_editor']/div/div/div/div/div/div[3]/div/a/span[2]")).click();

        assertTrue(homePageDriver.getWebDriver().findElement(By.xpath("//div[@id='tve_editor']/div/div/div[2]/form/div/div[2]/input")).isDisplayed()); // email field
        assertTrue(homePageDriver.getWebDriver().findElement(By.xpath("//div[@id='tve_editor']/div/div/div[2]/form/div/div[4]/button")).isDisplayed()); // subscribe button

        homePageDriver.quiet();
    }

}
