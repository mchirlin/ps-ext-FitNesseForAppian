package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRecordItem extends TempoObject{

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoRecordItem.class);
    private static final String XPATH_RECORD_ITEM = "//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/') and contains(text(), '%s')]";
    private static final String XPATH_RECORD_FACET = "//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/')]/span[contains(text(), '%s')]";
    private static final String XPATH_RECORD_RELATED_ACTION = "//a[starts-with(@class, 'aui-ActionLink') and contains(text(),'%s')]";
    
    public static boolean click(String itemName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_ITEM, itemName)));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String itemName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_RECORD_ITEM, itemName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_ITEM, itemName)));
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
            
            if (TempoRecordItem.waitFor(itemName)) {
                present = true;
                break;
            };        

            driver.navigate().refresh();
            i++;
        }

        return true;
    }
    
    public static boolean clickOnFacet(String facetName) {
        try {
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_FACET, facetName)));
            element.click();
        } catch (StaleElementReferenceException ste) {
            //If getting a stale element, try again immediately
            clickOnFacet(facetName);
        }

        return true;
    }
    
    public static boolean waitForFacet(String facetName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_RECORD_FACET, facetName))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnRelatedAction(String relatedActionName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_RELATED_ACTION, relatedActionName)));
        element.click();

        return true;
    }
    
    public static boolean waitForRelatedAction(String relatedActionName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_RECORD_RELATED_ACTION, relatedActionName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_RELATED_ACTION, relatedActionName)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean refreshAndWaitForRelatedAction(String relatedActionName) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > refreshTimes) return false;
            
            if (TempoRecordItem.waitForRelatedAction(relatedActionName)) {
                present = true;
                break;
            };
                            
            driver.navigate().refresh();
            i++;
        }

        return true;
    }
}
