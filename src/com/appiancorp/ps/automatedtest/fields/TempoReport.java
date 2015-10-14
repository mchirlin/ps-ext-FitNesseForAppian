package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoReport extends TempoObject{
    
    public static boolean click(String reportName) {
        WebElement element = driver.findElement(By.xpath("//a[contains(@class, 'appian-feed-entry-author') and contains(text(),'" + reportName +"')]"));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String reportName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'appian-feed-entry-author') and contains(text(),'" + reportName +"')]")));
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
