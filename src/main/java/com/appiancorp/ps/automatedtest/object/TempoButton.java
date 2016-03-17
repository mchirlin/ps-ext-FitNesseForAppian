package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoButton extends AppianObject {
  private static final Logger LOG = Logger.getLogger(TempoButton.class);
  private static final String XPATH_ABSOLUTE_BUTTON = Settings.getByConstant("xpathAbsoluteButton");

  public static void click(String buttonName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK BUTTON [" + buttonName + "]");

    try {
      WebElement button = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_BUTTON, buttonName)));
      clickElement(button, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Click Button", buttonName);
    }
  }

  public static void waitFor(String buttonName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR BUTTON [" + buttonName + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_BUTTON, buttonName))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Button", buttonName);
    }
  }

  public static boolean waitForReturn(String buttonName, Integer timeout, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR BUTTON [" + buttonName + "]");

    try {
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_BUTTON, buttonName))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Button", buttonName);
    }
  }
}
