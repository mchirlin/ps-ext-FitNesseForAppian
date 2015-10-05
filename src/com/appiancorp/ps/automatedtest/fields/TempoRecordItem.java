package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRecordItem extends TempoObject{

    public static boolean click(String itemName) {
        WebElement element = driver.findElement(By.xpath("//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/') and contains(text(), '"+ itemName +"')]"));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String itemName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/') and contains(text(), '"+ itemName +"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean refreshAndWaitFor(String itemName) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > refreshTimes) return false;
            
            if (TempoRecordItem.waitFor(itemName)) {
                present = true;
                break;
            };        

            driver.navigate().refresh();
            i++;
        }

        return true;
    }
    
    public static boolean clickOnFacet(String facetName) {
        WebElement element = driver.findElement(By.xpath("//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/')]/span[contains(text(), '"+ facetName +"')]"));
        element.click();

        return true;
    }
    
    public static boolean waitForFacet(String facetName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/item/')]/span[contains(text(), '"+ facetName +"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnRelatedAction(String relatedActionName) {
        WebElement element = driver.findElement(By.xpath("//a[starts-with(@class, 'aui-ActionLink') and contains(text(),'"+relatedActionName+"')]"));
        element.click();

        return true;
    }
    
    public static boolean waitForRelatedAction(String relatedActionName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@class, 'aui-ActionLink') and contains(text(),'"+relatedActionName+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean refreshAndWaitForRelatedAction(String relatedActionName) {
        boolean present = false;

        int i = 0;
        while (!present) {
            if (i > refreshTimes) return false;
            
            if (TempoRecordItem.waitForRelatedAction(relatedActionName)) {
                present = true;
                break;
            };
                            
            driver.navigate().refresh();
            i++;
        }

        return true;
    }
}
