package com.appiancorp.ps.automatedtest.object;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.object.TempoObject;
import com.google.common.base.Throwables;

public class TempoField extends TempoObject {
       
    private static final Logger LOG = Logger.getLogger(TempoField.class);
    protected static final String XPATH_ABSOLUTE_FIELD_LAYOUT = Settings.getByConstant("xpathAbsoluteFieldLayout");
    protected static final String XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX = "(" + XPATH_ABSOLUTE_FIELD_LAYOUT + ")[%d]";
    protected static final String XPATH_RELATIVE_READ_ONLY_FIELD = Settings.getByConstant("xpathRelativeReadOnlyField"); // Handles readOnly fields and paging grids
    protected static final String XPATH_CONCAT_ANCESTOR_FIELD_LAYOUT = Settings.getByConstant("xpathConcatAncestorFieldLayout");
    protected static final String XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE_SPECIFIC_VALUE = Settings.getByConstant("xpathRelativeFieldValidationMessageSpecificValue");
    protected static final String XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE = Settings.getByConstant("xpathRelativeFieldValidationMessage");
    
    public static WebElement getFieldLayout(String fieldName, Settings s) {
        if (isFieldIndex(fieldName)) {
            String fName = getFieldFromFieldIndex(fieldName);
            int index = getIndexFromFieldIndex(fieldName);
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX, fName, fName, index)));
        } else {
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_FIELD_LAYOUT, fieldName, fieldName)));
        }        
    }
    
    public static boolean populate(String fieldType, String fieldName, String[] fieldValues, Settings s){
        TempoFieldType type = getFieldTypeFromString(fieldType);
        for (String fieldValue : fieldValues) {
            WebElement fieldLayout;
            
            switch (type) {  
                case FILE_UPLOAD: 
                    fieldLayout = TempoFileUploadField.getFieldLayout(fieldName, s);
                    break;
                
                default:
                    return false;
            }
            if (!populate(fieldLayout, fieldName, fieldValue, s)) return false;
        }
        
        return true;
    }
    
    public static boolean populate(String fieldName, String[] fieldValues, Settings s){
        for (String fieldValue : fieldValues) {
            WebElement fieldLayout = getFieldLayout(fieldName, s);
            if (!populate(fieldLayout, fieldName, fieldValue, s)) return false;
        }
        
        return true;
    }
        
    public static boolean populate(WebElement fieldLayout, String fieldName, String fieldValue, Settings s) {      
        TempoFieldType fieldType = getFieldType(fieldLayout);
        fieldValue = TempoObject.parseVariable(fieldValue, s);
            
        try {
            switch (fieldType) {  
                case TEXT: 
                    return TempoTextField.populate(fieldLayout, fieldValue, s);
                    
                case PARAGRAPH:
                    return TempoParagraphField.populate(fieldLayout, fieldValue, s);
                    
                case INTEGER: 
                    return TempoIntegerField.populate(fieldLayout, fieldValue, s);
                    
                case SELECT:
                    return TempoSelectField.populate(fieldLayout, fieldValue, s);
        
                case RADIO: 
                    return TempoRadioField.populate(fieldLayout, fieldValue, s);
                    
                case CHECKBOX: 
                    return TempoCheckboxField.populate(fieldLayout, fieldValue, s);
                
                case FILE_UPLOAD: 
                    return TempoFileUploadField.populate(fieldLayout, fieldValue, s);
                    
                case DATE: 
                    return TempoDateField.populate(fieldLayout, fieldValue, s);
                    
                case DATETIME: 
                    return TempoDatetimeField.populate(fieldLayout, fieldValue, s);
                    
                case PICKER:
                    return TempoPickerField.populate(fieldLayout, fieldName, fieldValue, s);
                
                default:
                    return false;
            }
        } catch (Exception e) {
            LOG.warn("POPULATION for " + fieldName + "\n" + Throwables.getStackTraceAsString(e));
            return false;
        }
    }
    
    public static boolean waitFor(String fieldType, String fieldName, Settings s) {
        TempoFieldType type = getFieldTypeFromString(fieldType);
        
        switch (type) {
            case FILE_UPLOAD:
                return TempoFileUploadField.waitFor(fieldName, s);
                
            default:
                return false;
        }
    }
    
    public static boolean waitFor(String fieldName, Integer timeout, Settings s) {
        try {
            // Scroll the field layout into view
            if (isFieldIndex(fieldName)) {
                String fName = getFieldFromFieldIndex(fieldName);
                int index = getIndexFromFieldIndex(fieldName);
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_FIELD_LAYOUT_INDEX, fName, fName, index))));
            } else {
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_FIELD_LAYOUT, fieldName, fieldName))));
            }  
            WebElement fieldLayout = getFieldLayout(fieldName, s);
            scrollIntoView(fieldLayout, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean waitFor(String fieldName, Settings s) {
        return waitFor(fieldName, s.getTimeoutSeconds(), s);
    }
    
    public static boolean clear(String fieldName, Settings s){
        WebElement fieldLayout = getFieldLayout(fieldName, s);
        
        return clear(fieldLayout, fieldName, s);
    }

    public static boolean clear(WebElement fieldLayout, String fieldName, Settings s) { 
        
        TempoFieldType fieldType = getFieldType(fieldLayout);
        
        try {
            switch (fieldType) {
                case TEXT: 
                    return TempoTextField.clear(fieldLayout);
                    
                case PARAGRAPH:
                    return TempoParagraphField.clear(fieldLayout);
                    
                case INTEGER:
                    return TempoIntegerField.clear(fieldLayout);
                    
                case FILE_UPLOAD:
                    return TempoFileUploadField.clear(fieldLayout);
                    
                case PICKER:
                    return TempoPickerField.clear(fieldLayout);
                
                default:
                    return false;
            }
        } catch (Exception e) {
            LOG.warn("CLEAR for " + fieldName + "\n" + Throwables.getStackTraceAsString(e));
            return false;
        }
    }
    
    public static boolean clearOf(String fieldName, String[] fieldValues, Settings s){
        WebElement fieldLayout = getFieldLayout(fieldName, s);
        
        return clearOf(fieldLayout, fieldValues, s);
    }
    
    public static boolean clearOf(WebElement fieldLayout, String[] fieldValues, Settings s) {
        TempoFieldType fieldType = getFieldType(fieldLayout);
        
        switch (fieldType) {
        
            case PICKER: 
                return TempoPickerField.clearOf(fieldLayout, fieldValues);
                
            default: 
                return false;
        }
    }
    
    public static String getValue(String fieldName, Settings s) {
        WebElement fieldLayout = getFieldLayout(fieldName, s);
        
        return getValue(fieldLayout, fieldName);
    }
    
    public static String getValue(WebElement fieldLayout, String fieldName) {
        TempoFieldType fieldType = getFieldType(fieldLayout);
        
        LOG.debug("Field Type: " + fieldType);
        
        try {
            switch (fieldType) {
            
                case READ_ONLY: 
                    return getValue(fieldLayout);
                
                case TEXT: 
                    return TempoTextField.getValue(fieldLayout);
                    
                case PARAGRAPH: 
                    return TempoParagraphField.getValue(fieldLayout);
                    
                case INTEGER:
                    return TempoIntegerField.getValue(fieldLayout);
                    
                case SELECT:
                    return TempoSelectField.getValue(fieldLayout);
                
                case RADIO:
                    return TempoRadioField.getValue(fieldLayout);
                         
                case CHECKBOX: 
                    return TempoCheckboxField.getValue(fieldLayout);
                
                case FILE_UPLOAD:
                    return TempoFileUploadField.getValue(fieldLayout);
                    
                case DATE:
                    return TempoDateField.getValue(fieldLayout);
                    
                case DATETIME:
                    return TempoDatetimeField.getValue(fieldLayout);
                    
                case PICKER: 
                    return TempoPickerField.getValue(fieldLayout);
                    
                default:
                   return "";
            }
        } catch (Exception e) {
            LOG.warn("VALUE for " + fieldName + "\n" + Throwables.getStackTraceAsString(e));
            return "";
        }
    }
    
    public static boolean contains(String fieldName, String[] fieldValues, Settings s) {
        for (String fieldValue : fieldValues) {
            WebElement fieldLayout = getFieldLayout(fieldName, s);
            if (!contains(fieldLayout, fieldName, fieldValue, s)) return false;
        }
        
        return true;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldName, String fieldValue, Settings s) {
        TempoFieldType fieldType = getFieldType(fieldLayout);
        
        fieldValue = TempoObject.parseVariable(fieldValue, s);
        
        LOG.debug("Field Type: " + fieldType);
        
        try {
            switch (fieldType) {
            
                case READ_ONLY: 
                    return contains(fieldLayout, fieldValue);
            
                case TEXT: 
                    return TempoTextField.contains(fieldLayout, fieldValue);
                    
                case PARAGRAPH: 
                    return TempoParagraphField.contains(fieldLayout, fieldValue);
                    
                case INTEGER:
                    return TempoIntegerField.contains(fieldLayout, fieldValue);
                    
                case SELECT:
                    return TempoSelectField.contains(fieldLayout, fieldValue);
                    
                case RADIO:
                    return TempoRadioField.contains(fieldLayout, fieldValue);
                    
                case CHECKBOX: 
                    return TempoCheckboxField.contains(fieldLayout, fieldValue);
                    
                case FILE_UPLOAD:
                    return TempoFileUploadField.contains(fieldLayout, fieldValue, s);
                    
                case DATE:
                    return TempoDateField.contains(fieldLayout, fieldValue, s);
                    
                case DATETIME:
                    return TempoDatetimeField.contains(fieldLayout, fieldValue, s);
                    
                case PICKER: 
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
    
    public static boolean waitForValidationMessages(String fieldName, String[] validationMessages, Settings s) {
        WebElement fieldLayout = getFieldLayout(fieldName, s);
        String xpathLocator = getXpathLocator(fieldLayout);
        
        try {
            for (String validationMessage : validationMessages) {
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath("(" + xpathLocator + ")" + String.format(XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE_SPECIFIC_VALUE, validationMessage))));
            }
        } catch (TimeoutException e) {
            return false;
        }
       
        return true;
    }
    
    public static String getValidationMessages(String fieldName, Settings s) {
        WebElement fieldLayout = getFieldLayout(fieldName, s);
        List<String> values = new ArrayList<String>();
        
        for (WebElement a : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_FIELD_VALIDATION_MESSAGE))) {
            values.add(a.getText());
        }
        
        return String.join(",", values);
    }
    
    public static boolean isReadOnly(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_READ_ONLY_FIELD));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static TempoFieldType getFieldType(WebElement fieldLayout) {
        if (TempoField.isReadOnly(fieldLayout)) return TempoFieldType.READ_ONLY;
        else if (TempoTextField.isType(fieldLayout)) return TempoFieldType.TEXT;
        else if (TempoParagraphField.isType(fieldLayout)) return TempoFieldType.PARAGRAPH;
        else if (TempoIntegerField.isType(fieldLayout)) return TempoFieldType.INTEGER;
        else if (TempoSelectField.isType(fieldLayout)) return TempoFieldType.SELECT;
        else if (TempoRadioField.isType(fieldLayout)) return TempoFieldType.RADIO;
        else if (TempoCheckboxField.isType(fieldLayout)) return TempoFieldType.CHECKBOX;
        else if (TempoFileUploadField.isType(fieldLayout)) return TempoFieldType.FILE_UPLOAD;
        // Datetime must be before Date
        else if (TempoDatetimeField.isType(fieldLayout)) return TempoFieldType.DATETIME;
        else if (TempoDateField.isType(fieldLayout)) return TempoFieldType.DATE;
        else if (TempoPickerField.isType(fieldLayout)) return TempoFieldType.PICKER;
        else return TempoFieldType.UNKNOWN;
    }
    
    public static TempoFieldType getFieldTypeFromString(String fieldType) {
        if (fieldType.equals("READ_ONLY")) return TempoFieldType.READ_ONLY;
        else if (fieldType.equals("TEXT")) return TempoFieldType.TEXT;
        else if (fieldType.equals("PARAGRAPH")) return TempoFieldType.PARAGRAPH;
        else if (fieldType.equals("INTEGER")) return TempoFieldType.INTEGER;
        else if (fieldType.equals("SELECT")) return TempoFieldType.SELECT;
        else if (fieldType.equals("RADIO")) return TempoFieldType.RADIO;
        else if (fieldType.equals("CHECKBOX")) return TempoFieldType.CHECKBOX;
        else if (fieldType.equals("FILE_UPLOAD")) return TempoFieldType.FILE_UPLOAD;
        else if (fieldType.equals("DATETIME")) return TempoFieldType.DATETIME;
        else if (fieldType.equals("DATE")) return TempoFieldType.DATE;
        else if (fieldType.equals("PICKER")) return TempoFieldType.PICKER;
        else return TempoFieldType.UNKNOWN;
    }
}
