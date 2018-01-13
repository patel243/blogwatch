# Blog Watch

This project contains UI tests for baeldung.com


### Overview

The project uses Selenium framework. The tests can be run in GUI or headless mode. UI browser has been configured with Firefox using gecodriver and tested with Firefox 56.0 (64 bit) on Windows. Headless mode works with PhantomJS. 


### Running Tests from the IDE

The default configuration executes tests with headless mode in windows environment and target URL is http://www.baeldung.com. The configuration can be changed using following properties:

  - _spring.profiles.active_ - environment variable to either "headless-browser" and "ui-browser"
  - _target.env_ - environment variable should be set to "win" or "linux" for headless browser
  - _base.url_ - to target base URL, for example _http://www.baeldung.com_

These can be set as environment variables via the Eclipse run configuration. 


### Running Tests Using Maven 

Three Maven profiles are available for running tests: 
  - _headless-browser-windows_
  - _headless-browser-linux_ 
  - _ui-brower-windows_

The target URL for all profiles is http://baeldung.com. The can be changed using following property

- _base.url_ - to target base URL, for example _http://www.baeldung.com_


### Update Lists of Posts and Pages


_BlogLinksExtractor_ updates articles and pages URLs. To use it, set environment variable named _blog-url-list_. It's value should be absolute path to resources/blog-url-list directory. _BlogLinksExtractor_ should be run as Java application. 


### JUnit Tags

Following tags are available for running tests selectively. Refer Java docs in _GlobalConstants.java_ for details
  - _singleURL_
  - _sampleArticles_
  - _samplePages_
  - _sampleArticles_
  - _samplePages_


### Launch Mode
 
Set environment variable "LAUNCH_FLAG" to either _true_ or _false_ to set launch mode. Default is _false_


### On Jenkins
 
 The tests are running here, [on Jenkins](https://rest-security.ci.cloudbees.com/job/site-monitor/job/site-watch/)
 
### Tests

| Test Name | Tag/Frequency |
| --------- | ------------- |
| givenJaveWeeklyLinkClickedOnHomePage_whenSubscriptionPopupLoads_thenItContainsEmailAndSubscribeElements | daily |
| givenOnHomePage_whenPageLoads_thenJavaWeeklyLinksMatchWithLinkText | daily |
| givenOnHomePage_whenPageLods_thenItContainsCategoriesInFooterMenu | daily |
| givenHomePageUrlWithoutWWWPrefix_whenUrlIsHit_thenItRedirectsToWWW | daily |
| givenOnHomePage_whenHomePageLoaded_thenNoSevereMessagesInBrowserLog | daily |
| givenOnTheMicroservicesGuidePage_whenOptinPopupIsLoaded_thenItContainsImages | daily |
| givenArticleWithPopup_whenPopupOpens_thenPopupHasCloseButton | daily |
| givenTheArticleWithSeries_whenArticleLoads_thenPluginLoadsProperly | daily |
| givenTheArticleWithPersistenceEBookDownload_whenPageLoads_thenFooterImageIsDisplayed | daily |
| givenTheArticleWithGoogleAnalytics_whenArticleLoads_thenArticleHasAnalyticsCode | daily |
| givenThePageWithGoogleAnalytics_whenPageLoads_thenPageHasAnalyticsCode | daily |
| givenBaeldungFeedUrl_whenUrlIsHit_thenItRedirectsToFeedburner | daily |
| givenTheCategoryPage_whenPageLoads_thenItContainsNoindexRobotsMeta | daily |
| givenThePagesWithBlankTitle_whenPageLoads_thenItDoesNotContainNotitleText | daily |
| givenTheSampleArticleList_whenArticleLoads_thenContentDivExists|weekly |
| givenAllArticlesURLs_whenArticleLoads_thenItDoesNotThrow404 | bi-monthly |
| givenAllPagesURLs_whenPageLoads_thenItDoesNotThrow404 | bi-monthly |
| givenAllTheArticles_whenArticleLods_thenArticleHasNoEmptyDiv | monthly |












