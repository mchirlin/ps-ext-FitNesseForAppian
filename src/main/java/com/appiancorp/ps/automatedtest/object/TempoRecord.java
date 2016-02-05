package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;

public class TempoRecord extends TempoObject {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoRecord.class);
    private static final String XPATH_ABSOLUTE_RECORD_LINK = Metadata.getByConstant("xpathAbsoluteRecordLink");
    private static final String XPATH_ABSOLUTE_RECORD_VIEW_LINK = Metadata.getByConstant("xpathAbsoluteRecordViewLink");
    private static final String XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK = Metadata.getByConstant("xpathAbsoluteRecordRelatedActionLink");
    
    public static boolean click(String itemName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName)));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String itemName, Integer timeout) {
        try {
            (new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitFor(String itemName) {
        return waitFor(itemName, timeoutSeconds);
    }
    
    public static boolean refreshAndWaitFor(String itemName) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > refreshTimes) return false;
            
            if (TempoRecord.waitFor(itemName)) {
                present = true;
                break;
            };        

            driver.navigate().refresh();
            i++;
        }

        return true;
    }
    
    public static boolean clickOnView(String view) {
        try {
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_VIEW_LINK, view)));
            element.click();
        } catch (StaleElementReferenceException ste) {
            //If getting a stale element, try again immediately
            clickOnView(view);
        }

        return true;
    }
    
    public static boolean waitForView(String view) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_VIEW_LINK, view))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnRelatedAction(String relatedAction) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction)));
        element.click();

        return true;
    }
    
    public static boolean waitForRelatedAction(String relatedAction, Integer timeout) {
        try {
            (new WebDriverWait(driver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForRelatedAction(String relatedAction) {
        return waitForRelatedAction(relatedAction, timeoutSeconds);
    }
    
    public static boolean refreshAndWaitForRelatedAction(String relatedAction) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > refreshTimes) return false;
            
            if (TempoRecord.waitForRelatedAction(relatedAction)) {
                present = true;
                break;
            };
                            
            driver.navigate().refresh();
            i++;
        }

        return true;
    }
}
