package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoParagraphField extends TempoField {
    
    public static boolean populate(WebDriver driver, int timeOutSeconds, String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(driver, timeOutSeconds, fieldName);
        
        return populate(fieldLayout, timeOutSeconds, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, int timeOutSeconds, String fieldValue) {
        WebElement textAreaField = fieldLayout.findElement(By.xpath(".//textarea[contains(@class, 'aui-TextAreaInput')]"));
        textAreaField.clear();
        textAreaField.sendKeys(fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/div/textarea")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    } 
    
    public static boolean contains(WebElement fieldLayout, int timeOutSeconds, String fieldValue) {
        // For read-only
        try {
            return TempoField.contains(fieldLayout, timeOutSeconds, fieldValue);
        } catch (Exception e) {}
        
        // For editable
        try {
            fieldLayout.findElement(By.xpath(".//textarea[contains(@class, 'aui-TextAreaInput') and contains(@value, '"+fieldValue+"')]"));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
