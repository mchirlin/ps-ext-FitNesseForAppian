package com.appiancorp.ps.automatedtest.common;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.exception.WaitForWorkingTestException;

public class AppianObject {

  private static final Logger LOG = Logger.getLogger(AppianObject.class);

  protected Settings settings;
  public static final String DATE_ENTRY_FORMAT = "yyyy-MM-dd";
  public static final String TIME_ENTRY_FORMAT = "HH:mm";
  public static final String DATETIME_ENTRY_FORMAT = DATE_ENTRY_FORMAT + " " + TIME_ENTRY_FORMAT;

  private static final String XPATH_WORKING = Settings.getByConstant("xpathAbsoluteWorking");

  private static final Pattern INDEX_PATTERN = Pattern.compile("(.*)?\\[([0-9]+)\\]");
  private static final String DATETIME_REGEX = "([0-9]{4}-[0-9]{2}-[0-9]{2}([0-9]{2}:[0-9]{2})?)";
  private static final String DATETIME_CALC_REGEX = DATETIME_REGEX + "?[+-][0-9]+(minute(s)?|hour(s)?|day(s)?|month(s)?|year(s)?)";
  private static final String TEST_VARIABLE_PREFIX = "tv!";
  private static final String TEST_VARIABLE_REGEX = "^" + TEST_VARIABLE_PREFIX + ".*";

  public static AppianObject getInstance(Settings settings) {
    return new AppianObject(settings);
  }

  public AppianObject(Settings settings) {
    this.settings = settings;
  }

  public static boolean isDatetime(String dateTimeString) {
    dateTimeString = dateTimeString.replaceAll("\\s", "");
    return dateTimeString.matches(DATETIME_REGEX);
  }

  public static boolean isTestVariable(String variable) {
    return variable.matches(TEST_VARIABLE_REGEX);
  }

  public String formatDatetime(String dateTimeString) {
    dateTimeString = dateTimeString.trim();
    Date d;
    try {
      d = parseDate(dateTimeString);
    } catch (ParseException e) {
      d = settings.getStartDatetime();
    }

    return new SimpleDateFormat(settings.getDatetimeDisplayFormat()).format(d);
  }

  public boolean isDatetimeCalculation(String dateTimeString) {
    dateTimeString = dateTimeString.replaceAll("\\s", "");
    return dateTimeString.matches(DATETIME_CALC_REGEX);
  }

  public String formatDatetimeCalculation(String dateTimeString) {
    dateTimeString = dateTimeString.trim();
    int plusLocation = dateTimeString.indexOf("+");
    Date d;

    if (plusLocation > 0) {
      try {
        d = parseDate(dateTimeString.substring(0, plusLocation).trim());
      } catch (ParseException e) {
        d = settings.getStartDatetime();
      }
    } else {
      d = settings.getStartDatetime();
    }

    int addValue = Integer.parseInt(dateTimeString.substring(plusLocation, dateTimeString.length()).replaceAll("[^0-9]", ""));
    if (dateTimeString.contains("minute")) {
      d = DateUtils.addMinutes(d, addValue);
    } else if (dateTimeString.contains("hour")) {
      d = DateUtils.addHours(d, addValue);
    } else if (dateTimeString.contains("day")) {
      d = DateUtils.addDays(d, addValue);
    } else if (dateTimeString.contains("month")) {
      d = DateUtils.addMonths(d, addValue);
    } else if (dateTimeString.contains("year")) {
      d = DateUtils.addYears(d, addValue);
    }

    return new SimpleDateFormat(settings.getDatetimeDisplayFormat()).format(d);
  }

  public Date parseDate(String datetimeString) throws ParseException {
    return DateUtils.parseDateStrictly(datetimeString, new String[] {
      DATE_ENTRY_FORMAT,
      DATETIME_ENTRY_FORMAT,
      settings.getDateFormat(),
      settings.getDateDisplayFormat(),
      settings.getDatetimeFormat(),
      settings.getDatetimeDisplayFormat()
    });
  }

  public String parseVariable(String variable) {
    if (isDatetimeCalculation(variable)) {
      return formatDatetimeCalculation(variable);
    } else if (isDatetime(variable)) {
      return formatDatetime(variable);
    } else if (isTestVariable(variable)) {
      return settings.getTestVariable(variable.replace(TEST_VARIABLE_PREFIX, ""));
    } else {
      return variable;
    }
  }

