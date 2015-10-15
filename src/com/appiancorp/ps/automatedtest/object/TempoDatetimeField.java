package com.appiancorp.ps.automatedtest.object;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.object.TempoObject;

public class TempoDatetimeField extends TempoDateField{

    private static final Logger LOG = Logger.getLogger(TempoDatetimeField.class);
    protected static final String XPATH_ABSOLUTE_DATE_INPUT = "//span[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::input[contains(@class, 'aui-DateInput-TextBox')]";
    protected static final String XPATH_RELATIVE_DATE_PLACEHOLDER = ".//input[contains(@class, 'aui-DateInput-Placeholder')]";
    protected static final String XPATH_RELATIVE_DATE_INPUT = ".//input[contains(@class, 'aui-DateInput-TextBox')]";
    protected static final String XPATH_ABSOLUTE_TIME_INPUT = "//span[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::input[contains(@class, 'aui-TimeInput-TextBox')]";
    protected static final String XPATH_RELATIVE_TIME_PLACEHOLDER = ".//input[contains(@class, 'aui-TimeInput-Placeholder')]";
    protected static final String XPATH_RELATIVE_TIME_INPUT = ".//input[contains(@class, 'aui-TimeInput-TextBox')]";
    
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
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_DATE_INPUT, fieldName))));
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_TIME_INPUT, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        String dateString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_INPUT)).getAttribute("value");
        String timeString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_TIME_INPUT)).getAttribute("value");
        
        String datetimeString = dateString + " " + timeString;

        try{  
            String compareString = new SimpleDateFormat(TempoObject.DATETIME_DISPLAY_FORMAT_STRING).format(DateUtils.parseDate(datetimeString, DATETIME_FORMAT_STRING));
            LOG.debug("DATETIME FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");
            
            return compareString.equals(fieldValue);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }        
    }
    
    private static boolean populateTempoDatetimeFieldWithDate(WebElement fieldLayout, Date d) {
        String dateValue = new SimpleDateFormat(DATE_FORMAT_STRING).format(d);
        
        WebElement datePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_PLACEHOLDER));
        WebElement dateField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_INPUT));
        
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
    
    private static boolean isEmptyDatetimeDateField(WebElement fieldLayout) {
        return !fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_INPUT)).isDisplayed();
    }
    
    private static boolean populateTempoDatetimeFieldWithTime(WebElement fieldLayout, Date d) {
        String timeValue = new SimpleDateFormat(TIME_FORMAT_STRING).format(d);
        
        WebElement timePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_TIME_PLACEHOLDER));
        WebElement timeField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_TIME_INPUT));
        
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
    
    private static boolean isEmptyDatetimeTimeField(WebElement fieldLayout) {
        return !fieldLayout.findElement(By.xpath(XPATH_RELATIVE_TIME_INPUT)).isDisplayed();
    }
}
