package com.appiancorp.ps.automatedtest.tempo.record;

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
import com.appiancorp.ps.automatedtest.properties.Refreshable;

public class TempoRecordRelatedAction extends AppianObject implements Clickable, Refreshable {

  private static final Logger LOG = Logger.getLogger(TempoRecordRelatedAction.class);
  private static final String XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK = Settings.getByConstant("xpathAbsoluteRecordRelatedActionLink");

  public static TempoRecordRelatedAction getInstance(Settings settings) {
    return new TempoRecordRelatedAction(settings);
  }

  private TempoRecordRelatedAction(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String relatedAction = params[0];

    return xpathFormat(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction);
  }

  @Override
  public void click(String... params) {
    String relatedAction = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON RELATED ACTION [" + relatedAction + "]");

    try {
      WebElement element = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(element);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record Related Action", relatedAction);
    }
  }

  @Override
  public void waitFor(String... params) {
    String relatedAction = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RELATED ACTION [" + relatedAction + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record Related Action", relatedAction);
    }
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String relatedAction = params[0];

    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record Related Action", relatedAction);
    }
  }

  @Override
  public boolean waitForReturn(String... params) {
    return waitForReturn(settings.getTimeoutSeconds(), params);
  }

  @Override
  public void refreshAndWaitFor(String... params) {
    int i = 0;
    while (i < settings.getRefreshTimes()) {
      // If it is not the last refresh attempt don't throw error
      if (i < settings.getRefreshTimes() - 1) {
        if (waitForReturn(params)) {
          break;
        }
      } else {
        waitFor(params);
      }
    }
  }
}
