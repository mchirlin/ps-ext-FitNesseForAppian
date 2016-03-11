package com.appiancorp.ps.automatedtest.object;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appiancorp.ps.automatedtest.common.Settings;

public class TempoCheckboxField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoCheckboxField.class);
    private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteCheckboxFieldLabel");
    private static final String XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_LABEL = Settings.getByConstant("xpathRelativeCheckboxFieldChoiceLabel");
    private static final String XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_INDEX = Settings.getByConstant("xpathRelativeCheckboxFieldChoiceIndex");
    private static final String XPATH_RELATIVE_CHECKBOX_FIELD_INPUT = Settings.getByConstant("xpathRelativeCheckboxFieldInput");
    private static final String XPATH_RELATIVE_CHECKBOX_FIELD_INPUT_SPAN = Settings.getByConstant("xpathRelativeCheckboxFieldInputSpan");
    private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION = Settings.getByConstant("xpathAbsoluteCheckboxFieldOption");
    private static final String XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION_INDEX ="(" + XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION + ")[%d]";
    
    public static boolean populate(String fieldName, String fieldValue, Settings s) {        
        WebElement fieldLayout = getFieldLayout(fieldName, s);
        
        return populate(fieldLayout, fieldName, fieldValue, s);
    }
    
    public static boolean populate(WebElement fieldLayout, String fieldValue, Settings s) {
 
        WebElement checkboxField;
        if (isFieldIndex(fieldValue)) {
            int index = getIndexFromFieldIndex(fieldValue);
            checkboxField = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_INDEX, index)));
        } else {
            checkboxField = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_CHECKBOX_FIELD_CHOICE_LABEL, fieldValue)));
        }
        
        checkboxField.click();
        unfocus(s);
        
        LOG.debug("CHECKBOX FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_LABEL, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName, s);
            scrollIntoView(fieldLayout, s);
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
    
    public static WebElement getOptionLayout(String optionName, Settings s) {
        if (isFieldIndex(optionName)) {
            String oName = getFieldFromFieldIndex(optionName);
            int index = getIndexFromFieldIndex(optionName);
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION_INDEX, oName, index)));
        } else {
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION, optionName)));
        }        
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
    
    public static boolean clickOption(String optionName, Settings s) {
        WebElement element = getOptionLayout(optionName, s);
        element.click();
        
        LOG.debug("CHECKBOX OPTION CLICK : " + optionName);
        
        return true;
    }
    
    public static boolean waitForOption(String optionName, Settings s) {
        try {
            if (isFieldIndex(optionName)) {
                String oName = getFieldFromFieldIndex(optionName);
                int index = getIndexFromFieldIndex(optionName);
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION_INDEX, oName,  index))));
            } else {
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_CHECKBOX_FIELD_OPTION, optionName))));
            }
            WebElement element =getOptionLayout(optionName,s);
            scrollIntoView(element, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
