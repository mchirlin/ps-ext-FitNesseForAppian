package com.appiancorp.ps.automatedtest.object;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoCheckboxField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoCheckboxField.class);
    private static final String XPATH_ABSOLUTE_LABEL = "//span[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::input";
    private static final String XPATH_NAME_RELATIVE_INPUT = ".//label[contains(text(), '%s')]/preceding-sibling::input";
    private static final String XPATH_NUM_RELATIVE_INPUT = "(.//input[contains(@type, 'checkbox')])[%d]";
    private static final String XPATH_RELATIVE_CHECKBOX_INPUT = ".//input[contains(@type, 'checkbox')]";
    private static final String XPATH_RELATIVE_INPUT_SPAN = ".//span[input[contains(@type, 'checkbox')]]";
    private static final String XPATH_OPTION = "//label[contains(text(), '%s')]/preceding-sibling::input[@type = 'checkbox']";
    
    public static boolean populate(String fieldName, String fieldValue) {        
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldName, fieldValue);
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
        unfocus();
        
        LOG.debug("CHECKBOX FIELD POPULATION : " + fieldValue);
        
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
    
    public static String getValue(WebElement fieldLayout) {
        List<String> values = new ArrayList<String>();
  
        for (WebElement span : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_INPUT_SPAN))) {
            if (span.findElement(By.tagName("input")).isSelected()) {
                values.add(span.findElement(By.tagName("label")).getText());
            }
        }
        
        LOG.debug("CHECKBOX FIELD VALUE : " + values.toString());
        
        return String.join(",", values);
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        // For read-only
        try {
            return TempoField.contains(fieldLayout, fieldValue);
        } catch (Exception e) {}
        
        // For editable
        boolean checked;
        WebElement field;
        if (isFieldIndex(fieldValue)) {
            int index = getIndexFromFieldIndex(fieldValue);
            field = fieldLayout.findElement(By.xpath(String.format(XPATH_NUM_RELATIVE_INPUT, index)));
        } else {
            field = fieldLayout.findElement(By.xpath(String.format(XPATH_NAME_RELATIVE_INPUT, fieldValue)));
        }
        checked = field.getAttribute("checked") == null ? false : true;
        
        LOG.debug("CHECKBOX FIELD COMPARISON : Field value [" + fieldValue + "] is checked [" + checked + "]");
        
        return checked;
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_CHECKBOX_INPUT));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOption(String optionName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_OPTION, optionName)));
        element.click();
        
        LOG.debug("CHECKBOX OPTION CLICK : " + optionName);
        
        return true;
    }
    
    public static boolean waitForOption(String optionName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_OPTION, optionName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_OPTION, optionName)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
