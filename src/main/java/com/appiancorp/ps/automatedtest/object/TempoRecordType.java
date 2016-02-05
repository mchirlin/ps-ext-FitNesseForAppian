package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;

public class TempoRecordType extends TempoObject{

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoRecordType.class);
    private static final String XPATH_ABSOLUTE_RECORD_TYPE_LINK = Metadata.getByConstant("xpathAbsoluteRecordTypeLink");
    private static final String XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK = Metadata.getByConstant("xpathAbsoluteRecordTypeUserFilterLink");

    public static boolean click(String type) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_LINK, type)));
        element.click();
        waitForWorking();
        
        return true;
    }
    
    public static boolean waitFor(String type) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_LINK, type))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_LINK, type)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnUserFilter(String userFilter) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK, userFilter)));
        scrollIntoView(element, false);
        element.click();
        waitForWorking();

        return true;
    }
    
    public static boolean waitForUserFilter(String userFilter) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK, userFilter))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
