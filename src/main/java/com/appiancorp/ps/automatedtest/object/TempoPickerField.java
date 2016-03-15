package com.appiancorp.ps.automatedtest.object;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoPickerField extends TempoField {

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

  public static void populate(WebElement fieldLayout, String fieldName, String fieldValue, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("POPULATION [" + fieldValue + "]");

    WebElement groupPickerField;

    waitForSuggestBox(fieldLayout, fieldName, s);
    groupPickerField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PICKER_INPUT));
    groupPickerField.click();
    groupPickerField.sendKeys(fieldValue);

    // Wait until the suggestions populate
    waitForSuggestion(fieldValue, s);
    WebElement suggestion = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_PICKER_SUGGESTION, fieldValue, fieldValue)));
    suggestion.click();
    waitForSelection(fieldLayout, fieldValue, s);
    unfocus(s);
  }

  public static String getValue(WebElement fieldLayout) {
    List<String> values = new ArrayList<String>();

    for (WebElement a : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_PICKER_SELECTION))) {
      values.add(a.getText());
    }

    if (LOG.isDebugEnabled()) LOG.debug("PICKER FIELD VALUE : " + values.toString());

    return String.join(",", values);
  }

  public static boolean contains(WebElement fieldLayout, String fieldValue) {
    try {
      fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION, fieldValue)));
    } catch (NoSuchElementException nse) {
      return false;
    }

    if (LOG.isDebugEnabled()) LOG.debug("USER PICKER FIELD COMPARISON : Field value " + fieldValue + " found");

    return true;
  }

  public static void clearOf(String fieldName, String[] fieldValues, Settings s) {
    WebElement fieldLayout = getFieldLayout(fieldName, s);
    clearOf(fieldLayout, fieldValues);
  }

  public static void clear(WebElement fieldLayout) {
    List<WebElement> xs = fieldLayout.findElements(By.xpath(XPATH_RELATIVE_PICKER_SELECTION_REMOVE_LINK));

    for (WebElement x : xs) {
      x.click();
    }
  }

  public static void clearOf(WebElement fieldLayout, String[] fieldValues) {
    for (String fieldValue : fieldValues) {
      WebElement x = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION_REMOVE_LINK, fieldValue)));
      x.click();
    }
  }

  private static void waitForSuggestion(String fieldValue, Settings s) {
    (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
      XPATH_ABSOLUTE_PICKER_SUGGESTION, fieldValue, fieldValue))));
  }

  private static void waitForSelection(WebElement fieldLayout, String fieldValue, Settings s) {
    String xpathLocator = getXpathLocator(fieldLayout);
    (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" +
      xpathLocator + ")" + String.format(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION, fieldValue))));
  }

  private static void waitForSuggestBox(WebElement fieldLayout, String fieldValue, Settings s) {
    String xpathLocator = getXpathLocator(fieldLayout);
    (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" +
      xpathLocator + ")" + String.format(XPATH_RELATIVE_PICKER_SUGGEST_BOX, fieldValue))));
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
