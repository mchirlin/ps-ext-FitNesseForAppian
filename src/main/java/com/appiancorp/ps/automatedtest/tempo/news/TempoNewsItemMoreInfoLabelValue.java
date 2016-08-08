package com.appiancorp.ps.automatedtest.tempo.news;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoNewsItemMoreInfoLabelValue extends TempoNewsItemMoreInfo implements WaitFor {

  private static final Logger LOG = Logger.getLogger(TempoNewsItemMoreInfoLabelValue.class);
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LABEL = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemMoreInfoLabel");
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_VALUE = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemMoreInfoValue");

  public static TempoNewsItemMoreInfoLabelValue getInstance(Settings settings) {
    return new TempoNewsItemMoreInfoLabelValue(settings);
  }

  private TempoNewsItemMoreInfoLabelValue(Settings settings) {
    super(settings);
  }

  @Override
  public void waitFor(String... params) {
    String newsText = getParam(0, params);
    String label = getParam(1, params);
    String value = getParam(2, params);

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR LABEL [" + label + "] and VALUE [" + value + "]");

    value = parseVariable(value);

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathFormat(
        XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LABEL, newsText, label))));
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathFormat(
        XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_VALUE, newsText, value))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "News Item Label and Value", newsText, label, value);
    }
  }
}
