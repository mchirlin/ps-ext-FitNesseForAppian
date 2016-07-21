package com.appiancorp.ps.automatedtest.tempo.news;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoNewsItemPosted extends TempoNewsItem implements WaitFor {

  private static final Logger LOG = Logger.getLogger(TempoNewsItem.class);
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_POSTED_AT = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemPostedAt");

  public static TempoNewsItemPosted getInstance(Settings settings) {
    return new TempoNewsItemPosted(settings);
  }

  protected TempoNewsItemPosted(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String newsText = getParam(0, params);
    String newsPostedAt = getParam(1, params);

    return xpathFormat(XPATH_ABSOLUTE_NEWS_ITEM_POSTED_AT, newsText, newsPostedAt);
  }

  @Override
  public void waitFor(String... params) {
    String newsText = getParam(0, params);
    String newsPostedAt = getParam(1, params);

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and POSTED AT [" + newsPostedAt + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item Posted at", newsText, newsPostedAt);
    }
  }

}
