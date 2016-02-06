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

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoDateField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoDateField.class);
    private static final String XPATH_ABSOLUTE_DATE_FIELD_INPUT = Settings.getByConstant("xpathAbsoluteDateFieldInput");
    private static final String XPATH_RELATIVE_DATE_FIELD_PLACEHOLDER = Settings.getByConstant("xpathRelativeDateFieldPlaceholder");
    private static final String XPATH_RELATIVE_DATE_FIELD_INPUT = Settings.getByConstant("xpathRelativeDateFieldInput");
    
    public static boolean populate(WebElement fieldLayout, String fieldValue, Settings s) throws ParseException {        
        fieldValue = parseVariable(fieldValue, s);
        Date d = parseDate(fieldValue, s);

        populateTempoDateFieldWithDate(fieldLayout, d, s);
        unfocus(s);
        
        LOG.debug("DATE FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_DATE_FIELD_INPUT, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName, s);
            scrollIntoView(fieldLayout, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static String getValue(WebElement fieldLayout) {
        String value = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_INPUT)).getAttribute("value");
        LOG.debug("DATE FIELD VALUE : " + value);
        
        return value;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue, Settings s) {
        String dateString = getValue(fieldLayout);

        try{  
            Date compareDate = parseDate(dateString, s);
            Date fieldDate = parseDate(fieldValue, s);
            LOG.debug("DATE FIELD COMPARISON : Field value [" + compareDate.toString() + "] compared to Entered value [" + fieldDate.toString() + "]");
            
            return DateUtils.isSameDay(compareDate, fieldDate);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }        
    }
    
    private static boolean populateTempoDateFieldWithDate(WebElement fieldLayout, Date d, Settings s) {
        String dateValue = new SimpleDateFormat(s.getDateFormat()).format(d);
        
        WebElement datePlaceholder = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_PLACEHOLDER));
        WebElement dateField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_INPUT));
        
        // Clear out existing values
        if (dateField.isDisplayed()) {
            dateField.click();
            dateField.sendKeys(Keys.CONTROL + "a");
            dateField.sendKeys(Keys.DELETE);
            dateField.sendKeys(dateValue);
        } else {
            datePlaceholder.click();
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOf(dateField));
            dateField.sendKeys(dateValue);
        }
                
        return true;
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_DATE_FIELD_INPUT));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
