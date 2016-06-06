package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoFormInstructions extends AppianObject implements WaitFor, Captureable {

  private static final Logger LOG = Logger.getLogger(TempoFormInstructions.class);
  private static final String XPATH_ABSOLUTE_FORM_INSTRUCTIONS = Settings.getByConstant("xpathAbsoluteFormInstructions");

  public static TempoFormInstructions getInstance(Settings settings) {
    return new TempoFormInstructions(settings);
  }

  private TempoFormInstructions(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    return XPATH_ABSOLUTE_FORM_INSTRUCTIONS;
  }

  @Override
  public String capture(String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("GET FORM INSTRUCTIONS");

    try {
      WebElement element = settings.getDriver().findElement(By.xpath(xpathFormat(getXpath(params))));
      return element.getText();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Get Form Instructions");
    }
  }

  @Override
  public void waitFor(String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR FORM INSTRUCTIONS");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Form Instructions");
    }
  }
}
