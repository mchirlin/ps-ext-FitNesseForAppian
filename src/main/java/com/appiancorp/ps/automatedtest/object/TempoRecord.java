package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRecord extends TempoObject {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoRecord.class);
    private static final String XPATH_RECORD = "//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/') and contains(text(), '%s')]";
    private static final String XPATH_RECORD_VIEW = "//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/')]/span[contains(text(), '%s')]";
    private static final String XPATH_RECORD_RELATED_ACTION = "//a[(starts-with(@class, 'aui-ActionLink') or starts-with(@class, 'gwt-Anchor')) and contains(text(),'%s')]";
    
    public static boolean click(String itemName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD, itemName)));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String itemName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_RECORD, itemName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD, itemName)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
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
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_VIEW, view)));
            element.click();
        } catch (StaleElementReferenceException ste) {
            //If getting a stale element, try again immediately
            clickOnView(view);
        }

        return true;
    }
    
    public static boolean waitForView(String view) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_RECORD_VIEW, view))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnRelatedAction(String relatedAction) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_RELATED_ACTION, relatedAction)));
        element.click();

        return true;
    }
    
    public static boolean waitForRelatedAction(String relatedAction) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_RECORD_RELATED_ACTION, relatedAction))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_RELATED_ACTION, relatedAction)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
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
