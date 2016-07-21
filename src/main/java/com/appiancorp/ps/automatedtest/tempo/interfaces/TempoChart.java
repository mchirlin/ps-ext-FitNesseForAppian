package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;

public class TempoChart extends AppianObject implements WaitForReturn {

  private static final Logger LOG = Logger.getLogger(TempoChart.class);
  private static final String XPATH_ABSOLUTE_CHART_LABEL = Settings.getByConstant("xpathAbsoluteChartLabel");

  public TempoChart(Settings settings) {
    super(settings);
  }

  public static TempoChart getInstance(Settings settings) {
    return new TempoChart(settings);
  }

  @Override
  public void waitFor(String... params) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR TEMPO CHART");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds()))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Tempo Chart");
    }
  }

  @Override
  public String getXpath(String... params) {
    String chartName = getParam(0, params);
    return xpathFormat(XPATH_ABSOLUTE_CHART_LABEL, chartName);
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String chartName = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR CHART [" + chartName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "CHART", chartName);
    }
  }

  @Override
  public boolean waitForReturn(String... params) {
    return waitForReturn(settings.getTimeoutSeconds(), params);
  }

}
