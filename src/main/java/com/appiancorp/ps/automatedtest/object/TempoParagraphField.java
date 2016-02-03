package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.AppianVersions;

public class TempoParagraphField extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoParagraphField.class);
    private static final String XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL = AppianVersions.getByConstant("xpathAbsoluteParagraphFieldLabel");
    private static final String XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT = AppianVersions.getByConstant("xpathRelativeParagraphFieldInput");
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement textAreaField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT));
        textAreaField.clear();
        textAreaField.sendKeys(fieldValue);
        // For some reason paragraph fields have trouble when moving quickly
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        
        unfocus();
        
        LOG.debug("PARAGRAPH FIELD POPULATION : " + fieldValue);

        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_PARAGRAPH_FIELD_LABEL, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static String getValue(WebElement fieldLayout) {
        String value = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT)).getAttribute("value");
        LOG.debug("PARAGRAPH FIELD VALUE : " + value);
        
        return value;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        // For read-only
        try {
            return TempoField.contains(fieldLayout, fieldValue);
        } catch (Exception e) {}
        
        // For editable
        String compareString = getValue(fieldLayout);
        LOG.debug("PARAGRAPH FIELD COMPARISON : Field value [" + fieldValue + "] compared to Entered value [" + compareString + "]");
        
        return compareString.contains(fieldValue);
    }
    
    public static boolean clear(WebElement fieldLayout) {
        WebElement textAreaField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT));
        textAreaField.clear();
        
        return true;
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_PARAGRAPH_FIELD_INPUT));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
