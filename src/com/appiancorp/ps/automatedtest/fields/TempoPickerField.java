package com.appiancorp.ps.automatedtest.fields;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoPickerField extends TempoField {
    
    public static boolean clearOf(String fieldName, String[] fieldValues) {
        WebElement fieldLayout = getFieldLayout( fieldName);
        
        return clearOf(fieldLayout, fieldValues);
    }
    
    public static boolean clear(WebElement fieldLayout) {
        List<WebElement> xs = fieldLayout.findElements(By.xpath(".//a/following-sibling::a"));
        
        for (WebElement x : xs) {
            x.click();
        }
        
        return true;
    }
    
    public static boolean clearOf(WebElement fieldLayout, String[] fieldValues) {
        for (String fieldValue : fieldValues) {
            WebElement x = fieldLayout.findElement(By.xpath("//a[contains(text(), '"+fieldValue+"')]/following-sibling::a"));
            x.click();
        }
        
        return true;
    }
    
    protected static boolean waitForSuggestion(String fieldValue) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), '"+fieldValue+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    protected static boolean waitForSelection(String fieldValue) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), '"+fieldValue+"')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
