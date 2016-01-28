package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRecordType extends TempoObject{

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoRecordType.class);
    private static final String XPATH_RECORD_TYPE = "//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/view/all') and contains(text(), '%s')]";
    private static final String XPATH_RECORD_TYPE_USER_FILTER = "//ol[@class='facetgroup']/descendant::a[contains(text(),'%s')]";

    public static boolean click(String type) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_TYPE, type)));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String type) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_RECORD_TYPE, type))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_TYPE, type)));
            scrollIntoView(element, false);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnUserFilter(String userFilter) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_RECORD_TYPE_USER_FILTER, userFilter)));
        scrollIntoView(element, false);
        element.click();

        return true;
    }
    
    public static boolean waitForUserFilter(String userFilter) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_RECORD_TYPE_USER_FILTER, userFilter))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}