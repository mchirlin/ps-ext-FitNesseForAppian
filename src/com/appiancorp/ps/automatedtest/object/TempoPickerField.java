package com.appiancorp.ps.automatedtest.object;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoPickerField extends TempoField {
    
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoPickerField.class);
    protected static final String XPATH_ABSOLUTE_LABEL = "//label[contains(text(),'%s')]/parent::span";
    protected static final String XPATH_RELATIVE_INPUT = ".//input[contains(@class, 'gwt-SuggestBox')]";
    protected static final String XPATH_ABSOLUTE_SUGGESTION = "//p[contains(text(), '%s')]";
    protected static final String XPATH_ABSOLUTE_SELECTION = "//a[contains(text(), '%s')]";
    protected static final String XPATH_RELATIVE_SELECTION = ".//a[contains(text(), '%s')]";
    protected static final String XPATH_RELATIVE_SELECTION_REMOVE = ".//a/following-sibling::a";
    protected static final String XPATH_RELATIVE_SPECIFIC_SELECTION_REMOVE = "//a[contains(text(), '%s')]/following-sibling::a";
    protected static final String XPATH_ABSOLUTE_SUGGEST_BOX = XPATH_ABSOLUTE_LABEL + "/following-sibling::div/descendant::input[contains(@class, 'SuggestBox')]";
    protected static final String XPATH_RELATIVE_INPUT_OR_SELECTION = "(" + XPATH_RELATIVE_INPUT + " | " + XPATH_RELATIVE_SELECTION + ")";
    
    public static boolean clearOf(String fieldName, String[] fieldValues) {
        WebElement fieldLayout = getFieldLayout( fieldName);
        
        return clearOf(fieldLayout, fieldValues);
    }
    
    public static boolean clear(WebElement fieldLayout) {
        List<WebElement> xs = fieldLayout.findElements(By.xpath(XPATH_RELATIVE_SELECTION_REMOVE));
        
        for (WebElement x : xs) {
            x.click();
        }
        
        return true;
    }
    
    public static boolean clearOf(WebElement fieldLayout, String[] fieldValues) {
        for (String fieldValue : fieldValues) {
            WebElement x = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_SPECIFIC_SELECTION_REMOVE, fieldValue)));
            x.click();
        }
        
        return true;
    }
    
    protected static boolean waitForSuggestion(String fieldValue) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SUGGESTION, fieldValue))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    protected static boolean waitForSelection(String fieldValue) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SELECTION, fieldValue))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    protected static boolean waitForSuggestBox(String fieldValue) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SUGGEST_BOX, fieldValue))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT_OR_SELECTION));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
