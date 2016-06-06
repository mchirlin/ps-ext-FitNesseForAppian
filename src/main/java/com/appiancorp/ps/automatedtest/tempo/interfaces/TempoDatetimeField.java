package com.appiancorp.ps.automatedtest.tempo.interfaces;

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

public class TempoDatetimeField extends AbstractTempoField {

  private static final Logger LOG = Logger.getLogger(TempoDatetimeField.class);
  // private static final String XPATH_ABSOLUTE_DATETIME_FIELD_DATE_INPUT = Settings.getByConstant("xpathAbsoluteDatetimeFieldDateInput");
  private static final String XPATH_RELATIVE_DATETIME_FIELD_DATE_PLACEHOLDER = Settings.getByConstant("xpathRelativeDatetimeFieldDatePlaceholder");
  private static final String XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT = Settings.getByConstant("xpathRelativeDatetimeFieldDateInput");
  // private static final String XPATH_ABSOLUTE_DATETIME_FIELD_TIME_INPUT = Settings.getByConstant("xpathAbsoluteDatetimeFieldTimeInput");
  private static final String XPATH_RELATIVE_DATETIME_FIELD_TIME_PLACEHOLDER = Settings.getByConstant("xpathRelativeDatetimeFieldTimePlaceholder");
  private static final String XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT = Settings.getByConstant("xpathRelativeDatetimeFieldTimeInput");

  public static TempoDatetimeField getInstance(Settings settings) {
    return new TempoDatetimeField(settings);
  }

  private TempoDatetimeField(Settings settings) {
    super(settings);
  }

  // @Override
  // public void waitFor(String... params) {
  // String fieldName = params[0];
  //
  // try {
  // (new WebDriverWait(settings.getDriver(),
  // settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathFormat(
  // XPATH_ABSOLUTE_DATETIME_FIELD_DATE_INPUT, fieldName))));
  // (new WebDriverWait(settings.getDriver(),
  // settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathFormat(
  // XPATH_ABSOLUTE_DATETIME_FIELD_TIME_INPUT, fieldName))));
  // } catch (Exception e) {
  // throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
  // }
  // }

  @Override
  public void populate(WebElement fieldLayout, String... params) throws ParseException {
    String fieldValue = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    Date d = parseDate(fieldValue);

    populateTempoDatetimeFieldWithDate(fieldLayout, d);
    populateTempoDatetimeFieldWithTime(fieldLayout, d);
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    String dateString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT)).getAttribute("value");
    String timeString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT)).getAttribute("value");

    String value = dateString + " " + timeString;
    if (LOG.isDebugEnabled()) LOG.debug("DATE FIELD VALUE [" + value + "]");

    return value;
  }

  @Override
  public boolean contains(WebElement fieldLayout, String... params) throws ParseException {
    String fieldValue = params[0];

    String datetimeString = capture(fieldLayout);

    Date compareDate = parseDate(datetimeString);
    Date fieldDate = parseDate(fieldValue);
    LOG.debug("DATETIME FIELD COMPARISON : Field value [" + fieldDate.toString() + "] compared to Entered value [" + fieldDate.toString() +
      "]");

    return DateUtils.isSameInstant(compareDate, fieldDate);
  }

  private void populateTempoDatetimeFieldWithDate(WebElement fieldLayout, Date d) {
    String dateValue = new SimpleDateFormat(settings.getDateFormat()).format(d);

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
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOf(dateField));
      dateField.sendKeys(dateValue);
    }
  }

  private void populateTempoDatetimeFieldWithTime(WebElement fieldLayout, Date d) {
    String timeValue = new SimpleDateFormat(settings.getTimeFormat()).format(d);

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
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOf(timeField));
      timeField.sendKeys(timeValue);
    }
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {
    // TODO Auto-generated method stub
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
