package com.appiancorp.ps.automatedtest.tempo.news;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Refreshable;
import com.appiancorp.ps.automatedtest.properties.RegexCaptureable;

public class TempoNewsItemComment extends TempoNewsItem implements Refreshable, RegexCaptureable {

  private static final Logger LOG = Logger.getLogger(TempoNewsItemComment.class);
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_COMMENT = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemComment");

  public static TempoNewsItemComment getInstance(Settings settings) {
    return new TempoNewsItemComment(settings);
  }

  protected TempoNewsItemComment(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String newsText = params[0];
    String newsComment = params[1];

    return xpathFormat(XPATH_ABSOLUTE_NEWS_ITEM_COMMENT, newsText, newsComment);
  }

  @Override
  public void waitFor(String... params) {
    String newsText = params[1];
    String newsComment = params[2];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and COMMENT [" + newsComment + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item Comment", newsText, newsComment);
    }
  }

  @Override
  public boolean waitForReturn(int timeout, String... params) {
    String newsText = params[0];
    String newsComment = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and COMMENT [" + newsComment + "]");

    try {
      (new WebDriverWait(settings.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item Comment", newsText, newsComment);
    }

    return true;
  }

  @Override
  public String regexCapture(String regex, Integer group, String... params) {
    String newsText = params[0];
    String newsComment = params[1];

    if (LOG.isDebugEnabled()) LOG.debug("NEWS ITEM [" + newsText + "] COMMENT [" + newsComment + "] REGEX [" + regex + "]");

    try {
      String text = settings.getDriver().findElement(By.xpath(getXpath(params))).getText();
      return getRegexResults(regex, group, text);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item regex", newsText, regex);
    }
  }
}
