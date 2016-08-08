package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoCheckboxFieldOption extends TempoCheckboxField implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoCheckboxFieldOption.class);
  private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION = Settings.getByConstant("xpathAbsoluteCheckboxFieldOption");
  private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION_INDEX = "(" + XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION + ")[%2$d]";

  public static TempoCheckboxFieldOption getInstance(Settings settings) {
    return new TempoCheckboxFieldOption(settings);
  }

  private TempoCheckboxFieldOption(Settings settings) {
    super(settings);
  }

  public WebElement getOptionLayout(String optionName) {
    if (isFieldIndex(optionName)) {
      String oName = getFieldFromFieldIndex(optionName);
      int index = getIndexFromFieldIndex(optionName);
      return settings.getDriver().findElement(By.xpath(xpathFormat(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION_INDEX, oName, index)));
    } else {
      return settings.getDriver().findElement(By.xpath(xpathFormat(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION, optionName)));
    }
  }

  public void click(String... params) {
    String optionName = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("CHECKBOX OPTION CLICK [" + optionName + "]");

    try {
      WebElement element = getOptionLayout(optionName);
      clickElement(element);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Checkbox option", optionName);
    }
  }

  public void waitFor(String... params) {
    String optionName = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("CHECKBOX OPTION WAIT FOR [" + optionName + "]");

    try {
      if (isFieldIndex(optionName)) {
        String oName = getFieldFromFieldIndex(optionName);
        int index = getIndexFromFieldIndex(optionName);
        (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
          .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathFormat(
            XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION_INDEX, oName, index))));
      } else {
        (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
          .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathFormat(
            XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION, optionName))));
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Checkbox option", optionName);
    }
  }
}
