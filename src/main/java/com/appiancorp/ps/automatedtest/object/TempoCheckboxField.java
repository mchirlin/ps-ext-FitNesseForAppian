package com.appiancorp.ps.automatedtest.object;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Metadata;

public class TempoCheckboxField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoCheckboxField.class);
    private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL = Metadata.getByConstant("xpathAbsoluteCheckboxFieldLabel");
    private static final String XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_LABEL = Metadata.getByConstant("xpathRelativeCheckboxFieldChoiceLabel");
    private static final String XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_INDEX = Metadata.getByConstant("xpathRelativeCheckboxFieldChoiceIndex");
    private static final String XPATH_RELATIVE_CHECKBOX_FIELD_INPUT = Metadata.getByConstant("xpathRelativeCheckboxFieldInput");
    private static final String XPATH_RELATIVE_CHECKBOX_FIELD_INPUT_SPAN = Metadata.getByConstant("xpathRelativeCheckboxFieldInputSpan");
    private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION = Metadata.getByConstant("xpathAbsoluteCheckboxFieldOption");
    
    public static boolean populate(String fieldName, String fieldValue) {        
        WebElement fieldLayout = getFieldLayout(fieldName);
        
        return populate(fieldLayout, fieldName, fieldValue);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
 
        WebElement checkboxField;
        if (isFieldIndex(fieldValue)) {
            int index = getIndexFromFieldIndex(fieldValue);
            checkboxField = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_INDEX, index)));
        } else {
            checkboxField = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_LABEL, fieldValue)));
        }
        
        checkboxField.click();
        unfocus();
        
        LOG.debug("CHECKBOX FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName);
            scrollIntoView(fieldLayout);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static String getValue(WebElement fieldLayout) {
        List<String> values = new ArrayList<String>();
  
        for (WebElement span : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_CHECKBOX_FIELD_INPUT_SPAN))) {
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
            field = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_INDEX, index)));
        } else {
            field = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_LABEL, fieldValue)));
        }
        checked = field.getAttribute("checked") == null ? false : true;
        
        LOG.debug("CHECKBOX FIELD COMPARISON : Field value [" + fieldValue + "] is checked [" + checked + "]");
        
        return checked;
    }
    
    public static boolean isType(WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(XPATH_RELATIVE_CHECKBOX_FIELD_INPUT));
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean clickOption(String optionName) {
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION, optionName)));
        element.click();
        
        LOG.debug("CHECKBOX OPTION CLICK : " + optionName);
        
        return true;
    }
    
    public static boolean waitForOption(String optionName) {
        try {
            (new WebDriverWait(driver, timeoutSeconds)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION, optionName))));
            WebElement element = driver.findElement(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION, optionName)));
            scrollIntoView(element);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
