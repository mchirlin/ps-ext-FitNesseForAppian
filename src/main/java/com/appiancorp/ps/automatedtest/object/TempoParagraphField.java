package com.appiancorp.ps.automatedtest.object;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoParagraphField extends TempoField {

  private static final Logger LOG = Logger.getLogger(TempoParagraphField.class);
  private static final String XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteParagraphFieldLabel");
  private static final String XPATH_ABSOLUTE_PARAGRAPH_FIELD_INDEX = Settings.getByConstant("xpathAbsoluteParagraphFieldIndex");
  private static final String XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL_INDEX = "(" + XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL + ")[%d]";
  private static final String XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT = Settings.getByConstant("xpathRelativeParagraphFieldInput");

  public static WebElement getFieldLayout(String fieldName, Settings s) {
    if (isFieldIndex(fieldName)) {
      int index = getIndexFromFieldIndex(fieldName);
      String name = getFieldFromFieldIndex(fieldName);
      if (StringUtils.isBlank(name)) {
        return s.getDriver().findElement(
          By.xpath(String.format(XPATH_ABSOLUTE_PARAGRAPH_FIELD_INDEX + XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, index)));
      } else {
        return s.getDriver().findElement(
          By.xpath(String.format(XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL_INDEX + XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, name, index)));
      }

    } else {
      return s.getDriver().findElement(
        By.xpath(String.format(XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL + XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, fieldName)));
    }
  }

  public static void populate(WebElement fieldLayout, String fieldValue, Settings s) throws InterruptedException {
    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    WebElement textAreaField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT));
    textAreaField.clear();
    textAreaField.sendKeys(fieldValue);
    Thread.sleep(500); // For some reason paragraph fields have trouble when moving quickly
  }

  public static void waitFor(String fieldName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + fieldName + "]");

    try {
      if (isFieldIndex(fieldName)) {
        int index = getIndexFromFieldIndex(fieldName);
        String name = getFieldFromFieldIndex(fieldName);
        if (StringUtils.isBlank(name)) {
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
            XPATH_ABSOLUTE_PARAGRAPH_FIELD_INDEX, index))));
        } else {
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
            XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL_INDEX, index))));
        }

      } else {
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL, fieldName))));
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Field", fieldName);
    }
  }

  public static String getValue(WebElement fieldLayout) {
    String value = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT)).getAttribute("value");
    if (LOG.isDebugEnabled()) LOG.debug("PARAGRAPH FIELD VALUE : " + value);

    return value;
  }

  public static boolean contains(WebElement fieldLayout, String fieldValue) {
    // For read-only
    try {
      return TempoField.contains(fieldLayout, fieldValue);
    } catch (Exception e) {
    }

    // For editable
    String compareString = getValue(fieldLayout);
    if (LOG.isDebugEnabled())
      LOG.debug("PARAGRAPH FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");

    return compareString.contains(fieldValue);
  }

  public static void clear(WebElement fieldLayout) {
    WebElement textAreaField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT));
    textAreaField.clear();
  }

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
