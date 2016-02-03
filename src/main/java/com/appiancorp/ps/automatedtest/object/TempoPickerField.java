package com.appiancorp.ps.automatedtest.object;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianVersions;

public class TempoPickerField extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoPickerField.class);
    protected static final String XPATH_ABSOLUTE_PICKER_LABEL = AppianVersions.getByConstant("xpathAbsolutePickerLabel");
    protected static final String XPATH_RELATIVE_PICKER_INPUT = AppianVersions.getByConstant("xpathRelativePickerInput");
    protected static final String XPATH_ABSOLUTE_PICKER_SUGGESTION = AppianVersions.getByConstant("xpathAbsolutePickerSuggestion");
    protected static final String XPATH_RELATIVE_PICKER_SELECTION = AppianVersions.getByConstant("xpathRelativePickerSelection");
    protected static final String XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION = AppianVersions.getByConstant("xpathRelativePickerSpecificSelection");
    protected static final String XPATH_RELATIVE_PICKER_SELECTION_REMOVE_LINK = AppianVersions.getByConstant("xpathRelativePickerSelectionRemoveLink");
    protected static final String XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION_REMOVE_LINK = AppianVersions.getByConstant("xpathRelativePickerSpecificSelectionRemoveLink");
    protected static final String XPATH_RELATIVE_PICKER_SUGGEST_BOX = AppianVersions.getByConstant("xpathRelativePickerSuggestBox");
    protected static final String XPATH_RELATIVE_INPUT_OR_SELECTION = "(" + XPATH_RELATIVE_PICKER_INPUT + " | " + XPATH_RELATIVE_PICKER_SELECTION + ")";
    
    public static boolean populate(WebElement fieldLayout, String fieldName, String fieldValue) {
        WebElement groupPickerField;
         
        waitForSuggestBox(fieldLayout, fieldName);
        groupPickerField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PICKER_INPUT));
        groupPickerField.click();
        groupPickerField.sendKeys(fieldValue);
        
        // Wait until the suggestions populate
        waitForSuggestion(fieldValue);
        WebElement suggestion = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_PICKER_SUGGESTION, fieldValue, fieldValue)));
        suggestion.click();
        waitForSelection(fieldLayout, fieldValue);   
        unfocus();
        
        LOG.debug("PICKER FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static String getValue(WebElement fieldLayout) {
        List<String> values = new ArrayList<String>();
        
        for (WebElement a : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_PICKER_SELECTION))) {
            values.add(a.getText());
        }
        
        LOG.debug("PICKER FIELD VALUE : " + values.toString());
        
        return String.join(",", values);
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        try {
            fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION, fieldValue)));
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
        List<WebElement> xs = fieldLayout.findElements(By.xpath(XPATH_RELATIVE_PICKER_SELECTION_REMOVE_LINK));
        
        for (WebElement x : xs) {
            x.click();
        }
        
        return true;
    }
    
    public static boolean clearOf(WebElement fieldLayout, String[] fieldValues) {
        for (String fieldValue : fieldValues) {
            WebElement x = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION_REMOVE_LINK, fieldValue)));
            x.click();
        }
        
        return true;
    }
    
    protected static boolean waitForSuggestion(String fieldValue) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_PICKER_SUGGESTION, fieldValue, fieldValue))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    protected static boolean waitForSelection(WebElement fieldLayout, String fieldValue) {
        try {
            String xpathLocator = getXpathLocator(fieldLayout);
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" + xpathLocator + ")" + String.format(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION, fieldValue))));
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    protected static boolean waitForSuggestBox(WebElement fieldLayout, String fieldValue) {
        try {
            String xpathLocator = getXpathLocator(fieldLayout);
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(" + xpathLocator + ")" + String.format(XPATH_RELATIVE_PICKER_SPECIFIC_SELECTION, fieldValue))));
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
