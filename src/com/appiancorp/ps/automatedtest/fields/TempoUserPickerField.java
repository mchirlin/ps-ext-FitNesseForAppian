package com.appiancorp.ps.automatedtest.fields;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
            groupPickerField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
            groupPickerField.click();
            groupPickerField.sendKeys(fieldValues[i]);
            
            // Wait until the suggestions populate
            waitForSuggestion(fieldValues[i]);
            WebElement suggestion = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_SUGGESTION, fieldValues[i])));
            suggestion.click();
            
            // If there are more values to add
            if (i < fieldValues.length - 1) {
                // Wait until selected suggestion is added to the DOM
                waitForSelection(fieldValues[i]);
                // Wait until the next input box is added to the DOM
                waitForSuggestBox(fieldName);
                fieldLayout = getFieldLayout(fieldName);
            }
        }
        
        LOG.debug("USER PICKER FIELD POPULATION : " + Arrays.toString(fieldValues));
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SUGGEST_BOX, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }  
    
    public static boolean contains(WebElement fieldLayout, String fieldName, String[] fieldValues) {

        for (String fieldValue : fieldValues) {
            try {
                fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_SELECTION, fieldValue)));
                
                continue;
            } catch (Exception e) {}
            
            waitFor(fieldName);
            fieldLayout = getFieldLayout(fieldName);
            fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_SELECTION, fieldValue)));
        }
        
        LOG.debug("USER PICKER FIELD COMPARISON : Field values " + Arrays.toString(fieldValues) + " found");
        
        return true;
    }
}
