package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoPickerFieldSuggestion extends TempoPickerField implements WaitFor {

  @SuppressWarnings("unused")
  private static final Logger LOG = Logger.getLogger(TempoPickerFieldSuggestion.class);
  protected static final String XPATH_ABSOLUTE_PICKER_SUGGESTION = Settings.getByConstant("xpathAbsolutePickerSuggestion");

  public static TempoPickerFieldSuggestion getInstance(Settings settings) {
    return new TempoPickerFieldSuggestion(settings);
  }

  private TempoPickerFieldSuggestion(Settings settings) {
    super(settings);
  }

  @Override
  public void waitFor(String... params) {
    String fieldValue = params[0];

    (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathFormat(
      XPATH_ABSOLUTE_PICKER_SUGGESTION, fieldValue))));
  }
}
