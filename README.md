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

### Headless Browser selection

Available headless browsers

- _PhantomJS_ (default)
- _HtmlUnit_

Headless browser can be configured using following system property

- _headless.browser.name_


### Updating List of Posts and Pages


_BlogLinksExtractor_ updates articles and pages URLs. To use it, set environment variable named _blog-url-list_. It's value should be absolute path to resources/blog-url-list directory. _BlogLinksExtractor_ should be run as Java application. 


### JUnit Tags

Following tags are available for running tests selectively. Refer Java docs in _GlobalConstants.java_ for details
  - _hourly_
  - _daily_
  - _weekly_
  - _bi-monthly_
  - _monthly_


### Launch Mode
 
Set environment variable "LAUNCH_FLAG" to either _true_ or _false_ to set launch mode. Default is _false_


### On Jenkins
 
 The tests are running here, [on Jenkins](https://rest-security.ci.cloudbees.com/job/site-monitor/job/site-watch/)
 
### Tests
 The tests which are crossed out are disabled.

| Sr. | Test Name | Tag/Frequency |
| --- | --------- | ------------- |
| 1 | ~~givenOnTheJavaWebWeeklyPage_whenSubscriptionPopupLoads_thenItContainsSubscriptionElements~~ | hourly |
| 2 | givenOnTheHomePage_whenPageLoads_thenJavaWeeklyLinksMatchWithTheLinkText | daily |
| 3 | givenOnTheHomePage_whenPageLoads_thenItContainsCategoriesInTheFooterMenu | daily |
| 4 | givenOnTheHomePageUrlWithoutWWWPrefix_whenUrlIsHit_thenItRedirectsToWWW | daily |
| 5 | givenOnTheHomePage_whenHomePageLoaded_thenNoSevereMessagesInBrowserLog | daily |
| 6 | givenOnTheMicroservicesGuidePage_whenOptinPopupIsLoaded_thenItContainsImages | daily |
| 7 | givenArticleWithPopup_whenPopupOpens_thenPopupHasCloseButton | daily |
| 8 | givenTheArticleWithSeries_whenArticleLoads_thenPluginLoadsProperly | daily |
| 9 | givenTheArticleWithPersistenceEBookDownload_whenPageLoads_thenFooterImageIsDisplayed | daily |
| 10 | givenTheArticleWithGoogleAnalytics_whenArticleLoads_thenArticleHasAnalyticsCode | daily |
| 11 | givenThePageWithGoogleAnalytics_whenPageLoads_thenPageHasAnalyticsCode | daily |
| 12 | givenBaeldungFeedUrl_whenUrlIsHit_thenItRedirectsToFeedburner | daily |
| 13 | givenTheCategoryPage_whenPageLoads_thenItContainsNoindexRobotsMeta | daily |
| 14 | givenThePagesWithBlankTitle_whenPageLoads_thenItDoesNotContainNotitleText | daily |
| 15 | givenTheTagArticle_whenArticleLoads_thenItContainsNoindexRobotsMeta | daily
| 16 | givenOnTheHomePage_whenPageLoads_thenItHasOneAboutMenuInTheFooter | daily
| 17 | givenOnTheCoursePage_whenPageLoads_thenTrackingIsSetupCorrectly | daily |
| 18 | givenTheArticleWithFixedWidget_whenArticleLoads_thenStopIDIsConfiguredCorrectly | daily |
| 19 | givenThePageWithFixedWidget_whenPageLoads_thenStopIDIsConfiguredCorrectly | daily |
| 20 | givenOnTheHomePage_whenTheSurveyStarts_thenTheSelectValueIsPostedToTheDrip | weekly |
| 21 | ~~givenAllArticlesURLs_whenArticleLoads_thenItReturns200OK~~ | bi-monthly |
| 22 | givenAllPagesURLs_whenPageLoads_thenItReturns200OK | bi-monthly |
| 23 | givenAllArticleList_whenArticleLoads_thenItHasSingleShortcodeAtTheTop|monthly |
| 24 | givenAllArticleList_whenArticleLoads_thenItHasSingleShortcodeAtTheEnd | monthly |
| 25 | givenAllTheArticles_whenArticleLoads_thenArticleHasNoEmptyDiv | monthly |
| 26 | givenAllTheArticles_whenArticleLoads_thenImagesPointToCorrectEnv | monthly |
| 27 | givenAllThePages_whenPageLoads_thenImagesPointToCorrectEnv | monthly |
| 28 | givenAllArticles_whenTheArticleLoads_thenTheMetaDescriptionExists | monthly |
| 29 | givenAllPages_whenPageLods_thenTheMetaDescriptionExists | monthly |
| 30 | givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle | monthly |












