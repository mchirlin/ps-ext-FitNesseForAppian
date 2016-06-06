package com.appiancorp.ps.automatedtest.tempo.interfaces;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;
import com.appiancorp.ps.automatedtest.properties.Captureable;
import com.appiancorp.ps.automatedtest.properties.Populateable;
import com.appiancorp.ps.automatedtest.properties.PopulateableMultiple;
import com.appiancorp.ps.automatedtest.properties.RegexCaptureable;
import com.appiancorp.ps.automatedtest.properties.Verifiable;
import com.appiancorp.ps.automatedtest.properties.VerifiableMultiple;
import com.appiancorp.ps.automatedtest.properties.WaitFor;

public class TempoGridCell extends TempoGrid implements
  PopulateableMultiple, Populateable,
  VerifiableMultiple, Verifiable,
  Captureable, RegexCaptureable, WaitFor {

  private static final Logger LOG = Logger.getLogger(TempoGridCell.class);
  private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_LABEL +
    Settings.getByConstant("xpathConcatCellByGridLabelColumnLabel");
  private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_LABEL +
    Settings.getByConstant("xpathConcatCellByColumnIndex");
  private static final String XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_INDEX +
    Settings.getByConstant("xpathConcatCellByGridIndexColumnLabel");
  private static final String XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_INDEX +
    Settings.getByConstant("xpathConcatCellByColumnIndex");
  private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX +
    Settings.getByConstant("xpathConcatCellByGridLabelIndexColumnLabel");
  private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX +
    Settings.getByConstant("xpathConcatCellByGridLabelIndexColumnIndex");

  public static TempoGridCell getInstance(Settings settings) {
    return new TempoGridCell(settings);
  }

  protected TempoGridCell(Settings settings) {
    super(settings);
  }

  @Override
  public WebElement getWebElement(String... params) {
    WebElement cell = null;

    if (params.length == 1) {
      cell = settings.getDriver().findElement(By.xpath(super.getXpath(params)));
    } else if (params.length == 2) {
      String gridName = params[0];
      String rowNum = params[1];

      waitFor(gridName, "[1]", rowNum);
    } else {
      String gridName = params[0];
      String columnName = params[1];
      String rowNum = params[2];

      if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR GRID [" + gridName + "] COLUMN [" + columnName + "] ROW [" + rowNum + "]");

      try {
        int rNum = getIndexFromFieldIndex(rowNum);

        if (isFieldIndex(gridName)) {
          int gNum = getIndexFromFieldIndex(gridName);
          String gName = getFieldFromFieldIndex(gridName);

          if (StringUtils.isBlank(gName)) {
            if (isFieldIndex(columnName)) {
              // Using a columnNum
              int cNum = getIndexFromFieldIndex(columnName);
              cell = settings.getDriver().findElement(By.xpath(xpathFormat(
                XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_INDEX, gNum, rNum, cNum)));
            } else {
              // Using a columnName
              cell = settings.getDriver().findElement(By.xpath(xpathFormat(
                XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_LABEL, gNum, columnName, rNum)));
            }
          } else {
            if (isFieldIndex(columnName)) {
              // Using a columnNum
              int cNum = getIndexFromFieldIndex(columnName);
              cell = settings.getDriver().findElement(By.xpath(xpathFormat(
                XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_INDEX, gName, gNum, rNum, cNum)));

            } else {
              // Using a columnName
              cell = settings.getDriver().findElement(By.xpath(xpathFormat(
                XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_LABEL, gName, gNum, columnName, rNum)));
            }
          }
        } else {
          if (isFieldIndex(columnName)) {
            // Using a columnNum
            int cNum = getIndexFromFieldIndex(columnName);
            cell = settings.getDriver().findElement(By.xpath(xpathFormat(
              XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_INDEX, gridName, rNum, cNum)));

          } else {
            // Using a columnName
            cell = settings.getDriver().findElement(By.xpath(xpathFormat(
              XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_LABEL, gridName, columnName, rNum)));
          }
        }
      } catch (Exception e) {
        throw ExceptionBuilder.build(e, settings, "Grid Wait For", gridName, columnName, rowNum);
      }
    }
    scrollIntoView(cell);
    return cell;
  }

  @Override
  public String getXpath(String... params) {
    WebElement cell = getWebElement(params);
    return getXpathLocator(cell);
  }

  @Override
  public void populateMultiple(String[] fieldValues, String... params) {
    String gridName = params[0];
    String columnName = params[1];
    String rowNum = params[2];

    for (String fieldValue : fieldValues) {
      params = new String[] { gridName, columnName, rowNum, fieldValue };
      populate(params);
    }
  }

  @SuppressWarnings("unused")
  @Override
  public void populate(String... params) {
    String gridName = params[0];
    String columnName = params[1];
    String rowNum = params[2];
    String fieldValue = params[3];

    WebElement cell = getWebElement(params);
    TempoFieldFactory.getInstance(settings).populate(cell, "", fieldValue);
  }

  @Override
  public String capture(String... params) {
    WebElement cell = getWebElement(params);

    return TempoFieldFactory.getInstance(settings).capture(cell, "");
  }

  @Override
  public String regexCapture(String regex, Integer group, String... params) {
    WebElement cell = getWebElement(params);

    return TempoFieldFactory.getInstance(settings).regexCapture(cell, regex, group, "");
  }

  @Override
  public boolean containsMultiple(String[] fieldValues, String... params) {
    String gridName = params[0];
    String columnName = params[1];
    String rowNum = params[2];

    for (String fieldValue : fieldValues) {
      params = new String[] { gridName, columnName, rowNum, fieldValue };
      if (!contains(params)) return false;
    }

    return true;
  }

  @Override
  public boolean contains(String... params) {
    String gridName = params[0];
    String columnName = params[1];
    String rowNum = params[2];
    String fieldValue = params[3];

    WebElement cell = getWebElement(gridName, columnName, rowNum);
    if (!TempoFieldFactory.getInstance(settings).contains(cell, "", fieldValue)) return false;
    return true;
  }

  public void clear(String... params) {
    WebElement cell = getWebElement(params);
    TempoFieldFactory.getInstance(settings).clear(cell, "");
  }

  @Override
  public void waitFor(String... params) {
    (new WebDriverWait(settings.getDriver(), settings.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(params))));
  }
}