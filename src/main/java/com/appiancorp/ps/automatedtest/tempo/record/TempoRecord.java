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
import com.appiancorp.ps.automatedtest.properties.RegexCaptureable;

public class TempoRecord extends AppianObject implements Clickable, Refreshable, RegexCaptureable {

  private static final Logger LOG = Logger.getLogger(TempoRecord.class);
  private static final String XPATH_ABSOLUTE_RECORD_LINK = Settings.getByConstant("xpathAbsoluteRecordLink");
  private static final String XPATH_ABSOLUTE_RECORD_LINK_INDEX = "(" + XPATH_ABSOLUTE_RECORD_LINK + ")[%2$d]";

  public static TempoRecord getInstance(Settings settings) {
    return new TempoRecord(settings);
  }

  private TempoRecord(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String record = params[0];

    if (isFieldIndex(record)) {
      int rNum = getIndexFromFieldIndex(record);
      String rName = getFieldFromFieldIndex(record);
      return xpathFormat(XPATH_ABSOLUTE_RECORD_LINK_INDEX, rName, rNum);
    } else {
      return xpathFormat(XPATH_ABSOLUTE_RECORD_LINK, record);
    }
  }

  @Override
  public void click(String... params) {
    String itemName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK RECORD [" + itemName + "]");

    try {
      WebElement element = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(element);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record", itemName);
    }
  }

  @Override
  public void waitFor(String... params) {
    String itemName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RECORD [" + itemName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record", itemName);
    }
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String itemName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RECORD [" + itemName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record", itemName);
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
        settings.getDriver().navigate().refresh();
      } else {
        waitFor(params);
      }
      i++;
    }
  }

  @Override
  public String regexCapture(String regex, Integer group, String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("REGEX FOR RECORD NAME [" + regex + "]");

    try {
      String text = settings.getDriver().findElement(By.xpath(getXpath(params))).getText();
      if (LOG.isDebugEnabled()) LOG.debug("RECORD NAME [" + text + "]");
      return getRegexResults(regex, group, text);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record name regex", regex);
    }
  }
}
