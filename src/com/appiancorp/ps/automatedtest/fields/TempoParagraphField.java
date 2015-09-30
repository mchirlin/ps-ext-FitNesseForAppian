package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoParagraphField extends TempoField {
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement textAreaField = fieldLayout.findElement(By.xpath(".//textarea[contains(@class, 'aui-TextAreaInput')]"));
        textAreaField.clear();
        textAreaField.sendKeys(fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/div/textarea")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    } 
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        // For read-only
        try {
            return TempoField.contains(fieldLayout, fieldValue);
        } catch (Exception e) {}
        
        // For editable
        WebElement textAreaField = fieldLayout.findElement(By.xpath(".//textarea[contains(@class, 'aui-TextAreaInput')]"));
        return textAreaField.getAttribute("value").contains(fieldValue);
    }
    
    public static boolean clear(WebElement fieldLayout) {
        WebElement textAreaField = fieldLayout.findElement(By.xpath(".//textarea[contains(@class, 'aui-TextAreaInput')]"));
        textAreaField.clear();
        
        return true;
    }
}
