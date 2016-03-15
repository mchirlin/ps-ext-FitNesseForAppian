package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoRecord extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoRecord.class);
  private static final String XPATH_ABSOLUTE_RECORD_LINK = Settings.getByConstant("xpathAbsoluteRecordLink");
  private static final String XPATH_ABSOLUTE_RECORD_VIEW_LINK = Settings.getByConstant("xpathAbsoluteRecordViewLink");
  private static final String XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK = Settings.getByConstant("xpathAbsoluteRecordRelatedActionLink");

  public static void click(String itemName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK RECORD [" + itemName + "]");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName)));
      element.click();
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record", itemName);
    }
  }

  public static void waitFor(String itemName, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RECORD [" + itemName + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_RECORD_LINK, itemName))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName)));
      scrollIntoView(element, false, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record", itemName);
    }
  }

  public static boolean waitForReturn(String itemName, Integer timeout, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RECORD [" + itemName + "]");

    try {
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_RECORD_LINK, itemName))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName)));
      scrollIntoView(element, false, s);
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record", itemName);
    }
  }

  public static boolean waitForReturn(String itemName, Settings s) {
    return waitForReturn(itemName, s.getTimeoutSeconds(), s);
  }

  public static void refreshAndWaitFor(String itemName, Settings s) {
    int i = 0;
    while (i < s.getRefreshTimes()) {
      // If it is not the last refresh attempt don't throw error
      if (i < s.getRefreshTimes() - 1) {
        if (waitForReturn(itemName, s)) {
          break;
        }
        s.getDriver().navigate().refresh();
      } else {
        waitFor(itemName, s);
      }
      i++;
    }
  }

  public static void clickOnView(String view, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON RECORD VIEW [" + view + "]");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_VIEW_LINK, view)));
      element.click();
      waitForWorking(s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record View", view);
    }
  }

  public static void waitForView(String view, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RECORD VIEW [" + view + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_RECORD_VIEW_LINK, view))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record View", view);
    }
  }

  public static void clickOnRelatedAction(String relatedAction, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON RELATED ACTION [" + relatedAction + "]");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction)));
      element.click();
      waitForWorking(s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record Related Action", relatedAction);
    }
  }

  public static void waitForRelatedAction(String relatedAction, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR RELATED ACTION [" + relatedAction + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction)));
      scrollIntoView(element, s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record Related Action", relatedAction);
    }
  }

  public static boolean waitForRelatedActionReturn(String relatedAction, Integer timeout, Settings s) {
    try {
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction))));
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction)));
      scrollIntoView(element, s);
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "Record Related Action", relatedAction);
    }
  }

  public static boolean waitForRelatedActionReturn(String relatedAction, Settings s) {
    return waitForRelatedActionReturn(relatedAction, s.getTimeoutSeconds(), s);
  }

  public static void refreshAndWaitForRelatedAction(String relatedAction, Settings s) {
    int i = 0;
    while (i < s.getRefreshTimes()) {
      // If it is not the last refresh attempt don't throw error
      if (i < s.getRefreshTimes() - 1) {
        if (TempoRecord.waitForRelatedActionReturn(relatedAction, s)) {
          break;
        }
      } else {
        TempoRecord.waitForRelatedAction(relatedAction, s);
      }
    }
  }

}
