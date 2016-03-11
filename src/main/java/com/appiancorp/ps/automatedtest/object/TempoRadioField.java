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

public class TempoRadioField extends TempoField {

    private static final Logger LOG = Logger.getLogger(TempoRadioField.class);
    private static final String XPATH_ABSOLUTE_RADIO_FIELD_LABEL = Settings.getByConstant("xpathAbsoluteRadioFieldLabel");
    private static final String XPATH_RELATIVE_RADIO_FIELD_CHOICE_LABEL = Settings.getByConstant("xpathRelativeRadioFieldChoiceLabel");
    private static final String XPATH_RELATIVE_RADIO_FIELD_CHOICE_INDEX = Settings.getByConstant("xpathRelativeRadioFieldChoiceIndex");
    private static final String XPATH_RELATIVE_RADIO_BUTTON_GROUP = Settings.getByConstant("xpathRelativeRadioButtonGroup");
    private static final String XPATH_RELATIVE_RADIO_FIELD_INPUT_SPAN = Settings.getByConstant("xpathRelativeRadioFieldInputSpan");
    private static final String XPATH_ABSOLUTE_RADIO_FIELD_OPTION = Settings.getByConstant("xpathAbsoluteRadioFieldOption");
    private static final String XPATH_ABSOLUTE_RADIO_FIELD_OPTION_INDEX ="(" + XPATH_ABSOLUTE_RADIO_FIELD_OPTION + ")[%d]";
    
    public static boolean populate(WebElement fieldLayout, String fieldValue, Settings s) {
        WebElement radioField;
        if (isFieldIndex(fieldValue)) {
            int index = getIndexFromFieldIndex(fieldValue);
            LOG.debug(fieldLayout.toString());
            radioField = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_RADIO_FIELD_CHOICE_INDEX, index)));
        } else {
            radioField = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_RADIO_FIELD_CHOICE_LABEL, fieldValue)));
        }
        radioField.click();
        unfocus(s);
        LOG.debug("RADIO FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(String fieldName, Settings s) {
        try {
            (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RADIO_FIELD_LABEL, fieldName))));
            WebElement fieldLayout = getFieldLayout(fieldName, s);
            scrollIntoView(fieldLayout, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
    
    public static String getValue(WebElement fieldLayout) {
        List<String> values = new ArrayList<String>();
        
        for (WebElement span : fieldLayout.findElements(By.xpath(XPATH_RELATIVE_RADIO_FIELD_INPUT_SPAN))) {
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
            compareString = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_RADIO_FIELD_CHOICE_INDEX, index))).getAttribute("checked");
        } else {
            compareString = fieldLayout.findElement(By.xpath(String.format(XPATH_RELATIVE_RADIO_FIELD_CHOICE_LABEL, fieldValue))).getAttribute("checked");
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
    
    public static WebElement getOptionLayout(String optionName, Settings s) {
        if (isFieldIndex(optionName)) {
            String oName = getFieldFromFieldIndex(optionName);
            int index = getIndexFromFieldIndex(optionName);
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RADIO_FIELD_OPTION_INDEX, oName, index)));
        } else {
            return s.getDriver().findElement(By.xpath(String.format(XPATH_ABSOLUTE_RADIO_FIELD_OPTION, optionName)));
        }        
    }
    
    public static boolean clickOption(String optionName, Settings s) {
        WebElement element = getOptionLayout(optionName, s);
        element.click();
        
        LOG.debug("RADIO BUTTON OPTION CLICK : " + optionName);
        
        return true;
    }
    
    public static boolean waitForOption(String optionName, Settings s) {
        try {
            if (isFieldIndex(optionName)) {
                String oName = getFieldFromFieldIndex(optionName);
                int index = getIndexFromFieldIndex(optionName);
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RADIO_FIELD_OPTION_INDEX, oName, index))));
            } else {
                (new WebDriverWait(s.getDriver(), s.getTimeoutSeconds())).until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(XPATH_ABSOLUTE_RADIO_FIELD_OPTION, optionName))));
            } 
            WebElement element = getOptionLayout(optionName, s);
            scrollIntoView(element, s);
        } catch (TimeoutException e) {
            return false;
        }
        
        return true;
    }
}
