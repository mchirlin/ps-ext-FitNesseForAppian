package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoButton extends TempoField {
    
    public static boolean click(WebDriver driver, int timeOutSeconds, String buttonName) {
        WebElement element = driver.findElement(By.xpath("//button[contains(text(), '"+buttonName+"')]"));
        element.click();
        
        return true;
    }
    
    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String buttonName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), '"+buttonName+"')]")));
            // Wait for 'Working...' to go away before submitting form
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(text(), 'Working...')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
