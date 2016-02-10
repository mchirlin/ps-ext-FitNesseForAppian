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
    private static final String XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK = Metadata.getByConstant("xpathAbsoluteRecordGridColumnSortLink");
    private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS_NEXT = Metadata.getByConstant("xpathAbsoluteRecordGridNavigationPreviousNext");
    private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST_LAST = Metadata.getByConstant("xpathAbsoluteRecordGridNavigationFirstLast");
    
    public static boolean click(String itemName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName)));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String itemName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName)));
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
    
    public static boolean waitForRelatedAction(String relatedAction) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction)));
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

    public static boolean waitForRecordGridColumn(String columnName){
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnRecordGridColumn(String columnName){
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName)));
        element.click();
                
        return true;
    }
    
    public static boolean waitForRecordGridNavigation(String navOption){
        //navOption can only be First, Last, Previous, Next
        try{
            switch(navOption){
            case "First": case "Last": 
                (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST_LAST, navOption))));
                WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST_LAST, navOption)));
                scrollIntoView(element);
                return true;
            case "Previous": case "Next":
                (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS_NEXT, navOption))));
                WebElement element2 = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS_NEXT, navOption)));
                scrollIntoView(element2);
                return true;
            default:
                return false;
            }
        }
        catch (TimeoutException e){
            return false;
        }
        
    }
    
    public static boolean clickOnRecordGridNavigation(String navOption){
      //navOption can only be First, Last, Previous, Next
        switch(navOption){
        case "First": case "Last":
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST_LAST, navOption)));
            element.click();
            return true;
        case "Next": case "Previous":
            WebElement element2 = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS_NEXT, navOption)));
            element2.click();
            return true;
        default: 
            return false;
        }
    }

}
