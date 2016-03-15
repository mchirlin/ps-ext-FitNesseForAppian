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

public class TempoDateField extends TempoField {

  private static final Logger LOG = Logger.getLogger(TempoDateField.class);
  private static final String XPATH_ABSOLUTE_DATE_FIELD_INPUT = Settings.getByConstant("xpathAbsoluteDateFieldInput");
  private static final String XPATH_RELATIVE_DATE_FIELD_PLACEHOLDER = Settings.getByConstant("xpathRelativeDateFieldPlaceholder");
  private static final String XPATH_RELATIVE_DATE_FIELD_INPUT = Settings.getByConstant("xpathRelativeDateFieldInput");

  public static void populate(WebElement fieldLayout, String fieldValue, Settings s) throws ParseException {
    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    fieldValue = parseVariable(fieldValue, s);
    Date d = parseDate(fieldValue, s);

    populateTempoDateFieldWithDate(fieldLayout, d, s);
    unfocus(s);
  }

  public static void waitFor(String fieldName, Settings s) {
    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_DATE_FIELD_INPUT, fieldName))));
      WebElement fieldLayout = getFieldLayout(fieldName, s);
      scrollIntoView(fieldLayout, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Field", fieldName);
    }
  }

  public static String getValue(WebElement fieldLayout) {
    String value = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_INPUT)).getAttribute("value");

    if (LOG.isDebugEnabled()) LOG.debug("GET VALUE [" + value + "]");

    return value;
  }

  public static boolean contains(WebElement fieldLayout, String fieldValue, Settings s) throws ParseException {
    String dateString = getValue(fieldLayout);

    Date compareDate = parseDate(dateString, s);
    Date fieldDate = parseDate(fieldValue, s);
    if (LOG.isDebugEnabled())
      LOG.debug("DATE FIELD COMPARISON : Field value [" + compareDate.toString() + "] compared to Entered value [" + fieldDate.toString() +
        "]");

    return DateUtils.isSameDay(compareDate, fieldDate);
  }

  private static void populateTempoDateFieldWithDate(WebElement fieldLayout, Date d, Settings s) {
    String dateValue = new SimpleDateFormat(s.getDateFormat()).format(d);

    WebElement datePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_PLACEHOLDER));
    WebElement dateField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_INPUT));

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

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_INPUT));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
