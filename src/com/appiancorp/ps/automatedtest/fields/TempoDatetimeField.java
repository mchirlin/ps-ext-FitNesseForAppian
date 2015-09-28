package com.appiancorp.ps.automatedtest.fields;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoDatetimeField extends TempoPickerField{
    
    public static boolean populate(WebDriver driver, int timeOutSeconds, String fieldName, String[] fieldValues) {
        WebElement fieldLayout = getFieldLayout(driver, timeOutSeconds, fieldName);
        
        return populate(fieldLayout, timeOutSeconds, fieldValues);
    }
    
    public static boolean populate(WebElement fieldLayout, int timeOutSeconds, String[] fieldValues) {
        String dateValue, timeValue;
        
        if (fieldValues[0].contains("+")) {
            Date d = new Date();
            int addValue = Integer.parseInt(fieldValues[0].replaceAll("[^0-9]", ""));
            if (fieldValues[0].contains("minute")) {
                d = DateUtils.addMinutes(d, addValue);
            } else if (fieldValues[0].contains("hour")) {
                d = DateUtils.addHours(d, addValue);
            } else if (fieldValues[0].contains("day")) {
                d = DateUtils.addDays(d, addValue);
            }
            
            dateValue = new SimpleDateFormat("MM/dd/yyyy").format(d);
            timeValue = new SimpleDateFormat("hh:mm aaa").format(d);
        } else {
            dateValue = fieldValues[0];
            timeValue = fieldValues[1];
        }
        
        populateTempoDatetimeFieldWithDate(fieldLayout, timeOutSeconds, dateValue);
        populateTempoDatetimeFieldWithTime(fieldLayout, timeOutSeconds, timeValue);
        
        return true;
    }
    
    public static boolean waitFor(WebDriver driver, int timeOutSeconds, String fieldName) {
        try {
            (new WebDriverWait(driver, timeOutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+fieldName+"')]/parent::span/following-sibling::div/descendant::input")));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    protected static boolean populateTempoDatetimeFieldWithDate(WebElement fieldLayout, int timeOutSeconds, String dateValue) {
        WebElement datePlaceholder = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-DateInput-Placeholder')]"));
        datePlaceholder.click();
        
        WebElement dateField = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-DateInput-TextBox')]"));
        dateField.clear();
        dateField.sendKeys(dateValue);
        
        return true;
    }
    
    protected static boolean populateTempoDatetimeFieldWithTime(WebElement fieldLayout, int timeOutSeconds, String timeValue) {
        WebElement timePlaceholder = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-TimeInput-Placeholder')]"));
        timePlaceholder.click();
        
        WebElement timeField = fieldLayout.findElement(By.xpath(".//input[contains(@class, 'aui-TimeInput-TextBox')]"));
        timeField.clear();
        timeField.sendKeys(timeValue);
        timeField.sendKeys(Keys.TAB);
        
        return true;
    }    
}
