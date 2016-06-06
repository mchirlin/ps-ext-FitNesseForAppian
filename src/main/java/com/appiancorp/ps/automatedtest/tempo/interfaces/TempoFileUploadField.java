package com.appiancorp.ps.automatedtest.tempo.interfaces;

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

public class TempoFileUploadField extends AbstractTempoField {

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

  public static TempoFileUploadField getInstance(Settings settings) {
    return new TempoFileUploadField(settings);
  }

  private TempoFileUploadField(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String fieldName = params[0];

    if (isFieldIndex(fieldName)) {
      int index = getIndexFromFieldIndex(fieldName);
      String name = getFieldFromFieldIndex(fieldName);
      if (StringUtils.isBlank(name)) {
        return xpathFormat(XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, index);
      } else {
        return xpathFormat(XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT,
          name, index);
      }

    } else {
      return xpathFormat(XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_LABEL + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, fieldName);
    }
  }

  @Override
  public void waitFor(String... params) {
    String fieldName = params[0];

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
    }
  }

  @Override
  public void populate(WebElement fieldLayout, String... params) {
    String fieldValue = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    WebElement fileUpload = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_FILE_UPLOAD_FIELD_INPUT));
    fileUpload.sendKeys(fieldValue);
    unfocus(300);
    waitForFileUpload(fieldLayout);
  }

  public void waitForFileUpload(WebElement fieldLayout) {
    String xpathLocator = getXpathLocator(fieldLayout);
    (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(" +
      xpathLocator + ")" +
      XPATH_ABSOLUTE_FILE_UPLOAD_FIELD_WAITING)));
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    String value = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_FILE_UPLOAD_FIELD_FILE))).getText();
    Matcher m = FILENAME_PATTERN.matcher(value);

    if (m.find()) {
      value = m.group(1);
    }

    if (LOG.isDebugEnabled()) LOG.debug("FILE UPLOAD FIELD VALUE : " + value);

    return value;
  }

  @Override
  public boolean contains(WebElement fieldLayout, String... params) {
    String fieldValue = params[0];

    // For read-only
    try {
      return TempoFieldFactory.getInstance(settings).contains(fieldLayout, fieldValue);
    } catch (Exception e) {
    }

    fieldValue = Paths.get(fieldValue).getFileName().toString();
    String compareString = capture(fieldLayout);

    if (LOG.isDebugEnabled())
      LOG.debug("FILE UPLOAD FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");

    return compareString.equals(fieldValue);
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {
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
