package com.appiancorp.ps.automatedtest.object;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoCheckboxField extends TempoField {

  private static final Logger LOG = Logger.getLogger(TempoCheckboxField.class);
  private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteCheckboxFieldLabel");
  private static final String XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_LABEL = Settings.getByConstant("xpathRelativeCheckboxFieldChoiceLabel");
  private static final String XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_INDEX = Settings.getByConstant("xpathRelativeCheckboxFieldChoiceIndex");
  private static final String XPATH_RELATIVE_CHECKBOX_FIELD_INPUT = Settings.getByConstant("xpathRelativeCheckboxFieldInput");
  private static final String XPATH_RELATIVE_CHECKBOX_FIELD_INPUT_SPAN = Settings.getByConstant("xpathRelativeCheckboxFieldInputSpan");
  private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION = Settings.getByConstant("xpathAbsoluteCheckboxFieldOption");
  private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION_INDEX = "(" + XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION + ")[%d]";

  public static void populate(String fieldName, String fieldValue, Settings s) {
    WebElement fieldLayout = getFieldLayout(fieldName, s);
    populate(fieldLayout, fieldName, fieldValue, s);
  }

  public static void populate(WebElement fieldLayout, String fieldValue, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    WebElement checkboxField;
    if (isFieldIndex(fieldValue)) {
      int index = getIndexFromFieldIndex(fieldValue);
      checkboxField = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_INDEX, index)));
    } else {
      checkboxField = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_LABEL, fieldValue)));
    }
    checkboxField.click();
  }

  public static void waitFor(String fieldName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + fieldName + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL, fieldName))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Field", fieldName);
    }
  }

  public static String getValue(WebElement fieldLayout) {
    List<String> values = new ArrayList<String>();

    for (WebElement span : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_CHECKBOX_FIELD_INPUT_SPAN))) {
      if (span.findElement(By.tagName("input")).isSelected()) {
        values.add(span.findElement(By.tagName("label")).getText());
      }
    }

    if (LOG.isDebugEnabled()) LOG.debug("CHECKBOX FIELD VALUE : " + values.toString());

    return String.join(",", values);
  }

  public static WebElement getOptionLayout(String optionName, Settings s) {
    if (isFieldIndex(optionName)) {
      String oName = getFieldFromFieldIndex(optionName);
      int index = getIndexFromFieldIndex(optionName);
      return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION_INDEX, oName, index)));
    } else {
      return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION, optionName)));
    }
  }

  public static boolean contains(WebElement fieldLayout, String fieldValue) {
    // For read-only
    try {
      return TempoField.contains(fieldLayout, fieldValue);
    } catch (Exception e) {
    }

    // For editable
    boolean checked;
    WebElement field;
    if (isFieldIndex(fieldValue)) {
      int index = getIndexFromFieldIndex(fieldValue);
      field = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_INDEX, index)));
    } else {
      field = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_LABEL, fieldValue)));
    }
    checked = field.getAttribute("checked") == null ? false : true;

    if (LOG.isDebugEnabled()) LOG.debug("CONTAINS: Field value [" + fieldValue + "] is checked [" + checked + "]");

    return checked;
  }

  public static void clickOption(String optionName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CHECKBOX OPTION CLICK [" + optionName + "]");

    try {
      WebElement element = getOptionLayout(optionName, s);
      clickElement(element, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Checkbox option", optionName);
    }
  }

  public static void waitForOption(String optionName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CHECKBOX OPTION WAIT FOR [" + optionName + "]");

    try {
      if (isFieldIndex(optionName)) {
        String oName = getFieldFromFieldIndex(optionName);
        int index = getIndexFromFieldIndex(optionName);
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION_INDEX, oName, index))));
      } else {
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION, optionName))));
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Checkbox option", optionName);
    }
  }

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_CHECKBOX_FIELD_INPUT));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
