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

## Installation

1. Download [AutomatedTesting.zip](https://github.com/appianps/ps-ext-AutomatedTestFramework/releases/download/v0.1-beta.1/AutomatedTesting.zip).
1. Unzip contents of AutomatedTesting.zip into TESTING_HOME, e.g. ```C:\AutomatedTesting```
1. In a command prompt navigate to ```TESTING_HOME```.
1. Run ```start.bat``` to install FitNesse.
 * This will also start FitNesse, to stop type ctrl+C.

**NOTE:** if you choose a different location for TESTING_HOME than ```C:\AutomatedTesting``` then you must update the classpath variables in the examples to refer to the correct locations.

## Running Your First FitNesse Test

1. Start FitNesse if it isn't already running:
 1. In command prompt navigate to ```TESTING_HOME```.
 1. Run start.bat.
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
 1. Enter ```TESTING_HOME\fitnesse.jar``` in **Path to fitnesse.jar**.
 1. Enter ```TESTING_HOME\FitNesseRoot``` in **Path to FitNesseRoot**.
 1. Enter ```8980``` in **Port for FitNesse instance**.
 1. Enter the page for the test suite in **Target Page**.
 1. Check **Is target a suite?** if the page is a suite.
 1. Set **HTTP Timeout** and **Test Timeout** high enough that the tests will not timeout.
 1. Enter ```TESTING_HOME\results\fitnesse-results.xml``` in **Path to fitnesse xml results file**.
1. Click **Add post-build action** and select **Publish Fitnesse results report**.
1. Click **Save**.
1. You can now run your FitNesse test by clicking **Build Now**.
 
## Commands

### UTILITIES
* | setup selenium web driver with | *BROWSER_NAME* | browser |
* | set data source name to | *DATA_SOURCE_NAME* |
* | set appian url to | *APPIAN_URL* |
* | set start datetime | - will be used as relative datetime for date/time fields
* | take screenshot | *FILE_PATH* |
* | login with username | *USERNAME* | with password | *PASSWORD* | - uses set appian url
* | wait for | *RELATIVE_PERIOD*, e.g. +1 days, +1 hours | - waits for relative amount of time
* | wait until | *RELATIVE_PERIOD* | - waits until relative time
* | refresh | - refreshes screen
* | logout from tempo |

### NAVIGATION
* | click on tempo menu | *TEMPO_MENU_NAME* |

### NEWS
* | verify news feed containing text | *NEWS_ITEM_TITLE* | is present |
* | verify news feed containing text | *NEWS_ITEM_TITLE* | is not present |
* | toggle more info for news feed containing text | *NEWS_ITEM_TITLE* |
* | verify news feed containing text | *NEWS_ITEM_TITLE* | and more info with label | *LABEL* | and value | *VALUE* | is present |
* | verify news feed containing text | *NEWS_ITEM_TITLE* | tagged with | *TAG_NAME* | is present |
* | verify news feed containing text | *NEWS_ITEM_TITLE* | commented with | *COMMENT* | is present |

### TASKS
* | click on tempo task | *TASK_NAME* |
* | verify tempo task | *TASK_NAME* | is present |
* | verify tempo task | *TASK_NAME* | is not present |
* | verify tempo task | *TASK_NAME* | has a deadline of | *DEADLINE_TEXT* |

### RECORDS
* | click on tempo record list | *RECORD_LIST_NAME* |
* | click on tempo record list facet option | *FACET_NAME* |
* | verify tempo record list facet option | *FACET_NAME* | is present |
* | click on tempo record item | *RECORD_ITEM_NAME* |
* | verify tempo record item | *RECORD_ITEM_NAME* | is present |
* | verify tempo record item | *RECORD_ITEM_NAME* | is not present |
* | click on tempo record item facet | *FACET_NAME* |
* | click on tempo record item related action | *RELATED_ACTION_NAME* | - this is run on the related action dashboard, not as a button currently
* | verify tempo record item related action | *RELATED_ACTION_NAME* | is present |
* | verify tempo record item related action | *RELATED_ACTION_NAME* | is not present |

### REPORTS
* | click on tempo report | *REPORT_NAME* |

### ACTIONS
* | click on tempo action | *ACTION_NAME* |
* | verify tempo action completed |

### FORMS
* | populate tempo field | *FIELD_LABEL* | with | *VALUE(S)* |
* | clear tempo field | *FIELD_LABEL* | of | *VALUE_TO_REMOVE* | - this is for removing specific value from a picker
* | verify tempo field | *FIELD_LABEL* | contains | *VALUE* |
* | populate tempo editable grid | *GRID_NAME* | column | *COLUMN_NAME_OR_NUMBER* | row | *ROW_NUMBER* | with | *VALUE(S)* |
* | verify editable grid | *GRID_NAME* | column | *COLUMN_NAME_OR_NUMBER* | row | *ROW_NUMBER* | contains | *VALUE(S)* |
* | click on tempo link | *LINK_NAME* |
* | click on tempo button | *BUTTON_NAME* |
