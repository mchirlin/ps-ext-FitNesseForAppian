package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoPickerField extends TempoField {
    
    public static boolean clearOf(WebDriver driver, int timeOutSeconds, String fieldName, String[] fieldValues) {
        WebElement fieldLayout = getFieldLayout(driver, timeOutSeconds, fieldName);
        
        return clearOf(fieldLayout, timeOutSeconds, fieldValues);
    }
    
    public static boolean clearOf(WebElement fieldLayout, int timeOutSeconds, String[] fieldValues) {
        for (String fieldValue : fieldValues) {
            WebElement x = fieldLayout.findElement(By.xpath("//a[contains(text(), '"+fieldValue+"')]/following-sibling::a"));
            x.click();
        }
        
        return true;
    }
    
    protected static boolean waitForSuggestion(WebDriver driver, int timeOutSeconds, String fieldValue) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), '"+fieldValue+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    protected static boolean waitForSelection(WebDriver driver, int timeOutSeconds, String fieldValue) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), '"+fieldValue+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
