package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoIntegerField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoIntegerField.class);
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement intField = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-TextInput')]"));
        intField.clear();
        intField.sendKeys(fieldValue);
        
        LOG.debug("INTEGER field: " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/div/input")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clear(WebElement fieldLayout) {
        WebElement intField = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-TextInput')]"));
        intField.clear();
        
        return true;
    }
}
