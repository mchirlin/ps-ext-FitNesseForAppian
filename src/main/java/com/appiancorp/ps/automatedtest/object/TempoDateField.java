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

import com.appiancorp.ps.automatedtest.common.AppianVersions;

public class TempoDateField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoDateField.class);
    private static final String XPATH_ABSOLUTE_DATE_FIELD_INPUT = AppianVersions.getByConstant("xpathAbsoluteDateFieldInput");
    private static final String XPATH_RELATIVE_DATE_FIELD_PLACEHOLDER = AppianVersions.getByConstant("xpathRelativeDateFieldPlaceholder");
    private static final String XPATH_RELATIVE_DATE_FIELD_INPUT = AppianVersions.getByConstant("xpathRelativeDateFieldInput");
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) throws ParseException {        
        fieldValue = parseVariable(fieldValue);
        Date d = parseDate(fieldValue);

        populateTempoDateFieldWithDate(fieldLayout, d);
        unfocus();
        
        LOG.debug("DATE FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_DATE_FIELD_INPUT, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
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
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        String dateString = getValue(fieldLayout);

        try{  
            Date compareDate = parseDate(dateString);
            Date fieldDate = parseDate(fieldValue);
            LOG.debug("DATE FIELD COMPARISON : Field value [" + compareDate.toString() + "] compared to Entered value [" + fieldDate.toString() + "]");
            
            return DateUtils.isSameDay(compareDate, fieldDate);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }        
    }
    
    private static boolean populateTempoDateFieldWithDate(WebElement fieldLayout, Date d) {
        String dateValue = new SimpleDateFormat(DATE_FORMAT_STRING).format(d);
        
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
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOf(dateField));
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
