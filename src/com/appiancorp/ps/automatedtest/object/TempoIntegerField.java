package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoIntegerField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoIntegerField.class);
    private static final String XPATH_ABSOLUTE_LABEL = "//label[contains(text(),'%s')]/parent::span/following-sibling::div/div/input";
    private static final String XPATH_RELATIVE_INPUT = ".//input[contains(@class, 'aui-TextInput')]";
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement intField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        intField.clear();
        intField.sendKeys(fieldValue);
        
        LOG.debug("INTEGER field: " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_LABEL, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clear(WebElement fieldLayout) {
        WebElement intField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        intField.clear();
        
        return true;
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
