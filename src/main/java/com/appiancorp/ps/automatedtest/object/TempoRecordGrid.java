package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoRecordGrid extends TempoObject {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoRecord.class);
    private static final String XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK = Settings.getByConstant("xpathAbsoluteRecordGridColumnSortLink");
    private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS_NEXT = Settings.getByConstant("xpathAbsoluteRecordGridNavigationPreviousNext");
    private static final String XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST_LAST = Settings.getByConstant("xpathAbsoluteRecordGridNavigationFirstLast");
    
    public static boolean waitForRecordGridColumn(String columnName, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName)));
            scrollIntoView(element, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnRecordGridColumn(String columnName, Settings s) {
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_COLUMN_SORT_LINK, columnName)));
        element.click();
        waitForWorking(s);
                
        return true;
    }
    
    public static boolean waitForRecordGridNavigation(String navOption, Settings s) {
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
    
    public static boolean clickOnRecordGridNavigation(String navOption, Settings s) {
        WebElement link = null;
        
        switch(navOption){
            case "First": case "Last":
                link = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_FIRST_LAST, navOption)));
   
                break;
            case "Next": case "Previous":
                link = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_GRID_NAVIGATION_PREVIOUS_NEXT, navOption)));
                break;
            default: 
                return false;
        }
        
        link.click();
        waitForWorking(s);
        
        return true;
    }

}
