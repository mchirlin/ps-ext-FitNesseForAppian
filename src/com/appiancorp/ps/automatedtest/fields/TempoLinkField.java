package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoLinkField extends TempoField {
    
    public static boolean click(String linkName) {
        WebElement element = driver.findElement(By.xpath("//a[contains(text(), '"+linkName+"')]"));
        element.click();
        
        return true;
    }
    
    public static boolean waitFor(String linkName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), '"+linkName+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
