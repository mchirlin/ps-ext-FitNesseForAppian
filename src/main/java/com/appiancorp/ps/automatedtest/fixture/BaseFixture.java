package com.appiancorp.ps.automatedtest.fixture;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
/*
 * import org.openqa.selenium.phantomjs.PhantomJSDriver;
 * import org.openqa.selenium.phantomjs.PhantomJSDriverService;
 * import org.openqa.selenium.remote.DesiredCapabilities;
 */
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.AppianWebApi;
import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.Screenshot;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.tempo.TempoLogin;

/**
 * This is the base class for integrating Appian and FitNesse.
 * This class contains fixture commands which are generic to Appian tests.
 * 
 * @author henry.chandra
 * @author michael.chirlin
 */
public class BaseFixture {

  private static final Logger LOG = Logger.getLogger(BaseFixture.class);

  private Properties props = new Properties();

  protected Settings settings;

  public BaseFixture() {
    super();
    settings = Settings.initialize();
    loadProperties();
  }

  /**
   * Starts selenium browser<br>
   * <br>
   * FitNesse Example: <code>| setup selenium web driver with | BROWSER | browser |</code>
   * 
   * @param browser
   *          Browser to test with, currently supports FIREFOX, CHROME, IE
   */
  @Deprecated
  public void setupSeleniumWebDriverWithBrowser(String browser) {
    setupWithBrowser(browser);
  }

  /**
   * Starts selenium browser<br>
   * <br>
   * FitNesse Example: <code>| setup with | BROWSER | browser |</code>
   * 
   * @param browser
   *          Browser to test with, currently supports FIREFOX, CHROME, IE
   */
  public void setupWithBrowser(String browser) {
    if (browser.equals("FIREFOX")) {
      FirefoxProfile prof = new FirefoxProfile();
      prof.setPreference("browser.startup.homepage_override.mstone", "ignore");
      prof.setPreference("startup.homepage_welcome_url.additional", "about:blank");
      if (!StringUtils.isBlank(props.getProperty(Constants.FIREFOX_BROWSER_HOME))) {
        File pathToBinary = new File(props.getProperty(Constants.FIREFOX_BROWSER_HOME));
        FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
        settings.setDriver(new FirefoxDriver(ffBinary, prof));
      } else {
        settings.setDriver(new FirefoxDriver(prof));
      }
    } else if (browser.equals("CHROME")) {
      String driverHome;

      if (StringUtils.isNotBlank(props.getProperty(Constants.CHROME_DRIVER_HOME))) {
        driverHome = props.getProperty(Constants.CHROME_DRIVER_HOME);
      } else {
        driverHome = props.getProperty(Constants.AUTOMATED_TESTING_HOME) + Constants.DRIVERS_LOCATION +
          Constants.CHROME_DRIVER;
      }

      System.setProperty("webdriver.chrome.driver", driverHome);

      System.setProperty("webdriver.chrome.args", "--disable-logging");
      System.setProperty("webdriver.chrome.silentOutput", "true");

      ChromeOptions co = new ChromeOptions();
      co.addArguments("--disable-extensions");
      co.addArguments("no-sandbox");
      if (StringUtils.isNotBlank(props.getProperty(Constants.CHROME_BROWSER_HOME))) {
        co.setBinary(props.getProperty(Constants.CHROME_BROWSER_HOME));
        co.addArguments("--disable-extensions");
        co.addArguments("no-sandbox");
        settings.setDriver(new ChromeDriver(co));
      } else {
        settings.setDriver(new ChromeDriver(co));
      }
    } else if (browser.equals("IE")) {
      System.setProperty("webdriver.ie.driver", props.getProperty(Constants.AUTOMATED_TESTING_HOME) + Constants.DRIVERS_LOCATION +
        Constants.IE_DRIVER);
      System.setProperty("webdriver.ie.driver.silent", "true");
      DesiredCapabilities dCaps = new DesiredCapabilities();
      dCaps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
      settings.setDriver(new InternetExplorerDriver(dCaps));
    } /*
       * else if (browser.equals("PHANTOM")) {
       * DesiredCapabilities dCaps = new DesiredCapabilities();
       * dCaps.setJavascriptEnabled(true);
       * dCaps.setCapability("takesScreenshot", true);
       * dCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, prop.getProperty("phantomjs.binary.path"));
       * new PhantomJSDriver(dCaps);
       * }
       */
  }

