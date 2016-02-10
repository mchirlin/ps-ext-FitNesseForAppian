package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoRecord extends TempoObject {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoRecord.class);
    private static final String XPATH_ABSOLUTE_RECORD_LINK = Settings.getByConstant("xpathAbsoluteRecordLink");
    private static final String XPATH_ABSOLUTE_RECORD_VIEW_LINK = Settings.getByConstant("xpathAbsoluteRecordViewLink");
    private static final String XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK = Settings.getByConstant("xpathAbsoluteRecordRelatedActionLink");
    private static final String XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK = Settings.getByConstant("xpathAbsoluteRecordGridColumnSortLink");
    private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS_NEXT = Settings.getByConstant("xpathAbsoluteRecordGridNavigationPreviousNext");
    private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST_LAST = Settings.getByConstant("xpathAbsoluteRecordGridNavigationFirstLast");
    
    public static boolean click(String itemName, Settings s) {
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName)));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String itemName, Integer timeout, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_LINK, itemName)));
            scrollIntoView(element, false, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitFor(String itemName, Settings s) {
        return waitFor(itemName, s.getTimeoutSeconds(), s);
    }
    
    public static boolean refreshAndWaitFor(String itemName, Settings s) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > s.getRefreshTimes()) return false;
            
            if (TempoRecord.waitFor(itemName, s)) {
                present = true;
                break;
            };        

            s.getDriver().navigate().refresh();
            i++;
        }

        return true;
    }
    
    public static boolean clickOnView(String view, Settings s) {
        try {
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_VIEW_LINK, view)));
            element.click();
        } catch (StaleElementReferenceException ste) {
            //If getting a stale element, try again immediately
            clickOnView(view, s);
        }

        return true;
    }
    
    public static boolean waitForView(String view, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_VIEW_LINK, view))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnRelatedAction(String relatedAction, Settings s) {
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction)));
        element.click();

        return true;
    }
    
    public static boolean waitForRelatedAction(String relatedAction, Integer timeout, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_RELATED_ACTION_LINK, relatedAction)));
            scrollIntoView(element, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitForRelatedAction(String relatedAction, Settings s) {
        return waitForRelatedAction(relatedAction, s.getTimeoutSeconds(), s);
    }
    
    public static boolean refreshAndWaitForRelatedAction(String relatedAction, Settings s) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > s.getRefreshTimes()) return false;
            
            if (TempoRecord.waitForRelatedAction(relatedAction, s)) {
                present = true;
                break;
            };
                            
            s.getDriver().navigate().refresh();
            i++;
        }

        return true;
    }

    public static boolean waitForRecordGridColumn(String columnName, Settings s){
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName)));
            scrollIntoView(element, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnRecordGridColumn(String columnName, Settings s){
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName)));
        element.click();
                
        return true;
    }
    
    public static boolean waitForRecordGridNavigation(String navOption, Settings s){
        //navOption can only be First, Last, Previous, Next
        try{
            switch(navOption){
            case "First": case "Last": 
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST_LAST, navOption))));
                WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST_LAST, navOption)));
                scrollIntoView(element, s);
                return true;
            case "Previous": case "Next":
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS_NEXT, navOption))));
                WebElement element2 = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS_NEXT, navOption)));
                scrollIntoView(element2, s);
                return true;
            default:
                return false;
            }
        }
        catch (TimeoutException e){
            return false;
        }
        
    }
    
    public static boolean clickOnRecordGridNavigation(String navOption, Settings s){
      //navOption can only be First, Last, Previous, Next
        switch(navOption){
        case "First": case "Last":
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST_LAST, navOption)));
            element.click();
            return true;
        case "Next": case "Previous":
            WebElement element2 = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS_NEXT, navOption)));
            element2.click();
            return true;
        default: 
            return false;
        }
    }

}
