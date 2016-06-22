package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoImageField extends AbstractTempoField {

  private static final Logger LOG = Logger.getLogger(TempoTextField.class);
  private static final String XPATH_ABSOLUTE_IMAGE_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteImageFieldLabel");
  private static final String XPATH_ABSOLUTE_IMAGE_FIELD_INDEX = Settings.getByConstant("xpathAbsoluteImageFieldIndex");
  private static final String XPATH_ABSOLUTE_IMAGE_FIELD_LABEL_INDEX = "(" + XPATH_ABSOLUTE_IMAGE_FIELD_LABEL + ")[%2$d]";
  private static final String XPATH_RELATIVE_IMAGE_FIELD_INPUT = Settings.getByConstant("xpathRelativeImageFieldInput");

  public static TempoImageField getInstance(Settings settings) {
    return new TempoImageField(settings);
  }

  private TempoImageField(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String fieldName = params[0];

    if (isFieldIndex(fieldName)) {
      int index = getIndexFromFieldIndex(fieldName);
      String name = getFieldFromFieldIndex(fieldName);
      if (StringUtils.isBlank(name)) {
        return xpathFormat(XPATH_ABSOLUTE_IMAGE_FIELD_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, index);
      } else {
        return xpathFormat(XPATH_ABSOLUTE_IMAGE_FIELD_LABEL_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, name,
          index);
      }

    } else {
      return xpathFormat(XPATH_ABSOLUTE_IMAGE_FIELD_LABEL + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, fieldName);
    }
  }

  @Override
  public void waitFor(String... params) {
    String fieldName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + fieldName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
    }
  }

  @Override
  public void populate(WebElement fieldLayout, String... params) {
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    WebElement element = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_IMAGE_FIELD_INPUT)));

    String value = element.getAttribute("alt");
    if (LOG.isDebugEnabled()) LOG.debug("IMAGE FIELD ALT : " + value);

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

    // For editable
    String compareString = capture(fieldLayout);
    if (LOG.isDebugEnabled())
      LOG.debug("IMAGE FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");

    return compareString.contains(fieldValue);
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {

  }

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_IMAGE_FIELD_INPUT));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