  public String getParam(int index, String... params) {
    return parseVariable(params[index]);
  }

  public static String escapeForXpath(String variable) {
    variable = variable.toLowerCase();
    if (variable.contains("'") || variable.contains("\"")) {
      return "concat('" + variable.replace("'", "', \"'\", '") + "', '')";
    } else {
      return "'" + variable + "'";
    }
  }

  public static String xpathFormat(String template, Object... params) {
    for (int i = 0; i < params.length; i++) {
      if (params[i] instanceof String) {
        params[i] = escapeForXpath((String) params[i]);
      }
    }

    return String.format(template, params);
  }

  public String runExpression(String expression) {
    try {
      String servletUrl = settings.getUrl() + "/plugins/servlet/appianautomatedtest?operation=runExpression&expression=" +
        URLEncoder.encode(expression, "UTF-8");

      String returnVal = "";

      // Open new tab
      ((JavascriptExecutor) settings.getDriver()).executeScript("window.open('" + servletUrl + "','_blank');");

      // Switch to tab
      Set<String> handles = settings.getDriver().getWindowHandles();
      String popupHandle = "";
      for (String handle : handles) {
        if (!handle.equals(settings.getMasterWindowHandle()))
          popupHandle = handle;
      }
      settings.getDriver().switchTo().window(popupHandle);

      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//pre")));
      returnVal = settings.getDriver().findElement(By.xpath("//pre")).getText();

      LOG.debug("'" + expression + "' equals '" + returnVal + "'");

      // Close tab
      settings.getDriver().close();

      Thread.sleep(500);
      settings.getDriver().switchTo().window(settings.getMasterWindowHandle());
      settings.getDriver().switchTo().defaultContent();

      return returnVal;
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
    return null;
  }

  public void waitForWorking(int timeout) {
    try {
      Thread.sleep(550);
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(XPATH_WORKING)));
      Thread.sleep(200);
    } catch (InterruptedException e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Working");
    } catch (TimeoutException e) {
      throw new WaitForWorkingTestException(e);
    }
  }

  public void waitForWorking() {
    waitForWorking(settings.getTimeoutSeconds());
  }

  public void clickElement(WebElement element) {
    scrollIntoView(element);
    element.click();
    unfocus();
  }

  public void scrollIntoView(WebElement webElement, Boolean alignToTop) {
    // Have to manually scroll element into view because Tempo header covers the action link for long action lists
    ((JavascriptExecutor) settings.getDriver()).executeScript("arguments[0].scrollIntoView(" + alignToTop.toString() + ");", webElement);
  }

  public void scrollIntoView(WebElement webElement) {
    scrollIntoView(webElement, false);
  }

  public void unfocus() {
    unfocus(settings.getTimeoutSeconds());
  }

  public void unfocus(Integer timeout) {
    ((JavascriptExecutor) settings.getDriver()).executeScript("!!document.activeElement ? document.activeElement.blur() : 0");

    waitForWorking(timeout);
  }

  public static boolean isFieldIndex(String fieldNameIndex) {
    return INDEX_PATTERN.matcher(fieldNameIndex).matches();
  }

  public static String getFieldFromFieldIndex(String fieldNameIndex) {
    Matcher m = INDEX_PATTERN.matcher(fieldNameIndex);
    if (m.find()) {
      return m.group(1);
    } else {
      return "";
    }
  }

  public static int getIndexFromFieldIndex(String fieldNameIndex) {
    Matcher m = INDEX_PATTERN.matcher(fieldNameIndex);
    if (m.find()) {
      return Integer.parseInt(m.group(2));
    } else {
      return 1;
    }
  }

  public static String getXpathLocator(WebElement element) {
    Pattern p = Pattern.compile(".*xpath: (.*)");
    Matcher m = p.matcher(element.toString());

    if (m.find()) {
      return m.group(1).substring(0, m.group(1).length() - 1);
    } else {
      return null;
    }
  }

  public static String getRegexResults(String regex, Integer group, String text) {
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(text.toString());

    if (m.find()) {
      LOG.debug("REGEX [" + regex + "] RESULTS [" + m.group(group) + "]");
      return m.group(group);
    } else {
      return "";
    }
  }
}
