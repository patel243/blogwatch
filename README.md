# Blog Watch

This project contains UI tests for baeldung.com


### Overview

The project uses Selenium framework. The tests can be run in GUI or headless mode. UI browser has been configured with Firefox using gecodriver and tested with Firefox 56.0 (64 bit) on Windows. Headless mode works with PhantomJS and HtmlUnit browsers. 


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


_BlogLinksExtractor_ updates articles and pages URLs. _BlogLinksExtractor_ should be run as Java application. 


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

| Sr. | Test Name | Tag/Frequency | short description |
| --- | --------- | ------------- | ----------------- |
| 1 | ~~givenOnTheJavaWebWeeklyPage_whenSubscriptionPopupLoads_thenItContainsSubscriptionElements~~ | hourly | |
| 2 | givenOnTheRwSTeamOptInPage_whenTheGetAccessButtonIsClicked_thenTheOptInsPopupsWorkFine | every 30 minutes | This test verifies optin popups in the following article - http://www.baeldung.com/rest-with-spring-for-teams-opt-in |
| 3 | givenOnTheHomePage_whenPageLoads_thenJavaWeeklyLinksMatchWithTheLinkText | daily | Verifies that the Java weekly issue links in the homepage point to correct URLs |
| 4 | givenOnTheHomePage_whenPageLoads_thenItContainsCategoriesInTheFooterMenu | daily | Verifies that the CATEGORY menu is available in the homepage footer |
| 5 | givenOnTheHomePageUrlWithoutWWWPrefix_whenUrlIsHit_thenItRedirectsToWWW | daily | If we hit http://baeldung.com, it should redirect to http://www.baeldung.com |
| 6 | givenOnTheHomePage_whenHomePageLoaded_thenNoSevereMessagesInBrowserLog | daily | This test looks for JS error in the browser console falling in server category |
| 7 | givenOnTheMicroservicesGuidePage_whenOptinPopupIsLoaded_thenItContainsImages | daily | This test verifies that the images are displayed properly on _Download Guide_ popup in following URL - http://www.baeldung.com/spring-microservices-guide |
| 8 | givenArticleWithPopup_whenPopupOpens_thenPopupHasCloseButton | daily | This test checks that the pop-up, the one which auto lods,  has close button on the following page - http://www.baeldung.com/rest-with-spring-series/ |
| 9 | givenTheArticleWithSeries_whenArticleLoads_thenPluginLoadsProperly | daily | Verifies the series plugin on the following page - http://www.baeldung.com/spring-security-registration |
| 10 | givenTheArticleWithPersistenceEBookDownload_whenPageLoads_thenFooterImageIsDisplayed | daily | This test checks that the image is displayed properly in the EBook download widget available at the end of following page - http://www.baeldung.com/hibernate-spatial |
| 11 | givenTheArticleWithGoogleAnalytics_whenArticleLoads_thenArticleHasAnalyticsCode | daily | This test looks for the Google Analytics code in the following post - http://www.baeldung.com/persistence-layer-with-spring-and-hibernate |
| 12 | givenThePageWithGoogleAnalytics_whenPageLoads_thenPageHasAnalyticsCode | daily | This test looks for the Google Analytics code in the following page - http://www.baeldung.com/rest-with-spring-course |
| 13 | givenBaeldungFeedUrl_whenUrlIsHit_thenItRedirectsToFeedburner | daily | Baeldung feed URL - http://www.baeldung.com/feed/- should redirect to http://feeds.feedburner.com/Baeldung |
| 14 | givenTheCategoryPage_whenPageLoads_thenItContainsNoindexRobotsMeta | daily | For testing that the category pages should have a _robots_ meta tag with _noindex_ |
| 15 | givenThePagesWithBlankTitle_whenPageLoads_thenItDoesNotContainNotitleText | daily | This test verifies that the pages which have no title set do not show invalid title - _No Title_. The test runs against a list of following five pages.
http://www.baeldung.com/java-weekly-sponsorship/ , http://www.baeldung.com/webinar-rest-with-spring-thank-you , http://www.baeldung.com/webinar-api-security-thank-you , http://www.baeldung.com/webinar-api-discoverability-thank-you , http://www.baeldung.com/webinar-cqrs-thank-you |
| 16 | givenTheTagArticle_whenArticleLoads_thenItContainsNoindexRobotsMeta | daily |
| 17 | givenOnTheHomePage_whenPageLoads_thenItHasOneAboutMenuInTheFooter | daily |
| 18 | givenOnTheCoursePage_whenPageLoads_thenTrackingIsSetupCorrectly | daily | |
| 19 | givenTheArticleWithFixedWidget_whenArticleLoads_thenStopIDIsConfiguredCorrectly | daily | |
| 20 | givenThePageWithFixedWidget_whenPageLoads_thenStopIDIsConfiguredCorrectly | daily | |
| 21 | ~~givenOnTheHomePage_whenTheSurveyStarts_thenTheSelectValueIsPostedToTheDrip~~ | weekly | |
| 22 | givenAllArticlesURLs_whenArticleLoads_thenItReturns200OK | weekly | |
| 23 | givenAllPagesURLs_whenPageLoads_thenItReturns200OK | weekly | |
| 24 | givenAllArticleList_whenArticleLoads_thenItHasSingleShortcodeAtTheTop|twice-a-month | |
| 25 | givenAllArticleList_whenArticleLoads_thenItHasSingleShortcodeAtTheEnd | twice-a-month | |
| 26 | givenAllTheArticles_whenArticleLoads_thenArticleHasNoEmptyDiv | twice-a-month | |
| 27 | givenAllTheArticles_whenArticleLoads_thenImagesPointToCorrectEnv | twice-a-month | |
| 28 | givenAllThePages_whenPageLoads_thenImagesPointToCorrectEnv | twice-a-month | |
| 29 | givenAllArticles_whenTheArticleLoads_thenTheMetaDescriptionExists | twice-a-month | |
| 30 | givenAllPages_whenPageLods_thenTheMetaDescriptionExists | twice-a-month | |
| 31 | givenArticlesWithALinkToTheGitHubModule_whenTheArticleLoads_thenTheGitHubModuleLinksBackToTheArticle | twice-a-month | |
| 32 | givenAllTheArticles_whenAnArticleLoads_thenTheAuthorIsNotFromTheExcludedList | twice-a-month | |












