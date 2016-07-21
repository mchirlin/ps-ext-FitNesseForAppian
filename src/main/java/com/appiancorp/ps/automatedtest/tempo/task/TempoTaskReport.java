package com.appiancorp.ps.automatedtest.tempo.task;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoTaskReport extends AppianObject implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoTaskReport.class);
  private static final String XPATH_ABSOLUTE_TASK_REPORT_LINK = Settings.getByConstant("xpathAbsoluteTaskReportLink");

  public static TempoTaskReport getInstance(Settings settings) {
    return new TempoTaskReport(settings);
  }

  private TempoTaskReport(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String taskReport = getParam(0, params);

    return xpathFormat(XPATH_ABSOLUTE_TASK_REPORT_LINK, taskReport);
  }

  @Override
  public void waitFor(String... params) {
    String taskReport = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR TASK REPORT [" + taskReport + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Task Report", taskReport);
    }
  }

  @Override
  public void click(String... params) {
    String taskReport = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("CLICK TASK REPORT [" + taskReport + "]");

    try {
      WebElement report = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(report);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Task Report", taskReport);
    }
  }
}
