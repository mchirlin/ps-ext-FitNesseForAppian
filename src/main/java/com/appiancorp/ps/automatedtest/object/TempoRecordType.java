package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoRecordType extends TempoObject{

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoRecordType.class);
    private static final String XPATH_ABSOLUTE_RECORD_TYPE_LINK = Settings.getByConstant("xpathAbsoluteRecordTypeLink");
    private static final String XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK = Settings.getByConstant("xpathAbsoluteRecordTypeUserFilterLink");

    public static boolean click(String type, Settings s) {
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_LINK, type)));
        element.click();
        waitForWorking(s);
        
        return true;
    }
    
    public static boolean waitFor(String type, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_LINK, type))));
            WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_LINK, type)));
            scrollIntoView(element, false, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnUserFilter(String userFilter, Settings s) {
        WebElement element = s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK, userFilter)));
        scrollIntoView(element, false, s);
        element.click();
        waitForWorking(s);

        return true;
    }
    
    public static boolean waitForUserFilter(String userFilter, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RECORD_TYPE_USER_FILTER_LINK, userFilter))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
