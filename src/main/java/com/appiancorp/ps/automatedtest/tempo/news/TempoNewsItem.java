package com.appiancorp.ps.automatedtest.tempo.news;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clearable;
import com.appiancorp.ps.automatedtest.properties.Refreshable;
import com.appiancorp.ps.automatedtest.properties.RegexCaptureable;
import com.appiancorp.ps.automatedtest.tempo.interfaces.TempoButton;

public class TempoNewsItem extends AppianObject implements Refreshable, Clearable, RegexCaptureable {

  private static final Logger LOG = Logger.getLogger(TempoNewsItem.class);
  protected static final String XPATH_ABSOLUTE_NEWS_ITEM = Settings.getByConstant("xpathAbsoluteNewsItem");
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_DELETE_LINK = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemDeleteLink");

  public static TempoNewsItem getInstance(Settings settings) {
    return new TempoNewsItem(settings);
  }

  protected TempoNewsItem(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String newsText = params[0];

    return xpathFormat(XPATH_ABSOLUTE_NEWS_ITEM, newsText);
  }

  @Override
  public void waitFor(String... params) {
    String newsText = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + newsText + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item", newsText);
    }
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String newsText = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR REFRESH [" + newsText + "]");

    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item", newsText);
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
  public void clear(String... params) {
    String newsText = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("DELETE NEWS ITEM [" + newsText + "]");

    try {
      WebElement element = settings.getDriver().findElement(By.xpath(xpathFormat(XPATH_ABSOLUTE_NEWS_ITEM_DELETE_LINK, newsText)));
      element.click();
      TempoButton.getInstance(settings).click("Yes");
      waitForWorking();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item delete", newsText);
    }
  }

  @Override
  public String regexCapture(String regex, Integer group, String... params) {
    String newsText = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("NEWS ITEM [" + newsText + "] REGEX [" + regex + "]");

    try {
      String text = settings.getDriver().findElement(By.xpath(getXpath(params))).getText();
      return getRegexResults(regex, group, text);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item regex", newsText, regex);
    }
  }
}
