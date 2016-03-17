package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
// import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoRecordGrid extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoRecord.class);
  private static final String XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK = Settings.getByConstant("xpathAbsoluteRecordGridColumnSortLink");
  private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST = Settings.getByConstant("xpathAbsoluteRecordGridNavigationFirst");
  private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS = Settings.getByConstant("xpathAbsoluteRecordGridNavigationPrevious");
  private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_NEXT = Settings.getByConstant("xpathAbsoluteRecordGridNavigationNext");
  private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_LAST = Settings.getByConstant("xpathAbsoluteRecordGridNavigationLast");

  public static void waitForRecordGridColumn(String columnName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR COLUMN [" + columnName + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record Column", columnName);
    }
  }

  public static void clickOnRecordGridColumn(String columnName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON COLUMN [" + columnName + "]");

    try {
      WebElement column = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName)));
      clickElement(column, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Click Record", columnName);
    }
  }

  public static void waitForRecordGridNavigation(String navOption, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NAVIGATION [" + navOption + "]");

    try {
      navOption = navOption.toLowerCase();
      switch (navOption) {
        case "first":
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
            XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST)));
          break;
        case "previous":
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
            XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS)));
          break;
        case "next":
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
            XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_NEXT)));
          break;
        case "last":
          (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(
            XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_LAST)));
          break;
        default:
          throw new IllegalArgumentException("Invalid navigation option");
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Wait for Record Navigation", navOption);
    }

  }

  public static void clickOnRecordGridNavigation(String navOption, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON NAVIGATION [" + navOption + "]");

    try {
      navOption = navOption.toLowerCase();
      WebElement link = null;

      switch (navOption) {
        case "first":
          link = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST));
          break;
        case "previous":
          link = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS));
          break;
        case "next":
          link = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_NEXT));
          break;
        case "last":
          link = s.getDriver().findElement(By.xpath(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_LAST));
          break;
        default:
          throw new IllegalArgumentException("Invalid navigation option");
      }

      clickElement(link, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Click Record Navigation", navOption);
    }
  }

}
