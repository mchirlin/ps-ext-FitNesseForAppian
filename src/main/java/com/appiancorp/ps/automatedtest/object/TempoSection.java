package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.object.TempoObject;

public class TempoSection extends TempoObject {
       
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoSection.class);
    protected static final String XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT = Settings.getByConstant("xpathAbsoluteSectionFieldLayout");
    protected static final String XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX = Settings.getByConstant("xpathAbsoluteSectionFieldLayoutIndex");
    protected static final String XPATH_ABSOLUTE_SECTION_ERROR = Settings.getByConstant("xpathAbsoluteSectionError");
    
    public static WebElement getFieldLayout(String fieldName, String sectionName, Settings s) {
        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX, sectionName, index)));
        } else {
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT, sectionName, fieldName, sectionName, fieldName)));
        }
    }
    
    public static boolean populate(String fieldName, String fieldSection, String[] fieldValues, Settings s){
        for (String fieldValue : fieldValues) {    
            WebElement fieldLayout = getFieldLayout(fieldName, fieldSection, s);
            if (!TempoField.populate(fieldLayout, fieldName, fieldValue, s)) return false;
        }
        
        return true;
    }
    
    public static boolean waitFor(String fieldName, String sectionName, Settings s) {
        try {
            // Scroll the field layout into view
            if (isFieldIndex(fieldName)) {
                int index = getIndexFromFieldIndex(fieldName);
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX, sectionName, index))));
            } else {
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT, sectionName, fieldName, sectionName, fieldName))));
            }  
            // Attempt to scroll into view
            int attempt = 0;
            while (attempt < s.getAttemptTimes()) {
                try {
                    WebElement fieldLayout = getFieldLayout(fieldName, sectionName, s);
                    scrollIntoView(fieldLayout, s);
                    return true;
                } catch (Exception e) {
                    attempt++;
                }
            }        
        } catch (TimeoutException e) {
            return false;
        }
        
        return false;
    }
    
    public static boolean clear(String fieldName, String sectionName, Settings s){
        WebElement fieldLayout = getFieldLayout(fieldName, sectionName, s);
        
        return TempoField.clear(fieldLayout, fieldName, s);
    }
    
    public static String getValue(String fieldName, String sectionName, Settings s) {
        WebElement fieldLayout = getFieldLayout(fieldName, sectionName, s);
        
        return TempoField.getValue(fieldLayout, fieldName);
    }
    
    public static boolean contains(String fieldName, String sectionName, String[] fieldValues, Settings s) {
        for (String fieldValue : fieldValues) {
            WebElement fieldLayout = getFieldLayout(fieldName, sectionName, s);
            if (!TempoField.contains(fieldLayout, fieldName, fieldValue, s)) return false; 
        }
        
        return true;
    }
    
    public static boolean waitForError(String sectionName, String error, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_ERROR, sectionName, error))));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
