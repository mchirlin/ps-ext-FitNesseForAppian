package com.appiancorp.ps.automatedtest.tempo.record;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
// import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoRecordGridNavigation extends AppianObject implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoRecord.class);
  private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST = Settings.getByConstant("xpathAbsoluteRecordGridNavigationFirst");
  private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS = Settings.getByConstant("xpathAbsoluteRecordGridNavigationPrevious");
  private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_NEXT = Settings.getByConstant("xpathAbsoluteRecordGridNavigationNext");
  private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_LAST = Settings.getByConstant("xpathAbsoluteRecordGridNavigationLast");

  public static TempoRecordGridNavigation getInstance(Settings settings) {
    return new TempoRecordGridNavigation(settings);
  }

  private TempoRecordGridNavigation(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String navOption = params[0];

    navOption = navOption.toLowerCase();
    switch (navOption) {
      case "first":
        return XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST;
      case "previous":
        return XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS;
      case "next":
        return XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_NEXT;
      case "last":
        return XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_LAST;
      default:
        throw new IllegalArgumentException("Invalid navigation option");
    }
  }

  @Override
  public void waitFor(String... params) {
    String navOption = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NAVIGATION [" + navOption + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Wait for Record Navigation", navOption);
    }

  }

  @Override
  public void click(String... params) {
    String navOption = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON NAVIGATION [" + navOption + "]");

    try {
      navOption = navOption.toLowerCase();
      WebElement link = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(link);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Click Record Navigation", navOption);
    }
  }
}
