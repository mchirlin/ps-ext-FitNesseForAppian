package com.appiancorp.ps.automatedtest.object;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoPickerField extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoPickerField.class);
    protected static final String XPATH_ABSOLUTE_LABEL = "//label[contains(text(),'%s')]/parent::span";
    protected static final String XPATH_RELATIVE_INPUT = ".//input[contains(@class, 'gwt-SuggestBox')]";
    protected static final String XPATH_ABSOLUTE_SUGGESTION = "//div[contains(@class, 'SuggestBoxPopup')]/descendant::p[contains(text(), '%s')] | //div[contains(@class, 'SuggestBoxPopup')]/descendant::h2[contains(text(), '%s')]";
    protected static final String XPATH_RELATIVE_SELECTION = "/descendant::a[contains(text(), '%s')]";
    protected static final String XPATH_RELATIVE_SELECTION_REMOVE = ".//a/following-sibling::a";
    protected static final String XPATH_RELATIVE_SPECIFIC_SELECTION_REMOVE = "//a[contains(text(), '%s')]/following-sibling::a";
    protected static final String XPATH_RELATIVE_SUGGEST_BOX = "/descendant::input[contains(@class, 'SuggestBox')]";
    protected static final String XPATH_RELATIVE_INPUT_OR_SELECTION = "(" + XPATH_RELATIVE_INPUT + " | " + XPATH_RELATIVE_SELECTION + ")";
    
    public static boolean populate(WebElement fieldLayout, String fieldName, String fieldValue) {
        WebElement groupPickerField;
         
        waitForSuggestBox(fieldLayout, fieldName);
        groupPickerField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        groupPickerField.click();
        groupPickerField.sendKeys(fieldValue);
        
        // Wait until the suggestions populate
        waitForSuggestion(fieldValue);
        WebElement suggestion = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_SUGGESTION, fieldValue, fieldValue)));
        suggestion.click();
        waitForSelection(fieldLayout, fieldValue);
        
        LOG.debug("PICKER FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        try {
            fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_SELECTION, fieldValue)));
        } catch (NoSuchElementException nse) {
            return false;
        }
        
        LOG.debug("USER PICKER FIELD COMPARISON : Field value " + fieldValue + " found");
        
        return true;
    }
    
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
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SUGGESTION, fieldValue, fieldValue))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    protected static boolean waitForSelection(WebElement fieldLayout, String fieldValue) {
        try {
            String xpathLocator = getXpathLocator(fieldLayout);
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" + xpathLocator + ")" + String.format(XPATH_RELATIVE_SELECTION, fieldValue))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    protected static boolean waitForSuggestBox(WebElement fieldLayout, String fieldValue) {
        try {
            String xpathLocator = getXpathLocator(fieldLayout);
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" + xpathLocator + ")" + String.format(XPATH_RELATIVE_SUGGEST_BOX, fieldValue))));
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
