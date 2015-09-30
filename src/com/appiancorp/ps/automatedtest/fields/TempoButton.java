package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoButton extends TempoField {
    
    public static boolean click(String buttonName) {
        WebElement element = driver.findElement(By.xpath("//button[contains(text(), '"+buttonName+"')]"));
        element.click();
        
        return true;
    }
    
    public static boolean waitFor(String buttonName) {
        try {
            // Always wait for a second before submitting
            Thread.sleep(1000);
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), '"+buttonName+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
