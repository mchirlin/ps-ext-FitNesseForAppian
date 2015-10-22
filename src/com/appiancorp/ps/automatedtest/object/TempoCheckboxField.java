package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoCheckboxField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoCheckboxField.class);
    private static final String XPATH_ABSOLUTE_LABEL = "//span[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::input";
    private static final String XPATH_NAME_RELATIVE_INPUT = ".//label[contains(text(), '%s')]/preceding-sibling::input";
    private static final String XPATH_NUM_RELATIVE_INPUT = "(.//input[contains(@type, 'checkbox')])[%d]";
    private static final String XPATH_RELATIVE_CHECKBOX_INPUT = ".//input[contains(@type, 'checkbox')]";
    
    public static boolean populate(String fieldName, String fieldValue) {        
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement checkboxField;
        if (isFieldIndex(fieldValue)) {
            int index = getIndexFromFieldIndex(fieldValue);
            checkboxField = fieldLayout.findElement(By.xpath(String.format(XPATH_NUM_RELATIVE_INPUT, index)));
        } else {
            checkboxField = fieldLayout.findElement(By.xpath(String.format(XPATH_NAME_RELATIVE_INPUT, fieldValue)));
        }
        
        checkboxField.click();
        
        LOG.debug("CHECKBOX FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_LABEL, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        // For read-only
        try {
            return TempoField.contains(fieldLayout, fieldValue);
        } catch (Exception e) {}
        
        // For editable
        String compareString;
        if (isFieldIndex(fieldValue)) {
            int index = getIndexFromFieldIndex(fieldValue);
            compareString = fieldLayout.findElement(By.xpath(String.format(XPATH_NUM_RELATIVE_INPUT, index))).getAttribute("checked");
        } else {
            compareString = fieldLayout.findElement(By.xpath(String.format(XPATH_NAME_RELATIVE_INPUT, fieldValue))).getAttribute("checked");
        }
        LOG.debug("CHECKBOX FIELD COMPARISON : Field value [" + fieldValue + "] is checked [" + compareString + "]");
        
        return compareString.equals("true");
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_CHECKBOX_INPUT));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
