package com.appiancorp.ps.automatedtest.object;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TempoSelectField extends TempoField {
    
    private static final Logger LOG = Logger.getLogger(TempoSelectField.class);
    private static final String XPATH_ABSOLUTE_LABEL = "//span[contains(text(),'%s')]/parent::span/following-sibling::div/descendant::div/select";
    private static final String XPATH_RELATIVE_INPUT = ".//select";
    
    public static boolean populate(WebElement fieldLayout, String fieldValue) {
        WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        Select select = new Select(selectField);
        select.selectByVisibleText(fieldValue);
        unfocus();
        
        LOG.debug("SELECT FIELD POPULATION : " + fieldValue);
        
        return true;
    }
    
    public static boolean waitFor(WebDriver driver, String fieldName) {
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
        WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        Select select = new Select(selectField);
        List<WebElement> selects = select.getAllSelectedOptions();
        for (WebElement s : selects) {
            values.add(s.getText());
        }
        
        LOG.debug("SELECT FIELD VALUE : " + values.toString());
        
        return String.join(",", values);
    }
    
    public static boolean contains(WebElement fieldLayout, String fieldValue) {
        // For read-only
        try {
            return TempoField.contains(fieldLayout, fieldValue);
        } catch (Exception e) {}

        // For editable
        boolean selected = false;
        WebElement selectField = fieldLayout.findElement(By.xpath(XPATH_RELATIVE_INPUT));
        Select select = new Select(selectField);
        List<WebElement> selects = select.getAllSelectedOptions();
        for (WebElement s : selects) {
            if (s.getText().contains(fieldValue)) {
                selected = true;
                break;
            }
        }
        
        LOG.debug("SELECT FIELD COMPARISON : Field value [" + fieldValue + "] was selected [" + selected + "]");
        
        return selected;
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
