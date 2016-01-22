package com.appiancorp.ps.automatedtest.object;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoRadioField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoRadioField.class);
    private static final String XPATH_ABSOLUTE_LABEL = "//span[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::input";
    private static final String XPATH_NAME_RELATIVE_INPUT = ".//label[contains(text(), '%s')]/preceding-sibling::input";
    private static final String XPATH_NUM_RELATIVE_INPUT = "(.//input[contains(@type, 'radio')])[%d]";
    private static final String XPATH_RELATIVE_RADIO_BUTTON_GROUP = ".//div[contains(@class, 'RadioButtonGroup')]";
    private static final String XPATH_RELATIVE_INPUT_SPAN = ".//span[input[contains(@type, 'radio')]]";
    private static final String XPATH_OPTION = "//label[contains(text(), '%s')]/preceding-sibling::input[@type = 'radio']";
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement radioField;
        if (isFieldIndex(fieldValue)) {
            int index = getIndexFromFieldIndex(fieldValue);
            LOG.debug(fieldLayout.toString());
            radioField = fieldLayout.findElement(By.xpath(String.format(XPATH_NUM_RELATIVE_INPUT, index)));
        } else {
            radioField = fieldLayout.findElement(By.xpath(String.format(XPATH_NAME_RELATIVE_INPUT, fieldValue)));
        }
        radioField.click();
        unfocus();
        LOG.debug("RADIO FIELD POPULATION : " + fieldValue);
        
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
        
        LOG.debug("RADIO FIELD VALUE : " + values.toString());
        
        return String.join(",", values);
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
        LOG.debug("RADIO FIELD COMPARISON : Field value [" + fieldValue + "] is checked [" + compareString + "]");
        
        return compareString.equals("true");
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_RADIO_BUTTON_GROUP));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOption(String optionName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_OPTION, optionName)));
        element.click();
        
        LOG.debug("RADIO BUTTON OPTION CLICK : " + optionName);
        
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
