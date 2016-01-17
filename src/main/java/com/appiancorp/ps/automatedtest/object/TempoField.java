package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.object.TempoObject;
import com.google.common.base.Throwables;

public class TempoField extends TempoObject {
       
    private static final Logger LOG = Logger.getLogger(TempoField.class);
    protected static final String XPATH_LABEL_STARTS_WITH = "[starts-with(text(),'%s')]/parent::span/following-sibling::div/descendant::div[contains(@class, 'aui_FieldLayout_InputContainer')]";
    protected static final String XPATH_FIELD_LAYOUT = "//span" + XPATH_LABEL_STARTS_WITH + "| //label" + XPATH_LABEL_STARTS_WITH;
    protected static final String XPATH_FIELD_LAYOUT_INDEX = "(" + XPATH_FIELD_LAYOUT + ")[%d]";
    protected static final String XPATH_FIELD_SECTION_LAYOUT = "//h3[contains(text(),'%s')]/parent::div/following-sibling::div/descendant::span" + XPATH_LABEL_STARTS_WITH + "| //h3[contains(text(),'%s')]/parent::div/following-sibling::div/descendant::label" + XPATH_LABEL_STARTS_WITH;
    protected static final String XPATH_FIELD_SECTION_LAYOUT_INDEX = "//h3[contains(text(),'%s')]/parent::div/following-sibling::div/descendant::div[contains(@class, 'aui_FieldLayout_InputContainer')][%d]";
    protected static final String XPATH_RELATIVE_READ_ONLY_FIELD = ".//p[contains(@class, 'readonly')] | div[ancestor::table[contains(@class, 'DataGrid-Table')]]"; // Handles readOnly fields and paging grids
    
    protected static final String TEXT_FIELD = "textField";
    protected static final String PARAGRAPH_FIELD = "paragraphField";
    protected static final String INTEGER_FIELD = "integerField";
    protected static final String SELECT_FIELD = "selectField";
    protected static final String RADIO_FIELD = "radioField";
    protected static final String CHECKBOX_FIELD = "checkboxField";
    protected static final String DATE_FIELD = "dateField";
    protected static final String DATETIME_FIELD = "datetimeField";
    protected static final String FILE_UPLOAD_FIELD = "fileUploadField";
    protected static final String PICKER_FIELD = "pickerField";
    protected static final String UNKNOWN_FIELD = "unknownField";
    protected static final String READ_ONLY_FIELD = "readOnlyField";
    
    public static WebElement getFieldLayout(String fieldName) {
        if (isFieldIndex(fieldName)) {
            String fName = getFieldFromFieldIndex(fieldName);
            int index = getIndexFromFieldIndex(fieldName);
            return driver.findElement(By.xpath(String.format(XPATH_FIELD_LAYOUT_INDEX, fName, fName, index)));
        } else {
            return driver.findElement(By.xpath(String.format(XPATH_FIELD_LAYOUT, fieldName, fieldName)));
        }        
    }
    
    public static boolean populate(String fieldName, String[] fieldValues){
        for (String fieldValue : fieldValues) {
            WebElement fieldLayout = getFieldLayout(fieldName);
            if (!populate(fieldLayout, fieldName, fieldValue)) return false;
        }
        
        return true;
    }
        
