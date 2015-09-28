package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRecordItem {

    public static boolean click(WebDriver driver, int timeOutSeconds, String itemName) {
        WebElement element = driver.findElement(By.xpath("//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/') and contains(text(), '"+ itemName +"')]"));
        element.click();

        return true;
    }
    
    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String itemName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/') and contains(text(), '"+ itemName +"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnFacet(WebDriver driver, int timeOutSeconds, String facetName) {
        WebElement element = driver.findElement(By.xpath("//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/')]/span[contains(text(), '"+ facetName +"')]"));
        element.click();

        return true;
    }
    
    public static boolean waitForFacet(WebDriver driver, int timeOutSeconds, String facetName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/')]/span[contains(text(), '"+ facetName +"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnRelatedAction(WebDriver driver, int timeOutSeconds, String relatedActionName) {
        WebElement element = driver.findElement(By.xpath("//a[starts-with(@class, 'aui-ActionLink') and contains(text(),'"+relatedActionName+"')]"));
        element.click();

        return true;
    }
    
    public static boolean waitForRelatedAction(WebDriver driver, int timeOutSeconds, String relatedActionName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@class, 'aui-ActionLink') and contains(text(),'"+relatedActionName+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
