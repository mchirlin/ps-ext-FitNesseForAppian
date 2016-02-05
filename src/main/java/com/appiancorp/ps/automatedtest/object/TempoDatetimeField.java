package com.appiancorp.ps.automatedtest.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;

public class TempoDatetimeField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoDatetimeField.class);
    private static final String XPATH_ABSOLUTE_DATETIME_FIELD_DATE_INPUT = Metadata.getByConstant("xpathAbsoluteDatetimeFieldDateInput");
    private static final String XPATH_RELATIVE_DATETIME_FIELD_DATE_PLACEHOLDER = Metadata.getByConstant("xpathRelativeDatetimeFieldDatePlaceholder");
    private static final String XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT = Metadata.getByConstant("xpathRelativeDatetimeFieldDateInput");
    private static final String XPATH_ABSOLUTE_DATETIME_FIELD_TIME_INPUT = Metadata.getByConstant("xpathAbsoluteDatetimeFieldTimeInput");
    private static final String XPATH_RELATIVE_DATETIME_FIELD_TIME_PLACEHOLDER = Metadata.getByConstant("xpathRelativeDatetimeFieldTimePlaceholder");
    private static final String XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT = Metadata.getByConstant("xpathRelativeDatetimeFieldTimeInput");
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) throws ParseException {        
        Date d = parseDate(fieldValue);
        
        populateTempoDatetimeFieldWithDate(fieldLayout, d);
        populateTempoDatetimeFieldWithTime(fieldLayout, d);
        unfocus();
        
        LOG.debug("DATETIME FIELD POPULTATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_DATETIME_FIELD_DATE_INPUT, fieldName))));
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_DATETIME_FIELD_TIME_INPUT, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }
    
    public static String getValue(WebElement fieldLayout) {
        String dateString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT)).getAttribute("value");
        String timeString = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT)).getAttribute("value");
        
        String value = dateString + " " + timeString;
        LOG.debug("DATE FIELD VALUE : " + value);
        
        return value;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        String datetimeString = getValue(fieldLayout);

        try{  
            Date compareDate = parseDate(datetimeString);
            Date fieldDate = parseDate(fieldValue);
            LOG.debug("DATETIME FIELD COMPARISON : Field value [" + fieldDate.toString() + "] compared to Entered value [" + fieldDate.toString() + "]");
            
            return DateUtils.isSameInstant(compareDate, fieldDate);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }        
    }
    
    private static boolean populateTempoDatetimeFieldWithDate(WebElement fieldLayout, Date d) {
        String dateValue = new SimpleDateFormat(dateFormat).format(d);
        
        WebElement datePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_PLACEHOLDER));
        WebElement dateField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT));
        
        // Clear out existing values
        if (dateField.isDisplayed()) {
            dateField.click();
            dateField.sendKeys(Keys.CONTROL + "a");
            dateField.sendKeys(Keys.DELETE);
            dateField.sendKeys(dateValue);
        } else {
            datePlaceholder.click();
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOf(dateField));
            dateField.sendKeys(dateValue);
        }
                
        return true;
    }
    
    private static boolean populateTempoDatetimeFieldWithTime(WebElement fieldLayout, Date d) {
        String timeValue = new SimpleDateFormat(timeFormat).format(d);
        
        WebElement timePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_PLACEHOLDER));
        WebElement timeField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT));
        
        // Clear out existing values
        if (timeField.isDisplayed()) {
            timeField.click();
            timeField.sendKeys(Keys.CONTROL + "a");
            timeField.sendKeys(Keys.DELETE);
            timeField.sendKeys(timeValue);
        } else {
            timePlaceholder.click();
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOf(timeField));
            timeField.sendKeys(timeValue);
        }
        
        return true;
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_DATE_INPUT));
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATETIME_FIELD_TIME_INPUT));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
