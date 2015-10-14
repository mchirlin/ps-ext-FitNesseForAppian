package com.appiancorp.ps.automatedtest.fields;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.fields.TempoObject;

public class TempoField extends TempoObject {
       
    private static final Logger LOG = Logger.getLogger(TempoField.class);
    protected static final String XPATH_FIELD_LAYOUT = "//span[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::div[contains(@class, 'aui_FieldLayout')] | //label[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::div[contains(@class, 'aui_FieldLayout')]";
    protected static final String XPATH_FIELD_LABEL = "//label[contains(text(),'%s')] | //span[contains(text(), '%s')]";
    protected static final String XPATH_RELATIVE_READ_ONLY_FIELD = ".//p[contains(text(), '%s')] | .//div[contains(text(), '%s') and not(contains(@class, 'textarea_print'))]";
    
    public static WebElement getFieldLayout(String fieldName) {
        return driver.findElement(By.xpath(String.format(XPATH_FIELD_LAYOUT, fieldName, fieldName)));
    }
    
    public static boolean populate(String fieldName, String[] fieldValues){
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldName, fieldValues);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldName, String[] fieldValues) {      
        for (int i = 0; i < fieldValues.length; i++) {
            fieldValues[i] = TempoObject.parseVariable(fieldValues[i]);
        }
        
        try {
            return TempoDatetimeField.populate(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoDateField.populate(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoFileUploadField.populate(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoTextField.populate(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoParagraphField.populate(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoIntegerField.populate(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoRadioField.populate(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoSelectField.populate(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoUserPickerField.populate(fieldLayout, fieldName, fieldValues);
        } catch (Exception e) {}
        
        return false;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            // Scroll the field layout into view
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_FIELD_LABEL, fieldName, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clear(String fieldName){
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return clear(fieldLayout, fieldName);
    }
    
    public static boolean clear(WebElement fieldLayout, String fieldName) {      
        try {
            return TempoTextField.clear(fieldLayout);
        } catch (Exception e) {}
        
        try {
            return TempoParagraphField.clear(fieldLayout);
        } catch (Exception e) {}
        
        try {
            return TempoIntegerField.clear(fieldLayout);
        } catch (Exception e) {}
        
        try {
            return TempoFileUploadField.clear(fieldLayout);
        } catch (Exception e) {}
        
        try {
            return TempoUserPickerField.clear(fieldLayout);
        } catch (Exception e) {}
        
        return false;
    }
    
    public static boolean clearOf(String fieldName, String[] fieldValues){
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return clearOf(fieldLayout, fieldValues);
    }
    
    public static boolean clearOf(WebElement fieldLayout, String[] fieldValues) {
        try {
            return TempoUserPickerField.clearOf(fieldLayout, fieldValues);
        } catch (Exception e) {}
        
        return false;
    }
    
    public static boolean contains(String fieldName, String[] fieldValues) {
        WebElement fieldLayout = getFieldLayout(fieldName);

        return contains(fieldLayout, fieldName, fieldValues);
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldName, String[] fieldValues) {
        for (int i = 0; i < fieldValues.length; i++) {
            fieldValues[i] = TempoObject.parseVariable(fieldValues[i]);
        }

        try {
            return TempoDatetimeField.contains(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoDateField.contains(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoTextField.contains(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoParagraphField.contains(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoIntegerField.contains(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoRadioField.contains(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoSelectField.contains(fieldLayout, fieldName, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoFileUploadField.contains(fieldLayout, fieldValues[0]);
        } catch (Exception e) {}
        
        try {
            return TempoUserPickerField.contains(fieldLayout, fieldName, fieldValues);
        } catch (Exception e) {}
        
        return false;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_READ_ONLY_FIELD, fieldValue, fieldValue)));
        
        LOG.debug("READ ONLY FIELD COMPARISON : Field value [" + fieldValue + "] found");
        
        return true;
    }
}
