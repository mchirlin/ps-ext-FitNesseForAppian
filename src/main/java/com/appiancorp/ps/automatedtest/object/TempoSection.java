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

public class TempoSection extends TempoObject {
       
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TempoSection.class);
    
    protected static final String XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT = Settings.getByConstant("xpathAbsoluteSectionFieldLayout");
    protected static final String XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX = Settings.getByConstant("xpathAbsoluteSectionFieldLayoutIndex");
    protected static final String XPATH_RELATIVE_SECTION_VALIDATION_MESSAGE_SPECIFIC_VALUE = Settings.getByConstant("xpathRelativeSectionValidationMessageSpecificValue");
    protected static final String XPATH_RELATIVE_SECTION_VALIDATION_MESSAGE = Settings.getByConstant("xpathRelativeSectionValidationMessage");

    protected static final String XPATH_ABSOLUTE_SECTION_LAYOUT = Settings.getByConstant("xpathAbsoluteSectionLayout");
    protected static final String XPATH_RELATIVE_SECTION_EXPAND = Settings.getByConstant("xpathRelativeSectionExpand");
    protected static final String XPATH_RELATIVE_SECTION_COLLAPSE = Settings.getByConstant("xpathRelativeSectionCollapse");
    
    public static WebElement getFieldLayout(String fieldName, String sectionName, Settings s) {
        if (isFieldIndex(fieldName)) {
            int index = getIndexFromFieldIndex(fieldName);
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT_INDEX, sectionName, index)));
        } else {
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_FIELD_LAYOUT, sectionName, fieldName, sectionName, fieldName)));
        }
    }
    
    public static WebElement getSection(String sectionName, Settings s){
        return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_LAYOUT, sectionName)));
    }
    
    public static boolean populate(String fieldName, String fieldSection, String[] fieldValues, Settings s){
        for (String fieldValue : fieldValues) {    
            WebElement fieldLayout = getFieldLayout(fieldName, fieldSection, s);
            if (!TempoField.populate(fieldLayout, fieldName, fieldValue, s)) return false;
        }
        
        return true;
    }
    
    public static boolean waitFor(String sectionName, Settings s){
        try{
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_SECTION_LAYOUT, sectionName))));
            int attempt = 0;
            while (attempt < s.getAttemptTimes()) {
                try {
                    WebElement section = getSection(sectionName, s);
                    scrollIntoView(section, s);
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
    
    public static boolean waitForValidationMessages(String sectionName, String[] validationMessages, Settings s) {
        WebElement section = getSection(sectionName, s);;
        String xpathLocator = getXpathLocator(section);
        
        try {
            for (String validationMessage : validationMessages) {
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath("(" + xpathLocator + ")" + String.format(XPATH_RELATIVE_SECTION_VALIDATION_MESSAGE_SPECIFIC_VALUE, validationMessage))));
            }
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }
    
    public static String getValidationMessages(String sectionName, Settings s) {
        WebElement section = getSection(sectionName, s);
        List<String> values = new ArrayList<String>();
        
        for (WebElement a : section.findElements(By.xpath(XPATH_RELATIVE_SECTION_VALIDATION_MESSAGE))) {
            values.add(a.getText());
        }
        
        // Remove bullet points
        if (values.size() > 1) {
            for(int i = 0; i < values.size(); i++) {
                String val = values.get(i);
                values.set(i, val.substring(2, val.length()));
            }
        }
        
        return String.join(",", values);
    }
    
    public static boolean clickExpandSection(String sectionName, Settings s){
        WebElement section =  getSection(sectionName, s);
        WebElement expand = section.findElement(By.xpath(XPATH_RELATIVE_SECTION_EXPAND));
        expand.click();
        waitForWorking(s);
        return true;
    }
    
    public static boolean clickCollapseSection(String sectionName, Settings s){
        WebElement section =  getSection(sectionName, s);
        WebElement collapse = section.findElement(By.xpath(XPATH_RELATIVE_SECTION_COLLAPSE));
        collapse.click();
        waitForWorking(s);
        return true;
    }
}

