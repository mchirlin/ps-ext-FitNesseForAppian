package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoNewsItem {

    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String newsText) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]")));
        } catch (Exception e) {
            return false;
        }
    
        return true;
    }
    
    public static boolean refreshAndWaitFor(WebDriver driver, int timeOutSeconds, String newsText) {
        boolean present = false;

        int i=0;
        while (!present) {
            if (i >= 5) return false;
            
            driver.navigate().refresh();
            
            if (TempoNewsItem.waitFor(driver, timeOutSeconds, newsText)) {
                present = true;
                break;
            };
                            
            i++;
        }

        return true;
    }
    
    public static boolean waitForMoreInfo(WebDriver driver, int timeOutSeconds, String newsText) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/following-sibling::div[starts-with(@class, 'appian-feed-entry-metadata')]/descendant::a[contains(text(), 'More Info') or contains(text(), 'Hide Info')]")));
        } catch (Exception e) {
            return false;
        }
    
        return true;
    }
    
    public static boolean toggleMoreInfo(WebDriver driver, int timeOutSeconds, String newsText) {
        WebElement moreInfoLink = driver.findElement(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/following-sibling::div[starts-with(@class, 'appian-feed-entry-metadata')]/descendant::a[contains(text(), 'More Info') or contains(text(), 'Hide Info')]"));
        moreInfoLink.click();
        
        return true;
    }
    
    public static boolean waitForLabelAndValue(WebDriver driver, int timeOutSeconds, String newsText, String label, String value) {
        try {
            // With label
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/following-sibling::table/descendant::td[contains(text(), '"+label+"')]")));
            // With value
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/following-sibling::table/descendant::td[contains(text(), '"+value+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForLabelsAndValues(WebDriver driver, int timeOutSeconds, String newsText, String[] labels, String[] values) {
        for (int i = 0; i < labels.length; i ++) {            
            if (!TempoNewsItem.waitForLabelAndValue(driver, timeOutSeconds, newsText, labels[i], values[i])) return false;
        }
        
        return true;
    }
    
    public static boolean waitForTag(WebDriver driver, int timeOutSeconds, String newsText, String newsTag) {
        try {
            //Tagged with
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/parent::div/descendant::a[contains(text(), '"+newsTag+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForComment(WebDriver driver, int timeOutSeconds, String newsText, String newsComment) {
        try {
            //Tagged with
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/parent::div/descendant::div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsComment+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean refreshAndWaitForComment(WebDriver driver, int timeOutSeconds, String newsText, String newsComment) {
        boolean present = false;

        int i=0;
        while (!present) {
            if (i >= 120) return false;
            
            driver.navigate().refresh();
            
            if (TempoNewsItem.waitForComment(driver, timeOutSeconds, newsText, newsComment)) {
                present = true;
                break;
            };
                            
            i++;
        }

        return true;
    }
    
    public static boolean waitForPostedAt(WebDriver driver, int timeOutSeconds, String newsText, String newsPostedAt) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[starts-with(@class, 'appian-feed-entry-message') and contains(text(), '"+newsText+"')]/parent::div/descendant::a[contains(text(), '"+newsPostedAt+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
