package com.appiancorp.ps.automatedtest.tempo.news;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.Refreshable;

public class TempoNewsItemTag extends TempoNewsItem implements Clickable, Refreshable {

  private static final Logger LOG = Logger.getLogger(TempoNewsItemTag.class);
  protected static final String XPATH_ABSOLUTE_NEWS_ITEM = Settings.getByConstant("xpathAbsoluteNewsItem");
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemRecordTag");

  public static TempoNewsItemTag getInstance(Settings settings) {
    return new TempoNewsItemTag(settings);
  }

  protected TempoNewsItemTag(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String newsText = params[0];
    String recordTag = params[1];

    return xpathFormat(XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG, newsText, recordTag);
  }

  @Override
  public void waitFor(String... params) {
    String newsText = params[0];
    String recordTag = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and TAG [" + recordTag + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item Tag", newsText, recordTag);
    }
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String newsText = params[0];
    String recordTag = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and TAG [" + recordTag + "]");

    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item Tag", newsText, recordTag);
    }
  }

  @Override
  public void click(String... params) {
    String newsText = params[0];
    String recordTag = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON NEWS ITEM [" + newsText + "] and RECORD TAG [" + recordTag + "]");

    try {
      WebElement tag = settings.getDriver().findElement(By.xpath(xpathFormat(XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG, newsText, recordTag)));
      clickElement(tag);
    } catch (Exception e) {
      LOG.error("News Item Record tag", e);
      throw ExceptionBuilder.build(e, settings, "News Item Record tag", newsText, recordTag);
    }
  }
}
