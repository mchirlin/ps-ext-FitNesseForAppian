package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutWaitForReturn;

public class TempoPickerFieldSelection extends TempoPickerField implements FieldLayoutWaitForReturn {

  @SuppressWarnings("unused")
  private static final Logger LOG = Logger.getLogger(TempoPickerFieldSelection.class);
  protected static final String XPATH_RELATIVE_PICKER_SELECTION = Settings.getByConstant("xpathRelativePickerSelection");
  protected static final String XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION = Settings.getByConstant("xpathRelativePickerSpecificSelection");
  protected static final String XPATH_RELATIVE_PICKER_SELECTION_REMOVE_LINK = Settings.getByConstant("xpathRelativePickerSelectionRemoveLink");
  protected static final String XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION_REMOVE_LINK = Settings.getByConstant("xpathRelativePickerSpecificSelectionRemoveLink");

  public static TempoPickerFieldSelection getInstance(Settings settings) {
    return new TempoPickerFieldSelection(settings);
  }

  private TempoPickerFieldSelection(Settings settings) {
    super(settings);
  }

  public String getXpath(WebElement fieldLayout, String... params) {
    String fieldValue = getParam(0, params);

    String xpathLocator = getXpathLocator(fieldLayout);
    return "(" + xpathLocator + ")" + xpathFormat(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION, fieldValue);
  }

  @Override
  public void waitFor(WebElement fieldLayout, String... params) {
    (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(
      fieldLayout, params))));
  }

  @Override
  public boolean waitForReturn(int timeout, WebElement fieldLayout, String... params) {
    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(fieldLayout,
        params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  @Override
  public boolean waitForReturn(WebElement fieldLayout, String... params) {
    return waitForReturn(settings.getNotPresentTimeoutSeconds(), fieldLayout, params);
  }
}
