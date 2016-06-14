package com.appiancorp.ps.automatedtest.tempo.action;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoActionApplicationFilter extends AppianObject implements WaitFor, Clickable {

  private static final Logger LOG = Logger.getLogger(TempoActionApplicationFilter.class);
  private static final String XPATH_ABSOLUTE_ACTIONS_APP_FILTER_LINK = Settings.getByConstant("xpathAbsoluteActionsAppFilterLink");

  public static TempoActionApplicationFilter getInstance(Settings settings) {
    return new TempoActionApplicationFilter(settings);
  }

  private TempoActionApplicationFilter(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String filterName = params[0];

    return xpathFormat(XPATH_ABSOLUTE_ACTIONS_APP_FILTER_LINK, filterName);
  }

  @Override
  public void waitFor(String... params) {
    String filterName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR ACTION APP FILTER [" + filterName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Application Filter", filterName);
    }
  }

  @Override
  public void click(String... params) {
    String filterName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK ACTION APP FILTER [" + filterName + "]");
    try {
      WebElement filter = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(filter);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Application Filter", filterName);
    }
  }
}
