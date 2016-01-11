# Running Selenium with Fitnesse (Beta release)

## Overview

Selenium with FitNesse provides an "easy" way to create user interface driven automated tests with Appian. Test cases can be used for functional testing and regression testing of the user interface and the process model functional logic.

The presentation [Overview of Automate Testing with Appian](https://docs.google.com/presentation/d/1z17TOZcrWjDmkhpUfGmymDZJ-Kh8gKzggylEzJ9CDZw/edit#slide=id.gcab12166d_0_18) contains more information about the tool as well as a demo video.

## Usage and Contributions

To use Selenium and Fitnesse on your project, please follow the steps identified below for setting up the testing framework and creating/executing test cases.

This is still a Beta release. We ask that whenever possible, you contribute back to the repository and the Appian PS community by 
* adding any missing commands that you need to create for test cases
* fixing issues and defects
* implementing enhancements

As we work and contribute to make this tool better, we will take the greatest care to ensure that the next versions are backward compatible. Rest assure that whatever changes are released in the future are NOT going to break your test cases.

If your team cannot directly enhance the framework, please make sure to contact the Appian CoE on Home or over email in order to provide your feedbacks. (We can help only if we know where the problems are.)

To update the repository, please create a branch from `master`, implement/push your code and create a pull request. Tag the Appian CoE or send us a notification and we will review/merge your work.

## Prerequisities

* [Firefox](https://www.mozilla.org/en-US/firefox/new/)
* Java


## Installation

1. Download the newest release of [AutomatedTesting.zip](https://github.com/appianps/ps-ext-AutomatedTestFramework/releases).
1. Unzip contents of AutomatedTesting.zip into *TESTING_HOME*, e.g. ```C:\AutomatedTesting```
1. In a command prompt navigate to *TESTING_HOME*.
1. Run ```start.bat``` to install FitNesse.
 * This will also start FitNesse, to stop type ctrl+C.

**NOTE:** if you choose a different location for *TESTING_HOME* than ```C:\AutomatedTesting```, you must update the classpath variable in the examples to refer to the correct location.

## Running Your First FitNesse Test

1. Start FitNesse if it isn't already running:
 1. In a command prompt navigate to *TESTING_HOME*.
 1. Run ```start.bat```.
1. Navigate to ```http://localhost:8980/LoginTest```.
1. Click **Test**.

## Creating a wiki

1. Navigate to ```http://localhost:8980/WIKI_NAME```.
 * If the WIKI_NAME contains 'suite', then it will be considered a FitNesse suite.
 * If the WIKI_NAME contains 'test', then it will be considered a FitNesse test.
 * If the WIKI_NAME is 'SetUp', then it will be run before each test case.
 * If the WIKI_NAME is 'TearDown', then it will be run after each test case.
 * If the WIKI_NAME is 'SuiteSetUp', then it will be run once at the beginning of the test suite.
 * If the WIKI_NAME is 'SuiteTearDown', then it will be run once at the end of the test suite.
1. You can also nest wikis by navigating to subpages like ```http://localhost:8980/WIKI_SUITE_NAME/WIKI_TEST_NAME```.
 * You can see an example of this by copying ```etc\wikis\CaseManagementSuit``` folder from github repo into ```TESTING_HOME\FitNesseRoot``` and navigating to ```http://localhost:8980/CaseManagementSuite```.
 * This example has one each of the follow page types: Suite, Setup, TearDown, and Test.
 
## Setting up Jenkins with a FitNesse test

1. Download [Jenkins installer](https://jenkins-ci.org/).
1. Install Jenkins.
 1. Update ```JENKINS_HOME\jenkins.xml``` ```--httpPort``` argument to another port, e.g. 8081.
 1. Restart Jenkins service in Window services.
1. In a browser, navigate to ```http://localhost:8081```.
1. Click on **Manage Jenkins**.
1. Click on **Manage Plugins**.
1. Click on **Available** tab.
1. Filter by FitNesse check its box and click **Download now and install after restart**.
1. Navigate back to the home page and click **New Item**.
1. Choose a build name, select **Freestyle project**, and click **OK**.
1. Click **Add build step** and select **Execute FitNesse test**.
1. Enter the following values:
 1. Select **Start new FitNesse instance as part of build**.
 1. Enter ```TESTING_HOME``` in **Java working directory**.
 1. Enter ```TESTING_HOME\fitnesse-standalone.jar``` in **Path to fitnesse.jar**.
 1. Enter ```TESTING_HOME\FitNesseRoot``` in **Path to FitNesseRoot**.
 1. Enter ```8980``` in **Port for FitNesse instance**.
 1. Enter the page for the test suite in **Target Page**.
 1. Check **Is target a suite?** if the page is a suite.
 1. Set **HTTP Timeout** and **Test Timeout** high enough that the tests will not timeout.
 1. Enter ```TESTING_HOME\results\fitnesse-results.xml``` in **Path to fitnesse xml results file**.
1. Click **Add post-build action** and select **Publish Fitnesse results report**.
1. Click **Save**.
1. You can now run your FitNesse test by clicking **Build Now**.
 
## Creating a new method
All methods that are callable in FitNesse are derived from the appian-fixture.jar found in *C:\AutomatedTesting\lib\appian*. To create or modify an existing method, you must do the following:

1. Download the github repository for ps-ext-AutomatedTestFramework.
1. Modify class **com.appiancorp.ps.automatedtest.fixture.[BaseFixture|TempoFixture|PortalFixture]** to add new methods for the corresponding environment.
 1. Most methods written for these classes will simply call methods of an object in the **com.appiancorp.ps.automatest.object** package, e.g. *verifyNewsFeedContainingTextIsNotPresent(newsText)* calls *TempoNews.waitFor(newsText)*.
 1. Make sure to annotate the class for javadocs generation.
1. Add and test corresponding junit test cases in *\src\test\java\com\appiancorp\ps\automatedtest\fixture*.
1. Run **Project > Generate Javadoc...** and select the **com.appiancorp.ps.automatedtest.fixture** package (we don't need javadocs for the objects).
1. Run Maven **package** build to generate a new jar in the /target/ directory.
1. Copy new jar to *C:\AutomatedTesting\lib\appian\\* and delete the old.
1. To push back to the remote repository:
	1. Create new branch for changes: ```git branch BRANCH_NAME``` then ```git checkout BRANCH_NAME```.
	1. Publish new branch: ```git push```.
	1. Create pull request back to master, create this on github.com.

## Commands
For additional information, view the javadocs: /doc/index.html

### UTILITIES
* | setup selenium web driver with | *BROWSER_NAME* | browser |
* | set data source name to | *DATA_SOURCE_NAME* |
* | set appian url to | *APPIAN_URL* |
* | set start datetime | - will be used as relative datetime for date/time fields
* | set timeout seconds to | *TIMEOUT_SECONDS* | - Used as the wait timeout in between each command
* | set screenshot path to | *SCREENSHOT_PATH* | - Set the folder where screenshots will be saved. This will create new folders if necessary. Terminate path with "/" to ensure folder creation. (Default C:\AutomatedTesting\screenshots\)
* | set take error screenshots to | *SCREENSHOT_BOOLEAN* | - If true, screenshots will be taken for every failed test case. (Default true)
* | take screenshot | *FILE_NAME* | - take a screenshot with the name (do not include the extension)
* | login with username | *USERNAME* | with password | *PASSWORD* | - uses set appian url
* | login with terms with username | *USERNAME* | with password | *PASSWORD* | - uses set appian url
* | wait for | *RELATIVE_PERIOD*, e.g. +1 days, +1 hours | - waits for relative amount of time
* | wait until | *RELATIVE_PERIOD* | - waits until relative time
* | set | *VARIABLE_NAME* | get random string | *STRING_LENGTH* | - use @{VARIABLE_NAME} to parameterize test cases
* | refresh | - refreshes screen
* | logout |

### NAVIGATION
* | click on menu | *TEMPO_MENU_NAME* |

### NEWS
* | verify news feed containing text | *NEWS_TEXT* | is present |
* | verify news feed containing text | *NEWS_TEXT* | is not present |
* | toggle more info for news feed containing text | *NEWS_TEXT* |
* | verify news feed containing text | *NEWS_TEXT* | and more info with label | *LABEL* | and value | *VALUE* | is present |
* | verify news feed containing text | *NEWS_TEXT* | tagged with | *TAG_NAME* | is present |
* | verify news feed containing text | *NEWS_TEXT* | commented with | *COMMENT* | is present |
* | set | *VARIABLE_NAME* | get regex | *REGEX* | from news feed containing text | *NEWS_TEXT* | - use Java Regular Expression to return data from news as a variable
* | set | *VARIABLE_NAME* | get regex | *REGEX* | from news feed containing text | *NEWS_TEXT* | commented with | *COMMENT* |

### TASKS
* | click on task | *TASK_NAME* |
* | verify task | *TASK_NAME* | is present |
* | verify task | *TASK_NAME* | is not present |
* | verify task | *TASK_NAME* | has a deadline of | *DEADLINE_TEXT* |

### RECORDS
* | click on record list | *RECORD_LIST_NAME* |
* | click on record list facet option | *FACET_NAME* |
* | verify record list facet option | *FACET_NAME* | is present |
* | click on record item | *RECORD_ITEM_NAME* |
* | verify record item | *RECORD_ITEM_NAME* | is present |
* | verify record item | *RECORD_ITEM_NAME* | is not present |
* | click on record item facet | *FACET_NAME* |
* | click on record item related action | *RELATED_ACTION_NAME* | - this is run on the related action dashboard, not as a button currently
* | verify record item related action | *RELATED_ACTION_NAME* | is present |
* | verify record item related action | *RELATED_ACTION_NAME* | is not present |

### REPORTS
* | click on report | *REPORT_NAME* |
* | verify report | *REPORT_NAME* | is present |
* | verify report | *REPORT_NAME* | is not present |

### ACTIONS
* | click on action | *ACTION_NAME* |
* | verify action completed |

### INTERFACES
* | populate field | *FIELD_LABEL_OR_INDEX* | with | *VALUE(S)* |
* | populate field | *FIELD_LABEL[FIELD_INDEX]* | with | *VALUE(S)* | - use this to populate the 2nd, 3rd, etc field with the same label
* | popultate field | *FIELD_LABEL | with value | VALUE | - use this to populate a field with a value that contains a comma
* | populate field | [*FIELD_INDEX*] | in section | *SECTION_NAME* | with | *VALUE(S)* | - populate a field in a section with no label
* | clear field | *FIELD_LABEL* | of | *VALUE_TO_REMOVE* | - this is for removing specific value from a picker
* | get form title | - use with FitNesse keyword **check** to verify title, e.g. | check | get form title | *Expected Title* |
* | get form instructions | - use with FitNesse keyword **check** to verify title, e.g. | check | get form title | *Expected Instructions* |
* | get field | *FIELD_LABEL_OR_INDEX* | value | - use with FitNesse key words **check** or **set** to verify values or populate runtime variables
* | get field | *FIELD_LABEL_OR_INDEX* | in section | *SECTION_NAME* | value | - use with FitNesse key words **check** or **set** to verify values or populate runtime variables
* | verify field | *FIELD_LABEL_OR_INDEX* | contains | *VALUE* |
* | verify field | *FIELD_LABEL[FIELD_INDEX]* | contains | *VALUE* |
* | verify field | *FIELD_LABEL* | contains value | *VALUE* | - use this to verify a field that contains a comma
* | verify field | *FIELD_LABEL_OR_INDEX* | in section | *SECTION_NAME* | contains | *VALUE* |
* | verify field | *FIELD_LABEL_OR_INDEX* | is present |
* | verify field | *FIELD_LABEL_OR_INDEX* | is not present |
* | populate grid | *GRID_NAME* | column | *COLUMN_NAME_OR_NUMBER* | row | *ROW_NUMBER* | with | *VALUE(S)* |
* | verify grid | *GRID_NAME* | column | *COLUMN_NAME_OR_NUMBER* | row | *ROW_NUMBER* | contains | *VALUE(S)* |
* | select grid | *GRID_NAME* | row | *ROW_NUMBER* |
* | click on link | *LINK_NAME* |
* | click on button | *BUTTON_NAME* |
* | click on radio option | *RADIO_OPTION* |
* | click on checkbox option | *CHECKBOX_OPTION* |