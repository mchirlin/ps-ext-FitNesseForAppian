package com.appiancorp.ps.automatedtest.fields;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.fields.TempoObject;

public class TempoDateField extends TempoField{

    private static final Logger LOG = Logger.getLogger(TempoDateField.class);
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {        
        fieldValue = parseVariable(fieldValue);
        Date d;
        
        try{
            d = DateUtils.parseDate(fieldValue, TempoObject.DATETIME_DISPLAY_FORMAT_STRING);
        } catch (Exception E) {
            d = new Date();
        }
        
        populateTempoDateFieldWithDate(fieldLayout, d);
        
        LOG.debug("DATE FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::input")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    @SuppressWarnings("deprecation")
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        String dateString = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-DateInput-TextBox')]")).getAttribute("value");

        try{  
            Date compareDate = DateUtils.parseDate(dateString, DATE_FORMAT_STRING);
            Date fieldDate = DateUtils.parseDate(fieldValue, DATETIME_DISPLAY_FORMAT_STRING);
            LOG.debug("DATE FIELD COMPARISON : Field value (" + compareDate.toString() + ") compared to Entered value (" + fieldDate.toString() + ")");
            
            return compareDate.getDate() == fieldDate.getDate() &&
                    compareDate.getMonth() == fieldDate.getMonth() &&
                    compareDate.getYear() == fieldDate.getYear();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }        
    }
    
    private static boolean populateTempoDateFieldWithDate(WebElement fieldLayout, Date d) {
        String dateValue = new SimpleDateFormat(DATE_FORMAT_STRING).format(d);
        
        WebElement datePlaceholder = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-DateInput-Placeholder')]"));
        WebElement dateField = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-DateInput-TextBox')]"));
        
        // Clear out existing values
        if (!isEmptyDateDate(fieldLayout)) {
            dateField.click();
            dateField.sendKeys(Keys.CONTROL + "a");
            dateField.sendKeys(Keys.DELETE);
            dateField.sendKeys(dateValue);
        } else {
            datePlaceholder.click();    
            dateField.sendKeys(dateValue);
        }
                
        return true;
    }
    
    private static boolean isEmptyDateDate(WebElement fieldLayout) {
        return !fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-DateInput-TextBox')]")).isDisplayed();
    }
}
