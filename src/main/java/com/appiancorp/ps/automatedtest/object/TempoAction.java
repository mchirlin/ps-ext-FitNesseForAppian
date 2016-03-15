package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoAction extends AppianObject {
  private static final Logger LOG = Logger.getLogger(TempoAction.class);
  private static final String XPATH_ABSOLUTE_ACTION_LINK = Settings.getByConstant("xpathAbsoluteActionLink");

  public static void click(String actionName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK ACTION [" + actionName + "]");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_ACTION_LINK, actionName)));
      element.click();
      waitForWorking(s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Action click", actionName);
    }

  }

  public static void waitFor(String actionName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR ACTION [" + actionName + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_ACTION_LINK, actionName))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_ACTION_LINK, actionName)));
      scrollIntoView(element, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Action wait for", actionName);
    }
  }

  public static boolean waitForReturn(String actionName, Integer timeout, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR ACTION [" + actionName + "]");

    try {
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_ACTION_LINK, actionName))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_ACTION_LINK, actionName)));
      scrollIntoView(element, s);
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Action wait for", actionName);
    }
  }

  public static boolean waitForReturn(String actionName, Settings s) {
    return waitForReturn(actionName, s.getTimeoutSeconds(), s);
  }

  public static boolean isCompleted(Settings s) {
    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.textToBePresentInElementLocated(
        By.id("inlineConfirmMessage"), "Action completed successfully"));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
