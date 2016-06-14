package com.appiancorp.ps.automatedtest.tempo;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Populateable;
import com.appiancorp.ps.automatedtest.properties.WaitForReturn;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordType;

public class TempoSearch extends AppianObject implements WaitForReturn, Populateable {

  private static final Logger LOG = Logger.getLogger(TempoRecordType.class);
  private static final String XPATH_ABSOLUTE_SEARCH_FIELD = Settings.getByConstant("xpathAbsoluteSearchField");
  private static final String XPATH_ABSOLUTE_FEED_ENTRY = Settings.getByConstant("xpathAbsoluteFeedEntry");

  public static TempoSearch getInstance(Settings settings) {
    return new TempoSearch(settings);
  }

  private TempoSearch(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    return XPATH_ABSOLUTE_SEARCH_FIELD;
  }

  @Override
  public void populate(String... params) {
    String searchValue = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("SEARCH FOR [" + searchValue + "]");

    try {
      WebElement fieldLayout = settings.getDriver().findElement(By.xpath(getXpath(params)));

      scrollIntoView(fieldLayout);
      fieldLayout.clear();
      fieldLayout.sendKeys(searchValue);
      fieldLayout.sendKeys(Keys.ENTER);
      waitForWorking();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Tempo Search", searchValue);
    }
  }

  @Override
  public void waitFor(String... params) {
    String type = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR SEARCH [" + type + "]");

    try {
      // Wait until the page is loaded before searching
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_ABSOLUTE_FEED_ENTRY)));
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Tempo Search");
    }
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String type = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR SEARCH [" + type + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Tempo Search");
    }
  }

  @Override
  public boolean waitForReturn(String... params) {
    return waitForReturn(settings.getTimeoutSeconds(), params);
  }
}
