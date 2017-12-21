blogwatch
=========

- three Maven profiles are available, "headless-browser-windows", "headless-browser-linux" and "ui-brower-windows" for running tests using Maven.
- for running JUnits test from IDE, we need to set "spring.profiles.active" environment variable to either "headless-browser" and "ui-browser", "target.env" environment variable should be set to "win" or "linux" for headless browser, base.url to target base URL like http://baeldung.com.
- BaseURL can be switched by setting base.url property using mavn, for example -Dbase.url=http://www.baeldung.com. If base.url is not set, 
- headless browser is configured with phantomJS. Please download phantomjs.exe from  http://phantomjs.org/download.html ad copy it in the bin/win or bin/linux directory (if binary is not availabe in repo).
- ui browser has been configured with Firefox and tested with Firefox version 56.0 (64 bit) on windows 
- javascript message tests only work in headless browser as geckodriver is an implementation of W3C WebDriver which doesn’t specify a log interface at the moment, so this is expected behaviour.
- BlogLinksExtractor updates articles and pages URLs. To use it, set environment variable named blog-url-list. It's value should be absolute path to resources/blog-url-list directory.
