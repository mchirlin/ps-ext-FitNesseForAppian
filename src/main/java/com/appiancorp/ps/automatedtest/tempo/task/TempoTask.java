package com.appiancorp.ps.automatedtest.tempo.task;

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

public class TempoTask extends AppianObject implements Refreshable, Clickable, RegexCaptureable {

  private static final Logger LOG = Logger.getLogger(TempoTask.class);
  private static final String XPATH_ABSOLUTE_TASK_LINK = Settings.getByConstant("xpathAbsoluteTaskLink");
  private static final String XPATH_ABSOLUTE_TASK_LINK_INDEX = "(" + XPATH_ABSOLUTE_TASK_LINK + ")[%d]";

  public static TempoTask getInstance(Settings settings) {
    return new TempoTask(settings);
  }

  private TempoTask(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String taskName = params[0];

    if (isFieldIndex(taskName)) {
      int rNum = getIndexFromFieldIndex(taskName);
      String rName = getFieldFromFieldIndex(taskName);
      return xpathFormat(XPATH_ABSOLUTE_TASK_LINK_INDEX, rName, rNum);
    } else {
      return xpathFormat(XPATH_ABSOLUTE_TASK_LINK, taskName);
    }
  }

  @Override
  public void click(String... params) {
    String taskName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK TASK [" + taskName + "]");

    try {
      WebElement task = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(task);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Task", taskName);
    }
  }

  @Override
  public void waitFor(String... params) {
    String taskName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR TASK [" + taskName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Task", taskName);
    }
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String taskName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR TASK [" + taskName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Task", taskName);
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
    String taskName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("REGEX FOR TASK [" + regex + "]");

    try {
      String text = settings.getDriver().findElement(By.xpath(getXpath(params))).getText();
      return getRegexResults(regex, group, text);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Task name regex", taskName, regex);
    }
  }
}
