package com.appiancorp.ps.automatedtest.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoDatetimeField extends TempoField {

  private static final Logger LOG = Logger.getLogger(TempoDatetimeField.class);
  private static final String XPATH_ABSOLUTE_DATETIME_FIELD_DATE_INPUT = Settings.getByConstant("xpathAbsoluteDatetimeFieldDateInput");
  private static final String XPATH_RELATIVE_DATETIME_FIELD_DATE_PLACEHOLDER = Settings.getByConstant("xpathRelativeDatetimeFieldDatePlaceholder");
  private static final String XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT = Settings.getByConstant("xpathRelativeDatetimeFieldDateInput");
  private static final String XPATH_ABSOLUTE_DATETIME_FIELD_TIME_INPUT = Settings.getByConstant("xpathAbsoluteDatetimeFieldTimeInput");
  private static final String XPATH_RELATIVE_DATETIME_FIELD_TIME_PLACEHOLDER = Settings.getByConstant("xpathRelativeDatetimeFieldTimePlaceholder");
  private static final String XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT = Settings.getByConstant("xpathRelativeDatetimeFieldTimeInput");

  public static void populate(WebElement fieldLayout, String fieldValue, Settings s) throws ParseException {
    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    Date d = parseDate(fieldValue, s);

    populateTempoDatetimeFieldWithDate(fieldLayout, d, s);
    populateTempoDatetimeFieldWithTime(fieldLayout, d, s);
  }

  public static void waitFor(String fieldName, Settings s) {
    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_DATETIME_FIELD_DATE_INPUT, fieldName))));
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_DATETIME_FIELD_TIME_INPUT, fieldName))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Field", fieldName);
    }
  }

  public static String getValue(WebElement fieldLayout) {
    String dateString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT)).getAttribute("value");
    String timeString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT)).getAttribute("value");

    String value = dateString + " " + timeString;
    if (LOG.isDebugEnabled()) LOG.debug("DATE FIELD VALUE : " + value);

    return value;
  }

  public static boolean contains(WebElement fieldLayout, String fieldValue, Settings s) throws ParseException {
    String datetimeString = getValue(fieldLayout);

    Date compareDate = parseDate(datetimeString, s);
    Date fieldDate = parseDate(fieldValue, s);
    LOG.debug("DATETIME FIELD COMPARISON : Field value [" + fieldDate.toString() + "] compared to Entered value [" + fieldDate.toString() +
      "]");

    return DateUtils.isSameInstant(compareDate, fieldDate);
  }

  private static void populateTempoDatetimeFieldWithDate(WebElement fieldLayout, Date d, Settings s) {
    String dateValue = new SimpleDateFormat(s.getDateFormat()).format(d);

    WebElement datePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_PLACEHOLDER));
    WebElement dateField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT));

    // Clear out existing values
    if (dateField.isDisplayed()) {
      dateField.click();
      dateField.sendKeys(Keys.CONTROL + "a");
      dateField.sendKeys(Keys.DELETE);
      dateField.sendKeys(dateValue);
    } else {
      datePlaceholder.click();
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOf(dateField));
      dateField.sendKeys(dateValue);
    }
  }

  private static void populateTempoDatetimeFieldWithTime(WebElement fieldLayout, Date d, Settings s) {
    String timeValue = new SimpleDateFormat(s.getTimeFormat()).format(d);

    WebElement timePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_PLACEHOLDER));
    WebElement timeField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT));

    // Clear out existing values
    if (timeField.isDisplayed()) {
      timeField.click();
      timeField.sendKeys(Keys.CONTROL + "a");
      timeField.sendKeys(Keys.DELETE);
      timeField.sendKeys(timeValue);
    } else {
      timePlaceholder.click();
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOf(timeField));
      timeField.sendKeys(timeValue);
    }
  }

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT));
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
