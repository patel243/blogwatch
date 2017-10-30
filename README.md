blogwatch
=========

- two Maven profiles are available, "headless-browse" and "ui-brower" for running test using Maven.
- we can also set "spring.profiles.active" environment variable to either "headless-browse" and "ui-brower" for running JUnits test from IDE.
- headless brower is configured with phantomJS. Please download phantomjs.exe from  http://phantomjs.org/download.html ad copy it in the bin directly.
-ui browser has been configured with Firefox and tested with Firefox version 56.0 (64 bit) on windows 
-javascript message tests only work in headless browser as geckodriver is an implementation of W3C WebDriver which doesn’t specify a log interface at the moment, so this is expected behaviour.
	