  /**
   * Sets the default appian url.<br>
   * <br>
   * FitNesse Example: <code>| set appian url to | APPIAN_URL |</code>
   * 
   * @param url
   *          Url for Appian site, e.g. https://forum.appian.com/suite
   */
  public void setAppianUrlTo(String url) {
    settings.setUrl(url);
  }

  /**
   * Sets the default appian version.<br>
   * <br>
   * FitNesse Example: <code>| set appian version to | APPIAN_VERSION |</code>
   * 
   * @param version
   *          Version for Appian site, e.g. 16.1
   */
  public void setAppianVersionTo(String version) {
    // Version is the only non-thread safe settings variable
    Settings.setVersion(version);
  }

  /**
   * Sets the Appian locale. This is useful so that test cases will work in different geographic regions that format date and time
   * differently.<br>
   * <br>
   * FitNesse Example: <code>| set appian locale to | en_US or en_GB |</code>
   * 
   * @param locale
   *          Appian locale (en_US or en_GB)
   */
  public void setAppianLocaleTo(String locale) {
    settings.setLocale(locale);
  }

  /**
   * Sets the start datetime with which all of the relative dates and datetimes will be calculated.<br>
   * <br>
   * FitNesse Example: <code>| set start datetime |</code>
   */
  public void setStartDatetime() {
    settings.setStartDatetime(new Date());
  }

  /**
   * Sets the datasource name<br>
   * 
   * @param dataSourceName
   *          Name of the data source
   */
  @Deprecated
  public void setDataSourceNameTo(String dataSourceName) {
    settings.setDataSourceName(dataSourceName);
  }

  /**
   * Sets the global timeout seconds that are used for each implicit wait. <br>
   * FitNesse Example: <code>| set timeout seconds to | 10 |</code>
   * 
   * @param ts
   *          Timeout seconds
   */
  public void setTimeoutSecondsTo(Integer ts) {
    settings.setTimeoutSeconds(ts);
  }

  /**
   * Sets the path on the automated test server where screenshots will be placed. <br>
   * FitNesse Example: <code>| set screenshot path to | AUTOMATED_TESTING_HOME/screenshots/ |</code>
   * 
   * @param path
   *          Path to save screen shots
   */
  public void setScreenshotPathTo(String path) {
    settings.setScreenshotPath(path);
  }

  /**
   * Set the flag to stop FitNesse on error. If true, FitNesse will quit on the first failed test. This will also quit the WebDriver as
   * well.<br>
   * <br>
   * FitNesse Example: <code>| set stop on error to | BOOLEAN |</code>
   * 
   * @param bool
   *          true or false
   */
  public void setStopOnErrorTo(Boolean bool) {
    settings.setStopOnError(bool);
  }

  /**
   * Set the flag to take screenshots on errors. If true, every error in an automated test will trigger a screenshot to be placed in
   * {@link #setScreenshotPathTo(String)}.<br>
   * <br>
   * FitNesse Example: <code>| set take error screenshots to | true |</code>
   * 
   * @param bool
   *          true or false
   */
  public void setTakeErrorScreenshotsTo(Boolean bool) {
    if (settings.getScreenshotPath() == null) {
      settings.setScreenshotPath(props.getProperty(Constants.AUTOMATED_TESTING_HOME) + "/screenshots");
    }
    settings.setTakeErrorScreenshots(bool);
  }

  /**
   * Navigate to a particular url.<br>
   * <br>
   * FitNesse Example: <code>| open | https://forum.appian.com/suite |</code>
   * 
   * @param url
   *          Url to navigate to
   */
  public void open(String url) {
    settings.getDriver().get(url);
  }

