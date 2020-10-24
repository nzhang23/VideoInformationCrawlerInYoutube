1.Description

This tool only supports crawling data in searching webpages of Youtube. Using Selenium + Chrome to emulate the manual searching operations in Youtube such as automatically scrolling for scraping the searching results. Selenium is a web driver tool to automate the testing of a web application. It supports Chrome, Firefox and so on. Here are the processing steps of this tool. Firstly, using Selenium + Chrome to emulate the searching operations including running search url, automatically scrolling to update the searching results webpage and getting the html page source when there is no searching results to update. Secondly, using Jsoup to parse the page source to get the videos' information including title, upload time, uploader, and the number of views. Jsoup is a java html parser. Thirdly, using opencsv to write these videos' information data into a csv file.

2.Running Environment Setup

1) Need Chrome Browser installed.
 The Chrome Browser needs to be installed in the Linux environment. 

2) Download the chromedriver
Before downloading the chromedriver, you need to check the Chrome Browser version. 
The command line:
$ google-chrome --version

Then based on the google browser version, downloading the related chromedriver version in https://chromedriver.chromium.org/downloads
The command lines:
$ wget https://chromedriver.storage.googleapis.com/85.0.4183.87/chromedriver_linux64.zip
$ unzip chromedriver_linux64.zip

My chrome browser version is version 85, so the chromedriver version is 85.

3) Build the gradle project of this tool and run it
(1) install gradle (please note that sudo apt install gradle will install an older version of gradle that's not going to work for purposes of this tutorial)
The command lines:
$ sudo mkdir /opt/gradle
$ cd ~/Downloads
$ wget https://services.gradle.org/distributions/gradle-6.3-bin.zip
$ sudo unzip -d /opt/gradle gradle-6.3-bin.zip
$ sudo echo "export GRADLE_HOME=/opt/gradle/gradle-6.3" >> /etc/profile.d/gradle.sh
$ sudo echo "export PATH=\${GRADLE_HOME}/bin:\${PATH}" >> /etc/profile.d/gradle.sh
$ source /etc/profile.d/gradle.sh
$ gradle --version

(2) Download the gradle project of this tool from GitHub link: https://github.com/nzhang23/MovieSearchingToolInYoutube.git

(3) Build the gradle project
The command lines:
$ cd <this gradle project named MovieSearchingToolInYoutube path>
$ gradle wrapper
$ ./gradlew build

(4) Run this tool
In ./build/distributions, there are the zip and tar files of this tool's executive file. Need unzip one of them, it exists in ./build/distributions/MovieSearchingToolInYoutube/bin. Finally, run it. You need to input your chromedriver path and the search key words in it.
The command lines: 
$ cd <this gradle project path>
$ cd ./build/distributions
$ unzip MovieSearchingToolInYoutube.zip
$ cd MovieSearchingToolInYoutube/bin
$ ./MovieSearchingToolInYoutube




