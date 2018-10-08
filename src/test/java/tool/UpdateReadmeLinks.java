package tool;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.config.context.MyApplicationContextInitializer;
import com.baeldung.crawler.GitModulesReadmeLinksExtractor;
import com.baeldung.crawler.config.CrawlerMainCofig;


@ContextConfiguration(classes = { CrawlerMainCofig.class }, initializers = MyApplicationContextInitializer.class)
@ExtendWith(SpringExtension.class)
public class UpdateReadmeLinks {
    
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    GitModulesReadmeLinksExtractor gitModulesReadmeLinksExtractor;
    
    @Test    
    public final void updateReadmeLins() throws IOException {
        gitModulesReadmeLinksExtractor.findAndUpdateLinksToReadmeFiles();
    }

}