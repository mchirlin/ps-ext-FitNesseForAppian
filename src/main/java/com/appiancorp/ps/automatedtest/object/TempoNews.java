package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.object.TempoObject;

public class TempoNews extends TempoObject {
    
    private static final Logger LOG = Logger.getLogger(TempoNews.class);
    private static final String XPATH_ABSOLUTE_NEWS_ITEM = Settings.getByConstant("xpathAbsoluteNewsItem");
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LINK = XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsItemMoreInfoLink");
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LABEL = XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsItemMoreInfoLabel");
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_VALUE = XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsItemMoreInfoValue");
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG = XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsItemRecordTag");
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_COMMENT = XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsItemComment");
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_POSTED_AT = XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsItemPostedAt");
    private static final String XPATH_ABSOLUTE_NEWS_ITEM_DELETE_LINK = XPATH_ABSOLUTE_NEWS_ITEM + Settings.getByConstant("xpathConcatNewsItemDeleteLink");
    
    public static boolean waitFor(String newsText, Integer timeout, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM, newsText))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM, newsText)));
            scrollIntoView(element, false, s);
        } catch (TimeoutException e) {
            return false;
        }
    
        return true;
    }
    
    public static boolean waitFor(String newsText, Settings s) {
        return waitFor(newsText, s.getTimeoutSeconds(), s);
    }
    
    public static boolean refreshAndWaitFor(String newsText, Settings s) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > s.getRefreshTimes()) return false;
            
            if (TempoNews.waitFor(newsText, s)) {
                present = true;
                break;
            };
                            
            s.getDriver().navigate().refresh();
            i++;
        }
        
        return true;
    }
    
    public static boolean waitForMoreInfo(String newsText, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LINK, newsText))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LINK, newsText)));
            scrollIntoView(element, false, s);
        } catch (TimeoutException e) {
            return false;
        }
    
        return true;
    }
    
    public static boolean toggleMoreInfo(String newsText, Settings s) {
        WebElement moreInfoLink = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LINK, newsText)));
        moreInfoLink.click();
        
        return true;
    }
    
    public static boolean waitForLabelAndValue(String newsText, String label, String value, Settings s) {
        
        value = TempoObject.parseVariable(value, s);
        LOG.debug("LABEL ("+label+") and VALUE (" + value +")");
        try {
            // With label
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LABEL, newsText, label))));
            // With value
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_VALUE, newsText, value))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_MORE_INFO_LABEL, newsText, label)));
            scrollIntoView(element, false, s);
        } catch (TimeoutException e) {
            LOG.debug(e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public static boolean refreshAndWaitForTag(String newsText, String newsTag, Settings s) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i >= s.getRefreshTimes()) return false;
            
            s.getDriver().navigate().refresh();
            
            if (TempoNews.waitForTag(newsText, newsTag, s)) {
                present = true;
                break;
            };

            i++;
        }
       
        return true;
    }
    
    public static boolean waitForTag(String newsText, String newsTag, Settings s) {
        try {
            //Tagged with
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG, newsText, newsTag))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG, newsText, newsTag)));
            scrollIntoView(element, false, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForComment(String newsText, String newsComment, Settings s) {
        try {
            //Tagged with
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_COMMENT, newsText, newsComment))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_COMMENT, newsText, newsComment)));
            scrollIntoView(element, false, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean refreshAndWaitForComment(String newsText, String newsComment, Settings s) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i >= s.getRefreshTimes()) return false;
            
            s.getDriver().navigate().refresh();
            
            if (TempoNews.waitForComment(newsText, newsComment, s)) {
                present = true;
                break;
            };

            i++;
        }
       
        return true;
    }
    
    public static boolean waitForPostedAt(String newsText, String newsPostedAt, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_POSTED_AT, newsText, newsPostedAt))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_POSTED_AT, newsText, newsPostedAt)));
            scrollIntoView(element, false, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnRecordTag(String newsText, String recordTag, Settings s) {
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_RECORD_TAG, newsText, recordTag)));
        element.click();
        
        return true;
    }
    
    public static boolean deleteNewsPost(String newsText, Settings s) {
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_DELETE_LINK, newsText)));
        element.click();
        
        TempoButton.click("Yes", s);
        
        return true;
    }
    
    public static String getRegexForNewsPost(String regex, String newsText, Settings s) {
        String text = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM, newsText))).getText();
        LOG.debug("NEWS POST TEXT: " + text);
        return getRegexResults(regex, text);
    }
    
    public static String getRegexForNewsPostComment(String regex, String newsText, String newsComment, Settings s) {
        String text = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_NEWS_ITEM_COMMENT, newsText, newsComment))).getText();
        LOG.debug("NEWS POST TEXT: " + text);
        return getRegexResults(regex, text);
    }
}
