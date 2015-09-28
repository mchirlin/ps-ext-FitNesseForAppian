package com.appiancorp.ps.automatedtest.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoSelectField extends TempoField {
    
    public static boolean populate(WebDriver driver, int timeOutSeconds, String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(driver, timeOutSeconds, fieldName);
        
        return populate(fieldLayout, timeOutSeconds, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, int timeOutSeconds, String fieldValue) {
        WebElement selectField = fieldLayout.findElement(By.xpath(".//select"));
        Select select = new Select(selectField);
        select.selectByVisibleText(fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/div/select")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
}