  /**
   * Take a screenshot and place it in the directory defined by: {@link #setScreenshotPathTo(String)}.<br>
   * <br>
   * FitNesse Example: <code>| take screenshot |</code>
   * 
   * @param fileName
   *          File name for new screenshot
   */
  public void takeScreenshot(String fileName) {
    Screenshot.getInstance(settings).capture(fileName);
  }

  /**
   * Login to Appian.<br>
   * <br>
   * FitNesse Example: <code>| login into | APPIAN_URL | with username | USERNAME | and password | PASSWORD |</code>
   * 
   * @param url
   *          Url of Appian site
   * @param userName
   *          Username
   * @param password
   *          Password
   */
  public void loginIntoWithUsernameAndPassword(String url, String userName, String password) {
    TempoLogin.getInstance(settings).navigateToLoginPage(url);
    TempoLogin.getInstance(settings).waitForLogin();
    TempoLogin.getInstance(settings).login(url, userName, password);
  }

  /**
   * Login to Appian.<br>
   * <br>
   * FitNesse Example: <code>| login with username | USERNAME | and password | PASSWORD |</code> - Uses the url set here:
   * {@link #setAppianUrlTo(String)}
   * 
   * @param userName
   *          Username
   * @param password
   *          Password
   */
  public void loginWithUsernameAndPassword(String userName, String password) {
    loginIntoWithUsernameAndPassword(settings.getUrl(), userName, password);
  }

  /**
   * Login to Appian using users.properties.<br>
   * <br>
   * 
   * @param url
   * @param username
   */
  public void loginIntoWithUsername(String url, String username) {
    String password = props.getProperty(username);

    loginIntoWithUsernameAndPassword(url, username, password);
  }

  /**
   * Login to Appian using users.properties.<br>
   * <br>
   * 
   * @param url
   * @param username
   */
  public void loginWithUsername(String username) {
    loginIntoWithUsername(settings.getUrl(), username);
  }

  /**
   * Login to Appian using roles.properties.<br>
   * <br>
   * 
   * @param url
   * @param role
   */
  public void loginIntoWithRole(String url, String role) {
    String usernamePassword = props.getProperty(role);
    String username = StringUtils.substringBefore(usernamePassword, "|");
    String password = StringUtils.substringAfter(usernamePassword, "|");

    loginIntoWithUsernameAndPassword(url, username, password);
  }

  /**
   * Login to Appian using roles.properties.<br>
   * <br>
   * 
   * @param role
   */
  public void loginWithRole(String role) {
    loginIntoWithRole(settings.getUrl(), role);
  }

  /**
   * Login to and Appian site containing terms and conditions.<br>
   * <br>
   * FitNesse Example: <code>| login with terms with username | USERNAME | and password | PASSWORD |</code> - Uses the url set here:
   * {@link #setAppianUrlTo(String)}
   * 
   * @param userName
   *          Username
   * @param password
   *          Password
   */
  public void loginWithTermsWithUsernameAndPassword(String userName, String password) {
    TempoLogin.getInstance(settings).navigateToLoginPage(settings.getUrl());
    TempoLogin.getInstance(settings).waitForTerms();
    TempoLogin.getInstance(settings).loginWithTerms(settings.getUrl(), userName, password);
  }

  /**
   * Login to and Appian site containing terms and conditions.<br>
   * <br>
   * FitNesse Example: <code>| login with terms with username | USERNAME | </code> - Uses the url set here: {@link #setAppianUrlTo(String)}
   * 
   * @param userName
   *          Username
   */
  public void loginWithTermsWithUsername(String userName) {
    TempoLogin.getInstance(settings).navigateToLoginPage(settings.getUrl());
    TempoLogin.getInstance(settings).waitForTerms();
    String password = getProps().getProperty(userName);
    TempoLogin.getInstance(settings).loginWithTerms(settings.getUrl(), userName, password);
  }

