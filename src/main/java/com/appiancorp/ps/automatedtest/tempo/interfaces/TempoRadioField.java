package com.appiancorp.ps.automatedtest.tempo.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoRadioField extends AbstractTempoField {

  private static final Logger LOG = Logger.getLogger(TempoRadioField.class);
  // private static final String XPATH_ABSOLUTE_RADIO_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteRadioFieldLabel");
  private static final String XPATH_RELATIVE_RADIO_FIELD_CHOICE_LABEL = Settings.getByConstant("xpathRelativeRadioFieldChoiceLabel");
  private static final String XPATH_RELATIVE_RADIO_FIELD_CHOICE_INDEX = Settings.getByConstant("xpathRelativeRadioFieldChoiceIndex");
  private static final String XPATH_RELATIVE_RADIO_BUTTON_GROUP = Settings.getByConstant("xpathRelativeRadioButtonGroup");
  private static final String XPATH_RELATIVE_RADIO_FIELD_INPUT_SPAN = Settings.getByConstant("xpathRelativeRadioFieldInputSpan");

  public static TempoRadioField getInstance(Settings settings) {
    return new TempoRadioField(settings);
  }

  protected TempoRadioField(Settings settings) {
    super(settings);
  }

  // @Override
  // public void waitFor(String... params) {
  // String fieldName = getParam(0, params);
  //
  // try {
  // (new WebDriverWait(settings.getDriver(),
  // settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathFormat(
  // XPATH_ABSOLUTE_RADIO_FIELD_LABEL, fieldName))));
  // } catch (Exception e) {
  // throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
  // }
  // }

  @Override
  public void populate(WebElement fieldLayout, String... params) {
    String fieldValue = getParam(1, params);

    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    WebElement radioField;
    if (isFieldIndex(fieldValue)) {
      int index = getIndexFromFieldIndex(fieldValue);
      radioField = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_RADIO_FIELD_CHOICE_INDEX, index)));
    } else {
      radioField = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_RADIO_FIELD_CHOICE_LABEL, fieldValue)));
    }
    radioField.click();
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    List<String> values = new ArrayList<String>();

    for (WebElement span : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_RADIO_FIELD_INPUT_SPAN))) {
      if (span.findElement(By.tagName("input")).isSelected()) {
        values.add(span.findElement(By.tagName("label")).getText());
      }
    }

    if (LOG.isDebugEnabled()) LOG.debug("RADIO FIELD VALUE : " + values.toString());

    return String.join(",", values);
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
    String compareString;
    if (isFieldIndex(fieldValue)) {
      int index = getIndexFromFieldIndex(fieldValue);
      compareString = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_RADIO_FIELD_CHOICE_INDEX, index))).getAttribute(
        "checked");
    } else {
      compareString = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_RADIO_FIELD_CHOICE_LABEL, fieldValue))).getAttribute(
        "checked");
    }
    if (LOG.isDebugEnabled()) LOG.debug("RADIO FIELD COMPARISON : Field value [" + fieldValue + "] is checked [" + compareString + "]");

    return compareString.equals("true");
  }

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_RADIO_BUTTON_GROUP));
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {
    // NOOP
  }
}
