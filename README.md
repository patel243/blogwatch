Blog Watch
==========

- this project contains UI tests for baeldung.com



Running Tests from the IDE - Setup
==================================

- to run tests from the IDE, we need to set following three properties: 
  - spring.profiles.active - environment variable to either "headless-browser" and "ui-browser"
  - target.env - environment variable should be set to "win" or "linux" for headless browser
  - base.url - to target base URL, for example http://baeldung.com

- these can be set as environment variables via the Eclipse run configuration
- set environment variable "LAUNCH_FLAG" to either true or false to set launch mode

- headless browser is configured with phantomJS. Download phantomjs.exe from http://phantomjs.org/download.html and copy it in the bin/win or bin/linux directory (if binary is not availabe in repo)
- ui browser has been configured with Firefox and tested with Firefox version 56.0 (64 bit) on windows 
- javascript message tests only work in headless browser as geckodriver is an implementation of W3C WebDriver which doesn't specify a log interface at the moment, so this is expected behaviour



Maven 
==============
- three Maven profiles are available, "headless-browser-windows", "headless-browser-linux" and "ui-brower-windows" for running tests using Maven



Update Lists of Posts and Pages
===============================

- _BlogLinksExtractor_ updates articles and pages URLs
- to use it, set environment variable named blog-url-list
- it's value should be absolute path to resources/blog-url-list directory
