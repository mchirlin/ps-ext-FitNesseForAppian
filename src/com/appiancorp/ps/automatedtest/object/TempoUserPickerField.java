package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoUserPickerField extends TempoPickerField{
    
    private static final Logger LOG = Logger.getLogger(TempoUserPickerField.class);
    
    public static boolean populate(WebElement fieldLayout, String fieldName, String fieldValue) {
        WebElement groupPickerField;
        waitFor(fieldName);
         
        groupPickerField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        groupPickerField.click();
        groupPickerField.sendKeys(fieldValue);
        
        // Wait until the suggestions populate
        waitForSuggestion(fieldValue);
        WebElement suggestion = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_SUGGESTION, fieldValue)));
        suggestion.click();
        
        waitForSelection(fieldValue);
        // Wait until the next input box is added to the DOM
        waitForSuggestBox(fieldName);
        fieldLayout = getFieldLayout(fieldName);
        
        LOG.debug("USER PICKER FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SUGGEST_BOX, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (TimeoutException toe) {
            return false;
        }
        
        return true;
    }  
    
    public static boolean contains(WebElement fieldLayout, String fieldName, String fieldValue) {
        try {
            waitFor(fieldName);
            fieldLayout = getFieldLayout(fieldName);
            fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_SELECTION, fieldValue)));
        } catch (NoSuchElementException nse) {
            return false;
        }
        
        LOG.debug("USER PICKER FIELD COMPARISON : Field value " + fieldValue + " found");
        
        return true;
    }
}
