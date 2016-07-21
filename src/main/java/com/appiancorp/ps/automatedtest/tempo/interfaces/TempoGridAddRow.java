package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoGridAddRow extends TempoGrid implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoGridAddRow.class);

  private static final String XPATH_RELATIVE_GRID_ADD_ROW_LINK = Settings.getByConstant("xpathRelativeGridAddRowLink");

  public static TempoGridAddRow getInstance(Settings settings) {
    return new TempoGridAddRow(settings);
  }

  private TempoGridAddRow(Settings settings) {
    super(settings);
  }

  public void click(String... params) {
    String gridName = getParam(0, params);

    try {
      WebElement grid = settings.getDriver().findElement(By.xpath(getXpath(params)));
      WebElement link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_ADD_ROW_LINK));
      clickElement(link);
    } catch (Exception e) {
      LOG.error("Click Add Row", e);
      throw ExceptionBuilder.build(e, settings, "Click Add Row", gridName);
    }
  }

}