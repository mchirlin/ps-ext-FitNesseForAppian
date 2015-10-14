package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRecordList extends TempoObject{

    public static boolean click(String listName) {
        WebElement element = driver.findElement(By.xpath("//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/view/all') and contains(text(), '"+ listName +"')]"));
        element.click();

        return true;
    }
    
    public static boolean waitFor(String listName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@href, '/suite/tempo/records/type') and contains(@href, '/view/all') and contains(text(), '"+ listName +"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOnFacetOption(String facetName) {
        WebElement element = driver.findElement(By.xpath("//ol[@class='facetgroup']/descendant::a[contains(text(),'"+facetName+"')]"));
        element.click();

        return true;
    }
    
    public static boolean waitForFacetOption(String facetName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ol[@class='facetgroup']/descendant::a[contains(text(),'"+facetName+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
