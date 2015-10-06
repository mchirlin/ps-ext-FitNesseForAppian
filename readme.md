# Running Selenium with Fitnesse

## Installation

1. Download [FitNesse jar](http://www.fitnesse.org/fitnesse.jar?responder=releaseDownload&release=20111026)
 1. Place in C:\fitnesse\
1. Download [Selenium Java Web Driver[(http://selenium-release.storage.googleapis.com/2.47/selenium-java-2.47.1.zip)
 1. Place in C:\selenium-2.47.1\
1. Download [FitLibrary](http://sourceforge.net/projects/fitlibrary/files/latest/download)
 1. Copy FitLibrary fitnesse folder on top of C:\fitnesse folder
1. Download [Appian servlet plug-in](https://github.com/appianps/ps-ext-AutomatedTestFramework/blob/master/plugins/AutomatedTestServlet/AppianAutomatedTestServlet.1.0.0.jar) and place in APPIAN_HOME\_admin\plugins
1. Download [Appian fixtures](https://github.com/appianps/ps-ext-AutomatedTestFramework/blob/master/appian-fixtures.jar) and place in C:\fitnesse\lib\appian

## Running Fitnesse

1. Make sure Appian is currently running
1. In command prompt navigate to C:\fitnesse
1. Run runFitNesseDirectly.bat
1. Navigate to localhost:8980/<WikiName> to run a test

## Creating a wiki
1. Navigate to localhost:8980/<WikiName>
 * If the <WikiName> contains 'suite', then it will be considered a FitNesse suite
 * If the <WikiName> contains 'test', then it will be considered a FitNesse test
 * If the <WikiName> is 'SetUp', then it will be run before each test case
 * If the <WikiName> is 'TearDown', then it will be run after each test case
 * If the <WikiName> is 'SuiteSetUp', then it will be run once at the beginning of the test suite
 * If the <WikiName> is 'SuiteTearDown', then it will be run once at the end of the test suite
1. There are some example wikis in the wikis folder for creating a Suite, SetUp, TearDown, and a few example tests
 * These were based off a specific application, but can be modified to work with any application
 * To run any tests they will have to be started with the correct path, see wikis/CredentialsSuite.txt for an example
 * To run any DoFixture tests, the first table in the test must be a DoFixture class, see wikis/CredentialsSuite.SetUp for an example