  /**
   * Login to and Appian site containing terms and conditions.<br>
   * <br>
   * FitNesse Example: <code>| login with terms with role | USER_ROLE| </code> - Uses the url set here: {@link #setAppianUrlTo(String)}
   * 
   * @param userRole
   *          user role
   */
  public void loginWithTermsWithRole(String role) {
    TempoLogin.getInstance(settings).navigateToLoginPage(settings.getUrl());
    TempoLogin.getInstance(settings).waitForTerms();
    String usernamePassword = props.getProperty(role);
    String username = StringUtils.substringBefore(usernamePassword, "|");
    String password = StringUtils.substringAfter(usernamePassword, "|");

    TempoLogin.getInstance(settings).loginWithTerms(settings.getUrl(), username, password);
  }

  /**
   * Waits for a particular period of time.<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| wait for | 20 seconds |</code><br>
   * <code>| wait for | 5 minutes |</code><br>
   * <code>| wait for | 1 hour |</code><br>
   * 
   * @param period
   *          Period of time, e.g. 5 minutes, 1 hour, 10 seconds
   */
  public void waitFor(String period) {
    int periodNum = Integer.parseInt(period.replaceAll("[^0-9]", ""));
    int noOfSeconds = 0;
    if (period.contains("hour")) {
      noOfSeconds = periodNum * 60 * 24;
    } else if (period.contains("minute")) {
      noOfSeconds = periodNum * 60;
    } else {
      noOfSeconds = periodNum;
    }

    try {
      int i = 0;
      while (true) {
        if (i >= noOfSeconds) {
          break;
        }

        Thread.sleep(1000);
        i++;

      }
    } catch (InterruptedException e) {
      throw ExceptionBuilder.build(e, settings, "Wait Until");
    }
  }

  /**
   * Waits for X seconds.<br>
   * <br>
   * FitNesse Example: <code>| wait for | 5 | seconds|</code>
   * 
   * @param period
   *          Number of seconds to wait for
   */
  public void waitForSeconds(Integer period) {
    waitFor(period + " seconds");
  }

  /**
   * Waits for X minutes.<br>
   * <br>
   * FitNesse Example: <code>| wait for | 10 | minutes |</code>
   * 
   * @param period
   *          Number of minutes to wait for
   */
  public void waitForMinutes(Integer period) {
    waitFor(period + " minutes");
  }

  /**
   * Wait for X hours.<br>
   * <br>
   * FitNesse Example: <code>| wait for | 1 | hours |</code>
   * 
   * @param period
   *          Number of hours to wait for
   */
  public void waitForHours(Integer period) {
    waitFor(period + " hours");
  }

  /**
   * Waits for 'Working...' message to disappear<br>
   * <br>
   * FitNesse Example: <code>| wait for working |</code>
   */
  public void waitForWorking() {
    AppianObject.getInstance(settings).waitForWorking();
  }

