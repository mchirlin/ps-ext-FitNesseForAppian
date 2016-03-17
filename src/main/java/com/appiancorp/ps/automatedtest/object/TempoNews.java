package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.exception.ExceptionBuilder;

public class TempoNews extends AppianObject {

  private static final Logger LOG = Logger.getLogger(TempoNews.class);
  private static final String XPATH_ABSOLUTE_NEWS_ITEM = Settings.getByConstant("xpathAbsoluteNewsItem");
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LINK = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemMoreInfoLink");
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LABEL = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemMoreInfoLabel");
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_VALUE = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemMoreInfoValue");
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemRecordTag");
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_COMMENT = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemComment");
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_POSTED_AT = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemPostedAt");
  private static final String XPATH_ABSOLUTE_NEWS_ITEM_DELETE_LINK = XPATH_ABSOLUTE_NEWS_ITEM +
    Settings.getByConstant("xpathConcatNewsItemDeleteLink");

  public static void waitFor(String newsText, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR [" + newsText + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_NEWS_ITEM, newsText))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item", newsText);
    }
  }

  public static boolean waitForReturn(String newsText, int timeout, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR REFRESH [" + newsText + "]");

    try {
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_NEWS_ITEM, newsText))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item", newsText);
    }
  }

  public static boolean waitForReturn(String newsText, Settings s) {
    return waitForReturn(newsText, s.getTimeoutSeconds(), s);
  }

  public static void refreshAndWaitFor(String newsText, Settings s) {
    int i = 0;
    while (i < s.getRefreshTimes()) {
      // If it is not the last refresh attempt don't throw error
      if (i < s.getRefreshTimes() - 1) {
        if (waitForReturn(newsText, s)) {
          break;
        }
        s.getDriver().navigate().refresh();
      } else {
        waitFor(newsText, s);
      }
      i++;
    }
  }

  public static void waitForMoreInfo(String newsText, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR MORE INFO [" + newsText + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LINK, newsText))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item More Info", newsText);
    }
  }

  public static void toggleMoreInfo(String newsText, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("TOGGLE MORE INFO [" + newsText + "]");

    WebElement moreInfoLink = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LINK, newsText)));
    clickElement(moreInfoLink, s);
  }

  public static void waitForLabelAndValue(String newsText, String label, String value, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR LABEL [" + label + "] and VALUE [" + value + "]");

    value = AppianObject.parseVariable(value, s);

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LABEL, newsText, label))));
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_VALUE, newsText, value))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item Label and Value", newsText, label, value);
    }
  }

  public static void waitForTag(String newsText, String newsTag, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and TAG [" + newsTag + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG, newsText, newsTag))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item Tag", newsText, newsTag);
    }
  }

  public static boolean waitForTagReturn(String newsText, String newsTag, int timeout, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and TAG [" + newsTag + "]");

    try {
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG, newsText, newsTag))));
      return true;
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item Tag", newsText, newsTag);
    }
  }

  public static boolean waitForTagReturn(String newsText, String newsTag, Settings s) {
    return waitForTagReturn(newsText, newsTag, s.getTimeoutSeconds(), s);
  }

  public static void refreshAndWaitForTag(String newsText, String newsTag, Settings s) {
    int i = 0;
    while (i < s.getRefreshTimes()) {
      // If it is not the last refresh attempt don't throw error
      if (i < s.getRefreshTimes() - 1) {
        if (waitForTagReturn(newsText, newsTag, s)) {
          break;
        }
        s.getDriver().navigate().refresh();
      } else {
        waitForTag(newsText, newsTag, s);
      }
      i++;
    }
  }

  public static void waitForComment(String newsText, String newsComment, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and COMMENT [" + newsComment + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_NEWS_ITEM_COMMENT, newsText, newsComment))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item Comment", newsText, newsComment);
    }
  }

  public static boolean waitForCommentReturn(String newsText, String newsComment, int timeout, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and COMMENT [" + newsComment + "]");

    try {
      (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_NEWS_ITEM_COMMENT, newsText, newsComment))));
    } catch (TimeoutException e) {
      return false;
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item Comment", newsText, newsComment);
    }

    return true;
  }

  public static boolean waitForCommentReturn(String newsText, String newsComment, Settings s) {
    return waitForCommentReturn(newsText, newsComment, s.getTimeoutSeconds(), s);
  }

  public static void refreshAndWaitForComment(String newsText, String newsComment, Settings s) {
    int i = 0;
    while (i < s.getRefreshTimes()) {
      // If it is not the last refresh attempt don't throw error
      if (i < s.getRefreshTimes() - 1) {
        if (waitForCommentReturn(newsText, newsComment, s)) {
          break;
        }
        s.getDriver().navigate().refresh();
      } else {
        waitForComment(newsText, newsComment, s);
      }
      i++;
    }
  }

  public static void waitForPostedAt(String newsText, String newsPostedAt, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("WAIT FOR NEWS ITEM [" + newsText + "] and POSTED AT [" + newsPostedAt + "]");

    try {
      (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(
        XPATH_ABSOLUTE_NEWS_ITEM_POSTED_AT, newsText, newsPostedAt))));
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item Posted at", newsText, newsPostedAt);
    }
  }

  public static void clickOnRecordTag(String newsText, String recordTag, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("CLICK ON NEWS ITEM [" + newsText + "] and RECORD TAG [" + recordTag + "]");

    try {
      WebElement tag = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG, newsText, recordTag)));
      clickElement(tag, s);
    } catch (Exception e) {
      LOG.error("News Item Record tag", e);
      throw ExceptionBuilder.build(e, s, "News Item Record tag", newsText, recordTag);
    }
  }

  public static void deleteNewsPost(String newsText, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("DELETE NEWS ITEM [" + newsText + "]");

    try {
      WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_DELETE_LINK, newsText)));
      element.click();
      TempoButton.click("Yes", s);
      waitForWorking(s);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item delete", newsText);
    }
  }

  public static String getRegexForNewsPost(String regex, Integer group, String newsText, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("NEWS ITEM [" + newsText + "] REGEX [" + regex + "]");

    try {
      String text = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM, newsText))).getText();
      return getRegexResults(regex, group, text);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item regex", newsText, regex);
    }
  }

  public static String getRegexForNewsPostComment(String regex, Integer group, String newsText, String newsComment, Settings s) {
    if (LOG.isDebugEnabled()) LOG.debug("NEWS ITEM [" + newsText + "] COMMENT [" + newsComment + "] REGEX [" + regex + "]");

    try {
      String text = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_COMMENT, newsText, newsComment))).getText();
      return getRegexResults(regex, group, text);
    } catch (Exception e) {
      throw ExceptionBuilder.build(e, s, "News Item regex", newsText, regex);
    }
  }
}
