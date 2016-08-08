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

public class TempoRecordGridColumn extends AppianObject implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoRecord.class);
  private static final String XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK = Settings.getByConstant("xpathAbsoluteRecordGridColumnSortLink");

  public static TempoRecordGridColumn getInstance(Settings settings) {
    return new TempoRecordGridColumn(settings);
  }

  private TempoRecordGridColumn(Settings settings) {
    super(settings);
  }

  @Override
  public String getXpath(String... params) {
    String columnName = getParam(0, params);

    return xpathFormat(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName);
  }

  @Override
  public void waitFor(String... params) {
    String columnName = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR COLUMN [" + columnName + "]");

    try {
      (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Record Column", columnName);
    }
  }

  @Override
  public void click(String... params) {
    String columnName = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON COLUMN [" + columnName + "]");

    try {
      WebElement column = settings.getDriver()
        .findElement(By.xpath(getXpath(params)));
      clickElement(column);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Click Record", columnName);
    }
  }
}
