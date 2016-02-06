package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoIntegerField extends TempoTextField {

    private static final Logger LOG = Logger.getLogger(TempoIntegerField.class);
    private static final String XPATH_ABSOLUTE_INTEGER_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteIntegerFieldLabel");
    private static final String XPATH_RELATIVE_INTEGER_FIELD_INPUT = Settings.getByConstant("xpathRelativeIntegerFieldInput");
    
    public static boolean populate(WebElement fieldLayout, String fieldValue, Settings s) {
        WebElement intField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INTEGER_FIELD_INPUT));
        intField.clear();
        intField.sendKeys(fieldValue);
        unfocus(s);
        
        LOG.debug("INTEGER field: " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_INTEGER_FIELD_LABEL, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName, s);
            scrollIntoView(fieldLayout, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clear(WebElement fieldLayout) {
        WebElement intField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INTEGER_FIELD_INPUT));
        intField.clear();
        
        return true;
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INTEGER_FIELD_INPUT));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
