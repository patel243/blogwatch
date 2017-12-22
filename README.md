Blog Watch
==========

This project contains UI tests for baeldung.com



Running Tests from the IDE - Setup
==================================

To run tests from the IDE, we need to set following three properties: 
  - _spring.profiles.active_ - environment variable to either "headless-browser" and "ui-browser"
  - _target.env_ - environment variable should be set to "win" or "linux" for headless browser
  - _base.url_ - to target base URL, for example _http://www.baeldung.com_

These can be set as environment variables via the Eclipse run configuration. 
Set environment variable "LAUNCH_FLAG" to either true or false to set launch mode. 

Headless browser is configured with phantomJS. 
Download phantomjs.exe from http://phantomjs.org/download.html and copy it in the bin/win or bin/linux directory (if binary is not availabe in repo)

UI browser has been configured with Firefox and tested with Firefox 56.0 (64 bit) on Windows. 
 
Javascript message tests only work in headless browser as geckodriver is an implementation of W3C WebDriver which doesn't specify a log interface at the moment, so this is expected behaviour. 



Maven 
==============

Three Maven profiles are available for running tests: 
  - _headless-browser-windows_
  - _headless-browser-linux_ 
  - _ui-brower-windows_



Update Lists of Posts and Pages
===============================

_BlogLinksExtractor_ updates articles and pages URLs
To use it, set environment variable named _blog-url-list_

It's value should be absolute path to resources/blog-url-list directory. 
