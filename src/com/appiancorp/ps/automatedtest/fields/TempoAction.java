package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoAction extends TempoObject {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoAction.class);
    private static final String XPATH_ELEMENT = "//a[starts-with(@class, 'aui-ActionLink') and contains(text(),'%s')]";
    
    public static boolean click(String actionName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, actionName)));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // do nothing
        }
        element.click();
    
        return true;
    }
    
    public static boolean waitFor(String actionName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ELEMENT, actionName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ELEMENT, actionName)));
            scrollIntoView(element);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isCompleted() {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.textToBePresentInElementLocated(By.id("inlineConfirmMessage"), "Action completed successfully"));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