  /**
   * Waits until a particular datetime<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| wait until | 01/11/2016 12:31 PM |</code> - Test will halt until that particular time
   * 
   * @param datetime
   *          Datetime string must match {@link #setDateFormatTo(String)} and {@link #setTimeFormatTo(String)}
   */
  public void waitUntil(String datetime) {
    datetime = AppianObject.getInstance(settings).formatDatetimeCalculation(datetime);

    try {
      Date endDatetime = DateUtils.parseDate(datetime, settings.getDatetimeDisplayFormat());
      Date nowDatetime = new Date();

      while (endDatetime.after(nowDatetime)) {
        Thread.sleep(1000);
        nowDatetime = new Date();
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait Until");
    }
  }

  /**
   * Calls a web api and returns result<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get web api | WEB_API_ENDPOINT | with username | USERNAME |</code>
   * 
   * @param webApiName
   */
  public String getWebApiWithUsername(String webApiEndpoint, String username) {
    String password = props.getProperty(username);
    return AppianWebApi.getInstance(settings).callWebApi(webApiEndpoint, "", username, password);
  }

  /**
   * Calls a web api and returns result<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| get web api | WEB_API_ENDPOINT | with role | ROLE |</code>
   * 
   * @param webApiName
   */
  public String getWebApiWithRole(String webApiEndpoint, String role) {
    String usernamePassword = props.getProperty(role);
    String username = StringUtils.substringBefore(usernamePassword, "|");
    String password = StringUtils.substringAfter(usernamePassword, "|");

    return AppianWebApi.getInstance(settings).callWebApi(webApiEndpoint, "", username, password);
  }

  /**
   * Calls a web api and returns result<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| post web api | WEB_API_ENDPOINT | with body | BODY | with username | USERNAME |</code>
   * 
   * @param webApiName
   */
  public String postWebApiWithBodyWithUsername(String webApiEndpoint, String body, String username) {
    String password = props.getProperty(username);
    return AppianWebApi.getInstance(settings).callWebApi(webApiEndpoint, body, username, password);
  }

  /**
   * Calls a web api and returns result<br>
   * <br>
   * FitNesse Examples:<br>
   * <code>| post web api | WEB_API_ENDPOINT | with body | BODY | with role | ROLE |</code>
   * 
   * @param webApiName
   */
  public String postWebApiWithBodyWithRole(String webApiEndpoint, String body, String role) {
    String usernamePassword = props.getProperty(role);
    String username = StringUtils.substringBefore(usernamePassword, "|");
    String password = StringUtils.substringAfter(usernamePassword, "|");

    return AppianWebApi.getInstance(settings).callWebApi(webApiEndpoint, body, username, password);
  }

  /**
   * Sets test variables<br >
   * <br>
   * | set test variable | TEST_VAR_KEY | with | TEST_VAR_AS_JSON |
   */
  public void setTestVariableWith(String key, String val) {
    settings.setTestVariableWith(key, val);
  }

  /**
   * Get test variable<br>
   * <br>
   * | get test variable | tv!VARIABLE_NAME |
   */
  public String getTestVariable(String variableName) {
    return settings.getTestVariable(variableName.replace("tv!", ""));
  }

  /**
   * Refreshes page<br>
   * <br>
   * FitNesse Example: <code>| refresh |</code>
   */
  public void refresh() {
    settings.getDriver().navigate().refresh();
  }

  @Deprecated
  public boolean startProcessWithProcessModelUuId(String processModelUuid) {
    try {
      settings.getDriver().get(
        settings.getUrl() + "/suite/plugins/servlet/appianautomatedtest?operation=startProcessWithPMUuId&pmUuid=" +
          URLEncoder.encode(processModelUuid, "UTF-8"));
      String pageSource = settings.getDriver().getPageSource();
      if (pageSource.contains("Exceptions occur")) {
        return false;
      } else {
        LOG.debug("PROCESS ID: " + pageSource);
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Deprecated
  public boolean waitUntilTaskOfProcessModelUuidStartedRecentlyIsCompleted(String taskName, String pmUuid) {
    WebDriver driver = settings.getDriver();

    boolean completed = false;
    try {

      int i = 0;
      String seconds = "120";
      while (!completed) {
        if (i > 120) {
          return false;
        }

        Thread.sleep(5000);

        Set<String> windows = settings.getDriver().getWindowHandles();
        String mainHandle = settings.getDriver().getWindowHandle();

        ((JavascriptExecutor) driver).executeScript("window.open();");

        Set<String> completeWindow = driver.getWindowHandles();
        completeWindow.removeAll(windows);
        String completeHandle = ((String) completeWindow.toArray()[0]);

        driver.switchTo().window(completeHandle);
        driver.get(settings.getUrl() + "/suite/plugins/servlet/appianautomatedtest?operation=queryIsTaskCompletedWithinSeconds&pmUuid=" +
          URLEncoder.encode(pmUuid, "UTF-8") + "&taskName=" + URLEncoder.encode(taskName, "UTF-8") + "&seconds=" +
          URLEncoder.encode(seconds, "UTF-8"));

        String pageSource = driver.getPageSource();
        driver.close();
        driver.switchTo().window(mainHandle);

        if (pageSource.contains("Task is completed")) {
          completed = true;
        }

        i++;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return completed;
  }

  @Deprecated
  public boolean verifyDataInDatabaseWithQueryAndFields(String sqlQuery, String fields) {
    WebDriver driver = settings.getDriver();

    try {
      Set<String> windows = driver.getWindowHandles();
      String mainHandle = driver.getWindowHandle();

      ((JavascriptExecutor) driver).executeScript("window.open();");

      Set<String> verifyWindow = driver.getWindowHandles();
      verifyWindow.removeAll(windows);
      String verifyHandle = ((String) verifyWindow.toArray()[0]);

      driver.switchTo().window(verifyHandle);
      driver.get(settings.getUrl() + "/suite/plugins/servlet/appianautomatedtest?operation=verifyDataInDataBase&dataSource=" +
        URLEncoder.encode(settings.getDataSourceName(), "UTF-8") + "&sqlQuery=" + URLEncoder.encode(sqlQuery, "UTF-8") + "&fields=" +
        URLEncoder.encode(fields, "UTF-8"));

      String pageSource = driver.getPageSource();
      String jsonSource = pageSource.substring(pageSource.indexOf("["), pageSource.indexOf("]") + 1);

      driver.close();
      driver.switchTo().window(mainHandle);

      JSONArray resultArr = new JSONArray(jsonSource);

      return resultArr.length() > 0;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Deprecated
  public boolean verifyConstantHasValueOf(String constantName, String expectedConstantValue) {
    WebDriver driver = settings.getDriver();

    try {
      Set<String> windows = driver.getWindowHandles();
      String mainHandle = driver.getWindowHandle();

      ((JavascriptExecutor) driver).executeScript("window.open();");

      Set<String> verifyWindow = driver.getWindowHandles();
      verifyWindow.removeAll(windows);
      String verifyHandle = ((String) verifyWindow.toArray()[0]);

      driver.switchTo().window(verifyHandle);
      driver.get(settings.getUrl() + "/suite/plugins/servlet/appianautomatedtest?operation=verifyConstantHasValueOf&constantName=" +
        URLEncoder.encode(constantName, "UTF-8") + "&expectedConstantValue=" + URLEncoder.encode(expectedConstantValue, "UTF-8"));

      String pageSource = driver.getPageSource();
      driver.close();
      driver.switchTo().window(mainHandle);

      return pageSource.contains("Constant value is verified");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Closes browser and driver quits. Used to end automated test.<br>
   * <br>
   * FitNesse Example: <code>| tear down selenium driver |</code>
   */
  @Deprecated
  public void tearDownSeleniumWebDriver() {
    tearDown();
  }

  /**
   * Closes browser and driver quits. Used to end automated test.<br>
   * <br>
   * FitNesse Example: <code>| tear down |</code>
   */
  public void tearDown() {
    settings.getDriver().quit();
  }

  /**
   * Returns a random string of a specific length<br>
   * <br>
   * FitNesse example: <code>| $rand= | get random string | 5 | </code> - This will set the variable <i>rand</i> to a random string
   * which can later be accessed using $rand.
   * 
   * @param length
   *          Length of random string
   * @return Random alphanumeric string
   */
  public String getRandomString(int length) {
    return RandomStringUtils.randomAlphanumeric(length);
  }

  /**
   * Returns a random alphabetic string of a specific length<br>
   * <br>
   * FitNesse example: <code>| $rand= | get random alphabet string | 5 | </code> - This will set the variable <i>rand</i> to a random
   * alphabet string
   * which can later be accessed using $rand.
   * 
   * @param length
   *          Length of random alphabet string
   * @return Random alphabet string
   */
  public String getRandomAlphabetString(int length) {
    return RandomStringUtils.randomAlphabetic(length);
  }

  /**
   * Returns a random integer of a specific length<br>
   * <br>
   * FitNesse example: <code>| $randInt= | get random integer from | INT_MIN | to | INT_MAX | </code> - This will set the variable
   * <i>randInt</i> to a random integer between two numbers which can later be accessed using $randInt.
   * 
   * @param min
   *          Minimum of random integer
   * @param max
   *          Maximum of random integer
   * @return Random integer between the min and max
   */
  public int getRandomIntegerFromTo(int min, int max) {
    if (min > max) {
      throw ExceptionBuilder.build(new IllegalArgumentException("Min cannot exceed the Max"), settings, "Get Random Int");
    }
    Random random = new Random();
    long range = (long) max - (long) min;
    long fraction = (long) (range * random.nextDouble());
    return (int) (fraction + min);
  }

  /**
   * Returns a random integer of a specific length<br>
   * <br>
   * FitNesse example: <code>| $randDecimal= | get random decimal from | DOUBLE_MIN |to | DOUBLE_MAX|</code> - This will set the
   * variable <i>randDecimal</i> to a random decimal between two numbers which can later be accessed using $randDecimal.
   * 
   * @param min
   *          Minimum of random decimal
   * @param max
   *          Maximum of random decimal
   * @return Random decimal between the min and max
   */
  public double getRandomDecimalFromTo(double min, double max) {
    if (min > max) {
      throw ExceptionBuilder.build(new IllegalArgumentException("Min cannot exceed the Max"), settings, "Get Random Decimal");
    }
    Random random = new Random();
    double range = max - min;
    double fraction = (range * random.nextDouble());
    return (fraction + min);
  }

  /**
   * Returns a random integer of a specific length<br>
   * <br>
   * FitNesse example: <code>| $randDecimal= | get random decimal from | DOUBLE_MIN |to | DOUBLE_MAX| with | DECIMAL_PLACES |</code> -
   * This will set the variable <i>randDecimal</i> to a random decimal between two numbers which can later be accessed using $randDecimal.
   * 
   * @param min
   *          Minimum of random decimal
   * @param max
   *          Maximum of random decimal
   * @param decimalPlaces
   *          Number of integers after the decimal places to display
   * @return Random decimal between the min and max with a certain number of decimal places
   */
  public double getRandomDecimalFromToWith(double min, double max, int decimalPlaces) {
    if (min > max) {
      throw ExceptionBuilder.build(new IllegalArgumentException("Min cannot exceed the Max"), settings, "Get Random Decimal");
    }
    Random random = new Random();
    double range = max - min;
    double fraction = (range * random.nextDouble());
    BigDecimal total = new BigDecimal(fraction + min);
    BigDecimal trimmed = total.setScale(decimalPlaces, RoundingMode.HALF_DOWN);
    return trimmed.doubleValue();
  }

  public Settings getSettings() {
    return settings;
  }

  private void loadProperties() {

    InputStream inputStream = null;

    // If inside of jar
    try {
      List<String> files = IOUtils.readLines(BaseFixture.class.getClassLoader()
        .getResourceAsStream("configs/"), Charsets.UTF_8);
      for (String file : files) {
        inputStream = BaseFixture.class.getClassLoader().getResourceAsStream("configs/" + file);
        props.load(inputStream);
      }
    } catch (Exception e) {
    }

    try {
      // If outside of jar
      if (inputStream == null) {
        File jarPath = new File(BaseFixture.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath = jarPath.getParentFile().getAbsolutePath();
        File folder = new File(URLDecoder.decode(propertiesPath + "/../../configs", "UTF-8"));

        for (File file : folder.listFiles()) {
          if (FilenameUtils.getExtension(file.getPath()).equals("properties")) {
            inputStream = new FileInputStream(file.getAbsolutePath());
            props.load(inputStream);
          }
        }
      }
    } catch (Exception e) {
    }
  }

  public Properties getProps() {
    return props;
  }

  public void setProp(Properties prop) {
    this.props = prop;
  }
}
