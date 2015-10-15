package com.appiancorp.ps.automatedtest.object;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRadioField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoUserPickerField.class);
    protected static final String XPATH_ABSOLUTE_LABEL = "//span[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::input";
    protected static final String XPATH_RELATIVE_INPUT = ".//label[contains(text(), '%s')]/preceding-sibling::input";
    
    public static boolean populate(String fieldName, String fieldValue) {
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement radioField = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_INPUT, fieldValue)));
        radioField.click();
        
        LOG.debug("RADIO FIELD POPULATION : " + fieldValue);
        
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
        String compareString = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_INPUT, fieldValue))).getAttribute("checked");
        LOG.debug("RADIO FIELD COMPARISON : Field value [" + fieldValue + "] is checked [" + compareString + "]");
        
        return compareString.equals("true");
    }
}
