package com.appiancorp.ps.automatedtest.tempo.record;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoRecordTypeUserFilter extends AppianObject implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoRecordTypeUserFilter.class);
  private static final String XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK = Settings.getByConstant("xpathAbsoluteRecordTypeUserFilterLink");

  public static TempoRecordTypeUserFilter getInstance(Settings settings) {
    return new TempoRecordTypeUserFilter(settings);
  }

  private TempoRecordTypeUserFilter(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String userFilter = getParam(0, params);

    return xpathFormat(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK, userFilter);
  }

  @Override
  public void click(String... params) {
    String userFilter = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON USER FILTER [" + userFilter + "]");

    try {
      WebElement filter = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(filter);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "User Filter", userFilter);
    }
  }

  @Override
  public void waitFor(String... params) {
    String userFilter = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR USER FILTER [" + userFilter + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "User Filter", userFilter);
    }
  }
}
