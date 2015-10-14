package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoAction extends TempoObject {

    public static boolean click(String actionName) {
        WebElement element = driver.findElement(By.xpath("//a[starts-with(@class, 'aui-ActionLink') and contains(text(),'"+actionName+"')]"));
        // Have to manually scroll element into view because Tempo header covers the action link for long action lists
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
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
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@class, 'aui-ActionLink') and contains(text(),'"+actionName+"')]")));
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
