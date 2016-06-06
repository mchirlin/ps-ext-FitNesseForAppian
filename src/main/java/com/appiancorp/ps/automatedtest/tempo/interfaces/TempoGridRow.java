package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import com.appiancorp.ps.automatedtest.properties.Countable;
import com.appiancorp.ps.automatedtest.properties.Verifiable;

public class TempoGridRow extends TempoGridCell implements Clickable, Verifiable, Countable {

  @SuppressWarnings("unused")
  private static final Logger LOG = Logger.getLogger(TempoGridRow.class);
  private static final String XPATH_RELATIVE_GRID_CHECKBOX = Settings.getByConstant("xpathRelativeGridCheckbox");
  private static final String XPATH_RELATIVE_GRID_ROW = Settings.getByConstant("xpathRelativeGridRow");

  public static TempoGridRow getInstance(Settings settings) {
    return new TempoGridRow(settings);
  }

  protected TempoGridRow(Settings settings) {
    super(settings);
  }

  @Override
  public void click(String... params) {
    String gridName = params[0];
    String rowNum = params[1];

    try {
      WebElement cell = settings.getDriver().findElement(By.xpath(getXpath(gridName, "[1]", rowNum)));
      WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CHECKBOX));
      clickElement(checkbox);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Grid Row Selection", gridName, rowNum);
    }
  }

  @Override
  public boolean contains(String... params) {
    String gridName = params[0];
    String rowNum = params[1];

    try {
      WebElement cell = settings.getDriver().findElement(By.xpath(getXpath(gridName, "[1]", rowNum)));
      WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CHECKBOX));

      return checkbox.isSelected();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Grid Row Selected", gridName, rowNum);
    }
  }

  @Override
  public Integer count(String... params) {
    String gridName = params[0];

    try {
      WebElement grid = settings.getDriver().findElement(By.xpath(getXpath(gridName)));
      Integer numRows = grid.findElements(By.xpath(XPATH_RELATIVE_GRID_ROW)).size();

      return numRows;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, settings, "Grid Row Count", gridName);
    }
  }
}