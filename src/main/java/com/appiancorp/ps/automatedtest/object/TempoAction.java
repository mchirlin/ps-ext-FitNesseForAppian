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
  private static final String XPATH_ABSOLUTE_ACTIONS_APP_FILTER_LINK = Settings.getByConstant("xpathAbsoluteActionsAppFilterLink");

  public static void click(String actionName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK ACTION [" + actionName + "]");

    try {
      WebElement action = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_ACTION_LINK, actionName)));
      clickElement(action, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Action click", actionName);
    }

  }

  public static void clickOnAppFilter(String filterName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK ACTION APP FILTER [" + filterName + "]");
    try {
      WebElement filter = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_ACTIONS_APP_FILTER_LINK, filterName)));
      clickElement(filter, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Filter click", filterName);
    }

  }

  public static void waitFor(String actionName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR ACTION [" + actionName + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_ACTION_LINK, actionName))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Action wait for", actionName);
    }
  }

  public static boolean waitForReturn(String actionName, Integer timeout, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR ACTION [" + actionName + "]");

    try {
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_ACTION_LINK, actionName))));
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

  public static void waitForAppFilter(String appFilter, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR ACTIONS APPLICATION FILTER [" + appFilter + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_ACTIONS_APP_FILTER_LINK, appFilter))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Applications Filter", appFilter);
    }
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
