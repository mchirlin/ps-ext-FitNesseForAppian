package com.appiancorp.ps.automatedtest.object;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoGrid extends TempoField {

  private static final Logger LOG = Logger.getLogger(TempoGrid.class);
  private static final String XPATH_ABSOLUTE_GRID_BY_INDEX = Settings.getByConstant("xpathAbsoluteGridByIndex");
  private static final String XPATH_ABSOLUTE_GRID_BY_LABEL = Settings.getByConstant("xpathAbsoluteGridByLabel");
  private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX = "(" + XPATH_ABSOLUTE_GRID_BY_LABEL + ")[%d]";

  private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_LABEL +
    Settings.getByConstant("xpathConcatCellByGridLabelColumnLabel");
  private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_LABEL +
    Settings.getByConstant("xpathConcatCellByColumnIndex");
  private static final String XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_INDEX +
    Settings.getByConstant("xpathConcatCellByGridIndexColumnLabel");
  private static final String XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_INDEX +
    Settings.getByConstant("xpathConcatCellByColumnIndex");
  private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_LABEL = XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX +
    Settings.getByConstant("xpathConcatCellByGridLabelColumnLabel");
  private static final String XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_INDEX = XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX +
    Settings.getByConstant("xpathConcatCellByColumnIndex");

  private static final String XPATH_RELATIVE_GRID_CHECKBOX = Settings.getByConstant("xpathRelativeGridCheckbox");
  private static final String XPATH_RELATIVE_GRID_ADD_ROW_LINK = Settings.getByConstant("xpathRelativeGridAddRowLink");

  private static final String XPATH_RELATIVE_GRID_FIRST_PAGE_LINK = Settings.getByConstant("xpathRelativeGridFirstPageLink");
  private static final String XPATH_RELATIVE_GRID_PREVIOUS_PAGE_LINK = Settings.getByConstant("xpathRelativeGridPreviousPageLink");
  private static final String XPATH_RELATIVE_GRID_NEXT_PAGE_LINK = Settings.getByConstant("xpathRelativeGridNextPageLink");
  private static final String XPATH_RELATIVE_GRID_LAST_PAGE_LINK = Settings.getByConstant("xpathRelativeGridLastPageLink");

  private static final String XPATH_RELATIVE_GRID_COLUMN_LINK = Settings.getByConstant("xpathRelativeGridColumnLink");
  private static final String XPATH_RELATIVE_GRID_SELECT_ALL_CHECKBOX = Settings.getByConstant("xpathRelativeGridSelectAllColumn");
  private static final String XPATH_RELATIVE_GRID_PAGING_LABEL = Settings.getByConstant("xpathRelativeGridPagingLabel");

  public static WebElement getGrid(String gridName, Settings s) {
    WebElement grid = null;
    if (isFieldIndex(gridName)) {
      int gNum = getIndexFromFieldIndex(gridName);
      String gName = getFieldFromFieldIndex(gridName);
      if (StringUtils.isBlank(gName)) {
        grid = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_INDEX, gNum)));
      } else {
        grid = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX, gName, gNum)));
      }
    } else {
      grid = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL, gridName)));
    }

    scrollIntoView(grid, s);
    return grid;
  }

  public static WebElement getCell(String gridName, String columnName, String rowNum, Settings s) {
    WebElement cell = null;
    int rNum = getIndexFromFieldIndex(rowNum);

    if (isFieldIndex(gridName)) {
      int gNum = getIndexFromFieldIndex(gridName);
      String gName = getFieldFromFieldIndex(gridName);

      if (StringUtils.isBlank(gName)) {
        if (isFieldIndex(columnName)) {
          // Using a columnNum
          int cNum = getIndexFromFieldIndex(columnName);
          cell = s.getDriver().findElement(By.xpath(String.format(
            XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_INDEX, gNum, rNum, cNum)));
        } else {
          // Using a columnName
          cell = s.getDriver().findElement(By.xpath(String.format(
            XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_LABEL, gNum, columnName, rNum, gNum, columnName)));
        }
      } else {
        if (isFieldIndex(columnName)) {
          // Using a columnNum
          int cNum = getIndexFromFieldIndex(columnName);
          cell = s.getDriver().findElement(
            By.xpath(String.format(XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_INDEX, gName, gNum, rNum, cNum)));

        } else {
          // Using a columnName
          cell = s.getDriver().findElement(By.xpath(String.format(
            XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_LABEL, gName, gNum, columnName, rNum, gName, columnName)));
        }
      }
    } else {
      if (isFieldIndex(columnName)) {
        // Using a columnNum
        int cNum = getIndexFromFieldIndex(columnName);
        cell = s.getDriver().findElement(By.xpath(String.format(
          XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_INDEX, gridName, rNum, cNum)));

      } else {
        // Using a columnName
        cell = s.getDriver().findElement(By.xpath(String.format(
          XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_LABEL, gridName, columnName, rNum, gridName, columnName)));
      }
    }
    scrollIntoView(cell, s);
    return cell;
  }

  public static void populate(String gridName, String columnName, String rowNum, String[] fieldValues, Settings s) {
    for (String fieldValue : fieldValues) {
      WebElement cell = getCell(gridName, columnName, rowNum, s);
      populate(cell, null, fieldValue, s);
    }
  }

  public static void selectRow(String gridName, String rowNum, Settings s) {
    try {
      WebElement cell = getCell(gridName, "[1]", rowNum, s);
      WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CHECKBOX));
      clickElement(checkbox, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Grid Row Selection", gridName, rowNum);
    }
  }

  public static String getValue(String gridName, String columnName, String rowNum, Settings s) {
    WebElement cell = getCell(gridName, columnName, rowNum, s);

    return getValue(cell, null, s);
  }

  public static int getTotalCount(String gridName, Settings s) {
    try {
      WebElement grid = getGrid(gridName, s);
      WebElement pagingLabel = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_PAGING_LABEL));
      String pagingLabelText = pagingLabel.getText();
      String totalCountStr = pagingLabelText.split("of", 2)[1];
      int totalCount = Integer.parseInt(totalCountStr.trim());
      return totalCount;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Retrieving grid total count", gridName);
    }
  }

  public static int getRowCount(String gridName, Settings s) {
    try {
      WebElement grid = getGrid(gridName, s);
      WebElement pagingLabel = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_PAGING_LABEL));
      String pagingLabelText = pagingLabel.getText();
      String rowCountStr = pagingLabelText.split("of", 2)[0];
      String[] rowCountStrArray = rowCountStr.trim().split("-", 2);
      int rowCount = Integer.parseInt(rowCountStrArray[1]) - Integer.parseInt(rowCountStrArray[0]) + 1;
      if (LOG.isDebugEnabled()) LOG.debug("ROW COUNT " + rowCount);
      return rowCount;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Retrieving grid total count", gridName);
    }
  }

  public static boolean contains(String gridName, String columnName, String rowNum, String[] fieldValues, Settings s) {
    for (String fieldValue : fieldValues) {
      WebElement cell = getCell(gridName, columnName, rowNum, s);
      if (!contains(cell, null, fieldValue, s)) return false;
    }

    return true;
  }

  public static boolean verifyGridRowIsSelected(String gridName, String rowNum, Settings s) {
    try {
      WebElement cell = getCell(gridName, "[1]", rowNum, s);
      WebElement checkbox = cell.findElement(By.xpath(XPATH_RELATIVE_GRID_CHECKBOX));

      return checkbox.isSelected();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Grid row selected", gridName, rowNum);
    }
  }

  public static void clear(String gridName, String columnName, String rowNum, Settings s) {
    WebElement cell = getCell(gridName, columnName, rowNum, s);
    clear(cell, null, s);
  }

  public static void waitFor(String gridName, Settings s) {
    try {
      if (isFieldIndex(gridName)) {
        int gNum = getIndexFromFieldIndex(gridName);
        String gName = getFieldFromFieldIndex(gridName);

        if (StringUtils.isBlank(gName)) {
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds()))
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
              XPATH_ABSOLUTE_GRID_BY_INDEX, gNum))));
        } else {
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds()))
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
              XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX, gName, gNum))));
        }
      } else {
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_GRID_BY_LABEL, gridName))));
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Grid Wait For", gridName);
    }
  }

  public static void waitFor(String gridName, String rowNum, Settings s) {
    waitFor(gridName, "[1]", rowNum, s);
  }

  public static void waitFor(String gridName, String columnName, String rowNum, Settings s) {
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
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds()))
              .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
                XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_INDEX, gNum, rNum, cNum))));
          } else {
            // Using a columnName
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds()))
              .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
                XPATH_ABSOLUTE_GRID_BY_INDEX_CELL_BY_COLUMN_LABEL, gNum, columnName, rNum, gNum, columnName))));
          }
        } else {
          if (isFieldIndex(columnName)) {
            // Using a columnNum
            int cNum = getIndexFromFieldIndex(columnName);
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds()))
              .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
                XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_INDEX, gName, gNum, rNum, cNum))));

          } else {
            // Using a columnName
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds()))
              .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
                XPATH_ABSOLUTE_GRID_BY_LABEL_INDEX_CELL_BY_COLUMN_LABEL, gName, gNum, columnName, rNum, gName, columnName))));
          }
        }
      } else {
        if (isFieldIndex(columnName)) {
          // Using a columnNum
          int cNum = getIndexFromFieldIndex(columnName);
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds()))
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
              XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_INDEX, gridName, rNum, cNum))));

        } else {
          // Using a columnName
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds()))
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
              XPATH_ABSOLUTE_GRID_BY_LABEL_CELL_BY_COLUMN_LABEL, gridName, columnName, rNum, gridName, columnName))));
        }
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Grid Wait For", gridName, columnName, rowNum);
    }
  }

  public static void clickOnAddRowLink(String gridName, Settings s) {
    try {
      WebElement grid = getGrid(gridName, s);
      WebElement link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_ADD_ROW_LINK));
      clickElement(link, s);
    } catch (Exception e) {
      LOG.error("Click Add Row", e);
      throw ExceptionBuilder.build(e, s, "Click Add Row", gridName);
    }
  }

  public static void clickOnNavigationOption(String gridName, String navOption, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK GRID [" + gridName + "] NAVIGATION [" + navOption + "]");

    try {
      navOption = navOption.toLowerCase();

      WebElement grid = getGrid(gridName, s);
      WebElement link = null;

      switch (navOption) {
        case "first":
          link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_FIRST_PAGE_LINK));
          break;
        case "next":
          link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_NEXT_PAGE_LINK));
          break;
        case "previous":
          link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_PREVIOUS_PAGE_LINK));
          break;
        case "last":
          link = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_LAST_PAGE_LINK));
          break;
        default:
          throw new IllegalArgumentException("Invalid navigation option");
      }
      clickElement(link, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Click Navigation option", navOption);
    }
  }

  public static void sortByColumn(String gridName, String columnName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("SORT GRID [" + gridName + "] BY [" + columnName + "]");

    try {
      WebElement grid = getGrid(gridName, s);
      WebElement column = grid.findElement(By.xpath(String.format(XPATH_RELATIVE_GRID_COLUMN_LINK, columnName)));
      clickElement(column, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Sort Grid", columnName);
    }
  }

  public static void selectAll(String gridName, Settings s) {
    try {
      WebElement grid = getGrid(gridName, s);
      WebElement checkboxColumn = grid.findElement(By.xpath(XPATH_RELATIVE_GRID_SELECT_ALL_CHECKBOX));
      clickElement(checkboxColumn, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Select All ", gridName);
    }
  }
}