package com.appiancorp.ps.automatedtest.tempo.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoPickerField extends AbstractTempoField {

  private static final Logger LOG = Logger.getLogger(TempoPickerField.class);
  protected static final String XPATH_ABSOLUTE_PICKER_LABEL = Settings.getByConstant("xpathAbsolutePickerLabel");
  protected static final String XPATH_RELATIVE_PICKER_INPUT = Settings.getByConstant("xpathRelativePickerInput");
  protected static final String XPATH_ABSOLUTE_PICKER_SUGGESTION = Settings.getByConstant("xpathAbsolutePickerSuggestion");
  protected static final String XPATH_RELATIVE_PICKER_SELECTION = Settings.getByConstant("xpathRelativePickerSelection");
  protected static final String XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION = Settings.getByConstant("xpathRelativePickerSpecificSelection");
  protected static final String XPATH_RELATIVE_PICKER_SELECTION_REMOVE_LINK = Settings.getByConstant("xpathRelativePickerSelectionRemoveLink");
  protected static final String XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION_REMOVE_LINK = Settings.getByConstant("xpathRelativePickerSpecificSelectionRemoveLink");
  protected static final String XPATH_RELATIVE_PICKER_SUGGEST_BOX = Settings.getByConstant("xpathRelativePickerSuggestBox");
  protected static final String XPATH_RELATIVE_INPUT_OR_SELECTION = "(" + XPATH_RELATIVE_PICKER_INPUT + " | " +
    XPATH_RELATIVE_PICKER_SELECTION + ")";

  public static TempoPickerField getInstance(Settings settings) {
    return new TempoPickerField(settings);
  }

  protected TempoPickerField(Settings settings) {
    super(settings);
  }

  @Override
  public void populate(WebElement fieldLayout, String... params) {
    String fieldName = getParam(0, params);
    String fieldValue = getParam(1, params);

    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    WebElement groupPickerField;

    waitForSuggestBox(fieldLayout, fieldName);
    groupPickerField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PICKER_INPUT));
    groupPickerField.click();
    groupPickerField.sendKeys(fieldValue);

    // Wait until the suggestions populate
    TempoPickerFieldSuggestion.getInstance(settings).waitFor(fieldValue);
    WebElement suggestion = settings.getDriver().findElement(
      By.xpath(xpathFormat(XPATH_ABSOLUTE_PICKER_SUGGESTION, fieldValue, fieldValue)));
    suggestion.click();
    TempoPickerFieldSelection.getInstance(settings).waitFor(fieldLayout, fieldValue);
  }

  @Override
  public String capture(WebElement fieldLayout, String... params) {
    List<String> values = new ArrayList<String>();

    for (WebElement a : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_PICKER_SELECTION))) {
      values.add(a.getText());
    }

    if (LOG.isDebugEnabled()) LOG.debug("PICKER FIELD VALUE : " + values.toString());

    return String.join(",", values);
  }

  @Override
  public boolean contains(WebElement fieldLayout, String... params) {
    String fieldValue = getParam(0, params);

    if (TempoPickerFieldSelection.getInstance(settings).waitForReturn(fieldLayout, fieldValue)) {
      if (LOG.isDebugEnabled()) LOG.debug("USER PICKER FIELD COMPARISON : FIELD VALUE [" + fieldValue + "] FOUND");
      return true;
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("USER PICKER FIELD COMPARISON : FIELD VALUE [" + fieldValue + "] NOT FOUND");

      return false;
    }
  }

  @Override
  public void clear(WebElement fieldLayout, String... params) {
    List<WebElement> xs = fieldLayout.findElements(By.xpath(XPATH_RELATIVE_PICKER_SELECTION_REMOVE_LINK));

    for (WebElement x : xs) {
      x.click();
    }
  }

  public void clearOf(WebElement fieldLayout, String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("PICKER FIELD CLEAR OF : " + String.join(", ", params));

    for (int i = 0; i < params.length; i++) {
      WebElement x = fieldLayout.findElement(By.xpath(xpathFormat(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION_REMOVE_LINK, params[i])));
      x.click();
    }
  }

  private void waitForSuggestBox(WebElement fieldLayout, String fieldValue) {
    String xpathLocator = getXpathLocator(fieldLayout);
    (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" +
      xpathLocator + ")" + xpathFormat(XPATH_RELATIVE_PICKER_SUGGEST_BOX, fieldValue))));
  }

  public static boolean isType(WebElement fieldLayout) {
    try {
      fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT_OR_SELECTION));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
