package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoAction {

    public static boolean click(WebDriver driver, int timeOutSeconds, String actionName) {
        WebElement element = driver.findElement(By.xpath("//a[starts-with(@class, 'aui-ActionLink') and contains(text(),'"+actionName+"')]"));
        element.click();
    
        return true;
    }
    
    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String actionName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@class, 'aui-ActionLink') and contains(text(),'"+actionName+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isCompleted(WebDriver driver, int timeOutSeconds) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.textToBePresentInElementLocated(By.id("inlineConfirmMessage"), "Action completed successfully"));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
