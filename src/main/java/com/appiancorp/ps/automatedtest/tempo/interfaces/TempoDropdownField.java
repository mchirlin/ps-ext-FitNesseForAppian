package com.appiancorp.ps.automatedtest.tempo.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoDropdownField extends AbstractTempoField {

  private static final Logger LOG = Logger.getLogger(TempoDropdownField.class);
  // private static final String XPATH_ABSOLUTE_SELECT_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteSelectFieldLabel");
  private static final String XPATH_RELATIVE_SELECT_FIELD_INPUT = Settings.getByConstant("xpathRelativeSelectFieldInput");

  public static TempoDropdownField getInstance(Settings settings) {
    return new TempoDropdownField(settings);
  }

  private TempoDropdownField(Settings settings) {
    super(settings);
  }

  // @Override
  // public void waitFor(String... params) {
  // String fieldName = params[0];
  //
  // try {
  // (new WebDriverWait(settings.getDriver(),
  // settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathFormat(
  // XPATH_ABSOLUTE_SELECT_FIELD_LABEL, fieldName))));
  // } catch (Exception e) {
  // throw ExceptionBuilder.build(e, settings, "Wait for Field", fieldName);
  // }
  // }

  @Override
  public void populate(WebElement fieldLayout, String... params) {
    String fieldValue = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_SELECT_FIELD_INPUT));
    Select select = new Select(selectField);

    if (isFieldIndex(fieldValue)) {
      int index = getIndexFromFieldIndex(fieldValue);
      select.selectByIndex(index);
    } else {
      select.selectByVisibleText(fieldValue);
    }
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    List<String> values = new ArrayList<String>();
    WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_SELECT_FIELD_INPUT));
    Select select = new Select(selectField);
    List<WebElement> selects = select.getAllSelectedOptions();
    for (WebElement settings : selects) {
      values.add(settings.getText());
    }

    if (LOG.isDebugEnabled()) LOG.debug("SELECT FIELD VALUE : " + values.toString());

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
    WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_SELECT_FIELD_INPUT));
    Select select = new Select(selectField);
    List<WebElement> selectedOptions = select.getAllSelectedOptions();

    boolean selected = false;

    if (isFieldIndex(fieldValue)) {
      int index = getIndexFromFieldIndex(fieldValue);
      fieldValue = select.getAllSelectedOptions().get(index - 1).getText();
    }

    for (WebElement s : selectedOptions) {
      if (s.getText().contains(fieldValue)) {
        selected = true;
        break;
      }
    }

    if (LOG.isDebugEnabled()) LOG.debug("SELECT FIELD COMPARISON : Field value [" + fieldValue + "] was selected [" + selected + "]");

    return selected;
  }

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_SELECT_FIELD_INPUT));
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
