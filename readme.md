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

1. Download [FitNesse jar](http://www.fitnesse.org)
 1. Place in ```C:\fitnesse\```
1. Download [Selenium Java Web Driver](http://www.seleniumhq.org/download/)
 1. Place in ```C:\selenium-2.47.1\```
1. Download [FitLibrary](http://sourceforge.net/projects/fitlibrary/)
 1. Copy FitLibrary fitnesse folder on top of ```C:\fitnesse folder```
1. Download [Appian servlet plug-in](https://github.com/appianps/ps-ext-AutomatedTestFramework/blob/master/plugins/AutomatedTestServlet/AppianAutomatedTestServlet.1.0.0.jar) and place in ```APPIAN_HOME\_admin\plugins```
1. Download [Appian fixtures](https://github.com/appianps/ps-ext-AutomatedTestFramework/blob/master/appian-fixtures.jar) and place in ```C:\fitnesse\lib\appian```

## Running Fitnesse

1. Make sure Appian is currently running
1. In command prompt navigate to ```C:\fitnesse```
1. Run runFitNesseDirectly.bat
1. Navigate to ```http://localhost:8980/{WikiName}``` to run a test

## Setting up Jenkins with a FitNesse test

1. Download [Jenkins installer](https://jenkins-ci.org/)
1. Install Jenkins
 1. Update JENKINS_HOME/jenkins.xml ```--httpPort``` argument to another port, e.g. 8081
 1. Restart Jenkins service in Window services
1. In a browser, navigate to ```http://localhost:8081```
1. Click on **Manage Jenkins**
1. Click on **Manage Plugins**
1. Click on **Available** tab
1. Filter by FitNesse check its box and click **Download now and install after restart**
1. Navigate back to the home page and click **New Item**
1. Choose a build name, select **Freestyle project**, and click **OK**
1. Click **Add build step** and select **Execute FitNesse test**
1. Enter the following values
 1. Select **Start new FitNesse instance as part of build**
 1. Enter ```C:\fitnesse``` in **Java working directory**, 
 1. Enter ```C:\fitnesse\fitnesse.jar``` in **Path to fitnesse.jar**
 1. Enter ```C:\fitnesse\FitNesseRoot``` in **Path to FitNesseRoot**
 1. Enter ```8980``` in **Port for FitNesse instance**
 1. Enter the page for the test suite in **Target Page**
 1. Check **Is target a suite?** if the page is a suite
 1. Set **HTTP Timeout** and **Test Timeout** high enough that the tests will not timeout
 1. Enter ```C:\fitnesse\results\fitnesse-results.xml``` in **Path to fitnesse xml results file**
1. Click **Add post-build action** and select **Publish Fitnesse results report**
1. Click **Save**
1. You can now run your FitNesse test by clicking **Build Now**

## Creating a wiki
1. Navigate to ```localhost:8980/{WikiName}```
 * If the {WikiName} contains 'suite', then it will be considered a FitNesse suite
 * If the {WikiName} contains 'test', then it will be considered a FitNesse test
 * If the {WikiName} is 'SetUp', then it will be run before each test case
 * If the {WikiName} is 'TearDown', then it will be run after each test case
 * If the {WikiName} is 'SuiteSetUp', then it will be run once at the beginning of the test suite
 * If the {WikiName} is 'SuiteTearDown', then it will be run once at the end of the test suite
1. There are some example wikis in the wikis folder for creating a Suite, SetUp, TearDown, and a few example tests
 * These were based off a specific application, but can be modified to work with any application
 * To run any tests they will have to be started with the correct path, see wikis/CredentialsSuite.txt for an example
 * To run any DoFixture tests, the first table in the test must be a DoFixture class, see wikis/CredentialsSuite.SetUp for an example
 
## Commands

### UTILITIES
* | setup selenium web driver with | {browser name} | browser |
* | set data source name to | {data source name} |
* | set appian url to | {appian url } |
* | set start datetime | - will be used as relative datetime for date/time fields
* | take screenshot | {file path} |
* | login with username | {username} | with password | {password} | - uses set appian url
* | wait for | {relative period, e.g. +1 days, +1 hours} | - waits for relative amount of time
* | wait until | {relative period} | - waits until relative time
* | refresh | - refreshes screen
* | logout from tempo |

### NAVIGATION
* | click on tempo menu | {tempo menu name} |

### NEWS
* | verify news feed containing text | {news item title} | is present |
* | verify news feed containing text | {news item title} | is not present |
* | toggle more info for news feed containing text | {news item title} |
* | verify news feed containing text | {news item title} | and more info with label | {label value} | and value | {value value} | is present |
* | verify news feed containing text | {news item title} | tagged with | {tag name} | is present |
* | verify news feed containing text | {news item title} | commented with | {comment value} | is present |

### TASKS
* | click on tempo task | {task name} |
* | verify tempo task | {task name} | is present |
* | verify tempo task | {task name} | is not present |
* | verify tempo task | {task name} | has a deadline of | {deadline text} |

### RECORDS
* | click on tempo record list | {record list name} |
* | click on tempo record list facet option | {facet name} |
* | verify tempo record list facet option | {facet name} | is present |
* | click on tempo record item | {record item name} |
* | verify tempo record item | {record item name} | is present |
* | verify tempo record item | {record item name} | is not present |
* | click on tempo record item facet | {facet name} |
* | click on tempo record item related action | {related action name} | - this is run on the related action dashboard, not as a button currently
* | verify tempo record item related action | {related action name} | is present |
* | verify tempo record item related action | {related action name} | is not present |

### REPORTS
* | click on tempo report | {report name} |

### ACTIONS
* | click on tempo action | {action name} |
* | verify tempo action completed |

### FORMS
* | populate tempo field | {field label} | with | {values} |
* | clear tempo field | {field label} | of | {value to remove} | - this is for removing specific value from a picker
* | verify tempo field | {field label} | contains | {value} |
* | populate tempo editable grid | {grid name} | column | {column name or number} | row | {row number} | with | {value} |
* verify editable grid | {grid name} | column | {column name or number} | row | {row number} | contains | {value} |
* | click on tempo link | {link name} |
* | click on tempo button | {button name} |

