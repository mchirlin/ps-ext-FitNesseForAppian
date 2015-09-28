package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoField {
    public static WebElement getFieldLayout(WebDriver driver, int timeOutSeconds, String fieldName) {
        return driver.findElement(By.xpath("//span[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::div[contains(@class, 'aui_FieldLayout')] | //label[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::div[contains(@class, 'aui_FieldLayout')]"));
    }
    
    public static boolean populate(WebDriver driver, int timeOutSeconds, String fieldName, String[] fieldValues){
        WebElement fieldLayout = getFieldLayout(driver, timeOutSeconds, fieldName);
        
        return populate(driver, fieldLayout, timeOutSeconds, fieldName, fieldValues);
    }
    
    public static boolean populate(WebDriver driver, WebElement fieldLayout, int timeOutSeconds, String fieldName, String[] fieldValues) {
        try {
            return TempoDatetimeField.populate(fieldLayout, timeOutSeconds, fieldValues);
        } catch (Exception e) {}
        
        try {
            return TempoTextField.populate(fieldLayout, timeOutSeconds, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoParagraphField.populate(fieldLayout, timeOutSeconds, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoIntegerField.populate(fieldLayout, timeOutSeconds, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoRadioField.populate(fieldLayout, timeOutSeconds, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoSelectField.populate(fieldLayout, timeOutSeconds, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoUserPickerField.populate(driver, fieldLayout, timeOutSeconds, fieldName, fieldValues);
        } catch (Exception e) {}
        
        return false;
    }
    
    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'"+fieldName+"')] | //span[contains(text(), '"+fieldName+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clearOf(WebDriver driver, int timeOutSeconds, String fieldName, String[] fieldValues){
        WebElement fieldLayout = getFieldLayout(driver, timeOutSeconds, fieldName);
        
        return clearOf(fieldLayout, timeOutSeconds, fieldValues);
    }
    
    public static boolean clearOf(WebElement fieldLayout, int timeOutSeconds, String[] fieldValues) {
        /*
        try {
            return TempoDatetimeField.clear(fieldLayout, timeOutSeconds);
        } catch (Exception e) {}
        
        try {
            return TempoTextField.clear(fieldLayout, timeOutSeconds);
        } catch (Exception e) {}
        
        try {
            return TempoParagraphField.clear(fieldLayout, timeOutSeconds);
        } catch (Exception e) {}
        
        try {
            return TempoIntegerField.clear(fieldLayout, timeOutSeconds);
        } catch (Exception e) {}
        
        try {
            return TempoRadioField.clear(fieldLayout, timeOutSeconds);
        } catch (Exception e) {}
        
        try {
            return TempoSelectField.clear(fieldLayout, timeOutSeconds);
        } catch (Exception e) {}
        */
        try {
            return TempoUserPickerField.clearOf(fieldLayout, timeOutSeconds, fieldValues);
        } catch (Exception e) {}
        
        return false;
    }
    
    public static boolean contains(WebDriver driver, int timeOutSeconds, String fieldName, String[] fieldValues) {
        WebElement fieldLayout = getFieldLayout(driver, timeOutSeconds, fieldName);
        /*
        try {
            return TempoDatetimeField.contains(fieldLayout, timeOutSeconds, fieldValues);
        } catch (Exception e) {}
        */
        try {
            return TempoTextField.contains(fieldLayout, timeOutSeconds, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoParagraphField.contains(fieldLayout, timeOutSeconds, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoIntegerField.contains(fieldLayout, timeOutSeconds, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoRadioField.contains(fieldLayout, timeOutSeconds, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoSelectField.contains(fieldLayout, timeOutSeconds, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoUserPickerField.contains(fieldLayout, timeOutSeconds, fieldValues);
        } catch (Exception e) {}
        
        return false;
    }
    
    public static boolean contains(WebElement fieldLayout, int timeOutSeconds, String fieldValue) {
        // TODO Sort out non read-only fields
        fieldLayout.findElement(By.xpath(".//p[contains(text(), '"+fieldValue+"')]"));
        
        return true;
    }
}