    public static boolean populate(WebElement fieldLayout, String fieldName, String fieldValue) {      
        String fieldType = getFieldType(fieldLayout);
        fieldValue = TempoObject.parseVariable(fieldValue);
            
        try {
            switch (fieldType) {  
                case TEXT_FIELD: 
                    return TempoTextField.populate(fieldLayout, fieldValue);
                    
                case PARAGRAPH_FIELD:
                    return TempoParagraphField.populate(fieldLayout, fieldValue);
                    
                case INTEGER_FIELD: 
                    return TempoIntegerField.populate(fieldLayout, fieldValue);
                    
                case SELECT_FIELD:
                    return TempoSelectField.populate(fieldLayout, fieldValue);
        
                case RADIO_FIELD: 
                    return TempoRadioField.populate(fieldLayout, fieldValue);
                    
                case CHECKBOX_FIELD: 
                    return TempoCheckboxField.populate(fieldLayout, fieldValue);
                
                case FILE_UPLOAD_FIELD: 
                    return TempoFileUploadField.populate(fieldLayout, fieldValue);
                    
                case DATE_FIELD: 
                    return TempoDateField.populate(fieldLayout, fieldValue);
                    
                case DATETIME_FIELD: 
                    return TempoDatetimeField.populate(fieldLayout, fieldValue);
                    
                case PICKER_FIELD:
                    return TempoPickerField.populate(fieldLayout, fieldName, fieldValue);
                
                default:
                    return false;
            }
        } catch (Exception e) {
            LOG.warn("POPULATION for " + fieldName + "\n" + Throwables.getStackTraceAsString(e));
            return false;
        }
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            // Scroll the field layout into view
            if (isFieldIndex(fieldName)) {
                String fName = getFieldFromFieldIndex(fieldName);
                int index = getIndexFromFieldIndex(fieldName);
                (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_FIELD_LAYOUT_INDEX, fName, fName, index))));
            } else {
                (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_FIELD_LAYOUT, fieldName, fieldName))));
            }  
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clear(String fieldName){
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return clear(fieldLayout, fieldName);
    }

    public static boolean clear(WebElement fieldLayout, String fieldName) { 
        
        String fieldType = getFieldType(fieldLayout);
        
        try {
            switch (fieldType) {
                case TEXT_FIELD: 
                    return TempoTextField.clear(fieldLayout);
                    
                case PARAGRAPH_FIELD:
                    return TempoParagraphField.clear(fieldLayout);
                    
                case INTEGER_FIELD:
                    return TempoIntegerField.clear(fieldLayout);
                    
                case FILE_UPLOAD_FIELD:
                    return TempoFileUploadField.clear(fieldLayout);
                    
                case PICKER_FIELD:
                    return TempoPickerField.clear(fieldLayout);
                
                default:
                    return false;
            }
        } catch (Exception e) {
            LOG.warn("CLEAR for " + fieldName + "\n" + Throwables.getStackTraceAsString(e));
            return false;
        }
    }
    
    public static boolean clearOf(String fieldName, String[] fieldValues){
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return clearOf(fieldLayout, fieldValues);
    }
    
    public static boolean clearOf(WebElement fieldLayout, String[] fieldValues) {
        String fieldType = getFieldType(fieldLayout);
        
        switch (fieldType) {
        
            case PICKER_FIELD: 
                return TempoPickerField.clearOf(fieldLayout, fieldValues);
                
            default: 
                return false;
        }
    }
    
    public static String getValue(String fieldName) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return getValue(fieldLayout, fieldName);
    }
    
    public static String getValue(WebElement fieldLayout, String fieldName) {
        String fieldType = getFieldType(fieldLayout);
        
        LOG.debug("Field Type: " + fieldType);
        
        try {
            switch (fieldType) {
            
                case READ_ONLY_FIELD: 
                    return getValue(fieldLayout);
                
                case TEXT_FIELD: 
                    return TempoTextField.getValue(fieldLayout);
                    
                case PARAGRAPH_FIELD: 
                    return TempoParagraphField.getValue(fieldLayout);
                    
                case INTEGER_FIELD:
                    return TempoIntegerField.getValue(fieldLayout);
                    
                case SELECT_FIELD:
                    return TempoSelectField.getValue(fieldLayout);
                
                case RADIO_FIELD:
                    return TempoRadioField.getValue(fieldLayout);
                         
                case CHECKBOX_FIELD: 
                    return TempoCheckboxField.getValue(fieldLayout);
                
                case FILE_UPLOAD_FIELD:
                    return TempoFileUploadField.getValue(fieldLayout);
                    
                case DATE_FIELD:
                    return TempoDateField.getValue(fieldLayout);
                    
                case DATETIME_FIELD:
                    return TempoDatetimeField.getValue(fieldLayout);
                    
                case PICKER_FIELD: 
                    return TempoPickerField.getValue(fieldLayout);
                    
                default:
                   return "";
            }
        } catch (Exception e) {
            LOG.warn("VALUE for " + fieldName + "\n" + Throwables.getStackTraceAsString(e));
            return "";
        }
    }
    
    public static boolean contains(String fieldName, String[] fieldValues) {
        for (String fieldValue : fieldValues) {
            WebElement fieldLayout = getFieldLayout(fieldName);
            if (!contains(fieldLayout, fieldName, fieldValue)) return false;
        }
        
        return true;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldName, String fieldValue) {
        String fieldType = getFieldType(fieldLayout);
        
        fieldValue = TempoObject.parseVariable(fieldValue);
        
        LOG.debug("Field Type: " + fieldType);
        
        try {
            switch (fieldType) {
            
                case READ_ONLY_FIELD: 
                    return contains(fieldLayout, fieldValue);
            
                case TEXT_FIELD: 
                    return TempoTextField.contains(fieldLayout, fieldValue);
                    
                case PARAGRAPH_FIELD: 
                    return TempoParagraphField.contains(fieldLayout, fieldValue);
                    
                case INTEGER_FIELD:
                    return TempoIntegerField.contains(fieldLayout, fieldValue);
                    
                case SELECT_FIELD:
                    return TempoSelectField.contains(fieldLayout, fieldValue);
                    
                case RADIO_FIELD:
                    return TempoRadioField.contains(fieldLayout, fieldValue);
                    
                case CHECKBOX_FIELD: 
                    return TempoCheckboxField.contains(fieldLayout, fieldValue);
                    
                case FILE_UPLOAD_FIELD:
                    return TempoFileUploadField.contains(fieldLayout, fieldValue);
                    
                case DATE_FIELD:
                    return TempoDateField.contains(fieldLayout, fieldValue);
                    
                case DATETIME_FIELD:
                    return TempoDatetimeField.contains(fieldLayout, fieldValue);
                    
                case PICKER_FIELD: 
                    return TempoPickerField.contains(fieldLayout, fieldValue);
                    
                default:
                   return false;
            }
        } catch (Exception e) {
            LOG.warn("CONTAINS for " + fieldName + "\n" + Throwables.getStackTraceAsString(e));
            return false;
        }
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        String compareString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_READ_ONLY_FIELD)).getText();
        
        LOG.debug("READ ONLY FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");
        
        // Necessary to compare both to handle readOnly date fields that are compared to datetimes
        return compareString.contains(fieldValue) || fieldValue.contains(compareString);
    }
    
    public static String getValue(WebElement fieldLayout) {
        String value = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_READ_ONLY_FIELD))).getText();
        
        LOG.debug("READ ONLY FIELD VALUE: " + value);
        
        return value;
    }
    
    public static boolean isReadOnly(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_READ_ONLY_FIELD));
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
        else if (TempoCheckboxField.isType(fieldLayout)) return CHECKBOX_FIELD;
        else if (TempoFileUploadField.isType(fieldLayout)) return FILE_UPLOAD_FIELD;
        // Datetime must be before Date
        else if (TempoDatetimeField.isType(fieldLayout)) return DATETIME_FIELD;
        else if (TempoDateField.isType(fieldLayout)) return DATE_FIELD;
        else if (TempoPickerField.isType(fieldLayout)) return PICKER_FIELD;
        else return UNKNOWN_FIELD;
    }
}
