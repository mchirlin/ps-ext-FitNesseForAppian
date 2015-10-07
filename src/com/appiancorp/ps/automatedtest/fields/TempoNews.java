package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.fields.TempoObject;

public class TempoNews extends TempoObject{
    
    private static final Logger LOG = Logger.getLogger(TempoNews.class);
    
    public static boolean waitFor(String newsText) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]")));
        } catch (Exception e) {
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
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/following-sibling::div[starts-with(@class, 'appian-feed-entry-metadata')]/descendant::a[contains(text(), 'More Info') or contains(text(), 'Hide Info')]")));
        } catch (Exception e) {
            return false;
        }
    
        return true;
    }
    
    public static boolean toggleMoreInfo(String newsText) {
        WebElement moreInfoLink = driver.findElement(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/following-sibling::div[starts-with(@class, 'appian-feed-entry-metadata')]/descendant::a[contains(text(), 'More Info') or contains(text(), 'Hide Info')]"));
        moreInfoLink.click();
        
        return true;
    }
    
    public static boolean waitForLabelAndValue(String newsText, String label, String value) {
        
        value = TempoObject.parseVariable(value);
        LOG.debug("LABEL : ("+label+") and VALUE (" + value +")");
        try {
            // With label
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/following-sibling::table/descendant::td[contains(text(), '"+label+"')]")));
            // With value
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/following-sibling::table/descendant::td[contains(text(), '"+value+"')]")));
        } catch (Exception e) {
            LOG.debug(e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public static boolean refreshAndWaitForLabelAndValueBetween(String newsText, String label, String value) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > refreshTimes) return false;
            
            if (TempoNews.waitForLabelAndValue(newsText, label, value)) {
                present = true;
                break;
            };
                            
            driver.navigate().refresh();
            i++;
        }

        return true;
    }
    
    public static boolean waitForLabelsAndValues(String newsText, String[] labels, String[] values) {
        for (int i = 0; i < labels.length; i ++) {            
            if (!TempoNews.waitForLabelAndValue(newsText, labels[i], values[i])) return false;
        }
        
        return true;
    }
    
    public static boolean waitForTag(String newsText, String newsTag) {
        try {
            //Tagged with
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/parent::div/descendant::a[contains(text(), '"+newsTag+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForComment(String newsText, String newsComment) {
        try {
            //Tagged with
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/parent::div/descendant::div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsComment+"')]")));
        } catch (Exception e) {
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
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/parent::div/descendant::a[contains(text(), '"+newsPostedAt+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
