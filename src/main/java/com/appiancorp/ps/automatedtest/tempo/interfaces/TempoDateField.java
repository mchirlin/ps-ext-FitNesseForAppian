package com.appiancorp.ps.automatedtest.tempo.interfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoDateField extends AbstractTempoField {

  private static final Logger LOG = Logger.getLogger(TempoDateField.class);
  // private static final String XPATH_ABSOLUTE_DATE_FIELD_INPUT = Settings.getByConstant("xpathAbsoluteDateFieldInput");
  private static final String XPATH_RELATIVE_DATE_FIELD_PLACEHOLDER = Settings.getByConstant("xpathRelativeDateFieldPlaceholder");
  private static final String XPATH_RELATIVE_DATE_FIELD_INPUT = Settings.getByConstant("xpathRelativeDateFieldInput");

  public static TempoDateField getInstance(Settings settings) {
    return new TempoDateField(settings);
  }

  private TempoDateField(Settings settings) {
    super(settings);
  }

  // @Override
  // public void waitFor(String... params) {
  // String fieldName = getParam(0, params);
  //
  // try {
  // (new WebDriverWait(settings.getDriver(),
  // settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathFormat(
  // XPATH_ABSOLUTE_DATE_FIELD_INPUT, fieldName))));
  // } catch (Exception e) {
  // throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
  // }
  // }

  @Override
  public void populate(WebElement fieldLayout, String... params) throws ParseException {
    String fieldValue = getParam(1, params);

    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    Date d = parseDate(fieldValue);

    populateTempoDateFieldWithDate(fieldLayout, d);
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    String value = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_INPUT)).getAttribute("value");

    if (LOG.isDebugEnabled()) LOG.debug("GET VALUE [" + value + "]");

    return value;
  }

  @Override
  public boolean contains(WebElement fieldLayout, String... params) throws ParseException {
    String fieldValue = getParam(0, params);

    String dateString = capture(fieldLayout);

    Date compareDate = parseDate(dateString);
    Date fieldDate = parseDate(fieldValue);
    if (LOG.isDebugEnabled())
      LOG.debug("DATE FIELD COMPARISON : Field value [" + compareDate.toString() + "] compared to Entered value [" + fieldDate.toString() +
        "]");

    return DateUtils.isSameDay(compareDate, fieldDate);
  }

  private void populateTempoDateFieldWithDate(WebElement fieldLayout, Date d) {
    String dateValue = new SimpleDateFormat(settings.getDateFormat()).format(d);

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
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOf(dateField));
      dateField.sendKeys(dateValue);
    }
    ((JavascriptExecutor) settings.getDriver()).executeScript("var popup = document.getElementsByClassName('dateBoxPopup'); if (popup) popup[0].style.visibility = 'hidden';");
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {
    // TODO Auto-generated method stub
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
