package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRadioField extends TempoField {
    
    public static boolean populate(WebDriver driver, int timeOutSeconds, String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(driver, timeOutSeconds, fieldName);
        
        return populate(fieldLayout, timeOutSeconds, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, int timeOutSeconds, String fieldValue) {
        WebElement radioField = fieldLayout.findElement(By.xpath(".//label[contains(text(), '"+fieldValue+"')]/preceding-sibling::input"));
        radioField.click();
        
        return true;
    }
    
    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::input")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
