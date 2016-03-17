package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoRecordType extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoRecordType.class);
  private static final String XPATH_ABSOLUTE_RECORD_TYPE_LINK = Settings.getByConstant("xpathAbsoluteRecordTypeLink");
  private static final String XPATH_ABSOLUTE_RECORD_TYPE_LINK_INDEX = "(" + XPATH_ABSOLUTE_RECORD_TYPE_LINK + ")[%d]";
  private static final String XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK = Settings.getByConstant("xpathAbsoluteRecordTypeUserFilterLink");

  public static WebElement getRecordType(String type, Settings s) {
    if (isFieldIndex(type)) {
      int rNum = getIndexFromFieldIndex(type);
      String rName = getFieldFromFieldIndex(type);
      return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_LINK_INDEX, rName, rNum)));
    } else {
      return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_LINK, type)));
    }
  }

  public static void click(String type, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK RECORD TYPE [" + type + "]");

    try {
      WebElement recordType = getRecordType(type, s);
      clickElement(recordType, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record Type", type);
    }
  }

  public static void waitFor(String type, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RECORD TYPE [" + type + "]");

    try {
      if (isFieldIndex(type)) {
        int rNum = getIndexFromFieldIndex(type);
        String rName = getFieldFromFieldIndex(type);
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_RECORD_TYPE_LINK_INDEX, rName, rNum))));
      }
      else {
        (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
          XPATH_ABSOLUTE_RECORD_TYPE_LINK, type))));
      }
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record Type", type);
    }
  }

  public static void clickOnUserFilter(String userFilter, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON USER FILTER [" + userFilter + "]");

    try {
      WebElement filter = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK, userFilter)));
      clickElement(filter, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "User Filter", userFilter);
    }
  }

  public static void waitForUserFilter(String userFilter, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR USER FILTER [" + userFilter + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK, userFilter))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "User Filter", userFilter);
    }
  }
}
