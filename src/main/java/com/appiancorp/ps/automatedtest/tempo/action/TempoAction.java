package com.appiancorp.ps.automatedtest.tempo.action;

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
import com.appiancorp.ps.automatedtest.properties.Completeable;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;

public class TempoAction extends AppianObject implements WaitForReturn, Clickable, Completeable {

  private static final Logger LOG = Logger.getLogger(TempoAction.class);
  private static final String XPATH_ABSOLUTE_ACTION_LINK = Settings.getByConstant("xpathAbsoluteActionLink");
  private static final String XPATH_ABSOLUTE_ACTION_LINK_INDEX = "(" + XPATH_ABSOLUTE_ACTION_LINK + ")[%2$d]";

  public static TempoAction getInstance(Settings settings) {
    return new TempoAction(settings);
  }

  private TempoAction(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String action = params[0];

    if (isFieldIndex(action)) {
      int rNum = getIndexFromFieldIndex(action);
      String rName = getFieldFromFieldIndex(action);
      return xpathFormat(XPATH_ABSOLUTE_ACTION_LINK_INDEX, rName, rNum);
    } else {
      return xpathFormat(XPATH_ABSOLUTE_ACTION_LINK, action);
    }
  }

  @Override
  public void click(String... params) {
    String actionName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK ACTION [" + actionName + "]");

    try {
      WebElement action = settings.getDriver().findElement(By.xpath(getXpath(actionName)));
      clickElement(action);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Action click", actionName);
    }

  }

  @Override
  public void waitFor(String... params) {
    String actionName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR ACTION [" + actionName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Action wait for", actionName);
    }
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String actionName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR ACTION [" + actionName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Action wait for", actionName);
    }
  }

  @Override
  public boolean waitForReturn(String... params) {
    return waitForReturn(settings.getTimeoutSeconds(), params);
  }

  @Override
  public boolean complete(String... params) {
    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.textToBePresentInElementLocated(
        By.id("inlineConfirmMessage"), "Action completed successfully"));
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
