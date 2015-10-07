# Running Selenium with Fitnesse

## Installation

1. Download [FitNesse jar](http://www.fitnesse.org/fitnesse.jar?responder=releaseDownload&release=20111026)
 1. Place in ```C:\fitnesse\```
1. Download [Selenium Java Web Driver](http://selenium-release.storage.googleapis.com/2.47/selenium-java-2.47.1.zip)
 1. Place in ```C:\selenium-2.47.1\```
1. Download [FitLibrary](http://sourceforge.net/projects/fitlibrary/files/latest/download)
 1. Copy FitLibrary fitnesse folder on top of ```C:\fitnesse folder```
1. Download [Appian servlet plug-in](https://github.com/appianps/ps-ext-AutomatedTestFramework/blob/master/plugins/AutomatedTestServlet/AppianAutomatedTestServlet.1.0.0.jar) and place in ```APPIAN_HOME\_admin\plugins```
1. Download [Appian fixtures](https://github.com/appianps/ps-ext-AutomatedTestFramework/blob/master/appian-fixtures.jar) and place in ```C:\fitnesse\lib\appian```

## Running Fitnesse

1. Make sure Appian is currently running
1. In command prompt navigate to ```C:\fitnesse```
1. Run runFitNesseDirectly.bat
1. Navigate to ```http://localhost:8980/{WikiName}``` to run a test

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

