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

public class TempoRecordView extends AppianObject implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoRecordView.class);
  private static final String XPATH_ABSOLUTE_RECORD_VIEW_LINK = Settings.getByConstant("xpathAbsoluteRecordViewLink");

  public static TempoRecordView getInstance(Settings settings) {
    return new TempoRecordView(settings);
  }

  private TempoRecordView(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String view = params[0];

    return xpathFormat(XPATH_ABSOLUTE_RECORD_VIEW_LINK, view);
  }

  @Override
  public void click(String... params) {
    String view = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON RECORD VIEW [" + view + "]");

    try {
      WebElement element = settings.getDriver().findElement(By.xpath(getXpath(params)));
      clickElement(element);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record View", view);
    }
  }

  @Override
  public void waitFor(String... params) {
    String view = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RECORD VIEW [" + view + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record View", view);
    }
  }
}
