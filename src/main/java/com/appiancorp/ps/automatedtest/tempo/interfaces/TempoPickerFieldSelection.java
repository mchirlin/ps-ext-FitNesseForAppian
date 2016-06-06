package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.FieldLayoutWaitFor;

public class TempoPickerFieldSelection extends TempoPickerField implements FieldLayoutWaitFor {

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

  @Override
  public void waitFor(WebElement fieldLayout, String... params) {
    String fieldValue = params[0];

    String xpathLocator = getXpathLocator(fieldLayout);
    (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" +
      xpathLocator + ")" + xpathFormat(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION, fieldValue))));
  }
}
