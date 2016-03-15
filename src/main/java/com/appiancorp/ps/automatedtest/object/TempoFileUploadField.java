package com.appiancorp.ps.automatedtest.object;

import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoFileUploadField extends TempoField {

  private static final Logger LOG = Logger.getLogger(TempoFileUploadField.class);
  private static final String XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteFileUploadFieldLabel");
  private static final String XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL_INDEX = "(" + XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL + ")[%d]";
  private static final String XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_INDEX = Settings.getByConstant("xpathAbsoluteFileUploadFieldIndex");
  private static final String XPATH_RELATIVE_FILE_UPLOAD_FIELD_INPUT = Settings.getByConstant("xpathRelativeFileUploadFieldInput");
  private static final String XPATH_RELATIVE_FILE_UPLOAD_FIELD_FILE = Settings.getByConstant("xpathRelativeFileUploadFieldFile");
  private static final String XPATH_RELATIVE_FILE_UPLOAD_FIELD_REMOVE_LINK = Settings.getByConstant("xpathRelativeFileUploadFieldRemoveLink");
  private static final String XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_WAITING = XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL +
    Settings.getByConstant("xpathRelativeFileUploadFieldWaiting");
  private static final Pattern FILENAME_PATTERN = Pattern.compile("(.*) \\(.*\\)");

  public static WebElement getFieldLayout(String fieldName, Settings s) {
    if (isFieldIndex(fieldName)) {
      int index = getIndexFromFieldIndex(fieldName);
      String name = getFieldFromFieldIndex(fieldName);
      if (StringUtils.isBlank(name)) {
        return s.getDriver().findElement(
          By.xpath(String.format(XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_INDEX + XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, index)));
      } else {
        return s.getDriver().findElement(
          By.xpath(String.format(XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL_INDEX + XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, name, index)));
      }

    } else {
      return s.getDriver().findElement(
        By.xpath(String.format(XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL + XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, fieldName)));
    }
  }

  public static void populate(WebElement fieldLayout, String fieldValue, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    WebElement fileUpload = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_INPUT));
    fileUpload.sendKeys(fieldValue);
    unfocus(s);
    waitForFileUpload(fieldLayout, s);
    waitForWorking(s);
  }

  public static void waitFor(String fieldName, Settings s) {
    try {
      WebElement fieldLayout;
      if (isFieldIndex(fieldName)) {
        int index = getIndexFromFieldIndex(fieldName);
        String name = getFieldFromFieldIndex(fieldName);
        if (StringUtils.isBlank(name)) {
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
            XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_INDEX, index))));
        } else {
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
            XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL_INDEX, index))));
        }

      } else {
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL, fieldName))));
      }
      fieldLayout = getFieldLayout(fieldName, s);
      scrollIntoView(fieldLayout, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Field", fieldName);
    }
  }

  public static void waitForFileUpload(WebElement fieldLayout, Settings s) {
    String xpathLocator = getXpathLocator(fieldLayout);
    (new WebDriverWait(s.getDriver(), 300)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(" + xpathLocator + ")" +
      XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_WAITING)));
  }

  public static String getValue(WebElement fieldLayout) {
    String value = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_FILE_UPLOAD_FIELD_FILE))).getText();
    Matcher m = FILENAME_PATTERN.matcher(value);

    if (m.find()) {
      value = m.group(1);
    }

    if (LOG.isDebugEnabled()) LOG.debug("FILE UPLOAD FIELD VALUE : " + value);

    return value;
  }

  public static boolean contains(WebElement fieldLayout, String fieldValue, Settings s) {
    // For read-only
    try {
      return TempoField.contains(fieldLayout, fieldValue);
    } catch (Exception e) {
    }

    fieldValue = Paths.get(fieldValue).getFileName().toString();
    String compareString = getValue(fieldLayout);

    if (LOG.isDebugEnabled())
      LOG.debug("FILE UPLOAD FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");

    return compareString.equals(fieldValue);
  }

  public static void clear(WebElement fieldLayout) {
    WebElement removeLink = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_REMOVE_LINK));
    removeLink.click();
  }

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_INPUT));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
