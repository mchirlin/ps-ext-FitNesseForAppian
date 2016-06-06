package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;

public class TempoGridRowCount extends TempoGridNavigation implements Captureable {

  private static final Logger LOG = Logger.getLogger(TempoGridRowCount.class);

  public static TempoGridRowCount getInstance(Settings settings) {
    return new TempoGridRowCount(settings);
  }

  private TempoGridRowCount(Settings settings) {
    super(settings);
  }

  @Override
  public Integer capture(String... params) {
    String gridName = params[0];

    if (LOG.isDebugEnabled()) LOG.debug("GRID [" + gridName + "] ROW COUNT");

    try {
      WebElement grid = getWebElement(params);
      WebElement pagingLabel = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_PAGING_LABEL));
      String pagingLabelText = pagingLabel.getText();
      String rowCountStr = pagingLabelText.split("of", 2)[0];
      String[] rowCountStrArray = rowCountStr.trim().split("-", 2);
      int rowCount = Integer.parseInt(rowCountStrArray[1]) - Integer.parseInt(rowCountStrArray[0]) + 1;
      return rowCount;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Retrieving grid total count", gridName);
    }
  }

}