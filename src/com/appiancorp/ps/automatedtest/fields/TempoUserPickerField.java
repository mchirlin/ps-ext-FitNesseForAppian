package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoUserPickerField extends TempoPickerField{
    private static final Logger LOG = Logger.getLogger(TempoUserPickerField.class);
    
    public static boolean populate(WebDriver driver, int timeOutSeconds, String fieldName, String[] fieldValues) {
        WebElement fieldLayout = getFieldLayout(driver, timeOutSeconds, fieldName);
        
        return populate(driver, fieldLayout, timeOutSeconds, fieldName, fieldValues);
    }
    
    public static boolean populate(WebDriver driver, WebElement fieldLayout, int timeOutSeconds, String fieldName, String[] fieldValues) {
        WebElement groupPickerField;
        waitFor(driver, timeOutSeconds, fieldName);
        
        for (int i = 0; i < fieldValues.length; i++) {    
            groupPickerField = fieldLayout.findElement(By.xpath(".//input"));
            groupPickerField.click();
            groupPickerField.sendKeys(fieldValues[i]);
            
            // Wait until the suggestions populate
            waitForSuggestion(driver, timeOutSeconds, fieldValues[i]);
            WebElement suggestion = driver.findElement(By.xpath("//p[contains(text(), '"+fieldValues[i]+"')]"));
            String suggestionTitle = suggestion.findElement(By.xpath("./preceding-sibling::p")).getText();
            suggestion.click();
            
            // If there are more values to add
            if (i < fieldValues.length - 1) {
                // Wait until selected suggestion is added to the DOM
                waitForSelection(driver, timeOutSeconds, suggestionTitle);
                // Wait until the next input box is added to the DOM
                waitFor(driver, timeOutSeconds, fieldName);
                fieldLayout = getFieldLayout(driver, timeOutSeconds, fieldName);
            }
        }
        
        return true;
    }
    
    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::input[contains(@class, 'SuggestBox')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }  
    
    public static boolean contains(WebElement fieldLayout, int timeOutSeconds, String[] fieldValues) {
        try {
            // TODO Sort out non read-only fields
            for (String fieldValue : fieldValues) {
                fieldLayout.findElement(By.xpath(".//a[contains(text(), '"+fieldValue+"')]"));
            }
            
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
