package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoRadioFieldOption extends TempoRadioField implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoRadioFieldOption.class);
  private static final String XPATH_ABSOLUTE_RADIO_FIELD_OPTION = Settings.getByConstant("xpathAbsoluteRadioFieldOption");
  private static final String XPATH_ABSOLUTE_RADIO_FIELD_OPTION_INDEX = "(" + XPATH_ABSOLUTE_RADIO_FIELD_OPTION + ")[%2$d]";

  public static TempoRadioFieldOption getInstance(Settings settings) {
    return new TempoRadioFieldOption(settings);
  }

  private TempoRadioFieldOption(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String optionName = params[0];

    if (isFieldIndex(optionName)) {
      String oName = getFieldFromFieldIndex(optionName);
      int index = getIndexFromFieldIndex(optionName);
      return xpathFormat(XPATH_ABSOLUTE_RADIO_FIELD_OPTION_INDEX, oName, index);
    } else {
      return xpathFormat(XPATH_ABSOLUTE_RADIO_FIELD_OPTION, optionName);
    }
  }

  @Override
  public void click(String... params) {
    String optionName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("RADIO BUTTON OPTION CLICK : " + optionName);

    try {
      WebElement option = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(option);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Radio option", optionName);
    }
  }

  @Override
  public void waitFor(String... params) {
    String optionName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RADIO BUTTON OPTION CLICK : " + optionName);

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Radio option", optionName);
    }
  }
}
