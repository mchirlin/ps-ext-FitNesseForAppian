package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.object.TempoObject;

public class TempoNews extends TempoObject{
    
    private static final Logger LOG = Logger.getLogger(TempoNews.class);
    private static final String XPATH_NEWS_ITEM = "//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '%s')]";
    private static final String XPATH_NEWS_ITEM_MORE_INFO = XPATH_NEWS_ITEM + "/following-sibling::div[starts-with(@class, 'appian-feed-entry-metadata')]/descendant::a[contains(text(), 'More Info') or contains(text(), 'Hide Info')]";
    private static final String XPATH_NEWS_ITEM_LABEL = XPATH_NEWS_ITEM + "/following-sibling::table/descendant::td[contains(text(), '%s')]";
    private static final String XPATH_NEWS_ITEM_VALUE = XPATH_NEWS_ITEM + "/following-sibling::table/descendant::td[contains(text(), '%s')]";
    private static final String XPATH_NEWS_ITEM_TAG = XPATH_NEWS_ITEM + "/parent::div/descendant::a[contains(text(), '%s')]";
    private static final String XPATH_NEWS_ITEM_COMMENT = XPATH_NEWS_ITEM + "/parent::div/descendant::div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '%s')]";
    private static final String XPATH_NEWS_ITEM_POSTED_AT = XPATH_NEWS_ITEM + "/parent::div/descendant::a[contains(text(), '%s')]";
    
    public static boolean waitFor(String newsText) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NEWS_ITEM, newsText))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_NEWS_ITEM, newsText)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
    
        return true;
    }
    
    public static boolean refreshAndWaitFor(String newsText) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > refreshTimes) return false;
            
            if (TempoNews.waitFor(newsText)) {
                present = true;
                break;
            };
                            
            driver.navigate().refresh();
            i++;
        }
        
        return true;
    }
    
    public static boolean waitForMoreInfo(String newsText) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NEWS_ITEM_MORE_INFO, newsText))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_NEWS_ITEM_MORE_INFO, newsText)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
    
        return true;
    }
    
    public static boolean toggleMoreInfo(String newsText) {
        WebElement moreInfoLink = driver.findElement(By.xpath(String.format(XPATH_NEWS_ITEM_MORE_INFO, newsText)));
        moreInfoLink.click();
        
        return true;
    }
    
    public static boolean waitForLabelAndValue(String newsText, String label, String value) {
        
        value = TempoObject.parseVariable(value);
        LOG.debug("LABEL ("+label+") and VALUE (" + value +")");
        try {
            // With label
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NEWS_ITEM_LABEL, newsText, label))));
            // With value
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NEWS_ITEM_VALUE, newsText, value))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_NEWS_ITEM_LABEL, newsText, label)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            LOG.debug(e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForTag(String newsText, String newsTag) {
        try {
            //Tagged with
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NEWS_ITEM_TAG, newsText, newsTag))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_NEWS_ITEM_TAG, newsText, newsTag)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForComment(String newsText, String newsComment) {
        try {
            //Tagged with
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NEWS_ITEM_COMMENT, newsText, newsComment))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_NEWS_ITEM_COMMENT, newsText, newsComment)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean refreshAndWaitForComment(String newsText, String newsComment) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i >= refreshTimes) return false;
            
            driver.navigate().refresh();
            
            if (TempoNews.waitForComment(newsText, newsComment)) {
                present = true;
                break;
            };

            i++;
        }
       
        return true;
    }
    
    public static boolean waitForPostedAt(String newsText, String newsPostedAt) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_NEWS_ITEM_POSTED_AT, newsText, newsPostedAt))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_NEWS_ITEM_POSTED_AT, newsText, newsPostedAt)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static String getRegexForNewsPost(String regex, String newsText) {
        String text = driver.findElement(By.xpath(String.format(XPATH_NEWS_ITEM, newsText))).getText();
        LOG.debug("NEWS POST TEXT: " + text);
        return getRegexResults(regex, text);
    }
    
    public static String getRegexForNewsPostComment(String regex, String newsText, String newsComment) {
        String text = driver.findElement(By.xpath(String.format(XPATH_NEWS_ITEM_COMMENT, newsText, newsComment))).getText();
        LOG.debug("NEWS POST TEXT: " + text);
        return getRegexResults(regex, text);
    }
}