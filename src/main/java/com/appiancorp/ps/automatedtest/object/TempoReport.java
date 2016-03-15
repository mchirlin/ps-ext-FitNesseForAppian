package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoReport extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoReport.class);
  private static final String XPATH_ABSOLUTE_REPORT_LINK = Settings.getByConstant("xpathAbsoluteReportLink");

  public static void click(String reportName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK REPORT [" + reportName + "]");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_REPORT_LINK, reportName)));
      element.click();
      waitForWorking(s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Report", reportName);
    }

  }

  public static void waitFor(String reportName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + reportName + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_REPORT_LINK, reportName))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_REPORT_LINK, reportName)));
      scrollIntoView(element, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Report", reportName);
    }
  }

  public static boolean waitForReturn(String reportName, Integer timeout, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + reportName + "]");

    try {
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_REPORT_LINK, reportName))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_REPORT_LINK, reportName)));
      scrollIntoView(element, s);
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Report", reportName);
    }
  }

  public static boolean waitForReturn(String reportName, Settings s) {
    return waitForReturn(reportName, s.getTimeoutSeconds(), s);
  }
}
