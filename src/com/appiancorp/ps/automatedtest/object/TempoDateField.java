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

public class TempoDateField extends TempoField{

    private static final Logger LOG = Logger.getLogger(TempoDateField.class);
    private static final String XPATH_ABSOLUTE_DATE_INPUT = "//label[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::input[contains(@class, 'aui-DateInput-TextBox')]";
    private static final String XPATH_RELATIVE_DATE_PLACEHOLDER = ".//input[contains(@class, 'aui-DateInput-Placeholder')]";
    private static final String XPATH_RELATIVE_DATE_INPUT = ".//input[contains(@class, 'aui-DateInput-TextBox')]";
    
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
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_DATE_INPUT, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    @SuppressWarnings("deprecation")
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        String dateString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_INPUT)).getAttribute("value");

        try{  
            Date compareDate = DateUtils.parseDate(dateString, DATE_FORMAT_STRING);
            Date fieldDate = DateUtils.parseDate(fieldValue, DATETIME_DISPLAY_FORMAT_STRING);
            LOG.debug("DATE FIELD COMPARISON : Field value [" + compareDate.toString() + "] compared to Entered value [" + fieldDate.toString() + "]");
            
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
        
        WebElement datePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_PLACEHOLDER));
        WebElement dateField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_INPUT));
        
        // Clear out existing values
        if (!isEmptyDate(fieldLayout)) {
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
    
    private static boolean isEmptyDate(WebElement fieldLayout) {
        return !fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_INPUT)).isDisplayed();
    }
}
