package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;

public class TempoButton extends AppianObject implements WaitFor, WaitForReturn, Clickable {

  private static final Logger LOG = Logger.getLogger(TempoButton.class);
  private static final String XPATH_ABSOLUTE_BUTTON = Settings.getByConstant("xpathAbsoluteButton");
  private static final String XPATH_ABSOLUTE_BUTTON_INDEX = "(" + XPATH_ABSOLUTE_BUTTON + ")[%d]";

  public static TempoButton getInstance(Settings settings) {
    return new TempoButton(settings);
  }

  private TempoButton(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String button = params[0];

    if (isFieldIndex(button)) {
      int rNum = getIndexFromFieldIndex(button);
      String rName = getFieldFromFieldIndex(button);
      return xpathFormat(XPATH_ABSOLUTE_BUTTON_INDEX, rName, rNum);
    } else {
      return xpathFormat(XPATH_ABSOLUTE_BUTTON, button);
    }
  }

  @Override
  public void waitFor(String... params) {
    String buttonName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR BUTTON [" + buttonName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Button", buttonName);
    }
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String buttonName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR BUTTON [" + buttonName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Button", buttonName);
    }
  }

  @Override
  public boolean waitForReturn(String... params) {
    return waitForReturn(settings.getTimeoutSeconds(), params);
  }

  @Override
  public void click(String... params) {
    String buttonName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK BUTTON [" + buttonName + "]");

    try {
      WebElement button = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(button);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Click Button", buttonName);
    }
  }
}
