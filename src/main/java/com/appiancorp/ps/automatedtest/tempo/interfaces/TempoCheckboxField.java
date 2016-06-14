package com.appiancorp.ps.automatedtest.tempo.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoCheckboxField extends AbstractTempoField {

  private static final Logger LOG = Logger.getLogger(TempoCheckboxField.class);
  private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteCheckboxFieldLabel");
  private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_INDEX = Settings.getByConstant("xpathAbsoluteCheckboxFieldIndex");
  private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL_INDEX = "(" + XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL + ")[%d]";
  private static final String XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_LABEL = Settings.getByConstant("xpathRelativeCheckboxFieldChoiceLabel");
  private static final String XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_INDEX = Settings.getByConstant("xpathRelativeCheckboxFieldChoiceIndex");
  private static final String XPATH_RELATIVE_CHECKBOX_FIELD_INPUT = Settings.getByConstant("xpathRelativeCheckboxFieldInput");
  private static final String XPATH_RELATIVE_CHECKBOX_FIELD_INPUT_SPAN = Settings.getByConstant("xpathRelativeCheckboxFieldInputSpan");

  public static TempoCheckboxField getInstance(Settings settings) {
    return new TempoCheckboxField(settings);
  }

  protected TempoCheckboxField(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String fieldName = params[0];

    if (isFieldIndex(fieldName)) {
      int index = getIndexFromFieldIndex(fieldName);
      String name = getFieldFromFieldIndex(fieldName);
      if (StringUtils.isBlank(name)) {
        return xpathFormat(XPATH_ABSOLUTE_CHECKBOX_FIELD_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, index);
      } else {
        return xpathFormat(XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL_INDEX + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, name,
          index);
      }

    } else {
      return xpathFormat(XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL + TempoFieldFactory.XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT, fieldName);
    }
  }

  public String getXpathForCheckbox(String... params) {
    String fieldValue = params[0];

    if (isFieldIndex(fieldValue)) {
      int index = getIndexFromFieldIndex(fieldValue);
      return xpathFormat(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_INDEX, index);
    } else {
      return xpathFormat(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_LABEL, fieldValue);
    }
  }

  @Override
  public void waitFor(String... params) {
    String fieldName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + fieldName + "]");

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

    WebElement checkboxField = fieldLayout.findElement(By.xpath(getXpathForCheckbox(fieldValue)));
    checkboxField.click();
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    List<String> values = new ArrayList<String>();

    for (WebElement span : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_CHECKBOX_FIELD_INPUT_SPAN))) {
      if (span.findElement(By.tagName("input")).isSelected()) {
        values.add(span.findElement(By.tagName("label")).getText());
      }
    }

    if (LOG.isDebugEnabled()) LOG.debug("CHECKBOX FIELD VALUE : " + values.toString());

    return String.join(",", values);
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
    boolean checked;
    WebElement checkbox = fieldLayout.findElement(By.xpath(getXpathForCheckbox(params)));
    checked = checkbox.getAttribute("checked") == null ? false : true;

    if (LOG.isDebugEnabled()) LOG.debug("CONTAINS: Field value [" + fieldValue + "] is checked [" + checked + "]");

    return checked;
  }

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_CHECKBOX_FIELD_INPUT));
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {
    // TODO Auto-generated method stub
  }
}
