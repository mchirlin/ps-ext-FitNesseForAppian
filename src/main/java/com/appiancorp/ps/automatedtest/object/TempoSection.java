package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;
import com.appiancorp.ps.automatedtest.object.TempoObject;

public class TempoSection extends TempoObject {
       
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoSection.class);
    protected static final String XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT = Metadata.getByConstant("xpathAbsoluteSectionFieldLayout");
    protected static final String XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX = Metadata.getByConstant("xpathAbsoluteSectionFieldLayoutIndex");
    protected static final String XPATH_ABSOLUTE_SECTION_ERROR = Metadata.getByConstant("xpathAbsoluteSectionError");
    
    public static WebElement getFieldLayout(String fieldName, String sectionName) {
        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX, sectionName, index)));
        } else {
            return driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT, sectionName, fieldName, sectionName, fieldName)));
        }
    }
    
    public static boolean populate(String fieldName, String fieldSection, String[] fieldValues){
        for (String fieldValue : fieldValues) {    
            WebElement fieldLayout = getFieldLayout(fieldName, fieldSection);
            if (!TempoField.populate(fieldLayout, fieldName, fieldValue)) return false;
        }
        
        return true;
    }
    
    public static boolean waitFor(String fieldName, String sectionName) {
        try {
            // Scroll the field layout into view
            if (isFieldIndex(fieldName)) {
                int index = getIndexFromFieldIndex(fieldName);
                (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX, sectionName, index))));
            } else {
                (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT, sectionName, fieldName, sectionName, fieldName))));
            }  
            // Attempt to scroll into view
            int attempt = 0;
            while (attempt < attemptTimes) {
                try {
                    WebElement fieldLayout = getFieldLayout(fieldName, sectionName);
                    scrollIntoView(fieldLayout);
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
    
    public static boolean clear(String fieldName, String sectionName){
        WebElement fieldLayout = getFieldLayout(fieldName, sectionName);
        
        return TempoField.clear(fieldLayout, fieldName);
    }
    
    public static String getValue(String fieldName, String sectionName) {
        WebElement fieldLayout = getFieldLayout(fieldName, sectionName);
        
        return TempoField.getValue(fieldLayout, fieldName);
    }
    
    public static boolean contains(String fieldName, String sectionName, String[] fieldValues) {
        for (String fieldValue : fieldValues) {
            WebElement fieldLayout = getFieldLayout(fieldName, sectionName);
            if (!TempoField.contains(fieldLayout, fieldName, fieldValue)) return false; 
        }
        
        return true;
    }
    
    public static boolean waitForError(String sectionName, String error) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_ERROR, sectionName, error))));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
