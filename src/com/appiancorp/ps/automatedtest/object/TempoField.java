package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.object.TempoObject;

public class TempoField extends TempoObject {
       
    private static final Logger LOG = Logger.getLogger(TempoField.class);
    protected static final String XPATH_FIELD_LAYOUT = "//span[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::div[contains(@class, 'aui_FieldLayout_InputContainer')] | //label[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::div[contains(@class, 'aui_FieldLayout_InputContainer')]";
    protected static final String XPATH_FIELD_LAYOUT_INDEX = "(" + XPATH_FIELD_LAYOUT + ")[%d]";
    protected static final String XPATH_FIELD_LABEL = "//label[contains(text(),'%s')] | //span[contains(text(), '%s')]";
    protected static final String XPATH_RELATIVE_READ_ONLY_FIELD = ".//p[contains(@class, 'readonly')]"; // | .//div[contains(text(), '%s') and not(contains(@class, 'textarea_print'))]
    
    private static final String TEXT_FIELD = "textField";
    private static final String PARAGRAPH_FIELD = "paragraphField";
    private static final String INTEGER_FIELD = "integerField";
    private static final String SELECT_FIELD = "selectField";
    private static final String RADIO_FIELD = "radiioFeld";
    private static final String DATE_FIELD = "dateField";
    private static final String DATETIME_FIELD = "datetimeField";
    private static final String FILE_UPLOAD_FIELD = "fileUploadField";
    private static final String USER_PICKER_FIELD = "userPickerField";
    private static final String UNKNOWN_FIELD = "unknownField";
    private static final String READ_ONLY_FIELD = "readOnlyField";
    
    public static WebElement getFieldLayout(String fieldName) {
        return driver.findElement(By.xpath(String.format(XPATH_FIELD_LAYOUT, fieldName, fieldName)));
    }
    
    public static WebElement getFieldLayoutIndex(String fieldName, int index) {
        LOG.debug(String.format(XPATH_FIELD_LAYOUT_INDEX, fieldName, fieldName, index));
        return driver.findElement(By.xpath(String.format(XPATH_FIELD_LAYOUT_INDEX, fieldName, fieldName, index)));
    }
    
