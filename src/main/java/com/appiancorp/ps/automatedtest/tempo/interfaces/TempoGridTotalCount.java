package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;

public class TempoGridTotalCount extends TempoGridNavigation implements Captureable {

  private static final Logger LOG = Logger.getLogger(TempoGridTotalCount.class);

  public static TempoGridTotalCount getInstance(Settings settings) {
    return new TempoGridTotalCount(settings);
  }

  private TempoGridTotalCount(Settings settings) {
    super(settings);
  }

  @Override
  public Integer capture(String... params) {
    String gridName = getParam(0, params);

    if (LOG.isDebugEnabled()) LOG.debug("GRID [" + gridName + "] TOTAL COUNT");

    try {
      WebElement grid = getWebElement(gridName);
      WebElement pagingLabel = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_PAGING_LABEL));
      String pagingLabelText = pagingLabel.getText();
      String totalCountStr = pagingLabelText.split("of", 2)[1];
      int totalCount = Integer.parseInt(totalCountStr.trim());
      return totalCount;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Retrieving grid total count", gridName);
    }
  }

}