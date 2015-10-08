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

public class TempoDatetimeField extends TempoField{

    private static final Logger LOG = Logger.getLogger(TempoDatetimeField.class);
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {        
        Date d;
        
        try{
            d = DateUtils.parseDate(fieldValue, TempoObject.DATETIME_DISPLAY_FORMAT_STRING);
        } catch (Exception E) {
            d = new Date();
        }
        
        populateTempoDatetimeFieldWithDate(fieldLayout, d);
        populateTempoDatetimeFieldWithTime(fieldLayout, d);
        
        LOG.debug("DATETIME FIELD POPULTATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::input")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        String dateString = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-DateInput-TextBox')]")).getAttribute("value");
        String timeString = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-TimeInput-TextBox')]")).getAttribute("value");
        
        String datetimeString = dateString + " " + timeString;

        try{  
            String compareString = new SimpleDateFormat(TempoObject.DATETIME_DISPLAY_FORMAT_STRING).format(DateUtils.parseDate(datetimeString, DATETIME_FORMAT_STRING));
            LOG.debug("DATETIME FIELD COMPARISON : Field value (" + fieldValue + ") compared to Entered value (" + compareString + ")");
            
            return compareString.equals(fieldValue);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }        
    }
    
    private static boolean populateTempoDatetimeFieldWithDate(WebElement fieldLayout, Date d) {
        String dateValue = new SimpleDateFormat(DATE_FORMAT_STRING).format(d);
        
        WebElement datePlaceholder = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-DateInput-Placeholder')]"));
        WebElement dateField = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-DateInput-TextBox')]"));
        
        // Clear out existing values
        if (!isEmptyDatetimeDateField(fieldLayout)) {
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
    
    private static boolean populateTempoDatetimeFieldWithTime(WebElement fieldLayout, Date d) {
        String timeValue = new SimpleDateFormat(TIME_FORMAT_STRING).format(d);
        
        WebElement timePlaceholder = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-TimeInput-Placeholder')]"));
        WebElement timeField = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-TimeInput-TextBox')]"));
        
        // Clear out existing values
        if (!isEmptyDatetimeTimeField(fieldLayout)) {
            timeField.click();
            timeField.sendKeys(Keys.CONTROL + "a");
            timeField.sendKeys(Keys.DELETE);
            timeField.sendKeys(timeValue);
        } else {
            timePlaceholder.click();
            timeField.sendKeys(timeValue);
        }
        
        return true;
    }
    
    private static boolean isEmptyDatetimeDateField(WebElement fieldLayout) {
        return !fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-DateInput-TextBox')]")).isDisplayed();
    }
    
    private static boolean isEmptyDatetimeTimeField(WebElement fieldLayout) {
        return !fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-TimeInput-TextBox')]")).isDisplayed();
    }
}
