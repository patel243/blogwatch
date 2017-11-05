package org.baeldung.home;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.config.MainConfig;
import org.baeldung.site.base.SitePage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { MainConfig.class })
public class ContentOnPageUITest {
	
	@Autowired
    SitePage blogPage;
	
	@Test    
    public final void whenPageLoads_thenContentDivExists() {
		
		String baseURL = "http://www.baeldung.com";
		
		List<String> URLs = new ArrayList<String>();		
		URLs.add("/rest-with-spring-series/");
		URLs.add("/persistence-with-spring-series/");
		
		for (String URL: URLs) {
			blogPage.setPageURL(blogPage.getBaseURL()+ URL); 
			blogPage.openNewWindowAndLoadPage();
	        assertTrue(blogPage.findContentDiv().isDisplayed());                       	
	        blogPage.quiet();
		}
    }

}
