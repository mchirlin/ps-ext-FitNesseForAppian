package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;

public class TempoGridSelectAll extends TempoGrid implements Clickable {

  private static final Logger LOG = Logger.getLogger(TempoGridSelectAll.class);
  private static final String XPATH_RELATIVE_GRID_SELECT_ALL_CHECKBOX = Settings.getByConstant("xpathRelativeGridSelectAllColumn");

  public static TempoGridSelectAll getInstance(Settings settings) {
    return new TempoGridSelectAll(settings);
  }

  private TempoGridSelectAll(Settings settings) {
    super(settings);
  }

  @Override
  public void click(String... params) {
    String gridName = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("CLICK SELECT ALL [" + gridName + "]");

    try {
      WebElement grid = getWebElement(params);
      WebElement checkboxColumn = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_SELECT_ALL_CHECKBOX));
      clickElement(checkboxColumn);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Select All ", gridName);
    }
  }
}