    public static boolean populate(String fieldName, String[] fieldValues){
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldName, fieldValues);
    }
    
    public static boolean populateIndex(String fieldName, String[] fieldValues, int index){
        WebElement fieldLayout = getFieldLayoutIndex(fieldName, index);
        
        return populate(fieldLayout, fieldName, fieldValues);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldName, String[] fieldValues) {      
        for (int i = 0; i < fieldValues.length; i++) {
            fieldValues[i] = TempoObject.parseVariable(fieldValues[i]);
        }
        
        String fieldType = getFieldType(fieldLayout);
        
        switch (fieldType) {
        
            case TEXT_FIELD: 
                return TempoTextField.populate(fieldLayout, fieldValues[0]);
                
            case PARAGRAPH_FIELD:
                return TempoParagraphField.populate(fieldLayout, fieldValues[0]);
                
            case INTEGER_FIELD: 
                return TempoIntegerField.populate(fieldLayout, fieldValues[0]);
                
            case SELECT_FIELD:
                return TempoSelectField.populate(fieldLayout, fieldValues[0]);
                
            case RADIO_FIELD: 
                return TempoRadioField.populate(fieldLayout, fieldValues[0]);
            
            case FILE_UPLOAD_FIELD: 
                return TempoFileUploadField.populate(fieldLayout, fieldValues[0]);
                
            case DATE_FIELD: 
                return TempoDateField.populate(fieldLayout, fieldValues[0]);
                
            case DATETIME_FIELD: 
                return TempoDatetimeField.populate(fieldLayout, fieldValues[0]);
                
            case USER_PICKER_FIELD:
                return TempoUserPickerField.populate(fieldLayout, fieldName, fieldValues);
            
            default:
                return false;
        }
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
    
    public static boolean clearIndex(String fieldName, int index){
        WebElement fieldLayout = getFieldLayoutIndex(fieldName, index);
        
        return clear(fieldLayout, fieldName);
    }
    
    public static boolean clear(WebElement fieldLayout, String fieldName) { 
        
        String fieldType = getFieldType(fieldLayout);
        
        switch (fieldType) {
        
            case TEXT_FIELD: 
                return TempoTextField.clear(fieldLayout);
                
            case PARAGRAPH_FIELD:
                return TempoParagraphField.clear(fieldLayout);
                
            case INTEGER_FIELD:
                return TempoIntegerField.clear(fieldLayout);
                
            case FILE_UPLOAD_FIELD:
                return TempoFileUploadField.clear(fieldLayout);
                
            case USER_PICKER_FIELD:
                return TempoUserPickerField.clear(fieldLayout);
            
            default:
                return false;
        }
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
    
    public static boolean containsIndex(String fieldName, String[] fieldValues, int index) {
        WebElement fieldLayout = getFieldLayoutIndex(fieldName, index);

        return contains(fieldLayout, fieldName, fieldValues);
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldName, String[] fieldValues) {
        for (int i = 0; i < fieldValues.length; i++) {
            fieldValues[i] = TempoObject.parseVariable(fieldValues[i]);
        }
        
        String fieldType = getFieldType(fieldLayout);
        
        try {
            switch (fieldType) {
            
                case READ_ONLY_FIELD: 
                    return contains(fieldLayout, fieldValues[0]);
            
                case TEXT_FIELD: 
                    return TempoTextField.contains(fieldLayout, fieldValues[0]);
                    
                case PARAGRAPH_FIELD: 
                    return TempoParagraphField.contains(fieldLayout, fieldValues[0]);
                    
                case INTEGER_FIELD:
                    return TempoIntegerField.contains(fieldLayout, fieldValues[0]);
                    
                case SELECT_FIELD:
                    return TempoSelectField.contains(fieldLayout, fieldName, fieldValues[0]);
                    
                case RADIO_FIELD:
                    return TempoRadioField.contains(fieldLayout, fieldValues[0]);
                    
                case FILE_UPLOAD_FIELD:
                    return TempoFileUploadField.contains(fieldLayout, fieldValues[0]);
                    
                case DATE_FIELD:
                    return TempoDateField.contains(fieldLayout, fieldValues[0]);
                    
                case DATETIME_FIELD:
                    return TempoDatetimeField.contains(fieldLayout, fieldValues[0]);
                    
                case USER_PICKER_FIELD: 
                    return TempoUserPickerField.contains(fieldLayout, fieldName, fieldValues);
                    
                default:
                   return false;
            }
        } catch (StaleElementReferenceException ste) {
            // If getting a stale element, try again immediately
            return contains(fieldName, fieldValues);
        }
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        String compareString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_READ_ONLY_FIELD)).getText();
        
        LOG.debug("READ ONLY FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");
        
        return compareString.contains(fieldValue);
    }
    
    public static boolean isReadOnly(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_READ_ONLY_FIELD)));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static String getFieldType(WebElement fieldLayout) {
        if (TempoField.isReadOnly(fieldLayout)) return READ_ONLY_FIELD;
        else if (TempoTextField.isType(fieldLayout)) return TEXT_FIELD;
        else if (TempoParagraphField.isType(fieldLayout)) return PARAGRAPH_FIELD;
        else if (TempoIntegerField.isType(fieldLayout)) return INTEGER_FIELD;
        else if (TempoSelectField.isType(fieldLayout)) return SELECT_FIELD;
        else if (TempoRadioField.isType(fieldLayout)) return RADIO_FIELD;
        else if (TempoFileUploadField.isType(fieldLayout)) return FILE_UPLOAD_FIELD;
        // Datetime must be before Date
        else if (TempoDatetimeField.isType(fieldLayout)) return DATETIME_FIELD;
        else if (TempoDateField.isType(fieldLayout)) return DATE_FIELD;
        else if (TempoUserPickerField.isType(fieldLayout)) return USER_PICKER_FIELD;
        else return UNKNOWN_FIELD;
    }
}
