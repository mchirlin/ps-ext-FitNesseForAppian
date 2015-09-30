package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoSelectField extends TempoField {
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement selectField = fieldLayout.findElement(By.xpath(".//select"));
        Select select = new Select(selectField);
        select.selectByVisibleText(fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(WebDriver driver, String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/div/select")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
}
