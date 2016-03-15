package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoTask extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoTask.class);
  private static final String XPATH_ABSOLUTE_TASK_LINK = Settings.getByConstant("xpathAbsoluteTaskLink");
  private static final String XPATH_ABSOLUTE_TASK_DEADLINE = Settings.getByConstant("xpathAbsoluteTaskDeadline");
  private static final String XPATH_ABSOLUTE_TASK_REPORT_LINK = Settings.getByConstant("xpathAbsoluteTaskReportLink");

  public static void click(String taskName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK TASK [" + taskName + "]");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_LINK, taskName)));
      element.click();
      waitForWorking(s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Task", taskName);
    }
  }

  public static void waitFor(String taskName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR TASK [" + taskName + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_TASK_LINK, taskName))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_LINK, taskName)));
      scrollIntoView(element, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Task", taskName);
    }
  }

  public static boolean waitForReturn(String taskName, int timeout, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR TASK [" + taskName + "]");

    try {
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_TASK_LINK, taskName))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_LINK, taskName)));
      scrollIntoView(element, s);
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Task", taskName);
    }
  }

  public static boolean waitForReturn(String taskName, Settings s) {
    return waitForReturn(taskName, s.getTimeoutSeconds(), s);
  }

  public static void refreshAndWaitFor(String taskName, Settings s) {
    int i = 0;
    while (i < s.getRefreshTimes()) {
      // If it is not the last refresh attempt don't throw error
      if (i < s.getRefreshTimes() - 1) {
        if (waitForReturn(taskName, s)) {
          break;
        }
        s.getDriver().navigate().refresh();
      } else {
        waitFor(taskName, s);
      }
      i++;
    }
  }

  public static void waitForTaskReport(String taskReport, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR TASK REPORT [" + taskReport + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_TASK_REPORT_LINK, taskReport))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_REPORT_LINK, taskReport)));
      scrollIntoView(element, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Task Report", taskReport);
    }
  }

  public static void hasDeadlineOf(String taskName, String deadline, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("TASK [" + taskName + "] HAS DEADLINE [" + deadline + "]");

    try {
      s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_DEADLINE, taskName, deadline)));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Task deadline", taskName, deadline);
    }
  }

  public static void clickOnTaskReport(String taskReport, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK TASK REPORT [" + taskReport + "]");

    try {
      s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_TASK_REPORT_LINK, taskReport))).click();
      waitForWorking(s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Task Report", taskReport);
    }
  }
}
