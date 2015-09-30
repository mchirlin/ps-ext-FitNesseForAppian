package com.appiancorp.ps.automatedtest.fields;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.fields.TempoObject;

public class TempoUserPickerField extends TempoPickerField{
    private static final Logger LOG = Logger.getLogger(TempoUserPickerField.class);
    
    public static boolean populate(String fieldName, String[] fieldValues) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldName, fieldValues);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldName, String[] fieldValues) {
        WebElement groupPickerField;
        waitFor(fieldName);
        
        for (int i = 0; i < fieldValues.length; i++) {    
            groupPickerField = fieldLayout.findElement(By.xpath(".//input"));
            groupPickerField.click();
            groupPickerField.sendKeys(fieldValues[i]);
            
            // Wait until the suggestions populate
            waitForSuggestion(fieldValues[i]);
            WebElement suggestion = driver.findElement(By.xpath("//p[contains(text(), '"+fieldValues[i]+"')]"));
            String suggestionTitle = suggestion.findElement(By.xpath("./preceding-sibling::p")).getText();
            suggestion.click();
            
            // If there are more values to add
            if (i < fieldValues.length - 1) {
                // Wait until selected suggestion is added to the DOM
                waitForSelection(suggestionTitle);
                // Wait until the next input box is added to the DOM
                waitFor(fieldName);
                fieldLayout = getFieldLayout(fieldName);
            }
        }
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::input[contains(@class, 'SuggestBox')]")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }  
    
    public static boolean contains(WebElement fieldLayout, String fieldName, String[] fieldValues) {

        for (String fieldValue : fieldValues) {
            try {
                fieldLayout.findElement(By.xpath(".//a[contains(text(), '"+fieldValue+"')]"));
                
                continue;
            } catch (Exception e) {}
            
            LOG.debug("Unconverted fieldValue: " + fieldValue);
            fieldValue = TempoObject.runExpression("=user(\""+fieldValue+"\", \"firstName\") & \" \" & user(\""+fieldValue+"\", \"lastName\")");
            LOG.debug("Converted fieldValue: " + fieldValue);
            
            waitFor(fieldName);
            fieldLayout = getFieldLayout(fieldName);
            fieldLayout.findElement(By.xpath(".//a[contains(text(), '"+fieldValue+"')]"));
        }
        
        return true;
    }
}
