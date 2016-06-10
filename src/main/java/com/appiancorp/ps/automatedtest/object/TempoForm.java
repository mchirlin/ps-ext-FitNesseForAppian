package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoForm extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoForm.class);
  private static final String XPATH_ABSOLUTE_FORM_TITLE = Settings.getByConstant("xpathAbsoluteFormTitle");
  private static final String XPATH_ABSOLUTE_FORM_INSTRUCTIONS = Settings.getByConstant("xpathAbsoluteFormInstructions");

  public static String getFormTitle(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("GET FORM TITLE");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_TITLE)));
      return element.getText();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Get Form Title");
    }
  }

  public static void waitForTitle(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR FORM TITLE");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds()))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_FORM_TITLE))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Title");
    }
  }

  public static String getFormInstructions(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("GET FORM INSTRUCTIONS");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_INSTRUCTIONS)));
      return element.getText();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Get Form Instructions");
    }
  }

  public static void waitForInstructions(Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR FORM INSTRUCTIONS");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds()))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_FORM_INSTRUCTIONS))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Form Instructions");
    }
  }

  public static String getRegexGroupFromFormTitle(String regex, Integer group, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("REGEX FOR FORM TITLE [" + regex + "]");

    try {
      String text = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FORM_TITLE))).getText();
      if (LOG.isDebugEnabled()) LOG.debug("TITLE VALUE [" + text + "]");
      return getRegexResults(regex, group, text);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Title regex", regex);
    }
  }

  public static String getRegexGroupFromFieldValue(String regex, Integer group, String fieldName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("REGEX FOR FIELD VALUE [" + regex + "]");

    try {
      String text = TempoField.getValue(fieldName, s);
      if (LOG.isDebugEnabled()) LOG.debug("FIELD VALUE [" + text + "]");
      return getRegexResults(regex, group, text);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Field value regex", regex);
    }
  }
}
