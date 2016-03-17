# What is this? #

**Automated Test Suite** is a tool for Automating Appian UI tests using a combination of *FitNesse* and *Selenium*.

## Overview ##

Selenium and FitNesse provides an "easy" way to create user interface driven automated tests with Appian. Test cases can be used for functional testing and regression testing of the user interface and the process model functional logic.

The presentation [Overview of Automate Testing with Appian](https://docs.google.com/presentation/d/1z17TOZcrWjDmkhpUfGmymDZJ-Kh8gKzggylEzJ9CDZw/edit#slide=id.gcab12166d_0_18) contains more information about the tool as well as a demo video.

For additional API information, view the <a href="http://appianps.github.io/ps-ext-AutomatedTestFramework/docs/">JavaDocs</a>.

## Usage and Contributions ##

To use Selenium and Fitnesse on your project, please follow the steps identified below for setting up the testing framework and creating/executing test cases.

We ask that whenever possible, you contribute back to the repository and the Appian PS community by 
* adding any missing commands that you need to create for test cases
* fixing issues and defects
* implementing enhancements

As we work and contribute to make this tool better, we will take the greatest care to ensure that the next versions are backward compatible. Rest assure that whatever changes are released in the future are NOT going to break your test cases.

If your team cannot directly enhance the framework, please make sure to contact the Appian CoE on Home or over email in order to provide your feedback. 

**We can help only if we know where the problems are!**

## Installation ##
#### Prerequisities ####

* [Firefox](https://www.mozilla.org/en-US/firefox/new/) installed
* Java JDK installed and configured
 * Configure *JAVA_HOME* environment variable to point to your JDK installation directory.
 * Add *%JAVA_HOME%\bin* to PATH environment variable

#### Installation ###

1. Download the newest release of [AutomatedTesting.zip](https://github.com/appianps/ps-ext-AutomatedTestFramework/releases).
1. Unzip contents of AutomatedTesting.zip into *TESTING_HOME*, e.g. `C:\AutomatedTesting`. *TESTING\_HOME* should not include spaces. 
1. In a command prompt navigate to *TESTING_HOME*.
1. Run `start.bat` to install and run FitNesse. Installation only takes 30 seconds.
 * This will also start FitNesse, to stop type ctrl+C.

**NOTE:** if you choose a different location for *TESTING_HOME* than `C:\AutomatedTesting`, then you must update the classpath variable in the examples to refer to the correct location.

#### Running Your First FitNesse Test ####

1. Start FitNesse if it isn't already running:
 1. In a command prompt navigate to *TESTING_HOME*.
 1. Run `start.bat`.
1. Navigate to `http://localhost:8980/LoginTest`.
1. Click **Edit** to modify test and update the following variables:
 * Update the !path if your *TESTING_HOME* does not match `C:\AutomatedTesting`
 * Update the *APPIAN\_URL*, *APPIAN\_LOCALE* (en\_US or en\_GB), and *APPIAN\_VERSION* (7.10, 7.11, or 16.1)
 * Update the Username *REPLACE\_USERNAME* and Password *REPLACE\_PASSWORD*'
1. Click **Save**.
1. Click **Test**.

#### Running FitNesse Tests From Another directory ####
1. Open `TESTING_HOME\fitnesse.properties` in a text editor.
1. Update **FitNesseRootDir** to refer to the repository containing the *FitNesseRoot* folder.
1. Update **FitNesseRoot** to refer to the folder name containing the wikis.
1. Next time you use `TESTING_HOME\start.bat` the wikis will be pulled directly from the repo folders.

#### Setting up Jenkins with a GitHub Repo of FitNesse tests ####

1. Download [Jenkins installer](https://jenkins-ci.org/).
1. Install Jenkins.
 1. Update `JENKINS_HOME\jenkins.xml` `--httpPort` argument to another port, e.g. 8081.
 1. Restart Jenkins service in Window services.
1. In a browser, navigate to `http://localhost:8081`.
1. Add relevant plugins (FitNesse and Git):
 1. Click on **Manage Jenkins**.
 1. Click on **Manage Plugins**.
 1. Click on **Available** tab.
 1. Filter by FitNesse check its box and click **Download now and install after restart**.
 1. Repeat, but filter by Git check its box and click **Download now and install after restart**.
1. Once restarted, set up your GitHub credentials:
 1. Click on **Credentials**.
 1. Click on **Global credentials**.
 1. Click on **Add Credentials** and enter the following:<br>
 **Kind**: Username with password<br>
 **Username**: *GitHub username*<br>
 **Password**: *GitHub password*<br>
 **Description**: GitHub Credentials
1. Setup git.exe
 1. Click on **Manage Jenkins**.
 1. Click on **Configure System**.
 1. In the **Git** section, populate your **Path to Git executable** to the location of `git.exe`.
1. Navigate back to the home page and click **New Item**.
1. Choose a build name, select **Freestyle project**, and click **OK**.
1. Setup SCM:
 1. Under **Source Code Management** click **Git**.
 1. Enter the **Repository URL** for your GitHub repository containing the fitnesse tests.
 1. For **Credentials** select your previously enter gihub credentials. 
1. Click **Add build step** and select **Execute FitNesse test**.
1. Enter the following values:
 1. Select **Start new FitNesse instance as part of build**.
 1. Enter `TESTING_HOME` in **Java working directory**.
 1. Enter `TESTING_HOME\fitnesse-standalone.jar` in **Path to fitnesse.jar**.
 1. Enter the folder in your repository containing the FitNesse wikis in **Path to FitNesseRoot**, this can be subfolders.
 1. Enter `8981` in **Port for FitNesse instance**.
 1. Enter the page for the test or suite in **Target Page**.
 1. Check **Is target a suite?** if the page is a suite.
 1. Set **HTTP Timeout** and **Test Timeout** high enough that the tests will not timeout.
 1. Enter `fitnesse-results.xml` in **Path to fitnesse xml results file**.
1. Click **Add post-build action** and select **Publish Fitnesse results report**.
 1. Enter `fitnesse-results.xml` in **Path to fitnesse xml results file**.
1. Click **Save**.
1. You can now run your FitNesse test by clicking **Build Now**. This will download the newest version of the FitNesse tests from the GitHub repository and run them locally.
 
## Development Environment Setup ##

#### Setup Maven ####
1. Download [Maven](http://maven.apache.org/)
1. Configure `M2_HOME` environment variable to point to your Maven installation directory.
1. Add `%M2_HOME%\bin` to PATH environment variable
1. Add Git to PATH environment variable

#### Clone the GitHub Repo ####
1. Clone the project repo to your local computer using `git clone git@github.com:appianps/ps-ext-AutomatedTestFramework.git`

#### Eclipse Setup ####
1. Setup the `M2_REPO` variable in Eclipse by running: `mvn eclipse:configure-workspace -Declipse.workspace="<your-eclipse-workspace>"`.
  * If you don't know your eclipse workspace, open your Eclipse preferences and go to General > Startup and Shutdown > Workspaces.
1. Cleanup any existing Eclipse project files `mvn eclipse:clean`
1. Generate new Eclipse project files by running the following from the root of the Labs repo working directory: `mvn eclipse:eclipse`.
1. Open Eclipse
1. Import the projects using File > Import > General > Existing Projects into Workspace.
1. Select the repo folder and import the project.

## Development ##
#### Creating a New Method ####
All methods that are callable in FitNesse are derived from the `appian-fixture-x.x.x.jar` found in `C:\AutomatedTesting\lib\appian`. To create or modify an existing method, you must do the following:

1. Modify class `com.appiancorp.ps.automatedtest.fixture.[BaseFixture|TempoFixture]` to add new methods to the corresponding environment.
 1. Most methods written for these classes will simply call methods of an object in the `com.appiancorp.ps.automatest.object` package, e.g. `verifyNewsFeedContainingTextIsNotPresent(newsText)` calls `TempoNews.waitFor(newsText)`.
 1. Make sure to annotate the class for javadocs generation.
1. Add and test corresponding JUnit test cases in `\src\test\java\com\appiancorp\ps\automatedtest\fixture`.
1. Update the public javadocs:
 1. Run **Project > Generate Javadoc...** and select the **com.appiancorp.ps.automatedtest.fixture** package (we don't need javadocs for the objects).
 1. Copy the resulting docs folder onto your desktop.
 1. Switch to the **gh-pages** branch of the repository.
 1. Delete the existing docs folder in this repository and replace it with the copied version.
 1. Commit and push.
1. Follow the Local build procedures below to generate a new jar for testing.
1. Copy new jar to `TESTING_HOME\lib\appian\` and delete the existing version.
1. To push back to the remote repository:
	1. Create new branch for changes: `git branch BRANCH_NAME` then `git checkout BRANCH_NAME`.
	1. Publish new branch: `git push`.
	1. Create pull request back to master.

## Building ##
#### Version Selection Methodology ####
* Update **MAJOR** version when you make incompatible API changes, e.g. removing methods, changing method names, or anything that would require updates to existing test cases.
* Update **MINOR** version when you add functionality in a backwards-compatible manner, e.g. adding new methods, updating drivers or jars.
* Update **PATCH** version when you make backwards-compatible bug fixes, e.g. fixing existing methods.

#### Local ####
1. Run `mvn verify` to run integration tests.
1. Run `mvn clean package` (add `-DskipTests=true` to skip unit tests).
1. Final JAR's are placed in the `/target/` folder.

#### Release ####
A new release can be prepared by completing the following steps:

1. Run `mvn release:clean`.
1. Run `mvn release:prepare -DpushChanges=false` add `-DdryRun=true` to perform the Maven release procedure without commiting changes to the local repository. Add `-Darguments="-DskipTests"` to skip integration tests. <br>**This step will automatically do the following:**
 * Updates the pom.xml files with the release version number
 * Commits the updated pom.xml files to the local git repository
 * Runs all unit & integration tests and build the final packages
 * Updates the pom.xml files with the next development version number
 * Commits the updated pom.xml files to the local git repository
1. Push generated commits to GitHub.
1. Create new release on https://github.com/appianps/ps-ext-AutomatedTestFramework/releases.

As `mvn release:prepare` only commits the updated pom.xml files locally, they need to be pushed to GitHub the same as any other commit.
The final release JAR's are placed in the `/target/` folder of each module.

#### Create New Installation Package ####
1. Download `AutomatedTesting.zip` and `AutomatedTesting-X.X.X-Patch.zip` from previous release.
1. Explode `AutomatedTestingVx.x.x-Patch.zip` and rename folder to current release.
1. Update `updates.zip`:
 * Add new jars or delete and replace existing jars with new versions.
 * Add new browser drivers or delete a replace existing drivers with new versions.
 * Replace contents of existing `updates\FitNesseRoot` with `REPO\src\test\example\fitnesse` directory contents.<br>
**NOTE**: The file structure of the new or updated files should match the file structure in `AutomatedTesting.zip`.
1. Update `deleteFiles.bat`:
 1. Add a line at the top of the file: `rem X.X.X`
 2. Add a line for each file that needs to be deleted from the previous update, this usually will be due to an updated version of a jar or driver.<br>For file deletion use: `del 2>nul /F "FILE_NAME.EXT"`<br>For folder deletion use: `rmdir 2>nul /S /Q "FOLDER\SUBFOLDER"`
1. Explode `AutomatedTesting.zip` and run patch instructions to perform updates.
1. Repackage `AutomatedTesting.zip` and `AutomatedTesting-X.X.X-Patch.zip`.
1. Upload new packages to both the GitHub release and Share Component.
