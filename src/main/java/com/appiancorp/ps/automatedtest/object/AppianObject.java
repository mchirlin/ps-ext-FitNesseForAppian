package com.appiancorp.ps.automatedtest.object;

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

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.exception.WaitForWorkingTestException;

public class AppianObject {

  private static final Logger LOG = Logger.getLogger(AppianObject.class);

  public static final String DATE_ENTRY_FORMAT = "yyyy-MM-dd";
  public static final String TIME_ENTRY_FORMAT = "HH:mm";
  public static final String DATETIME_ENTRY_FORMAT = DATE_ENTRY_FORMAT + " " + TIME_ENTRY_FORMAT;

  private static final String XPATH_WORKING = Settings.getByConstant("xpathAbsoluteWorking");

  private static final Pattern INDEX_PATTERN = Pattern.compile("(.*)?\\[([0-9]+)\\]");
  private static final String DATETIME_REGEX = "([0-9]{4}-[0-9]{2}-[0-9]{2}([0-9]{2}:[0-9]{2})?)";
  private static final String DATETIME_CALC_REGEX = DATETIME_REGEX + "?[+-][0-9]+(minute(s)?|hour(s)?|day(s)?)";

  public static boolean isDatetime(String dateTimeString) {
    dateTimeString = dateTimeString.replaceAll("\\s", "");
    return dateTimeString.matches(DATETIME_REGEX);
  }

  public static String formatDatetime(String dateTimeString, Settings s) {
    dateTimeString = dateTimeString.trim();
    Date d;
    try {
      d = parseDate(dateTimeString, s);
    } catch (ParseException e) {
      d = s.getStartDatetime();
    }

    return new SimpleDateFormat(s.getDatetimeDisplayFormat()).format(d);
  }

  public static boolean isDatetimeCalculation(String dateTimeString) {
    dateTimeString = dateTimeString.replaceAll("\\s", "");
    return dateTimeString.matches(DATETIME_CALC_REGEX);
  }

  public static String formatDatetimeCalculation(String dateTimeString, Settings s) {
    dateTimeString = dateTimeString.trim();
    int plusLocation = dateTimeString.indexOf("+");
    Date d;

    if (plusLocation > 0) {
      try {
        d = parseDate(dateTimeString.substring(0, plusLocation).trim(), s);
      } catch (ParseException e) {
        d = s.getStartDatetime();
      }
    } else {
      d = s.getStartDatetime();
    }

    int addValue = Integer.parseInt(dateTimeString.substring(plusLocation, dateTimeString.length()).replaceAll("[^0-9]", ""));
    if (dateTimeString.contains("minute")) {
      d = DateUtils.addMinutes(d, addValue);
    } else if (dateTimeString.contains("hour")) {
      d = DateUtils.addHours(d, addValue);
    } else if (dateTimeString.contains("day")) {
      d = DateUtils.addDays(d, addValue);
    }

    return new SimpleDateFormat(s.getDatetimeDisplayFormat()).format(d);
  }

  public static Date parseDate(String datetimeString, Settings s) throws ParseException {
    return DateUtils.parseDateStrictly(datetimeString, new String[] {
      DATE_ENTRY_FORMAT,
      DATETIME_ENTRY_FORMAT,
      s.getDateFormat(),
      s.getDateDisplayFormat(),
      s.getDatetimeFormat(),
      s.getDatetimeDisplayFormat()
    });
  }

  public static String parseVariable(String variable, Settings s) {
    if (isDatetimeCalculation(variable))
      return formatDatetimeCalculation(variable, s);
    else if (isDatetime(variable))
      return formatDatetime(variable, s);
    else
      return variable;
  }

  public static String runExpression(String expression, Settings s) {
    try {
      String servletUrl = s.getUrl() + "/plugins/servlet/appianautomatedtest?operation=runExpression&expression=" +
        URLEncoder.encode(expression, "UTF-8");

      String returnVal = "";

      // Open new tab
      ((JavascriptExecutor) s.getDriver()).executeScript("window.open('" + servletUrl + "','_blank');");

      // Switch to tab
      Set<String> handles = s.getDriver().getWindowHandles();
      String popupHandle = "";
      for (String handle : handles) {
        if (!handle.equals(s.getMasterWindowHandle()))
          popupHandle = handle;
      }
      s.getDriver().switchTo().window(popupHandle);

      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//pre")));
      returnVal = s.getDriver().findElement(By.xpath("//pre")).getText();

      LOG.debug("'" + expression + "' equals '" + returnVal + "'");

      // Close tab
      s.getDriver().close();

      Thread.sleep(500);
      s.getDriver().switchTo().window(s.getMasterWindowHandle());
      s.getDriver().switchTo().defaultContent();

      return returnVal;
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
    return null;
  }

  public static void waitForWorking(Settings s, Integer timeout) {
    try {
      Thread.sleep(550);
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(XPATH_WORKING)));
      Thread.sleep(200);
    } catch (InterruptedException e) {
      throw ExceptionBuilder.build(e, s, "Wait for Working");
    } catch (TimeoutException e) {
      throw new WaitForWorkingTestException(e);
    }
  }

  public static void waitForWorking(Settings s) {
    waitForWorking(s, s.getTimeoutSeconds());
  }

  public static void clickElement(WebElement element, Settings s) {
    scrollIntoView(element, s);
    element.click();
    unfocus(s);
  }

  public static void scrollIntoView(WebElement webElement, Boolean alignToTop, Settings s) {
    // Have to manually scroll element into view because Tempo header covers the action link for long action lists
    LOG.warn(getXpathLocator(webElement));
    ((JavascriptExecutor) s.getDriver()).executeScript("arguments[0].scrollIntoView(" + alignToTop.toString() + ");", webElement);
  }

  public static void scrollIntoView(WebElement webElement, Settings s) {
    scrollIntoView(webElement, false, s);
  }

  public static void unfocus(Settings s, Integer timeout) {
    ((JavascriptExecutor) s.getDriver()).executeScript("!!document.activeElement ? document.activeElement.blur() : 0");
    waitForWorking(s, timeout);
  }

  public static void unfocus(Settings s) {
    unfocus(s, s.getTimeoutSeconds());
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
