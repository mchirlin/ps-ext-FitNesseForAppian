package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Container;

public class TempoParagraphField extends AbstractTempoField implements Container {

  private static final Logger LOG = Logger.getLogger(TempoParagraphField.class);
  private static final String XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteParagraphFieldLabel");
  private static final String XPATH_ABSOLUTE_PARAGRAPH_FIELD_INDEX = Settings.getByConstant("xpathAbsoluteParagraphFieldIndex");
  private static final String XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL_INDEX = "(" + XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL + ")[%2$d]";
  private static final String XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT = Settings.getByConstant("xpathRelativeParagraphFieldInput");

  public static TempoParagraphField getInstance(Settings settings) {
    return new TempoParagraphField(settings);
  }

  private TempoParagraphField(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String fieldName = getParam(0, params);

    if (isFieldIndex(fieldName)) {
      int index = getIndexFromFieldIndex(fieldName);
      String name = getFieldFromFieldIndex(fieldName);
      if (StringUtils.isBlank(name)) {
        return xpathFormat(XPATH_ABSOLUTE_PARAGRAPH_FIELD_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, index);
      } else {
        return xpathFormat(XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, name,
          index);
      }
    } else {
      return xpathFormat(XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, fieldName);
    }
  }

  @Override
  public void waitFor(String... params) {
    String fieldName = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + fieldName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
    }
  }

  @Override
  public void populate(WebElement fieldLayout, String... params) throws InterruptedException {
    String fieldValue = getParam(1, params);

    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    WebElement textAreaField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT));
    textAreaField.clear();
    textAreaField.sendKeys(fieldValue);

    // Paragraph fields don't update as quickly as other fields, not sure why
    Thread.sleep(700);
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    String value = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT)).getAttribute("value");
    if (LOG.isDebugEnabled()) LOG.debug("PARAGRAPH FIELD VALUE : " + value);

    return value;
  }

  @Override
  public boolean contains(WebElement fieldLayout, String... params) {
    String fieldValue = getParam(0, params);

    // For read-only
    try {
      return TempoFieldFactory.getInstance(settings).contains(fieldLayout, fieldValue);
    } catch (Exception e) {
    }

    // For editable
    String compareString = capture(fieldLayout);
    if (LOG.isDebugEnabled())
      LOG.debug("PARAGRAPH FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");

    return compareString.contains(fieldValue);
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {
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
